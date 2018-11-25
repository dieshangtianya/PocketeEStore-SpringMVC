package pocketestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("HomeController")
public class HomeController {
    @RequestMapping(value = {"/", "/home/index"}, method = RequestMethod.GET)//映射多个url
    public ModelAndView index(String keyword) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fe/index");
        if (keyword != null) {
            modelAndView.addObject("keyword", keyword);
        }
        return modelAndView;
    }
}
