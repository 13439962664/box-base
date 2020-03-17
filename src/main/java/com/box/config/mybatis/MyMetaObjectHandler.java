package com.box.config.mybatis;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.box.common.SysConstant;
import com.box.pojo.BasePojo;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill ....");
        BasePojo bp = (BasePojo)request.getAttribute(SysConstant.REQUEST_MY_INFO_ID);
    	if(bp!=null) {
    		this.strictInsertFill(metaObject, "createUser", Long.class, bp.getId());
    	}
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    	log.debug("start update fill ....");
    	
    	BasePojo bp = (BasePojo)request.getAttribute(SysConstant.REQUEST_MY_INFO_ID);
    	if(bp!=null) {
    		this.strictUpdateFill(metaObject, "lastUser", Long.class, bp.getId());
    	}
        this.strictUpdateFill(metaObject, "lastTime", Date.class, new Date());
    }
}