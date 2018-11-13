package com.swidy.concurrency.stage2.chapter1;

/**
 * 单例-double check,但是会存在NullException异常隐患  
 * @author swidy360
 * @date 2018年11月13日 下午2:02:18   
 */
public class SingletonObject4 {

	private static SingletonObject4 instance;
	
	private SingletonObject4() {
		
	}
	
	// 1、memory = allocate() 分配对象的内存空间
    // 2、ctorInstance() 初始化对象
    // 3、instance = memory 设置instance指向刚分配的内存

    // JVM和cpu优化，发生了指令重排

    // 1、memory = allocate() 分配对象的内存空间
    // 3、instance = memory 设置instance指向刚分配的内存
    // 2、ctorInstance() 初始化对象
	
	public static SingletonObject4 getInstance() {
		if (instance == null) {
			synchronized(SingletonObject4.class) {
				if (instance == null) {
					instance = new SingletonObject4();
				}
			}
		}
		return SingletonObject4.instance;
	}
}
