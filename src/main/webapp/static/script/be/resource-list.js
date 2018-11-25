(function () {
    $(document).ready(function () {
        buildResourceTable();
    });

    function buildResourceTable() {
        var predefinedColumns = htmlHelper.table.predefinedColumns;

        var config = {
            url: APPHelper.APIMap.FEATURE_LIST,
            height: 450,
            columns: [
                predefinedColumns.rowNumber,
                {
                    field: 'resourceName',
                    title: '功能名称',
                    width: 200,
                },
                {
                    field: 'path',
                    title: '功能路径',
                    width: 300,
                },
                {
                    field: 'resourceType',
                    title: '功能类型',
                    formatter: function (value) {
                        if (value === 'Navigation') {
                            return '导航';
                        } else if (value === 'Page') {
                            return '页面';
                        } else if (value === 'Interface') {
                            return '接口';
                        } else {
                            return '未知';
                        }
                    }
                },
                predefinedColumns.state,
                predefinedColumns.commonOperation,
            ],
        };
        htmlHelper.table('#resourceTable', config);
    }

})();