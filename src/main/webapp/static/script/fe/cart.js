var Cart = (function () {
        //购物车代码

        return function Cart() {

            var cartItemList = [];

            var totalPrice = 0;

            var isGlobalCart = true;

            var updateCartSection = function () {
                updateCartDetail();
                if (cartItemList.length === 0) {
                    $('.empty-cart').css('display', 'block');
                    $('.cart-detail').css('display', 'none');
                } else {

                    $('.empty-cart').css('display', 'none');
                    $('.cart-detail').css('display', 'block');
                }
            };

            var updateCartDetail = function () {
                var totalQuantity = 0;
                var $cartItemListContainer = $('#cart-item-list');

                $cartItemListContainer.empty();

                //update cart item list
                for (var index = 0; index < cartItemList.length; index += 1) {
                    var productQuantity = cartItemList[index].quantity;
                    totalQuantity += productQuantity;
                    var product = cartItemList[index].product;
                    var cartItemTemplate =
                        '<li class="cart-item">' +
                        '<div class="cart-item-left">' +
                        '<img src="' + product.thumbnail + '"/>' +
                        '<a class="cart-item-name" href="#">' + product.productName + '</a>' +
                        '</div>' +
                        '<div class="cart-item-right">' +
                        '<div class="bold">￥' + product.price + '    X' + productQuantity + '</div>' +
                        '<div><a class="remove-cart-item">删除</a></div>' +
                        '</div>' +
                        '</li>';

                    var $cartItemElement = $(cartItemTemplate);
                    (function () {
                        var productId = product.id;
                        $cartItemElement.find('.remove-cart-item').on('click', function () {
                            self.removeCarttItem(productId);
                        });
                    })();
                    $cartItemListContainer.append($cartItemElement);
                }
                // return totalQuantity;
                var summaryInfo = '共 <span class="bold">' + totalQuantity + '</span> 件商品  共计 <span class="bold">￥' + totalPrice + '</span>';

                $('#cart-summary-txt').html(summaryInfo);

                $('#totalQuantity').text('(' + totalQuantity + ')');
            };

            /**
             * Add new cart item to cart
             * @param cartItem
             */
            this.addCartItem = function addProductItem(productId, quantity) {
                return $.post(APPHelper.APIMap.CART_ADD_ITEM, {
                    productId: productId,
                    quantity: quantity
                }).then(function (res) {
                    if (res.status === 0) {
                        // 添加到购物车列表中
                        var cart = res.result.data;
                        // updateCartSection(cart);
                        cartItemList = cart.allItems;
                        totalPrice = cart.totalPrice;
                        if (isGlobalCart) {
                            updateCartSection();
                            htmlHelper.notifySuccessMessage('提示', '已加入购物车');
                        }
                    } else {
                        htmlHelper.notifyErrorMessage("提示", res.error);
                        console.log(res.error);
                    }
                    return res;
                });
            };

            /**
             * remove cart item from cart
             * @param productKey
             */
            this.removeCarttItem = function removeProductItem(productId) {
                return $.post(APPHelper.APIMap.CART_REMOVE_ITEM, {
                    productId: productId
                }).then(function (res) {
                    if (res.status === 0) {
                        var cart = res.result.data;
                        cartItemList = cart.allItems;
                        totalPrice = cart.totalPrice;
                        if (isGlobalCart) {
                            updateCartSection()
                        }
                    } else {
                        htmlHelper.notifyErrorMessage("提示", res.error);
                    }
                    return res;
                });
            };

            /**
             * update cart item quantity
             */
            this.updateCartItemQuantity = function updateCartItemQuantity(productId, quantity) {
                return $.post(APPHelper.APIMap.CART_CHANGE_QUANTITY, {
                    productId: productId,
                    quantity: quantity
                }).then(function (res) {
                    if (res.status === 0) {
                        var cart = res.result.data;
                        cartItemList = cart.allItems;
                        totalPrice = cart.totalPrice;
                        if (isGlobalCart) {
                            updateCartSection();
                        }
                    } else {
                        htmlHelper.notifyErrorMessage("提示", res.error);
                    }

                    return res;
                });
            };

            /**
             * Initialize cart and read data from local storage
             */
            this.initialize = function initialize() {
                var cartInfo = localStorage.getItem('cartInfo');
                if (cartInfo) {
                    cartItemList = cartInfo.cartItemList;
                }
                //sync the cart data
                this.syncCartData(cartItemList);
            };

            /**
             * Sync local cart information and server cart
             */
            this.syncCartData = function syncCartData(localCartItemList) {
                //同步购物车，会将本地现有的购物车的记录存到服务器上
                $.post(
                    APPHelper.APIMap.CART_SYNC_DATA, {
                        cartItemList: localCartItemList
                    })
                    .then(function (res) {
                        if (res.status === 0) {
                            var cart = res.result.data;
                            cartItemList = cart.allItems;
                            totalPrice = cart.totalPrice;
                            if (isGlobalCart) {
                                updateCartSection();
                            }
                        }
                    });
            }

            this.getAllItems = function getAllItems() {
                return cartItemList;
            };

            this.getTotalPrice = function getTotalPrice() {
                return totalPrice;
            }

            this.config = function (_isGlobalCart) {
                isGlobalCart = _isGlobalCart;
            };
        }
    }
)();