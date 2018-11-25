var htmlHelper = (function () {
        /********bootstrap table***********/

        var generateHtmlTable = function (tableSelector, config) {
            var attachNumberToPageSource = function (dataArray) {
                if (dataArray.length === 0) {
                    return;
                }
                var gridNumber = 1;
                if (dataArray.page) {
                    var pageInfo = dataArray.page;
                    gridNumber = ((pageInfo.currentPage - 1) * pageInfo.pageSize) + 1;
                }

                for (var index = 0; index < dataArray.length; index++) {
                    dataArray[index].gridNumber = gridNumber;
                    gridNumber += 1;
                }
            };

            var defaultQueryParam = function (params) {
                var questParam = {
                    limit: params.limit,
                    page: (params.offset / params.limit) + 1,
                };
                return questParam;
            };

            var showRowNumber = function () {
                var columns = config.columns;
                var index = _.findIndex(columns, function (item) {
                    return item.field === 'gridNumber';
                });
                return index >= 0;
            }

            var defaultResponseHandler = function (res) {
                var resultData = {
                    total: 0,
                    rows: []
                };
                if (res.status === 0) {
                    resultData = {
                        total: res.result.total,
                        rows: res.result.data,
                    }
                    if (resultData.rows.length > 0) {
                        resultData.rows.page = {
                            currentPage: res.result.currentPage,
                            pageSize: res.result.pageSize
                        };
                        //set row number
                        if (showRowNumber()) {
                            attachNumberToPageSource(resultData.rows);
                        }
                    }
                }
                return resultData;
            };

            var defaultConfig = {
                height: undefined,
                striped: true,
                sortOrder: 'asc',
                iconsPrefix: 'glyphicon',
                icons: {
                    paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
                    paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
                    refresh: 'glyphicon-refresh icon-refresh',
                    toggleOff: 'glyphicon-list-alt icon-list-alt',
                    toggleOn: 'glyphicon-list-alt icon-list-alt',
                    columns: 'glyphicon-th icon-th',
                    detailOpen: 'glyphicon-plus icon-plus',
                    detailClose: 'glyphicon-minus icon-minus',
                    fullscreen: 'glyphicon-fullscreen'
                },
                columns: [],
                data: [],
                ajax: undefined,
                method: 'post',
                url: '',
                cache: false,
                contentType: 'application/json',
                dataType: 'json',
                ajaxOptions: {},
                queryParams: defaultQueryParam,
                queryParamsType: 'limit',
                responseHandler: defaultResponseHandler,
                pagination: true,
                sidePagination: 'server',
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 25, 50],
                selectItemName: 'btSelectItem',
                search: false,
                searchOnEnterKey: true,
                strictSearch: false,
                searchText: '',
                searchTimeOut: 500,
                trimOnSearch: true,
                showColumns: true,
                showRefresh: true,
                showToggle: false,
                showFullscreen: false,
                minimumCountColumns: 2,
                idField: 'id',
                uniqueId: 'id',
                cardView: false,
                detailView: false,
                paginationPreText: '<',
                paginationNextText: '>',
                toolbar: '#toolbar',
                buttonsToolbar: undefined,
                sortable: true,
            };
            var tableConfig = Object.assign({}, defaultConfig, config);
            $(tableSelector).bootstrapTable(tableConfig);
            $('#drawer-toggle').on('click', function () {
                setTimeout(function () {
                    $(tableSelector).bootstrapTable('resetView');
                }, 500)
            });
        }

        var predefinedColumns = {
            rowNumber: {
                title: '编号',
                field: 'gridNumber',
                width: 60,
            },
            state: {
                field: 'state',
                title: '状态',
                formatter: function (value) {
                    if (value === '1') {
                        return '启用';
                    } else {
                        return '禁用'
                    }
                }
            },
            createdDate: {
                field: 'createdDate',
                title: '创建日期'
            },
            commonOperation: {
                field: 'Name', title: '操作',
                width: 140,
                align: 'center',
                formatter: function (value, row, index) {
                    return [
                        '<button type="button" class="btn btn-primary btn-sm btn-operation edit" style="margin-right:15px;">修改</button>',
                        '<button type="button" class="btn btn-primary btn-sm btn-operation delete">删除</button>',
                    ].join('');
                }
            }
        };

        generateHtmlTable.predefinedColumns = predefinedColumns;

        /**notification**/
        var notify = function (title, message, type, customIcon) {

            var icon = '';
            switch (type) {
                case notify.messageType.DANGER:
                    icon = 'fa fa-times';
                    break;
                case notify.messageType.INFO:
                    icon = 'fa fa-info-circle';
                    break;
                case notify.messageType.SUCCESS:
                    icon = 'fa fa-check-circle';
                case notify.messageType.WARNING:
                    icon = 'fa fa-exclamation-triangle';
                default:
                    icon = 'fa fa-info-circle';
            }
            if (customIcon) {
                icon = customIcon;
            }

            $.notify({
                icon: icon,
                title: title,
                message: message
            }, {
                // settings
                element: 'body',
                type: type,
                position: 'fixed',
                allow_dismiss: true,
                newest_on_top: false,
                showProgressbar: false,
                placement: {
                    from: 'top',
                    align: 'center',
                },
                delay: 500,
                timer: 500,
                icon_type: 'class',
                animate: {
                    enter: 'animated bounceInDown',
                    exit: 'animated fadeOutLeftBig'
                },
                z_index: 1050,
                template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
                    '<div class="app-notification"><button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                    '<span data-notify="icon"></span> ' +
                    '<span data-notify="title">{1}</span></div> ' +
                    '<div data-notify="message">{2}</div>' +
                    '<div class="progress" data-notify="progressbar">' +
                    '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                    '</div>' +
                    '<a href="{3}" target="{4}" data-notify="url"></a>' +
                    '</div>'
            });
        };

        var notifySuccessMessage = function (title, message) {
            notify(title, message, notify.messageType.SUCCESS);
        };
        var notifyErrorMessage = function (title, message) {
            notify(title, message, notify.messageType.DANGER);
        };

        notify.messageType = {
            SUCCESS: 'success',
            INFO: 'info',
            WARNING: 'warning',
            DANGER: 'danger'
        };

        //about the modal dialog, there are two libraries that very good for use
        //(1)bootbox, http://bootboxjs.com/, it is light weight that is enough for current use.
        //(2)bootstrap3-dialog, https://github.com/nakupanda/bootstrap3-dialog, it support more powerful functions
        //although bootstrap3-dialog is more powerful, but it is not compatible for bootstrap 4.
        //so we will use bootbox to work with dialog.

        var dialogType = {
            INFO: 'info',
            SUCCESS: 'success',
            WARNING: 'warning',
            DANGER: 'danger',
        };

        var mergeAlertDialogOptions = function (defaultOptions, param) {
            var paramOptions = param;
            if (typeof paramOptions === 'string') {
                paramOptions = {
                    message: param
                };
            }
            var configOptions = $.extend({}, defaultOptions, paramOptions);
            return configOptions;
        };

        var hasTopWindow = function () {
            if (window !== window.top) {
                return true;
            }
            return false;
        };

        var bootboxDelegator = function (method, param) {
            if (hasTopWindow()) {
                return window.top.window.bootbox.dialog(param);
            }
            return method.apply(bootbox, [param]);
        }

        var showAlert = function () {
            var defaultOption = {
                size: 'small',
                title: '消息提示',
                onEscape: true,
                className: 'pes-modal-dialog pes-alert-dialog'
            };
            var param = arguments[0];
            var configOptions = mergeAlertDialogOptions(defaultOption, param);
            bootboxDelegator(bootbox.alert, configOptions);
        };

        var showWarning = function () {
            var defaultOption = {
                size: 'small',
                title: '消息警告',
                onEscape: true,
                className: 'pes-modal-dialog pes-warning-dialog'
            };
            var param = arguments[0];
            var configOptions = mergeAlertDialogOptions(defaultOption, param);

            bootboxDelegator(bootbox.alert, configOptions);
        };

        var showDanger = function () {
            var defaultOption = {
                size: 'small',
                title: '消息警告',
                onEscape: true,
                className: 'pes-modal-dialog pes-danger-dialog'
            };
            var param = arguments[0];
            var configOptions = mergeAlertDialogOptions(defaultOption, param);

            bootboxDelegator(bootbox.alert, configOptions);
        };

        var showSuccess = function () {
            var defaultOption = {
                size: 'small',
                title: '消息提示',
                onEscape: true,
                className: 'pes-modal-dialog pes-success-dialog',
                buttons: {
                    ok: {
                        label: '确认',
                        className: 'button'
                    }
                },
            };
            var param = arguments[0];
            var configOptions = mergeAlertDialogOptions(defaultOption, param);

            bootboxDelegator(bootbox.alert, configOptions);
        };

        var showConfirm = function (options) {
            var defaultOption = {
                size: 'small',
                title: '消息确认',
                className: 'pes-modal-dialog',
                buttons: {
                    confirm: {
                        label: '确认',
                        className: 'button'
                    },
                    cancel: {
                        label: '取消',
                        className: 'button button-second'
                    }
                },
            };
            var param = arguments[0];
            var configOptions = mergeAlertDialogOptions(defaultOption, param);

            bootboxDelegator(bootbox.confirm, configOptions);
        };

        var showDialog = function () {
            var defaultOption = {
                className: 'pes-modal-dialog',
                onEscape: true
            };
            var param = arguments[0];
            var configOptions = mergeAlertDialogOptions(defaultOption, param);

            return bootboxDelegator(bootbox.dialog, configOptions);
        };

        var getDefaultDialogButton = function () {
            return {
                save: {
                    label: '保存',
                    className: 'button'
                },
                cancel: {
                    label: '取消',
                    className: 'button button-second'
                }
            };
        };

        /*****file upload control******/
            // var fileUpload = function ($element, content) {
            //     $element.addClass('file-upload-wrapper');
            //     var title = content || '选择文件';
            //     var uploadLabel = $('<label class="file-upload">' + title + '</label>');
            //     var fileControl = $('<input type="file"/>');
            //     var fileDetail = $('<span class="file-detail"></span>');
            //     uploadLabel.append(fileControl);
            //     uploadLabel.append(fileDetail);
            //     $element.append(uploadLabel);
            //
            //     fileControl.on('change', function () {
            //         var filePath = $(this).val();
            //         $(".file-detail").html(filePath);
            //     });
            // };

        var htmlHelper = {};
        if (window.ContentViewManager) {
            htmlHelper.loadContentViewTo = window.ContentViewManager.loadContentViewTo;
        }
        htmlHelper.table = generateHtmlTable;
        htmlHelper.notifyMessage = notify;
        htmlHelper.notifySuccessMessage = notifySuccessMessage;
        htmlHelper.notifyErrorMessage = notifyErrorMessage;
        /***add interface here to change library if possible****/
        htmlHelper.showDialog = showDialog;
        htmlHelper.showAlert = showAlert;
        htmlHelper.showWarning = showWarning;
        htmlHelper.showSuccess = showSuccess;
        htmlHelper.showConfirm = showConfirm;
        htmlHelper.showDanger = showDanger;
        htmlHelper.DialogSize = {
            LARGE: 'large',
            SMALL: 'small'
        };
        htmlHelper.getDefaultDialogButton = getDefaultDialogButton;
        return htmlHelper;
    }
)();