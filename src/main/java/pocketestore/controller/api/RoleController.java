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
@RequestMapping(value = "/api/management/role")
public class RoleController {
    @RequestMapping(value = "/list/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> roleList(@RequestBody Map<String, Object> map) {
        int page = (int) map.get("page");
        int limit = (int) map.get("limit");
        IRoleService roleService = new RoleService();
        PaginationData<Role> paginationData = roleService.getRoles(page, limit);
        return JsonResponseBuilder.buildSuccessResponse(page, limit, paginationData.getTotal(), paginationData.getPageData());
    }

    @RequestMapping(value = "/add/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@RequestBody Role role) throws Exception {
        IRoleService roleService = new RoleService();
        boolean flag = roleService.addRole(role);
        if (flag) {
            return JsonResponseBuilder.buildSuccessResponse(true);
        } else {
            return JsonResponseBuilder.buildErrorResponse("新增角色失败");
        }
    }

    @RequestMapping(value = "/update/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@RequestBody Role role) throws Exception {
        IRoleService roleService = new RoleService();
        boolean flag = roleService.updateRole(role);
        if (flag) {
            return JsonResponseBuilder.buildSuccessResponse(true);
        } else {
            return JsonResponseBuilder.buildErrorResponse("更新角色失败");
        }
    }

    @RequestMapping(value = "/delete/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String id) throws Exception {
        IRoleService roleService = new RoleService();
        boolean flag = roleService.deleteRole(id);
        if (flag) {
            return JsonResponseBuilder.buildSuccessResponse(true);
        } else {
            return JsonResponseBuilder.buildErrorResponse("删除角色失败");
        }
    }
}
