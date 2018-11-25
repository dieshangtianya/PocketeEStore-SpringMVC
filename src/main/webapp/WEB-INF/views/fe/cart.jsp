<%@ page import="pocketestore.model.Cart" %>
<%@ page import="pocketestore.model.CartItem" %>
<%@ page import="pocketestore.model.Product" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../globaldata.jsp" %>

<%
    Cart cart = (Cart) request.getAttribute("cartData");
%>

<layout:layoutContent name="librayStyleBlock">
    <%--<link rel="stylesheet" href="<%=staticLibraryPath%>/jquery.nice-number/jquery.nice-number.min.css" type="text/css"/>--%>
    <link rel="stylesheet" href="<%=staticLibraryPath%>/jquery.spinner/bootstrap-spinner.min.css" type="text/css"/>

</layout:layoutContent>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticFECssPath%>/cart.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="libraryScriptBlock">
    <%--<script src="<%=staticLibraryPath%>/jquery.nice-number/jquery.nice-number.js"></script>--%>
    <script src="<%=staticLibraryPath%>/jquery.spinner/jquery.spinner.js"></script>
</layout:layoutContent>

<layout:layoutContent name="pageScriptBlock">
    <script type="text/javascript" src="<%=staticFEScriptPath%>/cart.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/cart-detail.js"></script>
</layout:layoutContent>

<layout:layoutContent name="mainContent">
    <%DecimalFormat decimalFormat = new DecimalFormat("0.00");%>
    <div class="content-section">
        <div class="cart-content">
            <div class="cart-title">
                <span>全部商品</span>
            </div>
            <div class="cart-table">

                <div class="cart-table-header">
                    <div class="cell-name">商品</div>
                    <div class="cell-price">单价</div>
                    <div class="cell-quantity">数量</div>
                    <div class="cell-subtotal">小计</div>
                    <div class="cell-operation">操作</div>
                </div>
                <div class="cart-table-body">
                    <%
                        BigDecimal totalPrice = new BigDecimal(0);
                        for (CartItem cartItem : cart.getAllItems()) {
                            Product productItem = cartItem.getProduct();
                            BigDecimal subTotal = productItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
                            totalPrice = totalPrice.add(subTotal);
                    %>
                    <div class="cart-table-row">
                        <input type="hidden" value="<%=productItem.getId()%>"/>
                        <div class="cell-name"><%=productItem.getProductName()%>
                        </div>
                        <div class="cell-price">
                            ￥ <%=decimalFormat.format(productItem.getPrice())%>
                        </div>
                        <div class="cell-quantity">
                            <div data-trigger="spinner" class="i-spinner">
                                <button type="button" class="btn-decrement" data-spin="down">-</button>
                                <input class="txt-quantity" type="text" value="<%=cartItem.getQuantity()%>"
                                       data-rule="quantity">
                                <button type="button" class="btn-increment" data-spin="up">+</button>
                            </div>
                        </div>
                        <div class="cell-subtotal">
                            ￥ <%=decimalFormat.format(subTotal)%>
                        </div>
                        <div class="cell-operation">
                            <button class="btn-operation btn-delete">删除</button>
                            <button class="btn-operation btn-favourite">加入我的关注</button>
                        </div>
                    </div>
                    <%
                        }
                    %>
                    <%
                        if (cart.getAllItems().size() == 0) {
                    %>
                    <div class="empty-tip">您的购物车空空如也，<a href="/">赶快去购物吧!</a></div>
                    <%
                        }
                    %>
                </div>
                <%
                    if (cart.getAllItems().size() != 0) {
                %>
                <div class="cart-table-footer">
                    <div class="cart-table-row">
                        <div class="go-shopping">
                            <a href="/" class="button btn-go-shopping">返回购物</a>
                        </div>
                        <div class="cart-summary">
                            总计：<span>￥ <%=decimalFormat.format(totalPrice)%></span>
                        </div>
                        <div class="do-order">
                            <a id="btn-order" class="button btn-order">我要结算</a>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</layout:layoutContent>

<jsp:include page="common/feStreamlinedLayout.jsp"/>