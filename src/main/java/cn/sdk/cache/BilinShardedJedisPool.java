package cn.sdk.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import cn.sdk.util.StringUtil;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class BilinShardedJedisPool {
	private Logger logger = Logger.getLogger(BilinShardedJedisPool.class);
	private ShardedJedisPool shardedJedisPool;

	// 注入格式(name1:host1:port1,name2:host2:port2,name3:host3:port3)
	public BilinShardedJedisPool(final GenericObjectPoolConfig poolConfig,
			String configStr, int timeout,String password) {
		try {
			List<JedisShardInfo> infoList = new ArrayList<JedisShardInfo>();
			String[] configArr = configStr.split(",|，");
			for (String config : configArr) {
				String[] infoParams = config.split(":|：");
				if (infoParams.length != 3) {
					logger.error("redis配置格式非法:" + config);
				}
				JedisShardInfo shareInfo = new JedisShardInfo(infoParams[1],
						Integer.parseInt(infoParams[2]), timeout, infoParams[0]);
				if (StringUtil.isNotBlank(password)) {
					logger.info("jedis password :"+ password);
                    shareInfo.setPassword(password);
                }
				infoList.add(shareInfo);
			}
			logger.debug("load redis pool size:" + infoList.size());
			shardedJedisPool = new ShardedJedisPool(poolConfig, infoList);
		} catch (Exception e) {
			logger.error("redis配置格式非法:", e);
			throw new RuntimeException("redis配置格式非法:", e);
		}
	}

	public ShardedJedis getResource() {
		if (this.shardedJedisPool == null) {
			return null;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = this.shardedJedisPool.getResource();
		} catch (Exception e) {
			logger.debug("get jedisWrap from shardedJedisPool error", e);
		}
		return shardedJedis;
	}

	public void returnBrokenResource(final ShardedJedis resource) {
		if (resource != null) {
			this.shardedJedisPool.returnBrokenResource(resource);
		}
	}

	public void returnResource(final ShardedJedis resource) {
		if (resource == null) {
			return;
		}
		this.shardedJedisPool.returnResource(resource);
	}
}