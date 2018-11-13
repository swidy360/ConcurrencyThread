package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-double check + volatile
 * @author swidy360
 * @date 2018年11月13日 下午2:06:07   
 */
public class SingletonObject5 {
	
	// 单例对象 volatile + 双重检测机制 -> 禁止指令重排
	private static volatile SingletonObject5 instance;
	
	private SingletonObject5() {
		
	}
	
	public static SingletonObject5 getInstance() {
		if (instance == null) {
			synchronized(SingletonObject5.class) {
				if(instance == null) {
					instance = new SingletonObject5();
				}
			}
		}
		return SingletonObject5.instance;
	}

}
