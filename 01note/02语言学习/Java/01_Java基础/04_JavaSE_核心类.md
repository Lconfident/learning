## 字符串与编码

### 字符串本身即引用类型

在Java中，`String`是一个引用类型，它本身也是一个`class`。但是，Java编译器对`String`有特殊处理，即可以直接用`"..."`来表示一个字符串：

```java
String s1 = "Hello!";
```

实际上字符串在`String`内部是通过一个`char[]`数组表示的，因此，按下面的写法也是可以的：

```java
String s2 = new String(new char[] {'H', 'e', 'l', 'l', 'o', '!'});
```

因为`String`太常用了，所以Java提供了`"..."`这种字符串字面量表示方法。

Java字符串的一个重要特点就是字符串*不可变*。这种不可变性是通过内部的`private final char[]`字段，以及没有任何修改`char[]`的方法实现的。 

### 字符串比较

当我们想要比较两个字符串是否相同时，要特别注意，我们实际上是想比较字符串的内容是否相同。必须使用`equals()`方法而不能用`==`。

```java
// String 
public class Main {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "hello";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}
```

从表面上看，两个字符串用`==`和`equals()`比较都为`true`，但实际上那只是Java编译器在编译期，会自动把所有相同的字符串当作一个对象放入常量池，自然`s1`和`s2`的引用就是相同的。

所以，这种`==`比较返回`true`纯属巧合。换一种写法，`==`比较就会失败：

```java
public class Main {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "HELLO".toLowerCase();
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}
```

结论：两个字符串比较，必须总是使用`equals()`方法。

> 注意：实际上只有*字符串字面量*是共享的，而`+`或`substring`等操作得到的字符串并不共享。

要忽略大小写比较，使用`equalsIgnoreCase()`方法。

### NULL串

表示目前没有任何对象与该变量关联

### 包含子串 

```java
// 是否包含子串:
"Hello".contains("ll"); // true
```

注意到`contains()`方法的参数是`CharSequence`而不是`String`，因为`CharSequence`是`String`实现的一个接口。 

> CharSequence是一个描述字符串结构的接口，在这个接口里面一般发现有三种常用的子类： String类，StringBuffer类，StringBuilder类。CharSequence描述的就是一个字符串，以后见到就不要感觉的陌生了。 

### 提取子串

```java
"Hello".substring(2); // "llo"
"Hello".substring(2, 4); "ll"
```

注意索引号是从`0`开始的。

`substring`方法有个优点，容易计算子串的长度：4-2=2。 

### 搜索子串

更多的例子：

```java
"Hello".indexOf("l"); // 2
"Hello".lastIndexOf("l"); // 3
"Hello".startsWith("He"); // true
"Hello".endsWith("lo"); // true
```

### 去除首尾空白字符

1. 使用`trim()`方法可以移除字符串首尾空白字符。空白字符包括空格，`\t`，`\r`，`\n`： 

> `\r`在`java`中是回车符的意思，将光标切换到当前行的开头，若`\r`之后无内容，输出不影响；若`\r`之后有内容，后面的内容依次替代第一位、第二位...

注意：`trim()`并没有改变字符串的内容，而是返回了一个新字符串。 

2. 另一个`strip()`方法也可以移除字符串首尾空白字符。它和`trim()`不同的是，类似中文的空格字符`\u3000`也会被移除： 

```java
"\u3000Hello\u3000".strip(); // "Hello"
" Hello ".stripLeading(); // "Hello "
" Hello ".stripTrailing(); // " Hello"
```

`String`还提供了`isEmpty()`和`isBlank()`来判断字符串是否为空和空白字符串：

```java
"".isEmpty(); // true，因为字符串长度为0
"  ".isEmpty(); // false，因为字符串长度不为0
"  \n".isBlank(); // true，因为只包含空白字符
" Hello ".isBlank(); // false，因为包含非空白字符
```

### 替换子串

要在字符串中替换子串，有两种方法。

一种是根据字符或字符串替换：

```java
String s = "hello";
s.replace('l', 'w'); // "hewwo"，所有字符'l'被替换为'w'
s.replace("ll", "~~"); // "he~~o"，所有子串"ll"被替换为"~~"
```

另一种是通过正则表达式替换：

```java
String s = "A,,B;C ,D";
s.replaceAll("[\\,\\;\\s]+", ","); // "A,B,C,D"
```

上面的代码通过正则表达式，把匹配的子串统一替换为`","`。关于正则表达式的用法我们会在后面详细讲解。 

### 分割字符串

要分割字符串，使用`split()`方法，并且传入的也是正则表达式：

```java
String s = "A,B,C,D";
String[] ss = s.split("\\,"); // {"A", "B", "C", "D"}
```

### 拼接字符串

1. Java语言允许使用`+`号连接两个字符串（当一个字符串与一个非字符串的值拼接时，后者会转换成字符串）
2. Java11提供了`repeat()`方法

```java
String repeated = "Java".repeat(3); // repeated is "JavaJavaJava"
```



1. 使用静态方法`join()`，它用指定的字符串连接字符串数组：

```java
String[] arr = {"A", "B", "C"};
String s = String.join("*", arr); // "A*B*C"
```

### 格式化字符串

字符串提供了`formatted()`方法和`format()`静态方法，可以传入其他参数，替换占位符，然后生成新的字符串：

```java
public class Main {
    public static void main(String[] args) {
        String s = "Hi %s, your score is %d!";
        System.out.println(s.formatted("Alice", 80));
        System.out.println(String.format("Hi %s, your score is %.2f!", "Bob", 59.5));
    }
}
```

有几个占位符，后面就传入几个参数。参数类型要和占位符一致。我们经常用这个方法来格式化信息。常用的占位符有：

- `%s`：显示字符串；
- `%d`：显示整数；
- `%x`：显示十六进制整数；
- `%f`：显示浮点数。

占位符还可以带格式，例如`%.2f`表示显示两位小数。如果你不确定用啥占位符，那就始终用`%s`，因为`%s`可以显示任何数据类型。 

### 类型转换

要把任意基本类型或引用类型转换为字符串，可以使用静态方法`valueOf()`。

这是一个重载方法，编译器会根据参数自动选择合适的方法：

```java
String.valueOf(123); // "123"
String.valueOf(45.67); // "45.67"
String.valueOf(true); // "true"
String.valueOf(new Object()); // 类似java.lang.Object@636be97c
```

要把字符串转换为其他类型，就需要根据情况。例如，把字符串转换为`int`类型： 

```java
int n1 = Integer.parseInt("123"); // 123
int n2 = Integer.parseInt("ff", 16); // 按十六进制转换，255
```

把字符串转换为`boolean`类型：

```java
boolean b1 = Boolean.parseBoolean("true"); // true
boolean b2 = Boolean.parseBoolean("FALSE"); // false
```

要特别注意，`Integer`有个`getInteger(String)`方法，它不是将字符串转换为`int`，而是把该字符串对应的系统变量转换为`Integer`：

```java
Integer.getInteger("java.version"); // 版本号，11
```

### 转换为char[]

`String`和`char[]`类型可以互相转换，方法是：

```java
char[] cs = "Hello".toCharArray(); // String -> char[]
String s = new String(cs); // char[] -> String
```

如果修改了`char[]`数组，`String`并不会改变。

```java
char[] c = "hello".toCharArray();
String s = new String(c);
System.out.println(s);
c[0] = '_';
System.out.println(s);
//输出
hello
hello
```

