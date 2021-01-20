GC日志分析

1.串行GC日志分析

执行命令：

`java -Xms512m -Xmx512m -XX:+UseParallelGC -Xloggc:gc.parallel.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

2.产出日志片段-CommandLine

`CommandLine flags: -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=536870912 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
`

CommandLine行显示的是本次执行的GC相关命令信息

3.产出日志片段-PSYoungGen

`2021-01-20T13:16:34.076-0800: 0.205: [GC (Allocation Failure) [PSYoungGen: 131584K->21493K(153088K)] 131584K->46751K(502784K), 0.0210756 secs] [Times: user=0.02 sys=0.04, real=0.02 secs]
`

日志说明

以上的日志说明是因为分配对象失败导致的GC，GC发生在PSYoungGen，即新生代，而新生代采用的GC算法是标记复制mark-copy，回收前新生代大小是131584K，回收后21493K，整个新生代的大小是1573088K，整个堆内存回收前是131584K，回收后是46751K，堆内存的总大小是502784K，GC合计耗时约21毫秒，实际应用暂停时间是real时间20毫秒，其中GC线程是20毫秒，系统是40毫秒
real=user+sys/GC线程数

4.产出日志片段-Full GC

`2021-01-20T13:16:34.638-0800: 0.766: [Full GC (Ergonomics) [PSYoungGen: 20466K->0K(116736K)] [ParOldGen: 302399K->271904K(349696K)] 322865K->271904K(466432K), [Metaspace: 2689K->2689K(1056768K)], 0.0407678 secs] [Times: user=0.12 sys=0.01, real=0.04 secs]
`

日志说明

以上日志说明是因为JVM内部导致的GC，Full GC表示的是新生代和老年代发生的GC，老年代采用的是标记清除整理的算法mark-sweep-compact，清理前302399K，清理后302399K，整个老年代的大小是302399K，本次GC的时间长度为40毫秒，停顿时间real为40毫秒，Metaspace的大小为2689K

5.产出日志片段Full GC

`2021-01-19T19:15:24.466-0800: 1.015: [Full GC (Allocation Failure) 2021-01-19T19:15:24.466-0800: 1.015: [Tenured: 349564K->349555K(349568K), 0.0541392 secs] 506812K->359480K(506816K), [Metaspace: 2689K->2689K(1056768K)], 0.0542220 secs] [Times: user=0.06 sys=0.00, real=0.06 secs]
`

日志说明

以上日志说明产生的是Full GC，因为对象分配失败导致的，详情参加上面4的解读


to be continued

