var app = angular.module('myApp', ['ngRoute', 'ngCookies']);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', {
		controller: 'myController',
		templateUrl: 'view1.html',
	})
	.when('/products/:pid', {
		controller: 'productController',
		templateUrl: 'view2.html',
	})
	.when('/cart', {
		controller: 'cartController',
		templateUrl: 'cart.html',	
	})
	.when('/checkout', {
		controller: 'orderController',
		templateUrl: 'checkout.html',	
	})
	.when('/connect', {
		controller: 'myController',
		templateUrl: 'view3.html',	
	})
	.otherwise({ 
		redirectTo: '/' 
	});
});
app.controller("orderController", function($scope, $http, $routeParams, $cookies, $route, $rootScope) {
	console.log("orderController");
	console.log($rootScope.username);
	$scope.products = [];
	$rootScope.cartCount = 0;
	$.each(document.cookie.split(/; */), function() {
		var splitCookie = this.split('=');
		$http.get("http://127.0.0.1:8000/api/products/" + splitCookie[0])
		.then(function (response) {
			response.data.data['quantity'] = parseInt(splitCookie[1]);
			$scope.products.push(response.data.data);
		});
		// $rootScope.cartCount = $rootScope.cartCount + 1;
		// $cookies.remove(splitCookie[0]);
	});
	console.log($rootScope.cartCount);
});
app.controller("cartController", function($scope, $http, $routeParams, $cookies, $route, $rootScope) {
	console.log("cartController");
	$scope.products = [];
	$rootScope.cartCount = 0;
	$.each(document.cookie.split(/; */), function() {
		var splitCookie = this.split('=');
		$http.get("http://127.0.0.1:8000/api/products/" + splitCookie[0])
		.then(function (response) {
			response.data.data['quantity'] = parseInt(splitCookie[1]);
			$scope.products.push(response.data.data);
		});
		$rootScope.cartCount = $rootScope.cartCount + 1;
	});

	$scope.deleteFromCart = function(qty) {
		$cookies.remove(qty);
		$route.reload();
	};
	$scope.checkout = function() {
		$rootScope.address = $scope.formInfo.address;
		$rootScope.username = $scope.formInfo.username;
		console.log($scope.formInfo);
		var data = {
			'username' : $scope.formInfo.username,
			'address': $scope.formInfo.address,
			'status' : "CREATED"
		};
		// Create an order;
		$http.post('http://127.0.0.1:8000/api/orders/', data)
		.success(function(data) {
			console.log(data.data);
			var url = 'http://127.0.0.1:8000/api/orders/' + data.data.id + '/orderlineitem/'; 
			$.each(document.cookie.split(/; */), function() {
				var splitCookie = this.split('=');
				var product_id = parseInt(splitCookie[0]);
				var quantity = parseInt(splitCookie[1]);
				var price = undefined;
				//fetch current price
				//do validation here later when required
				$http.get("http://127.0.0.1:8000/api/products/" + product_id)
				.then(function (response) {
					price = response.data.data.price;
					var dataInner = {
						'product_id' : product_id,
						'price' : price,
						'quantity' : quantity
					};
					$http.post(url, dataInner);
				});
			});	
		})
	}
});
app.controller("productController", function($scope, $http, $routeParams, $cookies, $rootScope) {
	$scope.productID = $routeParams.pid;	
	console.log("productController");

	$rootScope.cartCount = 0;
	$.each(document.cookie.split(/; */), function() {
		var splitCookie = this.split('=');
		if (splitCookie[1] != undefined) {
			$rootScope.cartCount = $rootScope.cartCount + 1;			
		}
	});

	$http.get("http://127.0.0.1:8000/api/products/" + $scope.productID)
	.then(function (response) {
		$scope.products = response.data.data;
		$scope.products['url'] = '/images/' + (parseInt($scope.productID) % 5) + '.jpg';
		console.log($scope.products)
	});

	$scope.addToCart = function(qty) {
		var pid = $scope.productID;
		if($cookies.getObject(pid) == undefined) {
			$cookies.putObject(pid, parseInt(qty));
			$rootScope.cartCount = $rootScope.cartCount + 1; 
		} 
		else {
			$cookies.putObject(pid, parseInt(qty) + parseInt($cookies.getObject(pid)));	
		}
		// $.each(document.cookie.split(/; */), function()  {
		// 	var splitCookie = this.split('=');
		// 	console.log(splitCookie[0] + ':' + splitCookie[1]);
		// });
	};
});

app.controller("myController", function($scope, $http, $rootScope) {
	console.log("myController");

	$rootScope.cartCount = 0;
	$.each(document.cookie.split(/; */), function() {
		var splitCookie = this.split('=');
		if (splitCookie[1] != undefined) {
			$rootScope.cartCount = $rootScope.cartCount + 1;
		}
		console.log(splitCookie[0] + ':' + splitCookie[1]);
	});

	$http.get("http://127.0.0.1:8000/api/products/")
	.then(function (response) {
		$scope.products = response.data.data;
		for (var i = 0; i < $scope.products.length; ++i) {
			var id = parseInt($scope.products[i].id); 
			$scope.products[i]['url'] = '/images/' + (id % 5) + '.jpg';
		}
		console.log($scope.products);
	});

	$http.get("http://127.0.0.1:8000/api/products/summary?group_by=category")
	.then(function (response) {
		$scope.categories = response.data.data;
		console.log(response.data);
	});

	$scope.setCategoryID = function(id) {
		$scope.activeCategoryID = $scope.activeCategoryID == id ? undefined : id;
	};

	$scope.feedback = function() {
        alert("Thank your for your feedback.");
    };
});