# this is for learning note for this week(2017/6/25)
## 背景交待
由于这周我想准备找实习，虽然前段时间一直在学习机器学习和深度学习，但是苦于自己没有做过什么项目，心里瘆得慌，不敢找机器学习，数据挖掘方向的岗位，所以还是准备找Java方向的工作，对于Java，其实我更偏重大数据这块，但是由于自己很久没有碰hadoop,spark,Hbase,Hive 这些知识，很多都遗忘了，一个星期不能把所有知识都捡起来，所以大数据岗位也放弃了。最后决定找Java实习工程师，所以我这周我主要是在复习Java知识，同时在牛客网上刷题。
## 学习内容
2017/6/20 
1. java的内存模型
1. Java的多线程知识（特别是ThreadLocal）
1. Java的类加载器
1. Java的垃圾回收机制

`注`：由于内容比较多，我就不一一记录，我就把我以前不怎么知道的内容简单记录下,主要涉及到垃圾回收。
  
 **如何判定一个对象是否该回收？**
 * **引用计数器法**</br>
 `优点`：引用计数收集器可以很快的执行，交织在程序运行中。对程序需要不被长时间打断的实时环境比较有利。 </br>
 `缺点`：无法检测出循环引用。如父对象有一个对子对象的引用，子对象反过来引用父对象。这样，他们的引用计数永远不可能为0.
 * **可达性分析算法（枚举根节点）**  
 这是JVM采用的算法  
 
 **常见垃圾回收算法**  
 * 标记-清除算法(可能产生大量内存碎片)  
 * 复制算法（新生代，是标记清楚算法的改进）  
 * 标记-整理算法（老年代）
 * 分代收集算法（商业虚拟机都采用的）
 
 **常见垃圾收集器**
  * Serial收集器（复制算法)   
  新生代单线程收集器，标记和清理都是单线程，优点是简单高效。
  * Serial Old收集器(标记-整理算法)  
  老年代单线程收集器，Serial收集器的老年代版本
  * ParNew收集器(停止-复制算法)  
  新生代收集器，可以认为是Serial收集器的多线程版本,在多核CPU环境下有着比Serial更好的表现。
  * Parallel Scavenge收集器(停止-复制算法)  
  并行收集器，追求高吞吐量，高效利用CPU。吞吐量一般为99%， 吞吐量= 用户线程时间/(用户线程时间+GC线程时间)。适合后台应用等对交互相应要求不高的场景。
  * Parallel Old收集器(停止-复制算法)   
  Parallel Scavenge收集器的老年代版本，并行收集器，吞吐量优先
  * CMS(Concurrent Mark Sweep)收集器（标记-清理算法  
  高并发、低停顿，追求最短GC回收停顿时间，cpu占用比较高，响应时间快，停顿时间短，多核cpu 追求高响应时间的选择
  * G1收集器（Garbage-First，复制算法)  
  并行与并发，分代收集，空间整合，可预测的停顿  
  
 **新生代与老年代所用的垃圾收集器** 

 新生代  | 老年代
 ------ | --------
 Serial | Serial Old
 ParNew  | Parallel Old
 Parallel | CMS
 Scavenge | ----
 
  **Java 堆**  
  Java堆 划分为新生代和老年代(1:2)  
*  新生代   
    * Eden区  
    * From Survivor  
    * To Survivor     
Eden：From Survivor：To Survivor = 8:1:1,新生代发生Minor GC,是发生在新生代中的垃圾收集动作，所采用的是复制算法。
 *  老年代    
 老年代存放了大对象(--XX:PretenureSzieThreshold参数设置)以及长期存活的对象(在年轻代中经历了N次垃圾回收后仍然存活的对象，就会被放到年老代中)
 老年代发生FullGC ，所采用的是标记-清除算法  
 
 ----
 2017/6/21
