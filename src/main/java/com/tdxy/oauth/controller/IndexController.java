package com.tdxy.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView manager() {
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("key", "tdxy");
        return modelAndView;
    }
}
