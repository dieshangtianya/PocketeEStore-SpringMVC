package pocketestore.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pocketestore.controller.ControllerHelper;
import pocketestore.model.Admin;
import pocketestore.model.MenuItem;
import pocketestore.model.Resource;
import pocketestore.service.IResourceService;
import pocketestore.serviceimpl.AdminService;
import pocketestore.serviceimpl.ResourceService;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.utils.MenuBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller("ManageHomeController")
@RequestMapping("/management")
public class HomeController {
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Admin admin = getCurrentAdmin(request);
        if (admin == null) {
            modelAndView.setViewName("be/login");
        } else {
            modelAndView.setViewName("be/index");
            modelAndView.addObject("adminInfo", admin);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("be/dashboard");
        return modelAndView;
    }


    private Admin getCurrentAdmin(HttpServletRequest request) {
        String adminId = ControllerHelper.getAdminIdFromSession(request);
        if (adminId != null) {
            AdminService adminService = new AdminService();
            return adminService.getAdminById(adminId);
        }
        return null;
    }
}
