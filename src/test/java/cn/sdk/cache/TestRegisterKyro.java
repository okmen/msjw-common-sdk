package cn.sdk.cache;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

import cn.sdk.cache.JedisCacheManagerV20Impl;

/*** This test was originally taken from a GridGain blog. It is a compares the speed of serialization using Java serialization,
 * Kryo, Kryo with Unsafe patches and GridGain's serialization.
 * 
 * @author Roman Levenstein <romixlev@gmail.com> */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-sdk.xml",	"classpath:sdk-junit-test.xml" })
public class TestRegisterKyro extends AbstractJUnit4SpringContextTests {
	private static final int SLEEP_BETWEEN_RUNS = 300;
	private static final int OUTPUT_BUFFER_SIZE = 4096 * 10 * 4;
	private long oldTotal = 0;
	private long oldRead = 0;
	private long oldWrite = 0;
	private long newTotal = 0;
	private long newRead = 0;
	private long newWrite = 0;
	
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private JedisCacheManagerV20Impl<Object> jedisCacheManagerImpl;
	
	
	//@Test
	public void testCreateKryo() throws InterruptedException{
		long oldT = 0;
		long newT = 0;
		for (int i = 0; i < 100; i++) {
			long t1 = System.nanoTime();
			new Kryo();
			long t2 = System.nanoTime();
			long d1 = ( t2 - t1 );
			oldT += d1;
			getNewKryo();
			long t3 = System.nanoTime();
			long d2 = ( t3 - t2 );
			newT += d2;
			System.out.println("d1=" + d1 + ",d2=" + d2 );
		}
		oldT = TimeUnit.NANOSECONDS.toMillis( oldT );
		newT = TimeUnit.NANOSECONDS.toMillis( newT );
		System.out.println("oldTotal=" + oldT + ",newTotal=" + newT );
	}
	
	//@Test
	public void testKryoOld100() throws InterruptedException{
		for( int i = 0; i<= 50; i++ ){
			this.testKryoOld();
		}
	}

	//@Test
	public void testKryoNew100() throws InterruptedException{
		for( int i = 0; i<= 1; i++ ){
			this.testKryoNew();
		}
	}
	
	@Test
	public void testKryoOld() throws InterruptedException{
		Kryo writeK = new Kryo();
		Kryo readK = new Kryo();
		oldTotal = 0;
		oldRead = 0;
		oldWrite = 0;
		//先预热跑1000；
		for( int i = 0; i < 10000; i++  ){
			//Kryo writeK = new Kryo();
			//Kryo readK = new Kryo();
			UserBaseAttr obj = createUserBaseAttr( i );
			new UserChangefulAttr( i );  
			writeAndReadOld( writeK, readK,  obj, true );
//			/long t2 = writeAndReadOld( writeK, readK,  attr, true );
			//systemCleanupAfterRun();
		}
		
		System.out.println("预热结束, " + oldTotal + "," + oldWrite + "," + oldRead);
		
		//正式跑数据
		for( int i = 0; i < 10000; i++  ){
			//Kryo writeK = new Kryo();
			//Kryo readK = new Kryo();
			UserBaseAttr obj = createUserBaseAttr( 10000 + i );		
			long t = writeAndReadOld( writeK, readK,  obj, false);
			oldTotal += t;
			//System.out.println("t=" + t );
			//systemCleanupAfterRun();
		}
		
		oldTotal = TimeUnit.NANOSECONDS.toMillis( oldTotal );
		oldRead = TimeUnit.NANOSECONDS.toMillis( oldRead );
		oldWrite = TimeUnit.NANOSECONDS.toMillis( oldWrite );
		System.out.println("old total=" + oldTotal  + ",write=" + oldWrite + ",read=" + oldRead );
		//Assert.assertTrue( true );
	}	
	
