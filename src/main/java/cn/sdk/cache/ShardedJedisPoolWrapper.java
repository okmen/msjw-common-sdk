package cn.sdk.cache;

import org.apache.log4j.Logger;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardedJedisPoolWrapper {
	private Logger logger = Logger.getLogger(ShardedJedisPoolWrapper.class);
	private ShardedJedisPool shardedJedisPool;

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
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