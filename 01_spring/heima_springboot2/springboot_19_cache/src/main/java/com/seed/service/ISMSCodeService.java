package com.seed.service;

import com.seed.domain.SMSCode;

public interface ISMSCodeService {

    public String sendCodeToSMS(String tel);

    public boolean checkCode(SMSCode smsCode);
}
