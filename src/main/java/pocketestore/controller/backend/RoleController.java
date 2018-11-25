package pocketestore.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("ManagementRoleController")
@RequestMapping("/management/role")
public class RoleController {

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String list() {
        return "be/roleList";
    }
}
