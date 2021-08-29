var app = angular.module('admin-app', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider.when('/product', {
            templateUrl: '/admin/product/index.html',
            controller: 'product-ctrl'
        }).when(
            '/authorize', {
                templateUrl: '/admin/authority/index.html',
                controller: 'authority-ctrl'
            })
        .when(
            '/account', {
                templateUrl: '/admin/user/index.html',
                controller: 'account-ctrl'
            })
        .when(
            '/order', {
                templateUrl: '/admin/order/order.html',
                controller: 'order-ctrl'
            })
        .when(
            '/order/:orderId', {
                templateUrl: '/admin/order/details.html',
                controller: 'details-ctrl',
                $Params: 'orderId',
            })
        .when(
            '/category', {
                templateUrl: '/admin/category/index.html',
                controller: 'category-ctrl'
            })
        .when(
            '/statistics', {
                templateUrl: '/admin/statistics/index.html',
                controller: 'statistics-ctrl'
            })
        .when(
            '/profile', {
                templateUrl: '/admin/profile/user-profile-lite.html',
                controller: 'profile-ctrl'
            })
        .when(
            '/unauthorized', {
                templateUrl: '/admin/authority/unauthorized.html',
                controller: 'authority-ctrl'
            })
        .otherwise({
            template: '<h1>FPT POLYTECHNIC Administration</h1>'
        })

})