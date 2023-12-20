## 前言

接触了Github之前，我就已经接触到markdown，但用的不是很多，但之后意外结识了Typora这个编辑器。

## Markdown

### 简介

> Markdown是一种轻量级标记语言，创始人是John Cruber。它允许人们使用易读易写的纯文本格式编写文档，然后转成有效的XHTML(或者HTML)文档。

### 为什么使用Markdown

对我而言：

1. 只需要写好内容本身就好了，不必考虑字体大小、颜色等等，因为Markdown中字体样式都与结构有关，无手动设置
2. Markdown语法简单
3. 很多地方都用Markdown，如CSDN、博客园、语雀、Github

### Markown 基本语法

### 标题

语法格式：`#+空格+标题`，一个`#`是一级标题，两个`##`是二级标题，以此类推，支持六级标题

### 字体

- 加粗语法格式：`**加粗**`
- 斜体语法格式：`*斜体*`
- 删除线语法格式：`~~删除线~~`
- 高亮语法格式：`==高亮==`

### 引用

语法格式：`> + 空格 + 引用文字`
引用也可以嵌套：

> > 两个`>>`

支持无限套娃

### 分割线

语法格式：三个以上的`-`或者`*`都可以，例如`----`

### 图片

![这是图片备注](https://scpic.chinaz.net/files/pic/pic9/201311/apic2098.jpg)

语法格式：`![alt](图片地址 "title")`

> `alt`是当图片在链接失效时显示的提示文字，可加可不加；`title`是图片标题，当鼠标移动到图片上是显示的内容，可加可不加

如果想要调整图片的大小，有两种方式：

1. 右击图片，选择缩放图片后，语法格式变成`<img src="Typora/fitst.jpg" alt="first" style="zoom:20%;"/>`,`20%`可自行调整
2. 通过HTML调节，

```html
<center>
<img stle="border-radius:20px;"
     src="Typora/first.jpg"
     alt="fitst"
     width="688">
</center>
```

- `<center>`标签设置图片居中
- `<boder-radius>`设置圆角
- `src`设置图片路径
- `alt`放置图片链接失效后的显示文字
- `width`设置图片宽度（`width`和`height`只设置一个即可，等比例缩放）

### 超链接

语法格式：`[超链接名](超链接地址)`

### 列表

- 无序列表
  
  语法格式：`-`、`+`、`*`任何一种加空格都可以，如`- 无序列表`

- 有序列表
  
  语法格式：`数字+点+空格+内容`，如`1. 有序列表`

- 子列表
  
  在子列表前按下<kbd>Tab</kbd>键即可构成子列表

### 表格

语法格式：

```
|标题  |   标题   |  标题    |
| ---- | ---- | ---- |
|内容  |   内容   |   内容   |
|      |      |      |
|      |      |      |
```

### 代码

行内代码：\`中间是代码\`，单个反引号

1. 多行代码：

\`\`\`

代码

\`\`\`

三个反引号，且两边的反引号各占一行

### 高级技巧

1. 转义
   
   Markdown用反斜杠转移特殊字符
   
   ```
   \    反斜线
   `    反引号
   *    星号
   _    下划线
   []    方括号
   {}    花括号
   #    ＃字号
   +    加号
   -    减号
   .    英文句点
   !    感叹号
   ```

2. 公式（一般用LaTex来写）
   
   - 行内公式：用`$...$`括起来
   
   - 块间公式：
     
     ```
     $$$
         块间公式
     $$$
     ```

### Typora快捷键

| 内容         | 一般语法            | 快捷键操作                                         |
|:----------:|:--------------- |:--------------------------------------------- |
| 一级标题       | `#`             | <kbd>Ctrl</kbd>+<kbd>1</kbd>                  |
| 二级标题       | `##`            | <kbd>Ctrl</kbd>+<kbd>2</kbd>                  |
| 加粗         | `**加粗**`        | <kbd>Ctrl</kbd>+<kbd>B</kbd>                  |
| 斜体         | `*斜体*`          | <kbd>Ctrl</kbd>+<kbd>I</kbd>                  |
| 下划线        | `<u>下划线</u>`    | <kbd>Ctrl</kbd>+<kbd>U</kbd>                  |
| 删除线        | `~~删除线~~`       | <kbd>Alt</kbd>+<kbd>Shift</kbd>+<kbd>5</kbd>  |
| 创建表格       |                 | <kbd>Ctrl</kbd>+<kbd>T</kbd>                  |
| 创建超链接      | `[超链接名](超链接地址)` | <kbd>Ctrl</kbd>+<kbd>K</kbd>                  |
| 插入图片       | `![alt](图片地址)`  | <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>I</kbd> |
| 插入公式块      | `$$公式$$`        | <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>M</kbd> |
| 添加引用       | `> 引用`          | <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>Q</kbd> |
| 返回Typora顶部 |                 | <kbd>Ctrl</kbd>+<kbd>Home</kbd>               |
| 返回typora底部 |                 | <kbd>Ctrl</kbd>+<kbd>End</kbd>                |
| 启动/退出源代码模式 |                 | <kbd>Ctrl</kbd>+<kbd>/</kbd>                  |
| 搜索         |                 | <kbd>Ctrl</kbd>+<kbd>F</kbd>                  |
| 搜索并替换      |                 | <kbd>Ctrl</kbd>+<kbd>H</kbd>                  |
| 生成文档目录     |                 | 输入`[toc]`，再按<kbd>Enter</kbd>                  |

> TOC从文档中提取所有标题，其内容将自动更新

序号    类型    快捷键
1    新建    Ctrl+N
2    新建窗口    Ctrl+Shift+N
3    打开    Ctrl+O
4    快速打开    Ctrl+P
5    保存    Ctrl+S
6    另存为    Ctrl+Shift+S
7    关闭    Ctrl+W
8    撤销    Ctrl+Z
9    重做    Ctrl+Y
10    复制为MarkDown    Ctrl+Shift+C
11    粘贴为纯文本    Ctrl+Shift+V
12    选中当前行/句    Ctrl+L
13    选中当前格式文本    Ctrl+E
14    选中当前词    Ctrl+D
15    跳转到文首    Ctrl+Home
16    跳转到文末    Ctrl+End
17    查找    Ctrl+F
18    替换    Ctrl+H
19    大纲视图    Ctrl+Shift+1
20    文档列表视图    Ctrl+Shift+2
21    文件树视图    Ctrl+Shift+3
22    显示隐藏侧边栏    Ctrl+Shift+L
23    源代码模式    Ctrl+/
24    专注模式    F8
25    打字机模式    F9
26    切换全屏    F11
27    实际大小    Ctrl+Shift+0
28    放大/缩小    Ctrl + Shift+ +/-
29    应用内窗口切换    Ctrl+Tab
31    打开DevTools    Shift+F12
