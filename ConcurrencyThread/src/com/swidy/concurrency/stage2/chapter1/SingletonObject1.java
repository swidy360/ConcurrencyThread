package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-懒汉模式
 * @author swidy360
 * @date 2018年11月13日 下午1:57:39   
 */
public class SingletonObject1 {

	private static final SingletonObject1 instance = new SingletonObject1();
	
	private SingletonObject1() {
		
	}
	
	public static SingletonObject1 getInstance() {
		return instance;
	}
	
}


