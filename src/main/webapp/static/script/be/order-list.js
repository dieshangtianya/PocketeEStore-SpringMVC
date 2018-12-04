//@ sourceUrl=order-list.js

(function () {
    $(document).ready(function () {
        buildOrderList();
    });

    function buildOrderList() {
        var predefinedColumns = htmlHelper.table.getPredefinedColumns();
        var operationColumn = predefinedColumns.commonOperation;
        operationColumn.formatter = function (value, row, index) {
            return [
                '<button type="button" class="btn btn-primary btn-sm btn-operation edit" style="margin-right:15px;">修改状态</button>',
                '<button type="button" class="btn btn-primary btn-sm btn-operation detail">查看详细</button>',
            ].join('');
        };
        operationColumn.events = {
            'click .btn-operation.edit': operationEditClick,
            'click .btn-operation.detail': operationShowDetail
        };
        operationColumn.width = 160;
        var config = {
            url: APPHelper.APIMap.ORDER_LIST,
            height: 450,
            columns: [
                predefinedColumns.rowNumber,
                {
                    field: 'orderNo',
                    title: '订单编号',
                    width: 200
                },
                {
                    field: 'state',
                    title: '状态',
                    width: 150,
                    formatter: function (value) {
                        if (value === 'NoPaid') {
                            return '未支付';
                        } else if (value === 'Paid') {
                            return '已支付';
                        } else if (value === 'Completed') {
                            return '已完成';
                        } else if (value === 'Canceled') {
                            return '已取消';
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'payment',
                    title: '订单金额',
                    width: 100,
                    align: 'right',
                    formatter: function (value) {
                        return webUtil.formatToMoney(value);
                    }
                },
                {
                    field: 'createdDate',
                    title: '下单日期'
                },
                {
                    field: 'updatedDate',
                    title: '更新日期'
                },
                operationColumn,
            ],
        };
        htmlHelper.table('#orderTable', config);
    }

    function operationEditClick() {
        htmlHelper.showAlert("暂未实现");
    }

    function operationShowDetail() {
        htmlHelper.showAlert("暂未实现");
    }
})();