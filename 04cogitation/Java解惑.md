## 说明

本文旨在说明一些当时难以理解的Java或者CS的相关问题。

## 判断语句

```c
if (P)
意思等同于 if (P != NULL)
不为空执行语句


if(!P)
意思等同于 if(P == NULL)
为空执行语句![]()
```

## Java 字符串引用（String Interning）

我们都知道 *Strings* 在 Java 中是不可变的（ immutable），因此 JVM 可以通过访问这个字符串的引用，或者我们可以借用指针的这个概念来访问 String 字符串。

**通过指针访问字符串值的这个过程就可以称为引用（interning）。**

1. 当我们在内存中创建一个字符串的时候，JVM 将会根据你创建字符串的值在内存中进行查找有没有和你创建值相同的 String 对象已经被创建了。
2. 如果，JVM 找到了这个对象的话，JVM 就将会为你创建的对象返回已经存在 String 的地址的引用，而不会继续申请新的内存空间，以便于提高内存的利用率。
3. 如果，JVM 没有找到与创建对象相同的值的话，JVM 将会申请内存空间并且创建这个 String 对象，然后将新创建的这个 String 的对象进行返回，这个过程就称为引用（interned）。

让我们使用下面的方法进行验证：

```java
    @Test
    public void whenCreatingConstantStrings_thenTheirAddressesAreEqual() {
        String constantString1 = "HoneyMoose";
        String constantString2 = "HoneyMoose";

        assertThat(constantString1).isSameAs(constantString2);
    }
```

上面的方法将会通过，原因是 constantString2 在创建的时候，将会得到的是 constantString1 内存地址的引用。

因此上面 2 个字符串是完全相同的。

## 基本类型引用、封装类型引用、String引用

我们看一下具体的例子：

```java
public class Test {
    public static void main(String[] args) {
        String str = "ab";
        changeString(str);
        System.out.println("str="+str);
    }
 
    private static void changeString(String str) {
        str = "cd";
    }
}
```

大家猜一下运行结果是什么呢？按照前面的例子，String应该是一个封装类型，它应该是引用传递，是可以改变值得， 运行的结果应该是”cd”。我们实际运行一下看看，

`str=ab`,这如何解释呢？难道String是基本类型？也说不通呀。

这就要从java底层的机制讲起了，java的内存模型分为 **堆** 和 **栈** 。

```java
1.基本类型的变量放在栈里；
2.封装类型中，对象放在堆里，对象的引用放在栈里。
```

1. 堆内存用来存放由new创建的对象和数组。 

2. 栈内存用来存放方法或者局部变量等 
3. 堆是先进先出，后进后出 
4. 栈是后进先出，先进后出 

**java在方法传递参数时，是将变量复制一份，然后传入方法体去执行。** 这句话是很难理解的，也是解释这个问题的精髓。我们先按照这句话解释一下基本类型的传递：

> 1. 虚拟机分配给`num`一个内存地址，并且存了一个值0。
> 2. 虚拟机复制了一个`num`，我们叫他`num’`，`num’`和`num`的内存地址不同，但存的值都是0。
> 3. 虚拟机将`num’`传入方法，方法将`num’`的值改为1。
> 4. 方法结束，方法外打印`num`的值，由于`num`内存中的值没有改变，还是0，所以打印是0。

我们再解释封装类型的传递： 

> 1. 虚拟机在堆中开辟了一个`Product`的内存空间，内存中包含`proName`和`num`。
> 2. 虚拟机在栈中分配给`p`一个内存地址，这个地址中存的是1中的`Product`的内存地址。(即`p`指向`Product`)
> 3. 虚拟机复制了一个`p`，我们叫他`p’`,`p`和`p’`的内存地址不同，但它们存的值是相同的，都是1中`Product`的内存地址。
> 4. 将`p’`传入方法，方法改变了1中的`proName`和`num`。
> 5. 方法结束，方法外打印`p`中变量的值，由于`p`和`p’`中存的都是1中`Product`的地址，但是1中`Product`里的值发生了改变， 所以，方法外打印p的值，是方法执行以后的。我们看到的效果是封装类型的值是改变的。

最后我们再来解释String在传递过程中的步骤： 

> 1. 虚拟机在堆中开辟一块内存，并存值`ab`。
> 2. 虚拟机在栈中分配给`str`一个内存，内存中存的是`ab`的地址。
> 3. 虚拟机复制一份`str`，我们叫`str’`，`str`和`str’`内存不同，但存的值都是`ab`的地址。
> 4. 将`str’`传入方法体
> 5. 方法体在堆中开辟一块内存，并存值`cd`
> 6. 方法体将`str’`的值改变，存入`cd`的内存地址
> 7. 方法结束，方法外打印`str`，由于`str`存的是`ab`的地址，所有打印结果是`ab`

其实还是上面那句比较重要的话 **java在方法传递参数时，是将变量复制一份，然后传入方法体去执行。** 

## 批处理文件中文路径解决

1. 记事本编码
2. 保存为`.bat`后缀
3. 编码格式选为`ANSI`，务必不能选为`utf-8` 

