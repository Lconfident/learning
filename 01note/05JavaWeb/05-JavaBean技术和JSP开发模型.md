### javaBean

HTML代码与Java代码相分离，将Java代码单独封装成一个处理某种业务逻辑的类，然后在JSP页面中调用此类，可以降低HTML和Java之间的耦合度，简化JSP页面

这种与HTML代码相分离且使用Java代码封装的类，就是JavaBean组件

- JavaBean必须具有一个无参的构造函数
- 属性必须私有化
- 私有化属性必须通过public类型的方法暴露给其他程序

### JSP开发模型

使用JSP开发Web应用程序，有两种开发模型可选择，Model1和Model2

- JSP Model1：JSP实现流程控制和页面显示，JavaBean对象封装数据和业务逻辑
- JSP Model2：MVC（Model-View-Controller，模型JavaBean、视图View、控制器Controller）