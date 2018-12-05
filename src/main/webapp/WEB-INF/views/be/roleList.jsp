
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../globaldata.jsp"%>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>

<!----以下是AJAX布局内容---->
<layout:layoutContent name="contentBlock">
    <div id="toolbar">
        <button type="button" id="btnAddRole" class="btn btn-primary btn-operation">新增角色</button>
    </div>
    <table id="roleTable">
    </table>
    <!--新增窗口-->
    <div id="role-add-info" class="dialog-body-content">
        <div class="common-row">
            <label class="field-label">角色名称：</label>
            <input type="text" id="txtRoleName" class="form-control field-control"/>
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
    <script src="<%=staticBEScriptPath%>/role-list.js" type="text/javascript"></script>
</layout:layoutContent>

<jsp:include page="common/ajaxContentLayout.jsp"/>
