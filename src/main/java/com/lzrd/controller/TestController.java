package com.lzrd.controller;

import com.lzrd.Exception.LzrdException;
import com.lzrd.service.TestService;
import com.lzrd.util.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by zengtao on 2016/3/23.
 */

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "test", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String uploadAreaReply(@RequestBody String body) throws LzrdException {
        System.out.println(body);
        List<Map<String, Object>>  results =  testService.findAll();
        return CommonUtility.constructResultJson("0000", results);
    }


}
