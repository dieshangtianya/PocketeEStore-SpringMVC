(function () {
    var cart = null;
    $(document).ready(function () {
        pageLoad();
    });

    function pageLoad() {
        buildNumberControl();
        attachHandlers();
        cart = new Cart();
        cart.config(false);
        cart.initialize();
    }

    function updateCartItemInfo($cartItemElement, productId) {
        var allItems = cart.getAllItems();
        var cartItem = _.find(allItems, function (item) {
            return item.product.id === productId;
        });
        if (cartItem) {
            var unitPrice = cartItem.quantity;
            var quantity = cartItem.product.price;
            var totalPrice = webUtil.formatToMoney(unitPrice * quantity);
            $cartItemElement.parents('.cart-table-row').find('.cell-subtotal').text('￥ ' + totalPrice);

            var totalPrice = webUtil.formatToMoney(cart.getTotalPrice());
            $('.cart-summary').text('￥ ' + totalPrice);
        }
    }

    function buildNumberControl() {
        $('.cart-table').find('.i-spinner')
            .spinner('delay', 200) //delay in ms
            .spinner('changed', function (e, newVal) {
                var productId = $(this).parents('.cart-table-row').find('[type="hidden"]').val();
                var self = this;
                cart.updateCartItemQuantity(productId, newVal).then(function () {
                    updateCartItemInfo($(self), productId);
                });
            });
    }

    function deleteCartItem($rowItemEleemnt, productId) {
        cart.removeCarttItem(productId).then(function (res) {
            if (res.status === 0) {
                $rowItemEleemnt.remove();
                var totalPrice = webUtil.formatToMoney(cart.getTotalPrice());
                $('.cart-summary').text('￥ ' + totalPrice);

                if (cart.getAllItems().length === 0) {
                    var $emptyTip = $('<div class="empty-tip">您的购物车空空如也，<a href="/">赶快去购物吧!</a></div>');
                    $('.cart-table-body').empty();
                    $('.cart-table-body').append($emptyTip);
                    $('.cart-table-footer').remove();
                }
            }
        });
    }

    function attachHandlers() {
        $('.cart-table').find('.btn-operation.btn-delete').on('click', function () {
            var productId = $(this).parents('.cart-table-row').find('[type="hidden"]').val();
            var $rowItemElement = $(this).parents('.cart-table-row');
            deleteCartItem($rowItemElement, productId);
        });

        $('.cart-table').find('.btn-operation.btn-favourite').on('click', function () {
            htmlHelper.showAlert('未实现');
        });

        $('#btn-order').on("click", function () {
            $.get(APPHelper.APIMap.CUSTOMER_CHECK_LOGIN).then(function (res) {
                if (res.status === 0 && res.result.data === true) {
                    showDoOrderDialog();
                } else {
                    window.location.href = APPHelper.CUSTOMER_LOGIN + '?returnUrl=' + window.location.href;
                }
            });
        });
    }

    function showDoOrderDialog() {
        var totalPrice = cart.getTotalPrice();
        var messageElementTemplate = '<div class="order-section"><div>您所选购的商品需要支付<span>￥ ' + totalPrice + '</span>，请在下面输入金额，点击确定</div>' +
            '<div><input type="number" id="txtMoney"> </div></div>';
        var $messageElement = $(messageElementTemplate);

        var dialog = htmlHelper.showDialog({
            title: '下单',
            message: $messageElement,
            show: true,
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'button',
                    callback: function () {
                        var moneyString = dialog.find('#txtMoney').val();
                        if (webUtil.isNumber(moneyString)) {
                            if (parseFloat(moneyString) === totalPrice) {
                                doOrder();
                            } else {
                                htmlHelper.showAlert("输入金额不正确");
                                return false;
                            }
                        } else {
                            htmlHelper.showAlert("请输入正确格式的金额");
                            return false;
                        }
                    }
                },
                cancel: {
                    label: '取消',
                    className: 'button button-second'
                }
            },
        });
    }

    function doOrder() {
        var allItems = cart.getAllItems();
        var totalPrice = cart.getTotalPrice();
        var cartItems = _.map(allItems, function (item) {
            return {
                productId: item.product.id,
                quantity: item.quantity,
                price: item.product.price
            }
        });

        $.ajax({
            type: 'POST',
            url: APPHelper.APIMap.DO_ORDER,
            data: JSON.stringify({
                cartItems: cartItems,
                totalPrice: totalPrice
            }),
            contentType: "application/json;charset=utf-8"
        }).then(function (res) {
            if (res.status === 0) {
                htmlHelper.showSuccess({
                    message: '下单成功,订单号是：' + res.result.data,
                    buttons: {
                        ok: {
                            label: '确定',
                            className: 'button',
                            callback: function () {

                            }
                        }
                    },
                    callback: function () {
                        window.location.reload();
                    }
                });
            } else {
                htmlHelper.showDanger(res.error);
            }
        })
    }
})();