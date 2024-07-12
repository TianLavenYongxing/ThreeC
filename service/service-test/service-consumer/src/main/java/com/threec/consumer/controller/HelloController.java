package com.threec.consumer.controller;

import com.threec.tools.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class HelloCnotroller.
 * <p>
 * hello
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 18/6/24
 */
@RequestMapping("hello")
@RestController
public class HelloController {

    @GetMapping()
    public R<String> hello() {
        return R.ok("hello");
    }
}
