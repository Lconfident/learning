## Git分支

### 主分支Master

首先，代码库应该有一个、且仅有一个主分支。所有提供给用户使用的正式版本，都在这个主分支上发布。

Git主分支的名字，默认叫做Master。它是自动建立的，版本库初始化以后，默认就是在主分支在进行开发。 

### 开发分支Develop

主分支只用来发布重大版本，日常开发应该在另一条分支上完成。我们把开发用的分支，叫做Develop。

 这个分支可以用来生成代码的最新隔夜版本（nightly）。如果想正式对外发布，就在Master分支上，对Develop分支进行"合并"（merge）。 

Git创建Develop分支的命令：

```bash
　　git checkout -b develop master
```

将Develop分支发布到Master分支的命令：

```bash
　　# 切换到Master分支
　　git checkout master

　　# 对Develop分支进行合并
　　git merge --no-ff develop
```

这里稍微解释一下上一条命令的--no-ff参数是什么意思。

默认情况下，Git执行"快进式合并"（fast-farward merge），会直接将Master分支指向Develop分支。

使用--no-ff参数后，会执行正常合并，在Master分支上生成一个新节点。为了保证版本演进的清晰，我们希望采用这种做法。  

### 临时性分支

前面讲到版本库的两条主要分支：Master和Develop。前者用于正式发布，后者用于日常开发。其实，常设分支只需要这两条就够了，不需要其他了。

但是，除了常设分支以外，还有一些临时性分支，用于应对一些特定目的的版本开发。临时性分支主要有三种：

- 功能（feature）分支
- 预发布（release）分支
- 修补bug（fixbug）分支

这三种分支都属于临时性需要，使用完以后，应该删除，使得代码库的常设分支始终只有Master和Develop。

接下来，一个个来看这三种"临时性分支"。

**第一种是功能分支**，它是为了开发某种特定功能，从Develop分支上面分出来的。开发完成后，要再并入Develop。

功能分支的名字，可以采用feature-*的形式命名。

创建一个功能分支：

```bash
　　git checkout -b feature-x develop
```

开发完成后，将功能分支合并到develop分支：

```bash
　　git checkout develop

　　git merge --no-ff feature-x
```

删除feature分支：

```bash
　　git branch -d feature-x
```

**第二种是预发布分支**，它是指发布正式版本之前（即合并到Master分支之前），我们可能需要有一个预发布的版本进行测试。

预发布分支是从Develop分支上面分出来的，预发布结束以后，必须合并进Develop和Master分支。它的命名，可以采用release-*的形式。

创建一个预发布分支：

```bash
　　git checkout -b release-1.2 develop
```

确认没有问题后，合并到master分支：

```bash
　　git checkout master

　　git merge --no-ff release-1.2

　　# 对合并生成的新节点，做一个标签
　　git tag -a 1.2
```

再合并到develop分支：

```
　　git checkout develop

　　git merge --no-ff release-1.2
```

最后，删除预发布分支：

```bash
　　git branch -d release-1.2
```

**最后一种是修补bug分支**。软件正式发布以后，难免会出现bug。这时就需要创建一个分支，进行bug修补。

修补bug分支是从Master分支上面分出来的。修补结束以后，再合并进Master和Develop分支。它的命名，可以采用fixbug-*的形式。

创建一个修补bug分支：

```bash
　　git checkout -b fixbug-0.1 master
```

修补结束后，合并到master分支：

```bash
　　git checkout master

　　git merge --no-ff fixbug-0.1

　　git tag -a 0.1.1
```

再合并到develop分支：

```bash
　　git checkout develop

　　git merge --no-ff fixbug-0.1
```

最后，删除"修补bug分支"：

```bash
　　git branch -d fixbug-0.1
```

## Git实战

### 创建仓库

```bash
mkdir testgit
cd testgit
git init
ls -q
```

接下来，我们来新增一个文件 readme.txt，内容为“提交一个文件”，并将其提交到 Git 仓库。

第一步，使用 `git add` 命令将新增文件添加到暂存区。

