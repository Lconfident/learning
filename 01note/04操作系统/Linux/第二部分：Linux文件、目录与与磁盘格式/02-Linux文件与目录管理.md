前一章节认识了Linux系统下的文件权限概念以及目录的配置说明。 在这个章节当中，我们就直接来进一步的操作与管理文件与目录！包括在不同的目录间变换、 创建与删除目录、创建与删除文件，还有寻找文件、查阅文件内容等等， 都会在这个章节作个简单的介绍啊！

----

## 重点

- 绝对路径：『一定由根目录 / 写起』；相对路径：『不是由 / 写起』
- 特殊目录有：., .., -, ~, ~account需要注意；
- 与目录相关的命令有：cd, mkdir, rmdir, pwd 等重要命令；
- rmdir 仅能删除空目录，要删除非空目录需使用『 rm -r 』命令；
- 使用者能使用的命令是依据 PATH 变量所规定的目录去搜寻的；
- 不同的身份(root 与一般用户)系统默认的 PATH 并不相同。差异较大的地方在于 /sbin, /usr/sbin ；
- ls 可以检视文件的属性，尤其 -d, -a, -l 等选项特别重要！
- 文件的复制、删除、移动可以分别使用：cp, rm , mv等命令来操作；
- 检查文件的内容(读档)可使用的命令包括有：cat, tac, nl, more, less, head, tail, od 等
- cat -n 与 nl 均可显示行号，但默认的情况下，空白行会不会编号并不相同；
- touch 的目的在修改文件的时间参数，但亦可用来创建空文件；
- 一个文件记录的时间参数有三种，分别是 access time(atime), status time (ctime), modification time(mtime)，ls 默认显示的是 mtime。
- 除了传统的rwx权限之外，在Ext2/Ext3文件系统中，还可以使用chattr与lsattr配置及观察隐藏属性。 常见的包括只能新增数据的 +a 与完全不能更动文件的 +i 属性。
- 新建文件/目录时，新文件的默认权限使用 umask 来规范。默认目录完全权限为drwxrwxrwx， 文件则为-rw-rw-rw-。
- 文件具有SUID的特殊权限时，代表当使用者运行此一binary程序时，在运行过程中使用者会暂时具有程序拥有者的权限
- 目录具有SGID的特殊权限时，代表使用者在这个目录底下新建的文件之群组都会与该目录的群组名称相同。
- 目录具有SBIT的特殊权限时，代表在该目录下使用者创建的文件只有自己与root能够删除！
- 观察文件的类型可以使用 file 命令来观察；
- 搜寻命令的完整档名可用 which 或 type ，这两个命令都是透过 PATH 变量来搜寻档名；
- 搜寻文件的完整档名可以使用 whereis 或 locate 到数据库文件去搜寻，而不实际搜寻文件系统；
- 利用 find 可以加入许多选项来直接查询文件系统，以获得自己想要知道的档名。

## 目录与路径

### 相对路径与绝对路径

在开始目录的切换之前，你必须要先了解一下什么是『相对路径』与『绝对路径』？此外，当你下达命令时，该命令是透过什么功能来取得的？ 这与PATH这个变量有关呢！

 虽然前一章已经稍微针对这个议题提过一次，不过，这里不厌其烦的再次的强调一下！

- 绝对路径：路径的写法『一定由根目录 / 写起』，例如： /usr/share/doc 这个目录。

- 相对路径：路径的写法『不是由 / 写起』，例如由 /usr/share/doc 要到 /usr/share/man 底下时，可以写成： 『cd ../man』这就是相对路径的写法啦！相对路径意指『相对於目前工作目录的路径！』
  
  ----

- 相对路径的用途

那么相对路径与绝对路径有什么了不起呀？那可真的是了不起了！假设你写了一个软件， 这个软件共需要三个目录，分别是 etc, bin, man 这三个目录，然而由于不同的人喜欢安装在不同的目录之下， 假设甲安装的目录是 /usr/local/packages/etc, /usr/local/packages/bin 及 /usr/local/packages/man ，不过乙却喜欢安装在 /home/packages/etc, /home/packages/bin, /home/packages/man 这三个目录中，请问如果需要用到绝对路径的话，那么是否很麻烦呢？是的！ 如此一来每个目录下的东西就很难对应的起来！这个时候相对路径的写法就显的特别的重要了！

有人还喜欢将路径的名字写的很长，但好让自己清楚那个目录是在干什么的，例如： /cluster/raid/output/taiwan2006/smoke 这个目录，而另一个目录在 /cluster/raid/output/taiwan2006/cctm ，那么我从第一个要到第二个目录去的话，怎么写比较方便？ 当然是『 cd ../cctm 』比较方便罗！对吧！

- 绝对路径的用途