这是因为通过`new String(char[])`创建新的`String`实例时，它并不会直接引用传入的`char[]`数组，而是**会复制一份**，所以，修改外部的`char[]`数组不会影响`String`实例内部的`char[]`数组，因为这是两个不同的数组。 

从`String`的不变性设计可以看出，如果传入的对象有可能改变，我们需要复制而不是直接引用。 

### 字符编码

在早期的计算机系统中，为了给字符编码，美国国家标准学会（American National Standard Institute：ANSI）制定了一套英文字母、数字和常用符号的编码，它占用一个字节，编码范围从`0`到`127`，最高位始终为`0`，称为`ASCII`编码。例如，字符`'A'`的编码是`0x41`，字符`'1'`的编码是`0x31`。

如果要把汉字也纳入计算机编码，很显然一个字节是不够的。`GB2312`标准使用两个字节表示一个汉字，其中第一个字节的最高位始终为`1`，以便和`ASCII`编码区分开。例如，汉字`'中'`的`GB2312`编码是`0xd6d0`。

类似的，日文有`Shift_JIS`编码，韩文有`EUC-KR`编码，这些编码因为标准不统一，同时使用，就会产生冲突。

为了统一全球所有语言的编码，全球统一码联盟发布了`Unicode`编码，它把世界上主要语言都纳入同一个编码，这样，中文、日文、韩文和其他语言就不会冲突。

`Unicode`编码需要两个或者更多字节表示，我们可以比较中英文字符在`ASCII`、`GB2312`和`Unicode`的编码：

英文字符`'A'`的`ASCII`编码和`Unicode`编码：

```
         ┌────┐
ASCII:   │ 41 │
         └────┘
         ┌────┬────┐
Unicode: │ 00 │ 41 │
         └────┴────┘
```

英文字符的`Unicode`编码就是简单地在前面添加一个`00`字节。

中文字符`'中'`的`GB2312`编码和`Unicode`编码：

```
         ┌────┬────┐
GB2312:  │ d6 │ d0 │
         └────┴────┘
         ┌────┬────┐
Unicode: │ 4e │ 2d │
         └────┴────┘
```

那我们经常使用的`UTF-8`又是什么编码呢？因为英文字符的`Unicode`编码高字节总是`00`，包含大量英文的文本会浪费空间，所以，出现了`UTF-8`编码，它是一种变长编码，用来把固定长度的`Unicode`编码变成1～4字节的变长编码。通过`UTF-8`编码，英文字符`'A'`的`UTF-8`编码变为`0x41`，正好和`ASCII`码一致，而中文`'中'`的`UTF-8`编码为3字节`0xe4b8ad`。

`UTF-8`编码的另一个好处是容错能力强。如果传输过程中某些字符出错，不会影响后续字符，因为`UTF-8`编码依靠高字节位来确定一个字符究竟是几个字节，它经常用来作为传输编码。

### Unicode编码

在Java中，`char`类型实际上就是两个字节的`Unicode`编码。如果我们要手动把字符串转换成其他编码，可以这样做：

```java
byte[] b1 = "Hello".getBytes(); // 按系统默认编码转换，不推荐
byte[] b2 = "Hello".getBytes("UTF-8"); // 按UTF-8编码转换
byte[] b2 = "Hello".getBytes("GBK"); // 按GBK编码转换
byte[] b3 = "Hello".getBytes(StandardCharsets.UTF_8); // 按UTF-8编码转换
```

注意：转换编码后，就不再是`char`类型，而是`byte`类型表示的数组。

如果要把已知编码的`byte[]`转换为`String`，可以这样做：

```java
byte[] b = ...
String s1 = new String(b, "GBK"); // 按GBK转换
String s2 = new String(b, StandardCharsets.UTF_8); // 按UTF-8转换
```

始终牢记：Java的`String`和`char`**在内存中总是以Unicode编码表示。**

### 延伸阅读

对于不同版本的JDK，`String`类在内存中有不同的优化方式。具体来说，早期JDK版本的`String`总是以`char[]`存储，它的定义如下：

```java
public final class String {
    private final char[] value;
    private final int offset;
    private final int count;
}
```

而较新的JDK版本的`String`则以`byte[]`存储：如果`String`仅包含ASCII字符，则每个`byte`存储一个字符，否则，每两个`byte`存储一个字符，这样做的目的是为了节省内存，因为大量的长度较短的`String`通常仅包含ASCII字符：

```java
public final class String {
    private final byte[] value;
    private final byte coder; // 0 = LATIN1, 1 = UTF16
```

对于使用者来说，`String`内部的优化不影响任何已有代码，因为它的`public`方法签名是不变的。

### 小结

- Java字符串`String`是不可变对象；
- 字符串操作不改变原字符串内容，而是返回新字符串；
- 常用的字符串操作：提取子串、查找、替换、大小写转换等；
- Java在内存中使用Unicode编码表示`String`和`char`；
- 转换编码就是将`String`和`byte[]`转换，需要指定编码；
- 转换为`byte[]`时，始终优先考虑`UTF-8`编码。

## StringBuilder

Java编译器对`String`做了特殊处理，使得我们可以直接用`+`拼接字符串。

 虽然可以直接拼接字符串，但是，在循环中，若每次循环都会创建新的字符串对象，然后扔掉旧的字符串。这样，绝大部分字符串都是临时对象，不但浪费内存，还会影响GC效率。

为了能高效拼接字符串，Java标准库提供了`StringBuilder`，它是一个可变对象，可以预分配缓冲区，这样，往`StringBuilder`中新增字符时，不会创建新的临时对象： 

```java
StringBuilder sb = new StringBuilder(1024);
for (int i = 0; i < 1000; i++) {
    sb.append(',');
    sb.append(i);
}
String s = sb.toString();
```

 `StringBuilder`还可以进行链式操作： 

```java
public class Main {
    public static void main(String[] args) {
        var sb = new StringBuilder(1024);
        sb.append("Mr ")
          .append("Bob")
          .append("!")
          .insert(0, "Hello, ");
        System.out.println(sb.toString());
    }
}
```

如果我们查看`StringBuilder`的源码，可以发现，进行链式操作的关键是，定义的`append()`方法会返回`this`，这样，就可以不断调用自身的其他方法。 

> `return this` ： 意味着返回当前调用方法的对象的引用，以便链式操作

仿照`StringBuilder`，我们也可以设计支持链式操作的类。例如，一个可以不断增加的计数器： 

```java
package javase.hello;

public class Test {

    public static void main(String[] args) {
        Adder adder = new Adder();
        adder.add(3)
                .add(5)
                .inc()
                .add(10);
        System.out.println(adder.value());

    }
}

class Adder {
    private int sum = 0;

    public Adder add (int n) {
        sum += n;
        return this;
    }

    public Adder inc() {
        sum ++;
        return this;
    }

    public int value() {
        return sum;
    }
}
```

> 注意：对于普通的字符串`+`操作，并不需要我们将其改写为`StringBuilder`，因为Java编译器在编译时就自动把多个连续的`+`操作编码为`StringConcatFactory`的操作。 
>
> 在运行期，`StringConcatFactory`会自动把字符串连接操作优化为数组复制或者`StringBuilder`操作。 

你可能还听说过`StringBuffer`，这是Java早期的一个`StringBuilder`的线程安全版本，它通过同步来保证多个线程操作`StringBuffer`也是安全的，但是同步会带来执行速度的下降。

`StringBuilder`和`StringBuffer`接口完全相同，现在完全没有必要使用`StringBuffer`。

### 小结

`StringBuilder`是可变对象，用来高效拼接字符串；

