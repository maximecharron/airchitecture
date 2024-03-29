'use strict';


angular.module('ngCart', ['ngCart.directives', 'airchitecture.cart'])

    .config([function () {

    }])

    .provider('$ngCart', function () {
        this.$get = function () {
        };
    })

    .run(['$rootScope', 'ngCart', 'ngCartItem', 'store', function ($rootScope, ngCart, ngCartItem, store) {

        $rootScope.$on('ngCart:change', function () {
            ngCart.$save();
        });

        if (angular.isObject(store.get('cart'))) {
            ngCart.$restore(store.get('cart'));

        } else {
            ngCart.init();
        }

    }])

    .service('ngCart', ['$rootScope', 'ngCartItem', 'store', 'cartResource', '$http', '$location', function ($rootScope, ngCartItem, store, cartResource, $http, $location) {

        this.init = function () {
            this.$cart = {
                shipping: null,
                taxRate: null,
                tax: null,
                items: []
            };
        };

        this.addItem = function (id, name, price, quantity, quantityMax, data) {

            var inCart = this.getItemById(id);

            if (typeof inCart === 'object') {
                //Update quantity of an item if it's already in the cart
                inCart.setQuantity(quantity, false);
            } else {
                var newItem = new ngCartItem(id, name, price, quantity, quantityMax, data);
                this.$cart.items.push(newItem);
                if (data.airCargoFlight) {
                    if (id.indexOf("Economic") > -1) {
                        cartResource.reserveTicket({
                            "arrivalAirport": data.arrivalAirport,
                            "airlineCompany": data.airlineCompany,
                            "departureDate": data.departureDate,
                            "airCargoDepartureDate": data.airCargoFlight.departureDate,
                            "airCargoAirLineCompany": data.airCargoFlight.airlineCompany,
                            "luggageWeight": data.luggageWeight,
                            "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                        });
                    } else if (id.indexOf("Regular") > -1) {
                        cartResource.reserveTicket({
                            "arrivalAirport": data.arrivalAirport,
                            "airlineCompany": data.airlineCompany,
                            "departureDate": data.departureDate,
                            "airCargoDepartureDate": data.airCargoFlight.departureDate,
                            "airCargoAirLineCompany": data.airCargoFlight.airlineCompany,
                            "luggageWeight": data.luggageWeight,
                            "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                        });
                    } else if (id.indexOf("Business") > -1) {
                        cartResource.reserveTicket({
                            "arrivalAirport": data.arrivalAirport,
                            "airlineCompany": data.airlineCompany,
                            "departureDate": data.departureDate,
                            "airCargoDepartureDate": data.airCargoFlight.departureDate,
                            "airCargoAirLineCompany": data.airCargoFlight.airlineCompany,
                            "luggageWeight": data.luggageWeight,
                            "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                        });
                    }
                } else {
                    if (id.indexOf("Economic") > -1) {
                        cartResource.reserveTicket({
                            "arrivalAirport": data.arrivalAirport,
                            "airlineCompany": data.airlineCompany,
                            "departureDate": data.departureDate,
                            "luggageWeight": data.luggageWeight,
                            "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                        });
                    } else if (id.indexOf("Regular") > -1) {
                        cartResource.reserveTicket({
                            "arrivalAirport": data.arrivalAirport,
                            "airlineCompany": data.airlineCompany,
                            "departureDate": data.departureDate,
                            "luggageWeight": data.luggageWeight,
                            "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                        });
                    } else if (id.indexOf("Business") > -1) {
                        cartResource.reserveTicket({
                            "arrivalAirport": data.arrivalAirport,
                            "airlineCompany": data.airlineCompany,
                            "departureDate": data.departureDate,
                            "luggageWeight": data.luggageWeight,
                            "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                        });
                    }
                }
                $rootScope.$broadcast('ngCart:itemAdded', newItem);
            }

            $rootScope.$broadcast('ngCart:change', {});
        };

        this.getItemById = function (itemId) {
            var items = this.getCart().items;
            var build = false;

            angular.forEach(items, function (item) {
                if (item.getId() === itemId) {
                    build = item;
                }
            });
            return build;
        };

        this.setShipping = function (shipping) {
            this.$cart.shipping = shipping;
            return this.getShipping();
        };

        this.getShipping = function () {
            if (this.getCart().items.length == 0) return 0;
            return this.getCart().shipping;
        };

        this.setTaxRate = function (taxRate) {
            this.$cart.taxRate = +parseFloat(taxRate).toFixed(2);
            return this.getTaxRate();
        };

        this.getTaxRate = function () {
            return this.$cart.taxRate
        };

        this.getTax = function () {
            return +parseFloat(((this.getSubTotal() / 100) * this.getCart().taxRate )).toFixed(2);
        };

        this.setCart = function (cart) {
            this.$cart = cart;
            return this.getCart();
        };

        this.getCart = function () {
            return this.$cart;
        };

        this.getItems = function () {
            return this.getCart().items;
        };

        this.getTotalItems = function () {
            var count = 0;
            var items = this.getItems();
            angular.forEach(items, function (item) {
                count += item.getQuantity();
            });
            return count;
        };

        this.getTotalUniqueItems = function () {
            return this.getCart().items.length;
        };

        this.getSubTotal = function () {
            var total = 0;
            angular.forEach(this.getCart().items, function (item) {
                total += item.getTotal();
            });
            return +parseFloat(total).toFixed(2);
        };

        this.totalCost = function () {
            return +parseFloat(this.getSubTotal() + this.getShipping() + this.getTax()).toFixed(2);
        };

        this.removeItem = function (index) {
            this.$cart.items.splice(index, 1);
            $rootScope.$broadcast('ngCart:itemRemoved', {});
            $rootScope.$broadcast('ngCart:change', {});

        };

        this.removeOne = function (id) {
            var cart = this.getCart();
            angular.forEach(cart.items, function (item, index) {
                if (item.getId() === id && item.getQuantity() != 1) {
                    if (item.getData().airCargoFlight) {
                        if (id.indexOf("Economic") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Regular") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Business") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        }
                    } else {
                        if (id.indexOf("Economic") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Regular") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Business") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        }
                    }

                    item.setQuantity(-1, true);
                }
            });
            $rootScope.$broadcast('ngCart:change', {});
        }

        this.addOne = function (id) {
            var cart = this.getCart();
            angular.forEach(cart.items, function (item, index) {
                if (item.getId() === id) {
                    if (item.getQuantity() + 1 <= item.getQuantityMax()) {
                        if (item.getData().airCargoFlight) {
                            if (id.indexOf("Economic") > -1) {
                                cartResource.reserveTicket({
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                                });
                            } else if (id.indexOf("Regular") > -1) {
                                console.dir(item.getData());
                                cartResource.reserveTicket({
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                                });
                            } else if (id.indexOf("Business") > -1) {
                                cartResource.reserveTicket({
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                                });
                            }
                        } else {
                            if (id.indexOf("Economic") > -1) {
                                cartResource.reserveTicket({
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                                });
                            } else if (id.indexOf("Regular") > -1) {
                                console.dir(item.getData());
                                cartResource.reserveTicket({
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                                });
                            } else if (id.indexOf("Business") > -1) {
                                cartResource.reserveTicket({
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                                });
                            }
                        }
                        item.setQuantity(1, true);
                    }
                }
            });
            $rootScope.$broadcast('ngCart:change', {});
        }

        this.removeItemById = function (id) {
            var cart = this.getCart();
            angular.forEach(cart.items, function (item, index) {
                if (item.getId() === id) {
                    if (item.getData().airCargoFlight) {
                        if (id.indexOf("Economic") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Regular") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Business") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "airCargoDepartureDate": item.getData().airCargoFlight.departureDate,
                                    "airCargoAirLineCompany": item.getData().airCargoFlight.airlineCompany,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        }
                    } else {
                        if (id.indexOf("Economic") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 1, "regularSeats": 0, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Regular") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 1, "businessSeats": 0}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        } else if (id.indexOf("Business") > -1) {
                            $http({
                                method: 'DELETE',
                                url: 'http://localhost:8081/api/cartItems',
                                data: {
                                    "arrivalAirport": item.getData().arrivalAirport,
                                    "airlineCompany": item.getData().airlineCompany,
                                    "departureDate": item.getData().departureDate,
                                    "luggageWeight": item.getData().luggageWeight,
                                    "seatMapDto": {"economicSeats": 0, "regularSeats": 0, "businessSeats": 1}
                                },
                                headers: {'Content-Type': 'application/json;charset=utf-8'}
                            });
                        }
                    }

                    cart.items.splice(index, 1);
                }
            });
            this.setCart(cart);
            $rootScope.$broadcast('ngCart:itemRemoved', {});
            $rootScope.$broadcast('ngCart:change', {});
        };

        this.empty = function () {

            $rootScope.$broadcast('ngCart:change', {});
            this.$cart.items = [];
            localStorage.removeItem('cart');
        };

        this.isEmpty = function () {

            return (this.$cart.items.length > 0 ? false : true);

        };

        this.toObject = function () {

            if (this.getItems().length === 0) return false;

            var items = [];
            angular.forEach(this.getItems(), function (item) {
                items.push(item.toObject());
            });

            return {
                items: items
            }
        };

        this.toDTO = function (email) {

            if (this.getItems().length === 0) return false;

            var items = [];
            angular.forEach(this.getItems(), function (item) {
                items.push(item.toDTO());
            });

            return {
                emailAddress: email,
                cartItemDtos: items
            }
        };


        this.$restore = function (storedCart) {
            var _self = this;
            _self.init();
            _self.$cart.shipping = storedCart.shipping;
            _self.$cart.tax = storedCart.tax;

            angular.forEach(storedCart.items, function (item) {
                _self.$cart.items.push(new ngCartItem(item._id, item._name, item._price, item._quantity, item._quantityMax, item._data));
            });
            this.$save();
        };

        this.$save = function () {
            return store.set('cart', JSON.stringify(this.getCart()));
        }

    }])

    .factory('ngCartItem', ['$rootScope', '$log', 'cartResource', function ($rootScope, $log, cartResource) {

        var item = function (id, name, price, quantity, quantityMax, data) {
            this.setId(id);
            this.setName(name);
            this.setPrice(price);
            this.setData(data);
            this.setQuantity(quantity);
            this.setQuantityMax(quantityMax);
        };


        item.prototype.setId = function (id) {
            if (id) this._id = id;
            else {
                $log.error('An ID must be provided');
            }
        };

        item.prototype.getId = function () {
            return this._id;
        };


        item.prototype.setName = function (name) {
            if (name) this._name = name;
            else {
                $log.error('A name must be provided');
            }
        };
        item.prototype.getName = function () {
            return this._name;
        };

        item.prototype.setPrice = function (price) {
            var priceFloat = parseFloat(price);
            if (priceFloat) {
                if (priceFloat <= 0) {
                    $log.error('A price must be over 0');
                } else {
                    this._price = (priceFloat);
                }
            } else {
                $log.error('A price must be provided');
            }
        };
        item.prototype.getPrice = function () {
            return this._price;
        };


        item.prototype.setQuantity = function (quantity, relative) {


            var quantityInt = parseInt(quantity);
            if (quantityInt % 1 === 0) {
                if (relative === true) {
                    this._quantity += quantityInt;
                } else {
                    this._quantity = quantityInt;
                }
                if (this._quantity < 1) this._quantity = 1;

            } else {
                this._quantity = 1;
                $log.info('Quantity must be an integer and was defaulted to 1');
            }
            $rootScope.$broadcast('ngCart:change', {});

        };

        item.prototype.getQuantity = function () {
            return this._quantity;
        };

        item.prototype.setData = function (data) {
            if (data) this._data = data;
        };

        item.prototype.getData = function () {
            if (this._data) return this._data;
            else $log.info('This item has no data');
        };


        item.prototype.getTotal = function () {
            return +parseFloat(this.getQuantity() * this.getPrice()).toFixed(2);
        };

        item.prototype.getQuantityMax = function () {
            return this._quantityMax;
        };

        item.prototype.setQuantityMax = function (quantityMax) {
            this._quantityMax = quantityMax;
        }

        item.prototype.toObject = function () {
            return {
                id: this.getId(),
                name: this.getName(),
                price: this.getPrice(),
                quantity: this.getQuantity(),
                quantityMax: this.getQuantityMax(),
                data: this.getData(),
                total: this.getTotal()
            }
        };

        item.prototype.toDTO = function () {
            var itemDto = {};
            if (this.getData().airCargoFlight) {
                if (this.getId().indexOf("Economic") > -1) {
                    itemDto = {
                        arrivalAirport: this.getData().arrivalAirport,
                        airlineCompany: this.getData().airlineCompany,
                        departureDate: this.getData().departureDate,
                        airCargoDepartureDate: this.getData().airCargoFlight.departureDate,
                        airCargoAirLineCompany: this.getData().airCargoFlight.airlineCompany,
                        luggageWeight: this.getData().luggageWeight,
                        seatMapDto: {economicSeats: this.getQuantity(), regularSeats: 0, businessSeats: 0}
                    };
                } else if (this.getId().indexOf("Business") > -1) {
                    itemDto = {
                        arrivalAirport: this.getData().arrivalAirport,
                        airlineCompany: this.getData().airlineCompany,
                        departureDate: this.getData().departureDate,
                        airCargoDepartureDate: this.getData().airCargoFlight.departureDate,
                        airCargoAirLineCompany: this.getData().airCargoFlight.airlineCompany,
                        luggageWeight: this.getData().luggageWeight,
                        seatMapDto: {economicSeats: 0, regularSeats: this.getQuantity(), businessSeats: 0}
                    };
                } else {
                    itemDto = {
                        arrivalAirport: this.getData().arrivalAirport,
                        airlineCompany: this.getData().airlineCompany,
                        departureDate: this.getData().departureDate,
                        airCargoDepartureDate: this.getData().airCargoFlight.departureDate,
                        airCargoAirLineCompany: this.getData().airCargoFlight.airlineCompany,
                        luggageWeight: this.getData().luggageWeight,
                        seatMapDto: {economicSeats: 0, regularSeats: 0, businessSeats: this.getQuantity()}
                    };
                }
            } else {
                if (this.getId().indexOf("Economic") > -1) {
                    itemDto = {
                        arrivalAirport: this.getData().arrivalAirport,
                        airlineCompany: this.getData().airlineCompany,
                        departureDate: this.getData().departureDate,
                        luggageWeight: this.getData().luggageWeight,
                        seatMapDto: {economicSeats: this.getQuantity(), regularSeats: 0, businessSeats: 0}
                    };
                } else if (this.getId().indexOf("Business") > -1) {
                    itemDto = {
                        arrivalAirport: this.getData().arrivalAirport,
                        airlineCompany: this.getData().airlineCompany,
                        departureDate: this.getData().departureDate,
                        luggageWeight: this.getData().luggageWeight,
                        seatMapDto: {economicSeats: 0, regularSeats: this.getQuantity(), businessSeats: 0}
                    };
                } else {
                    itemDto = {
                        arrivalAirport: this.getData().arrivalAirport,
                        airlineCompany: this.getData().airlineCompany,
                        departureDate: this.getData().departureDate,
                        luggageWeight: this.getData().luggageWeight,
                        seatMapDto: {economicSeats: 0, regularSeats: 0, businessSeats: this.getQuantity()}
                    };
                }
            }

            return itemDto;
        };

        return item;

    }])

    .service('store', ['$window', function ($window) {

        return {

            get: function (key) {
                if ($window.localStorage [key]) {
                    var cart = angular.fromJson($window.localStorage [key]);
                    return JSON.parse(cart);
                }
                return false;

            },


            set: function (key, val) {

                if (val === undefined) {
                    $window.localStorage.removeItem(key);
                } else {
                    $window.localStorage [key] = angular.toJson(val);
                }
                return $window.localStorage [key];
            }
        }
    }])

    .controller('CartController', ['$scope', 'ngCart', function ($scope, ngCart) {
        $scope.ngCart = ngCart;

    }])

    .value('version', '1.0.0');
;'use strict';


angular.module('ngCart.directives', ['ngCart.fulfilment'])

    .controller('CartController', ['$scope', 'ngCart', function ($scope, ngCart) {
        $scope.ngCart = ngCart;
    }])

    .directive('ngcartAddtocart', ['ngCart', function (ngCart) {
        return {
            restrict: 'E',
            controller: 'CartController',
            scope: {
                id: '@',
                name: '@',
                quantity: '@',
                quantityMax: '@',
                price: '@',
                data: '='
            },
            transclude: true,
            templateUrl: function (element, attrs) {
                if (typeof attrs.templateUrl == 'undefined') {
                    return 'template/ngCart/addtocart.html';
                } else {
                    return attrs.templateUrl;
                }
            },
            link: function (scope, element, attrs) {
                scope.attrs = attrs;
                scope.inCart = function () {
                    return ngCart.getItemById(attrs.id);
                };

                if (scope.inCart()) {
                    scope.q = ngCart.getItemById(attrs.id).getQuantity();
                } else {
                    scope.q = parseInt(scope.quantity);
                }

                scope.qtyOpt = [];
                for (var i = 1; i <= scope.quantityMax; i++) {
                    scope.qtyOpt.push(i);
                }

            }

        };
    }])

    .directive('ngcartCart', [function () {
        return {
            restrict: 'E',
            controller: 'CartController',
            scope: {},
            templateUrl: function (element, attrs) {
                if (typeof attrs.templateUrl == 'undefined') {
                    return 'template/ngCart/cart.html';
                } else {
                    return attrs.templateUrl;
                }
            },
            link: function (scope, element, attrs) {

            }
        };
    }])

    .directive('ngcartSummary', [function () {
        return {
            restrict: 'E',
            controller: 'CartController',
            scope: {},
            transclude: true,
            templateUrl: function (element, attrs) {
                if (typeof attrs.templateUrl == 'undefined') {
                    return 'template/ngCart/summary.html';
                } else {
                    return attrs.templateUrl;
                }
            }
        };
    }])

    .directive('ngcartCheckout', [function () {
        return {
            restrict: 'E',
            controller: ('CartController', ['$rootScope', '$scope', 'ngCart', 'fulfilmentProvider', '$location', function ($rootScope, $scope, ngCart, fulfilmentProvider, $location) {
                $scope.ngCart = ngCart;

                $scope.checkout = function () {
                    fulfilmentProvider.setService($scope.service);
                    fulfilmentProvider.setSettings($scope.settings);
                    fulfilmentProvider.checkout()
                        .success(function (data, status, headers, config) {
                            $rootScope.$broadcast('ngCart:checkout_succeeded', data);
                            $location.path('/checkoutsummary');
                        })
                        .error(function (data, status, headers, config) {
                            $rootScope.$broadcast('ngCart:checkout_failed', {
                                statusCode: status,
                                error: data
                            });
                        });
                }
            }]),
            scope: {
                service: '@',
                settings: '='
            },
            transclude: true,
            templateUrl: function (element, attrs) {
                if (typeof attrs.templateUrl == 'undefined') {
                    return 'template/ngCart/checkout.html';
                } else {
                    return attrs.templateUrl;
                }
            }
        };
    }]);
;
angular.module('ngCart.fulfilment', [])
    .service('fulfilmentProvider', ['$injector', function ($injector) {

        this._obj = {
            service: undefined,
            settings: undefined
        };

        this.setService = function (service) {
            this._obj.service = service;
        };

        this.setSettings = function (settings) {
            this._obj.settings = settings;
        };

        this.checkout = function () {
            var provider = $injector.get('ngCart.fulfilment.' + this._obj.service);
            return provider.checkout(this._obj.settings);

        }

    }])

    .service('ngCart.fulfilment.http', ['$http', 'ngCart', '$rootScope', '$location', function ($http, ngCart, $rootScope, $location) {

        this.checkout = function () {
            return $http.post('http://localhost:8081/api/transactions',
                ngCart.toDTO($rootScope.checkoutEmail))
        }
    }]);