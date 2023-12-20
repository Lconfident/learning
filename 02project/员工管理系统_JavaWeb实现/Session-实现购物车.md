### 目标：实现购物车

以买蛋糕为例，实现购物车功能。

当用户使用浏览器访问某网站的蛋糕列表页面时，点击购买后，首先判断蛋糕是否存在，若蛋糕存在，加入购物车；否则返回蛋糕列表页面。

### 创建单个蛋糕的Cake类

定义`id`和`name`属性，分别表示蛋糕的编号和名称

默认构造方法，显式构造方法

`Getter`和`Setter`方法

```java
public class Cake {
    private static final Long serialVersionUID = 1L;
    private String id;
    private String name;

    public Cake() {
    }

    public Cake(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### 创建数据库模拟类

`CakeDB`类用于模拟保存所有蛋糕的数据库

`cake.values()`获得所有的蛋糕

`cake.get(id)`根据id指定的获取蛋糕

```java
public class CakeDB {
    private static Map<String, Cake> cake = new LinkedHashMap<String, Cake>();

    static {
        cake.put("1", new Cake("1", "A类蛋糕"));
        cake.put("2", new Cake("2", "B类蛋糕"));
        cake.put("3", new Cake("3", "C类蛋糕"));
        cake.put("4", new Cake("4", "D类蛋糕"));
        cake.put("5", new Cake("5", "E类蛋糕"));
    }

    //获得所有蛋糕
    public static Collection<Cake> getAll() {
        return cake.values();
    }

    //根据id指定的获取蛋糕
    public static Cake getCake(String id) {
        return cake.get(id);
    }
}
```

### 创建Servlet

#### 展示蛋糕列表

ListCakeServlet类

展示所有可购买蛋糕的列表，通过点击链接，可将蛋糕添加至购物车

```java
public class ListCakeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Collection<Cake> cakes = CakeDB.getAll();
        out.write("本站提供的蛋糕有：<br/>");
        for (Cake cake : cakes) {
            String url = "PurchaseServlet?id=" + cake.getId();
            out.write(cake.getName() + "<a href='" + url + "'>点击购买</a><br/>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

#### 获得用户购买的蛋糕	

`String id = resquest.getParameter("id"); `获得URL中的`id`信息

如果`id`为`null`的话，重定向到`ListCakeServlet` 页面

否则，从数据库中取出`cake`对象

#### 创建或获得用户的`Session`对象

从`session`对象中得到用户的购物车`cart`

如果是首次购买，需要创建一个新的购物车，将`cart`存入`session`对象

将蛋糕`cake`放进购物车`cart`

#### 重定向到购物车页面

创建Cookie存放Session的标识号

重定向到购物车页面

```java
@WebServlet(name = "PurchaseServlet", value = "/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得用户购买的商品
        String id = request.getParameter("id");
        if (id == null) {
            //如果id为null，重定向到ListCakeServlet 页面
            String url = "ListCakeServlet";
            response.sendRedirect(url);
            return;
        }
        Cake cake = CakeDB.getCake(id);
        //创建或获得用户的Session对象
        HttpSession session = request.getSession();
        //从Session对象中获得用户的购物车
        List<Cake> cart = (List) session.getAttribute("cart");
        if (cart == null) {
            //首次购买，为用户创建一个购物车（List模拟购物车）
            cart = new ArrayList<Cake>();
            //将购物车存入Session对象
            session.setAttribute("cart", cart);
        }
        //将商品存入购物车
        cart.add(cake);
        //创建Cookie存放Session的标识号
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(60 * 30);
        cookie.setPath("/Servlet");
        response.addCookie(cookie);
        //重定向到购物车页面
        String url = "CartServlet";
        response.sendRedirect(url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

####  购物车页面

`cart`代表购物车，`purFlag`代表用户是否买过蛋糕

- 如果`session`为`null`，那么`purFlag`为`false`
  - 如果购物车为`null`，那么`purFlag`为`false`
- 如果`purFlag`为`false`，则重定向到`ListServlet`页面
- 否则显示购买蛋糕的记录

```java
@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //变量cart引用用户的购物车
        List<Cake> cart = null;
        //变量purFlag标记用户是否买过商品
        boolean purFlag = true;
        //获得用户的session
        HttpSession session = request.getSession(false);
        //如果Session为null，则purFlag为false
        if (session == null) {
            purFlag = false;
        } else {
            //获得用户购物车
            cart = (List) session.getAttribute("cart");
            //如果购物车为null，则purFlag为false
            if (cart == null) {
                purFlag = false;
            }
        }

        /*
        如果purFlag为false，表明用户没有购买蛋糕，则重定向到ListServlet页面
         */
        if (!purFlag) {
            out.write("对不起！您还没有购买任何蛋糕！<br/>");
        } else {
            //否则显示用户购买蛋糕的信息
            out.write("您购买的蛋糕有：<br/>");
            double price = 0;
            for (Cake cake : cart) {
                out.write(cake.getName() + "<br/>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```



