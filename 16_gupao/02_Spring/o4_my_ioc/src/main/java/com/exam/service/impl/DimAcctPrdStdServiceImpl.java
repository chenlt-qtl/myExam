package com.exam.service.impl;

import com.exam.entity.DimAcctPrdStd;
import com.exam.mapper.DimAcctPrdStdMapper;
import com.exam.service.DimAcctPrdStdService;

/**
 * @description: dim标准帐期维
 * @author: mfish
 * @date: 2025-08-26
 * @version: V1.3.2
 */

public class DimAcctPrdStdServiceImpl implements DimAcctPrdStdService {

    DimAcctPrdStdMapper dimAcctPrdStdMapper;

    @Override
    public DimAcctPrdStd getById(String id) {
        return dimAcctPrdStdMapper.getById(id);
    }
}