`StringBuilder`可以支持链式操作，实现链式操作的关键是返回实例本身；

`StringBuffer`是`StringBuilder`的线程安全版本，现在很少使用。

## StringJoiner

要高效拼接字符串，应该使用`StringBuilder`。 

我们拼接的字符串像这样： 

```java
public class Main {
    public static void main(String[] args) {
        String[] names = {"Bob", "Alice", "Grace"};
        var sb = new StringBuilder();
        sb.append("Hello ");
        for (String name : names) {
            sb.append(name).append(", ");
        }
        // 注意去掉最后的", ":
        sb.delete(sb.length() - 2, sb.length());
        sb.append("!");
        System.out.println(sb.toString());
    }
}
```

类似用分隔符拼接数组的需求很常见，所以Java标准库还提供了一个`StringJoiner`来干这个事： 

```java
public class Main {
    public static void main(String[] args) {
        String[] names = {"Bob", "Alice", "Grace"};
        var sj = new StringJoiner(", ");
        for (String name : names) {
            sj.add(name);
        }
        System.out.println(sj.toString());
    }
}
```

用`StringJoiner`的结果少了前面的`"Hello "`和结尾的`"!"`！遇到这种情况，需要给`StringJoiner`指定“开头”和“结尾”： 

```java
public class Main {
    public static void main(String[] args) {
        String[] names = {"Bob", "Alice", "Grace"};
        var sj = new StringJoiner(", ", "Hello ", "!");
        for (String name : names) {
            sj.add(name);
        }
        System.out.println(sj.toString());
    }
}
```

### String.join()

`String`还提供了一个静态方法`join()`，这个方法在内部使用了`StringJoiner`来拼接字符串，在不需要指定“开头”和“结尾”的时候，用`String.join()`更方便：

```java
String[] names = {"Bob", "Alice", "Grace"};
var s = String.join(", ", names);
```

练习：

```java
package javase.hello;


import java.util.StringJoiner;

public class Test {
    public static void main(String[] args) {
        String[] fields = {"name", "position", "salary"};
        String table = "employee";
        String select = buildSelectSql(table, fields);
        System.out.println(select);
        System.out.println("SELECT name, position, salary FROM employee".equals(select) ? "测试成功" : "测试失败");
    }
    static String buildSelectSql(String table, String[] fields) {
        var sj = new StringJoiner(", ","SELECT "," FROM "+table);
        for (String field:fields) {
            sj.add(field);
        }
        return sj.toString();
    }
}
```

### 小结

用指定分隔符拼接字符串数组时，使用`StringJoiner`或者`String.join()`更方便；

用`StringJoiner`拼接字符串时，还可以额外附加一个“开头”和“结尾”。

## 包装类型

Java的数据类型分两种：

- 基本类型：`byte`，`short`，`int`，`long`，`boolean`，`float`，`double`，`char`
- 引用类型：所有`class`和`interface`类型

引用类型可以赋值为`null`，表示空，但基本类型不能赋值为`null`：

```java
String s = null;
int n = null; // compile error!
```

那么，如何把一个基本类型视为对象（引用类型）？

比如，想要把`int`基本类型变成一个引用类型，我们可以定义一个`Integer`类，它只包含一个实例字段`int`，这样，`Integer`类就可以视为`int`的包装类（Wrapper Class）：

```java
public class Integer {
    private int value;

    public Integer(int value) {
        this.value = value;
    }

    public int intValue() {
        return this.value;
    }
}
```

定义好了`Integer`类，我们就可以把`int`和`Integer`互相转换：

```java
Integer n = null;
Integer n2 = new Integer(99);
int n3 = n2.intValue();
```

实际上，因为包装类型非常有用，Java核心库为每种基本类型都提供了对应的包装类型：

| 基本类型 | 对应的引用类型      |
| -------- | ------------------- |
| boolean  | java.lang.Boolean   |
| byte     | java.lang.Byte      |
| short    | java.lang.Short     |
| int      | java.lang.Integer   |
| long     | java.lang.Long      |
| float    | java.lang.Float     |
| double   | java.lang.Double    |
| char     | java.lang.Character |

我们可以直接使用，并不需要自己去定义：

```java
public class Main {
    public static void main(String[] args) {
        int i = 100;
        // 通过new操作符创建Integer实例(不推荐使用,会有编译警告):
        Integer n1 = new Integer(i);
        // 通过静态方法valueOf(int)创建Integer实例:
        Integer n2 = Integer.valueOf(i);
        // 通过静态方法valueOf(String)创建Integer实例:
        Integer n3 = Integer.valueOf("100");
        System.out.println(n3.intValue());
    }
}
```

### Auto Boxing

因为`int`和`Integer`可以互相转换：

```java
int i = 100;
Integer n = Integer.valueOf(i);
int x = n.intValue();
```

所以，Java编译器可以帮助我们自动在`int`和`Integer`之间转型：

```java
Integer n = 100; // 编译器自动使用Integer.valueOf(int)
int x = n; // 编译器自动使用Integer.intValue()
```

这种直接把`int`变为`Integer`的赋值写法，称为自动装箱（Auto Boxing），反过来，把`Integer`变为`int`的赋值写法，称为自动拆箱（Auto Unboxing）。 

注意：自动装箱和自动拆箱只发生在编译阶段，目的是为了少写代码。

装箱和拆箱会影响代码的执行效率，因为编译后的`class`代码是严格区分基本类型和引用类型的。并且，自动拆箱执行时可能会报`NullPointerException`：

```java
public class Main {
    public static void main(String[] args) {
        Integer n = null;
        int i = n;
    }
}
```

### 不变类

所有的包装类型都是不变类。我们查看`Integer`的源码可知，它的核心代码如下：

```java
public final class Integer {
    private final int value;
}
```

因此，一旦创建了`Integer`对象，该对象就是不变的。

对两个`Integer`实例进行比较要特别注意：绝对不能用`==`比较，因为`Integer`是引用类型，必须使用`equals()`比较：

```java
public class Main {
    public static void main(String[] args) {
        Integer x = 127;
        Integer y = 127;
        Integer m = 99999;
        Integer n = 99999;
        System.out.println("x == y: " + (x==y)); // true
        System.out.println("m == n: " + (m==n)); // false
        System.out.println("x.equals(y): " + x.equals(y)); // true
        System.out.println("m.equals(n): " + m.equals(n)); // true
    }
}
```

仔细观察结果的童鞋可以发现，`==`比较，较小的两个相同的`Integer`返回`true`，较大的两个相同的`Integer`返回`false`，这是因为`Integer`是不变类，编译器把`Integer x = 127;`自动变为`Integer x = Integer.valueOf(127);`，为了节省内存，`Integer.valueOf()`对于较小的数，始终返回相同的实例，因此，`==`比较“恰好”为`true`，但我们*绝不能*因为Java标准库的`Integer`内部有缓存优化就用`==`比较，必须用`equals()`方法比较两个`Integer`。 

>  按照语义编程，而不是针对特定的底层实现去“优化”。 

### 静态工厂方法

因为`Integer.valueOf()`可能始终返回同一个`Integer`实例，因此，在我们自己创建`Integer`的时候，以下两种方法：

- 方法1：`Integer n = new Integer(100);`
- 方法2：`Integer n = Integer.valueOf(100);`

**方法2更好**，因为方法1总是创建新的`Integer`实例，方法2把内部优化留给`Integer`的实现者去做，即使在当前版本没有优化，也有可能在下一个版本进行优化。

