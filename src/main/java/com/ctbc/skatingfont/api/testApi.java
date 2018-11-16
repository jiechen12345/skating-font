package com.ctbc.skatingfont.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by JieChen on 2018/11/12.
 */
@Controller

public class testApi {

    @RequestMapping(value ="/test", method = RequestMethod.POST )
    public String save(@RequestBody String test, Model model) throws IOException {
        String a=test+" OK!!";

        model.addAttribute("a", a);
        return "index";
    }
}
