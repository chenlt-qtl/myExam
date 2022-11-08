package com.lucence.bean;

import lombok.Data;

/**
 * 用来测试英文
 */
@Data
public class Sentence {

	private java.lang.String id;
	private java.lang.String content;
	private int idx;
	private java.util.Date createTime;

	@Override
	public String toString() {
		return "Sentence{" +
				"id='" + id + '\'' +
				", content='" + content + '\'' +
				", idx=" + idx +
				", createTime=" + createTime +
				'}';
	}
}
