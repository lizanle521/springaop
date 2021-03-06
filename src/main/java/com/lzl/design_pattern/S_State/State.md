### State模式
在面向对象编程中，是用类表示对象的。也就是说，程序的设计者需要考虑用类来表示什么东西。类对应的东西可能
真实的存在于世界中，也可能不存在。对于后者来说，可能有人会在看到代码后吃惊：这个也能是类？
状态模式就是这样的一个模式。
在state模式中，我们用类表示状态，state的意思就是状态。在现实世界中，我们会考虑各种东西的状态。
但是几乎不会将状态当作东西来看。
在本实例中，我们要用类来表示状态，这样我们就能通过切换类来改变对象的状态，当需要增加新的状态时，
如何修改这个代码的问题也很明确

### 示例程序
这里我们来看一个警戒状态每小时会改变一次的警报系统，虽说是警报系统，其实功能非常简单。
下边我们来用程序实现这个金库报警系统。该系统并不会真正的呼叫报警中心。只是页面上显示呼叫状态。
并且用1s来代表现实世界的1小时

- 有一个金库
- 金库与报警中心相连
- 金库里有警铃和正常通话用的电话
- 金库里有时钟，监视现在的时间
- 白天是9点到下午4：59点，晚上是下午5点到第二天8：59

- 金库只能在白天使用
- 白天使用金库的话，会在警报中心留下记录
- 晚上使用金库的话，会向警报中心发送紧急事态通知

- 任何时候都可以使用警铃
- 使用警铃电话，会向警报中心发送紧急事态变更

- 任何时候都可以使用电话（但晚上只有留言电话）
- 白天使用电话，会呼叫报警中心
- 晚上用电话，会呼叫报警中心的留言电话

### 不使用state模式的伪代码
```
警报系统的类 {
    使用金库时（）{
        if(白天){
            向警报中心报告使用记录
        }else if(晚上){
            向警报中心发送紧急事态通知
        }
    }
    
    警铃想起时 （）{
        向警报中心发送紧急事态通知
    }
    
    正常通话时 (){
        if(白天){
           呼叫警报中心
        }else if(晚上){
           呼叫警报重新的留言电话
        }
    }
}
```
### 使用state模式的伪代码
```text
白天状态类 {
    使用金库时（）{
        向警报中心报告使用记录
    }
        
    警铃想起时 （）{
        向警报中心发送紧急事态通知
    }
        
    正常通话时 (){
        呼叫警报中心    
    }
}
```

```text
晚上状态类 {
    使用金库时（）{
        向警报中心发送紧急事态通知
    }
        
    警铃想起时 （）{
        向警报中心发送紧急事态通知
    }
        
    正常通话时 (){
        呼叫警报重新的留言电话    
    }
}
```