<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../globaldata.jsp"%>

<layout:layoutContent name="title">WiWi购物商城注册</layout:layoutContent>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticFECssPath%>/common-app.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticFECssPath%>/register.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="bodyBlock">
    <div class="app">
        <section class="pure-header best-area">
            <a href="/" class="logo-icon">
                <img src="../../static/images/logo.png" style="width:64px;height: 64px;">
            </a>
            <span>WiWi购物品平台注册</span>
            <div class="login-tip">
                <span>已有账号?</span>
                <a href="/customer/signIn">请登录</a>
            </div>
        </section>
        <section class="register-body">
            <div class="register-form">
                <div class="register-info">
                    <div class="form-group">
                        <label for="txtCustomerName" class="required">用户名称</label>
                        <span class="error-tip"></span>
                        <input type="text" class="form-control" id="txtCustomerName" placeholder="输入用户名">
                    </div>
                    <div class="form-group">
                        <label for="txtPWD" class="required">密码</label>
                        <span class="error-tip"></span>
                        <input type="password" class="form-control" id="txtPwd" placeholder="输入密码">
                    </div>
                    <div class="form-group">
                        <label for="txtRepeatPWD" class="required">重复密码</label>
                        <span class="error-tip"></span>
                        <input type="password" class="form-control" id="txtRepeatPwd" placeholder="重复输入密码">
                    </div>
                    <div class="form-group">
                        <label for="txtRepeatPWD" class="required">邮箱地址</label>
                        <span class="error-tip"></span>
                        <input type="text" class="form-control" id="txtMail" placeholder="输入邮箱地址">
                    </div>
                    <div class="form-group">
                        <label for="txtRepeatPWD" class="required">手机号</label>
                        <span class="error-tip"></span>
                        <input type="text" class="form-control" id="txtPhone" placeholder="输入个人手机号">
                    </div>
                    <div class="form-group operation-bar">
                        <span id="success-tip"></span>
                        <span class="error-tip"></span>
                        <button id="btnRegister" type="button" class="btn button btn-register">点击注册</button>
                    </div>
                </div>
            </div>
        </section>
        <section class="pure-footer">
            <%@ include file="common/pure-footer.jsp" %>
        </section>
    </div>
</layout:layoutContent>

<layout:layoutContent name="footerScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-notify/bootstrap-notify.min.js"></script>
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootbox/bootbox.min.js"></script>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/htmlHelper.js"></script>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/models.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/app.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/register.js"></script>
</layout:layoutContent>

<jsp:include page="../layout.jsp"/>
