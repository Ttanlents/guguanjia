package com.yjf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 余俊锋
 * @date 2020/10/24 16:42
 * @Description
 */
@Controller
public class CommonController {

    @RequestMapping(value = "getNavBar",method = RequestMethod.GET)
    public ModelAndView getNavBar(){
        return new ModelAndView("/common/navbar.html");
    }

    @RequestMapping(value = "getSideBar",method = RequestMethod.GET)
    public ModelAndView getSideBar(){
        return new ModelAndView("/common/sidebar.html");
    }
}
