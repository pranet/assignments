var app = angular.module('myApp', ['ngRoute']);

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
	.otherwise({ 
		redirectTo: '/' 
	});
});

app.controller("productController", function($scope, $http, $routeParams) {
	$scope.productID = $routeParams.pid;	
	$http.get("http://127.0.0.1:8000/api/products/" + $scope.productID)
	.then(function (response) {
		$scope.products = response.data;
	});
});

app.controller("myController", function($scope, $http) {
	$http.get("http://127.0.0.1:8000/api/products/")
	.then(function (response) {
		$scope.products = response.data;
	});

	$http.get("http://127.0.0.1:8000/api/products/summary?group_by=category")
	.then(function (response) {
		$scope.categories = response.data.data;
		console.log(response.data);
	});

	$scope.setCategoryID = function(id) {
		$scope.activeCategoryID = $scope.activeCategoryID == id ? undefined : id;
	};
});