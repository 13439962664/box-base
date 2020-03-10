package com.box.config.db;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

	@Pointcut("!@annotation(com.box.config.db.Master) "
			+ "&& (execution(* com.box.*.service..*.select*(..)) "
			+ "|| execution(* com.box.*.service..*.find*(..))"
			+ "|| execution(* com.box.*.service..*.query*(..))"
			+ "|| execution(* com.box.*.service..*.get*(..)))")
	public void readPointcut() {

	}

	@Pointcut("@annotation(com.box.config.db.Master) "
			+ "|| execution(* com.box.*.service..*.insert*(..)) "
			+ "|| execution(* com.box.*.service..*.create*(..)) "
			+ "|| execution(* com.box.*.service..*.add*(..)) "
			+ "|| execution(* com.box.*.service..*.update*(..)) "
			+ "|| execution(* com.box.*.service..*.edit*(..)) "
			+ "|| execution(* com.box.*.service..*.delete*(..)) "
			+ "|| execution(* com.box.*.service..*.remove*(..))")
	public void writePointcut() {

	}

	@Before("readPointcut()")
	public void read() {
		DBContextHolder.slave();
	}

	@Before("writePointcut()")
	public void write() {
		DBContextHolder.master();
	}

	/**
	 * 另一种写法：if...else... 判断哪些需要读从数据库，其余的走主数据库
	 */
//    @Before("execution(* com.cjs.example.service.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        }else {
//            DBContextHolder.master();
//        }
//    }
}