<%@ page import="pocketestore.model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../globaldata.jsp" %>

<%
    Product product = (Product) request.getAttribute("productInfo");
%>

<layout:layoutContent name="librayStyleBlock">
    <link rel="stylesheet" href="<%=staticLibraryPath%>/jquery.nice-number/jquery.nice-number.min.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticFECssPath%>/product.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="libraryScriptBlock">
    <script type="text/javascript"
            src="<%=staticLibraryPath%>/jquery.nice-number/jquery.nice-number.min.js"></script>
</layout:layoutContent>

<layout:layoutContent name="pageScriptBlock">
    <script type="text/javascript" src="<%=staticFEScriptPath%>/cart.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/product-detail.js"></script>
</layout:layoutContent>

<layout:layoutContent name="mainContent">
    <div class="content-section">
        <% if (product == null) {
        %>
        <div class="not-found">
            <img src="/static/images/sorry-not-found.png"/>
            <span>找不到该商品信息!</span>
        </div>
        <%} else {%>
        <div class="product-detail clearfix">
            <div class="big-img-block">
                <div class="img-wrapper">
                    <img src="<%=product.getThumbnail()%>"/>
                </div>
            </div>
            <div class="info-block">
                <div class="info-data">
                    <div class="product-title">
                        <input id="hiddenInputProductId" value="<%=product.getId()%>" type="hidden"/>
                        <%=product.getProductName()%>
                    </div>
                    <div class="field">
                        <span class="field-label">品牌:</span>
                        <span class="field-value"><%=product.getBrandName()%></span>
                    </div>
                    <div class="field">
                        <span class="field-label">产地:</span>
                        <span class="field-value"><%=product.getHabitat()%></span>
                    </div>
                    <div class="field">
                        <span class="field-label price">￥<%=product.getPrice()%></span>
                    </div>
                </div>
                <div class="info-operation">
                    <input class="counter-input" value="1"/>
                </div>
                <div class="info-operation">
                    <button class="button btn-add-cart">加入购物车</button>
                </div>
            </div>
        </div>
    </div>
    <%}%>
    </div>
</layout:layoutContent>

<jsp:include page="common/feLayout.jsp"/>