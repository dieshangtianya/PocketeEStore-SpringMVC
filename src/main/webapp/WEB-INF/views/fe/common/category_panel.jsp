<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="pocketestore.model.CategorySet" %>
<%@ page import="pocketestore.model.Category" %>
<%@ page import="pocketestore.serviceimpl.CategorySetService" %>

<ul class="product-category-panel">
    <%
        CategorySetService categorySetService = new CategorySetService();
        List<CategorySet> categorySetList = categorySetService.getAllStandCategorySets();
        for (CategorySet categorySetItem : categorySetList) {
    %>
    <li>
        <%
            List<Category> categories = categorySetItem.getCategoryList();
            for (int index = 0; index < categories.size(); index++) {
        %>
        <a>
            <%=categories.get(index).getCategoryName()%>
        </a>
        <%
            if (index != categories.size() - 1) {
        %>
        &nbsp;/
        <%
            }
        %>
        <%
            }
        %>
    </li>
    <%
        }
    %>
</ul>
