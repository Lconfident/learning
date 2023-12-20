JSP为了获取Servlet域对象中存储的数据，经常需要写很多Java代码，导致JSP页面混乱。为了降低JSP页面的复杂度，增加代码的重用性，Sun公司制定了一套标准标签库JSTL，同时为了获取数据，JSP 2.0规范提供了EL（表达语言），来降低开发难度

## EL

`Expression Language`表达式语言

EL简化JSP开发中的对象引用，从而规范代码，增加程序的可读性和可维护性

### 语法格式

`${ 表达式 }`

`${username}$`

当域对象不存在时，使用EL获取里面的值时返回空字符串；而是用Java方式获取时，如果返回的是null，会报空指针异常

### El中的标识符

大小写字母、数字、下划线组成

- 不能以数字开头
- 不能是EL的关键字
- 不能是EL的隐式对象
- 不能包含单双引号、减号、正斜线（`/`）等特殊字符

### 关键字

```
and or not eq ne lt gt le ge true false null instanceof empty div mod
```

### 变量

El中的变量就是一个基本的存储单元，不用事先定义就可以直接使用。El可以将变量映射到一个对象上

```
${product}
通过上面这个表达式考研访问变量product的值
```

### 常量

1. 布尔常量
2. 整型常量
3. 浮点数常量
4. 字符串常量
5. Null常量，用于表示变量引用的对象为空，它只有一个值，用null表示

### EL访问数据

1. 点运算符
   `${customer.name}$`

2. 中括号运算符
   当获取对象属性名中包含一些特殊符号，例如`-`，就只能用中括号运算符访问该属性

   `${user["My-name"]}`

   访问数组

   `${users[0]}`

### 运算符

1. 算术运算符
2. 比较运算符
3. 逻辑运算符
4. empty运算符，判断对象是否为空
5. 条件运算符
6. `()`运算符

## El隐式对象

11个隐式对象，类似于JSP的内置对象，可以直接通过对象名进行操作

### `pageContext`对象

### `Web`域对象 

`pageScope、requestScope、sessionScope、applicationScope`

### 请求参数的隐式对象

`param、paramValues`

### Cookie对象

### inirParam对象

## JSTL

### 概述

JSTL，全称为`JSP Standard Tag Library`，是一种用于Java服务器页（JSP）的标准标记库。它包含一组JSP标记，可以简化JSP页面的开发和维护。

JSTL从JSP页面中移除Java代码，将其替换为标记。这有助于开发人员将业务逻辑和界面页面代码分离。JSTL标记分为四个功能集：Core，XML，Internationalization，和SQL。Core标记用于控制流程和数据输出，XML标记用于处理XML文档， Internationalization标记用于国际化，SQL标记用于访问数据库。

JSTL是Java EE平台的一部分，因此它可以在Java应用程序服务器上运行，如Apache Tomcat、Glasfish和JBoss。它可以与其他Java技术、框架和库结合使用，例如JavaServer Faces（JSF）。

> 包含五大类
>
> 1. 核心标签库
> 2. 国际标签库
> 3. SQL标签库
> 4. XML标签库
> 5. 函数标签库

在使用标签库之前，必须在JSP页面的顶部使用`<%@taglib%>`指令定义引用的标签库和访问前缀

