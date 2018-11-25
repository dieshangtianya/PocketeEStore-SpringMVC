package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.PaginationData;
import pocketestore.model.Role;
import pocketestore.service.IRoleService;
import pocketestore.serviceimpl.RoleService;

import java.util.Map;

@Controller("APIRoleController")
@RequestMapping(value="/api/management/role")
public class RoleController {
    @RequestMapping(value = "/list/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resourceList(@RequestBody Map<String, Object> map) {
        int page = (int) map.get("page");
        int limit = (int) map.get("limit");
        IRoleService roleService = new RoleService();
        PaginationData<Role> paginationData = roleService.getRoles(page, limit);
        return JsonResponseBuilder.buildSuccessResponse(page,limit,paginationData.getTotal(),paginationData.getPageData());
    }
}
