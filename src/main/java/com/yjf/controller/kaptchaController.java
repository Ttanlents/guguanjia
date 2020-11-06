package com.yjf.controller;

import com.baomidou.kaptcha.Kaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 余俊锋
 * @date 2020/11/3 10:26
 * @Description
 */
@Controller
@RequestMapping("/kaptcha")
public class kaptchaController {
    @Autowired
    private Kaptcha kaptcha;

    @GetMapping("/render")
    public void render() {
        kaptcha.render();
    }

    @PostMapping("/valid")
    public void validDefaultTime(@RequestParam String code) {
        //default timeout 900 seconds  KAPTCHA_SESSION_KEY
        boolean validate = kaptcha.validate(code);
        System.out.println(validate);
    }

    @PostMapping("/validTime")
    public void validCustomTime(@RequestParam String code) {
        kaptcha.validate(code, 60);
    }


}
