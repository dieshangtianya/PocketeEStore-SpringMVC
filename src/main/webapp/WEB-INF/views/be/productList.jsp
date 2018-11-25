<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../globaldata.jsp" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>

<!----以下是AJAX布局内容---->
<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticBECssPath%>/product-list.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="contentBlock">
    <div id="toolbar">
        <button type="button" id="btnAddProduct" class="btn btn-primary btn-operation">
            新增商品
        </button>
    </div>
    <table id="productTable" style="width:100%">
    </table>
    <!--新增窗口-->
    <div id="product-add-info" class="dialog-body-content">
        <div class="common-row">
            <label class="field-label">商品名称：</label>
            <input type="text" id="txtProductName" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">品牌：</label>
            <input type="text" id="txtBrandName" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">单价：</label>
            <input type="number" min="0" id="txtProductPrice" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">缩略图：</label>
            <input id="fileProductThumbnail" type="file" class="form-control-file field-control"/>
            <img id="imgThumbnailPreview" class="product-thumbnail"/>
        </div>
        <div class="common-row">
            <label class="field-label">产地：</label>
            <input type="text" id="txtHabitat" class="form-control field-control"/>
        </div>
        <div class="common-row">
            <label class="field-label">标签：</label>
            <div class="field-control">
                <input id="product-tag" class="form-control"/>
            </div>
        </div>
        <div class="common-row">
            <label class="field-label">状态：</label>
            <label class="radio-inline">
                <input type="radio" name="radioState" id="radioEnable" checked value="1"> 启用
            </label>
            <label class="radio-inline">
                <input type="radio" name="radioState" id="radioDisable" value="0"> 禁用
            </label>
        </div>
        <div class="common-row">
            <label class="field-label textArea-lable">描述：</label>
            <textarea id="txtDescription" class="form-control field-control"></textarea>
        </div>
    </div>
</layout:layoutContent>

<layout:layoutContent name="scriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/product-list.js"></script>
    <script>
        console.log("静态执行");
    </script>
</layout:layoutContent>

<jsp:include page="common/ajaxContentLayout.jsp"/>


