<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../globaldata.jsp" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticBECssPath%>/user-list.css" type="text/css"/>
</layout:layoutContent>

<!----以下是AJAX布局内容---->
<layout:layoutContent name="contentBlock">
    <div id="toolbar">
        <button id="btnAddUser" type="button" class="btn btn-primary btn-operation">新增管理员</button>
    </div>
    <table id="userTable">
    </table>
    <!--新增窗口-->
    <div id="user-add-info" class="dialog-body-content">
        <div class="common-row">
            <label class="field-label">用户名称：</label>
            <input type="text" id="txtUserName" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">用户密码：</label>
            <input type="password" id="txtPwd" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">性别：</label>
            <label class="radio-inline">
                <input type="radio" id="rbMale" name="rbSex" checked="true"
                       value="1"/>
                男
            </label>
            <label class="radio-inline">
                <input type="radio" id="rbFemale" name="rbSex" value="0"/>
                女
            </label>
        </div>
        <div class="common-row">
            <label class="field-label">邮箱：</label>
            <input id="txtMail" type="text" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">手机：</label>
            <input id="txtPhone" type="text" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">状态：</label>
            <label class="radio-inline">
                <input type="radio" id="rbEnable" name="rbState" value="1" checked="true"/>
                启用
            </label>
            <label class="radio-inline">
                <input type="radio" id="rbDisable" name="rbState" value="0"/>
                禁用
            </label>
        </div>
    </div>
</layout:layoutContent>

<layout:layoutContent name="scriptBlock">
    <script src="<%=staticCommonScriptPath%>/encodeHelper.js" type="text/javascript"></script>
    <script src="<%=staticBEScriptPath%>/user-list.js" type="text/javascript"></script>
</layout:layoutContent>

<jsp:include page="common/ajaxContentLayout.jsp"/>