> 知识点：**new 究竟做了什么?**
>
> 简单来说：当我们使用 new 来构造一个新的类实例时，其实是告诉了 JVM 我需要一个新的实例。JVM 就会自动在内存中开辟一片空间，然后调用构造函数来初始化成员变量，最终把引用返回给调用方。

我们把能创建“新”对象的静态方法称为*静态工厂方法*。`Integer.valueOf()`就是静态工厂方法，它尽可能地返回缓存的实例以节省内存。

《Effective Java》 开篇第一条就是『**考虑使用静态工厂方法代替构造器**』，关于其原因，作者总结了 4 条 ：

1. 静态工厂方法与构造器不同的第一优势在于，它们有名字

> 由于语言的特性，Java 的构造函数都是跟类名一样的。
> 这导致的一个问题是构造函数的名称不够灵活，经常不能准确地描述返回值，在有多个重载的构造函数时尤甚，如果参数类型、数目又比较相似的话，那更是很容易出错。
>
> 比如，如下的一段代码 ：
>
> ```java
> Date date0 = new Date();
> Date date1 = new Date(0L);
> Date date2 = new Date("0");
> Date date3 = new Date(1,2,1);
> Date date4 = new Date(1,2,1,1,1);
> Date date5 = new Date(1,2,1,1,1,1);
> ```
>
> 而如果使用静态工厂方法，就可以给方法起更多有意义的名字，比如前面的 `valueOf`、`newInstance`、`getInstance` 等，对于代码的编写和阅读都能够更清晰。 

2. 第二个优势，不用每次被调用时都创建新对象

> 这个很容易理解了，有时候外部调用者只需要拿到一个实例，而不关心是否是新的实例；又或者我们想对外提供一个单例时 —— 如果使用工厂方法，就可以很容易的在内部控制，防止创建不必要的对象，减少开销。

   在实际的场景中，单例的写法也大都是用静态工厂方法来实现的。

3. 第三个优势，可以返回原返回类型的子类 


> 设计模式中的基本的原则之一——『里氏替换』原则，就是说子类应该能替换父类。 显然，构造方法只能返回确切的自身类型，而静态工厂方法则能够更加灵活，可以根据需要方便地返回任何它的子类型的实例。 

4. 第四个优势，在创建带泛型的实例时，能使代码变得简洁

> 这条主要是针对带泛型类的繁琐声明而说的，需要重复书写两次泛型参数：
>
> ```java
> Map<String,Date> map = new HashMap<String,Date>();
> ```
>
> 不过自从 java7 开始，这种方式已经被优化过了 —— 对于一个已知类型的变量进行赋值时，由于泛型参数是可以被推导出，所以可以在创建实例时省略掉泛型参数。
>
> ```java
> Map<String,Date> map = new HashMap<>();
> ```
>
> 所以这个问题实际上已经不存在了。

如果你看过之后仍然犹豫不决，那么我觉得可以再给你更多一些理由：

5. 可以有多个参数相同但名称不同的工厂方法

> 构造函数虽然也可以有多个，但是由于函数名已经被固定，所以就要求参数必须有差异时（类型、数量或者顺序）才能够重载了。
>  举例来说：
>
> ```java
> class Child{
>     int age = 10;
>     int weight = 30;
>     public Child(int age, int weight) {
>         this.age = age;
>         this.weight = weight;
>     }
>     public Child(int age) {
>         this.age = age;
>     }
> }
> ```
>
> Child 类有 age 和 weight 两个属性，如代码所示，它已经有了两个构造函数：Child(int age, int weight) 和 Child(int age)，这时候如果我们想再添加一个指定 wegiht 但不关心 age 的构造函数，一般是这样：
>
> ```java
> public Child( int weight) {
>     this.weight = weight;
> }
> ```
>
> ↑ 但要把这个构造函数添加到 Child 类中，我们都知道是行不通的，因为 java 的函数签名是忽略参数名称的，所以 `Child(int age)` 跟 `Child(int weight)` 会冲突。
>
> 这时候，静态工厂方法就可以登场了。
>
> ```java
> class Child{
>     int age = 10;
>     int weight = 30;
>     public static Child newChild(int age, int weight) {
>         Child child = new Child();
>         child.weight = weight;
>         child.age = age;
>         return child;
>     }
>     public static Child newChildWithWeight(int weight) {
>         Child child = new Child();
>         child.weight = weight;
>         return child;
>     }
>     public static Child newChildWithAge(int age) {
>         Child child = new Child();
>         child.age = age;
>         return child;
>     }
> }
> ```

>  其中的 `newChildWithWeight` 和 `newChildWithAge`，就是两个参数类型相同的的方法，但是作用不同，如此，就能够满足上面所说的类似`Child(int age)` 跟 `Child(int weight)`同时存在的需求。
>   （另外，这两个函数名字也是自描述的，相对于一成不变的构造函数更能表达自身的含义，这也是上面所说的第一条优势 —— 『它们有名字』）

6. 可以减少对外暴露的属性

> 软件开发中有一条很重要的经验：**对外暴露的属性越多，调用者就越容易出错**。所以对于类的提供者，一般来说，应该努力减少对外暴露属性，从而降低调用者出错的机会。 
>
> 考虑一下有如下一个 Player 类：
>
> ```java
> // Player : Version 1
> class Player {
>     public static final int TYPE_RUNNER = 1;
>     public static final int TYPE_SWIMMER = 2;
>     public static final int TYPE_RACER = 3;
>     protected int type;
>     public Player(int type) {
>         this.type = type;
>     }
> }
> ```
>
> Player 对外提供了一个构造方法，让使用者传入一个 type 来表示类型。那么这个类期望的调用方式就是这样的：
>
> ```java
>     Player player1 = new Player(Player.TYPE_RUNNER);
>     Player player2 = new Player(Player.TYPE_SWEIMMER);
> ```
>
> 但是，我们知道，提供者是无法控制调用方的行为的，实际中调用方式可能是这样的：
>
> ```java
>     Player player3 = new Player(0);
>     Player player4 = new Player(-1);
>     Player player5 = new Player(10086);
> ```
>
> 提供者期望的构造函数传入的值是事先定义好的几个常量之一，但如果不是，就**很容易导致程序错误**。
>
> —— 要避免这种错误，使用枚举来代替常量值是常见的方法之一，当然如果不想用枚举的话，使用我们今天所说的主角`静态工厂方法`也是一个很好的办法。
>
> > 插一句：
> >  实际上，使用枚举也有一些缺点，比如增大了调用方的成本；如果枚举类成员增加，会导致一些需要完备覆盖所有枚举的调用场景出错等。
>
> 如果把以上需求用静态工厂方法来实现，代码大致是这样的：
>
> ```java
> // Player : Version 2
> class Player {
>     public static final int TYPE_RUNNER = 1;
>     public static final int TYPE_SWIMMER = 2;
>     public static final int TYPE_RACER = 3;
>     int type;
> 
>     private Player(int type) {
>         this.type = type;
>     }
> 
>     public static Player newRunner() {
>         return new Player(TYPE_RUNNER);
>     }
>     public static Player newSwimmer() {
>         return new Player(TYPE_SWIMMER);
>     }
>     public static Player newRacer() {
>         return new Player(TYPE_RACER);
>     }
> }
> ```
>
> 注意其中的构造方法被声明为了 `private`，这样可以防止它被外部调用，于是调用方在使用 Player 实例的时候，基本上就必须通过 newRunner、newSwimmer、newRacer 这几个静态工厂方法来创建，调用方无须知道也无须指定 type 值 —— 这样就能把 type 的赋值的范围控制住，防止前面所说的异常值的情况。

