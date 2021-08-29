app.controller('details-ctrl', function ($scope, $http, $routeParams) {
    $scope.items = [];
    $scope.form = {};
    $scope.initialize = function () {
        // load order
        $scope.orderId = $routeParams.orderId;
        $http.get(`/rest/orders/` + $routeParams.orderId).then(resp => {
            $scope.items = resp.data;
            console.log(resp.data);
            $scope.getTotal = function () {
                var total = 0;
                for (var i = 0; i < $scope.items.length; i++) {
                    var product = $scope.items[i];
                    total += ((product.price - (product.price * product.product.discount * 0.01)) * product.quantity);
                }
                return total;

            }
            $scope.getTotal();
            console.log($scope.getTotal());
            $scope.items.forEach(items => {
                items.createDate = new Date(items.createDate)
            })
        })

    }
    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 30,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        },
    }
})