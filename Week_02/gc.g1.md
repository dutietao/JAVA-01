GC日志分析

1.串行GC日志分析

执行命令：

`java -Xms512m -Xmx512m -XX:+UseG1GC -Xloggc:gc.g1.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

2.产出日志片段-CommandLine

`CommandLine flags: -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=536870912 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC
`

CommandLine行显示的是本次执行的GC相关命令信息

3.产出日志片段-Evacuation Pause

`2021-01-20T13:34:59.091-0800: 0.154: [GC pause (G1 Evacuation Pause) (young), 0.0040366 secs]
[Parallel Time: 3.4 ms, GC Workers: 4]
[GC Worker Start (ms): Min: 154.1, Avg: 154.2, Max: 154.6, Diff: 0.5]
[Ext Root Scanning (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 0.8]
[Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
[Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
[Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
[Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
[Object Copy (ms): Min: 2.6, Avg: 2.9, Max: 3.0, Diff: 0.3, Sum: 11.4]
[Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.4]
[Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
[GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
[GC Worker Total (ms): Min: 2.8, Avg: 3.2, Max: 3.3, Diff: 0.5, Sum: 12.7]
[GC Worker End (ms): Min: 157.4, Avg: 157.4, Max: 157.4, Diff: 0.0]
[Code Root Fixup: 0.0 ms]
[Code Root Purge: 0.0 ms]
[Clear CT: 0.1 ms]
[Other: 0.6 ms]
[Choose CSet: 0.0 ms]
[Ref Proc: 0.3 ms]
[Ref Enq: 0.0 ms]
[Redirty Cards: 0.1 ms]
[Humongous Register: 0.0 ms]
[Humongous Reclaim: 0.0 ms]
[Free CSet: 0.0 ms]
[Eden: 25.0M(25.0M)->0.0B(21.0M) Survivors: 0.0B->4096.0K Heap: 33.2M(512.0M)->8654.2K(512.0M)]
[Times: user=0.00 sys=0.01, real=0.00 secs]
`

日志说明

以上的日志说明是因为年轻代对象提升导致的GC，年轻代存不下，GC总耗时4毫秒0.0040366 secs，本次GC[Parallel Time: 3.4 ms, GC Workers: 4]，4个线程耗时3.4毫秒
GC后各个区大小是[Eden: 25.0M(25.0M)->0.0B(21.0M) Survivors: 0.0B->4096.0K Heap: 33.2M(512.0M)->8654.2K(512.0M)]

几个处理阶段
initial-mark
root-region-scan
concurrent-mark并发标记
remark再次标记
cleanup清理

