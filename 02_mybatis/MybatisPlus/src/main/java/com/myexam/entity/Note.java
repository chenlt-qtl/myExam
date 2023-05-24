package com.myexam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 笔记管理
 * @author： chenlt
 * @date： 2019-04-23
 * @version： V1.0
 */
@Data
@TableName("note_info")
@Slf4j
public class Note implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * contentId
     */

    private Long contentId;
    /**
     * name
     */
    private String name;
    /**
     * pId
     */
    private Long parentId;
    /**
     * pId
     */
    private String parentIds;
    /**
     * tag
     */
    private String tag;
    /**
     * from
     */
    private String source;

    private Boolean isLeaf;
    /**
     * createTime
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * createBy
     */
    private String createBy;
    /**
     * updateTime
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * updateBy
     */
    private String updateBy;


}
