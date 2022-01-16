package com.wq.resilence4j.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @PackageName:com.wq.controller
 * @ClassName:EurkaClientFeignContorller
 * @Description:
 * @author: wq
 * @date 2021/7/24 22:54
 */
@Controller
public class EurkaClientFeignContorller {

    @Autowired
    EurakClientFeignServiceImpl eurkaClientFeignService;

    @RequestMapping(value = "/callEurkaClient", method = RequestMethod.GET)
    @ResponseBody
    public String fileServerInfo(HttpServletResponse response) {
        eurkaClientFeignService.getInfo();
        return "EurkaClientFeignContorller";
    }

}