	//@Test
	public void testKryoNew() throws InterruptedException{
		//Kryo writeK = new Kryo();
		//Kryo readK = new Kryo();
		Kryo writeK = getNewKryo();
		Kryo readK = getNewKryo();
		
		newTotal = 0;
		newRead = 0;
		newWrite = 0;
		for( int i = 0; i < 10000; i++  ){
			//Kryo writeK = getNewKryo();
			//Kryo readK = getNewKryo();
			UserBaseAttr obj = createUserBaseAttr( i );		
			writeAndRead( writeK, readK, obj, true );
		}
		
		System.out.println("预热结束," + newTotal + "," + newWrite + "," + newRead);
		
		for( int i = 0; i < 10000; i++  ){
			//Kryo writeK = getNewKryo();
			//Kryo readK = getNewKryo();
			UserBaseAttr obj = createUserBaseAttr( 20000 + i );		
			
			long t= writeAndRead( writeK, readK, obj, false );
			newTotal += t;
			//System.out.println("t=" + t );
			//systemCleanupAfterRun();
		}
		System.out.println("new1 total=" + newTotal  + ",write=" + newWrite + ",read=" + newRead );
		newTotal = TimeUnit.NANOSECONDS.toMillis( newTotal );
		newRead = TimeUnit.NANOSECONDS.toMillis( newRead );
		newWrite = TimeUnit.NANOSECONDS.toMillis( newWrite );
		System.out.println("new total=" + newTotal  + ",write=" + newWrite + ",read=" + newRead );
		//Assert.assertTrue( true );
		
	}
	
	
	private long writeAndRead( Kryo writeK, Kryo readK, UserBaseAttr obj, boolean isInit ){
		long t1 = System.nanoTime();
		//写入
		byte[] out = new byte[OUTPUT_BUFFER_SIZE ];
		Output kryoOut = new UnsafeOutput(out);
		writeK.writeObject(kryoOut, obj);
		kryoOut.close();
		
		long t2 = System.nanoTime();
		long writeT = t2 - t1;
		if( isInit == false ){
			newWrite += writeT;
			if( obj.getId() % 1000 == 1 ){
				System.out.println("new write, id=" + obj.getId() +",time=" + writeT / 1000000f );
			}
		}
		long t3 = System.nanoTime();
		//读
		Input kryoIn =  new UnsafeInput(kryoOut.getBuffer());
		UserBaseAttr newObj = readK.readObject(kryoIn, UserBaseAttr.class);
		kryoIn.close();
		
		long t4 = System.nanoTime();
		long readT = t4 -t3;
		if( isInit == false ){
			newRead += readT;
			if( obj.getId() % 1000 == 1 ){
				System.out.println("new read obj=" + obj.getId() +",new=" + newObj.getId()+ ",time=" + readT/1000000f);
				System.out.println("================================================");
			}
		}
		//System.out.println("w=" + newWrite + " read=" + newRead);
		return readT + writeT;
	}
	
	public long writeAndReadOld(Kryo writeK, Kryo readK, UserChangefulAttr attr, boolean isInit ){
		long t1 = System.nanoTime();
		byte[] data_ = null;
		//写入
		Output output = new Output(1, 4096);
		writeK.writeObject(output, attr);
		data_ = output.toBytes();
		output.flush();
		output.close();
		long t2 = System.nanoTime();
		long writeT = t2 - t1;
		if( isInit == false ){
			oldWrite += writeT;
			if( attr.getId() % 1000 == 1 ){
				System.out.println("old write, changful id=" + attr.getId() +",time=" + writeT / 1000000f );				
			}
		}
		
		//读
		long t3 = System.nanoTime();
		Input input = new Input(data_);
		UserBaseAttr newObj = readK.readObject(input, UserBaseAttr.class);
		input.close();
		long t4 = System.nanoTime();
		long readT = t4 -t3;
		if( isInit == false ){
			oldRead += readT;
			if( attr.getId() % 1000 == 1 ){
				System.out.println("old read changful=" + attr.getId() +",new=" + newObj.getId()+ ",time=" + readT/1000000f);
				System.out.println("================================================");
			}			
		}
		
		
		return writeT + readT;
	}
	
	private long writeAndReadOld(Kryo writeK, Kryo readK, UserBaseAttr obj, boolean isInit ){
		long t1 = System.nanoTime();
		byte[] data_ = null;
		//写入
		Output output = new Output(1, 4096);
		writeK.writeObject(output, obj);
		data_ = output.toBytes();
		output.flush();
		output.close();
		long t2 = System.nanoTime();
		long writeT = t2 - t1;
		if( isInit == false ){
			oldWrite += writeT;
			if( obj.getId() % 1000 == 1 ){
				System.out.println("old write, id=" + obj.getId() +",time=" + writeT / 1000000f );				
			}
		}
		
		//读
		long t3 = System.nanoTime();
		Input input = new Input(data_);
		UserBaseAttr newObj = readK.readObject(input, UserBaseAttr.class);
		input.close();
		long t4 = System.nanoTime();
		long readT = t4 -t3;
		if( isInit == false ){
			oldRead += readT;
			if( obj.getId() % 1000 == 1 ){
				System.out.println("old read obj=" + obj.getId() +",new=" + newObj.getId()+ ",time=" + readT/1000000f);
				System.out.println("================================================");
			}			
		}
		
		
		return writeT + readT;
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
	
	public void systemCleanupAfterRun () throws InterruptedException {
		System.gc();
		Thread.sleep(SLEEP_BETWEEN_RUNS);
		System.gc();
	}
	
	private Kryo getNewKryo(){
		Kryo marsh = new Kryo();
		marsh.setReferences(false);
		marsh.setRegistrationRequired(true);
		marsh.register(Date.class, 41);
		// Use fastest possible serialization of object fields
		FieldSerializer<UserBaseAttr> ser = new FieldSerializer<UserBaseAttr>(marsh, UserBaseAttr.class);
		ser.setUseAsm(false);
		marsh.register(UserBaseAttr.class, ser , 42);
		return marsh;
	}
	
	
	
}
