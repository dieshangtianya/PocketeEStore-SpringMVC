(function () {
        var currentPageIndex = 1;
        var productPageSize = 20;
        var cart = null;

        $(document).ready(function () {
            pageLoad();
        });

        function pageLoad() {
            //附件事件
            attatchEvents();
            //读取页码和页容量
            var pageIndexString = webUtil.getURLParameter('page');
            if (pageIndexString) {
                var pageIndex = parseInt(pageIndexString);
                if (webUtil.isNumber(pageIndex)) {
                    currentPageIndex = pageIndex;
                }
            }
            //加载购物车信息
            loadCartInformation();
            //加载数据
            loadProductList(currentPageIndex);
        }

        function loadProductList(pageIndex) {
            getProductList(pageIndex)
                .then(function (res) {
                    if (res.status === 0) {
                        buildProductList(res.result.data);
                        buildPagination(res.result.total, currentPageIndex);
                    }
                });
        }

        function buildProductList(productList) {
            var countOneRow = 4;
            var rowElement = null;
            for (var index = 0; index < productList.length; index++) {
                if (index / countOneRow === 0) {
                    if (rowElement != null) {
                        $('#product-list').append(rowElement);
                    }
                    rowElement = $('<div class="row"></div>');
                }
                var $itemWrapperElement = $('<div class="col-sm-3 product-item-wrapper"></div>');
                var itemTemplate =
                    '<div class="product-item">' +
                    '<div class="product-img">' +
                    '<div class="img-container">' +
                    '<a href="/product/' + productList[index].id + '"><img src="' + productList[index].thumbnail + '"/></a>' +
                    '</div>' +
                    '<div class="floating-bar">' +
                    '<span class="icon-concern">' +
                    '<i class="icon iconfont icon-heart"></i>' +
                    '<i class="icon iconfont icon-heart-fill"></i>' +
                    '关注' +
                    '</span>' +
                    '</div>' +
                    '</div>' +
                    '<div class="product-content">' +
                    '<div class="product-info">' +
                    '<a href="/product/' + productList[index].id + '">' + productList[index].productName + '</a>' +
                    '</div>' +
                    '<div class="product-price">' +
                    '<span>￥' + productList[index].price + '</span>' +
                    '<span class="icon-add-to-cart" title="加入购物车"><i class="icon iconfont icon-cartplus"></i></span>' +
                    '</div>'
                '</div>' + '</div>';
                var $itemElement = $(itemTemplate);
                $itemWrapperElement.append($itemElement);
                (function () {
                    var productId = productList[index].id;
                    $itemElement.find('.icon-add-to-cart').on('click', function () {
                        cart.addCartItem(productId, 1);
                    });
                })();
                rowElement.append($itemWrapperElement);
                if (index === productList.length - 1) {
                    $('#product-list').append(rowElement);
                }
            }
        }

        function buildPagination(total, currentPage) {
            var hrefPrefix = getHrefPrefix();
            if (hrefPrefix === '') {
                hrefPrefix += '?'
            } else {
                hrefPrefix += '&';
            }
            hrefPrefix += 'page=';
            $('#product-pagination').pagination({
                items: total,
                itemsOnPage: productPageSize,
                prevText: '上一页',
                nextText: '下一页',
                currentPage: currentPage,
                cssStyle: 'orange-theme',
                //useAnchors:false,
                hrefTextPrefix: hrefPrefix,
                onPageClick: function (pageNumber, event) {
                }
            });
        }

        function getProductList(pageIndex) {
            var paramObject = {
                limit: productPageSize,
                page: pageIndex
            };
            var keyword = webUtil.getURLParameter('keyword');
            if (!webUtil.isNullOrEmpty(keyword)) {
                paramObject.keyword = keyword;
            }
            return $.ajax({
                url: APPHelper.APIMap.PRODUCT_LIST_FE,
                method: 'post',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(paramObject)
            });
        }

        function loadCartInformation() {
            cart = new Cart();
            cart.initialize();
        }

        function attatchEvents() {
            $('#btn-search').on('click', function () {
                var keyword = $('#search-input').val();
                if (keyword) {
                    window.location.href = "?keyword=" + encodeURIComponent(keyword);
                }
            });
        }

        function getKeyword() {
            var keyword = $('#search-input').val();
            return keyword;
        }

        function getHrefPrefix() {
            var keyword = getKeyword();
            var hrefPrefix = '';
            if (!webUtil.isNullOrEmpty(keyword)) {
                hrefPrefix += '?keyword=' + keyword;
            }
            return hrefPrefix;
        }
    }

)();