(function () {

    var roleInfoDialogHtml = '';
    var currentEditedRoleInfo = null;

    $(document).ready(function () {
        buildRoleTable();
        roleInfoDialogHtml = $('#role-add-info').html();
        attachEvents();
        $('#role-add-info').empty();
    });

    function buildRoleTable() {
        var predefinedColumns = htmlHelper.table.getPredefinedColumns();
        var operationColumn = predefinedColumns.commonOperation;
        operationColumn.formatter = function (value, row, index) {
            if (row.roleName !== 'Administrator') {
                return [
                    '<button type="button" class="btn btn-primary btn-sm btn-operation setRight" style="margin-right:15px;">编辑权限</button>',
                    '<button type="button" class="btn btn-primary btn-sm btn-operation edit" style="margin-right:15px;">修改</button>',
                    '<button type="button" class="btn btn-primary btn-sm btn-operation delete">删除</button>',
                ].join('');
            }
            return '';
        };
        operationColumn.width = 200;
        operationColumn.events = {
            'click .btn-operation.edit': operationEditClick,
            'click .btn-operation.delete': operationDeleteClick,
        };
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

    function getRoleInfo($dialog) {
        var roleInfo = {};
        roleInfo.roleName = $dialog.find('#txtRoleName').val();
        roleInfo.state = $dialog.find('input[name="rbState"]:checked').val();
        return roleInfo;
    }

    function validateRoleInfo(roleInfo) {
        var valiateResult = {
            message: '',
            result: true
        }

        var message = [];
        if (!roleInfo.roleName) {
            message.push('角色名不能为空');
        }
        if (roleInfo.roleName.length > 50) {
            message.push('角色名不能超过50个字符');
        }
        if (message.length > 0) {
            var content = "";
            for (var index = 0; index < message.length; index += 1) {

                content += message[index];
                if (index < message.length - 1)
                    content += '<br/>';
            }
            valiateResult.result = false;
            valiateResult.message = content;
        }
        return valiateResult;
    }

    function addRoleInfo($dialog) {
        var roleInfo = getRoleInfo($dialog);
        var validationResult = validateRoleInfo(roleInfo);
        if (!validationResult.result) {
            htmlHelper.showAlert(validationResult.message);
        }

        // send data to api add user information
        $.ajax({
                url: APPHelper.APIMap.ROLE_ADD,
                method: 'post',
                data: JSON.stringify(roleInfo),
                contentType: 'application/json;charset=utf-8'
            }
        ).then(function (res) {
            if (res.status === 0 && res.result.data === true) {
                $dialog.modal('hide');
                htmlHelper.notifySuccessMessage('提示', '新增角色成功');
                $("#roleTable").bootstrapTable('refresh');
            } else {
                htmlHelper.showDanger(res.error);
            }
        });
    }

    function updateRoleInfo($dialog) {
        var roleInfo = getRoleInfo($dialog);
        roleInfo.id = currentEditedRoleInfo.id;
        var validationResult = validateRoleInfo(roleInfo);
        if (!validationResult.result) {
            htmlHelper.showAlert(validationResult.message);
        }

        // send data to api add user information
        $.ajax({
                url: APPHelper.APIMap.ROLE_UPDATE,
                method: 'post',
                data: JSON.stringify(roleInfo),
                contentType: 'application/json;charset=utf-8'
            }
        ).then(function (res) {
            if (res.status === 0 && res.result.data === true) {
                $dialog.modal('hide');
                htmlHelper.notifySuccessMessage('提示', '修改角色成功');
                $("#roleTable").bootstrapTable('refresh');
            } else {
                htmlHelper.showDanger(res.error);
            }
        });
    }

    function deleteRole(roleId) {
        $.post(APPHelper.APIMap.ROLE_DELETE, {
            id: roleId
        }).then(function (res) {
            if (res.status === 0) {
                htmlHelper.notifySuccessMessage('提示', '删除角色成功');
                $("#roleTable").bootstrapTable('refresh');
            } else {
                htmlHelper.notifyErrorMessage("提示", res.error);
            }
        });
    }

    function fillRoleInfo(roleInfo) {
        $('#txtRoleName').val(roleInfo.roleName);
        if (roleInfo.state === 1) {
            $('#rbEnable').attr('checked', true);
        } else {
            $('#rbDisable').attr('checked', true);
        }
    }

    function openAddRoleDialog() {
        var buttons = htmlHelper.getDefaultDialogButton();
        var dialog = null;
        buttons.save.callback = function () {
            addRoleInfo(dialog);
            return false;
        };

        dialog = htmlHelper.showDialog({
            title: '新增角色信息',
            message: roleInfoDialogHtml,
            buttons: buttons,
        });
    }

    function operationEditClick(e, value, row) {
        var buttons = htmlHelper.getDefaultDialogButton();
        var dialog = null;
        buttons.save.callback = function () {
            updateRoleInfo(dialog);
            return false;
        };
        currentEditedRoleInfo = row;
        dialog = htmlHelper.showDialog({
            title: '修改角色信息',
            message: roleInfoDialogHtml,
            buttons: buttons,
        });
        dialog.init(function () {
            fillRoleInfo(row);
        });
    }

    function operationDeleteClick(e, value, row) {
        var roleId = row.id;
        htmlHelper.showConfirm({
            size: "small",
            message: "确认要删除该管理员信息吗",
            callback: function (result) {
                if (result) {
                    deleteRole(roleId);
                }
            }
        });
    }

    function attachEvents() {
        $('#btnAddRole').on('click', openAddRoleDialog);
    }

})();