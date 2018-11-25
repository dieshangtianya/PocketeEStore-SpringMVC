package pocketestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("CustomerController")
public class CustomerController {
    /**
     * request the page for customer to sign in
     * @return
     */
    @RequestMapping(value="/customer/signIn",method = RequestMethod.GET)
    public String signIn(){
        return "fe/login";
    }

    /**
     * request the page for customer to sign up
     * @return
     */
    @RequestMapping(value="/customer/signUp",method=RequestMethod.GET)
    public  String signUp(){
        return "fe/register";
    }
}

