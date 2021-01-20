# 第2周作业

1.串行GC

java -Xms512m -Xmx512m -XX:+UseSerialGC -Xloggc:gc.serial.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

2.并行GC

java -Xms512m -Xmx512m -XX:+UseParallelGC -Xloggc:gc.parallel.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

3.G1 GC

java -Xms512m -Xmx512m -XX:+UseG1GC -Xloggc:gc.g1.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis