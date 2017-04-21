package com.codeindexer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for the HTML form
 */
@Controller
public class WebController {
    /**
     * the default view for searching
     * @return the html template to render
     */
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String homepage(){
        return "search";
    }
}
