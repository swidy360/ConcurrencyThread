package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-懒汉模式，加synchronized锁，但是效率不高
 * 
 * @author swidy360
 * @date 2018年11月13日 下午1:58:29
 */
public class SingletonObject3 {

	private static SingletonObject3 instance;

	private SingletonObject3() {

	}

	public synchronized static SingletonObject3 getInstance() {
		if (instance == null) {
			instance = new SingletonObject3();
		}
		return SingletonObject3.instance;
	}

}