7. 多了一层控制，方便统一修改

> 我们在开发中一定遇到过很多次这样的场景：在写一个界面时，服务端的数据还没准备好，这时候我们经常就需要自己在客户端编写一个测试的数据，来进行界面的测试，像这样：
>
> ```java
>     // 创建一个测试数据
>     User tester = new User();
>     tester.setName("隔壁老张");
>     tester.setAge(16);
>     tester.setDescription("我住隔壁我姓张！");
>     // use tester
>     bindUI(tester);
>     ……
> ```
>
> 要写一连串的测试代码，如果需要测试的界面有多个，那么这一连串的代码可能还会被复制多次到项目的多个位置。
>
> 这种写法的缺点呢，首先是代码臃肿、混乱；其次是万一上线的时候漏掉了某一处，忘记修改，那就可以说是灾难了……
>
> 但是如果你像我一样，习惯了用静态工厂方法代替构造器的话，则会很自然地这么写，先在 User 中定义一个 newTestInstance 方法：
>
> ```java
> static class User{
>     String name ;
>     int age ;
>     String description;
>     public static User newTestInstance() {
>         User tester = new User();
>         tester.setName("隔壁老张");
>         tester.setAge(16);
>         tester.setDescription("我住隔壁我姓张！");
>         return tester;
>     }
> }
> ```
>
> 然后调用的地方就可以这样写了：
>
> ```java
>     // 创建一个测试数据
>     User tester = User.newTestInstance();
>     // use tester
>     bindUI(tester);
> ```
>
> 是不是瞬间就觉得优雅了很多？！
>
> 而且不只是代码简洁优雅，由于所有测试实例的创建都是在这一个地方，所以在需要正式数据的时候，也只需把这个方法随意删除或者修改一下，所有调用者都会编译不通过，彻底杜绝了由于疏忽导致线上还有测试代码的情况。

总体来说，我觉得它实质上的最主要作用是：**能够增大类的提供者对自己所提供的类的控制力**。 

作为一个开发者，当我们作为调用方，使用别人提供的类时，如果要使用 new 关键字来为其创建一个类实例，如果对类不是特别熟悉，那么一定是要特别慎重的 —— `new` 实在是太好用了，以致于它经常被滥用，随时随地的 new 是有很大风险的，除了可能导致性能、内存方面的问题外，也经常会使得代码结构变得混乱。

而当我们在作为类的提供方时，无法控制调用者的具体行为，但是我们可以尝试使用一些方法来增大自己对类的控制力，减少调用方犯错误的机会，这也是对代码更负责的具体体现。

>  重点：创建新对象时，优先选用静态工厂方法而不是new操作符。 

### 进制转换

`Integer`类本身还提供了大量方法，例如，最常用的静态方法`parseInt()`可以把字符串解析成一个整数：

```java
int x1 = Integer.parseInt("100"); // 100
int x2 = Integer.parseInt("100", 16); // 256,因为按16进制解析
```

`Integer`还可以把整数格式化为指定进制的字符串：

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(Integer.toString(100)); // "100",表示为10进制
        System.out.println(Integer.toString(100, 36)); // "2s",表示为36进制
        System.out.println(Integer.toHexString(100)); // "64",表示为16进制
        System.out.println(Integer.toOctalString(100)); // "144",表示为8进制
        System.out.println(Integer.toBinaryString(100)); // "1100100",表示为2进制
    }
}
```

注意：上述方法的输出都是`String`，在计算机内存中，只用二进制表示，不存在十进制或十六进制的表示方法。`int n = 100`在内存中总是以4字节的二进制表示：

```
┌────────┬────────┬────────┬────────┐
│00000000│00000000│00000000│01100100│
└────────┴────────┴────────┴────────┘
```

我们经常使用的`System.out.println(n);`是依靠核心库自动把整数格式化为10进制输出并显示在屏幕上，使用`Integer.toHexString(n)`则通过核心库自动把整数格式化为16进制。

这里我们注意到程序设计的一个重要原则：**数据的存储和显示要分离**。

Java的包装类型还定义了一些有用的静态变量

```java
// boolean只有两个值true/false，其包装类型只需要引用Boolean提供的静态字段:
Boolean t = Boolean.TRUE;
Boolean f = Boolean.FALSE;
// int可表示的最大/最小值:
int max = Integer.MAX_VALUE; // 2147483647
int min = Integer.MIN_VALUE; // -2147483648
// long类型占用的bit和byte数量:
int sizeOfLong = Long.SIZE; // 64 (bits)
int bytesOfLong = Long.BYTES; // 8 (bytes)
```

最后，所有的整数和浮点数的包装类型都继承自`Number`，因此，可以非常方便地直接通过包装类型获取各种基本类型：

```java
// 向上转型为Number:
Number num = new Integer(999);
// 获取byte, int, long, float, double:
byte b = num.byteValue();
int n = num.intValue();
long ln = num.longValue();
float f = num.floatValue();
double d = num.doubleValue();
```

### 处理无符号整型

在Java中，并没有无符号整型（Unsigned）的基本数据类型。`byte`、`short`、`int`和`long`都是带符号整型，最高位是符号位。而C语言则提供了CPU支持的全部数据类型，包括无符号整型。无符号整型和有符号整型的转换在Java中就需要借助包装类型的静态方法完成。

例如，byte是有符号整型，范围是`-128`~`+127`，但如果把`byte`看作无符号整型，它的范围就是`0`~`255`。我们把一个负的`byte`按无符号整型转换为`int`：

```java
public class Main {
    public static void main(String[] args) {
        byte x = -1;
        byte y = 127;
        System.out.println(Byte.toUnsignedInt(x)); // 255
        System.out.println(Byte.toUnsignedInt(y)); // 127
    }
}
```

因为`byte`的`-1`的二进制表示是`11111111`，以无符号整型转换后的`int`就是`255`。

类似的，可以把一个`short`按unsigned转换为`int`，把一个`int`按unsigned转换为`long`。

### 小结

Java核心库提供的包装类型可以把基本类型包装为`class`；

自动装箱和自动拆箱都是在编译期完成的（JDK>=1.5）；

装箱和拆箱会影响执行效率，且拆箱时可能发生`NullPointerException`；

包装类型的比较必须使用`equals()`；

整数和浮点数的包装类型都继承自`Number`；

包装类型提供了大量实用方法。

## JavaBean

在Java中，有很多`class`的定义都符合这样的规范：

- 若干`private`实例字段；
- 通过`public`方法来读写实例字段。

```java
public class Person {
    private String name;
    private int age;

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return this.age; }
    public void setAge(int age) { this.age = age; }
}
```

如果读写方法符合以下这种命名规范：

```java
// 读方法:
public Type getXyz()
// 写方法:
public void setXyz(Type value)
```

### JavaBean

那么这种`class`被称为`JavaBean`：

上面的字段是`xyz`，那么读写方法名分别以`get`和`set`开头，并且后接大写字母开头的字段名`Xyz`，因此两个读写方法名分别是`getXyz()`和`setXyz()`。 

`boolean`字段比较特殊，它的读方法一般命名为`isXyz()`：

```java
// 读方法:
public boolean isChild()
// 写方法:
public void setChild(boolean value)
```

我们通常把一组对应的读方法（`getter`）和写方法（`setter`）称为属性（`property`）。例如，`name`属性：

- 对应的读方法是`String getName()`
- 对应的写方法是`setName(String)`

只有`getter`的属性称为只读属性（read-only），例如，定义一个age只读属性：

- 对应的读方法是`int getAge()`
- 无对应的写方法`setAge(int)`

类似的，只有`setter`的属性称为只写属性（write-only）。

很明显，只读属性很常见，只写属性不常见。

属性只需要定义`getter`和`setter`方法，不一定需要对应的字段。例如，`child`只读属性定义如下：

```java
public class Person {
    private String name;
    private int age;

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return this.age; }
    public void setAge(int age) { this.age = age; }

    public boolean isChild() {
        return age <= 6;
    }
}
```

可以看出，`getter`和`setter`也是一种数据封装的方法。

### JavaBean的作用

JavaBean主要用来传递数据，即把一组数据组合成一个JavaBean便于传输。

此外，JavaBean可以方便地被IDE工具分析，生成读写属性的代码，主要用在图形界面的可视化设计中。IDEA中`Alt + insert `快捷键自动生成。

### 小结

JavaBean是一种符合命名规范的`class`，它通过`getter`和`setter`来定义属性；

属性是一种通用的叫法，并非Java语法规定；

可以利用IDE快速生成`getter`和`setter`；

使用`Introspector.getBeanInfo()`可以获取属性列表。

## 枚举类

在Java中，我们可以通过`static final`来定义常量。例如，我们希望定义周一到周日这7个常量，可以用7个不同的`int`表示：

```java
public class Weekday {
    public static final int SUN = 0;
    public static final int MON = 1;
    public static final int TUE = 2;
    public static final int WED = 3;
    public static final int THU = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
}
```

使用常量的时候，可以这么引用：

```java
if (day == Weekday.SAT || day == Weekday.SUN) {
    // TODO: work at home
}
```

也可以把常量定义为字符串类型，例如，定义3种颜色的常量：

```java
public class Color {
    public static final String RED = "r";
    public static final String GREEN = "g";
    public static final String BLUE = "b";
}
```

使用常量的时候，可以这么引用：

```java
String color = ...
if (Color.RED.equals(color)) {
    // TODO:
}
```

无论是`int`常量还是`String`常量，使用这些常量来表示一组枚举值的时候，有一个严重的问题就是，编译器无法检查每个值的合理性。例如：

```java
if (weekday == 6 || weekday == 7) {
    if (tasks == Weekday.MON) {
        // TODO:
    }
}
```

上述代码编译和运行均不会报错，但存在两个问题：

- 注意到`Weekday`定义的常量范围是`0`~`6`，并不包含`7`，编译器无法检查不在枚举中的`int`值；
- 定义的常量仍可与其他变量比较，但其用途并非是枚举星期值。

### enum

为了让编译器能自动检查某个值在枚举的集合内，并且，不同用途的枚举需要不同的类型来标记，不能混用，我们可以使用`enum`来定义枚举类：

```java
public class Main {
    public static void main(String[] args) {
        Weekday day = Weekday.SUN;
        if (day == Weekday.SAT || day == Weekday.SUN) {
            System.out.println("Work at home!");
        } else {
            System.out.println("Work at office!");
        }
    }
}

