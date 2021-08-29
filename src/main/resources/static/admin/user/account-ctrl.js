app.controller("account-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.form = [];
    $scope.account = [];
    $scope.roles = [];
    $scope.flag;
    $scope.accounts = [];
    // $scope.role = [];
    $scope.initialize = function () {
        // load account
        $http.get(`/rest/accounts/list`).then(resp => {
            $scope.items = resp.data;
            $scope.sortType = resp.data.username; // set the default sort type
            $scope.sortReverse = false; // set the default sort order
        })
        // load roles
        $http.get(`/rest/roles`).then(resp => {
            $scope.roles = resp.data;
        })
    }
    $scope.initialize();
    // hiển thị lên form
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        openTab('EDIT');
    }

    function openTab(typeTab) {
        if (typeTab === 'EDIT') {
            $(".nav-link#edit-tab").addClass("active");
            $(".nav-link#list-tab").removeClass("active");
            $(".tab-pane#edit").addClass("show active");
            $(".tab-pane#list").removeClass("show active");
        }
        if (typeTab === 'LIST') {
            $(".nav-link#list-tab").addClass("active");
            $(".nav-link#edit-tab").removeClass("active");
            $(".tab-pane#list").addClass("show active");
            $(".tab-pane#edit").removeClass("show active");
        }
    }
    // thêm mới user
    // $scope.create = function () {
    //     var item = angular.copy($scope.form);
    //     $http.post(`/rest/accounts`, item).then(resp => {
    //         $scope.items.push(resp.data);
    //         $scope.reset();
    //         alert("Insert user successfully !");
    //     }).catch(error => {
    //         alert("You can't insert user !");
    //         console.log("Error :", error);
    //     })
    // }
    // cap nhat user
    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/rest/accounts/${item.username}`, item).then(resp => {
            var index = $scope.items.findIndex(a => a.username == item.username);
            $scope.items[index] = item;
            alert("Update user successfully !");
        }).catch(error => {
            alert("You can't update user !");
            console.log("Error:", error);
        })
    }
    // xóa người dùng
    $scope.delete = function (item) {
        $http.delete(`/rest/accounts/${item.username}`).then(resp => {
            var index = $scope.items.findIndex(a => a.username == item.username);
            console.log(index);
            $scope.items.splice(index, 1); //xoa
            $scope.reset();
            alert("Delete user successfully!");
        }).catch(error => {
            alert("You can't delete user!");
            console.log("Error", error);
        })
    }
    // xóa form
    $scope.reset = function (item) {
        $scope.form = {
            createDate: new Date(),
            photo: "",
        };
    }
    // load hình
    $scope.imageChanged = function (files) {
        var data = new FormData();
        data.append('file', files[0]); //lấy file bỏ vào data
        $http.post('/rest/upload/users', data, { //post len server
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).then(resp => {
            $scope.form.photo = resp.data.name; //upload xong lấy tên bỏ vào image của form

        }).catch(error => {
            alert("Error upload image ! ");
            console.log("Error", error);
        });
    }
    $scope.pager = {
        page: 0,
        size: 20,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size); //trích sp de xem
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
    // getByRole
    $scope.getByRole = function (role) {
        $http.get(`/rest/authorities/${role}`).then(resp => {
            $scope.flag = 1;
            $scope.items = resp.data;
            console.log($scope.items);
        });
    }

    // cap nhat user
    $scope.update1 = function () {
        var item = angular.copy($scope.form.account);
        $http.put(`/rest/accounts/${item.username}`, item).then(resp => {
            var index = $scope.items.findIndex(a => a.username == item.username);
            $scope.items[index] = item;
            alert("Update user successfully !");
        }).catch(error => {
            alert("You can't update user !");
            console.log("Error:", error);
        })
    }
    // xóa người dùng
    $scope.delete1 = function (item) {
        $http.delete(`/rest/accounts/${item.username}`).then(resp => {
            var index = $scope.items.findIndex(a => a.username == item.username);
            console.log(index);
            $scope.items.splice(index, 1); //xoa
            $scope.reset();
            alert("Delete user successfully!");
        }).catch(error => {
            alert("You can't delete user!");
            console.log("Error", error);
        })
    }
    // xóa form
    $scope.reset1 = function (item) {
        $scope.form.account = {
            createDate: new Date(),
            photo: "",
        };
    }
})