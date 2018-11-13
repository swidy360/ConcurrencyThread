package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-枚举写法,推荐，安全
 * @author swidy360
 * @date 2018年11月13日 下午2:14:56
 */
public class SingletonObject7 {

	private SingletonObject7() {

	}
	
	public static SingletonObject7 getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	private enum Singleton {
		INSTANCE;

		private final SingletonObject7 instance;

		// JVM保证这个方法绝对只调用一次
		Singleton() {
			instance = new SingletonObject7();
		}
		
		public SingletonObject7 getInstance() {
			return instance;
		}
	}
}
