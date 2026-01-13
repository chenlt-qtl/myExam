package com.exam.controller;


import com.exam.entity.DimAcctPrdStd;
import com.exam.service.DimAcctPrdStdService;

/**
 * @description: dim标准帐期维
 * @author: hithium
 * @date: 2025-08-26
 * @version: V1.3.2
 */

public class DimAcctPrdStdController {

	private DimAcctPrdStdService dimAcctPrdStdService;


	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回dim标准帐期维对象
	 */
	public Object queryById(String id) {
		DimAcctPrdStd dimAcctPrdStd = dimAcctPrdStdService.getById(id);
		return dimAcctPrdStd;
	}


}
