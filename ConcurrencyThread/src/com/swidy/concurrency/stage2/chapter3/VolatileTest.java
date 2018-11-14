package com.swidy.concurrency.stage2.chapter3;

/**
 * 初识volatile关键字用例一
 * @author swidy360
 * @date 2018年11月13日 下午4:45:48   
 */
public class VolatileTest {

	/**java内存模型（JMM）知识点，主存----高速缓存 
	 * 缓存不一致问题，2种解决方案：
	 * 1、给数据总线（数据总线，地址总线，控制总线）加锁，cpu串行化，执行效率低
	 * 2、CPU高速缓存一致性协议，intel提出的MESI，核心思想，当cpu写入数据的时候，如果发现该变量被内存共享（也就是说，在其他cpu中也存在该变量的副本），
	 * 会发出一个信号，通知其他cpu该变量的缓存无效，需要重新到主内存获取。
	 */
	private static volatile int INIT_VALUE = 0; //两个线程的共享数据
	private final static int MAX_VALUE = 50;
	
	public static void main(String[] args) {
		new Thread(() -> {
			int localValue = INIT_VALUE;
			while (INIT_VALUE < MAX_VALUE) {
				if (localValue != INIT_VALUE) {
					System.out.printf("The value update to [%d]\n", INIT_VALUE);
					localValue = INIT_VALUE;
				}
			}
		}, "READER").start(); 
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				int localValue = INIT_VALUE;
				while (INIT_VALUE < MAX_VALUE) {
					System.out.printf("update the value to [%d]\n", ++localValue);
					INIT_VALUE =  localValue;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "UPDATE").start();
	}
}
