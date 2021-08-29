app.controller('category-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.form = {};
    // load categories
    $scope.initialize = function () {
        $http.get(`/rest/categories`).then(resp => {
            $scope.items = resp.data;
            $scope.sortTypeCate = resp.data.name;
            $scope.sortReverse = false; // set the default sort order
        });
    }
    $scope.initialize();
    // load form
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
    }
    $scope.pager = {
        page: 0,
        size: 10,
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
    // add cate
    $scope.create = function () {
        var item = angular.copy($scope.form);
        $http.post(`/rest/categories`, item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Insert category successfully !");
        }).catch(error => {
            alert("You can't insert category !");
            console.log("Error", error);
        });
    }
    // update cate
    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/rest/categories/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(c => c.id == item.id);
            $scope.items[index] = item;
            alert("Update category successfully !");
        }).catch(error => {
            alert("Sorry, you can't update category !");
            console.log("Error", error);
        })
    }
    // delete cate
    $scope.delete = function (item) {
        $http.delete(`/rest/categories/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(c => c.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Delete category successfully !");
        }).catch(error => {
            alert("Sorry, you can't delete category, because there's a foreign key !")
        })
    }
    // xóa form
    $scope.reset = function (item) {
        $scope.form = {
            id: "",
            name: "",
            available: true,
        }
    };
})