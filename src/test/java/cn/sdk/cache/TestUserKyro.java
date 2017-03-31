package cn.sdk.cache;


import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sdk.cache.JedisCacheManagerV20Impl;

/*** This test was originally taken from a GridGain blog. It is a compares the speed of serialization using Java serialization,
 * Kryo, Kryo with Unsafe patches and GridGain's serialization.
 * 
 * @author Roman Levenstein <romixlev@gmail.com> */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-sdk.xml",	"classpath:sdk-junit-test.xml" })
public class TestUserKyro extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private JedisCacheManagerV20Impl<Object> jedisCacheManagerImpl;
	
	//@Test
	public void testOldUser(){
		long userId = 1000;
		UserBaseAttr bAttr1 = createUserBaseAttr( userId );
		String baseKey = "BaseAttr:" + userId;
		jedisCacheManagerImpl.setByKryo( baseKey , bAttr1 );
		
		UserBaseAttr bAttr2 = (UserBaseAttr) jedisCacheManagerImpl.getByKryo( baseKey, UserBaseAttr.class );
		System.out.println("test old user, " + bAttr1.getId() + "," + bAttr2 );
		//System.out.println("test old user, " + bAttr1.getId() + "," + bAttr2.getId() );
		Assert.assertTrue( bAttr2 != null );
	}

	//@Test
	public void testNewUser(){
		System.out.println("test new user");
		long userId = 1001;
		UserBaseAttr bAttr1 = createUserBaseAttr( userId );
		String baseKey = "BaseAttr:" + userId;
		jedisCacheManagerImpl.setByRegisterKryo( baseKey , bAttr1 );
		
		UserBaseAttr bAttr2 = (UserBaseAttr) jedisCacheManagerImpl.getByRegisterKryo( baseKey, UserBaseAttr.class );
		System.out.println("test new user, " + bAttr1.getId() + "," + bAttr2 );
		Assert.assertTrue( bAttr2 != null );
	}

	//@Test
	public void compareOldAndNewUserByMget(){
		//init data
		int userNum = 1000;
		String[] oldkeys = new String[userNum];
		String[] newkeys = new String[userNum];
		for ( int i=0; i <userNum; i++ ) {
			long userId = i + 1;
			UserBaseAttr u = createUserBaseAttr( userId );
			String oldKey = "OldUAttr:" + userId;
			String newKey = "NewUAttr:" + userId;
			jedisCacheManagerImpl.setByKryo( oldKey , u );
			jedisCacheManagerImpl.setByRegisterKryo( newKey , u );
			oldkeys[i] = oldKey;
			newkeys[i] = newKey;
		}
		
		long t1 = System.nanoTime();
		Map<String, Object> oldUsers = jedisCacheManagerImpl.mgetByKryo( UserBaseAttr.class, oldkeys );
		long t2 = System.nanoTime();
		Map<String, Object> newUsers = jedisCacheManagerImpl.mgetByRegisterKryo( UserBaseAttr.class, newkeys );
		long t3 = System.nanoTime();	
		long oldT = t2 - t1;
		long newT = t3 - t2;
		oldT = TimeUnit.NANOSECONDS.toMillis( oldT );
		newT = TimeUnit.NANOSECONDS.toMillis( newT );
		System.out.println("old time= " + oldT  + ",new time=" + newT );
		System.out.println("old users= " + oldUsers.size() + ",new uses=" + newUsers.size());
		Assert.assertTrue( oldUsers.size() == newUsers.size() );
		
	}	
	
	@Test
	public void compareOldAndNewUserByGet(){
		int userNum = 1000;
		for ( int i=0; i <userNum; i++ ) {
			long userId = i + 1;
			UserBaseAttr u = createUserBaseAttr( userId );
			String oldKey = "OldUAttr:" + userId;
			String newKey = "NewUAttr:" + userId;
			jedisCacheManagerImpl.setByKryo( oldKey , u );
			jedisCacheManagerImpl.setByRegisterKryo( newKey , u );
		}

		long oldTotal = 0;
		long newTotal = 0;
		for( int i =0;i < userNum; i++ ){
			long userId = i + 1;
			String oldKey = "OldUAttr:" + userId;
			String newKey = "NewUAttr:" + userId;
			long b1 = System.nanoTime();
			jedisCacheManagerImpl.getByKryo( oldKey, UserBaseAttr.class );
			long b2 = System.nanoTime();
			jedisCacheManagerImpl.getByRegisterKryo( newKey, UserBaseAttr.class );
			long b3 = System.nanoTime();
			long oldT = b2 - b1;
			long newT = b3 - b2;
			oldTotal += oldT;
			newTotal += newT;
		}
		
		oldTotal = TimeUnit.NANOSECONDS.toMillis( oldTotal );
		newTotal = TimeUnit.NANOSECONDS.toMillis( newTotal );
		System.out.println("oldTotal=" + oldTotal + ",newTotal=" + newTotal );
		
		
	}
	


	private UserBaseAttr createUserBaseAttr( long userId ){
		UserBaseAttr attr = new UserBaseAttr( userId );
		attr.setBigHeadImgId(10);
		attr.setBigHeadUrl("http://www.baidu.com.big_" + userId );
		attr.setBirthday( new Date());
		attr.setCareer("career_" + userId );
		attr.setClientType("IPHONE" + userId );
		attr.setCreateOn( new Date());
		attr.setDeviceTokens("deviceToken" + userId );
		attr.setEnabled(1);
		attr.setGroupId(0);
		attr.setJpushId("jpushId" + userId);
		attr.setMiPushId("miPushId" + userId );
		attr.setNickname("nickname" + userId );
		attr.setOsVersion("3.7.0" + userId);
		attr.setPassword("password" + userId );
		attr.setPhotoNum(1);
		attr.setPlatform("platform" + userId );
		attr.setSetupMark("setupmark" + userId);
		attr.setSexOrientation(1);
		attr.setShowSex(1);
		attr.setSign("sign" + userId);
		attr.setSmallHeadUrl("http://www.baidu.com.small" + userId );
		attr.setToken("token" + userId);
		attr.setUsername("username" + userId);
		attr.setVersion("3.7.0" + userId );
		return attr;
	}
	
}