package com.seed.controller;

import com.seed.controller.utils.ResultBean;
import com.seed.domain.SMSCode;
import com.seed.service.ISMSCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SMSController {

    @Autowired
    private ISMSCodeService smsCodeService;


    @PostMapping
    public ResultBean check(SMSCode smsCode) {
        return ResultBean.success(smsCodeService.checkCode(smsCode));
    }


    @GetMapping("/{tel}")
    public ResultBean<String> get(@PathVariable String tel) {
        String code = smsCodeService.sendCodeToSMS(tel);
        return ResultBean.success(code);
    }

}
