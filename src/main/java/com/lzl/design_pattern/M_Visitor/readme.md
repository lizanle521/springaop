### Visitor模式中的登场角色
#### Visitor
Visitor角色负责对数据中每个具体的元素声明一个用户访问Xx的visit(Xx),visit(Xx)用于处理Xx的方法，负责实现该方法的是具体的 ConcretVisitor

#### ConcretVisitor
具体的角色负责实现visitor接口所定义的接口API，它要实现所有的visit(Xx)方法，即实现如何处理每个具体的ConcretElement,在示例程序中，由ListVisitor
类扮演此角色。

#### Element
Element角色表示Visitor角色访问的对象，它声明了接受accept方法。accept方法接收到的是Visitor角色。

#### ConcreteElement
ConcreteElement角色负责实现Element角色所定义的接口API。在示例程序中，由File和Directory扮演此角色

### 拓展思路要点
#### 双重分发
调用方式我们发现：
accept方法调用方式如下：
element.accept(visitor);
而visit访问的调用方式如下：
visitor.visit(element);
对比以下我们可以知道，他们是相反的。concreteelement和 concretvisitor这两个角色共同决定了实际进行的处理。这种称为双重分发

#### 为什么这么复杂
visitor模式看起来把问题复杂化了。如果需要循环处理，从数据结构的类中直接写循环语句不就解决了么
visitor的目的是`将数据处理从数据结构中分离出来`。数据结构很重要，它能将元素集合关联在一起，但是需要注意的是，保存数据结构和
和以数据结构为基础的处理是两种不同的东西。也就是说，visitor提高了File类和Directory类作为组件的独立性。如果将进行处理的方法定义在File类 和 Directory
中，每次要扩展功能时，就要去修改File类和Directory的代码

#### 开闭原则-对扩展开发，对修改封闭
易于增加具体的visitor,难以增加具体的Element

#### visitor工作条件
Element必须公开足够的信息给visitor.这存在一个潜在的问题是，如果公开的信息不当，导致Element难以改良

### 相关的设计模式
#### Iterator模式
Iterator模式和Visitor模式都是在某种数据结构上进行处理
Iterator模式用于遍历保存在数据结构中的元素
Visitor模式用于对保存在数据结构中的元素进行某种特殊的处理

#### Composite模式
有时候visitor所访问的数据结构会用Composite模式

#### Interpreter模式
在Interpreter模式中，有时候会用Visitor模式，例如在生成了语法树以后，可能会用Visitor模式访问语法树中的各个节点进行处理