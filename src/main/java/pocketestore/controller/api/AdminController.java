package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pocketestore.config.StaticData;
import pocketestore.controller.ControllerHelper;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.Admin;
import pocketestore.model.PaginationData;
import pocketestore.service.IAdminService;
import pocketestore.serviceimpl.AdminService;

import javax.servlet.http.*;
import java.util.Map;

@Controller("APIAdminController")
@RequestMapping("api/management/admin")
public class AdminController {

    @RequestMapping(value = "/login/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        AdminService adminService = new AdminService();
        Admin admin = adminService.getAdminByNameAndPassword(userName, password);


        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute(StaticData.KEY_SESSION_ADMIN_ID, admin.getId());
            return JsonResponseBuilder.buildSuccessResponse();
        } else {
            return JsonResponseBuilder.buildErrorResponse("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/logout/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request) {
        String adminIDKey = StaticData.KEY_SESSION_ADMIN_ID;
        ControllerHelper.removeFromSession(request, adminIDKey);
        return JsonResponseBuilder.buildSuccessResponse();
    }

    @RequestMapping(value="/list/v1",method =RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resourceList(@RequestBody Map<String, Object> map) {
        int page = (int) map.get("page");
        int limit = (int) map.get("limit");
        IAdminService adminService = new AdminService();
        PaginationData<Admin> paginationData = adminService.getAdmins(page, limit);
        return JsonResponseBuilder.buildSuccessResponse(page,limit,paginationData.getTotal(),paginationData.getPageData());
    }
}
