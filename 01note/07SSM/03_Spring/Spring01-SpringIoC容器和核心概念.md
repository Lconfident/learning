**SpringIoC容器和核心概念**

------

### 组件和组件管理概念

#### 组件

![组件概念](https://secure2.wostatic.cn/static/8LSuy5YWXmufYmPWM894rN/image.png?auth_key=1699952040-nAfDK3ThLBetKLS6sQvHbQ-0-525745eddc03863297c707d6e2016746&image_process=resize,w_772&file_size=31162)

#### Spring充当管理的角色（IoC）

> 组件可以完全交给Spring 框架进行管理，Spring框架替代了程序员原有的new对象和对象属性赋值动作等！

我们只需要编写元数据（配置文件）告知Spring 管理哪些类组件和他们的关系即可！

Spring 充当一个组件容器，创建、管理、存储组件，减少了我们的编码压力，让我们更加专注进行业务编写！ 

#### 优势

- 降低耦合
- 提高代码的可重用性和可维护性
- 方便配置和管理

### Spring IoC容器和容器实现

#### 普通容器和复杂容器

**普通容器**：

![普通容器](https://api.wolai.com/v1/proxy/image?src=http%3A%2F%2Fheavy_code_industry.gitee.io%2Fcode_heavy_industry%2Fassets%2Fimg%2Fimg002.6e874877.png&spaceId=fqkGyHKKxSnzkhVZnoSxhC&userId=8NXf6KydrDbtt68KunQyt6&image_process=resize,w_692)

程序中的普通容器

- 数组
- 集合：List
- 集合：Set

**复杂容器**：

![复杂容器](https://api.wolai.com/v1/proxy/image?src=http%3A%2F%2Fheavy_code_industry.gitee.io%2Fcode_heavy_industry%2Fassets%2Fimg%2Fimg003.6f9c041c.png&spaceId=fqkGyHKKxSnzkhVZnoSxhC&userId=8NXf6KydrDbtt68KunQyt6&image_process=resize,w_695)

> 政府管理我们的一生，生老病死都和政府有关。

程序中的复杂容器

Servlet 容器能够管理 Servlet(init,service,destroy)、Filter、Listener 这样的组件的一生，所以它是一个复杂容器。

| 名称       | 时机                                                         | 次数 |
| ---------- | ------------------------------------------------------------ | ---- |
| 创建对象   | 默认情况：接收到第一次请求  修改启动顺序后：Web应用启动过程中 | 一次 |
| 初始化操作 | 创建对象之后                                                 | 一次 |
| 处理请求   | 接收到请求                                                   | 多次 |
| 销毁操作   | Web应用卸载之前                                              | 一次 |

#### SpringIoC容器介绍

Spring IoC 容器，负责实例化、配置和组装 bean（组件），读取配置元数据来获取指令

配置元数据以 XML、Java 注解或 Java 代码形式表现 

![SpringIoC容器](https://secure2.wostatic.cn/static/mFt9PQ2ggCqB193CC57AKi/image.png?auth_key=1699952042-xqVajVLoLDseogUn27KNU3-0-07c4fb95c9930a6f1e043c4ec203273b&image_process=resize,w_687&file_size=11514)

#### SpringIoC容器接口和实现类

**SpringIoC容器接口：**

`BeanFactory` ：SpringIoC容器标准化超接口，能够管理任何类型的对象！

`ApplicationContext` ：是 `BeanFactory` 的子接口。

**ApplicationContext容器实现类：**

| 类型名                             | 简介                                                         |
| ---------------------------------- | ------------------------------------------------------------ |
| ClassPathXmlApplicationContext     | 通过读取类路径下的 XML 格式的配置文件创建 IOC 容器对象（项目的类路径下resources） |
| FileSystemXmlApplicationContext    | 通过文件系统路径读取 XML 格式的配置文件创建 IOC 容器对象     |
| AnnotationConfigApplicationContext | 通过读取Java配置类创建 IOC 容器对象                          |
| WebApplicationContext              | 专门为 Web 应用准备，基于 Web 环境创建 IOC 容器对象，并将对象引入存入 ServletContext 域中。 |

#### SpringIoC容器管理配置方式

- xml配置方式
- 注解方式
- Java配置类方式

### Spring IoC/DI 概念

#### IoC容器

Spring IoC 容器，负责实例化、配置和组装 bean（组件）核心容器。容器通过读取配置元数据来获取有关要实例化、配置和组装组件的指令。 

#### IoC（Inversion of control）控制反转

IoC主要针对对象的创建和调用控制而言的

> 当应用程序需要使用一个对象时，不再是应用程序直接创建new一个对象，而是由IoC容器来创建和管理。即控制权由应用程序转移到IoC容器，“反转”了控制权
>
> 这种操作是通过依赖查找的方式实现的

#### DI（Dependency Injection）依赖注入

DI主要针对在组件之间传递依赖关系的过程中，将依赖关系在容器内部进行处理。

> DI通过XML配置文件或注解的方式实现，提供三种形式的依赖注入：
>
> - 构造函数注入
> - Setter注入
> - 接口注入