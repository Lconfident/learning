## 前言

CentOS 7 64位

## Linux基础

### 操作系统

操作系统**Operating System**简称**OS**，是软件的一部分，它是硬件基础上的第一层软件，是硬件与其他软件沟通的桥梁。

操作系统会控制其他程序运行，管理系统资源，提供最基础的计算功能，如管理及配置内存，决定系统资源供需的优先次序等，同时还提供一些基本的服务程序。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c17c21686f21413085f3e32c85a19443~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

### 什么是Linux

**Linux系统内核与Linux发型套件的区别**

- Linux系统内核是指由Linus Torvalds负责维护，提供硬件抽象层，硬盘及文件系统控制及多任务功能的系统核心程序

- Linux发行套件系统是我们常说的Linux操作系统，也即是由Linux内核与各种常用软件的集合产品

**Linux对比Windows**

1. 稳定且有效率

2. 免费或些许费用

3. 漏洞少且快速修补

4. 多任务多用户

5. 更加安全的用户与文件权限策略

6. 适合小内核程序的嵌入系统

7. 相对不耗资源

**Linux系统种类**

- 红帽企业版Linux：RHEL是全世界内使用最广泛的Linux系统。太具有极强的性能与稳定性，是众多生成环境中使用的（收费）系统。

- Fedora：由红帽公司发布的桌面版系统套件，用户可以免费体验带最新的技术与工具，这些技术或工具在成熟后会被加入到RHEL系统中，因此Fedora也成为RHEL系统的试验版本。

- CentOS:通过把RHEL系统重新编译并发布给用户免费使用的Linux系统，具有广泛使用的人群。

- Deepin:中国发行，对优秀的开源成品进行集成和配置。

- Debian:稳定性、安全性强，提供了免费的基础支持，在国外拥有很高的认知度和使用率。

- Ubuntu:一款派生自Debian的操作系统，对新款硬件具有极强的兼容能力。Ubuntu与Fedora都是极其出色的Linux桌面系统，而且Ubuntu也可用于服务器领域。

## Shell

