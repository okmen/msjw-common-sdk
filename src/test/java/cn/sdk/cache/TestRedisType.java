package cn.sdk.cache;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sdk.cache.ICacheManger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-sdk.xml",
		"classpath:sdk-junit-test.xml" })
public class TestRedisType extends AbstractJUnit4SpringContextTests {
	@Resource
	private ICacheManger<String> cacheManager;

	@SuppressWarnings("rawtypes")
	public ICacheManger getCacheManager() {
		return cacheManager;
	}

	@SuppressWarnings("unchecked")
	public void setCacheManager(@SuppressWarnings("rawtypes") ICacheManger cacheManager) {
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

	@Ignore
	@Test
	public void testPresure() throws UnsupportedEncodingException {
		String key = "testkey";
		String obj = "testvalue";
		cacheManager.setByKryo(key, obj);
		
		String key2 = "testkey2";
		String key3 = "testkey3";
		String key4 = "testkey4";
		String value = "testvalue";
		cacheManager.setString(key2,value);
		cacheManager.set(key3, value);
		//cacheManager.set(key4, value.getBytes("UTF-8"));
		
		assertEquals(cacheManager.getString(key4),cacheManager.getString(key2));
		
		assertNotEquals(cacheManager.getString(key), cacheManager.get(key3));
		assertNotEquals(cacheManager.getString(key), cacheManager.get(key4));
		assertNotEquals(cacheManager.getString(key), cacheManager.getString(key2));
		
	}

}
