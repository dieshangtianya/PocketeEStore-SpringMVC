package pocketestore.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller("ManagementAdminController")
@RequestMapping(value="/management/admin")
public class AdminController {

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signIn() {
        return "be/login";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "be/userList";
    }
}
