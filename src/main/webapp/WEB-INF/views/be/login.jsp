<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@ include file="../globaldata.jsp" %>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticBECssPath%>/login.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="bodyBlock">
    <div class="login-page">
        <div class="login-form">
            <div class="login-title">WiWi商城管理系统</div>
            <div class="login-content">
                <div class="row form-group">
                    <div class="col-sm-12">
                        <input type="text" id="txtUserName" class="control-input" placeholder="用户名"/>
                    </div>
                </div>
                <div class="row form-group">
                    <div class="col-sm-12">
                        <input type="password" id="txtPassword" class="control-input" placeholder="密码"/>
                    </div>
                </div>
                <div class="row form-group error-tip">
                    <div class="col-sm-12 login-btn-area">
                        <span id="error-message"></span>
                    </div>
                </div>
                <div class="row form-group">
                    <div class="col-sm-12 login-btn-area">
                        <button id="btnLogin" class="button">登录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:layoutContent>

<layout:layoutContent name="footerScriptBlock">
    <script type="text/javascript" src="<%=staticBEScriptPath%>/login.js"></script>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/encodeHelper.js"></script>
</layout:layoutContent>

<jsp:include page="../layout.jsp"/>