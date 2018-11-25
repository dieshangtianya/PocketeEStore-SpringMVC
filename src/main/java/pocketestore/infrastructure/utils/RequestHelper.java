package pocketestore.infrastructure.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestWithHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(requestWithHeader) ? true : false;
        return isAjax;
    }
}
