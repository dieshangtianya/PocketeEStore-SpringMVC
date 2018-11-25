<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../globaldata.jsp" %>

<layout:layoutContent name="title">
    WiWi购物商城登录
</layout:layoutContent>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticFECssPath%>/common-app.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticFECssPath%>/login.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="bodyBlock">
    <div class="app">
        <section class="pure-header best-area">
            <a href="/" class="logo-icon">
                <img src="../../static/images/logo.png" style="width:64px;height: 64px;">
            </a>
            <span>WiWi购物商登录</span>
        </section>
        <section class="login-body">
            <div class="login-wrapper clearfix">
                <div class="login-form">
                    <div class="login-nav">
                        账号登录
                    </div>
                    <div>
                        <div class="error-tip">
                            <i class="icon iconfont icon-minus-circle-fill"></i>
                            <span id="error-message">
                    </span>
                        </div>
                    </div>
                    <div class="common-row">
                        <input id="txtUserName" type="text" class="login-input" placeholder="用户名/手机号"/>
                    </div>
                    <div class="common-row">
                        <input id="txtPassword" type="password" class="login-input" placeholder="密码"/>
                    </div>
                    <div class="common-row margin-0">
                        <button id="btnButton" class="button login-button">登录</button>
                    </div>
                    <div class="user-tips">
                        <a href="/customer/signUp">立即注册</a>
                        <span class="nav-separator">|</span>
                        <a href="/cusotomer/forgetword">忘记密码</a>
                    </div>
                    <div class="other-mode">
                        <fieldset>
                            <legend align="center">其他方式登录</legend>
                        </fieldset>
                        <div class="mode-collection">
                            <a class="circle-block circle-qq"><i class="icon iconfont icon-QQ"></i></a>
                            <a class="circle-block circle-weibo"><i class="icon iconfont icon-weibo"></i></a>
                            <a class="circle-block circle-alipay"><i class="icon iconfont icon-alipay"></i></a>
                            <a class="circle-block circle-wechat"><i class="icon iconfont icon-wechat-fill"></i></a>
                        </div>
                    </div>
                </div>
                <div class="login-bg-banner"></div>
            </div>
        </section>
        <section class="pure-footer">
            <%@include file="common/pure-footer.jsp" %>
        </section>
    </div>

</layout:layoutContent>

<layout:layoutContent name="footerScriptBlock">
    <script type="text/javascript" src="<%=staticFEScriptPath%>/login.js"></script>
</layout:layoutContent>

<jsp:include page="../layout.jsp"/>
