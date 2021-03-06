### 背景
在使用Java编程过程中，我们对于一些代码调用的细节有多种编写方式，但是不确定它们性能时，
往往采用重复多次计数的方式来解决。但是随着JVM不断的进化，随着代码执行次数的增加，
JVM会不断的进行编译优化，使得重复多少次才能够得到一个稳定的测试结果变得让人疑惑，
这时候有经验的同学就会在测试执行前先循环上万次并注释为预热。

没错！这样做确实可以获得一个偏向正确的测试结果，但是我们试想如果每到需要斟酌性能的时候，
都要根据场景写一段预热的逻辑吗？当预热完成后，需要多少次迭代来进行正式内容的测量呢？
每次测试结果的输出报告是不是都需要用System.out来输出呢？

其实这些工作都可以交给 JMH (the Java Microbenchmark Harness) ，
它被作为Java9的一部分来发布，但是我们完全不需要等待Java9，而可以方便的使用它来简化我们测试，
它能够照看好JVM的预热、代码优化，让你的测试过程变得更加简单。