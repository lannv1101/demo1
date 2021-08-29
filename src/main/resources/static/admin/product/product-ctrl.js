app.controller('product-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.form = {};
    $scope.cates = [];
    $scope.max = new Date();
    $scope.initialize = function () {
        // load products
        $http.get(`/rest/products`).then(resp => {
            $scope.items = resp.data;
            $scope.sortType = resp.data.name; // set the default sort type
            $scope.sortReverse = false; // set the default sort order
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate) //chuyen doi ngay thang
            })
        });
        // load categories
        $http.get(`/rest/categories`).then(resp => {
            $scope.cates = resp.data;
            $scope.sortTypeCate = resp.data.name;
        });
    };
    // khởi đầu
    $scope.initialize();

    // hiển thị lên form
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        // $(".nav-tabs a:eq(0)").tab('show')
        // console.log('EDIT Item', item);
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

    $scope.flag;
    // thêm mới sản phẩm
    $scope.create = function () {
        var item = angular.copy($scope.form); //lấy dữ liệu từ form
        $http.post(`/rest/products`, item).then(resp => {
            resp.data.createDate = new Date(resp.data.createDate);
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Thêm mới sản phẩm thành công !");
        }).catch(error => {
            $scope.flag = true;
            alert("Bạn không thể thêm mới sản phẩm");
            console.log("Error:", error);
        });
    }
    // cập nhật sản phẩm
    $scope.update = function () {
        var item = angular.copy($scope.form); //lấy dữ liệu từ form
        $http.put(`/rest/products/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items[index] = item;
            alert("Cập nhật thông tin sản phẩm thành công!");
        }).catch(error => {
            alert("Bạn không thể cập nhật thông tin sản phẩm");
            console.log("Error:", error);
        });
    }
    // xóa sản phẩm
    $scope.delete = function (item) {
        $http.delete(`/rest/products/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            console.log(index);
            $scope.items.splice(index, 1); //xóa splice trong js
            $scope.reset();
            alert("Xóa sản phẩm thành công !");
        }).catch(error => {
            alert("Xin lỗi, bạn không thể xóa sản phầm này, vì đang có dữ liệu liên kết");
            console.log("Error:", error);
        });
    }
    // xóa form
    $scope.reset = function (item) {
        $scope.form = {
            createDate: new Date(),
            image: "",
            available: true,
        };
    }
    // upload hình
    $scope.imageChanged = function (files) {
        var data = new FormData();
        data.append('file', files[0]); //lấy file bỏ vào data
        $http.post('/rest/upload/images', data, { //post len server
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).then(resp => {
            $scope.form.image = resp.data.name; //upload xong lấy tên bỏ vào image của form

        }).catch(error => {
            alert("Không thể cập nhật hình ảnh ! ");
            console.log("Error", error);
        });
    }

    $scope.pager = {
        page: 0,
        size: 10,
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

})