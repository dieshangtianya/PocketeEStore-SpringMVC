(function () {

    var tooltipTemplate =
        '<div class="tooltip" role="tooltip">' +
        '<div class="arrow">' +
        '</div>' +
        '<div class="custom-tooltip">' +
        '<span><i class="icon iconfont icon-info-circle"></i></span><div class="tooltip-inner"></div>'
    '</div>' +
    '</div>';

    $(document).ready(function () {
        $('#btnLogin').on("click", function () {
            var txtUserName = $('#txtUserName');
            var txtPassword = $('#txtPassword');
            var userName = txtUserName.val();
            var password = txtPassword.val();

            if (webUtil.isNullOrEmpty(userName)) {
                showTooltip(txtUserName, '输入用户名');
                return;
            }

            if (webUtil.isNullOrEmpty(password)) {
                showTooltip(txtPassword, '输入用户密码');
                return;
            }

            login(userName, password);
        });

        function showTooltip(element, tooltip) {
            var tooltipOption = {
                delay: {show: 500, hide: 100},
                placement: 'bottom',
                title: tooltip,
                trigger: 'manual',
                template: tooltipTemplate
            };
            element.tooltip(tooltipOption);
            element.tooltip('show');
            setTimeout(function () {
                element.tooltip('hide');
            }, 1000);
        }

        function login(userName, password) {
            // call api to login
            var base64Encodeer = new Base64();
            $.post(
                APPHelper.APIMap.ADMIN_LOGIN,
                {
                    userName: userName,
                    password: base64Encodeer.encode(password),
                },
                function (res) {
                    if (res.status === 0) {
                        var returnUrl = webUtil.getURLParameter('returnUrl');
                        if (webUtil.isNullOrEmpty(returnUrl)) {
                            returnUrl = '/management/';
                        }
                        location.href = returnUrl;
                    } else {
                        showErrorMessage(res.error);
                    }
                });
        }

        function showErrorMessage(message) {
            $('#error-message').text(message);
            $('.error-tip').show();
        }
    })
})()