1. Mysql的数据库引擎的区别(MYISAM和INNODB)
1. Mysql数据库索引的实现原理
1. Mysql数据库常见的优化
1. Java常见集合的实现原理（ConcurrentHashMap)  

 **存储引擎MYISAM和INNODB的区别**
 
 对比项   |      MYISAM |    INNODB
 ------  | ------- | -------
 主外键  | 不支持   | 支持
 事务    | 不支持    |支持
 行表所  | 表锁，即使操作一条记录也会锁住整个表，不适合高并发的操作 | 行锁,操作时只锁某一行，不对其它行有影响，适合高并发的操作
 缓存    | 只缓存索引，不缓存真实数据 | 不仅缓存索引还要缓存真实数据，对内存要求较高，而且内存大小对性能有决定性的影响
 表空间   |小   |大
 关注点  |性能 | 事务
 默认安装 | Y |Y
 
 ----
  2017/6/22
1. 常见排序算法的实现
1. 手写单例模式（线程安全的，懒汉式，饿汉式）
1. spring的IOC和AOP的原理(常用的注入方式有：setter,构造函数，注解)
1. spring 事务隔离级别，servlet的生命周期，spring的特性  

**单例模式最好的三种写法**  
* 双重检查锁+volatile（饱汉式的最优写法，推荐）
```java
 public class Singleton{
    private static volatile Singleton instance =null;
    private Singleton(){}
    public static Singleton getSingleton(){
      if(instance==null){
        synchronized(Singleton.class){
          if(instance==null){
             instance = new Singleton();
          }     
        }
      }
      return instance;
    }
}

```
* 静态内部类(线程安全，强烈推荐)
```java
public class Singleton{
    private Singleton(){}
    private  static class SingletonHolder{
         private static Singleton instance =new Singleton();
    }
    public static Singleton getSingleton(){
      return SingletonHolder.instance;
    }
}

```
* 使用枚举(强烈推荐)
```java
public enum Singleton{
    /**
     * 定义一个枚举的元素，它就代表了Singleton的一个实例。
     */
    instance;
     /**
     * 单例可以有自己的操作
     */ 
    public void SingletonOperation(){
           //功能处理
    }
}
```
`注` 自己在印象笔记里面写了篇单例模式的详细学习笔记，后续打算整理出来，放到github上

------
  2017/6/23  
  
由于实习僧网站目前反馈比较慢，没有收到面试通知，所以内心无比焦急,遂没有怎么去复习，就去浏览各大招聘网站，耽误了不少时间。在吃晚饭的时候用手机随手投了几个简历，然后5分钟之后就收到了中能电气的面试通知，所以心里多多少少还是有一点安慰。(后来京东金融反馈的信息他们已经招满了，阿里健康反馈的是我的毕业时间不符合，他们只要2018届毕业的)晚上复习了一下排序算法。  
* 冒泡排序
```java
   public static int [] bubbleSort(int [] arry){
    	for(int i =0;i<arry.length-1;i++){
    		for(int j=0;j<arry.length-i-1;j++){
    			if(arry[j]>arry[j+1]){
    				int temp ;
    				temp =arry[j];
    				arry[j]=arry[j+1];
    				arry[j+1]=temp;
    			}
    		}
    	}
    	return arry;
    }
```
* 快排
```java 
 public static int getMiddle(int arry[],int low,int high){
        //取第一个元素为基准
    	int key = arry[low];
    	while(low<high){
    		//从右边开始扫描
    		while(low<high && arry[high]>=key){
    			high--;
    		}
    		//搜索完毕，此时碰到arry[high]<key ,所以得把他和基准值互换
    		arry[low]=arry[high];
    		
    		//从左边开始扫描
    		
    		while(low<high && arry[low]<=key){
    			low++;
    		}
       		//搜索完毕，此时碰到arry[low]>key ,所以得把他和基准值互换,此时基准是array[high],换完之后基准是arry[low]
    		arry[high]=arry[low];
    	}
    	//设置
    	arry[low] = key;
    	return low;
    }
    
    
    public static int[] quickSort(int []arry, int low, int high){
    	if(low < high){
    		int middle = getMiddle(arry, low, high);
    		//递归左链
    		quickSort(arry,0,middle-1);
    		//递归右链
    		quickSort(arry,middle+1,high);
    	}
    	return arry;
    }
}
```


