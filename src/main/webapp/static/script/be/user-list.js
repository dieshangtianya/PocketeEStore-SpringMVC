(function () {
    var userInfDialogHtml = '';
    var currentEditedAdminInfo = null;

    $(document).ready(function () {
        buildAdminTable();
        userInfDialogHtml = $('#user-add-info').html();
        attachEvents();
        $('#user-add-info').empty();
    });

    function buildAdminTable() {
        var predefinedColumns = htmlHelper.table.getPredefinedColumns();
        var operationColumn = predefinedColumns.commonOperation;
        operationColumn.formatter = function (value, row, index) {
            if (row.adminName !== 'admin') {
                return [
                    '<button type="button" class="btn btn-primary btn-sm btn-operation edit" style="margin-right:15px;">修改</button>',
                    '<button type="button" class="btn btn-primary btn-sm btn-operation delete">删除</button>',
                ].join('');
            }
            return '';
        };
        operationColumn.events = {
            'click .btn-operation.edit': operationEditClick,
            'click .btn-operation.delete': operationDeleteClick,
        };
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

    function getAdminInfo($dialog) {
        var admininfo = {};
        admininfo.adminName = $dialog.find('#txtUserName').val();
        admininfo.password = $dialog.find('#txtPwd').val();
        admininfo.sex = $dialog.find('input[name="rbSex"]:checked').val();
        admininfo.email = $dialog.find('#txtMail').val();
        admininfo.phone = $dialog.find('#txtPhone').val();
        admininfo.state = $dialog.find('input[name="rbState"]:checked').val();
        return admininfo;
    }

    function validateAdminInfo(adminInfo) {
        var valiateResult = {
            message: '',
            result: true
        }

        var message = [];
        if (!adminInfo.adminName) {
            message.push('用户名不能为空');
        }
        if (adminInfo.adminName.length > 50) {
            message.push('用户名不能超过50个字符');
        }
        if (!adminInfo.password) {
            message.push('用户密码不能为空');
        }
        if (adminInfo.email && !webUtil.checkEmail(adminInfo.email)) {
            message.push('电子邮件格式不正确');
        }
        if (adminInfo.phone && !webUtil.checkPhone(adminInfo.phone)) {
            message.push('手机号格式不正确');
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

    function addAdminInfo($dialog) {
        var adminInfo = getAdminInfo($dialog);

        var validationResult = validateAdminInfo(adminInfo);
        if (!validationResult.result) {
            htmlHelper.showAlert(validationResult.message);
        }
        var encodeHelper = new Base64();

        adminInfo.password = encodeHelper.encode(adminInfo.password);

        // send data to api add user information
        $.ajax({
                url: APPHelper.APIMap.ADMIN_ADD,
                method: 'post',
                data: JSON.stringify(adminInfo),
                contentType: 'application/json;charset=utf-8'
            }
        ).then(function (res) {
            if (res.status === 0 && res.result.data === true) {
                $dialog.modal('hide');
                htmlHelper.showAlert('新增管理员成功');
                $("#userTable").bootstrapTable('refresh');
            } else {
                htmlHelper.showDanger(res.error);
            }
        });
    }

    function deleteAdmin(adminId) {
        $.post(APPHelper.APIMap.ADMIN_DELETE, {
            id: adminId
        }).then(function (res) {
            if (res.status === 0) {
                htmlHelper.notifySuccessMessage('提示', '删除管理员成功');
                $("#userTable").bootstrapTable('refresh');
            } else {
                htmlHelper.notifyErrorMessage("提示", res.error);
            }
        });
    }

    function fillAdminInformation(adminInfo) {
        $('#txtUserName').val(adminInfo.adminName);
        $('#txtPwd').val(adminInfo.password);
        if (adminInfo.sex === 1) {
            $('#rbMale').attr('checked', true);
        } else {
            $('#rbFemale').attr('checked', true);
        }

        $('#txtMail').val(adminInfo.email);
        $('#txtPhone').val(adminInfo.phone);

        if (adminInfo.state === 1) {
            $('#rbEnable').attr('checked', true);
        } else {
            $('#rbDisable').attr('checked', true);
        }

    }

    function updateAdminInfo($dialog) {
        var adminInfo = getAdminInfo($dialog);
        adminInfo.id = currentEditedAdminInfo.id;
        var validationResult = validateAdminInfo(adminInfo);
        if (!validationResult.result) {
            htmlHelper.showAlert(validationResult.message);
        }
        var encodeHelper = new Base64();

        adminInfo.password = encodeHelper.encode(adminInfo.password);

        // send data to api add user information
        $.ajax({
                url: APPHelper.APIMap.ADMIN_UPDATE,
                method: 'post',
                data: JSON.stringify(adminInfo),
                contentType: 'application/json;charset=utf-8'
            }
        ).then(function (res) {
            if (res.status === 0 && res.result.data === true) {
                $dialog.modal('hide');
                htmlHelper.showAlert('修改管理员成功');
                $("#userTable").bootstrapTable('refresh');
            } else {
                htmlHelper.showDanger(res.error);
            }
        });
    }

    function operationEditClick(e, value, row) {
        var buttons = htmlHelper.getDefaultDialogButton();
        var dialog = null;
        buttons.save.callback = function () {
            updateAdminInfo(dialog);
            return false;
        };
        currentEditedAdminInfo = row;
        dialog = htmlHelper.showDialog({
            title: '修改管理员信息',
            message: userInfDialogHtml,
            buttons: buttons,
        });
        dialog.init(function () {
            fillAdminInformation(row);
        });
    }

    function operationDeleteClick(e, value, row) {
        var adminId = row.id;
        htmlHelper.showConfirm({
            size: "small",
            message: "确认要删除该管理员信息吗",
            callback: function (result) {
                if (result) {
                    deleteAdmin(adminId);
                }
            }
        });
    }

    function openAddUserDialog() {
        var buttons = htmlHelper.getDefaultDialogButton();
        var dialog = null;
        buttons.save.callback = function () {
            addAdminInfo(dialog);
            return false;
        };

        dialog = htmlHelper.showDialog({
            title: '新增管理员信息',
            message: userInfDialogHtml,
            buttons: buttons,
        });
    }

    function attachEvents() {
        $('#btnAddUser').on('click', openAddUserDialog);
    }
})
();