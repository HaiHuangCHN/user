package com.user.config;
//package com.profile.config;
//
//import java.util.Collections;
//import java.util.Random;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.retry.RecoveryCallback;
//import org.springframework.retry.RetryCallback;
//import org.springframework.retry.RetryContext;
//import org.springframework.retry.backoff.FixedBackOffPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//import org.springframework.retry.support.RetryTemplate;
//
//public class RetryConfig {
//	private static Logger logger = LoggerFactory.getLogger(RetryConfig.class);
//
//	public static Boolean vpmsRetryCoupon(final String userId) {
//		RetryTemplate retryTemplate = new RetryTemplate();
//		// Set up RetryPolicy, mainly set up retry times
//		SimpleRetryPolicy policy = new SimpleRetryPolicy(10,
//				Collections.<Class<? extends Throwable>, Boolean>singletonMap(Exception.class, true));
//		// Set up设置重试回退操作策略，主要设置重试间隔时间
//		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
//		fixedBackOffPolicy.setBackOffPeriod(100);
//		retryTemplate.setRetryPolicy(policy);
//		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
//		// 通过RetryCallback 重试回调实例包装正常逻辑逻辑，第一次执行和重试执行执行的都是这段逻辑
//		final RetryCallback<Object, Exception> retryCallback = new RetryCallback<Object, Exception>() {
//			// RetryContext 重试操作上下文约定，统一spring-try包装
//			@Override
//			public Object doWithRetry(RetryContext context) throws Exception {
//				boolean result = pushCouponByVpmsaa(userId);
//				if (!result) {
//					throw new RuntimeException();// Attention should be paid here, 重试的根源通过Exception返回
//				}
//				return true;
//			}
//		};
//		// 通过RecoveryCallback 重试流程正常结束或者达到重试上限后的退出恢复操作实例
//		final RecoveryCallback<Object> recoveryCallback = new RecoveryCallback<Object>() {
//			@Override
//			public Object recover(RetryContext context) throws Exception {
//				logger.info("正在重试发券::::::::::::" + userId);
//				return null;
//			}
//		};
//		try {
//			//  retryTemplate call execute method 开始逻辑执行
//			retryTemplate.execute(retryCallback, recoveryCallback);
//		} catch (Exception e) {
//			logger.info("发券错误异常========" + e.getMessage());
//			e.printStackTrace();
//		}
//		return true;
//	}
//
//	public static void main(String[] args) {
//		vpmsRetryCoupon("43333");
//	}
//
//	public static Boolean pushCouponByVpmsaa(String userId) {
//		Random random = new Random();
//		int a = random.nextInt(10);
//		System.out.println("a is: " + a);
//		if (a == 8) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//}
