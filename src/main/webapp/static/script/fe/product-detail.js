(function () {
    var cart = null;
    $(document).ready(function () {
        pageLoad();
    });

    function pageLoad() {
        attachEvent();
        initQuantityCounter();
        loadCartInformation();
    }

    function attachEvent() {
        $('.btn-add-cart').on('click', function () {
            var quantity = $('.counter-input').val();
            var productId = $('#hiddenInputProductId').val();
            cart.addCartItem(productId, quantity);
        });
    }

    function initQuantityCounter() {
        $(".counter-input").niceNumber({
            autoSize: false,
            autoSizeBuffer: 1,
        });
    }

    function loadCartInformation() {
        cart = new Cart();
        cart.initialize();
    }
})();