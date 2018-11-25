(function () {
    $(document).ready(function () {
        buildAdminTable();
    });

    function buildAdminTable() {
        var predefinedColumns = htmlHelper.table.predefinedColumns;
        var config = {
            url: APPHelper.APIMap.ADMIN_LIST,
            height: 450,
            columns: [
                predefinedColumns.rowNumber,
                {
                    field: 'adminName',
                    title: '管理员名称',
                    width: 200,
                },
                {
                    field: 'sex',
                    title: '性别',
                    width: 200,
                    formatter: function (value) {
                        if (!value || value === 1) {
                            return '男';
                        } else {
                            return '女';
                        }
                    }
                },
                {
                    field: 'email',
                    title: '邮箱地址',
                },
                {
                    field: 'phone',
                    title: '电话',
                },
                predefinedColumns.state,
                predefinedColumns.createdDate,
                predefinedColumns.commonOperation,
            ],
        };
        htmlHelper.table('#userTable', config);
    }

})();