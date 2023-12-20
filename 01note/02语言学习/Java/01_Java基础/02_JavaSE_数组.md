## 数组

一组类型相同的变量

### 创建数组

定义一个数组类型的变量，使用数组类型“类型[]”，例如，`int[]`。和单个基本类型变量不同，数组变量初始化必须使用`new int[5]`表示创建一个可容纳5个`int`元素的数组。

> Java的数组有几个特点：
>
> - 数组所有元素初始化为默认值，整型都是`0`，浮点型是`0.0`，布尔型是`false`；
> - 数组一旦创建后，大小就不可改变。

- 要访问数组中的某一个元素，需要使用索引。数组索引从`0`开始，例如，5个元素的数组，索引范围是`0`~`4`。

- 可以修改数组中的某一个元素，使用赋值语句，例如，`ns[1] = 79;`。

- 可以用`数组变量.length`获取数组大小。

- 数组是引用类型，在使用索引访问数组元素时，如果索引超出范围，运行时将报错： 

- 也可以在定义数组时直接指定初始化的元素，这样就不必写出数组大小，而是由编译器自动推算数组大小。例如： 

  ```java
  public class Main {
      public static void main(String[] args) {
          // 5位同学的成绩:
          int[] ns = new int[] { 68, 79, 91, 85, 62 };
          System.out.println(ns.length); // 编译器自动推算数组大小为5
      }
  }
  ```

  注意数组是引用类型，并且数组大小不可变。 

**数组元素**可以是值类型（如int）或引用类型（如String），但**数组本身**是引用类型；

### 数组遍历

第一种是直接**for循环**

第二种是**for each**

```java
public class ForEach {
    public static void main(String[] args) {
        int[] ns = {1,3,5,7,8};
        for (int n: ns){
            System.out.println(n);
        }
    }
}
```

注意：在`for (int n : ns)`循环中，变量`n`直接拿到`ns`数组的**元素**，而不是索引。

显然`for each`循环更加简洁。但是，`for each`循环无法拿到数组的索引，因此，到底用哪一种`for`循环，取决于我们的需要。 

### 打印数组内容

直接打印数组变量，得到的是JVM中引用地址。

```java
int[] nn = {12,21,23};
System.out.println(nn);//类似于[I@1b6d3586这样
```

Java标准库提供了`Arrays.toString()`，可以快速打印数组内容： 

```java
import java.util.Arrays;

public class ForEach {
    public static void main(String[] args) {
        int[] ns = {1,3,5,7,8};
        System.out.println(Arrays.toString(ns));
    }
}
输出：
[1, 3, 5, 7, 8]
```

### 数组拷贝

Java允许一个数组变量拷贝到另一个数组变量。即**两个变量将引用同一个数组**。

`Arrays.copyOf()`：将一个数组的所有值拷贝到一个新的数组中去。

```java
int[] copiedLuckyNumbers = Arrays.copyOf(luckyNumers, luckyNumbers.length);
```

第二个参数是新数组的长度，这个参数通常用来增加数组的大小。

### 数组排序

**冒泡排序**

> 算法步骤：
>
> 1. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
> 2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
> 3. 针对所有的元素重复以上的步骤，除了最后一个。
> 4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。

```java
import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {1,98,23,2,43,89,56,18};
        System.out.println("排序之前：" + Arrays.toString(arr));
        //BubbleSort
        for (int i = 1; i < arr.length; i++) {
            //设定一个标记flag，如果此次循环没有进行交换，说明数组已经有序，排序完成
            boolean flag = true;
            for(int j = 0;j < arr.length-i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = false;
                }
            }
            if (flag) {break;}
        }
        System.out.println("排序之后：" + Arrays.toString(arr));
    }
}

输出：
排序之前：[1, 98, 23, 2, 43, 89, 56, 18]
排序之后：[1, 2, 18, 23, 43, 56, 89, 98]
```



Java的标准库已经内置了排序功能，我们只需要调用JDK提供的`Arrays.sort()`就可以排序： 

```java
package javase.hello;
import java.util.Arrays;
public class Test {
    public static void main(String[] args) {
        int[] arr= {12,98,27,33,92,123,9};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

输出：
[9, 12, 27, 33, 92, 98, 123]
```

必须注意，对数组排序实际上修改了数组本身 。原来的3个字符串在内存中均没有任何变化，但是`ns`数组的每个元素指向变化了。 

### 二维数组

二维数组就是数组的数组。定义一个二维数组如下： 

```java
public class Main {
    public static void main(String[] args) {
        int ns = {
            { 1, 2, 3, 4 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 }
        };
        System.out.println(ns.length); // 3
    }
}
```

访问二维数组的某个元素需要使用`array[row][col]` 

要打印一个二维数组，可以使用两层嵌套的for循环 ，或者使用Java标准库的`Arrays.deepToString()`： 

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] ns = {
            { 1, 2, 3, 4 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 }
        };
        System.out.println(Arrays.deepToString(ns));
    }
}
```

### 命令行参数

Java程序的入口是`main`方法，而`main`方法可以接受一个命令行参数，它是一个`String[]`数组。 

这个命令行参数由JVM接收用户输入并传给`main`方法： 

```java
public class Test {
    public static void main(String[] args) {
        for (String arg : args){
            System.out.println(arg);
        }
    }
}
```

我们可以利用接收到的命令行参数，根据不同的参数执行不同的代码。例如，实现一个`-version`参数，打印程序版本号： 

```java
public class Test {
    public static void main(String[] args) {
        for (String arg : args){
            if ("-version".equals(arg)){
                System.out.println("v 1.8 JDK8");
                break;
            }
        }
    }
}
```

小结：

命令行参数类型是`String[]`数组；

命令行参数由JVM接收用户输入并传给`main`方法；

如何解析命令行参数需要由程序自己实现。