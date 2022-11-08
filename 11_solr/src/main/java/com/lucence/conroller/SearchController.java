package com.lucence.conroller;

import com.lucence.service.SearchService;
import com.lucence.vo.ResultModel;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class SearchController {

    @Autowired
    SearchService searchService;

    /**
     * 分页查询数据
     * @param queryString
     * @param page
     * @param idxStr
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String query(String queryString, Integer page, String idxStr, Model model) throws IOException, ParseException {

        if(page == null||page<=0){
            page = 1;
        }

        ResultModel resultModel = searchService.query(queryString, page, idxStr);
        model.addAttribute("result",resultModel);

        model.addAttribute("queryString",queryString);
        model.addAttribute("page",page);
        model.addAttribute("idxStr",idxStr);
        return "index";
    }
}
