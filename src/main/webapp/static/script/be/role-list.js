(function () {
    $(document).ready(function () {
        buildRoleTable();
    });

    function buildRoleTable() {
        var predefinedColumns = htmlHelper.table.getPredefinedColumns();
        var config = {
            url: APPHelper.APIMap.ROLE_LIST,
            height: 450,
            columns: [
                predefinedColumns.rowNumber,
                {
                    field: 'roleName',
                    title: '角色名称',
                    width: 200,
                },
                predefinedColumns.state,
                predefinedColumns.createdDate,
                predefinedColumns.commonOperation,
            ],
        };
        htmlHelper.table('#roleTable', config);
    }

})();