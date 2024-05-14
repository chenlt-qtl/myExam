package com.seed.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.seed.domain.SMSCode;
import com.seed.service.ISMSCodeService;
import com.seed.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SMSCodeServiceImpl implements ISMSCodeService {

    @Autowired
    CodeUtils codeUtils;

//    remote
//    @CreateCache(area = "sms", name = "myCache", expire = 10, timeUnit = TimeUnit.SECONDS)
    @CreateCache(name = "myLocalCache",cacheType = CacheType.LOCAL)
    private Cache<String, String> jetCache;


    @Override
    public String sendCodeToSMS(String tel) {
        String code = codeUtils.generator(tel);
        jetCache.put(tel, code);
        return code;
    }

    @Override
    public boolean checkCode(SMSCode smsCode) {
        String cacheCode = jetCache.get(smsCode.getTel());
        return smsCode.getCode().equals(cacheCode);
    }
}
