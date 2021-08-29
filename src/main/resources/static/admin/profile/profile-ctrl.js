app.controller('profile-ctrl', function ($scope, $http, $rootScope) {
    // load username login
    $rootScope.user = [];
    $scope.items = [];
    $rootScope.getUserNow = function () {
        $http.get(`/rest/accounts/userNow`).then(resp => {
            $rootScope.user = resp.data;
            console.log($rootScope.user);
        })
    }
    $rootScope.getUserNow();
    // cap nhat user
    $scope.update = function () {
        var item = angular.copy($scope.user);

        $http.put(`/rest/accounts/${item.username}`, item).then(resp => {
            var index = $scope.items.findIndex(a => a.username == item.username);
            $scope.items[index] = item;
            console.log($scope.items[index]);
            alert("Cập nhật thông tin người dùng thành công !");
        }).catch(error => {
            alert("Không thể cập nhật thông tin !");
            console.log("Error:", error);
        })
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
            $scope.user.photo = resp.data.name; //upload xong lấy tên bỏ vào image của form

        }).catch(error => {
            alert("Không thể cập nhật hình ảnh ! ");
            console.log("Error", error);
        });
    }
})