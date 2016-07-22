from __future__ import print_function
from rest_framework import viewsets, status, generics
from rest_framework.views import APIView
from django.http import HttpResponse
from django.http import JsonResponse
from django.db.models import Count, F, Q
from django.shortcuts import get_object_or_404
from models import Product
from models import Orders
from models import Orderdetails
from serializers import ProductSerializer
from serializers import OrdersSerializer
from serializers import OrderDetailsSerializer
from boto.sqs.message import  Message
from globalVariables import addMessageToQueue


def getHealth(request):
    addMessageToQueue("Entering and exiting health API")
    return JsonResponse({},safe=False)

class ProductSummaryList(APIView):
    """ Returns product summary.
    Query parameters :
        code, category_name : Filters
        groupBy = category
    """

    def get(self, request):
        addMessageToQueue("Entering Product Summary: get")

        if request.method == 'GET':
            code = request.GET.get('code', None)
            category_name = request.GET.get('category_name', None)
            products = Product.objects.filter(isavailable=1)
            if code is not None:
                products = products.filter(productcode=str(code))
            if category_name is not None:
                products = products.filter(categoryid__name=category_name)
            if request.GET.get('group_by') == 'category':
                products = products.annotate(category_id=F('categoryid__id'),
                                             category_name=F('categoryid__name')).values('category_id',
                                                                                         'category_name').annotate(
                    count=Count('productid'))
            else:
                products = [{'count': products.count()}]
            addMessageToQueue("Exiting Product Summary: get")

            return JsonResponse(list(products), safe=False);


class ProductViewSet(viewsets.ModelViewSet):
    """ Create, update are handled by ProductSerializer
    Deletions are handled by the isavailable field of Product Model.
    """
    queryset = Product.objects.filter(isavailable=1).prefetch_related('categoryid')
    serializer_class = ProductSerializer

    def destroy(self, request, *args, **kwargs):
        addMessageToQueue("Entering Destroying Product");
        instance = self.get_object()
        instance.isavailable = 0
        instance.save()
        addMessageToQueue("Exiting Destroying Product");
        return HttpResponse(status=status.HTTP_204_NO_CONTENT)


class OrdersViewSet(viewsets.ModelViewSet):
    """ Create, update are handled by OrdersSerializer
    Deletions are handled by the status field of Orders Model.
    """
    queryset = Orders.objects.filter(~Q(status="Deleted"))
    serializer_class = OrdersSerializer

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.status = "Deleted"
        instance.save()
        return HttpResponse(status=status.HTTP_204_NO_CONTENT)


class OrderDetailsViewSet(viewsets.ModelViewSet):
    serializer_class = OrderDetailsSerializer

    def get_queryset(self):
        queryset = Orderdetails.objects.filter(~Q(orderid__status="Deleted"))
        queryset = queryset.filter(orderid__orderid=int(self.kwargs['order_id']))
        return queryset;

    def create(self, request, *args, **kwargs):
        order = get_object_or_404(Orders, orderid=kwargs['order_id'])
        if order.status == "Deleted":
            return JsonResponse(status=404)
        product = get_object_or_404(Product, productid=request.data['product_id'], isavailable=True)
        orderdetails = Orderdetails.objects.create(productid=product, orderid=order,
                                                   sellprice=request.data['price'],
                                                   quantity=request.data.get('quantity', 0))
        serializer = OrderDetailsSerializer(orderdetails)
        return JsonResponse(serializer.data, status=status.HTTP_201_CREATED,safe=False)


class OrderSummaryList(APIView):
    """ Returns order summary.
    Query parameters :
        code, category_name : Filters
        groupBy = category, product
    """

    def get(self, request):
        if request.method == 'GET':
            code = request.GET.get('code', None)
            category_name = request.GET.get('category_name', None)
            products = Product.objects.filter(isavailable=1)
            if code is not None:
                products = products.filter(productcode=str(code))
            if category_name is not None:
                products = products.filter(categoryid__name=category_name)
            if request.GET.get('group_by') == 'category':
                products = products.annotate(category_id=F('categoryid__id')).values('category_id').annotate(
                    count=Count('productid'))
            else:
                products = [{'count': products.count()}]
            return JsonResponse(list(products),safe=False)


class MiddleWare(object):
    def process_response(self, request, response):
        if (response is not None):
            if (response._container is not None and response._container is not ['']):
                response._container = ['{"data":' + response._container[0] + '}']

        return response
