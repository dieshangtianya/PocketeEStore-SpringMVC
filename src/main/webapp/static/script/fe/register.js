(function () {

    var setErrorTipForField = function (elementSelector, msg) {
        $(elementSelector).prev('.error-tip').html(msg);
    };

    var registerCustomer = function (customerInfo) {
        var promise = $.post(
            APPHelper.APIMap.CUSTOMER_REGISTER,
            {
                customerName: customerInfo.customerName,
                password: customerInfo.password,
                email: customerInfo.email,
                phone: customerInfo.phone
            });
        promise
            .then(function (result) {
                if (result.status === 0) {
                    htmlHelper.showSuccess("注册成功");
                    setTimeout(function () {
                        window.location.href = '/customer/signIn';
                    }, 1000)
                } else {
                    setErrorTipForField('#btnRegister', '注册失败，请重新尝试！')
                }
            })
            .fail(function (error) {
                console.log(error);
                setErrorTipForField('#btnRegister', '注册失败，请重新尝试！')
            });
    };

    var clearErrorTips = function () {
        $('.error-tip').text('');
    }

    $(document).ready(function () {
        var registerButton = $('#btnRegister');
        registerButton.on('click', function () {
            var customerName = $('#txtCustomerName').val();
            var password = $('#txtPwd').val();
            var repeatPassword = $('#txtRepeatPwd').val();
            var email = $('#txtMail').val();
            var phone = $('#txtPhone').val();

            var errorTipsAmount = 0;

            if (webUtil.isNullOrEmpty(customerName)) {
                setErrorTipForField('#txtCustomerName', '请输入用户名');
                errorTipsAmount += 1;
            } else {
                setErrorTipForField('#txtCustomerName', '');
            }
            if (webUtil.isNullOrEmpty(password)) {
                setErrorTipForField('#txtPwd', '请输入密码');
                errorTipsAmount += 1;
            } else {
                setErrorTipForField('#txtPwd', '');
            }
            if (webUtil.isNullOrEmpty(repeatPassword)) {
                setErrorTipForField('#txtRepeatPwd', '请再次输入密码');
                errorTipsAmount += 1;
            } else if (password !== repeatPassword) {
                setErrorTipForField('#txtRepeatPwd', '两次输入密码不相同');
                errorTipsAmount += 1;
            } else {
                setErrorTipForField('#txtRepeatPwd', '');
            }

            if (webUtil.isNullOrEmpty(email)) {
                setErrorTipForField('#txtMail', '请输入邮箱地址');
                errorTipsAmount += 1;
            } else {
                setErrorTipForField('#txtMail', '')
            }
            if (webUtil.isNullOrEmpty(phone)) {
                setErrorTipForField('#txtPhone', '请输入手机号');
                errorTipsAmount += 1;
            } else {
                setErrorTipForField('#txtPhone', '');
            }

            if (errorTipsAmount === 0) {
                clearErrorTips();
                // 发送注册请求
                var customer = {
                    customerName: customerName,
                    password: password,
                    email: email,
                    phone: phone
                }
                registerCustomer(customer);
            }
        });
    });
})();