(function () {
    $(document).ready(function () {
        var drawerShow = true;
        var pageLoad = function () {
            registerDrawerToggleEvents();
            registerUserActions();
        }

        pageLoad();

        function registerDrawerToggleEvents() {
            $('#drawer-toggle').on('click', function () {
                drawerShow = !drawerShow;
                if (drawerShow) {
                    $('#drawer-panel').animate(
                        {
                            'margin-left': '0',
                            'opacity': '1',
                        }
                        , 500);
                } else {
                    $('#drawer-panel').animate(
                        {
                            'margin-left': '-300px',
                            'opacity': '.2',
                        }
                        , 500);
                }
            });
        }

        function registerUserActions() {
            $('#linkLogout').on('click', logout);
        }

        //退出登录
        function logout() {
            $.post(APPHelper.APIMap.ADMIN_LOGOUT)
                .then(function (res) {
                    if (res.status === 0) {
                        window.location.href = APPHelper.MANAGEMENT_HOME;
                    } else {
                        alert("退出失败")
                    }
                })
        }
    });
})();