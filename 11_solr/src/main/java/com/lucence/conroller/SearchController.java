package com.lucence.conroller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {
    @RequestMapping
    public String query(String queryString, int page, String idxStr, Model model){

        return "";
    }
}