但是对于档名的正确性来说，『绝对路径的正确度要比较好～』。 一般来说，如果是在写程序 (shell scripts) 来管理系统的条件下，务必使用绝对路径的写法。 怎么说呢？因为绝对路径的写法虽然比较麻烦，但是可以肯定这个写法绝对不会有问题。 如果使用相对路径在程序当中，则可能由于你运行的工作环境不同，导致一些问题的发生。 这个问题在[工作排程(at, cron]当中尤其重要！这个现象在[shell script]时，会再次的提醒你！ ^_^

### 目录的相关操作：cd，pwd，mkdir，rmdir

我们之前稍微提到切换目录的命令是cd，还有哪些可以进行目录操作的命令呢？ 例如创建目录啊、删除目录之类的～还有，得要先知道的，就是有哪些比较特殊的目录呢？ 举例来说，底下这些就是比较特殊的目录，得要用力的记下来才行：

```shell
.         代表此层目录
..        代表上一层目录
-         代表前一个工作目录
~         代表『目前使用者身份』所在的家目录
~account  代表 account 这个使用者的家目录(account是个帐号名称)
```

需要特别注意的是：在所有目录底下都会存在的两个目录，分别是『.』与『..』 分别代表此层与上一级目录的意思。那么来思考一下底下这个例题：

> 例题：
> 
> 请问在Linux底下，根目录下有没有上一级目录(..)存在？
> 
> 答：
> 
> 若使用『 ls -al / 』去查询，可以看到根目录下确实存在 . 与 .. 两个目录，再仔细的查阅， 可发现这两个目录的属性与权限完全一致，这代表根目录的上一层(..)与根目录自己(.)是同一个目录。

下面我们就来谈一谈几个常见的处理目录的命令吧：

- cd：变换目录
- pwd：显示目前的目录
- mkdir：创建一个新的目录
- rmdir：删除一个空的目录

----

### cd（切换目录）

我们知道vbird这个使用者的家目录是/home/vbird/，而root家目录则是/root/，假设我以root身份在 Linux系统中，那么简单的说明一下这几个特殊的目录的意义是：

```shell
[root@www ~]# cd [相对路径或绝对路径]
# 最重要的就是目录的绝对路径与相对路径，还有一些特殊目录的符号罗！
[root@www ~]# cd ~vbird
# 代表去到 vbird 这个使用者的家目录，亦即 /home/vbird
[root@www vbird]# cd ~
# 表示回到自己的家目录，亦即是 /root 这个目录
[root@www ~]# cd
# 没有加上任何路径，也还是代表回到自己家目录的意思喔！
[root@www ~]# cd ..
# 表示去到目前的上一级目录，亦即是 /root 的上一级目录的意思；
[root@www /]# cd -
# 表示回到刚刚的那个目录，也就是 /root 罗～
[root@www ~]# cd /var/spool/mail
# 这个就是绝对路径的写法！直接指定要去的完整路径名称！
[root@www mail]# cd ../mqueue
# 这个是相对路径的写法，我们由/var/spool/mail 去到/var/spool/mqueue 就这样写！
```

cd是Change Directory的缩写，这是用来变换工作目录的命令。

注意，目录名称与cd命令之间存在一个空格。 一登陆Linux系统后，root会在root的家目录！那回到上一层目录可以用『 cd .. 』。 

利用相对路径的写法必须要确认你目前的路径才能正确的去到想要去的目录。

> 例如上表当中最后一个例子， 你必须要确认你是在/var/spool/mail当中，并且知道在/var/spool当中有个mqueue的目录才行啊～ 这样才能使用cd ../mqueue去到正确的目录说，否则就要直接输入cd /var/spool/mqueue～

其实，我们的提示字节，亦那个 [root@www ~]# 当中，就已经有指出目前的目录了， 刚登陆时会到自己的家目录，而家目录还有一个代码，那就是『 ~ 』符号！ 

> 例如上面的例子可以发现，使用『 cd ~ 』可以回到个人的家目录里头去呢！ 另外，针对 cd 的使用方法，如果仅输入 cd 时，代表的就是『 cd ~ 』的意思喔～ 即是会回到自己的家目录啦！而那个『 cd - 』比较难以理解，请自行多做几次练习， 就会比较明白了。

> **Tips:**  
> 还是要一再地提醒，我们的 Linux 的默认命令列模式 (bash shell) 具有文件补齐功能， 你要常常利用 [tab] 按键来达成你的目录完整性啊！这可是个好习惯啊～ 可以避免你按错键盘输入错字～ ^_^

### pwd (显示目前所在的目录)

```shell
[root@www ~]# pwd [-P]
选项与参数：
-P  ：显示出确实的路径，而非使用连结 (link) 路径。

范例：单纯显示出目前的工作目录：
[root@www ~]# pwd
/root   <== 显示出目录啦～

范例：显示出实际的工作目录，而非连结档本身的目录名而已
[root@www ~]# cd /var/mail   <==注意，/var/mail是一个连结档
[root@www mail]# pwd
/var/mail         <==列出目前的工作目录
[root@www mail]# pwd -P
/var/spool/mail   <==怎么回事？有没有加 -P 差很多～
[root@www mail]# ls -ld /var/mail
lrwxrwxrwx 1 root root 10 Sep  4 17:54 /var/mail -> spool/mail
# 看到这里应该知道为啥了吧？因为 /var/mail 是连结档，连结到 /var/spool/mail 
# 所以，加上 pwd -P 的选项后，会不以连结档的数据显示，而是显示正确的完整路径啊！
```

pwd是Print Working Directory的缩写，也就是显示目前所在目录的命令。

> 例如在上个表格最后的目录是/var/mail这个目录，但是提示字节仅显示mail， 如果你想要知道目前所在的目录，可以输入pwd即可。此外，由于很多的套件所使用的目录名称都相同，例如 /usr/local/etc还有/etc，但是通常Linux仅列出最后面那一个目录而已，这个时候你就可以使用pwd 来知道你的所在目录罗！免得搞错目录，结果...

其实有趣的是那个 -P 的选项啦！他可以让我们取得正确的目录名称，而不是以连结档的路径来显示的。 

> 如果你使用的是CentOS 5.x的话，刚刚好/var/mail是/var/spool/mail的连结档， 所以，透过到/var/mail下达pwd -P就能够知道这个选项的意义～ ^_^

### mkdir (创建新目录)

```shell
[root@www ~]# mkdir [-mp] 目录名称
选项与参数：
-m ：配置文件的权限喔！直接配置，不需要看默认权限 (umask) 的脸色～
-p ：帮助你直接将所需要的目录(包含上一级目录)递回创建起来！

范例：请到/tmp底下尝试创建数个新目录看看：
[root@www ~]# cd /tmp
[root@www tmp]# mkdir test    <==创建一名为 test 的新目录
[root@www tmp]# mkdir test1/test2/test3/test4
mkdir: cannot create directory `test1/test2/test3/test4': 
No such file or directory       <== 没办法直接创建此目录啊！
[root@www tmp]# mkdir -p test1/test2/test3/test4
# 加了这个 -p 的选项，可以自行帮你创建多层目录！

范例：创建权限为rwx--x--x的目录
[root@www tmp]# mkdir -m 711 test2
[root@www tmp]# ls -l
drwxr-xr-x  3 root  root 4096 Jul 18 12:50 test
drwxr-xr-x  3 root  root 4096 Jul 18 12:53 test1
drwx--x--x  2 root  root 4096 Jul 18 12:54 test2
# 仔细看上面的权限部分，如果没有加上 -m 来强制配置属性，系统会使用默认属性。
# 那么你的默认属性为何？这要透过底下介绍的 umask 才能了解喔！ ^_^
```

如果想要创建新的目录的话，那么就使用mkdir (make directory)吧！

 不过，在默认的情况下， 你所需要的目录得一层一层的创建才行！

> 例如：假如你要创建一个目录为 /home/bird/testing/test1，那么首先必须要有 /home 然后 /home/bird ，再来 /home/bird/testing 都必须要存在，才可以创建 /home/bird/testing/test1 这个目录！
> 
> 假如没有 /home/bird/testing 时，就没有办法创建 test1 的目录罗！

不过，现在有个更简单有效的方法啦！那就是加上 -p 这个选项喔！你可以直接下达：『 mkdir -p /home/bird/testing/test1 』 则系统会自动的帮你将 /home, /home/bird, /home/bird/testing 依序的创建起目录！并且， 如果该目录本来就已经存在时，系统也不会显示错误信息喔！挺快乐的吧！ ^_^。

> 不过这里不建议常用-p这个选项，因为担心如果你打错字，那么目录名称就会变的乱七八糟的！

另外，有个地方你必须要先有概念，那就是『默认权限』的地方。我们可以利用 -m 来强制给予一个新的目录相关的权限， 

> 例如上表当中，我们给予 -m 711 来给予新的目录 drwx--x--x 的权限。不过，如果没有给予 -m 选项时， 那么默认的新建目录权限又是什么呢？这个跟 [umask]有关，我们在本章后头会加以介绍的。

### rmdir (删除『空』的目录)

```shell
[root@www ~]# rmdir [-p] 目录名称
选项与参数：
-p ：连同上一级『空的』目录也一起删除

范例：将於mkdir范例中创建的目录(/tmp底下)删除掉！
[root@www tmp]# ls -l   <==看看有多少目录存在？
drwxr-xr-x  3 root  root 4096 Jul 18 12:50 test
drwxr-xr-x  3 root  root 4096 Jul 18 12:53 test1
drwx--x--x  2 root  root 4096 Jul 18 12:54 test2
[root@www tmp]# rmdir test   <==可直接删除掉，没问题
[root@www tmp]# rmdir test1  <==因为尚有内容，所以无法删除！
rmdir: `test1': Directory not empty
[root@www tmp]# rmdir -p test1/test2/test3/test4
[root@www tmp]# ls -l        <==您看看，底下的输出中test与test1不见了！
drwx--x--x  2 root  root 4096 Jul 18 12:54 test2
# 瞧！利用 -p 这个选项，立刻就可以将 test1/test2/test3/test4 一次删除～
# 不过要注意的是，这个 rmdir 仅能『删除空的目录』喔！
```

如果想要删除旧有的目录时，就使用remove directory【rmdir】吧！

例如将刚刚创建的test杀掉，使用『 rmdir test 』即可！

请注意！

目录需要一层一层的删除才行！而且被删除的目录里面必定不能存在其他的目录或文件！ 这也是所谓的空的目录(empty directory)的意思啊！

那如果要将所有目录下的东西都杀掉呢？！ 这个时候就必须使用『 rm -r test 』！不过，还是使用 rmdir 比较不危险！你也可以尝试以 -p 的选项加入，来删除上一级的目录喔！

### 运行档路径的变量： $PATH

经过FHS的说明后，我们知道查阅文件属性的命令ls，完整档名为：/bin/ls(这是绝对路径)， 那你会不会觉得很奇怪：【为什么我可以在任何地方运行/bin/ls这个命令呢？】为什么我在任何目录下 输入ls就一定可以显示出一些信息而不是说找不到命令/bin/ls呢？这是因为环境变量PATH的帮助啊！

当我们在运行一个命令的时候，举例来说『ls』好了，系统会依照PATH的配置去每个PATH定义的目录下搜寻档名为ls的可运行档， 如果在PATH定义的目录中含有多个档名为ls的可运行档，那么先被搜寻到的同名命令先被运行！

现在，请下达『`echo $PATH`』来看看到底有哪些目录被定义出来了？【echo】有『显示、印出』的意思，而 PATH 前面加的 $ 表示后面接的是变量，所以会显示出目前的 PATH ！

```shell
范例：先用root的身份列出搜寻的路径为何？
[root@www ~]# echo $PATH
/usr/kerberos/sbin:/usr/kerberos/bin:/usr/local/sbin:/usr/local/bin:/sbin
:/bin:/usr/sbin:/usr/bin:/root/bin  <==这是同一行！

范例：用vbird的身份列出搜寻的路径为何？
[root@www ~]# su - vbird
[vbird@www ~]# echo $PATH
/usr/kerberos/bin:/usr/local/bin:/bin:/usr/bin:/home/vbird/bin
# 仔细看，一般用户vbird的PATH中，并不包含任何『sbin』的目录存在喔！
```

PATH(一定是大写)这个变量的内容是由一堆目录所组成的，每个目录中间用冒号(:)来隔开， 每个目录是有『顺序』之分的。

仔细看一下上面的输出，你可以发现到无论是root还是vbird都有`/bin` 这个目录在PATH变量内，所以当然就能够在任何地方运行【`ls`】来找到【`/bin/ls`】运行档了！

> 我们用几个范例来让你了解一下，为什么PATH是那么重要的项目?
> 
> 例题1：
> 
> 请问你能不能使用一般身份使用者下达`ifconfig eth0`这个命令呢？
> 
> 答：
> 
> 如上面的范例所示，当你使用`vbird`这个帐号运行`ifconfig`时，会出现『`-bash: ifconfig: command not found`』的字样， 因为`ifconfig`的是放置到`/sbin`底下，而由上表的结果中我们可以发现`vbird`的`PATH`并没有配置`/sbin`， 所以**默认无法运行**  
> 
> 但可以使用『`/sbin/ifconfig eth0`』来运行这个命令！
> 
> 因为一般用户还是可以使用`ifconfig`来查询系统IP的参数， 既然PATH没有规范到`/sbin`，那么我们使用『绝对路径』也可以运行到该命令的！
> 
> ----
> 
> 例题2：
> 
> 假设你是root，如果你将`ls`由`/bin/ls`移动成为`/root/ls`(可用『`mv /bin/ls /root`』命令达成)，然后你自己本身也在`/root`目录下， 
> 
> 请问
> 
> (1)你能不能直接输入`ls`来运行？
> 
> (2)若不能，你该如何运行ls这个命令？
> 
> (3)若要直接输入ls即可运行，又该如何进行？
> 
> 答：
> 
> 这个例题的重点是将某个运行档移动到**非正规目录**去，所以我们先要进行底下的动作才行：(务必使用root的身份)
> 
> ```shell
> [root@www ~]# mv /bin/ls /root
> # mv 为移动，可将文件在不同的目录间进行移动作业
> ```
> 
> (1)接下来不论你在哪个目录底下输入任何与`ls`相关的命令，都**没有办法顺利的运行**`ls`了！ 也就是说，你不能直接输入ls来运行，
> 
> 因为`/root`这个目录并不在PATH指定的目录中， 所以，即使你在/root目录下，也不能够搜寻到ls这个命令！  
> 
> (2)因为这个ls确实存在于`/root`底下，并不是被删除了！所以我们可以透过绝对路径或者是相对路径直接指定这个运行档档名， 底下的两个方法都能够运行ls这个命令：
> 
> ```shell
> [root@www ~]# /root/ls  <==直接用绝对路径指定该档名
> [root@www ~]# ./ls      <==因为在 /root 目录下，就用./ls来指定
> ```
> 
> (3)如果想要让root在任何目录均可运行`/root`底下的ls，那么就将`/root`加入PATH当中即可。 加入的方法很简单，就像底下这样：
> 
> ```shell
> [root@www ~]# PATH="$PATH":/root
> ```
> 
> 上面这个作法就能够将`/root`加入到运行档搜寻路径PATH中了！不相信的话请您自行使用『echo $PATH』去查看吧！
> 
>  如果确定这个例题进行没有问题了，请将ls搬回/bin底下，不然系统会挂点的！
> 
> ```shell
> [root@www ~]# mv /root/ls /bin
> ```
> 
> ----
> 
> 例题3：
> 
> 如果我有两个ls命令在不同的目录中，例如`/usr/local/bin/ls`与`/bin/ls`，那么当我下达 ls 的时候，哪个ls会被运行？
> 
> 答：
> 
> 那还用说，找出 PATH 里面哪个目录先被查询，则那个目录下的命令就会被先运行了！
> 
> ----
> 
> 例题4：
> 
> 为什么PATH搜寻的目录不加入本目录(.)？加入本目录的搜寻不是也不错？
> 
> 答：
> 
> 如果在PATH中加入本目录(.)后，确实我们就能够在命令所在目录进行命令的运行了。 但是由于你的工作目录并非固定(常常会使用cd来切换到不同的目录)，
> 
> 因此能够运行的命令会有变动(因为每个目录底下的可运行档都不相同嘛！)，这对使用者来说并非好事  
> 
> 另外，如果有个坏心使用者在/tmp底下做了一个命令，因为/tmp是大家都能够写入的环境，所以他当然可以这样做。 
> 
> 假设该命令可能会窃取使用者的一些数据，如果你使用root的身份来运行这个命令，那不是很糟糕？ 如果这个命令的名称又是经常会被用到的ls时，那『中标』的机率就更高了！  
> 
> 所以，为了安全起见，不建议将『.』加入PATH的搜寻目录中。

-----

> 总结一下：
> 
> - 不同身份使用者默认的PATH不同，默认能够随意运行的命令也不同(如root与vbird)；
> - PATH是可以修改的，所以一般使用者还是可以透过修改PATH来运行某些位于`/sbin`或`/usr/sbin`下的命令来查询；
> - 使用绝对路径或相对路径直接指定某个命令的档名来运行，会比搜寻PATH来的正确；
> - 命令应该要放置到正确的目录下，运行才会比较方便；
> - 本目录(`.`)最好不要放到PATH当中。

## 文件与目录管理

谈了谈目录与路径之后，再来讨论一下关于文件的一些基本管理吧！文件与目录的管理上，不外乎『显示属性』、 『拷贝』、『删除文件』及『移动文件或目录』等等，因此文件与目录的管理在 Linux 当中是很重要的， 尤其是每个人自己家目录的数据也都需要注意管理！

### 文件与目录的检视： ls

```shell
[root@www ~]# ls [-aAdfFhilnrRSt] 目录名称
[root@www ~]# ls [--color={never,auto,always}] 目录名称
[root@www ~]# ls [--full-time] 目录名称
选项与参数：
-a  ：全部的文件，连同隐藏档( 开头为 . 的文件) 一起列出来(常用)【all】
-A  ：全部的文件，连同隐藏档，但不包括 . 与 .. 这两个目录
-d  ：仅列出目录本身，而不是列出目录内的文件数据(常用)
-f  ：直接列出结果，而不进行排序 (ls 默认会以档名排序！)
-F  ：根据文件、目录等资讯，给予附加数据结构，例如：
      *:代表可运行档； /:代表目录； =:代表 socket 文件； |:代表 FIFO 文件；
-h  ：将文件容量以人类较易读的方式(例如 GB, KB 等等)列出来；
-i  ：列出 inode 号码，inode 的意义 之后将会介绍；【inode】
-l  ：长数据串列出，包含文件的属性与权限等等数据；(常用)【long】
-n  ：列出 UID 与 GID 而非使用者与群组的名称 (UID与GID会在帐号管理提到！)
-r  ：将排序结果反向输出，例如：原本档名由小到大，反向则为由大到小；
-R  ：连同子目录内容一起列出来，等于该目录下的所有文件都会显示出来；
-S  ：以文件容量大小排序，而不是用档名排序；
-t  ：依时间排序，而不是用档名。【time】
--color=never  ：不要依据文件特性给予颜色显示；
--color=always ：显示颜色
--color=auto   ：让系统自行依据配置来判断是否给予颜色
--full-time    ：以完整时间模式 (包含年、月、日、时、分) 输出
--time={atime,ctime} ：输出 access 时间或改变权限属性时间 (ctime) 
                       而非内容变更时间 (modification time)
```

> 我们随时都要知道文件或者是目录的相关资讯啊～ 不过，我们Linux的文件所记录的资讯实在是太多了，ls 没有需要全部都列出来呢～

所以当只有下达 ls 时，默认显示的只有：非隐藏档的档名、 以档名进行排序及档名代表的颜色显示如此而已。

举例来说， 你下达『 ls /etc 』之后，只有经过排序的档名以及以蓝色显示目录及白色显示一般文件。

> 那如果我还想要加入其他的显示资讯时，可以加入上头提到的那些有用的选项呢～ 举例来说，我们之前一直用到的 -l 这个长串显示数据内容，以及将隐藏档也一起列示出来的 -a 选项等等。 底下则是一些常用的范例，实际试做看看：
> 
> ```shell
> 范例一：将家目录下的所有文件列出来(含属性与隐藏档)
> [root@www ~]# ls -al ~
> total 156
> drwxr-x---  4 root root  4096 Sep 24 00:07 .
> drwxr-xr-x 23 root root  4096 Sep 22 12:09 ..
> -rw-------  1 root root  1474 Sep  4 18:27 anaconda-ks.cfg
> -rw-------  1 root root   955 Sep 24 00:08 .bash_history
> -rw-r--r--  1 root root    24 Jan  6  2007 .bash_logout
> -rw-r--r--  1 root root   191 Jan  6  2007 .bash_profile
> -rw-r--r--  1 root root   176 Jan  6  2007 .bashrc
> drwx------  3 root root  4096 Sep  5 10:37 .gconf
> -rw-r--r--  1 root root 42304 Sep  4 18:26 install.log
> -rw-r--r--  1 root root  5661 Sep  4 18:25 install.log.syslog
> 
> # 这个时候你会看到以 . 为开头的几个文件，以及目录档 (.) (..) .gconf 等等，
> 
> # 不过，目录档档名都是以深蓝色显示，有点不容易看清楚就是了。
> 
> 范例二：承上题，不显示颜色，但在档名末显示出该档名代表的类型(type)
> [root@www ~]# ls -alF --color=never  ~
> total 156
> drwxr-x---  4 root root  4096 Sep 24 00:07 ./
> drwxr-xr-x 23 root root  4096 Sep 22 12:09 ../
> -rw-------  1 root root  1474 Sep  4 18:27 anaconda-ks.cfg
> -rw-------  1 root root   955 Sep 24 00:08 .bash_history
> -rw-r--r--  1 root root    24 Jan  6  2007 .bash_logout
> -rw-r--r--  1 root root   191 Jan  6  2007 .bash_profile
> -rw-r--r--  1 root root   176 Jan  6  2007 .bashrc
> drwx------  3 root root  4096 Sep  5 10:37 .gconf/
> -rw-r--r--  1 root root 42304 Sep  4 18:26 install.log
> -rw-r--r--  1 root root  5661 Sep  4 18:25 install.log.syslog
> 
> # 注意看到显示结果的第一行，知道为何我们会下达类似 ./command
> 
> # 之类的命令了吧？因为 ./ 代表的是『目前目录下』的意思啊！至于什么是 FIFO/Socket ？
> 
> # 请参考前一章节的介绍啊！另外，那个.bashrc 时间仅写2007，能否知道详细时间？
> 
> 范例三：完整的呈现文件的修改时间 *(modification time)
> [root@www ~]# ls -al --full-time  ~
> total 156
> drwxr-x---  4 root root  4096 2008-09-24 00:07:00.000000 +0800 .
> drwxr-xr-x 23 root root  4096 2008-09-22 12:09:32.000000 +0800 ..
> -rw-------  1 root root  1474 2008-09-04 18:27:10.000000 +0800 anaconda-ks.cfg
> -rw-------  1 root root   955 2008-09-24 00:08:14.000000 +0800 .bash_history
> -rw-r--r--  1 root root    24 2007-01-06 17:05:04.000000 +0800 .bash_logout
> -rw-r--r--  1 root root   191 2007-01-06 17:05:04.000000 +0800 .bash_profile
> -rw-r--r--  1 root root   176 2007-01-06 17:05:04.000000 +0800 .bashrc
> drwx------  3 root root  4096 2008-09-05 10:37:49.000000 +0800 .gconf
> -rw-r--r--  1 root root 42304 2008-09-04 18:26:57.000000 +0800 install.log
> -rw-r--r--  1 root root  5661 2008-09-04 18:25:55.000000 +0800 install.log.syslog
> 
> # 请仔细看，上面的『时间』栏位变了喔！变成较为完整的格式。
> 
> # 一般来说， ls -al 仅列出目前短格式的时间，有时不会列出年份，
> 
> # 藉由 --full-time 可以查阅到比较正确的完整时间格式啊！
> ```

> 其实 ls 的用法还有很多，包括查阅文件所在 i-node 号码的 `ls -i` 选项，以及用来进行文件排序的 `-S` 选项，还有用来查阅不同时间的动作的`--time=atime` 等选项

无论如何， `ls` 最常被使用到的功能还是那个 `-l` 的选项，为此，很多 distribution 在默认的情况中， 已经将 `ll `(L 的小写) 配置成为 `ls -l` 的意思了！其实，那个功能是 [Bash shell]的 [alias] 功能呢～也就是说，我们直接输入 `ll` 就等於是输入 `ls -l` 是一样的～

----

要复制文件，请使用 cp (copy) 这个命令即可～

不过， cp 这个命令的用途可多了～ 除了单纯的复制之外，还可以创建连结档 (就是捷径咯)，比对两文件的新旧而予以升级， 以及复制整个目录等等的功能呢！

至于移动目录与文件，则使用 mv (move)， 这个命令也可以直接拿来作更名 (rename) 的动作喔！

至于移除吗？那就是 rm (remove) 这个命令咯～～

----

### 复制文件或目录（cp）

```shell
[root@www ~]# cp [-adfilprsu] 来源档(source) 目标档(destination)
[root@www ~]# cp [options] source1 source2 source3 .... directory
选项与参数：
-a  ：相当于 -pdr 的意思，至于 pdr 请参考下列说明；(常用)
-d  ：若来源档为连结档的属性(link file)，则复制连结档属性而非文件本身；
-f  ：为强制(force)的意思，若目标文件已经存在且无法开启，则移除后再尝试一次；
-i  ：若目标档(destination)已经存在时，在覆盖时会先询问动作的进行(常用)
-l  ：进行硬式连结(hard link)的连结档创建，而非复制文件本身；
-p  ：连同文件的属性一起复制过去，而非使用默认属性(备份常用)；
-r  ：递回持续复制，用于目录的复制行为；(常用)
-s  ：复制成为符号连结档 (symbolic link)，即『捷径』文件；
-u  ：若 destination 比 source 旧才升级 destination ！
最后需要注意的，如果来源档有两个以上，则最后一个目的档一定要是『目录』才行！
```

复制(cp)这个命令是非常重要的，不同身份者运行这个命令会有不同的结果产生，尤其是那个-a, -p的选项， 对於不同身份来说，差异则非常的大！底下的练习中，有的身份为root有的身份为一般帐号(在这里用vbird这个帐号)， 练习时请特别注意身份的差别喔！好！开始来做复制的练习与观察：

```shell
范例一：用root身份，将家目录下的 .bashrc 复制到 /tmp 下，并更名为 bashrc
[root@www ~]# cp ~/.bashrc /tmp/bashrc
[root@www ~]# cp -i ~/.bashrc /tmp/bashrc
cp: overwrite `/tmp/bashrc'? n  <==n不覆盖，y为覆盖
# 重复作两次动作，由于 /tmp 底下已经存在 bashrc 了，加上 -i 选项后，
# 则在覆盖前会询问使用者是否确定！可以按下 n 或者 y 来二次确认呢！

范例二：变换目录到/tmp，并将/var/log/wtmp复制到/tmp且观察属性：
[root@www ~]# cd /tmp
[root@www tmp]# cp /var/log/wtmp . <==想要复制到目前的目录，最后的 . 不要忘
[root@www tmp]# ls -l /var/log/wtmp wtmp
-rw-rw-r-- 1 root utmp 96384 Sep 24 11:54 /var/log/wtmp
-rw-r--r-- 1 root root 96384 Sep 24 14:06 wtmp
# 注意上面的特殊字体，在不加任何选项的情况下，文件的某些属性/权限会改变；
# 这是个很重要的特性！要注意喔！还有，连文件创建的时间也不一样了！
# 那如果你想要将文件的所有特性都一起复制过来该怎办？可以加上 -a 喔！如下所示：

[root@www tmp]# cp -a /var/log/wtmp wtmp_2
[root@www tmp]# ls -l /var/log/wtmp wtmp_2
-rw-rw-r-- 1 root utmp 96384 Sep 24 11:54 /var/log/wtmp
-rw-rw-r-- 1 root utmp 96384 Sep 24 11:54 wtmp_2
# 了了吧！整个数据特性完全一模一样ㄟ！真是不赖～这就是 -a 的特性！
```

 一般来说，我们如果去复制别人的数据 (当然，该文件你必须要有 read 的权限才行啊！) 时， 总是希望复制到的数据最后是我们自己的，所以，在默认的条件中， cp 的来源档与目的档的权限是不同的，目的档的拥有者通常会是命令操作者本身。举例来说， 上面的范例二中，由于我是 root 的身份，因此复制过来的文件拥有者与群组就应该改变成为 root 所有了！ 这样说，可以明白吗？

由于具有这个特性，因此当我们在进行备份的时候，某些需要特别注意的特殊权限文件， 例如密码档 (/etc/shadow) 以及一些配置档，就不能直接以 cp 来复制，而必须要加上 -a 或者是 -p 等等可以完整复制文件权限的选项才行！另外，如果你想要复制文件给其他的使用者， 也必须要注意到文件的权限(包含读、写、运行以及文件拥有者等等)， 否则，其他人还是无法针对你给予的文件进行修订的动作喔！注意注意！

```shell
范例三：复制 /etc/ 这个目录下的所有内容到 /tmp 底下
[root@www tmp]# cp /etc/ /tmp
cp: omitting directory `/etc'   <== 如果是目录则不能直接复制，要加上 -r 的选项
[root@www tmp]# cp -r /etc/ /tmp
# 还是要再次的强调喔！ -r 是可以复制目录，但是，文件与目录的权限可能会被改变
# 所以，也可以利用『 cp -a /etc /tmp 』来下达命令喔！尤其是在备份的情况下！

范例四：将范例一复制的 bashrc 创建一个连结档 (symbolic link)
[root@www tmp]# ls -l bashrc
-rw-r--r-- 1 root root 176 Sep 24 14:02 bashrc  <==先观察一下文件情况
[root@www tmp]# cp -s bashrc bashrc_slink
[root@www tmp]# cp -l bashrc bashrc_hlink
[root@www tmp]# ls -l bashrc*
-rw-r--r-- 2 root root 176 Sep 24 14:02 bashrc  <==与原始文件不太一样了！
-rw-r--r-- 2 root root 176 Sep 24 14:02 bashrc_hlink
lrwxrwxrwx 1 root root   6 Sep 24 14:20 bashrc_slink -> bashrc
```

范例四可有趣了！使用 `-l` 及 `-s` 都会创建所谓的连结档(link file)，但是这两种连结档却有不一样的情况。这是怎么一回事啊？ 那个 -l 就是所谓的实体连结(hard link)，至于 -s 则是符号连结(symbolic link)， 简单来说，bashrc_slink 是一个『捷径』，这个捷径会连结到bashrc去！所以你会看到档名右侧会有个指向(->)的符号！

至于bashrc_hlink文件与bashrc的属性与权限完全一模一样，与尚未进行连结前的差异则是第二栏的link数由1变成2了！ 这里先不介绍实体连结，因为实体连结涉及 i-node 的相关知识，我们之后谈到文件系统(filesystem)时再来讨论这个问题。

```shell
范例五：若 ~/.bashrc 比 /tmp/bashrc 新才复制过来
[root@www tmp]# cp -u ~/.bashrc /tmp/bashrc
# 这个 -u 的特性，是在目标文件与来源文件有差异时，才会复制的。
# 所以，比较常被用於『备份』的工作当中喔！ ^_^

范例六：将范例四造成的 bashrc_slink 复制成为 bashrc_slink_1 与bashrc_slink_2
[root@www tmp]# cp bashrc_slink bashrc_slink_1
[root@www tmp]# cp -d bashrc_slink bashrc_slink_2
[root@www tmp]# ls -l bashrc bashrc_slink*
-rw-r--r-- 2 root root 176 Sep 24 14:02 bashrc
lrwxrwxrwx 1 root root   6 Sep 24 14:20 bashrc_slink -> bashrc
-rw-r--r-- 1 root root 176 Sep 24 14:32 bashrc_slink_1       <==与原始文件相同
lrwxrwxrwx 1 root root   6 Sep 24 14:33 bashrc_slink_2 -> bashrc <==是连结档！
# 这个例子也是很有趣喔！原本复制的是连结档，但是却将连结档的实际文件复制过来了
# 也就是说，如果没有加上任何选项时，cp复制的是原始文件，而非连结档的属性！
# 若要复制连结档的属性，就得要使用 -d 的选项了！如 bashrc_slink_2 所示。

范例七：将家目录的 .bashrc 及 .bash_history 复制到 /tmp 底下
[root@www tmp]# cp ~/.bashrc ~/.bash_history /tmp
# 可以将多个数据一次复制到同一个目录去！最后面一定是目录！
```

> 例题：
> 
> 你能否使用vbird的身份，完整的复制`/var/log/wtmp`文件到`/tmp`底下，并更名为`vbird_wtmp`呢？
> 
> 答：
> 
> 实际做看看的结果如下：  
> 
> ```shell
> [vbird@www ~]$ cp -a /var/log/wtmp /tmp/vbird_wtmp
> [vbird@www ~]$ ls -l /var/log/wtmp /tmp/vbird_wtmp
> -rw-rw-r-- 1 vbird vbird 96384 9月 24 11:54 /tmp/vbird_wtmp
> -rw-rw-r-- 1 root utmp 96384 9月 24 11:54 /var/log/wtmp
> ```
> 
> vbird的身份并不能随意修改文件的拥有者与群组，因此虽然能够复制wtmp的相关权限与时间等属性， 但是与拥有者、群组相关的，原本vbird身份无法进行的动作，即使加上 -a 选项，也是无法达成完整复制权限的！

总之，由于 cp 有种种的文件属性与权限的特性，所以，在复制时，你必须要清楚的了解到：

- 是否需要完整的保留来源文件的资讯？用`-a`

- 来源文件是否为连结档 (symbolic link file)？成为连结档用`-s`，复制连结档用`-d`

- 来源档是否为特殊的文件，例如 FIFO, socket 等？

- 来源档是否为目录？用`-r`
  
  ----

### 移除文件或目录（rm）

```shell
[root@www ~]# rm [-fir] 文件或目录
选项与参数：
-f  ：就是 force 的意思，忽略不存在的文件，不会出现警告信息；
-i  ：互动模式，在删除前会询问使用者是否动作
-r  ：递回删除啊！最常用在目录的删除了！这是非常危险的选项！！！

范例一：将刚刚在 cp 的范例中创建的 bashrc 删除掉！
[root@www ~]# cd /tmp
[root@www tmp]# rm -i bashrc
rm: remove regular file `bashrc'? y
# 如果加上 -i 的选项就会主动询问喔，避免你删除到错误的档名！

范例二：透过万用字节*的帮忙，将/tmp底下开头为bashrc的档名通通删除：
[root@www tmp]# rm -i bashrc*
# 注意那个星号，代表的是 0 到无穷多个任意字节喔！很好用的东西！

范例三：将 cp 范例中所创建的 /tmp/etc/ 这个目录删除掉！
[root@www tmp]# rmdir /tmp/etc
rmdir: etc: Directory not empty  <== 删不掉啊！因为这不是空的目录！
[root@www tmp]# rm -r /tmp/etc
rm: descend into directory `/tmp/etc'? y
....(中间省略)....
# 因为身份是 root ，默认已经加入了 -i 的选项，所以你要一直按 y 才会删除！
# 如果不想要继续按 y ，可以按下『 [ctrl]-c 』来结束 rm 的工作。
# 这是一种保护的动作，如果确定要删除掉此目录而不要询问，可以这样做：
[root@www tmp]# \rm -r /tmp/etc
# 在命令前加上反斜线，可以忽略掉 alias 的指定选项喔！至于 alias 我们在bash再谈！

范例四：删除一个带有 - 开头的文件
[root@www tmp]# touch ./-aaa-  <==touch这个命令可以创建空文件！
[root@www tmp]# ls -l 
-rw-r--r-- 1 root  root      0 Sep 24 15:03 -aaa-  <==文件大小为0，所以是空文件
[root@www tmp]# rm -aaa-
Try `rm --help' for more information.  <== 因为 "-" 是选项嘛！所以系统误判了！
[root@www tmp]# rm ./-aaa-
```

这是移除的命令(remove)，

要注意的是，通常在Linux系统下，为了怕文件被误杀，所以很多 distributions 都已经默认加入 -i 这个选项了！

而如果要连目录下的东西都一起杀掉的话， 例如子目录里面还有子目录时，那就要使用 -r 这个选项了！

不过，使用『 rm -r 』这个命令之前，请千万注意了，因为该目录或文件『肯定』会被 root 杀掉！因为系统不会再次询问你是否要砍掉呦！所以那是个超级严重的命令下达呦！ 得特别注意！

不过，如果你确定该目录不要了，那么使用 rm -r 来循环杀掉是不错的方式！

另外，范例四也是很有趣的例子，我们在之前就谈过，档名最好不要使用 "-" 号开头， 因为 "-" 后面接的是选项，因此，单纯的使用『 rm -aaa- 』系统的命令就会误判啦！ 那如果使用后面会谈到的正规表示法时，还是会出问题的！所以，只能用避过首位字节是 "-" 的方法啦！ 就是加上本目录『 ./ 』即可！如果 man rm 的话，其实还有一种方法，那就是『 rm -- -aaa- 』也可以啊！

### 移动/更名文件与目录（mv）

```shell
[root@www ~]# mv [-fiu] source destination
[root@www ~]# mv [options] source1 source2 source3 .... directory
选项与参数：
-f  ：force 强制的意思，如果目标文件已经存在，不会询问而直接覆盖；
-i  ：若目标文件 (destination) 已经存在时，就会询问是否覆盖！
-u  ：若目标文件已经存在，且 source 比较新，才会升级 (update)

范例一：复制一文件，创建一目录，将文件移动到目录中
[root@www ~]# cd /tmp
[root@www tmp]# cp ~/.bashrc bashrc
[root@www tmp]# mkdir mvtest
[root@www tmp]# mv bashrc mvtest
# 将某个文件移动到某个目录去，就是这样做！

范例二：将刚刚的目录名称更名为 mvtest2
[root@www tmp]# mv mvtest mvtest2 <== 这样就更名了！简单～
# 其实在 Linux 底下还有个有趣的命令，名称为 rename ，
# 该命令专职进行多个档名的同时更名，并非针对单一档名变更，与mv不同。请man rename。

范例三：再创建两个文件，再全部移动到 /tmp/mvtest2 当中
[root@www tmp]# cp ~/.bashrc bashrc1
[root@www tmp]# cp ~/.bashrc bashrc2
[root@www tmp]# mv bashrc1 bashrc2 mvtest2
# 注意到这边，如果有多个来源文件或目录，则最后一个目标档一定是『目录！』
# 意思是说，将所有的数据移动到该目录的意思！
```

这是搬移 (move) 的意思！

当你要移动文件或目录的时候，这个命令就很重要啦！ 同样的，你也可以使用 -u ( update )来测试新旧文件，看看是否需要搬移！ 

另外一个用途就是『变更档名！』，我们可以很轻易的使用 mv 来变更一个文件的档名呢！不过，在 Linux 才有的命令当中，有个 rename ， 可以用来更改大量文件的档名，你可以利用 man rename 来查阅一下，也是挺有趣的命令喔！

### 取得路径的文件名称与目录名称

前面介绍的完整档名 (包含目录名称与文件名称) 当中提到，完整档名最长可以到达 4096 个字节。 

那么你怎么知道哪个是档名？哪个是目录名？就是利用斜线 (/) 来分辨啊！ 其实，取得档名或者是目录名称，一般的用途应该是在写程序的时候，用来判断用的啦～ 

所以，这部分的命令可以用在 shell scripts 里头喔！ 底下我们简单的以几个范例来谈一谈 basename 与 dirname 的用途！

```shell
[root@www ~]# basename /etc/sysconfig/network
network         <== 很简单！就取得最后的档名～
[root@www ~]# dirname /etc/sysconfig/network
/etc/sysconfig  <== 取得的变成目录名了！
```

## 文件内容查阅

之前学习了如何切换目录，读取文件等一系列操作，那如果我们要查阅一个文件的内容时，该如何是好呢？

这里有相当多有趣的命令可以来分享一下： 最常使用的显示文件内容的命令可以说是 `cat` 与 `more` 及 `less` 了！此外，如果我们要查看一个很大型的文件 (好几百MB时)，但是我们只需要后端的几行字而已，那么该如何是好？用 `tail` 呀，此外， `tac` 这个命令也可以达到！好了，说说各个命令的用途吧！

- `cat`  由第一行开始显示文件内容
- `tac`  从最后一行开始显示，可以看出 tac 是 cat 的倒写！
- `nl`   显示的时候，顺便输出行号！
- `more` 一页一页的显示文件内容
- `less` 与 `more` 类似，但是比 `more` 更好的是，他可以往前翻页！
- `head` 只看头几行
- `tail` 只看尾巴几行
- `od`   以二进位的方式读取文件内容！

----

### 直接检阅文件内容：cat，tac，nl

直接查阅一个文件的内容可以使用 cat/tac/nl 这几个命令啊！

#### cat（con<u>cat</u>enate）

```shell
[root@www ~]# cat [-AbEnTv]
选项与参数：
-A  ：相当于 -vET 的整合选项，可列出一些特殊字符而不是空白而已；
-b  ：列出行号，仅针对非空白行做行号显示，空白行不标行号！
-E  ：将结尾的断行字节 $ 显示出来；
-n  ：列印出行号，连同空白行也会有行号，与 -b 的选项不同；
-T  ：将 [tab] 按键以 ^I 显示出来；
-v  ：列出一些看不出来的特殊字符

范例一：检阅 /etc/issue 这个文件的内容
[root@www ~]# cat /etc/issue
CentOS release 5.3 (Final)
Kernel \r on an \m

范例二：承上题，如果还要加印行号呢？
[root@www ~]# cat -n /etc/issue
     1  CentOS release 5.3 (Final)
     2  Kernel \r on an \m
     3
# 看到了吧！可以印出行号呢！这对于大文件要找某个特定的行时，有点用处！
# 如果不想要编排空白行的行号，可以使用『cat -b /etc/issue』，自己测试看看：

范例三：将 /etc/xinetd.conf 的内容完整的显示出来(包含特殊字节)
[root@www ~]# cat -A /etc/xinetd.conf
#$
....(中间省略)....
$
defaults$
{$
# The next two items are intended to be a quick access place to$
....(中间省略)....
^Ilog_type^I= SYSLOG daemon info $
^Ilog_on_failure^I= HOST$
^Ilog_on_success^I= PID HOST DURATION EXIT$
....(中间省略)....
includedir /etc/xinetd.d$
 $
# 上面的结果限于篇幅，已经删除掉很多数据了。另外，输出的结果并不会有特殊字体，
# 上面的特殊字体是要让你发现差异点在哪里就是了。基本上，在一般的环境中，
# 使用 [tab] 与空白键的效果差不多，都是一堆空白啊！我们无法知道两者的差别。
# 此时使用 cat -A 就能够发现那些空白的地方是啥鬼东西了！[tab]会以 ^I 表示，
# 断行字节则是以 $ 表示，所以你可以发现每一行后面都是 $ 啊！不过断行字节
# 在Windows/Linux则不太相同，Windows的断行字节是 ^M$ 罗。
# 这部分我们会在 vim 的介绍时，再次的说明到喔！
```

嘿嘿！Linux 里面有『猫』命令？

喔！不是的， cat 是 Concatenate （连续）的简写， 主要的功能是将一个文件的内容连续的印出在屏幕上面！例如上面的例子中，我们将 `/etc/issue` 印出来！如果加上 `-n` 或 `-b` 的话，则每一行前面还会加上行号呦！

个人是比较少用 cat 啦！毕竟当你的文件内容的行数超过 40 行以上，嘿嘿！根本来不及在屏幕上看到结果！ 

所以，配合等一下要介绍的 more 或者是 less 来运行比较好！此外，如果是一般的 DOS 文件时，就需要特别留意一些奇奇怪怪的符号了， 例如断行与 [tab] 等，要显示出来，就得加入 -A 之类的选项了！

#### tac (反向列示)

```shell
[root@www ~]# tac /etc/issue

Kernel \r on an \m
CentOS release 5.3 (Final)

# 嘿嘿！与刚刚上面的范例一比较，是由最后一行先显示喔！
```

tac 这个好玩了！怎么说呢？详细的看一下， cat 与 tac ，有没有发现呀！对啦！ tac 刚好是将 cat 反写过来，所以他的功能就跟 cat 相反啦， 

cat 是由『第一行到最后一行连续显示在萤幕上』，而 tac 则是『 由最后一行到第一行反向在萤幕上显示出来 』，很好玩吧！

#### nl（添加行号列印）

```shell
[root@www ~]# nl [-bnw] 文件
选项与参数：
-b  ：指定行号指定的方式，主要有两种：
      -b a ：表示不论是否为空行，也同样列出行号(类似 cat -n)；
      -b t ：如果有空行，空的那一行不要列出行号(默认值)；
-n  ：列出行号表示的方法，主要有三种：
      -n ln ：行号在萤幕的最左方显示；
      -n rn ：行号在自己栏位的最右方显示，且不加 0 ；
      -n rz ：行号在自己栏位的最右方显示，且加 0 ；
-w  ：行号栏位的占用的位数。

范例一：用 nl 列出 /etc/issue 的内容
[root@www ~]# nl /etc/issue
     1  CentOS release 5.3 (Final)
     2  Kernel \r on an \m

# 注意看，这个文件其实有三行，第三行为空白(没有任何字节)，
# 因为他是空白行，所以 nl 不会加上行号喔！如果确定要加上行号，可以这样做：

[root@www ~]# nl -b a /etc/issue
     1  CentOS release 5.3 (Final)
     2  Kernel \r on an \m
     3
# 呵呵！行号加上来罗～那么如果要让行号前面自动补上 0 呢？可这样

[root@www ~]# nl -b a -n rz /etc/issue
000001  CentOS release 5.3 (Final)
000002  Kernel \r on an \m
000003
# 嘿嘿！自动在自己栏位的地方补上 0 了～默认栏位是六位数，如果想要改成 3 位数？

[root@www ~]# nl -b a -n rz -w 3 /etc/issue
001     CentOS release 5.3 (Final)
002     Kernel \r on an \m
003
# 变成仅有 3 位数～
```

nl 可以将输出的文件内容自动的加上行号！其默认的结果与 cat -n 有点不太一样， nl 可以将行号做比较多的显示设计，包括位数与是否自动补齐 0 等等的功能呢。

### 可翻页检视：more，less

前面提到的 nl 与 cat, tac 等等，都是一次性的将数据一口气显示到萤幕上面，那有没有可以进行一页一页翻动的命令啊？ 让我们可以一页一页的观察，才不会前面的数据看不到啊～呵呵！有的！那就是 more 与 less 咯～

----

#### more (一页一页翻动)

```shell
[root@www ~]# more /etc/man.config
#
# Generated automatically from man.conf.in by the
# configure script.
#
# man.conf from man-1.6d
....(中间省略)....
--More--(28%)  <== 重点在这一行喔！你的光标也会在这里等待你的命令
```

仔细的给他看到上面的范例，如果 more 后面接的文件内容行数大于萤幕输出的行数时， 就会出现类似上面的图示。

重点在最后一行，最后一行会显示出目前显示的百分比， 而且还可以在最后一行输入一些有用的命令喔！在 more 这个程序的运行过程中，你有几个按键可以按的：

- 空白键 (space)：代表向下翻一页；

- Enter         ：代表向下翻『一行』；

- /字串         ：代表在这个显示的内容当中，向下搜寻『字串』这个关键字；

- :f            ：立刻显示出档名以及目前显示的行数；

- q             ：代表立刻离开 more ，不再显示该文件内容。

- b 或 [ctrl]-b ：代表往回翻页，不过这动作只对文件有用，对管线无用。

总结：要离开more这个命令，就按q【quit】；要向下翻页，就按空格；搜索的话：

```shell
[root@www ~]# more /etc/man.config
#
# Generated automatically from man.conf.in by the
# configure script.
#
# man.conf from man-1.6d
....(中间省略)....
/MANPATH   <== 输入了 / 之后，光标就会自动跑到最底下一行等待输入！
```

如同上面的说明，输入了 / 之后，光标就会跑到最底下一行，并且等待你的输入， 你输入了字串并按下[enter]之后. more 就会开始向下搜寻该字串～而重复搜寻同一个字串， 可以直接按下 n 即可啊！最后，不想要看了，就按下 q 即可离开 more 啦！

#### less (一页一页翻动)

```shell
[root@www ~]# less /etc/man.config
#
# Generated automatically from man.conf.in by the
# configure script.
#
# man.conf from man-1.6d
....(中间省略)....
:   <== 这里可以等待你输入命令！
```

less 的用法比起 more 又更加的有弹性，怎么说呢？

在 more 的时候，我们并没有办法向前面翻， 只能往后面看，但若使用了 less 时，就可以使用 [pageup] [pagedown] 等按键的功能来往前往后翻看文件，你瞧，是不是更容易使用来观看一个文件的内容了呢！

除此之外，在 less 里头可以拥有更多的『搜寻』功能喔！不止可以向下搜寻，也可以向上搜寻～ 实在是很不错～基本上，可以输入的命令有：

- 空白键    ：向下翻动一页；
- [pagedown]：向下翻动一页；
- [pageup]  ：向上翻动一页；
- /字串     ：向下搜寻『字串』的功能；
- ?字串     ：向上搜寻『字串』的功能；
- n         ：重复前一个搜寻 (与 / 或 ? 有关！)
- N         ：反向的重复前一个搜寻 (与 / 或 ? 有关！)
- q         ：离开 less 这个程序；

你是否会觉得 less 使用的画面与环境与 [man page] 非常的类似呢？没错啦！因为man这个命令就是呼叫 less 来显示说明文件的内容的！ 现在你是否觉得 less 很重要呢？ ^_^

### 数据撷取：head，tail

我们可以将输出的数据作一个最简单的撷取，那就是取出前面 (head) 与取出后面 (tail) 文字的功能。 不过，要注意的是， head 与 tail 都是以『行』为单位来进行数据撷取的哦！

----

#### head（取前几行）

```shell
[root@www ~]# head [-n number] 文件 
选项与参数：
-n  ：后面接数字，代表显示几行的意思

[root@www ~]# head /etc/man.config
# 默认的情况中，显示前面十行！若要显示前 20 行，就得要这样：
[root@www ~]# head -n 20 /etc/man.config

范例：如果后面100行的数据都不列印，只列印/etc/man.config的前面几行，该如何是好？
[root@www ~]# head -n -100 /etc/man.config
```

head 的英文意思就是『头』啦，那么这个东西的用法自然就是显示出一个文件的前几行咯！ 没错！就是这样！若没有加上 -n 这个选项时，默认只显示十行，若只要一行呢？

那就加入『 head -n 1 filename 』即可！

另外那个 -n 选项后面的参数较有趣，如果接的是负数？

例如上面范例的-n -100时，代表列前的所有行数， 但不包括后面100行。

举例来说，/etc/man.config共有141行，则上述的命令『head -n -100 /etc/man.config』 就会**列出前面41行**，后面100行不会列印出来了。这样说，比较容易懂了吧？ ^_^

#### tail（取后几行）

```shell
[root@www ~]# tail [-n number] 文件 
选项与参数：
-n  ：后面接数字，代表显示几行的意思
-f  ：表示持续侦测后面所接的档名，要等到按下[ctrl]-c才会结束tail的侦测

[root@www ~]# tail /etc/man.config
# 默认的情况中，显示最后的十行！若要显示最后的 20 行，就得要这样：
[root@www ~]# tail -n 20 /etc/man.config

范例一：如果不知道/etc/man.config有几行，却只想列出100行以后的数据时？
[root@www ~]# tail -n +100 /etc/man.config

范例二：持续侦测/var/log/messages的内容
[root@www ~]# tail -f /var/log/messages
  <==要等到输入[crtl]-c之后才会离开tail这个命令的侦测！
```

有 head 自然就有 tail ( 尾巴 ) ！

没错！这个 tail 的用法跟 head 的用法差不多类似，只是显示的是后面几行就是了！

默认也是显示十行，若要显示非十行，就加 -n number 的选项即可。

范例一的内容就有趣啦！其实与head -n -xx有异曲同工之妙。

当下达『tail -n +100 /etc/man.config』 代表该文件从100行以后都会被列出来，同样的，在man.config共有141行，因此第100~141行就会被列出来啦！ 前面的99行都不会被显示出来喔！

至於范例二中，由于/var/log/messages随时会有数据写入，你想要让该文件有数据写入时就立刻显示到萤幕上， 就利用 -f 这个选项，他可以一直侦测/var/log/messages这个文件，新加入的数据都会被显示到萤幕上。 直到你按下[crtl]-c才会离开tail的侦测喔！

> 例题：
> 
> 假如我想要显示 /etc/man.config 的第 11 到第 20 行呢？
> 
> 答：
> 
> 这个应该不算难，想一想，在第 11 到第 20 行，那么我取前 20 行，再取后十行，所以结果就是：『 head -n 20 /etc/man.config | tail -n 10 』，这样就可以得到第 11 到第 20 行之间的内容了！ 但是里面涉及到管线命令，需要在后面的时候才讲到！

### 非纯文字档

我们上面提到的，都是在查阅纯文字档的内容。 

那么万一我们想要查阅非文字档，举例来说，例如 /usr/bin/passwd 这个运行档的内容时， 又该如何去读出资讯呢？

事实上，由于运行档通常是 binary file（二进制文件） ，使用上头提到的命令来读取他的内容时， 确实会产生类似乱码的数据啊！那怎么办？没关系，我们可以利用 od 这个命令来读取！

```shell
[root@www ~]# od [-t TYPE] 文件
选项或参数：
-t  ：后面可以接各种『类型 (TYPE)』的输出，例如：
      a       ：利用默认的字节来输出；
      c       ：使用 ASCII 字节来输出
      d[size] ：利用十进位(decimal)来输出数据，每个整数占用 size bytes ；
      f[size] ：利用浮点数值(floating)来输出数据，每个数占用 size bytes ；
      o[size] ：利用八进位(octal)来输出数据，每个整数占用 size bytes ；
      x[size] ：利用十六进位(hexadecimal)来输出数据，每个整数占用 size bytes ；

范例一：请将/usr/bin/passwd的内容使用ASCII方式来展现！
[root@www ~]# od -t c /usr/bin/passwd
0000000 177   E   L   F 001 001 001  \0  \0  \0  \0  \0  \0  \0  \0  \0
0000020 002  \0 003  \0 001  \0  \0  \0 260 225 004  \b   4  \0  \0  \0
0000040 020   E  \0  \0  \0  \0  \0  \0   4  \0      \0  \a  \0   (  \0
0000060 035  \0 034  \0 006  \0  \0  \0   4  \0  \0  \0   4 200 004  \b
0000100   4 200 004  \b 340  \0  \0  \0 340  \0  \0  \0 005  \0  \0  \0
.....(后面省略)....
# 最左边第一栏是以 8 进位来表示bytes数。以上面范例来说，第二栏0000020代表开头是
# 第 16 个 byes (2x8) 的内容之意。

范例二：请将/etc/issue这个文件的内容以8进位列出储存值与ASCII的对照表
[root@www ~]# od -t oCc /etc/issue
0000000 103 145 156 164 117 123 040 162 145 154 145 141 163 145 040 065
          C   e   n   t   O   S       r   e   l   e   a   s   e       5
0000020 056 062 040 050 106 151 156 141 154 051 012 113 145 162 156 145
          .   2       (   F   i   n   a   l   )  \n   K   e   r   n   e
0000040 154 040 134 162 040 157 156 040 141 156 040 134 155 012 012
          l       \   r       o   n       a   n       \   m  \n  \n
0000057
# 如上所示，可以发现每个字节可以对应到的数值为何！
# 例如e对应的记录数值为145，转成十进位：1x8^2+4x8+5=101。
```

利用这个命令，可以将 data file 或者是 binary file 的内容数据给他读出来喔！ 虽然读出的来数值默认是使用非文字档，亦即是 16 进位的数值来显示的， 不过，我们还是可以透过 -t c 的选项与参数来将数据内的字节以 ASCII 类型的字节来显示， 虽然对于一般使用者来说，这个命令的用处可能不大，但是对于工程师来说， 这个命令可以将 binary file 的内容作一个大致的输出，他们可以看得出东西的啦～ ^_^

如果对纯文字档使用这个命令，你甚至可以发现到 ASCII 与字节的对照表！

非常有趣！ 例如上述的范例二，你可以发现到每个英文字 e 对照到的数字都是 145，转成十进位你就能够发现那是 101 咯！ 如果你有任何程序语言的书，拿出来对照一下 ASCII 的对照表，就能够发现真是正确啊！

### 修改时间与新建文档：touch

我们在 [ls 这个命令的介绍]时，有稍微提到每个文件在linux底下都会记录许多的时间参数， 其实是有三个主要的变动时间，那么三个时间的意义是什么呢？

- **modification time (mtime)**：  
  当该文件的『内容数据』变更时，就会升级这个时间！内容数据指的是文件的内容，而不是文件的属性或权限喔！  

- **status time (ctime)**：  
  当该文件的『状态 (status)』改变时，就会升级这个时间，举例来说，像权限与属性被更改了，都会升级这个时间啊！  

- **access time (atime)**：  
  当『该文件的内容被读取』时，就会升级这个读取时间 (access)。举例来说，我们使用 cat 去读取 /etc/man.config ， 就会升级该文件的 atime 了。

这是个挺有趣的现象，举例来说，我们来看一看你自己的 /etc/man.config 这个文件的时间吧！

```shell
[root@www ~]# ls -l /etc/man.config
-rw-r--r-- 1 root root 4617 Jan  6  2007 /etc/man.config
[root@www ~]# ls -l --time=atime /etc/man.config
-rw-r--r-- 1 root root 4617 Sep 25 17:54 /etc/man.config
[root@www ~]# ls -l --time=ctime /etc/man.config
-rw-r--r-- 1 root root 4617 Sep  4 18:03 /etc/man.config
```

看到了吗？在默认的情况下，ls 显示出来的是该文件的 mtime ，也就是这个文件的内容上次被更动的时间。 

 还记得刚刚我们使用的范例当中，有使用到man.config这个文件啊，所以啊，他的 atime 就会变成刚刚使用的时间了！

文件的时间是很重要的，因为，如果文件的时间误判的话，可能会造成某些程序无法顺利的运行。 

OK！那么万一我发现了一个文件来自未来，该如何让该文件的时间变成『现在』的时刻呢？ 很简单啊！就用『touch』这个命令即可！

> **Tips:**  
> 嘿嘿！不要怀疑系统时间会『来自未来』喔！很多时候会有这个问题的！举例来说在安装过后系统时间可能会被改变！ 因为中国的台湾时区在国际标准时间『格林威治时间, GMT』的右边，所以会比较早看到阳光，也就是说，中国的台湾时间比GMT时间快了八小时！ 如果安装行为不当，系统可能会有八小时快转，你的文件就有可能来自八小时后了。  至于某些情况下，由於BIOS的配置错误，导致系统时间跑到未来时间，并且你又创建了某些文件。 等你将时间改回正确的时间时，该文件不就变成来自未来了？^_^

```shell
[root@www ~]# touch [-acdmt] 文件
选项与参数：
-a  ：仅修订 access time；
-c  ：仅修改文件的时间，若该文件不存在则不创建新文件；
-d  ：后面可以接欲修订的日期而不用目前的日期，也可以使用 --date="日期或时间"
-m  ：仅修改 mtime ；
-t  ：后面可以接欲修订的时间而不用目前的时间，格式为[YYMMDDhhmm]

范例一：新建一个空的文件并观察时间
[root@www ~]# cd /tmp
[root@www tmp]# touch testtouch
[root@www tmp]# ls -l testtouch
-rw-r--r-- 1 root root 0 Sep 25 21:09 testtouch
# 注意到，这个文件的大小是 0 呢！在默认的状态下，如果 touch 后面有接文件，
# 则该文件的三个时间 (atime/ctime/mtime) 都会升级为目前的时间。若该文件不存在，
# 则会主动的创建一个新的空的文件喔！例如上面这个例子！

范例二：将 ~/.bashrc 复制成为 bashrc，假设复制完全的属性，检查其日期
[root@www tmp]# cp -a ~/.bashrc bashrc
[root@www tmp]# ll bashrc; ll --time=atime bashrc; ll --time=ctime bashrc
-rw-r--r-- 1 root root 176 Jan  6  2007 bashrc  <==这是 mtime
-rw-r--r-- 1 root root 176 Sep 25 21:11 bashrc  <==这是 atime
-rw-r--r-- 1 root root 176 Sep 25 21:12 bashrc  <==这是 ctime
```

在上面这个案例当中我们使用了『ll』这个命令(两个英文L的小写)，这个命令其实就是『ls -l』的意思， ll本身不存在，是被『做出来』的一个命令别名。相关的[命令别名我们会在bash章节]当中详谈的，这里先知道ll="ls -l"即可。 

至于分号『 ; 』则代表连续命令的下达啦！你可以在一行命令当中写入多重命令， 这些命令可以『依序』运行。由上面的命令我们会知道ll那一行有三个命令被下达在同一行中。

在运行的结果当中，我们可以发现数据的内容与属性是被复制过来的，因此文件内容时间(mtime)与原本文件相同。 但是由于这个文件是刚刚被创建的，因此状态(ctime)与读取时间（atime）就便呈现在的时间啦！ 那如果你想要变更这个文件的时间呢？可以这样做：

```shell
范例三：修改案例二的 bashrc 文件，将日期调整为两天前
[root@www tmp]# touch -d "2 days ago" bashrc
[root@www tmp]# ll bashrc; ll --time=atime bashrc; ll --time=ctime bashrc
-rw-r--r-- 1 root root 176 Sep 23 21:23 bashrc
-rw-r--r-- 1 root root 176 Sep 23 21:23 bashrc
-rw-r--r-- 1 root root 176 Sep 25 21:23 bashrc
# 跟上个范例比较看看，本来是 25 日的变成了 23 日了 (atime/mtime)～
# 不过， ctime 并没有跟着改变喔！

范例四：将上个范例的 bashrc 日期改为 2007/09/15 2:02
[root@www tmp]# touch -t 0709150202 bashrc
[root@www tmp]# ll bashrc; ll --time=atime bashrc; ll --time=ctime bashrc
-rw-r--r-- 1 root root 176 Sep 15  2007 bashrc
-rw-r--r-- 1 root root 176 Sep 15  2007 bashrc
-rw-r--r-- 1 root root 176 Sep 25 21:25 bashrc
# 注意看看，日期在 atime 与 mtime 都改变了，但是 ctime 则是记录目前的时间！
```

透过 touch 这个命令，我们可以轻易去修订文件的日期与时间。

并且也可以创建一个空的文件喔！

不过，要注意的是，即使我们复制一个文件时，复制所有的属性，但也没有办法复制 ctime 这个属性的。 ctime 可以记录这个文件最近的状态 (status) 被改变的时间。

无论如何，还是要告知大家， 我们平时看的文件属性中，比较重要的还是属于那个 mtime 啊！我们关心的常常是这个文件的『内容』 是什么时候被更动的？

无论如何， touch 这个命令最常被使用的情况是：

- 创建一个空的文件；
- 将某个文件日期修订为目前 (mtime 与 atime)

## 文件或目录的默认权限与隐藏权限

我们知道一个文件有若干个属性， 包括读写运行(r, w, x)等基本权限，及是否为目录 (d) 与文件 (-) 或者是连结档 (l) 等等的属性！ 

要修改属性的方法在前面也约略提过了([chgrp], [chown], [chmod] ，本小节会再加强补充一下！

除了基本r, w, x权限外，在Linux的Ext2/Ext3文件系统下，我们还可以配置其他的系统隐藏属性， 这部份可使用 [chattr] 来配置，而以 [lsattr] 来查看，最重要的属性就是可以配置其不可修改的特性！让连文件的拥有者都不能进行修改！ 这个属性可是相当重要的，尤其是在安全机制上面 (security)！

首先，先来复习一下上一章谈到的权限概念，将底下的例题先看一看：

> 例题：
> 
> 你的系统有个一般身份使用者 dmtsai，他的群组属于 users，他的家目录在 /home/dmtsai， 你是root，你想将你的 ~/.bashrc 复制给他，可以怎么作？
> 
> 答：
> 
> 由上一章的权限概念我们可以知道 root 虽然可以将这个文件复制给 dmtsai，不过这个文件在 dmtsai 的家目录中却可能让 dmtsai 没有办法读写(因为该文件属于 root 的嘛！而 dmtsai 又不能使用 chown 之故)。 此外，我们又担心覆盖掉 dmtsai 自己的 .bashrc 配置档，因此，我们可以进行如下的动作喔：  
> 
> > 复制文件： cp ~/.bashrc ~dmtsai/bashrc  
> > 修改属性： chown dmtsai:users ~dmtsai/bashrc
> 
> ----
> 
> 例题：
> 
> 我想在 /tmp 底下创建一个目录，这个目录名称为 chapter7_1 ，并且这个目录拥有者为 dmtsai， 群组为 users ，此外，任何人都可以进入该目录浏览文件，不过除了 dmtsai 之外，其他人都不能修改该目录下的文件。
> 
> 答：
> 
> 因为除了 dmtsai 之外，其他人不能修改该目录下的文件，所以整个目录的权限应该是 drwxr-xr-x 才对！ 因此你应该这样做：
> 
> > 创建目录： mkdir /tmp/chapter7_1  
> > 修改属性： chown -R dmtsai:users /tmp/chapter7_1  
> > 修改权限： chmod -R 755 /tmp/chapter7_1

假设你对於权限都认识的差不多了，那么底下我们就要来谈一谈，『新增一个文件或目录时，默认的权限是什么？』这个议题！

-----

### 文件默认权限：umask

OK！那么现在我们知道如何创建或者是改变一个目录或文件的属性了，不过， 你知道当你创建一个新的文件或目录时，他的默认权限会是什么吗？

那就与 umask 这个玩意儿有关了！那么 umask 是在搞什么呢？基本上， umask 就是指定 『目前使用者在创建文件或目录时候的权限默认值』， 那么如何得知或配置 umask 呢？他的指定条件以底下的方式来指定：

```shell
[root@www ~]# umask
0022             <==与一般权限有关的是后面三个数字！
[root@www ~]# umask -S
u=rwx,g=rx,o=rx
```

查阅的方式有两种，一种可以直接输入 umask ，就可以看到数字型态的权限配置分数， 一种则是加入 -S (Symbolic) 这个选项，就会以符号类型的方式来显示出权限了！

 奇怪的是，怎么 umask 会有四组数字啊？不是只有三组吗？是没错啦。 第一组是特殊权限用的，我们先不要理他，所以先看后面三组即可。

在默认权限的属性上，**目录与文件是不一样的**。

我们知道 x 权限对于目录是非常重要的！ 但是一般文件的创建则不应该有运行的权限，因为一般文件通常是用在于数据的记录嘛！当然不需要运行的x权限了。 

因此，**默认的情况如下**：

- 若使用者创建为『文件』则默认『没有可运行( x )权限』，亦即只有 rw 这两个项目，也就是最大为 666 分，默认权限如下：  
  -rw-rw-rw-  

- 若使用者创建为『目录』，则由於 x 与是否可以进入此目录有关，因此默认为所有权限均开放，亦即为 777 分，默认权限如下：  
  drwxrwxrwx

要注意的是，umask 的分数指的是『该默认值需要减掉的权限！』因为 r、w、x 分别是 4、2、1 分，所以罗！也就是说，当要拿掉能写的权限，就是输入 2 分，而如果要拿掉能读的权限，也就是 4 分，那么要拿掉读与写的权限，也就是 6 分，而要拿掉运行与写入的权限，也就是 3 分，这样了解吗？请问你， 5 分是什么？呵呵！ 就是读与运行的权限啦！

如果以上面的例子来说明的话，因为 umask 为 022 ，所以 user 并没有被拿掉任何权限，不过 group 与 others 的权限被拿掉了 2 (也就是 w 这个权限)，那么当使用者：

- 创建文件时：(-rw-rw-rw-) - (-----w--w-) ==> -rw-r--r--
- 创建目录时：(drwxrwxrwx) - (d----w--w-) ==> drwxr-xr-x

不相信吗？我们就来测试看看吧！

```shell
[root@www ~]# umask
0022
[root@www ~]# touch test1
[root@www ~]# mkdir test2
[root@www ~]# ll 
-rw-r--r-- 1 root root     0 Sep 27 00:25 test1
drwxr-xr-x 2 root root  4096 Sep 27 00:25 test2
```

### umask的利用与重要性：专题制作

想象一个状况，如果你跟你的同学在同一部主机里面工作时，因为你们两个正在进行同一个专题， 老师也帮你们两个的帐号创建好了相同群组的状态，并且将 /home/class/ 目录做为你们两个人的专题目录。 但有没有可能你所制作的文件你的同学无法编辑？果真如此的话，那就伤脑筋了！

这个问题很常发生啊！举上面的案例来看就好了，你看一下 test1 的权限是几分？ 644 呢！意思是『如果 umask 订定为 022 ，那新建的数据只有使用者自己具有 w 的权限， 同群组的人只有 r 这个可读的权限而已，并无法修改喔！』这样要怎么共同制作专题啊！您说是吧！

所以，当我们需要新建文件给同群组的使用者共同编辑时，那么 umask 的群组就不能拿掉 2 这个 w 的权限！ 所以， umask 就得要是 002 之类的才可以！这样新建的文件才能够是 -rw-rw-r-- 的权限模样喔！ 

那么如何配置 umask 呢？简单的很，直接在 umask 后面输入 002 就好了！

```shell
[root@www ~]# umask 002
[root@www ~]# touch test3
[root@www ~]# mkdir test4
[root@www ~]# ll 
-rw-rw-r-- 1 root root     0 Sep 27 00:36 test3
drwxrwxr-x 2 root root  4096 Sep 27 00:36 test4
```

所以说，这个 umask 对于新建文件与目录的默认权限是很有关系的！这个概念可以用在任何服务器上面， 尤其是未来在你架设文件服务器 (file server)！

> 例题：
> 
> 假设你的 umask 为 003 ，请问该 umask 情况下，创建的文件与目录权限为？
> 
> 答：
> 
> umask 为 003 ，所以拿掉的权限为 --------wx，因此：  
> 
> > 文件： (-rw-rw-rw-) - (--------wx) = -rw-rw-r--  
> > 目录： (drwxrwxrwx) - (--------wx) = drwxrwxr--

> **Tips:**  
> 关于 umask 与权限的计算方式中，教科书喜欢使用二进位的方式来进行 AND 与 NOT 的计算， 不过，本人还是比较喜欢使用符号方式来计算～联想上面比较容易一点～  
> 
> 但是，有的书籍或者是 BBS 上面的朋友，喜欢使用文件默认属性 666 与目录默认属性 777 来与 umask 进行相减的计算～这是不好的喔！以上面例题来看， 如果使用默认属性相加减，则文件变成：666-003=663，亦即是 -rw-rw--wx ，这可是完全不对的喔！ 想想看，原本文件就已经去除 x 的默认属性了，怎么可能突然间冒出来了？ 所以，这个地方得要特别小心喔！

在默认的情况中， root 的 umask 会拿掉比较多的属性，root 的 umask 默认是 022 ， 这是基于安全的考量啦～至于一般身份使用者，通常他们的 umask 为 002 ，亦即保留同群组的写入权力！ 其实，关于默认 umask 的配置可以参考 /etc/bashrc 这个文件的内容，不过，不建议修改该文件！

### 文件隐藏属性

什么？文件还有隐藏属性？光是那九个权限就快要疯掉了，竟然还有隐藏属性，真是要命～ 

但是没办法，就是有文件的隐藏属性存在啊！不过，这些隐藏的属性确实对于系统有很大的帮助的～ 尤其是在系统安全 (Security) 上面，重要的紧呢！

不过要先强调的是，底下的chattr命令只能在Ext2/Ext3的文件系统上面生效， 其他的文件系统可能就无法支持这个命令了。底下我们就来谈一谈如何配置与检查这些隐藏的属性吧！

----

#### chattr (配置文件隐藏属性)

```shell
[root@www ~]# chattr [+-=][ASacdistu] 文件或目录名称
选项与参数：
+   ：添加某一个特殊参数，其他原本存在参数则不动。
-   ：移除某一个特殊参数，其他原本存在参数则不动。
=   ：配置一定，且仅有后面接的参数

A  ：当配置了 A 这个属性时，若你有存取此文件(或目录)时，他的存取时间 atime
     将不会被修改，可避免I/O较慢的机器过度的存取磁碟。这对速度较慢的计算机有帮助
S  ：一般文件是非同步写入磁碟的(原理请参考sync的说明)，如果加上 S 这个
     属性时，当你进行任何文件的修改，该更动会『同步』写入磁碟中。
a  ：当配置 a 之后，这个文件将只能添加数据，而不能删除也不能修改数据，只有root 
     才能配置这个属性。 
c  ：这个属性配置之后，将会自动的将此文件『压缩』，在读取的时候将会自动解压缩，
     但是在储存的时候，将会先进行压缩后再储存(看来对于大文件似乎蛮有用的！)
d  ：当 dump 程序被运行的时候，配置 d 属性将可使该文件(或目录)不会被 dump 备份
i  ：这个 i 可就很厉害了！他可以让一个文件『不能被删除、改名、配置连结也无法
     写入或新增数据！』对于系统安全性有相当大的助益！只有 root 能配置此属性
s  ：当文件配置了 s 属性时，如果这个文件被删除，他将会被完全的移除出这个硬盘
     空间，所以如果误删了，完全无法救回来了喔！
u  ：与 s 相反的，当使用 u 来配置文件时，如果该文件被删除了，则数据内容其实还
     存在磁碟中，可以使用来救援该文件喔！
注意：属性配置常见的是 a 与 i 的配置值，而且很多配置值必须要身为 root 才能配置

范例：请尝试到/tmp底下创建文件，并加入 i 的参数，尝试删除看看。
[root@www ~]# cd /tmp
[root@www tmp]# touch attrtest     <==创建一个空文件
[root@www tmp]# chattr +i attrtest <==给予 i 的属性
[root@www tmp]# rm attrtest        <==尝试删除看看
rm: remove write-protected regular empty file `attrtest'? y
rm: cannot remove `attrtest': Operation not permitted  <==操作不许可
# 看到了吗？呼呼！连 root 也没有办法将这个文件删除呢！赶紧解除配置！

范例：请将该文件的 i 属性取消！
[root@www tmp]# chattr -i attrtest
```

这个命令是很重要的，尤其是在系统的数据安全上面！由于这些属性是隐藏的性质，所以需要以 [lsattr] 才能看到该属性呦！

其中，个人认为最重要的当属 +i 与 +a 这个属性了。+i 可以让一个文件无法被更动，对于需要强烈的系统安全的人来说， 真是相当的重要的！里头还有相当多的属性是需要 root 才能配置的呢！

此外，如果是 log file 这种的登录档，就更需要 +a 这个可以添加，但是不能修改旧有的数据与删除的参数了！怎样？很棒吧！ 未来提到【登录档】的认知时，我们再来聊一聊如何配置他吧！

#### lsattr (显示文件隐藏属性)

```shell
[root@www ~]# lsattr [-adR] 文件或目录
选项与参数：
-a ：将隐藏档的属性也秀出来；
-d ：如果接的是目录，仅列出目录本身的属性而非目录内的档名；
-R ：连同子目录的数据也一并列出来！ 

[root@www tmp]# chattr +aij attrtest
[root@www tmp]# lsattr attrtest
----ia---j--- attrtest
```

使用 chattr 配置后，可以利用 lsattr 来查阅隐藏的属性。

不过， 这两个命令在使用上必须要特别小心，否则会造成很大的困扰。

> 例如：某天你心情好，突然将 /etc/shadow 这个重要的密码记录文件给他配置成为具有 i 的属性，那么过了若干天之后， 你突然要新增使用者，却一直无法新增！别怀疑，赶快去将 i 的属性拿掉吧！

### 文件特殊权限： SUID, SGID, SBIT

我们前面一直提到关于文件的重要权限，那就是 rwx 这三个读、写、运行的权限。 但是，眼尖的朋友们在目录树中， 一定注意到了一件事，那就是，怎么我们的 /tmp 权限怪怪的？ 还有，那个 /usr/bin/passwd 也怪怪的？怎么回事啊？看看先：

```shell
[root@www ~]# ls -ld /tmp ; ls -l /usr/bin/passwd
drwxrwxrwt 7 root root 4096 Sep 27 18:23 /tmp
-rwsr-xr-x 1 root root 22984 Jan  7  2007 /usr/bin/passwd
```

不是应该只有 rwx 吗？还有其他的特殊权限( s 跟 t )啊？

啊.....头又开始昏了～ @_@ 

因为 s 与 t 这两个权限的意义与[系统的帐号 ]及[系统的程序]较为相关， 所以等到后面的章节谈完后你才会比较有概念！底下的说明先看看就好，如果看不懂也没有关系， 先知道s放在哪里称为SUID/SGID以及如何配置即可，等系统程序章节读完后，再回来看看喔！

----

#### Set UID

当 s 这个标志出现在文件拥有者的 x 权限上时，例如刚刚提到的 /usr/bin/passwd 这个文件的权限状态：『-rw**s**r-xr-x』，此时就被称为 Set UID，简称为 SUID 的特殊权限。 那么SUID的权限对于一个文件的特殊功能是什么呢？基本上SUID有这样的限制与功能：

- SUID 权限**仅对二进位程序(binary program)有效**；
- 运行者对于该程序需要具有 x 的可运行权限；
- 本权限仅在运行该程序的过程中有效 (run-time)；
- 运行者将具有该程序拥有者 (owner) 的权限。

讲这么硬的东西你可能对於 SUID 还是没有概念，没关系，我们举个例子来说明好了。 

我们的 Linux 系统中，所有帐号的密码都记录在 /etc/shadow 这个文件里面，这个文件的权限为：『-r-------- 1 root root』，意思是这个文件仅有root可读且仅有root可以强制写入而已。 

既然这个文件仅有 root 可以修改，那么 vbird 这个一般帐号使用者能否自行修改自己的密码呢？ 你可以使用你自己的帐号输入『passwd』这个命令来看看，嘿嘿！一般使用者当然可以修改自己的密码了！

唔！有没有冲突啊！明明 /etc/shadow 就不能让 vbird 这个一般帐户去存取的，为什么 vbird 还能够修改这个文件内的密码呢？ 这就是 SUID 的功能啦！藉由上述的功能说明，我们可以知道

1. vbird 对于 /usr/bin/passwd 这个程序来说是具有 x 权限的，表示 vbird 能运行 passwd；
2. passwd 的拥有者是 root 这个帐号；
3. vbird 运行 passwd 的过程中，会『暂时』获得 root 的权限；
4. /etc/shadow 就可以被 vbird 所运行的 passwd 所修改。

但如果 vbird 使用 cat 去读取 /etc/shadow 时，他能够读取吗？因为 cat 不具有 SUID 的权限，所以 vbird 运行 『cat /etc/shadow』 时，是不能读取 /etc/shadow 的。我们用一张示意图来说明如下：

![](http://cn.linux.vbird.org/linux_basic/0220filemanager_files/suid.gif)

另外，SUID 仅可用在binary program 上， 不能够用在 shell script 上面！这是因为 shell script 只是将很多的 binary 运行档叫进来运行而已！所以 SUID 的权限部分，还是得要看 shell script 呼叫进来的程序的配置， 而不是 shell script 本身。当然，SUID 对於目录也是无效的～这点要特别留意。

#### Set GID

当 s 标志在文件拥有者的 x 项目为 SUID，那 s 在群组的 x 时则称为 Set GID, SGID 咯！

是这样没错！^_^。 举例来说，你可以用底下的命令来观察到具有 SGID 权限的文件喔：

```shell
[root@www ~]# ls -l /usr/bin/locate
-rwx--s--x 1 root slocate 23856 Mar 15  2007 /usr/bin/locate
```

与 SUID 不同的是，SGID 可以针对文件或目录来配置！如果是对文件来说， SGID 有如下的功能：

- SGID 对二进位程序有用；
- 程序运行者对于该程序来说，需具备 x 的权限；
- 运行者在运行的过程中将会获得该程序群组的支持！

举例来说，上面的 /usr/bin/locate 这个程序可以去搜寻 /var/lib/mlocate/mlocate.db 这个文件的内容 (详细说明会在下节讲述)， mlocate.db 的权限如下：

```shell
[root@www ~]# ll /usr/bin/locate /var/lib/mlocate/mlocate.db
-rwx--s--x 1 root slocate   23856 Mar 15  2007 /usr/bin/locate
-rw-r----- 1 root slocate 3175776 Sep 28 04:02 /var/lib/mlocate/mlocate.db
```

与 SUID 非常的类似，若我使用 vbird 这个帐号去运行 locate 时，那 vbird 将会取得 slocate 群组的支持， 因此就能够去读取 mlocate.db 啦！非常有趣吧！

除了 binary program 之外，事实上 SGID 也能够用在目录上，这也是非常常见的一种用途！ 当一个目录配置了 SGID 的权限后，他将具有如下的功能：

- 使用者若对于此目录具有 r 与 x 的权限时，该使用者能够进入此目录；
- 使用者在此目录下的有效群组(effective group)将会变成该目录的群组；
- 用途：若使用者在此目录下具有 w 的权限(可以新建文件)，则使用者所创建的新文件，该新文件的群组与此目录的群组相同。

SGID 对于专案开发来说是非常重要的！因为这涉及群组权限的问题，您可以参考一下本章后续[情境模拟的案例]，应该就能够对 SGID 有一些了解的！^_^

#### Sticky Bit

这个 Sticky Bit, SBIT 目前只针对目录有效，对文件已经没有效果了。 SBIT 对目录的作用是：

- 当使用者对于此目录具有 w, x 权限，亦即具有写入的权限时；
- 当使用者在该目录下创建文件或目录时，仅有自己与 root 才有权力删除该文件

换句话说：当甲这个使用者于 A 目录是具有群组或其他人的身份，并且拥有该目录 w 的权限， 这表示『甲使用者对该目录内任何人创建的目录或文件均可进行 "删除/更名/搬移" 等动作。』 不过，如果将 A 目录加上了 SBIT 的权限项目时， 则甲只能够针对自己创建的文件或目录进行删除/更名/移动等动作，而无法删除他人的文件。

举例来说，我们的 /tmp 本身的权限是『drwxrwxrwt』， 在这样的权限内容下，任何人都可以在 /tmp 内新增、修改文件，但仅有该文件/目录创建者与 root 能够删除自己的目录或文件。这个特性也是挺重要的啊！你可以这样做个简单的测试：

1. 以 root 登陆系统，并且进入 /tmp 当中；
2. touch test，并且更改 test 权限成为 777 ；
3. 以一般使用者登陆，并进入 /tmp；
4. 尝试删除 test 这个文件！

由于 SUID/SGID/SBIT 牵涉到程序的概念，因此再次强调，这部份的数据在您读完程序方面的知识后，要再次的回来瞧瞧喔！ 目前，你先有个简单的基础概念就好了！文末的参考数据也建议阅读一番！  

---

- SUID/SGID/SBIT 权限配置

前面介绍过 SUID 与 SGID 的功能，那么如何配置文件使成为具有 SUID 与 SGID 的权限呢？ 这就需要【数字更改权限】的方法了！ 现在你应该已经知道数字型态更改权限的方式为『三个数字』的组合， 那么如果在这三个数字之前再加上一个数字的话，最前面的那个数字就代表这几个权限了！

- 4 为 SUID
- 2 为 SGID
- 1 为 SBIT

假设要将一个文件权限改为『-rwsr-xr-x』时，由于 s 在使用者权限中，所以是 SUID ，因此， 在原先的 755 之前还要加上 4 ，也就是：『 chmod 4755 filename 』来配置！此外，还有大 S 与大 T 的产生喔！参考底下的范例啦！

> **Tips:**  
> 注意：底下的范例只是练习而已，所以使用同一个文件来配置，你必须了解 SUID 不是用在目录上，而 SBIT 不是用在文件上的喔！

```shell
[root@www ~]# cd /tmp
[root@www tmp]# touch test                  <==创建一个测试用空档
[root@www tmp]# chmod 4755 test; ls -l test <==加入具有 SUID 的权限
-rwsr-xr-x 1 root root 0 Sep 29 03:06 test
[root@www tmp]# chmod 6755 test; ls -l test <==加入具有 SUID/SGID 的权限
-rwsr-sr-x 1 root root 0 Sep 29 03:06 test
[root@www tmp]# chmod 1755 test; ls -l test <==加入 SBIT 的功能！
-rwxr-xr-t 1 root root 0 Sep 29 03:06 test
[root@www tmp]# chmod 7666 test; ls -l test <==具有空的 SUID/SGID 权限
-rwSrwSrwT 1 root root 0 Sep 29 03:06 test
```

最后一个例子就要特别小心啦！怎么会出现大写的 S 与 T 呢？不都是小写的吗？ 

因为 s 与 t 都是取代 x 这个权限的，但是你有没有发现，我们是下达 7666 喔！也就是说， user, group 以及 others 都没有 x 这个可运行的标志( 因为 666 嘛 )，所以，这个 S, T 代表的就是『空的』啦！

怎么说？ SUID 是表示『该文件在运行的时候，具有文件拥有者的权限』，但是文件拥有者都无法运行了，哪里来的权限给其他人使用？当然就是空的啦！ ^_^

----

而除了数字法之外，你也可以透过符号法来处理喔！其中 SUID 为 u+s ，而 SGID 为 g+s ，SBIT 则是 o+t 罗！来看看如下的范例：

```shell
# 配置权限成为 -rws--x--x 的模样：
[root@www tmp]# chmod u=rwxs,go=x test; ls -l test
-rws--x--x 1 root root 0 Aug 18 23:47 test

# 承上，加上 SGID 与 SBIT 在上述的文件权限中！
[root@www tmp]# chmod g+s,o+t test; ls -l test
-rws--s--t 1 root root 0 Aug 18 23:47 test
```

### 观察文件类型：file

如果你想要知道某个文件的基本数据，例如是属於 ASCII 或者是 data 文件，或者是 binary ， 且其中有没有使用到动态函式库 (share library) 等等的资讯，就可以利用 file 这个命令来检阅喔！ 举例来说：

```shell
[root@www ~]# file ~/.bashrc
/root/.bashrc: ASCII text <==告诉我们是 ASCII 的纯文字档啊！
[root@www ~]# file /usr/bin/passwd
/usr/bin/passwd: setuid ELF 32-bit LSB executable, Intel 80386, version 1 
(SYSV), for GNU/Linux 2.6.9, dynamically linked (uses shared libs), for 
GNU/Linux 2.6.9, stripped

# 运行档的数据可就多的不得了！包括这个文件的 suid 权限、兼容 Intel 386

# 等级的硬件平台、使用的是 Linux 核心 2.6.9 的动态函式库连结等等。

[root@www ~]# file /var/lib/mlocate/mlocate.db
/var/lib/mlocate/mlocate.db: data <== 这是 data 文件！
```

透过这个命令，我们可以简单的先判断这个文件的格式为何喔！

## 命令与文件的搜寻

文件的搜寻可就厉害了！因为我们常常需要知道那个文件放在哪里，才能够对该文件进行一些修改或维护等动作。 有些时候某些软件配置档的档名是不变的，但是各 distribution 放置的目录则不同。 此时就得要利用一些搜寻命令将该配置档的完整档名捉出来，这样才能修改嘛！您说是吧！^_^

----

### 命令档名的搜寻

我们知道在终端机模式当中，连续输入两次[tab]按键就能够知道使用者有多少命令可以下达。 那你知不知道这些命令的完整档名放在哪里？举例来说，ls 这个常用的命令放在哪里呢？ 就透过 which 或 type 来找寻吧！

#### which (寻找『运行档』)

```shell
[root@www ~]# which [-a] command
选项或参数：
-a ：将所有由 PATH 目录中可以找到的命令均列出，而不止第一个被找到的命令名称

范例一：分别用root与一般帐号搜寻 ifconfig 这个命令的完整档名
[root@www ~]# which ifconfig
/sbin/ifconfig            <==用 root 可以找到正确的运行档名喔！
[root@www ~]# su - vbird <==切换身份成为 vbird 去！
[vbird@www ~]$ which ifconfig
/usr/bin/which: no ifconfig in (/usr/kerberos/bin:/usr/local/bin:/bin:/usr/bin
:/home/vbird/bin)         <==见鬼了！竟然一般身份帐号找不到！
# 因为 which 是根据使用者所配置的 PATH 变量内的目录去搜寻可运行档的！所以，
# 不同的 PATH 配置内容所找到的命令当然不一样啦！因为 /sbin 不在 vbird 的 
# PATH 中，找不到也是理所当然的啊！明白？
[vbird@www ~]$ exit      <==记得将身份切换回原本的 root

范例二：用 which 去找出 which 的档名为何？
[root@www ~]# which which
alias which='alias | /usr/bin/which --tty-only --read-alias --show-dot '
        /usr/bin/which
# 竟然会有两个 which ，其中一个是 alias 这玩意儿呢！那是啥？
# 那就是所谓的『命令别名』，意思是输入 which 会等於后面接的那串命令啦！
# 更多的数据我们会在 bash 章节中再来谈的！

范例三：请找出 cd 这个命令的完整档名
[root@www ~]# which cd
/usr/bin/which: no cd in (/usr/kerberos/sbin:/usr/kerberos/bin:/usr/local/sbin
:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin)
# 瞎？怎么可能没有 cd ，我明明就能够用 root 运行 cd 的啊！
```

这个命令是根据『PATH』这个环境变量所规范的路径，去搜寻『运行档』的档名～ 所以，重点是找出『运行档』而已！且 which 后面接的是『完整档名』喔！若加上 -a 选项，则可以列出所有的可以找到的同名运行档，而非仅显示第一个而已！

最后一个范例最有趣，怎么 cd 这个常用的命令竟然找不到啊！为什么呢？这是因为 cd 是『bash 内建的命令』啦！ 但是 which 默认是找 PATH 内所规范的目录，所以当然一定找不到的啊！那怎办？没关系！我们可以透过 type 这个命令喔！ 关於 type 的用法我们将在 [ bash] 再来谈！

### 文件档名的搜寻

再来谈一谈怎么搜寻文件吧！在 Linux 底下也有相当优异的搜寻命令呦！

通常 find 不很常用的！因为速度慢之外， 也很操硬盘！

通常我们都是先使用 whereis 或者是 locate 来检查，如果真的找不到了，才以 find 来搜寻呦！ 为什么呢？因为 whereis 与 locate 是利用数据库来搜寻数据，所以相当的快速，而且并没有实际去搜寻硬盘， 比较省时间啦！

#### whereis（寻找特定文件）

```shell
[root@www ~]# whereis [-bmsu] 文件或目录名
选项与参数：
-b    :只找 binary 格式的文件
-m    :只找在说明档 manual 路径下的文件
-s    :只找 source 来源文件
-u    :搜寻不在上述三个项目当中的其他特殊文件

范例一：请用不同的身份找出 ifconfig 这个档名
[root@www ~]# whereis ifconfig 
ifconfig: /sbin/ifconfig /usr/share/man/man8/ifconfig.8.gz
[root@www ~]# su - vbird        <==切换身份成为 vbird
[vbird@www ~]$ whereis ifconfig <==找到同样的结果喔！
ifconfig: /sbin/ifconfig /usr/share/man/man8/ifconfig.8.gz
[vbird@www ~]$ exit              <==回归身份成为 root 去！
# 注意看，明明 which 一般使用者找不到的 ifconfig 却可以让 whereis 找到！
# 这是因为系统真的有 ifconfig 这个『文件』，但是使用者的 PATH 并没有加入 /sbin
# 所以，未来你找不到某些命令时，先用文件搜寻命令找找看再说！

范例二：只找出跟 passwd 有关的『说明文件』档名(man page)
[root@www ~]# whereis -m passwd
passwd: /usr/share/man/man1/passwd.1.gz /usr/share/man/man5/passwd.5.gz
```

等一下我们会提到 find 这个搜寻命令， find 是很强大的搜寻命令，但时间花用的很大！ (因为 find 是直接搜寻硬盘，为如果你的硬盘比较老旧的话，有的等！) 这个时候 whereis 就相当的好用了！

另外， whereis 可以加入选项来找寻相关的数据， 如果你是要找可运行档( binary )那么加上 -b 就可以啦！ 如果不加任何选项的话，那么就将所有的数据列出来罗！

那么 whereis 到底是使用什么咚咚呢？为何搜寻的速度会比 find 快这么多？ 其实那也没有什么！

这是因为 Linux 系统会将系统内的所有文件都记录在一个数据库文件里面， 而当使用 whereis 或者是底下要说的 locate 时，都会以此数据库文件的内容为准， 因此，有的时后你还会发现使用这两个运行档时，会找到已经被杀掉的文件！ 而且也找不到最新的刚刚创建的文件呢！这就是因为这两个命令是由数据库当中的结果去搜寻文件的所在啊！ 更多与这个数据库有关的说明，请参考下列的 locate 命令。

#### locate

```shell
[root@www ~]# locate [-ir] keyword
选项与参数：
-i  ：忽略大小写的差异；
-r  ：后面可接正规表示法的显示方式

范例一：找出系统中所有与 passwd 相关的档名
[root@www ~]# locate passwd
/etc/passwd
/etc/passwd-
/etc/news/passwd.nntp
/etc/pam.d/passwd
....(底下省略)....
```

这个 locate 的使用更简单，直接在后面输入『文件的部分名称』后，就能够得到结果。 举上面的例子来说，我输入 locate passwd ，那么在完整档名 (包含路径名称) 当中，只要有 passwd 在其中， 就会被显示出来的！这也是个很方便好用的命令，如果你忘记某个文件的完整档名时～～

但是，这个东西还是有使用上的限制呦！为什么呢？你会发现使用 locate 来寻找数据的时候特别的快， 这是因为 locate 寻找的数据是由『已创建的数据库 /var/lib/mlocate/』 里面的数据所搜寻到的，所以不用直接在去硬盘当中存取数据，呵呵！当然是很快速罗！

那么有什么限制呢？就是因为他是经由数据库来搜寻的，而数据库的创建默认是在每天运行一次 (每个 distribution 都不同，CentOS 5.x 是每天升级数据库一次！)，所以当你新创建起来的文件， 却还在数据库升级之前搜寻该文件，那么 locate 会告诉你『找不到！』呵呵！因为必须要升级数据库呀！

那能否手动升级数据库哪？当然可以啊！升级 locate 数据库的方法非常简单，直接输入『 updatedb 』就可以了！ updatedb 命令会去读取 /etc/updatedb.conf 这个配置档的配置，然后再去硬盘里面进行搜寻档名的动作， 最后就升级整个数据库文件罗！因为 updatedb 会去搜寻硬盘，所以当你运行 updatedb 时，可能会等待数分钟的时间喔！

- updatedb：根据 /etc/updatedb.conf 的配置去搜寻系统硬盘内的档名，并升级 /var/lib/mlocate 内的数据库文件；
- locate：依据 /var/lib/mlocate 内的数据库记载，找出使用者输入的关键字档名。

#### find

```shell
[root@www ~]# find [PATH] [option] [action]
选项与参数：
1. 与时间有关的选项：共有 -atime, -ctime 与 -mtime ，以 -mtime 说明
   -mtime  n ：n 为数字，意义为在 n 天之前的『一天之内』被更动过内容的文件；
   -mtime +n ：列出在 n 天之前(不含 n 天本身)被更动过内容的文件档名；
   -mtime -n ：列出在 n 天之内(含 n 天本身)被更动过内容的文件档名。
   -newer file ：file 为一个存在的文件，列出比 file 还要新的文件档名

范例一：将过去系统上面 24 小时内有更动过内容 (mtime) 的文件列出
[root@www ~]# find / -mtime 0
# 那个 0 是重点！0 代表目前的时间，所以，从现在开始到 24 小时前，
# 有变动过内容的文件都会被列出来！那如果是三天前的 24 小时内？
# find / -mtime 3 有变动过的文件都被列出的意思！

范例二：寻找 /etc 底下的文件，如果文件日期比 /etc/passwd 新就列出
[root@www ~]# find /etc -newer /etc/passwd
# -newer 用在分辨两个文件之间的新旧关系是很有用的！
```

时间参数真是挺有意思的！我们现在知道 atime, ctime 与 mtime 的意义，如果你想要找出一天内被更动过的文件名称， 可以使用上述范例一的作法。但如果我想要找出『4天内被更动过的文件档名』呢？那可以使用『 find /var -mtime -4 』。那如果是『4天前的那一天』就用『 find /var -mtime 4 』。有没有加上『+, -』差别很大喔！我们可以用简单的图示来说明一下：

![find 相关的时间参数意义](http://cn.linux.vbird.org/linux_basic/0220filemanager_files/find_time.gif "find 相关的时间参数意义")  
图5.2.1、find 相关的时间参数意义  

图中最右边为目前的时间，越往左边则代表越早之前的时间轴啦。由图5.2.1我们可以清楚的知道：

- +4代表大於等於5天前的档名：ex> find /var -mtime +4
- -4代表小於等於4天内的文件档名：ex> find /var -mtime -4
- 4则是代表4-5那一天的文件档名：ex> find /var -mtime 4

非常有趣吧！你可以在 /var/ 目录下搜寻一下，感受一下输出文件的差异喔！再来看看其他 find 的用法吧！

```shell
选项与参数：
2. 与使用者或群组名称有关的参数：
   -uid n ：n 为数字，这个数字是使用者的帐号 ID，亦即 UID ，这个 UID 是记录在
            /etc/passwd 里面与帐号名称对应的数字。这方面我们会在第四篇介绍。
   -gid n ：n 为数字，这个数字是群组名称的 ID，亦即 GID，这个 GID 记录在
            /etc/group，相关的介绍我们会第四篇说明～
   -user name ：name 为使用者帐号名称喔！例如 dmtsai 
   -group name：name 为群组名称喔，例如 users ；
   -nouser    ：寻找文件的拥有者不存在 /etc/passwd 的人！
   -nogroup   ：寻找文件的拥有群组不存在於 /etc/group 的文件！
                当你自行安装软件时，很可能该软件的属性当中并没有文件拥有者，
                这是可能的！在这个时候，就可以使用 -nouser 与 -nogroup 搜寻。

范例三：搜寻 /home 底下属於 vbird 的文件
[root@www ~]# find /home -user vbird
# 这个东西也很有用的～当我们要找出任何一个使用者在系统当中的所有文件时，
# 就可以利用这个命令将属於某个使用者的所有文件都找出来喔！

范例四：搜寻系统中不属於任何人的文件
[root@www ~]# find / -nouser
# 透过这个命令，可以轻易的就找出那些不太正常的文件。
# 如果有找到不属於系统任何人的文件时，不要太紧张，
# 那有时候是正常的～尤其是你曾经以原始码自行编译软件时。
```

如果你想要找出某个使用者在系统底下创建了啥咚咚，使用上述的选项与参数，就能够找出来啦！ 至於那个 -nouser 或 -nogroup 的选项功能中，除了你自行由网络上面下载文件时会发生之外， 如果你将系统里面某个帐号删除了，但是该帐号已经在系统内创建很多文件时，就可能会发生无主孤魂的文件存在！ 此时你就得使用这个 -nouser 来找出该类型的文件！

```shell
选项与参数：
3. 与文件权限及名称有关的参数：
   -name filename：搜寻文件名称为 filename 的文件；
   -size [+-]SIZE：搜寻比 SIZE 还要大(+)或小(-)的文件。这个 SIZE 的规格有：
                   c: 代表 byte， k: 代表 1024bytes。所以，要找比 50KB
                   还要大的文件，就是『 -size +50k 』
   -type TYPE    ：搜寻文件的类型为 TYPE 的，类型主要有：一般正规文件 (f),
                   装置文件 (b, c), 目录 (d), 连结档 (l), socket (s), 
                   及 FIFO (p) 等属性。
   -perm mode  ：搜寻文件权限『刚好等於』 mode 的文件，这个 mode 为类似 chmod
                 的属性值，举例来说， -rwsr-xr-x 的属性为 4755 ！
   -perm -mode ：搜寻文件权限『必须要全部囊括 mode 的权限』的文件，举例来说，
                 我们要搜寻 -rwxr--r-- ，亦即 0744 的文件，使用 -perm -0744，
                 当一个文件的权限为 -rwsr-xr-x ，亦即 4755 时，也会被列出来，
                 因为 -rwsr-xr-x 的属性已经囊括了 -rwxr--r-- 的属性了。
   -perm +mode ：搜寻文件权限『包含任一 mode 的权限』的文件，举例来说，我们搜寻
                 -rwxr-xr-x ，亦即 -perm +755 时，但一个文件属性为 -rw-------
                 也会被列出来，因为他有 -rw.... 的属性存在！

范例五：找出档名为 passwd 这个文件
[root@www ~]# find / -name passwd
# 利用这个 -name 可以搜寻档名啊！

范例六：找出 /var 目录下，文件类型为 Socket 的档名有哪些？
[root@www ~]# find /var -type s
# 这个 -type 的属性也很有帮助喔！尤其是要找出那些怪异的文件，
# 例如 socket 与 FIFO 文件，可以用 find /var -type p 或 -type s 来找！

范例七：搜寻文件当中含有 SGID 或 SUID 或 SBIT 的属性
[root@www ~]# find / -perm +7000 
# 所谓的 7000 就是 ---s--s--t ，那么只要含有 s 或 t 的就列出，
# 所以当然要使用 +7000 ，使用 -7000 表示要含有 ---s--s--t 的所有三个权限，
# 因此，就是 +7000 ～了乎？
```

上述范例中比较有趣的就属 -perm 这个选项啦！他的重点在找出特殊权限的文件罗！ 我们知道 SUID 与 SGID 都可以配置在二进位程序上，假设我想要找出来 /bin, /sbin 这两个目录下， 只要具有 SUID 或 SGID 就列出来该文件，你可以这样做：

```shell
[root@www ~]# find /bin /sbin -perm +6000
```

因为 SUID 是 4 分，SGID 2 分，总共为 6 分，因此可用 +6000 来处理这个权限！ 至於 find 后面可以接多个目录来进行搜寻！另外， find 本来就会搜寻次目录，这个特色也要特别注意喔！ 最后，我们再来看一下 find 还有什么特殊功能吧！

```shell
选项与参数：
4. 额外可进行的动作：
   -exec command ：command 为其他命令，-exec 后面可再接额外的命令来处理搜寻到
                   的结果。
   -print        ：将结果列印到萤幕上，这个动作是默认动作！

范例八：将上个范例找到的文件使用 ls -l 列出来～
[root@www ~]# find / -perm +7000 -exec ls -l {} \;
# 注意到，那个 -exec 后面的 ls -l 就是额外的命令，命令不支持命令别名，
# 所以仅能使用 ls -l 不可以使用 ll 喔！注意注意！

范例九：找出系统中，大於 1MB 的文件
[root@www ~]# find / -size +1000k
# 虽然在 man page 提到可以使用 M 与 G 分别代表 MB 与 GB，
# 不过，俺却试不出来这个功能～所以，目前应该是仅支持到 c 与 k 吧！
```

find 的特殊功能就是能够进行额外的动作(action)。我们将范例八的例子以图解来说明如下：

![find 相关的额外动作](http://cn.linux.vbird.org/linux_basic/0220filemanager_files/find_exec.gif "find 相关的额外动作")  
图5.2.2、find 相关的额外动作  

该范例中特殊的地方有 {} 以及 \; 还有 -exec 这个关键字，这些东西的意义为：

- {} 代表的是『由 find 找到的内容』，如上图所示，find 的结果会被放置到 {} 位置中；
- -exec 一直到 \; 是关键字，代表 find 额外动作的开始 (-exec) 到结束 (\;) ，在这中间的就是 find 命令内的额外动作。 在本例中就是『 ls -l {} 』罗！
- 因为『 ; 』在 bash 环境下是有特殊意义的，因此利用反斜线来跳脱。

透过图 5.2.2 你应该就比较容易了解 -exec 到 \; 之间的意义了吧！

如果你要找的文件是具有特殊属性的，例如 SUID 、文件拥有者、文件大小等等， 那么利用 locate 是没有办法达成你的搜寻的！此时 find 就显的很重要啦！ 另外，find 还可以利用万用字节来找寻档名呢！举例来说，你想要找出 /etc 底下档名包含 httpd 的文件， 那么你就可以这样做：

```shell
[root@www ~]# find /etc -name '*httpd*'
```

不但可以指定搜寻的目录(连同次目录)，并且可以利用额外的选项与参数来找到最正确的档名！真是好好用！ 不过由於 find 在寻找数据的时后相当的操硬盘！所以没事情不要使用 find 啦！有更棒的命令可以取代呦！那就是上面提到的 [whereis](http://cn.linux.vbird.org/linux_basic/0220filemanager.php#whereis) 与 [locate](http://cn.linux.vbird.org/linux_basic/0220filemanager.php#locate) ！

## 极重要！权限与命令间的关系：

我们知道权限对於使用者帐号来说是非常重要的，因为他可以限制使用者能不能读取/创建/删除/修改文件或目录！ 在这一章我们介绍了很多文件系统的管理命令，第六章则介绍了很多文件权限的意义。在这个小节当中， 我们就将这两者结合起来，说明一下什么命令在什么样的权限下才能够运行吧！^_^

一、让使用者能进入某目录成为『可工作目录』的基本权限为何：

- 可使用的命令：例如 cd 等变换工作目录的命令；
- 目录所需权限：使用者对这个目录至少需要具有 x 的权限
- 额外需求：如果使用者想要在这个目录内利用 ls 查阅档名，则使用者对此目录还需要 r 的权限。

二、使用者在某个目录内读取一个文件的基本权限为何？

- 可使用的命令：例如本章谈到的 cat, more, less等等
- 目录所需权限：使用者对这个目录至少需要具有 x 权限；
- 文件所需权限：使用者对文件至少需要具有 r 的权限才行！

三、让使用者可以修改一个文件的基本权限为何？

- 可使用的命令：例如 [nano](http://cn.linux.vbird.org/linux_basic/0160startlinux.php#nano) 或未来要介绍的 [vi](http://cn.linux.vbird.org/linux_basic/0310vi.php) 编辑器等；
- 目录所需权限：使用者在该文件所在的目录至少要有 x 权限；
- 文件所需权限：使用者对该文件至少要有 r, w 权限

四、让一个使用者可以创建一个文件的基本权限为何？

- 目录所需权限：使用者在该目录要具有 w,x 的权限，重点在 w 啦！

五、让使用者进入某目录并运行该目录下的某个命令之基本权限为何？

- 目录所需权限：使用者在该目录至少要有 x 的权限；
- 文件所需权限：使用者在该文件至少需要有 x 的权限

> 例题：
> 
> 让一个使用者 vbird 能够进行『cp /dir1/file1 /dir2』的命令时，请说明 dir1, file1, dir2 的最小所需权限为何？
> 
> 答：
> 
> 运行 cp 时， vbird 要『能够读取来源档，并且写入目标档！』所以应参考上述第二点与第四点的说明！ 因此各文件/目录的最小权限应该是：
> 
> - dir1 ：至少需要有 x 权限；
> - file1：至少需要有 r 权限；=
> - dir2 ：至少需要有 w, x 权限。

> 例题：
> 
> 有一个文件全名为 /home/student/www/index.html ，各相关文件/目录的权限如下：
> 
> ```shell
> drwxr-xr-x 23 root root 4096 Sep 22 12:09 /
> drwxr-xr-x 6 root root 4096 Sep 29 02:21 /home
> drwx------ 6 student student 4096 Sep 29 02:23 /home/student
> drwxr-xr-x 6 student student 4096 Sep 29 02:24 /home/student/www
> -rwxr--r-- 6 student student 369 Sep 29 02:27 /home/student/www/index.html
> ```
> 
> 请问 vbird 这个帐号(不属於student群组)能否读取 index.html 这个文件呢？
> 
> 答：
> 
> 虽然 www 与 index.html 是可以让 vbird 读取的权限，但是因为目录结构是由根目录一层一层读取的， 因此 vbird 可进入 /home 但是却不可进入 /home/student/ ，既然连进入 /home/student 都不许了， 当然就读不到 index.html 了！所以答案是『vbird不会读取到 index.html 的内容』喔！

那要如何修改权限呢？其实只要将 /home/student 的权限修改为最小 711 ，或者直接给予 755 就可以罗！ 这可是很重要的概念喔！

## 情境模拟题：

假设系统中有两个帐号，分别是 alex 与 arod ，这两个人除了自己群组之外还共同支持一个名为 project 的群组。假设这两个用户需要共同拥有 /srv/ahome/ 目录的开发权，且该目录不许其他人进入查阅。 请问该目录的权限配置应为何？请先以传统权限说明，再以 SGID 的功能解析。  

- 目标：了解到为何专案开发时，目录最好需要配置 SGID 的权限！
- 前提：多个帐号支持同一群组，且共同拥有目录的使用权！
- 需求：需要使用 root 的身份来进行 chmod, chgrp 等帮用户配置好他们的开发环境才行！ 这也是管理员的重要任务之一！

首先我们得要先制作出这两个帐号的相关数据，帐号/群组的管理在后续我们会介绍， 您这里先照底下的命令来制作即可：

```shell
[root@www ~]# groupadd project        <==添加新的群组
[root@www ~]# useradd -G project alex <==创建 alex 帐号，且支持 project
[root@www ~]# useradd -G project arod <==创建 arod 帐号，且支持 project
[root@www ~]# id alex                 <==查阅 alex 帐号的属性
uid=501(alex) gid=502(alex) groups=502(alex),501(project) <==确实有支持！
[root@www ~]# id arod
uid=502(arod) gid=503(arod) groups=503(arod),501(project)
```

然后开始来解决我们所需要的环境吧！  

1. 首先创建所需要开发的专案目录：

```shell
[root@www ~]# mkdir /srv/ahome
[root@www ~]# ll -d /srv/ahome
drwxr-xr-x 2 root root 4096 Sep 29 22:36 /srv/ahome
```

2. 从上面的输出结果可发现 alex 与 arod 都不能在该目录内创建文件，因此需要进行权限与属性修改。 由於其他人均不可进入此目录，因此该目录的群组应为project，权限应为770才合理。

```shell
[root@www ~]# chgrp project /srv/ahome
[root@www ~]# chmod 770 /srv/ahome
[root@www ~]# ll -d /srv/ahome
drwxrwx--- 2 root project 4096 Sep 29 22:36 /srv/ahome
# 从上面的权限结果来看，由於 alex/arod 均支持 project，因此似乎没问题了！
```

3. 实际分别以两个使用者来测试看看，情况会是如何？先用 alex 创建文件，然后用 arod 去处理看看。

```shell
[root@www ~]# su - alex       <==先切换身份成为 alex 来处理
[alex@www ~]$ cd /srv/ahome   <==切换到群组的工作目录去
[alex@www ahome]$ touch abcd  <==创建一个空的文件出来！
[alex@www ahome]$ exit        <==离开 alex 的身份

[root@www ~]# su - arod
[arod@www ~]$ cd /srv/ahome
[arod@www ahome]$ ll abcd
-rw-rw-r-- 1 alex alex 0 Sep 29 22:46 abcd
# 仔细看一下上面的文件，由於群组是 alex ，arod并不支持！
# 因此对於 abcd 这个文件来说， arod 应该只是其他人，只有 r 的权限而已啊！
[arod@www ahome]$ exit
```

由上面的结果我们可以知道，若单纯使用传统的 rwx 而已，则对刚刚 alex 创建的 abcd 这个文件来说， arod 可以删除他，但是却不能编辑他！这不是我们要的样子啊！赶紧来重新规划一下。

4. 加入 SGID 的权限在里面，并进行测试看看：

```shell
[root@www ~]# chmod 2770 /srv/ahome
[root@www ~]# ll -d /srv/ahome
drwxrws--- 2 root project 4096 Sep 29 22:46 /srv/ahome

测试：使用 alex 去创建一个文件，并且查阅文件权限看看：
[root@www ~]# su - alex
[alex@www ~]$ cd /srv/ahome
[alex@www ahome]$ touch 1234
[alex@www ahome]$ ll 1234
-rw-rw-r-- 1 alex project 0 Sep 29 22:53 1234
# 没错！这才是我们要的样子！现在 alex, arod 创建的新文件所属群组都是 project，
# 由於两人均属於此群组，加上 umask 都是 002，这样两人才可以互相修改对方的文件
```

所以最终的结果显示，此目录的权限最好是『2770』，所属文件拥有者属於root即可，至於群组必须要为两人共同支持的project 这个群组才行！

## 简答题部分：

- 什么是绝对路径与相对路径
  
  绝对路径的写法为由 / 开始写，至於相对路径则不由 / 开始写！此外，相对路径为相对於目前工作目录的路径！

- 如何更改一个目录的名称？例如由 /home/test 变为 /home/test2
  
  mv /home/test /home/test2

- PATH 这个环境变量的意义？
  
  这个是用来指定运行档运行的时候，命令搜寻的目录路径。

- umask 有什么用处与优点？
  
  umask 可以拿掉一些权限，因此，适当的定义 umask 有助於系统的安全， 因为他可以用来创建默认的目录或文件的权限。

- 当一个使用者的 umask 分别为 033 与 044 他所创建的文件与目录的权限为何？
  
  在 umask 为 033 时，则默认是拿掉 group 与 other 的 w(2)x(1) 权限，因此权限就成为『文件 -rw-r--r-- ， 目录 drwxr--r-- 』而当 umask 044 时，则拿掉 r 的属性，因此就成为『文件 -rw--w--w-，目录 drwx-wx-wx』

- 什么是 SUID ？
  
  当一个命令具有 SUID 的功能时，则：
  
  - SUID 权限仅对二进位程序(binary program)有效；
  - 运行者对於该程序需要具有 x 的可运行权限；
  - 本权限仅在运行该程序的过程中有效 (run-time)；
  - 运行者将具有该程序拥有者 (owner) 的权限。

- 当我要查询 /usr/bin/passwd 这个文件的一些属性时(1)传统权限；(2)文件类型与(3)文件的隐藏属性，可以使用什么命令来查询？
  
  ls -al  
  file  
  lsattr

- 尝试用 find 找出目前 linux 系统中，所有具有 SUID 的文件有哪些？
  
  find / -perm +4000 -print

- 找出 /etc 底下，文件大小介於 50K 到 60K 之间的文件，并且将权限完整的列出 (ls -l)：
  
  find /etc -size +50k -a -size -60k -exec ls -l {} \;  
  注意到 -a ，那个 -a 是 and 的意思，为符合两者才算成功

- 找出 /etc 底下，文件容量大於 50K 且文件所属人不是 root 的档名，且将权限完整的列出 (ls -l)；
  
  find /etc -size +50k -a ! -user root -exec ls -ld {} \;  
  find /etc -size +50k -a ! -user root -type f -exec ls -l {} \;  
  上面两式均可！注意到 ! ，那个 ! 代表的是反向选择，亦即『不是后面的项目』之意！

- 找出 /etc 底下，容量大於 1500K 以及容量等於 0 的文件：
  
  find /etc -size +1500k -o -size 0  
  相对於 -a ，那个 -o 就是或 (or) 的意思罗！