enum Weekday {
    SUN, MON, TUE, WED, THU, FRI, SAT;
}
```

注意到定义枚举类是通过关键字`enum`实现的，我们只需依次列出枚举的常量名。 

和`int`定义的常量相比，使用`enum`定义枚举有如下好处：

1. 首先，`enum`常量本身带有类型信息，即`Weekday.SUN`类型是`Weekday`，编译器会自动检查出类型错误。例如，下面的语句不可能编译通过：

```java
int day = 1;
if (day == Weekday.SUN) { // Compile error: bad operand types for binary operator '=='
}
```

2. 其次，不可能引用到非枚举的值，因为无法通过编译。 
3. 最后，不同类型的枚举不能互相比较或者赋值，因为类型不符。例如，不能给一个`Weekday`枚举类型的变量赋值为`Color`枚举类型的值：

```java
Weekday x = Weekday.SUN; // ok!
Weekday y = Color.RED; // Compile error: incompatible types
```

这就使得编译器可以在编译期自动检查出所有可能的潜在错误。

总之就是类与实例得配对。

### enum的比较

使用`enum`定义的枚举类是一种引用类型。前面我们讲到，引用类型比较，要使用`equals()`方法，如果使用`==`比较，它比较的是两个引用类型的变量是否是同一个对象。因此，引用类型比较，要始终使用`equals()`方法，但`enum`类型可以例外。

这是因为`enum`类型的每个常量在JVM中只有一个唯一实例，所以可以直接用`==`比较： 

```java
if (day == Weekday.FRI) { // ok!
}
if (day.equals(Weekday.SUN)) { // ok, but more code!
}
```

### enum类型

通过`enum`定义的枚举类，和其他的`class`有什么区别？

答案是没有任何区别。`enum`定义的类型就是`class`，只不过它有以下几个特点：

- 定义的`enum`类型总是继承自`java.lang.Enum`，且无法被继承；
- **只能定义出`enum`的实例**，而无法通过`new`操作符创建`enum`的实例；
- 定义的每个实例都是引用类型的唯一实例；
- 可以将`enum`类型用于`switch`语句。

例如，我们定义的`Color`枚举类：

```java
public enum Color {
    RED, GREEN, BLUE;
}
```

编译器编译出的`class`大概就像这样：

```java
public final class Color extends Enum { // 继承自Enum，标记为final class
    // 每个实例均为全局唯一:
    public static final Color RED = new Color();
    public static final Color GREEN = new Color();
    public static final Color BLUE = new Color();
    // private构造方法，确保外部无法调用new操作符:
    private Color() {}
}
```

所以，编译后的`enum`类和普通`class`并没有任何区别。但是我们自己无法按定义普通`class`那样来定义`enum`，必须使用`enum`关键字，这是Java语法规定的。

因为`enum`是一个`class`，每个枚举的值都是`class`实例，因此，这些实例有一些方法： 

#### name()

返回常量名，例如：

```java
String s = Weekday.SUN.name(); // "SUN"
```

#### ordinal()

返回定义的常量的顺序，从0开始计数，例如：

```java
int n = Weekday.MON.ordinal(); // 1
```

改变枚举常量定义的顺序就会导致`ordinal()`返回值发生变化。例如：

```java
public enum Weekday {
    SUN, MON, TUE, WED, THU, FRI, SAT;
}
```

和

```java
public enum Weekday {
    MON, TUE, WED, THU, FRI, SAT, SUN;
}
```

的`ordinal`就是不同的。如果在代码中编写了类似`if(x.ordinal()==1)`这样的语句，就要保证`enum`的枚举顺序不能变。新增的常量必须放在最后。

有些童鞋会想，`Weekday`的枚举常量如果要和`int`转换，使用`ordinal()`不是非常方便？比如这样写： 

```java
String task = Weekday.MON.ordinal() + "/ppt";
saveToFile(task);
```

### 枚举常量

但是，如果不小心修改了枚举的顺序，编译器是无法检查出这种逻辑错误的。要编写健壮的代码，就不要依靠`ordinal()`的返回值。因为`enum`本身是`class`，所以我们可以定义`private`的构造方法，并且，给每个枚举常量添加字段： 

```java
public class Main {
    public static void main(String[] args) {
        Weekday day = Weekday.SUN;
        if (day.dayValue == 6 || day.dayValue == 0) {
            System.out.println("Work at home!");
        } else {
            System.out.println("Work at office!");
        }
    }
}

