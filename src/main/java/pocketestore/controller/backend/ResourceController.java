package pocketestore.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller("ManagementResourceController")
@RequestMapping("/management/feature")
public class ResourceController {
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("be/resourceList");
        return modelAndView;
    }
}
