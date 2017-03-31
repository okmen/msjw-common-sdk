package cn.sdk.serialization;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sdk.cache.UserBaseAttr;
import cn.sdk.cache.UserChangefulAttr;
import cn.sdk.serialization.KryoUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-sdk.xml",	"classpath:sdk-junit-test.xml" })
public class TestKryoUtils extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	@Qualifier("kryoUtil")
	private KryoUtil<Object> kryoUtil;

	@Autowired
	@Qualifier("kryoWithFactory")
	private KryoUtil<Object> kryoWithFactory;
	
	@Ignore
	@Test
	public void testNoFactory() throws Exception{
		long userId = 1000;
		UserBaseAttr baseAttr = createUserBaseAttr( userId );
		byte[] baseOT = kryoUtil.serialize( baseAttr );
		
		
		UserChangefulAttr changeAttr = new UserChangefulAttr( userId );
		byte[] changeOT = kryoUtil.serialize( changeAttr );
		
		
		UserBaseAttr newBase = (UserBaseAttr) kryoUtil.deserialize( baseOT, UserBaseAttr.class );
		UserChangefulAttr newChange = (UserChangefulAttr) kryoUtil.deserialize( changeOT, UserChangefulAttr.class );
		
		System.out.println( "testNoFactory, baseOT=" + baseOT.length + ", changeOT=" + changeOT.length );
		System.out.println( "testNoFactory, base=" + baseAttr );
		System.out.println( "testNoFactory, newBase" + newBase );
		System.out.println( "testNoFactory, change=" + changeAttr );
		System.out.println( "testNoFactory, newChange=" + newChange );
		Assert.assertTrue( newBase.getId() == baseAttr.getId() );
	}

	@Ignore
	@Test
	public void testWithFactory() throws Exception{
		long userId = 1000;
		UserBaseAttr baseAttr = createUserBaseAttr( userId );
		byte[] baseOT = kryoWithFactory.serialize( baseAttr );
		
		
		UserChangefulAttr changeAttr = new UserChangefulAttr( userId );
		byte[] changeOT = kryoWithFactory.serialize( changeAttr );
		
		
		UserBaseAttr newBase = (UserBaseAttr) kryoWithFactory.deserialize( baseOT, UserBaseAttr.class );
		UserChangefulAttr newChange = (UserChangefulAttr) kryoWithFactory.deserialize( changeOT, UserChangefulAttr.class );
		
		System.out.println( "testWithFactory, baseOT=" + baseOT.length + ", changeOT=" + changeOT.length );
		System.out.println( "testWithFactory, base=" + baseAttr );
		System.out.println( "testWithFactory, newBase" + newBase );
		System.out.println( "testWithFactory, change=" + changeAttr );
		System.out.println( "testWithFactory, newChange=" + newChange );
		Assert.assertTrue( newBase.getId() == baseAttr.getId() );
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