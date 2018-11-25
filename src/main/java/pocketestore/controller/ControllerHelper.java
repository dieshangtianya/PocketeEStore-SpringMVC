package pocketestore.controller;

import pocketestore.config.StaticData;
import pocketestore.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ControllerHelper {
    public static String getAdminIdFromSession(HttpServletRequest request) {
        String adminId = null;
        Object adminIdObject = ControllerHelper.getDataFromSession(request, StaticData.KEY_SESSION_ADMIN_ID);
        if (adminIdObject != null) {
            adminId = adminIdObject.toString();
        }
        return adminId;
    }

    public static String getCustomerIdFromSession(HttpServletRequest request) {
        String customerId = null;
        Object customerIdObject = ControllerHelper.getDataFromSession(request, StaticData.KEY_SESSION_CUSTOMER_ID);
        if (customerIdObject != null) {
            customerId = customerIdObject.toString();
        }
        return customerId;
    }

    public static Cart getCartFromSession(HttpServletRequest request) {
        Cart cartInfo = null;
        Object cartObject = ControllerHelper.getDataFromSession(request, StaticData.Key_SESSION_CART);
        if (cartObject != null) {
            cartInfo = (Cart) cartObject;
        }
        return cartInfo;
    }

    public static Object getDataFromSession(HttpServletRequest request, String objectKey) {
        HttpSession session = request.getSession();
        Object object = session.getAttribute(objectKey);
        return object;
    }

    public static void removeFromSession(HttpServletRequest request, String objectKey) {
        HttpSession session = request.getSession();
        session.removeAttribute(objectKey);
    }

    public static void addDataToSession(HttpServletRequest request, String objectKey, Object object) {
        HttpSession session = request.getSession();
        session.setAttribute(objectKey, object);
    }
}
