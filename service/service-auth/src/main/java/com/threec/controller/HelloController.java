package com.threec.controller;

import com.threec.tools.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 你好控制器
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/22 15:31
 */
@RequestMapping("hello")
@RestController
public class HelloController {

    @GetMapping()
    public R<Object> hello() {
        return R.ok("hello");
    }
}
