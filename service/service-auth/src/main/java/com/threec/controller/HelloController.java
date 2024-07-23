package com.threec.controller;

import com.threec.service.SmsService;
import com.threec.tools.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 你好控制器
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/22 15:31
 */
@RequestMapping("hello")
@RestController
public class HelloController {

    @Resource
    SmsService service;
    @Value("${application.test}")
    private String test;

    @GetMapping("a")
    public R<Object> hello() {
        return R.ok(test);
    }

    @GetMapping("sms")
    public R<Object> hello2() {
        boolean b = service.SendSMSCaptcha();
        if (b) {
            return R.ok("hello2");
        }
        return R.error();
    }
}