## Java内部类

内部类是定义在另一个类中的类，被发明有两个初衷：

- 内部类可以对**同一个包中**的其他类隐藏（隐身）
- 内部类的方法可以访问定义这个类的作用域中的数据，包括原本的私有数据（相当于家中突然来了一个隐身人，但其他人都察觉不到他的存在，但他可以看透所有）

内部类的对象总有一个隐式的引用，指向创建它的外部类对象，这个引用在内部类的定义中式不可见的。

内部类的是一个*编译器*现象，与虚拟机无关。（这里涉及到反编译，略）只需知道编译器会将内部类转换为常规的类文件，用`$`分隔外部类与内部类名，而虚拟机对此一无所知。

这是一个内部类的程序：

```java
package innerClass;

import java.awt.*;
import java.awt.event.*;
import java.time.*;

import javax.swing.*;

/**
 * @author www
 */
public class InnerClassTest
{
    public static void main(String[] args)
    {
        TalkingClock clock = new TalkingClock(1000,true);
        clock.start();
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals
 * 按指定常规时间打印时间的钟
 */
class TalkingClock
{
    private int interval;
    private boolean beep;

    /**
     * Construct a talking clock
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public TalkingClock(int interval, boolean beep)
    {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Start the clock.
     */
    public void start()
    {
        TimePrinter listener = new TimePrinter();
        Timer timer = new Timer(interval, listener);
        timer.start();
    }

    public class TimePrinter implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            System.out.println("At the tone,the time is " +
                    Instant.ofEpochMilli(event.getWhen()));
            if (beep) {Toolkit.getDefaultToolkit().beep();}
        }
    }
}
```



### 局部内部类

当内部类的名字只出现一次时，可以在*一个方法中局部地定义这个类*。

```java
public void start() {
    //局部内部类...
    class TimePrinter implements ActionListener {
        
    }
    
    var listener = new TimePrinter();
    var timer = new Timer(interval,listener);
    timer.start();
}
```



声明局部内部类，不能有访问控制符（即`public或private`）。局部类的作用域也被限定在声明这个局部类的块中。

局部类有一个很大的优势，除了`start`方法之外，没有任何方法知道`TimePrinter类`的存在，完整代码如下：

```java
package innerClass;

import java.awt.*;
import java.awt.event.*;
import java.time.*;

import javax.swing.*;

/**
 * @author www
 */
public class InnerClassTest
{
    public static void main(String[] args)
    {
        TalkingClock clock = new TalkingClock(1000,true);
        clock.start();
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals
 * 按指定常规时间打印时间的钟
 */
class TalkingClock
{
    private int interval;
    private boolean beep;

    /**
     * Construct a talking clock
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public TalkingClock(int interval, boolean beep)
    {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Start the clock.局部内部类
     */
    public void start()
    {
        class TimePrinter implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.out.println("At the tone,the time is " +
                        Instant.ofEpochMilli(event.getWhen()));
                if (beep) {Toolkit.getDefaultToolkit().beep();}
            }
        }

        TimePrinter listener = new TimePrinter();
        Timer timer = new Timer(interval, listener);
        timer.start();
    }
}
```

而局部内部类还有一个优点，他们不仅可以访问外部类的字段，还可以访问局部变量。但这些变量必须是*事实最终变量*，即一旦赋值，值绝不改变。



### 匿名内部类

使用局部内部类时，还可以更进一步，只想用这个类的一个对象，甚至不想给它命名。这样的一个类就是*匿名内部类*。

```java
package innerClass;

import java.awt.*;
import java.awt.event.*;
import java.time.*;

import javax.swing.*;

/**
 * @author www
 */
public class InnerClassTest
{
    public static void main(String[] args)
    {
        TalkingClock clock = new TalkingClock();
        clock.start(1000,true);
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals
 * 按指定常规时间打印时间的钟
 */
class TalkingClock
{
    private int interval;
    private boolean beep;

    /**
     * Construct a talking clock
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep

    public TalkingClock(int interval, boolean beep)
    {
        this.interval = interval;
        this.beep = beep;
    }
*/
    /**
     * Start the clock.	匿名内部类
     */
    public void start(int interval, boolean beep)
    {
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.out.println("At the tone,the time is " +
                        Instant.ofEpochMilli(event.getWhen()));
                if (beep) {Toolkit.getDefaultToolkit().beep();}
            }
        };
        Timer timer = new Timer(interval, listener);
        timer.start();
    }
}
```



### 静态内部类

有时候，我们只希望把一个类隐藏在另一个类内部，但不需要内部类对外部类对象有一个引用，这里可以把内部类声明为`static`，就可以避免引用。

Q：计算数组中的最大值和最小值。

A：一般来说，只需要编写两个方法，一个求最大值，另一个最小值即可，然后执行两个方法求得结果。

但这种办法需要遍历两次数组，能不能只遍历一次数组？

