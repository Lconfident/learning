`HTTPServletResquest对象`

`Servlet API`中，定义了一个`HTTPServletRequest`接口，它继承自`ServletRequest`接口，专门用于封装HTTP请求消息

### 获取请求行信息

请求消息的请求行信息中包含请求方法、请求资源名、请求路径等信息

### 获取请求头

```java
Enumeration getHeaderNames(String name) //返回一个Enumeration 集合对象
String getHeader(String name)  //获取一个指定字段的值
```

### 请求转发

利用Servlet的跳转吧一项任务按模块分开，Servlet的跳转要通过 `RequestDispatcher`接口的实例对象实现。

`HttpServletRequest`接口提供了`getRequestDispatcher()`方法用于获取`RequestDispatcher`对象

> `RequestDispatcher getRequestDispatcher (String path)`
>
> 返回某条路径所指定资源的`ResquestDispatcher`对象
>
> 参数必须以“`/`”开头，用于表示当前Web应用的根目录

获取到`RequestDispatcher`对象后，如果当前Web资源不想处理请求，`RequestDispatcher`接口提供了一个`forward()`方法，该方法可以将当前请求传递给其他Web资源，这种方式称为请求转发

> `forward(ServletRequest request,ServletResponse response)`

### 获取请求参数

```java
String getParameter(String name) //获取某个指定的参数
String[] getParameterValues(String name) //获取多个同名的参数
```

### 通过Request对象传递数据

```java
//setAttribute()方法
public void setAttribute(String name,Object o);

//getAttribute()方法
public Object getAttribute(String name);

//removeAttribute()方法
public void removeAttribute(String name);

//getAttributeNames()方法
public Enumeration getAttributeNames();
```

