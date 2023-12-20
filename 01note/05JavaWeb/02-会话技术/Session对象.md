Servlet的另一种会话技术——Session，将数据保存到数据库

## 什么是Session

当人们去医院就诊时，就诊病人需要办理医院的就诊卡，就诊卡上只有卡号，没有其他信息。但是病人每次就诊时出示就诊卡，医务人员就会根据卡号查询到病人的就诊信息。Seesion技术类似于卡号和医院为每位病人保留病历档案的过程。

当浏览器访问Web服务器时，Servlet容器就会创建一个Session对象和ID属性，Session对象就相当于病历档案，ID就相当于卡号。当客户端后续访问服务器时，只要将ID传递给服务器，服务器就能判断出请求是哪个客户端发送的，从而选择与之相对应的Session对象为其服务。

此外，Session还具有更高的安全性，它将关键数据保存在服务器中

## HttpSessionAPI

Session与每个请求消息紧密相关

获取Session对象

```java
public HttpSession getSession(boolean create)
public HttpSession getSession()
/**
    上面两个方法都用于返回与当前请求相关的HttpSession对象
第一个方法根据传递的参数判断是否创建新的HttpSession对象。如果参数为true，则在相关HttpSession不存在时创建并返回新的HttpSession对象，否则不创建新的HttpSession对象，而是返回null
第二个方法相当于第一个方法参数为true的情况
*/
```

```java
String getID()
void setAttribute(String name,Object value)
String getAttribute()
void removeAttribute(String name)

void 
```

## Session 的生命周期

1. Session生效
   Session在用户第一次访问服务器时创建。需要注意的是，只有访问JSP、Servlet等程序时才会创建Session。只访问HTML、IMAGE等静态资源并不会创建Session
2. Session失效
   两种方式失效，“超时限制”和强制失效
   （1）“超时限制”：在一定时间内，如果某个客户端一直没有请求访问，Web服务器就会认为已经结束请求，并且将该客户端会话所对应的HttpSession对象变成垃圾对象，等待垃圾收集器将其从内存中彻底清楚
   （2）强制失效：`HttpSession session = request.getSession(); session.invalidate();`
   注销该`request`的所有`session`