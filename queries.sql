1
select orderDate, SUM(sellPrice * quantity) as Sales
FROM Orders INNER JOIN OrderDetails
WHERE Orders.orderID = OrderDetails.orderID
and Orders.status != 'Cancelled'
GROUP BY orderDate

2
select orderDate, SUM((sellPrice - buyPrice) * quantity) as Profit
FROM Orders INNER JOIN OrderDetails
WHERE Orders.orderID = OrderDetails.orderID
and Orders.status != 'Cancelled'
GROUP BY orderDate

3
select Users.userID, customerName, AVG(quantity) as AverageUnitsOrdered
FROM Orders 
INNER JOIN OrderDetails
	on Orders.orderID = OrderDetails.orderID
INNER JOIN Users 
	on Users.userID = Orders.userID 
GROUP BY Users.userID

4
UPDATE Product 
SET isAvailable = '0'
WHERE productID = 2

5
-- (to be implemented using Spring in assignment 4)