package com.seed.service.impl;

import com.seed.domain.SMSCode;
import com.seed.service.ISMSCodeService;
import com.seed.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SMSCodeServiceImpl implements ISMSCodeService {

    @Autowired
    CodeUtils codeUtils;
    @Override
    @CachePut(value = "myCache",key = "#tel")
    public String sendCodeToSMS(String tel) {
        return codeUtils.generator(tel);
    }

    @Override
    public boolean checkCode(SMSCode smsCode) {
        String cacheCode = codeUtils.getCode(smsCode.getTel());
        return smsCode.getCode().equals(cacheCode);
    }
}