Shell这个单词的原意是“外壳”，跟Kernel(内核）相对应，比喻内核外面的一层，即用户跟内核交互的对话界面。

1. Shell是一个程序，提供一个与用户对话的环境。这个环境只有一个命令提示符，让用户从键盘输入命令，所以又称为命令行环境（command line interface,简写为CLI）。Shell接受到用户输入的命令，将命令送入操作系统执行，并将结果返回给用户。

2. Shell是一个命令解释器，解释用户输入的命令。它支持变量、条件判断、循环操作等语法，所以用户可以用Shrll命令写出各种小程序，又称为Shell脚本。这些脚本都通过Shell的解释执行，而不通过编译。

3. Shell是一个工具箱，提供了各种小工具，供用户方便使用操作系统的功能。 
   
   ## Shell的种类

Shell有很多种，只要能给用户提供命令行环境的程序，都可以看作是Shell
历史上，主要的Shell有下面的这些: 

- Bourne Shell(sh)
- Bourne Again Shell(bash)
- C Shell(csh)
- TENEX C Shell(tcsh)
- Korn shell(ksh)
- Z Shell(zsh)
- Friendly Interactive Shell(fish)

其中Bash是最常用的Shell。MacOS中的默认Shell就是Bash。
通过执行`echo $SHELL`命令可以查看到当前正在使用的Shell。还可以通过`cat /etc/shells`查看当前系统安装的所有Shell种类。

## 命令

### 命令行提示符

进入命令行环境后，用户会看到Shell的提示符。提示符往往是一串前缀，最后一个美元符号$结尾，用户可以在这个符号后面输入各种命令。
执行一个简单的命令pwd：

```shell
[root@iZm5e8dsxce9ufaic7hi3uZ~]#pwd /root
```

命令解析:

- root：表示用户名；

- iZm5e8dsxce9ufaic7hi3uZ：表示主机名；

- ~：表示目前所在目录为家目录，其中root用户的家目录是/root普通用户的家目录在/home下;

- #：指示你所具有的权限（root为#,普通用户为$）。

- 执行`whoami`命令可以查看当前用户名；

- 执行`hostname`命令可以查看当前主机名；

> 注：root是超级用户，具备操作系统的一切权限。

### 命令格式

```shell
  command parameters(命令 参数)
```

#### 长短参数

```shell
  单个参数： ls -a(a是英文all的缩写，表示所有的）
  多个参数 ：ls -al（表示全部文件+列表形式显示）
  单个长参数： ls --all
  多个长参数：ls --reverse --all
  长短混合参数：ls --all -l
```

#### 参数值

```shell
短参数： command -p 10(例如：ssh root@121.42.11.34 -p 22)
行参数： command --paramters=10 (例如：ssh root@121.42.11.34 --port=22)
```

### 快捷方式

在开始学习Linux命令之前，有这么一些快捷方式，是必须要提前掌握的，它将贯穿整个Linux使用生涯。

- 通过使用上下键<kbd>↑↓</kbd>来调取过往执行过得Linux命令

- 命令或参数仅需输入前几位就可以用<kbd>Tab</kbd>键补全

- <kbd>Ctrl + R</kbd>用于查找使用过的命令（history命令用于列出之前使用过的所有命令，然后输入`!`命令加上编号，如`!2`就可以直接执行该历史命令）

- <kbd>Ctrl + L </kbd>：清除屏幕并将当前行移除到页面顶部

- <kbd>Ctrl + C</kbd>：中止当前正在执行的命令

- <kbd>Ctrl + U</kbd>：从光标位置剪切到行首

- <kbd>Ctrl + K</kbd>：从光标位置剪切到行尾

- <kbd>Ctrl + W</kbd>：剪切光标左侧第一个单词

- <kbd>Ctrl + Y</kbd>：粘贴<kbd>Ctrl +U|K|W</kbd>剪切过的命令

- <kbd>Ctrl + A</kbd>：光标跳到命令行的开头

- <kbd>Ctrl + E</kbd>：光标跳到命令行的结尾

- <kbd>Ctrl + D</kbd>：关闭Shell会话

## 文件和目录

### 文件的组织

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/226f8a87a9804141802d5ba0a55bd1f1~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

### 查看路径

- pwd
  
  显示当前目录的路径
  
  ![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6fc89d9816a54a428d122abd7616c566~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

- which
  
  查看命令的可执行文件所在路径，
  
  Linux每一条命令其实都对应一个可执行程序。在终端中输入命令，按回车的时候，就是执行了对应的那个程序，`which`命令本身对应的程序也存在与Linux中
  
  总的来说一条命令就是一个可执行程序
  
  ![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9c0c60677e574aff82a0009339f72fb5~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

### 浏览和切换目录

- ls
  
  列出文件和目录，它是Linux最常用的命令之一
  
  【常用参数】
  
  - `-a`显示所有文件和目录包括隐藏的
  
  - `-l`显示详细列表
  
  - `-h`适合人类阅读的
  
  - `-t`按文件最近一次修改时间排序
  
  - `-i`显示文件的`inode`(`inode`是文件内容的标识)
    
    ![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/015b1a4af3c14631a45b92c0f8755ea3~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

- cd
  
  `cd`是英文`change directory`的缩写，表示切换目录。
  
  ```shell
  cd /    --> 跳转到根目录
  cd ~    --> 跳转到家目录
  cd ..    --> 跳转到上级目录
  cd ./home    --> 跳转到当前目录的home目录下
  cd /home/lion    --> 跳转到根目录下的home目录下的lion目录
  cd    --> 不添加任何参数，也是回到家目录
  ```
  
  > [注意] 输入`cd /ho` + 单次 `tab` 键会自动补全路径 + 两次 `tab` 键会列出所有可能的目录列表。

- du
  
  列举目录大小信息
  
  【常用参数】
  
  - `-h`适合人类阅读的
  
  - `-a`同时列举出目录下文件的大小信息
  
  - `-s`只显示总计大小，不显示具体信息

### 浏览和创建文件

- cat
  
  一次性显示文件所有内容，适合查看小文件
  
  ```shell
  cat cloud-init.log
  ```
  
  【常用参数】
  
  `-n`显示行号

- less
  
  分页显示文件内容，更适合查看大文件
  
  ```shell
  less cloud-init.log
  ```
  
  【快捷操作】
  
  - 空格键：前进一页（一个屏幕）；
  
  - `b`键：后退一页；
  
  - 回车键：前进一行；
  
  - `y`键：后退一行；
  
  - 上下键：后退或前进一行；
  
  - `d`键：前进半页；
  
  - `u`键：后退半页；
  
  - `q`键：停止读取文件，中止`less`命令；
  
  - `=`键：显示当前页面的内容是文件中的第几行到第几行以及一些其他关于本页内容的详细信息；
  
  - `h`键：显示帮助文档；
  
  - `/`键：进入搜索模式后，按`n`键跳到一个符合项目，按`N`键跳到上一个符合项目，同时也可以输入正则表达式匹配。

- head
  
  - 显示文件的开头几行（默认是10行)
    
    - ```shell
      head cloud-init.log
      ```
  
  - 【参数】
    
    - `-n`指定行数`head cloud-init.log -n 2`

- tail
  
  - 显示文件的结尾几行（默认是10行）
    
    - ```shell
      tail cloud-init.log
      ```
  
  - 【参数】
    
    - `-n`指定行数    `tail cloud-init.logg -n 2`
    
    - `-f`会每过1秒检查文件是否更新内容，也可用`-s`参数指定间隔时间`tail -f -s 4 cloud-init.log`

- touch
  
  - 创建一个文件
  
  - ```shell
    touch new_file
    ```

- mkdir
  
  - 创建一个目录
    
    - ```shell
      mkdir new_file
      ```
  
  - 【参数】
    
    - `-p`递归的创建目录结构    `mkdir -p one/two/three`

### 文件的复制和移动

- cp
  
  - 拷贝文件和目录
    
    - ```shell
      cp file file-copy    -->file是要拷贝的目标文件，file_copy是拷贝出来的文件
      cp file one    -->把file文件拷贝到one目录下，并且文件名称仍为file
      cp file one/file_copy    -->把file文件拷贝到one目录下，文件名更为file_copy
      cp *.txt folder    -->把当前目录下的所有txt文件拷贝到folder目录下
      ```
  
  - 【参数】
    
    - `-r`递归的拷贝，常用来拷贝一整个目录

- mv
  
  - 移动或重命名文件或目录
    
    - ```shell
      mv file one -->将file文件移动到one目录下
      mv new_folder one -->将new_folder移动到one目录下
      mv *.txt folder    -->把当前目录所以txt文件移动到folder
      mv file new_file    -->将file文件重命名为new_file
      ```

### 文件的删除和链接

- rm
  
  - 删除文件和目录，由于`Linux`下没有回收站，一旦删除非常难恢复，因此需要谨慎操作
  
  - ```shell
    rm new_file    -->删除new_file文件
    rm f1 f2 f3    -->同时删除f1,f2,f3 3个文件
    ```
  
  - 【参数】
    
    - `-i`向用户确认是否删除
    
    - `-f`文件强制删除
    
    - `-r`递归删除文件夹，著名的删除操作`rm -rf`

- ln
  
  - 英文`Link`的缩写，表示创建链接。
  
  - 学习创建链接之前，首先要理解链接是什么，先看看`Linux`的文件是如何存储的：
    
    - `Linux`文件的存储方式分为3个部分，文件名、文件内容以及权限，其中文件名的列表是存储在硬盘的其他地方和文件内容是分开存放的，每个文件名通过`inode`标识绑定到文件内容。
    
    - ----
    
    - Linux下有两汇总链接类型：硬链接和软链接。
    
    - 硬链接：使链接的两个文件共享同样的文件内容，就是同样的`inode`，一旦文件1和文件2之间有了硬链接，那么修改一个文件，修改的都是同一块内容，它的缺点是，只能创建指向文件的链硬接，不能创建指向目录的（其实也可以，但比较复杂），而软链接可以，因此软链接使用更加广泛。

- 硬链接

- 软链接

## 用户与权限

### 用户