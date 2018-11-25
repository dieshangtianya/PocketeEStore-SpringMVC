package pocketestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pocketestore.config.StaticData;
import pocketestore.infrastructure.IUserSession;
import pocketestore.model.Cart;

import javax.servlet.http.*;

public class BaseController {
    protected Logger logger = LogManager.getLogger("operationLog");

    //对于HttpServletRequest和HttpServletResponse对象，也都可以通过RequestContextHolder进行创建
    @Autowired
    protected HttpServletRequest currentRequest;

    @Autowired(required = false)
    protected HttpServletResponse currentResponse;

    @Autowired
    protected IUserSession userSession;


    public String getAdminIdFromSession() {
        return userSession.getAdminIdFromSession();
    }

    public String getCustomerIdFromSession() {
        return userSession.getCustomerIdFromSession();
    }

    public Cart getCartFromSession() {
        return userSession.getCartFromSession();
    }

    public Object getDataFromSession(String objectKey) {
        return userSession.getDataFromSession(objectKey);
    }

    public void removeFromSession(String objectKey) {
        userSession.removeDataFromSession(objectKey);
    }

    public void addDataToSession(String objectKey, Object object) {
        userSession.addDataToSession(objectKey, object);
    }

    public boolean checkCustomerLogin() {
        return userSession.checkCustomerLogin();
    }
}
