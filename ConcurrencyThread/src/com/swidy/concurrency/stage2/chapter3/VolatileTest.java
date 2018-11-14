package com.swidy.concurrency.stage2.chapter3;

/**
 * 初识volatile关键字用例一
 * 涉及到的知识点
 * 1、CPU的大致结构
 * 2、JMM的内存模型
 * 3、缓存一致性协议
 * 4、指令重排序
 * 5、happen-before原则
 * 6、并发编程的三大要素
 * 7、volatile关键字的使用场景
 * 
 * 一旦一个共享变量被volatile修饰，具备两层语义
 * 1、保证不同线程间的可见性
 * 2、禁止对其进行指令重排序，也就保证了有序性
 * 3、并未保证原子性
 * 
 * volatile关键字
 * 1、保证重排序的时候不会把后面的指令放到屏障前面，也不会把前面的放到后面
 * 2、强制对缓存的修改操作立刻写入主存
 * 3、如果是写操作，会导致其他cpu中的缓存失效
 * 
 * volatile使用场景
 * 1、状态标记量
 * volatile boolean start = true;
 * while(start){
 * 		//
 * }
 * void close(){
 * 		start = false;
 * }
 * 2、屏障前后的一致性(单例double check)
 * volatile boolean init;
 * ---------Thread-1------------
 * obj = createObj();			1;
 * init = true;					2;
 * ---------Thread-2------------
 * while(!init){
 * 		sleep(1);
 * }
 * useTheObj(obj);
 * -----------------------------
 * 
 * @author swidy360
 * @date 2018年11月13日 下午4:45:48   
 */
public class VolatileTest {

	/**java内存模型（JMM）知识点，主存----高速缓存 
	 * 缓存不一致问题，2种解决方案：
	 * 1、给数据总线（数据总线，地址总线，控制总线）加锁，cpu串行化，执行效率低
	 * 2、CPU高速缓存一致性协议，intel提出的MESI，核心思想，当cpu写入数据的时候，如果发现该变量被内存共享（也就是说，在其他cpu中也存在该变量的副本），
	 * 会发出一个信号，通知其他cpu该变量的缓存无效，需要重新到主内存获取。
	 * 
	 * 并发编程的3个比较重要的概念：
	 * 1、原子性atomicity，一个操作，或者多个操作，要么都成功，要么都失败，中间不能有任何的因素中断
	 * 2、可见性visibility，
	 * 3、有序性orderly，代码按照顺序执行的，cpu会优化执行，进行指令重排序，重排序只要求最终一致性，仅适用于单线程，多线程有影响。
	 * 
	 * java中如何保证这3个特性的：
	 * 1、原子性，对基本数据类型变量读取和赋值是保证了原子性的，要么都成功，要么都失败，这些操作是不可中断的。
	 * 	a=10,		原子性
	 * 	b=a,		不满足，1、read a; 2、assgin b; 
	 * 	c++,		不满足，1、read c; 3、assgin to c;
	 * 	c=c+1,		不满足，1、read c; 2、add; 3、assgin to c;	
	 * 2、可见性，volatile关键字保证可见性，变量会被刷新到主存中，其他线程从主存获取该变量。
	 * 3、有序性，happen-before realtionship
	 * 3.1、代码的执行顺序，编写在前面的发生在编写在后面的
	 * 3.2、unlock必须发生在lock之后
	 * 3.3、volatile修饰的变量，对一个变量的写操作先于对该变量的读操作
	 * 3.4、传递规则，操作A先于B，B先于C，那么A肯定先于C
	 * 3.5、线程启动规则，start方法肯定先于线程的run方法
	 * 3.6、线程中断规则，interrupt这个动作，必须发生在捕获该动作之前
	 * 3.7、对象销毁规则，对象的初始化必须发生在finalize之前
	 * 3.8、线程终结规则，所有的操作都是发生在线程死亡之前
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
