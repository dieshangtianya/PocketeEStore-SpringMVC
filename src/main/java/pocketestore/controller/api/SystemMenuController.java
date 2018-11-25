package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pocketestore.controller.ControllerHelper;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.MenuItem;
import pocketestore.model.Resource;
import pocketestore.service.IResourceService;
import pocketestore.serviceimpl.ResourceService;
import pocketestore.utils.MenuBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller("APISystemMenuController")
@RequestMapping("/api/management/menu/")
public class SystemMenuController {
    @RequestMapping(value = "/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMenus(HttpServletRequest request) {
        IResourceService resourceService = new ResourceService();
        String adminId = ControllerHelper.getAdminIdFromSession(request);
        List<Resource> resourceList = resourceService.getMenuResourceByAdminId(adminId);
        List<MenuItem> menu = MenuBuilder.buildMenuFromResourceList(resourceList);

        return JsonResponseBuilder.buildSuccessResponse(menu.toArray());
    }
}
