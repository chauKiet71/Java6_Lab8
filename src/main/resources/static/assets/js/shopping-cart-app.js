const app = angular.module("shopping-cart-app", [])
app.controller("shopping-cart-ctrl", function ($scope, $http) {
    $scope.cart = {
        items: [],
        add(id) {
            var item = this.items.find(item => item.id == id)
            if (item) {
                item.qty++;
                this.saveToLocalStorage()
            } else {
                $http.get(`/rest/product/${id}`).then(resp => {
                    resp.data.qty = 1
                    this.items.push(resp.data)
                    this.saveToLocalStorage()
                })
            }
        },
        //xoa san pham khoi gio hang
        remove(id) {
            var index = this.items.findIndex(item => item.id == id)
            this.items.splice(index, 1)
            this.saveToLocalStorage()
        },
        //xoa all gio hang
        clear() {
            this.items = []
            this.saveToLocalStorage()
        },
        // tinh tong so tien cac mat hang trong gio
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        // tong thanh tien cac mat hang trong gio
        get amount() {
            return this.items
                .map(item => item.qty * item.price)
                .reduce((total, qty) => total += qty, 0)
        },
        //luu gio hang vao local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items))
            localStorage.setItem("cart", json)
        },
        loadFromLocalStorege() {
            var json = localStorage.getItem("cart")
            this.items = json ? JSON.parse(json) : []
        }
    }
    $scope.cart.loadFromLocalStorege();
})