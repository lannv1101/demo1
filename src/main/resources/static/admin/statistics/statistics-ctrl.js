app.controller('statistics-ctrl', function ($scope, $http, $rootScope) {
    $scope.items = [];
    $scope.cates = [];
    $scope.months = [];
    $scope.years = [];
    $scope.values = [];
    $rootScope.user = [];
    $scope.initialize = function () {
        $http.get(`/rest/orders/month`).then(resp => {
            $scope.months = resp.data;
            console.log(resp.data);
        })
        $http.get(`/rest/orders/year`).then(resp => {
            $scope.years = resp.data;
            console.log(resp.data);
        })
        $http.get(`/rest/accounts/countUser`).then(resp => {
            $scope.countUser = resp.data;
            console.log(resp.data);
        })
        $http.get(`/rest/orders/sum`).then(resp => {
            $scope.values = resp.data;
            console.log(resp.data);
        })
    }
    $scope.initialize();
    $scope.getThongke = function (year, month) {
        console.log('year month', year, month);
        $http.get(`/rest/orders/thongkeCate/${year}/${month}`).then(resp => {
            $scope.cates = resp.data;
            console.log(resp.data);
        });
        $http.get(`/rest/orders/thongke/${year}/${month}`).then(resp => {
            console.log(resp.data);
            $scope.items = resp.data;
            $scope.getTotal = function () {
                var total = 0;
                for (var i = 0; i < $scope.items.length; i++) {
                    var product = $scope.items[i];
                    total += (product.price * product.quantity);
                }
                return total;

            }
            // tổng hóa đơn
            $scope.getCount = function () {
                var flags = [],
                    output = [],
                    l = $scope.items.length,
                    i;
                for (i = 0; i < l; i++) {
                    if (flags[$scope.items[i].order.id]) continue;
                    flags[$scope.items[i].order.id] = true;
                    output.push($scope.items[i].order.id);
                }
                return output.length;
            }
            // thống kê sp bán theo loại
            $scope.getCategory = function () {
                var flags = {},
                    output = [],
                    l = $scope.items,
                    i;
                for (i = 0; i < l.length; i++) {
                    if (flags[l[i].product.category.id]) continue;
                    flags[l[i].product.category.id] = true;
                    output.push(l[i]);
                }
                return output;
            }
            console.log($scope.getTotal());
            console.log($scope.items.length);
            console.log($scope.getCount());
            console.log($scope.getCategory());
        })

    }

    // load username login
    $scope.getUserNow = function () {
        $http.get(`/rest/accounts/userNow`).then(resp => {
            $rootScope.user = resp.data;
            console.log($rootScope.user);
        })
    }
    $scope.getUserNow();
})