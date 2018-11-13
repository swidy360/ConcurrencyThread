package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-懒汉模式，需要的时候再创建，但是会存在线程不安全的情况
 * @author swidy360
 * @date 2018年11月13日 下午1:58:09   
 */
public class SingletonObject2 {

	private static SingletonObject2 instance;

	private SingletonObject2() {

	}

	public static SingletonObject2 getInstance() {
		if (instance == null) {
			instance = new SingletonObject2();
		}
		return SingletonObject2.instance;
	}

}
