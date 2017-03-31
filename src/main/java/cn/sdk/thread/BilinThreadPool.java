package cn.sdk.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BilinThreadPool {
	private int corePoolSize = 10;
	private int maxPoolSize = 100;
	ThreadPoolExecutor threadPool = null;

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public BilinThreadPool() {
		// 构造一个线程池
		threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L,
				TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(
						100000), new ThreadPoolExecutor.DiscardPolicy());
	}
	
	public void execute(Runnable r) {
		threadPool.execute(r);
	}
}
