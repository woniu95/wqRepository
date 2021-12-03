package com.wq.integration.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName:PACKAGE_NAME
 * @ClassName:TestController
 * @Description:
 * @author: wq
 * @date 2021/11/10 22:26
 */
@RestController
public class TestController {

    @RequestMapping(value = "/testGet", method = RequestMethod.GET)
    public String testGet(Pojo pojo){
        return pojo.toString();
    }

    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    public String testPost( Pojo pojo){
        return pojo.toString();
    }
}