```java
package staticInnerClass;

/**
 * @author www
 */
public class StaticInnerClassTest
{
    public static void main(String[] args)
    {
        double[] values = new double[20];
        for(int i=0;i<values.length;i++)
        {
            values[i] = 100 * Math.random();
        }
        ArrayAlg.Pair p = ArrayAlg.minmax(values);
        System.out.println("min = " + p.getFirst());
        System.out.println("max = " + p.getSecond());

    }
}

class ArrayAlg
{
    /**
     * A pair of floating-point numbers
     * 静态内部类
     */
    public static class Pair
    {
        private double first;
        private double second;
        /**
         * Construct a pair from two floating-point numbers
         * @param f the first number
         * @param s the second number
         */
        public Pair(double f, double s)
        {
            first = f;
            second = s;
        }

        /**
         * Return the first number of the pair
         * @return the first number
         */
        public double getFirst()
        {
            return first;
        }

        /**
         * Return the second number of the pair
         * @return the second number
         */
        public double getSecond()
        {
            return second;
        }
    }

    /**
     * Computes both the minimum and the maximum of an array
     * @param values an array of floating-point numbers
     * return a pair whose first element is the minimum and whose second element
     * is the maximum
     */
    public static Pair minmax(double[] values)
    {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (double v:values)
        {
            if (min > v) {min = v;}
            if (max < v) {max = v;}
        }
        return new Pair(min, max);
    }
}
```

## 队列、堆、栈、堆栈的区别

**堆栈：先进后出**（就像放在箱子里的衣服，先放进来的后拿出来）

**队列：先进先出**（就像一条路，有一个入口和出口，先进去的就可以先出去）

进程中每个线程都有自己的堆栈，这是一段线程创建是保留下的地址区域。我们的“栈内存”即在此。至于“堆内存”，个人理解是在未用new定义时，堆应该就是未“保留”未“提交”的自由空间，new的功能是这些自由空间中保留（并提交）出一个地址范围。

**栈（Stack）**是操作系统在建立某个系统时或者线程（在支持多线程的操作系统中是线程）为这个线程建立的存储区域，该区域具有FIFO的特性，在编译的时候可以指定需要的Stack的大小。

> 在C/C++中，所有的局部变量都是从栈中分配内存空间，实际上也不是分配，只是从栈顶向上用就行，在退出函数的时候，只是修改栈指针就可以吧占中的内容销毁，所以速度最快。

**堆（Heap）**是应用程序在运行的时候请求操作系统分配给自己的内存，一般是申请/给予的过程。

> C/C++分别用malloc / new 请求分配Heap，用free / delete销毁内存。由于从操作系统管理的内幕产能分配，所以在分配和销毁时都要占用时间，所以用堆的效率低得多！但是堆的好处是可以做得很大，C/C++对分配的Heap是不初始化的。

在Java中，除了简单类型（int，char等）都是在堆中分配内存，这也是程序慢的一个主要原因。但是跟C/C++不同，Java中分配Heap内存是自动初始化的。

在Java中所有的对象（包括int的wrapper Integer）都是**在堆中分配**的，但是**这个对象的引用**却是**在Stack中分配**。也就是说建立一个对象时从两个地方都分配内存，在Heap中分配的内存实际建立这个对象，而在Stack中分配的内存只是一个指向这个堆对象的引用而已。

## 解惑

### 2023/1/8

可以设计一个静态内部类，用来输出特定的结果。

而把算法设计在一个公共方法中。

```java
class ArrayAlg
{
	public static class Pair
    {
        ...
	}
    ...
    public static Pair minmax(double[] values)
    {
        ...
        return new Pair(min,max);
    }
}
```

第八行必须将`Pair类`声明为`static`，否则编译器将会报错，指出没有可用的隐式`ArrayAlg`类型对象来初始化内部类对象。

### 2023/1/15

抽奖系统：

随机数  0 - (n-1)	`int r = (int) (Math.random() * n);`

将要抽取的数字`1 2 ... n`放入一个初始数组`nummber[n]`中，将要输出的随机数放入另一个数组中`result[k]`；

随机数指定索引`r`，数值存入：`result[i] = numbers[r];`

避免抽取相同数字，将已存入的数值替换为末尾的数值，并且删除末尾的数值：`numbers[r] = numbers[n-1]; n--;`

附源码：

```java
package LotteryDrawing;

import java.util.*;
/**
 * @author lyf
 */
public class LotteryDrawing {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("How many numbers do you need to draw? ");
        int k = in.nextInt();

        System.out.print("What is the highest number you can draw? ");
        int n = in.nextInt();

        // fill an array with numbers 1 2 3 ... n
        int[] numbers = new int[n];
        for (int i = 0; i < numbers.length; i++)
        {
            numbers[i] = i + 1;
        }

        // draw k numbers and put them into a second array
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++)
        {
            // make a random number between 0 and n - 1
            int r = (int) (Math.random() * n);

            // pick the element at the random location
            result[i] = numbers[r];

            // move the last element into the random location
            numbers[r] = numbers[n-1];
            n--;
        }

        Arrays.sort(result);
        System.out.println("Bet the following combination.It'll make you rich!");
        for (int number:result)
        {
            System.out.println(number);
        }
    }
}
```

