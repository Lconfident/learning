### `HTTPServletResponse`

`Servlet API`中，定义了一个`HTTPServletResponse`接口，继承自`ServletResponse`接口，专门用于封装HTTP响应消息。

HTTP响应消息分为【响应消息状态行】、【响应消息头】、【响应消息体】三部分

所以`HTTPServletResponse`接口中定义了【向客户端发送响应状态码】、【响应消息头】、【响应消息体】的方法

### 发送响应状态码

Sevlet向客户端发送响应消息时，需要在响应消息中发送状态码。状态码代表客户端请求服务器的结果。

> 正常情况下，状态码为200

#### `setStatus(int status)方法`

设置HTTP响应状态码，并生成响应状态行。

> 因为响应状态行中的状态描述信息直接与状态码相关

#### `sendError(int sc)方法`

发送表示错误信息的状态码

#### `sendError(int code,String message)方法`

设置状态码，向客户端发出一条错误信息

服务器默认创建一个HTML格式的错误服务页面作为响应结果，其中包含参数`message`指定的文本信息，内容类型为"`text/html`"，保留cookies和其他未修改的响应头信息。

>  如果一个对应传入的错误码的错误页面已经在web.xml中声明，那么这个声明的错误页面会将优先建议的message参数服务于客户端。

### 发送响应消息头

#### 设置头字段

|                   方法                   | 描述                                                         |
| :--------------------------------------: | ------------------------------------------------------------ |
| void addHeader(String name,String value) | 这两个方法都用于设置HTTP协议的响应头字段，参数name用于指定响应头字段的名称，value用于指定其值。 |
| void setHeader(String name,String value) | 不同的是，addHeader（）方法可以增加同名的响应头字段，而setHeader（）方法则会覆盖同名的头字段。 |
| void addIntHeader(String name,int value) | 这两个方法专门用于设置包含整数值的响应头字段。               |
| void setIntHeader(String name,int value) | 避免了需要将int类型的设置值转换为String类型的麻烦。          |

#### 设置字符编码

|                   方法                    | 描述                                                         |
| :---------------------------------------: | ------------------------------------------------------------ |
|      void setContextLength(int len)       | 用于设置响应消息的实体的大小，单位为字节。对于HTTP来说，就是设置Context-Length响应头字段的值。 |
|     void setContentType(String type)      | 用于设置Servlet输出内容的MIME类型，对于HTTTP来说，就是设置Context-Type的值，如果响应的内容为文本，还可以设置字符编码text/html;charset=UTF-8 |
|        void setLocale(Locale loc)         | 用于设置响应消息的本地化信息，对于HTTP来说，就是Context-Language响应头字段和Context-Type头字段中的字符集编码部分。 |
| void setCharacterEncoding(String charset) | 用于设置输出内容使用的字符编码，对于HTTP协议来说，及时设置Content-Type头字段中的字符集编码部分。 |

### 发送响应消息体

HTTP响应消息中，大量数据都是通过响应消息体传递的

#### `getOutputStream（）方法`

该方法所获取的字节输出流对象为`ServletOuputSream`类型

`ServletOutputSream`是`OutputStream`的子类，可以直接输出字节数组中的二进制数据

#### `getWriter()方法`

该方法所获取的字符输出流对象为`PrintWriter`类型

可以输出字符文本内容

### `HTTPServletResponse`应用

#### 实现重定向

- 针对客户端的请求，一个Servlet类可能无法完成全部工作，这时，可以使用请求重定向来完成
- 所谓**重定向**，是指Web服务器接收到到客户端的请求后，由于某些条件限制，不能访问当前请求URL所指向的Web资源，而是指定了一个新的资源路径，让客户端重新发送请求。
- 实现重定向，`HTTPServletResponse`接口定义了一个`sendRedirect（）`方法，该方法用于生成302响应码和Location响应头，从而通知客户端重新访问Location响应头中指定的URL

> public void sendRedirect(java.lang.String location) throws java.io.IOException
>
> 参数location可以使用相对URL，Web服务器会自动将相对URL翻译成绝对URL，再生成Location字段

