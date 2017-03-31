package cn.sdk.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;



import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sdk.cache.ICacheManger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-sdk.xml",
		"classpath:sdk-junit-test.xml" })
public class TestRedisOperation extends AbstractJUnit4SpringContextTests {
	@Resource
	private ICacheManger<TestBean> cacheManager;

	public ICacheManger<TestBean> getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(ICacheManger<TestBean> cacheManager) {
		this.cacheManager = cacheManager;
	}

	@BeforeClass
	public static void setup() {
		System.out.println("test cacheManger start");
	}

	@AfterClass
	public static void teardown() {
		System.out.println("test cacheManger end");
	}

	@Test
	public void testPresure() {
		String redisKey = SDKTestKey.TEST_BEAN_KEY;
		TestBean bean = new TestBean();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			cacheManager.setByKryo(redisKey, bean);
//			TestBean b2 = (TestBean) cacheManager.getByKryo(redisKey,
//					TestBean.class);
		}
		System.out.println(String.format("use time:%s ms", (System.currentTimeMillis()-start)));
	}

	//@Test
	public void testSaveBeanRedis() {
		TestBean bean = new TestBean();
		String name = "我是abc";
		bean.setName(name);
		bean.setSex(SexEnum.MALE);
		List<String> testList = new ArrayList<String>();
		String tStr1 = "haha";
		String tStr2 = "redis可以存list吗?";
		testList.add(tStr1);
		testList.add(tStr2);
		bean.setNameList(testList);

		// 统一访问key,注意key有统一的prefix前缀，避免redis空间污染
		String redisKey = SDKTestKey.TEST_BEAN_KEY;
		boolean result = cacheManager.set(redisKey, bean);

		Assert.assertEquals(result, true);

		TestBean b2 = cacheManager.get(redisKey);
		Assert.assertNotNull(b2);

		// 验证name
		Assert.assertEquals(name, b2.getName());

		// 验证sex
		// 枚举值使用，常量
		Assert.assertEquals(SexEnum.MALE, b2.getSex());

		// 验证list大小 和 list的值
		Assert.assertEquals(2, b2.getNameList().size());
		Assert.assertEquals(tStr2, b2.getNameList().get(1));
	}

}
