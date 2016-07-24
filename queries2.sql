Query:
select orderDate, SUM(sellPrice * quantity) as Sales
FROM Orders INNER JOIN OrderDetails
WHERE Orders.orderID = OrderDetails.orderID
and Orders.status != 'Cancelled'
GROUP BY orderDate
Without index : 4.23s
With index: 2.72s


Query:
select orderDate, SUM((sellPrice - buyPrice) * quantity) as Profit
FROM Orders INNER JOIN OrderDetails
WHERE Orders.orderID = OrderDetails.orderID
and Orders.status != 'Cancelled'
GROUP BY orderDate
Without index: 3.04s
With index: 2.78s

Query:
select Users.userID, customerName, AVG(quantity) as AverageUnitsOrdered
FROM Orders 
INNER JOIN OrderDetails
	on Orders.orderID = OrderDetails.orderID
INNER JOIN Users 
	on Users.userID = Orders.userID 
GROUP BY Users.userID

Without index: 2.82s
With index: 2.69s
