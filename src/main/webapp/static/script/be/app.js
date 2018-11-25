//该文件是系统的启动选项
var App = (function () {
    var app = {};
    var configAjaxRequest = function () {
        $(document).ajaxStart(function () {
            //AJAX请求刚开始之前进行处理
        });
        $(document).ajaxSend(function (event, xhr, settings) {
            //AJAX请求发送之前进行处理
        });
        $(document).ajaxComplete(function (event, xhr, settings) {
            //AJAX请求发送完成进行处理
        });
        $(document).ajaxError(function (event, xhr, settings, thrownError) {
            //AJAx请求失败进行处理
            if (xhr.status === 401) {
                window.location.href = APPHelper.MANAGEMENT_LOGIN;
            } else {
                //htmlHelper.notifyMessage(htmlHelper.notifyMessage.messageType.DANGER, );
                htmlHelper.notifyErrorMessage('不小心发生了错误', '请求过程发生失败');
            }
        });
        $(document).ajaxSuccess(function (event, xhr, settings) {
            //AJAX请求成功进行处理
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