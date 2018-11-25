//@ sourceUrl=product-list.js

(function () {
    var productInfoDialogHtml;
    var currentEditedProduct = null;
    $(document).ready(function () {
        buildProductList();
        attachEvents();
        productInfoDialogHtml = $('#product-add-info').html();
        $('#product-add-info').empty();
    });

    function buildProductList() {
        var predefinedColumns = htmlHelper.table.predefinedColumns;
        var operationColumn = predefinedColumns.commonOperation;
        operationColumn.events = {
            'click .btn-operation.edit': operationEditClick,
            'click .btn-operation.delete': operationDeleteClick,
        };
        var config = {
            url: APPHelper.APIMap.PRODUCT_LIST,
            height: 450,
            columns: [
                predefinedColumns.rowNumber,
                {
                    field: 'productName',
                    title: '商品名称',
                    width: 200,
                    formatter: function (value) {
                        return '<a href="#">' + value + '</a>';
                    }
                },
                {
                    field: 'brandName',
                    title: '品牌',
                    width: 150,
                },
                {
                    field: 'price',
                    title: '商品价格',
                    width: 100,
                },
                {
                    field: 'habitat',
                    title: '产地',
                },
                {
                    field: 'tags',
                    title: '标签',
                },
                operationColumn,
            ],
        };
        htmlHelper.table('#productTable', config);
    }

    function openAddProductDialog() {
        var buttons = htmlHelper.getDefaultDialogButton();
        var dialog = null;
        buttons.save.callback = function () {
            addProductInfo(dialog);
            return false;
        };

        dialog = htmlHelper.showDialog({
            title: '新增产品信息',
            message: productInfoDialogHtml,
            buttons: buttons,
        });
        dialog.init(function () {
            dialogInitialization(webUtil.DataMode.ADD);
        });
    }

    function validateProduct(productInfo, dataMode) {
        var errors = [];
        if (webUtil.isNullOrEmpty(productInfo.productName)) {
            errors.push('请输入商品名称!');
        }
        if (webUtil.isNullOrEmpty(productInfo.price)) {
            errors.push('请输入商品价格!');
        }

        if(!webUtil.isNumber(productInfo.price)){
            errors.push('商品价格只能是数字');
        }

        if (dataMode === webUtil.DataMode.ADD) {
            if (!productInfo.thumbnailFile) {
                errors.push('请选择商品的缩略图!');
            }
        }

        return new ValidateResult(errors);
    }

    function addProductInfo($dialog) {
        var productInfo = getProductInfoFromForm();

        var validateResult = validateProduct(productInfo, webUtil.DataMode.ADD);
        if (validateResult.result) {
            saveProductInfo(webUtil.DataMode.ADD, productInfo)
                .then(function (res) {
                    if (res.status === 0) {
                        $dialog.modal('hide');
                        $("#productTable").bootstrapTable('refresh');
                    }
                });
        } else {
            htmlHelper.showDanger(validateResult.getErrorMessageHtml());
        }
    }

    function getProductInfoFromForm() {
        var productInfo = {};
        productInfo.productName = $('#txtProductName').val();
        productInfo.brandName = $('#txtBrandName').val();
        productInfo.price = $('#txtProductPrice').val();
        var currentFiles = $('#fileProductThumbnail').get(0).files;
        if (currentFiles.length > 0) {
            productInfo.thumbnailFile = currentFiles[0];
        }
        productInfo.habitat = $('#txtHabitat').val();
        productInfo.tags = $('#product-tag').tagsinput('items');
        productInfo.state = $('input[name="radioState"][checked]').val();
        productInfo.description = $('#txtDescription').val();
        return productInfo;
    }

    function updateProductInfo($dialog) {
        var productInfo = getProductInfoFromForm();
        productInfo.thumbnail = currentEditedProduct.thumbnail;
        productInfo.id = currentEditedProduct.id;

        var validateResult = validateProduct(productInfo, webUtil.DataMode.UPDATE);
        if (validateResult.result) {
            saveProductInfo(webUtil.DataMode.UPDATE, productInfo)
                .then(function (res) {
                    if (res.status === 0) {
                        $dialog.modal('hide');
                        $("#productTable").bootstrapTable('refresh');
                    }
                });
        } else {
            htmlHelper.showDanger(validateResult.getErrorMessageHtml());
        }
    }

    function dialogInitialization(dataMode) {
        initializeTagInput();
        $('#fileProductThumbnail').on('change', showThumbnailImage);
    }

    function showThumbnailImage(e) {
        var fileControl = e.target;
        var file = fileControl.files[0];
        var pic = $('#imgThumbnailPreview');
        if (file) {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                pic.css('display', 'inline-block');
                pic.attr('src', this.result);
            }
        } else {
            pic.css('display', 'none');
            pic.attr('src', '');
        }
    }

    function fillProductInformation(dataItem) {
        $('#txtProductName').val(dataItem.productName);
        $('#txtBrandName').val(dataItem.brandName);
        $('#txtProductPrice').val(dataItem.price);
        if (dataItem.thumbnail) {
            $('#imgThumbnailPreview').attr('src', dataItem.thumbnail);
            $('#imgThumbnailPreview').css('display', 'inline-block');
        }
        $('#txtHabitat').val(dataItem.habitat);
        $('input[name="radioState"][value="' + dataItem.state + '"]').attr('checked', true);
        $('#txtDescription').val(dataItem.description);
        if (dataItem.tags) {
            var tagItems = dataItem.tags;
            for (var index = 0; index < tagItems.length; index++) {
                $('#product-tag').tagsinput('add', tagItems[index]);
            }
        }
    }

    function operationEditClick(e, value, row) {
        var buttons = htmlHelper.getDefaultDialogButton();
        var dialog = null;
        buttons.save.callback = function () {
            updateProductInfo(dialog);
            return false;
        };
        currentEditedProduct = row;
        dialog = htmlHelper.showDialog({
            title: '修改产品信息',
            message: productInfoDialogHtml,
            buttons: buttons,
        });
        dialog.init(function () {
            dialogInitialization(webUtil.DataMode.UPDATE);
            fillProductInformation(row);
        });
    }

    function operationDeleteClick(e, value, row, index) {
        var productId = row.id;
        htmlHelper.showConfirm({
            size: "small",
            message: "确认要删除该商品吗",
            callback: function (result) {
                if (result) {
                    deleteProductInfo(productId);
                }
            }
        });
    }

    function deleteProductInfo(productId) {
        $.post(APPHelper.APIMap.PRODUCT_DELETE, {
            id: productId
        }).then(function (res) {
            if (res.status === 0) {
                htmlHelper.notifySuccessMessage('提示', '商品删除成功');
                $("#productTable").bootstrapTable('refresh');
            } else {
                htmlHelper.notifyErrorMessage("提示", res.error);
            }
        });
    }

    function saveProductInfo(mode, productInfo) {
        var apiUrl = APPHelper.APIMap.PRODUCT_ADD;
        var successMsg = '新增商品成功';
        if (mode === webUtil.DataMode.UPDATE) {
            apiUrl = APPHelper.APIMap.PRODUCT_UPDATE;
            successMsg = '更新商品成功';
        }

        var formData = new FormData();
        if (productInfo.id) {
            formData.append('id', productInfo.id);
        }
        formData.append('productName', productInfo.productName);
        formData.append('brandName', productInfo.brandName);
        formData.append('price', productInfo.price);
        formData.append('habitat', productInfo.habitat);
        formData.append('thumbnailFile', productInfo.thumbnailFile);
        if (productInfo.thumbnail) {
            formData.append('thumbnail', productInfo.thumbnail);
        }
        formData.append('tags', productInfo.tags.join(','));
        formData.append('state', productInfo.state);
        formData.append('description', productInfo.description);

        var options = {
            url: apiUrl,
            method: 'POST',
            data: formData,
            contentType: false,
            processData: false,//如果不填的，报错 illegal request
        };

        return $.ajax(options)
            .then(function (res) {
                if (res.status === 0) {
                    // htmlHelper.showSuccess(successMsg);
                    htmlHelper.notifySuccessMessage('提示', successMsg);
                } else {
                    htmlHelper.notifyErrorMessage('提示', res.error);
                }
                return res;
            });
    }

    function attachEvents() {
        $('#btnAddProduct').on('click', openAddProductDialog);
    }

    function initializeTagInput() {
        $('#product-tag').tagsinput({
            maxTags: 5,
            trimValue: true
        });
    }
})();