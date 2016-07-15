from rest_framework import viewsets, status
from rest_framework.views import APIView
from models import Product
from serializers import ProductSerializer
from django.http import HttpResponse
from django.http import JsonResponse
from django.db.models import Count, F


class ProductSummaryList(APIView):
    """ Returns product summary.
    Query parameters :
        code, category_name : Filters
        groupBy = category
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

            products = products.annotate(category_id=F('categoryid__id')).values('category_id').annotate(count=Count('productid'))

            temp = dict()
            temp['data'] = list(products)
            return JsonResponse(temp)


class ProductViewSet(viewsets.ModelViewSet):
    """ Create, update are handled by ProductSerializer
    Deletions are handled by the isavailable field of Product Model.
    """
    queryset = Product.objects.filter(isavailable=1)
    serializer_class = ProductSerializer

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.isavailable = 0
        instance.save()
        return HttpResponse(status=status.HTTP_204_NO_CONTENT)