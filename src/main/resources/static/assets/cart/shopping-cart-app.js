const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function ($scope, $http) {
    // alert("angularjs initialzed")
    // Quản lý giỏ hàng
    $scope.cart = {
        items: [],
        //thêm sản phẩm vào giỏ hàng
        add(id) {
            var item = this.items.find(item => item.id == id);
            if (item) {
                item.qty++;
                alert("Thêm thành công sản phẩm vào giỏ hàng" + id);
                this.saveToLocalStorage();

            } else {
                $http.get(`/rest/products/${id}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    alert("Thêm thành công sản phẩm vào giỏ hàng" + id);
                    this.saveToLocalStorage();

                })
            }

        },

        //Xóa sản phẩm ra khỏi giỏ hàng 
        remove(id) {
            var index = this.items.findIndex(item => item.id == id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();

        },

        // Xóa sạch các mặt hàng trong giỏ
        clear() {
            this.items = [];
            this.saveToLocalStorage();
        },

        // Tính thành tiền của 1 sản phẩm
        amt_of(item) {},

        // Tính tổng số lượng các mặt hàng trong giỏ
        get count() {
            return this.items.map(item => item.qty).reduce((total, qty) => total += qty, 0);

        },

        // Tổng thành tiền các mặt hàng trong giỏ
        get amount() {
            return this.items.map(item => item.qty * item.price).reduce((total, qty) => total += qty, 0);

        },

        // Lưu giỏ hàng vào trong local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        //Đọc giỏ hàng từ local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        }
    }
    $scope.cart.loadFromLocalStorage();
    $scope.products = [];
    $http.get(`/rest/products`).then(resp => {
        $scope.products = resp.data;
    });
    $scope.order = {
        createDate: new Date(),
        address: "",
        account: {
            username: $("#username").text(),

        },
        get orderDetails() {
            return $scope.cart.items.map(item => {
                return {
                    product: {
                        id: item.id
                    },
                    price: item.price,
                    quantity: item.qty
                }
            });
        },

        purchase() {
            var order = angular.copy(this); //lấy order hiện tại
            //gửi thông tin lên server
            $http.post("/rest/orders", order).then(resp => { //post lên url
                alert("Order successfully !");
                // $http.put(`/rest/products/${quantityNew}/${id}`).then(resp => {
                //     alert("Update successfully !");
                // })

                $scope.cart.clear();

                setTimeout(function () {
                    location.href = "/order/detail/" + resp.data.id;
                }, 1000)

            }).catch(error => {
                alert("Order failed");
                console.log(error);
            })
        }

    }


})