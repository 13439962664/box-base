package com.box.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;

@Data
public abstract class BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date lastTime;
	@TableField(fill = FieldFill.INSERT)
	private Long createUser;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long lastUser;
	@TableLogic
	private Integer del_ = 0;
	@Version
	private Long version_ = 0L;

}
