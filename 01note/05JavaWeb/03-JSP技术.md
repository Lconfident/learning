### 什么是JSP

`JSP`(`Java Servlet Page`，Java服务器页面)

是Servlet更高级别的扩展

> JSP文件=HTML+Java
>
> HTML负责静态内容显示
>
> Java实现动态内容的显示
>
> JSP文件通过Web服务器的Web容器编译成一个Servlet，用于处理各种请求

### JSP运行原理

JSP的工作模式是请求/响应模式，客户端发出HTTP请求，JSP程序收到请求后处理并返回处理结果

一个JSP文件第一次被请求时，JSP容器把该文件转换成Servelt，而这个容器本身也是一个Servlet

> JSP运行过程：
>
> 1. 客户端发送请求，请求访问JSP文件
> 2. JSP容器先将JSP文件转换成一个Java源程序（Java Servlet 源程序），在转换过程中，如果发现存在任何语法错误，则中断转换过程，并向服务端和客户端返回出错信息
> 3. 如果转换成功，则JSP容器将生成的Java源文件编译成相应的字节码文件(`*.class`文件)，该文件就是一个Servlet，Servlet容器会像处理其他Servlet一样来处理它
> 4. 由Servlet容器加载转换后的Servlet类（`*.class`文件）创建一个该Servlet的实例，斌执行Servlet的`jspInit()`方法完成初始化。（这个初始化在Servlet的整个生命周期中只执行一次）
> 5. JSP容器执行`jspService()`方法处理客户端的请求。对于灭一个请求，JSP容器都会创建一个新的线程来处理它。多个客户端同时请求该JSP文件，则JSP容器会创建多个线程，一一对应
> 6. 如果JSP文件被修改，则服务器根据设置决定是否对该文件进行重新编译。若重新编译，则使用重新编译后的结果取代内存中常驻的Servlet实例
> 7. 虽然JSP效率很高，但是第一次调用需要转换和编译，会产生一些延迟
> 8. 当请求处理完成后，响应对象由JSP容器接收，并将HTML格式的响应信息发送到客户端

### JSP基本语法

#### JSP 基本构成

包括指令标识、HTML代码、JavaScript代码、嵌入的Java代码、注释和JSP动作标识等内容

#### JSP脚本元素

- JSP Scriptlet
- 声明标识
- JSP表达式

```java
<%! %>定义属性和方法
<%  %>输出内容
<%= %>表达式，结果转化成字符串
```

#### JSP注释

1. 带有JSP表达式的注释

   ```java
   // 单行注释
   
   /*
   	多行注释1
   	多行注释2
   	多行注释3
   */
   
   /**
   	*文档注释
   */
   ```

2. 隐藏注释

   HTML注释在浏览器的源代码中可被看到，这些注释不太安全。JSP提供了隐藏注释，隐藏注释不仅在浏览器页面看不到，在查看页面源代码也看不到，所以隐藏注释具有较高安全性

   ```html
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>JSP隐藏注释</title>
   </head>
   <body>
   <!--这是HTML注释-->
   <%--这是JSP隐藏注释--%>
   </body>
   </html>
   ```

3. 动态注释

    动态的HTML注释文本

   ```html
   <%@ page import="java.util.Date" %>
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>动态注释</title>
   </head>
   <body>
   <!-- <%=new Date()%> -->
   </body>
   </html>
   ```

### JSP指令

为了设置JSP页面的一些信息，Sun公司提供了JSP指令

#### page指令

```html
<@ page 属性名1='属性值1' 属性名2='属性值2' %>

page指明指令名称
属性指定JSP页面的某些特性
```

相关属性，除了`import`属性外，其他属性只能出现一次，否则编译失败

```html
<%@ page language="java" contentType="text/html; chartset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page isErrorPage="false"%>
```

#### include指令

在实际开发中，需要在JSP页面中包含另一个JSP页面，这是可以通过`include`指令实现

```html
<%@ include file="date.jsp" %>
```

插入文件的路径易班不以`/`开头，而是使用相对路径

#### taglib指令

在JSP文件中，考研通过`taglib`指令表示该页面中所用的标签库，同时引用标签库并指定标签的前缀

`<%@ taglib prefix="tagPrefix" uri="http://java.sun.com/jsp/jstl/core">`

### JSP动作元素

用于控制JSP的行为，执行一些常见的JSP页面动作

通过动作元素可以实现使用多行Java代码才能实现的效果，例如包含页面文件、实现请求转发等

#### 包含文件元素`<jsp:include>`

用于向当前页面引入其他文件（动态、静态文件皆可）

`<jsp:include page="URL" flush="true|false" />`

- page：相对路径
- flush：指定是否将当前页面的输出内容刷新到客户端。默认情况下，flush属性的值为false。

原理：将被包含页面编译处理后的**结果**包含在当前页面中

> `include`指令与`<jsp:include>`动作元素的区别
>
> - `include`指令通过file属性指定被包含的文件，file属性不支持任何表达式；`<jsp:include>`动作元素通过page属性指定，page属性支持JSP表达式
> - `include`指令，被包含文件内容会原封不动地插入包含页中，然后JSP编辑器再将合成的文件编译成一个Java文件；`<jsp:include>`动作元素执行被包含文件时，程序会将请求转发到被包含页面，并将执行结果输出到浏览器中，然后返回包含页面中，继续执行后续代码。因为服务器执行的是多个文件，所以如果一个页面包含多个文件，JSP编辑器会分别对被包含文件进行编译。
> - `include`指令包含文件时，因为被包含文件最终会生成一个文件，所以在被包含文件、包含文件中不能有重复的变量名或方法；`<jsp:include>`动作元素包含文件时，因为每个文件是单独编译的，所以被包含文件和包含文件中的重名变量和方法是不冲突的。

#### 请求转发元素`<jsp:forward>`

将当前请求转发到其他Web资源

执行请求转发之后，当前页面不再执行，而是执行该元素指定的目标页面

 ```html
<jsp:forward page="relativeURL" />
 ```

