app.controller('order-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.form = {};
    $scope.initialize = function () {
        // load order
        $http.get(`/rest/orders`).then(resp => {
            $scope.items = resp.data;
            $scope.sortType = resp.data.username; // set the default sort type
            $scope.sortReverse = false; // set the default sort order
            $scope.items.forEach(items => {
                items.createDate = new Date(items.createDate)
            })
        })

    }
    $scope.initialize();

    // phan trang
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