enum Weekday {
    MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6), SUN(0);

    public final int dayValue;

    private Weekday(int dayValue) {
        this.dayValue = dayValue;
    }
}
```

这样就无需担心顺序的变化，新增枚举常量时，也需要指定一个`int`值。 

### toString()覆写

默认情况下，对枚举常量调用`toString()`会返回和`name()`一样的字符串。但是，`toString()`可以被覆写，而`name()`则不行。我们可以给`Weekday`添加`toString()`方法： 

```java
public class Main {
    public static void main(String[] args) {
        Weekday day = Weekday.SUN;
        if (day.dayValue == 6 || day.dayValue == 0) {
            System.out.println("Today is " + day + ". Work at home!");
        } else {
            System.out.println("Today is " + day + ". Work at office!");
        }
    }
}

enum Weekday {
    MON(1, "星期一"), TUE(2, "星期二"), WED(3, "星期三"), THU(4, "星期四"), FRI(5, "星期五"), SAT(6, "星期六"), SUN(0, "星期日");

    public final int dayValue;
    private final String chinese;

    private Weekday(int dayValue, String chinese) {
        this.dayValue = dayValue;
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return this.chinese;
    }
}
```

覆写`toString()`的目的是在输出时更有可读性。

 注意：判断枚举常量的名字，要始终使用name()方法，绝不能调用toString()！

### switch

最后，枚举类可以应用在`switch`语句中。因为枚举类天生具有类型信息和有限个枚举常量，所以比`int`、`String`类型更适合用在`switch`语句中：

```java
public class Main {
    public static void main(String[] args) {
        Weekday day = Weekday.SUN;
        switch(day) {
        case MON:
        case TUE:
        case WED:
        case THU:
        case FRI:
            System.out.println("Today is " + day + ". Work at office!");
            break;
        case SAT:
        case SUN:
            System.out.println("Today is " + day + ". Work at home!");
            break;
        default:
            throw new RuntimeException("cannot process " + day);
        }
    }
}

enum Weekday {
    MON, TUE, WED, THU, FRI, SAT, SUN;
}
```

加上`default`语句，可以在漏写某个枚举常量时自动报错，从而及时发现错误。 

### 小结

Java使用`enum`定义枚举类型，它被编译器编译为`final class Xxx extends Enum { … }`；

通过`name()`获取常量定义的字符串，注意不要使用`toString()`；

通过`ordinal()`返回常量定义的顺序（无实质意义）；

可以为`enum`编写构造方法、字段和方法

`enum`的构造方法要声明为`private`，字段强烈建议声明为`final`；

`enum`适合用在`switch`语句中。

## BigInteger

在Java中，由CPU原生提供的整型最大范围是64位`long`型整数。使用`long`型整数可以直接通过CPU指令进行计算，速度非常快。

如果我们使用的整数范围超过了`long`型怎么办？这个时候，就只能用软件来模拟一个大整数。`java.math.BigInteger`就是用来表示任意大小的整数。`BigInteger`内部用一个`int[]`数组来模拟一个非常大的整数：

```java
BigInteger bi = new BigInteger("1234567890");
System.out.println(bi.pow(5)); // 2867971860299718107233761438093672048294900000
```

对`BigInteger`做运算的时候，只能使用实例方法，例如，加法运算：

```java
BigInteger i1 = new BigInteger("1234567890");
BigInteger i2 = new BigInteger("12345678901234567890");
BigInteger sum = i1.add(i2); // 12345678902469135780
```

和`long`型整数运算比，`BigInteger`不会有范围限制，但缺点是速度比较慢。

也可以把`BigInteger`转换成`long`型：

```java
BigInteger i = new BigInteger("123456789000");
System.out.println(i.longValue()); // 123456789000
System.out.println(i.multiply(i).longValueExact()); // java.lang.ArithmeticException: BigInteger out of long range
```

使用`longValueExact()`方法时，如果超出了`long`型的范围，会抛出`ArithmeticException`。

`BigInteger`和`Integer`、`Long`一样，也是不可变类，并且也继承自`Number`类。因为`Number`定义了转换为基本类型的几个方法：

- 转换为`byte`：`byteValue()`
- 转换为`short`：`shortValue()`
- 转换为`int`：`intValue()`
- 转换为`long`：`longValue()`
- 转换为`float`：`floatValue()`
- 转换为`double`：`doubleValue()`

因此，通过上述方法，可以把`BigInteger`转换成基本类型。如果`BigInteger`表示的范围超过了基本类型的范围，转换时将丢失高位信息，即结果不一定是准确的。如果需要准确地转换成基本类型，可以使用`intValueExact()`、`longValueExact()`等方法，在转换时如果超出范围，将直接抛出`ArithmeticException`异常。

### 小结

`BigInteger`用于表示任意大小的整数；

`BigInteger`是不变类，并且继承自`Number`；

将`BigInteger`转换成基本类型时可使用`longValueExact()`等方法保证结果准确。

## BigDecimal

和`BigInteger`类似，`BigDecimal`可以表示一个任意大小且精度完全准确的浮点数。

```java
BigDecimal bd = new BigDecimal("123.4567");
System.out.println(bd.multiply(bd)); // 15241.55677489
```

`BigDecimal`用`scale()`表示小数位数，例如：

```java
BigDecimal d1 = new BigDecimal("123.45");
BigDecimal d2 = new BigDecimal("123.4500");
BigDecimal d3 = new BigDecimal("1234500");
System.out.println(d1.scale()); // 2,两位小数
System.out.println(d2.scale()); // 4
System.out.println(d3.scale()); // 0
```

通过`BigDecimal`的`stripTrailingZeros()`方法，可以将一个`BigDecimal`格式化为一个相等的，但去掉了末尾0的`BigDecimal`：

```java
BigDecimal d1 = new BigDecimal("123.4500");BigDecimal d2 = d1.stripTrailingZeros();
System.out.println(d1.scale()); // 4
System.out.println(d2.scale()); // 2,因为去掉了00

