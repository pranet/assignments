# sudo pip install SQLAlchemy
# sudo pip install pymysql
import csv
import config
from sqlalchemy import *
	
metadata = MetaData()
db = create_engine("mysql+pymysql://" + config.username + ":" + config.password + "@" + config.host + "/" + config.database);
connection = db.connect()

#User is inserted only if customerName is not None, and does not already exists in the DB
def populateUser(row):
	ID = None
	if row['customerName']:
		table = Table('User', metadata, autoload=True, autoload_with=db)
		sel = select([table.c.userID]).where(table.c.customerName == row['customerName'])
		for r in db.execute(sel):
			ID = r[0]
		if not ID:
			ins = table.insert().values(customerName = row['customerName'], 
										contactLastName = row['contactLastName'], 
										contactFirstName = row['contactFirstName'],
										phone = row['phone']);
			res = connection.execute(ins)
			ID = res.inserted_primary_key[0]
	return ID

#Address is inserted if atleast one field is non None
def populateAddress(row, user_ID):
	if row['addressLine1'] or row['addressLine2'] or row['city'] or row['state'] or row['country'] or row['postalCode']:
		table = Table('Address', metadata, autoload=True, autoload_with=db)
		ins = table.insert().values(userID = user_ID,
									addressLine1 = row['addressLine1'], 
									addressLine2 = row['addressLine2'], 
									city = row['city'],
									state = row['state'],
									country = row['country'],
									postalCode = row['postalCode']);
		connection.execute(ins)

#Product is inserted if atleast one of name or code is not None
def populateProducts(row):
	ID = None
	if row['productName'] or row['productCode']:
		table = Table('Product', metadata, autoload=True, autoload_with=db)
		ins = table.insert().values(productName = row['productName'],
									productCode = row['productCode'],
									productDescription = row['productDescription'],
									quantityInStock = row['quantityInStock'],
									buyPrice = '1000',
									sellPrice = '1000');
		res = connection.execute(ins)
		ID = res.inserted_primary_key[0]
	return ID

#Order is inserted if productID is not None
def populateOrder(row, product_ID, user_ID):
	ID = None
	if productID:
		table = Table('Order', metadata, autoload=True, autoload_with=db)
		ins = table.insert().values(userID = user_ID,
									orderDate = row['orderDate'],
									status = row['status']);
		res = connection.execute(ins)
		ID = res.inserted_primary_key[0]
	return ID

#Order detail is inserted only if orderID is not None
def populateOrderDetail(row, order_ID, product_ID):
	if order_ID:
		table = Table('OrderDetails', metadata, autoload=True, autoload_with=db)
		ins = table.insert().values(orderID = order_ID,
									productID = product_ID,
									quantity = row['quantityOrdered'],
									sellPrice = row['priceEach'],
									buyPrice = row['buyPrice']);
		connection.execute(ins);
cnt = 0
with open('a3.csv', 'rb') as f:
    reader = csv.DictReader(f)
    for row in reader:
    	for col in row:
    		if row[col] == 'NULL':
    			row[col] = None
    	userID = populateUser(row)
    	populateAddress(row, userID)	
    	productID = populateProducts(row)
    	orderID = populateOrder(row, productID, userID)
    	populateOrderDetail(row, orderID, productID)
    	cnt += 1
    	print cnt
