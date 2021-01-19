GC日志分析

1.串行GC日志分析

执行命令：

`java -Xms512m -Xmx512m -XX:+UseSerialGC -Xloggc:gc.serial.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

2.产出日志片段-CommandLine

`CommandLine flags: -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=536870912 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC `

CommandLine行显示的是本次执行的GC相关命令信息

3.产出日志片段-DefNew

`2021-01-19T19:15:23.622-0800: 0.170: [GC (Allocation Failure) 2021-01-19T19:15:23.622-0800: 0.171: [DefNew: 139554K->17472K(157248K), 0.0294272 secs] 139554K->46878K(506816K), 0.0295482 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] `

日志说明

以上的日志说明是因为分配对象失败导致的GC，GC发生在DefNew，即新生代，而新生代采用的GC算法是标记复制mark-copy，回收前新生代大小是139554K，回收后17472K，整个新生代的大小是157248K，整个堆内存回收前是139554K，回收后是46878K，堆内存的总大小是506816K，GC合计耗时约29毫秒，实际应用暂停时间是real时间30毫秒，其中GC线程是20毫秒，系统是10毫秒

4.产出日志片段-Tenured

`2021-01-19T19:15:24.028-0800: 0.576: [GC (Allocation Failure) 2021-01-19T19:15:24.028-0800: 0.576: [DefNew: 157247K->157247K(157248K), 0.0000208 secs]2021-01-19T19:15:24.028-0800: 0.576: [Tenured: 327793K->281122K(349568K), 0.0576218 secs] 485041K->281122K(506816K), [Metaspace: 2688K->2688K(1056768K)], 0.0577598 secs] [Times: user=0.06 sys=0.00, real=0.06 secs]
`

日志说明

以上日志说明是因为分配对象失败导致的GC，Tenured表示的是老年代发生的GC，采用的是标记清除整理的算法mark-sweep-compact，清理前327793K，清理后281122K，整个老年代的大小是349568K，本次GC的时间长度为29毫秒，停顿时间real为60毫秒，Metaspace的大小为2688K

5.产出日志片段Full GC

`2021-01-19T19:15:24.466-0800: 1.015: [Full GC (Allocation Failure) 2021-01-19T19:15:24.466-0800: 1.015: [Tenured: 349564K->349555K(349568K), 0.0541392 secs] 506812K->359480K(506816K), [Metaspace: 2689K->2689K(1056768K)], 0.0542220 secs] [Times: user=0.06 sys=0.00, real=0.06 secs]
`

日志说明

以上日志说明产生的是Full GC，因为对象分配失败导致的，详情参加上面4的解读


to be continued