BigDecimal d3 = new BigDecimal("1234500");
BigDecimal d4 = d3.stripTrailingZeros();
System.out.println(d3.scale()); // 0
System.out.println(d4.scale()); // -2
如果一个BigDecimal的scale()返回负数，例如，-2，表示这个数是个整数，并且末尾有2个0。
```

可以对一个BigDecimal设置它的scale，如果精度比原始值低，那么按照指定的方法进行四舍五入或者直接截断：

```java
public class Main {
    public static void main(String[] args) {
        BigDecimal d1 = new BigDecimal("123.456789");
        BigDecimal d2 = d1.setScale(4, RoundingMode.HALF_UP); // 四舍五入，123.4568
        BigDecimal d3 = d1.setScale(4, RoundingMode.DOWN); // 直接截断，123.4567
        System.out.println(d2);
        System.out.println(d3);
    }
}
```

对`BigDecimal`做加、减、乘时，精度不会丢失，但是做除法时，存在无法除尽的情况，这时，就必须指定精度以及如何进行截断：

```java
BigDecimal d1 = new BigDecimal("123.456");
BigDecimal d2 = new BigDecimal("23.456789");
BigDecimal d3 = d1.divide(d2, 10, RoundingMode.HALF_UP); // 保留10位小数并四舍五入
BigDecimal d4 = d1.divide(d2); // 报错：ArithmeticException，因为除不尽
```

还可以对`BigDecimal`做除法的同时求余数：

```java
public class Main {
    public static void main(String[] args) {
        BigDecimal n = new BigDecimal("12.345");
        BigDecimal m = new BigDecimal("0.12");
        BigDecimal[] dr = n.divideAndRemainder(m);
        System.out.println(dr[0]); // 102
        System.out.println(dr[1]); // 0.105
    }
}
```

调用`divideAndRemainder()`方法时，返回的数组包含两个`BigDecimal`，分别是商和余数，其中商总是整数，余数不会大于除数。我们可以利用这个方法判断两个`BigDecimal`是否是整数倍数： 

### 比较BigDecimal

在比较两个`BigDecimal`的值是否相等时，要特别注意，使用`equals()`方法不但要求两个`BigDecimal`的值相等，还要求它们的`scale()`相等：

```java
BigDecimal d1 = new BigDecimal("123.456");
BigDecimal d2 = new BigDecimal("123.45600");
System.out.println(d1.equals(d2)); // false,因为scale不同
System.out.println(d1.equals(d2.stripTrailingZeros())); // true,因为d2去除尾部0后scale变为2
System.out.println(d1.compareTo(d2)); // 0
```

必须使用`compareTo()`方法来比较，它根据两个值的大小分别返回负数、正数和`0`，分别表示小于、大于和等于。

>  总是使用compareTo()比较两个BigDecimal的值，不要使用equals()！ 

如果查看`BigDecimal`的源码，可以发现，实际上一个`BigDecimal`是通过一个`BigInteger`和一个`scale`来表示的，即`BigInteger`表示一个完整的整数，而`scale`表示小数位数：

```java
public class BigDecimal extends Number implements Comparable<BigDecimal> {
    private final BigInteger intVal;
    private final int scale;
}
```

`BigDecimal`也是从`Number`继承的，也是不可变对象。

### 小结

`BigDecimal`用于表示精确的小数，常用于财务计算；

比较`BigDecimal`的值是否相等，必须使用`compareTo()`而不能使用`equals()`。

## 常用工具类

### Math

顾名思义，`Math`类就是用来进行数学计算的，它提供了大量的静态方法来便于我们实现数学计算：

求绝对值：

```java
Math.abs(-100); // 100
Math.abs(-7.8); // 7.8
```

取最大或最小值：

```java
Math.max(100, 99); // 100
Math.min(1.2, 2.3); // 1.2
```

计算x的y次方：

```java
Math.pow(2, 10); // 2的10次方=1024
```

计算√x：

```java
Math.sqrt(2); // 1.414...
```

计算e的x次方：

```java
Math.exp(2); // 7.389...
```

计算以e为底的对数：

```java
Math.log(4); // 1.386...
```

计算以10为底的对数：

```java
Math.log10(100); // 2
```

三角函数：

```java
Math.sin(3.14); // 0.00159...
Math.cos(3.14); // -0.9999...
Math.tan(3.14); // -0.0015...
Math.asin(1.0); // 1.57079...
Math.acos(1.0); // 0.0
```

Math还提供了几个数学常量：

```java
double pi = Math.PI; // 3.14159...
double e = Math.E; // 2.7182818...
Math.sin(Math.PI / 6); // sin(π/6) = 0.5
```

生成一个随机数x，x的范围是`0 <= x < 1`：

```java
Math.random(); // 0.53907... 每次都不一样
```

如果我们要生成一个区间在`[MIN, MAX)`的随机数，可以借助`Math.random()`实现，计算如下： 

```java
public class Main {
    public static void main(String[] args) {
        double x = Math.random(); // x的范围是[0,1)
        double min = 10;
        double max = 50;
        double y = x * (max - min) + min; // y的范围是[10,50)
        long n = (long) y; // n的范围是[10,50)的整数
        System.out.println(y);
        System.out.println(n);
    }
}
```

### HexFormat

在处理`byte[]`数组时，我们经常需要与十六进制字符串转换，自己写起来比较麻烦，用Java标准库提供的`HexFormat`则可以方便地帮我们转换。

要将`byte[]`数组转换为十六进制字符串，可以用`formatHex()`方法：

```java
import java.util.HexFormat;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        byte[] data = "Hello".getBytes();
        HexFormat hf = HexFormat.of();
        String hexData = hf.formatHex(data); // 48656c6c6f
    }
}
```

如果要定制转换格式，则使用定制的`HexFormat`实例：

```java
// 分隔符为空格，添加前缀0x，大写字母:
HexFormat hf = HexFormat.ofDelimiter(" ").withPrefix("0x").withUpperCase();
hf.formatHex("Hello".getBytes())); // 0x48 0x65 0x6C 0x6C 0x6F
```

从十六进制字符串到`byte[]`数组转换，使用`parseHex()`方法：

```java
byte[] bs = HexFormat.of().parseHex("48656c6c6f");
```

### Random

`Random`用来创建伪随机数。所谓伪随机数，是指只要给定一个初始的种子，产生的随机数序列是完全一样的。

要生成一个随机数，可以使用`nextInt()`、`nextLong()`、`nextFloat()`、`nextDouble()`：

```java
Random r = new Random();
r.nextInt(); // 2071575453,每次都不一样
r.nextInt(10); // 5,生成一个[0,10)之间的int
r.nextLong(); // 8811649292570369305,每次都不一样
r.nextFloat(); // 0.54335...生成一个[0,1)之间的float
r.nextDouble(); // 0.3716...生成一个[0,1)之间的double
```

有童鞋问，每次运行程序，生成的随机数都是不同的，没看出*伪随机数*的特性来。

这是因为我们创建`Random`实例时，如果不给定种子，就使用系统当前时间戳作为种子，因此每次运行时，种子不同，得到的伪随机数序列就不同。

如果我们在创建`Random`实例时指定一个种子，就会得到完全确定的随机数序列：

```java
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random(12345);
        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextInt(100));
        }
        // 51, 80, 41, 28, 55...
    }
}
```

前面我们使用的`Math.random()`实际上内部调用了`Random`类，所以它也是伪随机数，只是我们无法指定种子。 

### SecureRandom

有伪随机数，就有真随机数。实际上真正的真随机数只能通过量子力学原理来获取，而我们想要的是一个不可预测的安全的随机数，`SecureRandom`就是用来创建安全的随机数的：

```java
SecureRandom sr = new SecureRandom();
System.out.println(sr.nextInt(100));
```

`SecureRandom`无法指定种子，它使用RNG（random number generator）算法。JDK的`SecureRandom`实际上有多种不同的底层实现，有的使用安全随机种子加上伪随机数算法来产生安全的随机数，有的使用真正的随机数生成器。实际使用的时候，可以优先获取高强度的安全随机数生成器，如果没有提供，再使用普通等级的安全随机数生成器： 

```java
import java.util.Arrays;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
public class Main {
    public static void main(String[] args) {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
        } catch (NoSuchAlgorithmException e) {
            sr = new SecureRandom(); // 获取普通的安全随机数生成器
        }
        byte[] buffer = new byte[16];
        sr.nextBytes(buffer); // 用安全随机数填充buffer
        System.out.println(Arrays.toString(buffer));
    }
}
```

`SecureRandom`的安全性是通过操作系统提供的安全的随机种子来生成随机数。这个种子是通过CPU的热噪声、读写磁盘的字节、网络流量等各种随机事件产生的“熵”。

在密码学中，安全的随机数非常重要。如果使用不安全的伪随机数，所有加密体系都将被攻破。因此，时刻牢记必须使用`SecureRandom`来产生安全的随机数。

 需要使用安全随机数的时候，必须使用SecureRandom，绝不能使用Random！

### 小结

Java提供的常用工具类有：

- Math：数学计算
- Random：生成伪随机数
- SecureRandom：生成安全的随机数