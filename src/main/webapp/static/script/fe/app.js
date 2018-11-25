//该文件是系统的启动选项
var App = (function () {
    var app = {};
    var configAjaxRequest = function () {
        $(document).ajaxError(function (event, xhr, settings, thrownError) {
            //AJAx请求失败进行处理
            if (xhr.status === 401) {
                window.location.href = APPHelper.CUSTOMER_LOGIN;
            }else{
                console.log(xhr);
            }
        });
    };
    app.config = function () {
        configAjaxRequest();
    };
    return app;
})();

$(document).ready(function () {
    App.config();
});