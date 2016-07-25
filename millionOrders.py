import database
from database import populateOrder
from database import populateOrderDetail
from random import randint
from datetime import date
from time import strftime

left = 1000000
while left > 0:
    print left
    start_date = date.today().replace(day=1, month=1).toordinal()
    end_date = date.today().toordinal()
    random_day = date.fromordinal(randint(start_date, end_date))
        
    # Create an order
    row = {}
    row['orderDate'] = random_day.strftime('%Y-%m-%d')
    row['status'] = 'Created';
    user_id = randint(1, 120)
    product_id = randint(1, 1000)
    order_id = populateOrder(row, product_id, user_id)
    
    # Create these many order details corresponding to this order. 
    count = randint(1, min(1000, left))
    left -= count
    for i in xrange(count):
        row['quantityOrdered'] = randint(1, 100)
        row['buyPrice'] = randint(1, 100)
        row['priceEach'] = randint(1, 100)
        populateOrderDetail(row, order_id, product_id)