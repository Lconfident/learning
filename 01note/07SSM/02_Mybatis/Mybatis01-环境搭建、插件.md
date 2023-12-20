## Mybatis环境搭建

### Mybatis简介

[Mybatis3简介](https://mybatis.org/mybatis-3/zh/index.html?spm=wolai.workspace.0.0.661623065ABOBu)

MyBatis最初是Apache的一个开源项目iBatis, 2010年6月这个项目由Apache Software Foundation迁移到了Google Code。随着开发团队转投Google Code旗下， iBatis3.x正式更名为MyBatis。代码于2013年11月迁移到Github。

MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

`本次学习使用：3.5.11版本` 

### 持久层框架对比

-JDBC
 -SQL 夹杂在Java代码中耦合度高，导致硬编码内伤
 -维护不易且实际开发需求中 SQL 有变化，频繁修改的情况多见
 -代码冗长，开发效率低
-Hibernate 和 JPA
 -操作简便，开发效率高
 -程序中的长难复杂 SQL 需要绕过框架
 -内部自动生成的 SQL，不容易做特殊优化
 -基于全映射的全自动框架，大量字段的 POJO 进行部分映射时比较困难
 -反射操作太多，导致数据库性能下降
-MyBatis
 -轻量级，性能出色
 -SQL 和 Java 编码分开，功能边界清晰。Java代码专注业务、SQL语句专注数据
 -开发效率稍逊于 Hibernate，但是完全能够接受

开发效率：`Hibernate>Mybatis>JDBC`

运行效率：`JDBC>Mybatis>Hibernate`

### 快速入门

1. 准备数据库

```mysql
CREATE DATABASE `mybatis-example`;

USE `mybatis-example`;

CREATE TABLE `t_emp`(
  emp_id INT AUTO_INCREMENT,
  emp_name CHAR(100),
  emp_salary DOUBLE(10,5),
  PRIMARY KEY(emp_id)
);

INSERT INTO `t_emp`(emp_name,emp_salary) VALUES("tom",200.33);
INSERT INTO `t_emp`(emp_name,emp_salary) VALUES("jerry",666.66);
INSERT INTO `t_emp`(emp_name,emp_salary) VALUES("andy",777.77);
```

2. 项目搭建和准备

   a.项目搭建

    ![项目搭建](https://secure2.wostatic.cn/static/vF2ubwBhNSqJE4ZkVSeTxL/image.png?auth_key=1699931867-uqvSgk5AUn7B4MZBnFnvna-0-b80893e0cb94ea4f505b9a917ba9779a&image_process=resize,w_572&file_size=60303)

   b.依赖导入

   pom.xml

   ```xml
   <dependencies>
     <!-- mybatis依赖 -->
     <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
         <version>3.5.11</version>
     </dependency>
   
     <!-- MySQL驱动 mybatis底层依赖jdbc驱动实现,本次不需要导入连接池,mybatis自带! -->
     <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>8.0.25</version>
     </dependency>
   
     <!--junit5测试-->
     <dependency>
         <groupId>org.junit.jupiter</groupId>
         <artifactId>junit-jupiter-api</artifactId>
         <version>5.3.1</version>
     </dependency>
   </dependencies>
   ```

   c.实体类准备

   ```java
   public class Employee {
   
       private Integer empId;
   
       private String empName;
   
       private Double empSalary;
       
       //getter | setter
   }
   ```

3. 准备Mapper接口和MapperXML文件 

   MyBatis 框架下，SQL语句编写位置发生改变，从原来的Java类，改成XML或者注解定义！

   推荐在XML文件中编写SQL语句，让用户能更专注于 SQL 代码，不用关注其他的JDBC代码。

   如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码！！

   一般编写SQL语句的文件命名：XxxMapper.xml  Xxx一般取表名！！

   Mybatis 中的 Mapper 接口相当于以前的 Dao。但是区别在于，Mapper 仅仅只是建接口即可，我们不需要提供实现类，具体的SQL写到对应的Mapper文件，该用法的思路如下图所示：

   ![Mapper](https://secure2.wostatic.cn/static/bkCL19U4AA9SGMm4kuRXT3/image.png?auth_key=1699931867-ntJ82VR5NQBm7an8o4MQXz-0-d7e8db3027cd0d8a12dc2ca8583fec8f&image_process=resize,w_926&file_size=39238)

   a.定义mapper接口

   包：`com.atguigu.mapper`

   ```java
   package com.atguigu.mapper;
   
   import com.atguigu.pojo.Employee;
   
   /**
    * t_emp表对应数据库SQL语句映射接口!
    *    接口只规定方法,参数和返回值!
    *    mapper.xml中编写具体SQL语句!
    */
   public interface EmployeeMapper {
   
       /**
        * 根据员工id查询员工数据方法
        * @param empId  员工id
        * @return 员工实体对象
        */
       Employee selectEmployee(Integer empId);
       
   }
   ```

   b.定义mapper xml

   位置： `resources/mappers/EmployeeMapper.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!-- namespace等于mapper接口类的全限定名,这样实现对应 -->
   <mapper namespace="com.atguigu.mapper.EmployeeMapper">
       
       <!-- 查询使用 select标签
               id = 方法名
               resultType = 返回值类型
               标签内编写SQL语句
        -->
       <select id="selectEmployee" resultType="com.atguigu.pojo.Employee">
           <!-- #{empId}代表动态传入的参数,并且进行赋值!后面详细讲解 -->
           select emp_id empId,emp_name empName, emp_salary empSalary from 
              t_emp where emp_id = #{empId}
       </select>
   </mapper>
   ```

   注意：

   -方法名和SQL的id一致
   -方法返回值和resultType一致
   -方法的参数和SQL的参数一致
   -接口的全类名和映射配置文件的名称空间一致

4. 准备Mybatis配置文件

   mybatis框架配置文件： 数据库连接信息，性能配置，mapper.xml配置等！

   习惯上命名为 `mybatis-config.xml`，这个文件名仅仅只是建议，并非强制要求。将来整合 Spring 之后，这个配置文件可以省略，所以操作时可以直接复制、粘贴。

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
     PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
     "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   
     <!-- environments表示配置Mybatis的开发环境，可以配置多个环境，在众多具体环境中，使用default属性指定实际运行时使用的环境。default属性的取值是environment标签的id属性的值。 -->
     <environments default="development">
       <!-- environment表示配置Mybatis的一个具体的环境 -->
       <environment id="development">
         <!-- Mybatis的内置的事务管理器 -->
         <transactionManager type="JDBC"/>
         <!-- 配置数据源 -->
         <dataSource type="POOLED">
           <!-- 建立数据库连接的具体信息 -->
           <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/mybatis-example"/>
           <property name="username" value="root"/>
           <property name="password" value="root"/>
         </dataSource>
       </environment>
     </environments>
   
     <mappers>
       <!-- Mapper注册：指定Mybatis映射文件的具体位置 -->
       <!-- mapper标签：配置一个具体的Mapper映射文件 -->
       <!-- resource属性：指定Mapper映射文件的实际存储位置，这里需要使用一个以类路径根目录为基准的相对路径 -->
       <!--    对Maven工程的目录结构来说，resources目录下的内容会直接放入类路径，所以这里我们可以以resources目录为基准 -->
       <mapper resource="mappers/EmployeeMapper.xml"/>
     </mappers>
   
   </configuration>
   ```

5. 运行和测试

```java
/**
 * projectName: com.atguigu.test
 *
 * description: 测试类
 */
public class MyBatisTest {

    @Test
    public void testSelectEmployee() throws IOException {

        // 1.创建SqlSessionFactory对象
        // ①声明Mybatis全局配置文件的路径
        String mybatisConfigFilePath = "mybatis-config.xml";

        // ②以输入流的形式加载Mybatis配置文件
        InputStream inputStream = Resources.getResourceAsStream(mybatisConfigFilePath);

        // ③基于读取Mybatis配置文件的输入流创建SqlSessionFactory对象
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2.使用SqlSessionFactory对象开启一个会话
        SqlSession session = sessionFactory.openSession();

        // 3.根据EmployeeMapper接口的Class对象获取Mapper接口类型的对象(动态代理技术)
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);

        // 4. 调用代理类方法既可以触发对应的SQL语句
        Employee employee = employeeMapper.selectEmployee(1);

        System.out.println("employee = " + employee);

        // 4.关闭SqlSession
        session.commit(); //提交事务 [DQL不需要,其他需要]
        session.close(); //关闭会话

    }
}
```

说明：

-SqlSession：代表Java程序和数据库之间的会话。（HttpSession是Java程序和浏览器之间的会话）
-SqlSessionFactory：是“生产”SqlSession的“工厂”。
-工厂模式：如果创建某一个对象，使用的过程基本固定，那么我们就可以把创建这个对象的相关代码封装到一个“工厂类”中，以后都使用这个工厂类来“生产”我们需要的对象。

6. SqlSession和HttpSession区别
   -HttpSession：工作在Web服务器上，属于表述层。
    -代表浏览器和Web服务器之间的会话。
   -SqlSession：不依赖Web服务器，属于持久化层。
    -代表Java程序和数据库之间的会话。

![SqlSession](https://secure2.wostatic.cn/static/xb7Pyrm8YMF3qK6c7Jr5R3/image.png?auth_key=1699931867-2UDsGTVUyoL3B2GWUm4kfd-0-883a665adc7107deb39a2850489dcd32&image_process=resize,w_926&file_size=17222)

## 插件

-`JBLJavaToWeb`
-`MyBatisX`
-`maven-search`