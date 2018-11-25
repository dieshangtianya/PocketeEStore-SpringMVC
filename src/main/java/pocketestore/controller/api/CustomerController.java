package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pocketestore.config.StaticData;
import pocketestore.controller.BaseController;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.Customer;
import pocketestore.serviceimpl.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Map;


@Controller("APICustomerController")
@RequestMapping(value = "/api/customer")
public class CustomerController extends BaseController {
    /**
     * 客户登录接口
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/login/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        CustomerService customerService = new CustomerService();
        Customer customer = customerService.getUserByNameAndPassword(userName, password);

        if (customer != null) {
            HttpSession session = request.getSession();
            session.setAttribute(StaticData.KEY_SESSION_CUSTOMER_ID, customer.getId());
            return JsonResponseBuilder.buildSuccessResponse();
        } else {
            return JsonResponseBuilder.buildErrorResponse("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/register/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request) {
        String customerName = request.getParameter("customerName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setPassword(password);
        customer.setEmail(email);
        customer.setPhone(phone);

        CustomerService customerService = new CustomerService();
        boolean flag = customerService.registerCustomer(customer);
        if (flag) {
            return JsonResponseBuilder.buildSuccessResponse();
        } else {
            return JsonResponseBuilder.buildErrorResponse("注册失败");
        }
    }

    @RequestMapping(value = "/logout/v1")
    @ResponseBody
    public Map<String, Object> logout() {
        String customerIdKey = StaticData.KEY_SESSION_CUSTOMER_ID;
        this.removeFromSession(customerIdKey);
        return JsonResponseBuilder.buildSuccessResponse();
    }

    @RequestMapping(value = "/checkLogin/v1")
    @ResponseBody
    public Map<String, Object> checkLogin() {
        boolean isLogin = this.checkCustomerLogin();
        return JsonResponseBuilder.buildSuccessResponse(isLogin);
    }
}
