package com.box.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSONObject;
import com.box.config.CodeConfig;

public class JsonUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	private static String success;
	private static String error;
	
	private static Environment env;

	private static Environment getEnv() {
		if (env == null) {
			Environment env = (Environment) ApplicationContextHolder.getBean("env");
			JsonUtil.env = env;
		}
		return env;
	}
	
	public static String controllerErrorJson(Object obj) {
		if(error==null) {
			error = JsonUtil.getEnv().getProperty(CodeConfig.error);
		}
		return controllerReturnJson(error, obj);
	}
	
	public static String controllerSuccessJson(Object obj) {
		if(success==null) {
			success = JsonUtil.getEnv().getProperty(CodeConfig.success);
		}
		return controllerReturnJson(success, obj);
	}

	public static String controllerReturnJson(String code, Object obj) {
		String json = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("obj", obj);
		json = JSONObject.toJSONString(map);
		log.debug("controllerReturnJson--->" + json);
		return json;
	}
}
