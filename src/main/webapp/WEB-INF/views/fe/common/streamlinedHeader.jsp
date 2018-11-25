<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pocketestore.config.StaticData" %>
<%@ page import="pocketestore.model.Customer" %>
<%@ page import="pocketestore.serviceimpl.CustomerService" %>

<%
    String customerID = null;
    Object customerSession = session.getAttribute(StaticData.KEY_SESSION_CUSTOMER_ID);
    if (customerSession != null) {
        customerID = customerSession.toString();
    }
    Customer currentCustomer = null;
    String customerName = "";
    if (customerID != null) {
        CustomerService customerService = new CustomerService();
        currentCustomer = customerService.getCustomerById(customerID);
        if (currentCustomer != null) {
            customerName = currentCustomer.getCustomerName();
        }
    }

%>

<section class="app-header">
    <div class="top-bar clearfix">
        <div class="top-bar-nav">
            <a>小微商城</a>
            <span class="nav-separator">|</span>
            <a>微萌金融</a>
            <span class="nav-separator">|</span>
            <a>企业采购</a>
            <span class="nav-separator">|</span>
            <a>客户服务</a>
            <span class="nav-separator">|</span>
            <a>App下载</a>
        </div>
        <div class="top-bar-info">
            <%
                if (customerID != null) {
            %>
            <span>您好，<%= customerName%></span>
            <a id="btn-exist" class="btn-exist">退出</a>
            <%
            } else {
            %>
            <a href="/customer/signIn">登录</a>
            <span class="nav-separator">|</span>
            <a href="/customer/signUp">注册</a>
            <%
                }
            %>
            <span class="nav-separator">|</span>
            <a>我的小微</a>
        </div>
    </div>
    <div class="search-bar">
        <div class="search-box-wrapper">
            <div class="search-box">
                <input type="text" class="search-input" placeholder="请输入商品关键字"/>
            </div>
            <button class="search-button">
                <span class="icon-search"/>
                <span>搜索</span>
            </button>
        </div>
    </div>

</section>