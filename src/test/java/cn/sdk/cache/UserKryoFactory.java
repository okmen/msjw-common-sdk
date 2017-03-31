package cn.sdk.cache;

import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

public class UserKryoFactory implements KryoFactory{

	@Override
	public Kryo create() {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(true);
		kryo.register(Date.class, 30);
		// Use fastest possible serialization of object fields
		FieldSerializer<UserBaseAttr> base = new FieldSerializer<UserBaseAttr>(kryo, UserBaseAttr.class);
		base.setUseAsm(false);
		kryo.register(UserBaseAttr.class, base , 50);
		
		FieldSerializer<UserChangefulAttr> changeful = new FieldSerializer<UserChangefulAttr>(kryo, UserChangefulAttr.class);
		changeful.setUseAsm(false);
		kryo.register(UserChangefulAttr.class, changeful , 51);
		return kryo;
		
	}

}
