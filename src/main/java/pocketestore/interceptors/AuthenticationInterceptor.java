package pocketestore.interceptors;


import pocketestore.config.StaticData;
import pocketestore.infrastructure.utils.RequestHelper;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static String feLoginPageUrl = "/customer/signIn";
    private static String beLoginPageUrl = "/management/admin/signIn";
    private String requestUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // check if user has login before processing the current request
        String requestUrl = request.getRequestURI();

        String sessionKey = StaticData.KEY_SESSION_CUSTOMER_ID;
        String loginPage = feLoginPageUrl;
        if (isUrlBelongToBackendManagement(requestUrl)) {
            sessionKey = StaticData.KEY_SESSION_ADMIN_ID;
            loginPage = beLoginPageUrl;
        }

        HttpSession session = request.getSession();
        String identityValue = (String) session.getAttribute(sessionKey);
        if (identityValue == null || identityValue.isEmpty()) {
            try {
                if (isUrlBelongToAPI(requestUrl) || RequestHelper.isAjaxRequest(request)) {
                    response.sendError(401);
                } else {
                    response.sendRedirect(loginPage + "?returnUrl=" + URLEncoder.encode(requestUrl));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        return true;
    }

    private boolean isUrlBelongToBackendManagement(String requestUrl) {
        // if the request url start with "management", that to say it is the back end page
        // or it will be from the web front end page
        if (requestUrl.toLowerCase().startsWith("/management") ||
                requestUrl.toLowerCase().startsWith("/api/management")) {
            return true;
        }
        return false;
    }

    private boolean isUrlBelongToAPI(String requestUrl) {
        if (requestUrl.toLowerCase().startsWith("/api/")) {
            return true;
        }
        return false;
    }
}
