<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../globaldata.jsp" %>

<div class="header-section">
    <span class="drawer-toggle" id="drawer-toggle"></span>
    <span class="header-product">WiWi商城管理系统</span>
    <div class="header-nav">
        <span>
             <a><i class="icon iconfont icon-user"></i></a>
        </span>
        <span>${adminInfo.getAdminName()}</span>
        <span id="linkLogout" class="icon iconfont icon-export logout" title="退出系统"></span>
    </div>
</div>
