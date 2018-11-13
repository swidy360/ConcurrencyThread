package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-静态内部类写法
 * @author swidy360
 * @date 2018年11月13日 下午2:10:55   
 */
public class SingletonObject6 {

	private SingletonObject6() {
		
	}
	
	private static class InstanceHolder {
		private static final SingletonObject6 instance = new SingletonObject6();
	}
	
	public static SingletonObject6 getInstance() {
		return InstanceHolder.instance;
	}
}
