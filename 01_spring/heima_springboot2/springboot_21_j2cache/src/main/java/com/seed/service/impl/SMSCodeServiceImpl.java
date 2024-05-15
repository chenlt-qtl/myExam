package com.seed.service.impl;

import com.seed.domain.SMSCode;
import com.seed.service.ISMSCodeService;
import com.seed.utils.CodeUtils;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSCodeServiceImpl implements ISMSCodeService {

    @Autowired
    CodeUtils codeUtils;


    @Autowired
    private CacheChannel channel;

    @Override
    public String sendCodeToSMS(String tel) {
        String code = codeUtils.generator(tel);
        channel.set("sms_",tel,code);
        return code;
    }

    @Override
    public boolean checkCode(SMSCode smsCode) {
        String cacheCode = channel.get("sms_",smsCode.getTel()).asString();
        return smsCode.getCode().equals(cacheCode);
    }
}
