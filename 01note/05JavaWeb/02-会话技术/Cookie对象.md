### 会话及会话技术

当用户使用浏览器访问Web应用时，在通常情况下，浏览器需要对用户的状态进行跟踪

> 例如，用户在结算商品时，Web服务器必须根据用户的身份找到该用户所购买的商品

在Web开发中，服务器跟踪用户信息的技术成为**会话技术**

**会话**

即一个客户端与Web服务器之间连续发生的一系列请求和响应过程

> 例如，用户在某网站的整个购买过程就是会话

为了保存会话过程中产生的数据，`Servlet`提供了两个用于保存会话数据的对象，`Cookie和Session`

### Cookie对象

`Cookie`是服务器将会话过程中的数据保存到**用户的浏览器**中，与响应消息有关

#### 过程

当用户通过浏览器第一次访问Web服务器时，服务器在响应消息中增加`Set-Cookie`响应头字段，例如用户信息和商品信息，将用户信息以`Cookie`形式发送给浏览器。

用户浏览器一旦接受浏览器发送的`Cookie`信息，就会保存在自身的缓存区中。

这样，当下次该浏览器访问服务器时，会在请求头中将用户信息以`Cookie`形式发送给服务器，以便服务器分辨出当前请求是由哪个用户发出的

> `Set-Cookie`响应头字段格式：
>
> `Set-Cookie: user=itcast; path=/;`
>
> `Cookie`必须以键值对形式存在，属性可以有多个，属性之间用英文封号和空格隔开

### Cookie API

#### 构造方法

`public Cookie (java.lang.String name,java.lang.String value)`

参数`name`指定名称，`value`指定`Coolie`的值

`Cookie`一旦创建，名称不能再改变，值允许创建后修改

#### 常用方法

```java
setMaxAge(int expiry)
/**
设置Cookie在浏览器上保持有效的秒数
如果为负数，浏览器会将Cookie信息保存在缓存中，当浏览器关闭时，Cookie信息会被删除
设置为0，浏览器会立即删除Cookie信息
*/
setPath(String uri)和getPath()
/**
针对Cookie的Path属性
如果没有设置该属性，那么该Cookie只对当前访问路径所属的目录及其子目录有效
如果想让某个Cookie对站点的所有目录下的访问路径都有效，就设置为"/"
*/
String getName()
void setValue(String newValue)
String getValue()
void setMaxAge(int expity)
```



### 实例

```java
Data  data = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String str_time = sdf.format(date);
str_date= URLEncoder.encode(str_date,"utf-8");

Cookie cookie = new Cookie("lastTame",str_date);
//30分钟
cookie.setMaxAge(30*60)
//由于Cookie 是服务器响应的，所以是 response,addCookie（cookie）
response.addCookie(cookie);
```