第二步，使用 `git commit` 命令告诉 Git，把文件提交到仓库。

也可以在文件中新增一行内容“yyy”，再使用 `git status` 来查看结果。 

如果想查看文件到底哪里做了修改，可以使用 `git diff` 命令。

确认修改的内容后，可以使用 `git add` 和 `git commit` 再次提交。

### 版本回滚

再次对文件进行修改，追加一行内容为：“xxx”，并且提交到 Git 仓库。

 现在已经对 readme.txt 文件做了三次修改了。可以通过 `git log` 命令来查看历史记录。

如果想回滚的话，比如说回滚到上一个版本，可以执行以下两种命令：

1）`git reset --hard HEAD^`，上上个版本就是 `git reset --hard HEAD^^`，以此类推。

2）`git reset --hard HEAD~100`，如果回滚到前 100 个版本，用这个命令比上一个命令更方便。

那假如回滚错了，想恢复，不记得版本号了，可以先执行 `git reflog` 命令查看版本号。

然后再通过 `git reset --hard` 命令来恢复。

### 工作区和暂存区的区别

1）**工作区**，比如说前面提到的 testgit 目录就属于工作区，我们操作的 readme.txt 文件就放在这个里面。

2）**暂存区**，隐藏目录 .git 不属于工作区，它（Git 仓库）里面存了很多东西，其中最重要的就是暂存区。

Git 在提交文件的时候分两步，第一步 `git add` 命令是把文件添加到暂存区，第二步 `git commit` 才会把暂存区的所有内容提交到 Git 仓库中。

“**为什么要先 add 才能 commit 呢？**”

最直接的原因就是Linus 搞了这个“暂存区”的概念。那为什么要搞这个概念呢？没有暂存区不行吗？

嗯，要回答这个问题，我们就需要追本溯源了。

在 Git 之前， SVN 是代码版本管理系统的集大成者。SVN 比之前的 CVS 更优秀的一点是，每次的提交可以由多个文件组成，并且这次提交是原子性的，要么全部成功，要么全部失败。

原子性带来的好处是显而易见的，这使得我们可以把项目整体还原到某个时间点，就这一点，SVN 就完虐 CVS 这些代码版本管理系统了。

Git 作为逼格最高的代码版本管理系统，自然要借鉴 SVN 这个优良特性的。但不同于 SVN 的是，Git 一开始搞的都是命令行，没有图形化界面，如果想要像 SVN 那样一次性选择多个文件或者不选某些文件，还真的是个麻烦事。

对于像 Linus 这种天才级选手来说，图形化界面无疑是 low 逼，可命令行在这种情况下又实在是麻烦~

嗯，怎么办呢？

神之所以为神，就是他能在遇到问题的时候想到完美的解决方案——搞个**暂存区**不就完事了？

暂存区可以随意地将各种文件的修改放进去，只需要通过 `git add` 这种简单的命令就可以精心地挑选要提交哪些文件了，然后再一次性（原子性）的 `git commit` 到版本库，所有的问题都迎刃而解嘛。

### 撤销修改

现在，我在 readyou.txt 文件中追加了一行内容：“不小心写的~~~”。在我想要提交的时候，突然发现追加的内容有误，我得恢复到以前的版本，该怎么办呢？

1）我知道要修改的内容，直接修改，然后 add 和 commit 覆盖。

2）我忘记要修改哪些内容了，通过 `git reset -- hard HEAD` 恢复到上一个版本。

还有其他办法吗？

答案当然是有了，其实在我们执行 `git status` 命令查看 Git 状态的时候，结果就提示我们可以使用 `git restore` 命令来撤销这次操作的。

其实在 git version 2.23.0 版本之前，是可以通过 `git checkout` 命令来完成撤销操作的。

checkout 可以创建分支、导出分支、切换分支、从暂存区删除文件等等，一个命令有太多功能就容易让人产生混淆。2.23.0 版本改变了这种混乱局面，git switch 和 git restore 两个新的命令应运而生。

**switch 专注于分支的切换，restore 专注于撤销修改。**

