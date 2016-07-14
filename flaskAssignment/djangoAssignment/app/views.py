from __future__ import print_function
from models import Orders,Orderdetails
from django.http import JsonResponse
from django.db.models import Count,Sum, F
import datetime
def get_daily_sales_report(request):
    if request.method == 'GET':
        start_time = request.GET.get('startDate', '01/31/2000')
        end_time = request.GET.get('endDate', '01/31/3000')

        start_time = datetime.datetime.strptime(start_time, '%m/%d/%Y').strftime('%Y-%m-%d')
        end_time = datetime.datetime.strptime(end_time, '%m/%d/%Y').strftime('%Y-%m-%d')

        # Choose only dates in specified range
        data = Orderdetails.objects.filter(orderid__orderdate__range=(start_time, end_time))

        # Rename orderid__orderdate to date
        data = data.annotate(date=F('orderid__orderdate')).values('date')

        # Calling annotate after values will function as group_by. Define relevant fields
        data = data.annotate(orders=Count('orderid__orderid'),
                             qty=Sum('quantity'),
                             buy_price=Sum(F('quantity') * F('buyprice')),
                             sale_price=Sum(F('quantity') * F('sellprice')))

        # Sort in decreasing order of date
        data = data.annotate(profit=F('sale_price') - F('buy_price')).order_by('-date');

        for x in data:
            # x['date'] = datetime.date.strptime(x['date'], '%Y-%m-%d').strftime("%m/%d/%Y");
            x['date'] = datetime.date.strftime(x['date'], "%m/%d/%Y");

        temp = dict()
        temp['data'] = list(data)
        return JsonResponse(temp)