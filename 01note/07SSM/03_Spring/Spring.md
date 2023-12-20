**Spring IoC实践和应用**

------

## Spring IoC/DI实现步骤

1. 配置元数据（配置）
2. 实例化IoC容器
3. 获取Bean（组件）

## 基于 XML 配置方式组件管理

### 基于xml的ioc配置

学习如何定义XML配置文件，声明组件类信息，然后交给Sping的IoC容器进行组件管理

![xml的ioc配置](https://api.wolai.com/v1/proxy/image?src=http%3A%2F%2Fheavy_code_industry.gitee.io%2Fcode_heavy_industry%2Fassets%2Fimg%2Fimg006.c8bae859.png&spaceId=fqkGyHKKxSnzkhVZnoSxhC&userId=8NXf6KydrDbtt68KunQyt6&image_process=resize,w_688)

> 实现步骤：
>
> 1. 准备项目
>
>    1. 创建maven工程
>    2. 导入SpringIoc相关依赖（Spring context，junit5）
>
> 2. 基于无参构造函数
>
>    1. 准备组件类
>
>       ```java
>       package com.atguigu.ioc;
>       
>       
>       public class HappyComponent {
>       
>           //默认包含无参数构造函数
>       
>           public void doWork() {
>               System.out.println("HappyComponent.doWork");
>           }
>       }
>       ```
>
>    2. xml配置文件编写
>
>       ```xml
>       <!--
>       	1.<bean - 一个组件对象
>       		id 组件的标识 唯一 方便后期读取
>       		class 组件的类的全限定符
>       -->
>       <bean id="happyComponent" class="com.atguigu.ioc.HappyComponent"/>
>       ```
>
> 3. 基于静态工厂实例化
>
>    1. 准备组件类
>
>       ```java
>       package com.atguigu.ioc_01;
>       
>       public class ClientService {
>         private static ClientService clientService = new ClientService();
>         private ClientService() {}
>       
>         public static ClientService createInstance() {
>         
>           return clientService;
>         }
>       }
>       ```
>
>    2. xml配置文件编写
>
>       ```xml
>        <!-- 
>       	2.静态工厂如何声明ioc配置
>                   <bean
>                       id="组件的标识"
>                       class="工厂类的全限定符"
>                       factory-method="静态工厂方法"
>       -->
>           <bean id="clientService" class="com.atguigu.ioc_01.ClientService" factory-method="createInstance" />
>       ```
>
> 4. 基于实例工厂实例化
>
>    1. 准备组建类
>
>       ```java
>       public class DefaultServiceLocator {
>       
>         private static ClientServiceImplclientService = new ClientServiceImpl();
>       
>         public ClientService createClientServiceInstance() {
>           return clientService;
>         }
>       }
>       ```
>
>    2. xml配置文件编写 
>
>       ```xml
>       <!-- 3.非静态工厂如何声明ioc配置
>                   配置工厂类的组件信息
>       -->
>           <bean id="defaultServiceLocator" class="com.atguigu.ioc_01.DefaultServiceLocator" />
>           <!-- 通过指定静态工厂对象和方法名 来配置生成的ioc信息 -->
>           <bean id="clientService2" factory-bean="defaultServiceLocator" factory-method="createClientServiceInstance"/>
>       ```

### 基于xml的di配置

1. 目标
   通过配置文件,实现IoC容器中Bean之间的引用（依赖注入DI配置）
   主要涉及注入场景：基于构造函数的依赖注入和基于 Setter 的依赖注入

2. 思路
   ![xml的di配置](https://secure2.wostatic.cn/static/opfLAb8XnCZDyPMm9BuiMc/image.png?auth_key=1700060119-oX6cfZTvx9YvLyU28fGwGW-0-25c33ba2fc587e0bb9dc08d06423fdd5&image_process=resize,w_843&file_size=112759)

3. 单个构造参数

   1. 准备组件类

      ```java
      package com.atguigu.ioc_02;
      
      public class UserDao {
      }
      ```

      ```java
      package com.atguigu.ioc_02;
      
      public class UserService {
          
          private UserDao userDao;
      
          public UserService(UserDao userDao) {
              this.userDao = userDao;
          }
      }
      ```

   2. xml配置文件编写

      ```xml
          <!-- 引用和被引用的组件必须都在ioc容器中 -->
          <!-- 单个构造参数注入 -->
          <!-- 步骤1：将它们都存放入ioc容器 -->
          <bean id="userDao" class="com.atguigu.ioc_02.UserDao" />
      
          <bean id="userService" class="com.atguigu.ioc_02.UserService">
              <!-- 构造参数传值 di的配置
                  <constructor-arg 构造参数值的 di配置
                      value = 直接属性值 String name = "jerry"
                      ref = 引用    beanId值
               -->
              <constructor-arg ref="userDao"/>
          </bean>
      ```

      

4. 多个构造参数

   1. 准备组件类

      ```java
      package com.atguigu.ioc_02;
      
      public class UserService {
      
          private UserDao userDao;
      
          private int age;
      
          private String name;
      
          public UserService(UserDao userDao) {
              this.userDao = userDao;
          }
      
          public UserService(int age, String name, UserDao userDao) {
              this.userDao = userDao;
              this.age = age;
              this.name = name;
          }
      }
      ```

   2. xml文件编写

      ```xml
      <!-- 多个构造参数注入 -->
          <bean id="userService1" class="com.atguigu.ioc_02.UserService">
              <!-- 方案1：构造参数的顺序填写值 value 直接复赋值 ref 引用值 -->
              <constructor-arg value="18"/>
              <constructor-arg value="jerry"/>
              <constructor-arg ref="userDao"/>
          </bean>
      
          <bean id="userService2" class="com.atguigu.ioc_02.UserService">
              <!-- 方案2：构造参数的名字填写值 -->
              <constructor-arg name="age" value="18"/>
              <constructor-arg name="name" value="jerry"/>
              <constructor-arg name="userDao" ref="userDao"/>
          </bean>
      
          <bean id="userService3" class="com.atguigu.ioc_02.UserService">
              <!-- 方案3：构造参数的下角标填写值 -->
              <constructor-arg index="0" value="18"/>
              <constructor-arg index="1" value="jerry"/>
              <constructor-arg index="2" ref="userDao"/>
          </bean>
      ```

5. 基于setter方法依赖注入

   1. 准备组件类

      ```java
      package com.atguigu.ioc_02;
      
      public class MovieFinder {
      }
      
      package com.atguigu.ioc_02;
      
      public class SimpleMovieLister {
      
        private MovieFinder movieFinder;
        
        private String movieName;
      
        public void setMovieFinder(MovieFinder movieFinder) {
          this.movieFinder = movieFinder;
        }
        
        public void setMovieName(String movieName){
          this.movieName = movieName;
        }
      
        // business logic that actually uses the injected MovieFinder is omitted...
      }
      ```

   2. xml文件编写

      ```xml
          <bean id="movieFinder" class="com.atguigu.ioc_02.MovieFinder"/>
          <bean id="simpleMovieLister" class="com.atguigu.ioc_02.SimpleMovieLister">
              <!--
                  name -> 属性名setter方法 去掉set和首字母小写的值！ 调用set方法的名
                  ref|value 二选一 value="直接属性值" ref="其他bean的Id"
              -->
              <property name="movieFinder" ref="movieFinder"/>
              <property name="movieName" value="长江七号"/>
          </bean>
      ```

### 基于xml的ioc容器的创建

```xml
//方式1:实例化并且指定配置文件
//参数：String...locations 传入一个或者多个配置文件
ApplicationContext context = 
           new ClassPathXmlApplicationContext("services.xml", "daos.xml");
           
//方式2:先实例化，再指定配置文件，最后刷新容器触发Bean实例化动作 [springmvc源码和contextLoadListener源码方式]  
ApplicationContext context = 
           new ClassPathXmlApplicationContext();   
//设置配置配置文件,方法参数为可变参数,可以设置一个或者多个配置
iocContainer1.setConfigLocations("services.xml", "daos.xml");
//后配置的文件,需要调用refresh方法,触发刷新配置
iocContainer1.refresh();
```

### 容器的bean三种获取方式

```xml
        // IoC容器中获取bean
        // 1.由beanId直接获取 返回值类型Object 需要强转【不推荐】
        HappyComponent happyComponent = (HappyComponent) applicationContext.getBean("happyComponent");

        // 2.根据beanId，同时指定bean的类型Class
        HappyComponent happyComponent1 = applicationContext.getBean("happyComponent", HappyComponent.class);

        // 3.直接根据类型获取
        // 根据bean的类型获取，同一个类型，在ioc容器中只能有一个bean！！！
        // 如果ioc容器中存在多个同类型的Bean，会出现 NoUniqueBeanDefinitionException
        HappyComponent happyComponent2 = applicationContext.getBean(HappyComponent.class);

        happyComponent2.doWork();
```

### 组件周期方法

1. 组件准备

   ```java
   package com.atguigu.ioc_04;
   
   public class JavaBean {
   
       public void work(){}
   
       public void init() {
           System.out.println("JavaBean.init() 被调用");
       }
   
       public void clear() {
           System.out.println("clear() 被调用");
       }
   }
   ```

   

2. xml配置文件

   ```xml
   <!-- init-method = “初始化方法名”
            destroy-method = “销毁方法名”
            spring ioc 容器会在回应的时间节点回调对应的方法
   
       -->
       <bean id="javaBean" class="com.atguigu.ioc_04.JavaBean" init-method="init" destroy-method="clear"/>
   ```

3. 测试

   ```java
       @Test
       void work() {
           // 1.创建ioc容器 进行组件的实例化 -> init
           ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-04.xml");
           // 2.获取bean
           JavaBean javaBean = applicationContext.getBean(JavaBean.class);
           // 3.正常结束ioc容器，调用destroy，ioc释放
           applicationContext.close();
       }
   ```

默认单例模式

多例模式：`<bean id="javaBean2" class="com.atguigu.ioc_04.JavaBean2" scope="prototype"/>`



## 基于 注解 方式管理Bean



## 基于 配置类 方式管理Bean

## 三种配置方式总结

## 整合 Spring5-Test5 搭建测试环境