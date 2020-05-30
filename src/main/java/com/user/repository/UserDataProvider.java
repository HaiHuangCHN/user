package com.user.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.entity.User;
import com.user.service.UserService;
import com.user.util.RedisCacheUtil;

/**
 * Created by yexin on 2017/9/8.
 *
 * 在Impl基础上+ 防止缓存雪崩和缓存穿透功能
 */
@Service /* (value = "userServiceImpl4") */
public class UserDataProvider {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    RedisCacheUtil redisCacheUtil;
//
//	@Value("${cache.default.expire-time}")
//	private Long defaultExpireTime;
//
//	public User getUser(Integer userId) {
//
//		String key = "user" + userId;
//		User user = (User) redisCacheUtil.getValueOfObject(key);
//		String keySign = key + "_sign";
//		String valueSign = redisCacheUtil.getValue(keySign);
//		if (user == null) {// 防止第一次查询时返回时空结果
//			// Avoid 缓存穿透
//			if (redisCacheUtil.exists(key)) {
//				return null;
//			}
//			user = userRepository.findByUserID(userId);
//
//			redisCacheUtil.set(key, user);
//			redisCacheUtil.set(keySign, "1", defaultExpireTime * (new Random().nextInt(10) + 1));
////            redisCacheUtil.set(keySign,"1",0L);  //过期时间不能设置为0，必须比0大的数
//			return user;
//		}
//
//		if (valueSign != null) {
//			return user;
//		} else {
//			// Set 设置标记的实效时间
//			Long tt = defaultExpireTime * (new Random().nextInt(10) + 1);
//			System.out.println("tt:" + tt);
//			redisCacheUtil.set(keySign, "1", tt);
//			// For 异步处理缓存更新 应对与高并发的情况，会产生脏读的情况
////			ThreadPoolUtil.getExecutorService().execute(new Runnable() {
////				public void run() { //
////					System.out.println("-----执行异步操作-----");
////					User user1 = userMapper.selectByPrimaryKey(userId);
////					redisCacheUtil.set(key, user1);
////				}
////			});
//
//			new Thread() {
//				public void run() { // 应对与高并发的情况，会产生脏读的情况
//					System.out.println("-----执行异步操作-----");
//					User user1 = userRepository.findByUserID(userId);
//					redisCacheUtil.set(key, user1);
//				}
//			}.start();
//		}
//		return user;
//	}	

    // bean name "keyGenerator" should exist, or will cause error
    @Cacheable(value = "users", key = "#userName")
    public User findByUserName(String userName) {
        log.info("Enter findByUserName method");
        User user = userRepository.findByUsername(userName);
        return user;
    }

    @CachePut(value = "users", key = "#user.userId")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public User saveOrUpdate(User user) {
        log.info("Enter saveOrUpdate method");
        User userSaved = userRepository.save(user);
        profileRepository.save(user.getProfile());
        addressRepository.saveAll(user.getProfile().getAddressList());
        return userSaved;
    }

    @CacheEvict(value = "users", key = "#user.userId")
    public void delete(User user) throws Exception {
//		log.info("Enter delete method");
//		redisCacheUtil.delete("users::" + user.getUsername());
//		if (6 / 3 == 2) {
//			throw new Exception();			
//		}
        userRepository.delete(user);
    }

    // method to simulate a slow response
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}