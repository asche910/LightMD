# LightMd 

> 很早之前就有了写一个类似Windows记事本的想法，加上最近也刚好在学编译原理，所以就想把两者结合起来，于是就打算结合MarkDown，开发一款MarkDown编辑器。
> 不过由于我之前一直使用的是Java语言居多，对c++并不熟悉，所以一些糟糕的代码风格和规范还望各位大佬谅解！


[__LightMD__](https://github.com/asche910/LightMD) 即一款基于QT实现的markdown编辑器，当然也可以作为代码编辑器，由于时间与个人能力等原因，目前实现的功能非常有限！

主要包括：

* 支持语法高亮（目前支持C/C++）

* 支持MarkDown预览

* 代码行数、文本信息统计等

* 其它一些基本的文件处理相关功能


好了直接上图吧

![image](https://img2018.cnblogs.com/blog/1470456/201905/1470456-20190508205235739-283039740.png)


![image](https://img2018.cnblogs.com/blog/1470456/201905/1470456-20190508205249824-1786194754.png)



本项目主要目的在于学习qt相关的windows开发，其中主要有以下几个模块：
* 整体布局
* 代码编辑器
* markdown编辑器

## 整体布局

最外面当然是_QMainWindow_， 然后从上到下依次是
* menuBar
* QSplitter
* statusBar

menubar的简单示例如下：
``` c++
    QMenu *menuFile = menuBar()->addMenu(tr("&File"));
    QAction *itemNew = new QAction(tr("&New"), this);
    itemNew->setStatusTip(tr("Create a new file"));
    connect(itemNew, &QAction::triggered, this, &Home::newFile);
    menuFile->addAction(itemNew);
   
``` 
其中QMenu就是最外面显示的menu，即鼠标不点击就可以看见的那个menu；QAction则是QMenu上众多选项之一；然后是调用connect函数为QAction设置点击事件。

中间主体则是QSplitter， 
``` c++

    QSplitter *centralSplitter = new QSplitter(this);

    setCentralWidget(centralSplitter);

    centralSplitter->addWidget(codeEditor);
    centralSplitter->addWidget(preview);

``` 
首先new一个QSplitter，然后将其设置为中间组件，然后在QSplitter上再添加两个组件，分别为代码编辑区域和markdown预览区域的组件。
这两个区域在文章下面将有具体讲解。

底部则是statusBar：
``` c++
    label = new QLabel("LightMD is ready!");
    textType = new QLabel("Plain Text");
    codeLength = new QLabel("Length:652");
    codeLines = new QLabel("Lines:54");

    statusBar()->addWidget(label, 1);
    statusBar()->addPermanentWidget(textType);
    statusBar()->addPermanentWidget(codeLength);
    statusBar()->addPermanentWidget(codeLines);
``` 
label用来显示正常的提示消息；textType用来显示当前的文本类型，如markdown或c++等；codeLength和codeLines就不用过多解释了吧。

## 代码编辑器

其中代码编辑框我纠结了半天，用QPlainTextEdit好呢，还是QTextEdit好？？？其中StackOverflow上一高赞回答如下：

> QPlainTextEdit is an advanced viewer/editor supporting plain text. It is optimized to handle large documents and to respond quickly to user input.
>
> QPlainText uses very much the same technology and concepts as  QTextEdit, but is optimized for plain text handling.
> 
> QPlainTextEdit works on paragraphs and characters. A paragraph is a formatted string which is word-wrapped to fit into the width of the widget. By default when reading plain text, > one newline signifies a paragraph. A document consists of zero or more paragraphs. Paragraphs are separated by hard line breaks. Each character within a paragraph has its 
> own attributes, for example, font and color.

简单点说，就是QPlainTextEdit对普通文本的支持度特别高，也就是很方便，不过一些复杂功能却不能实现；而QTextEdit是一个更加重量级的组件，支持各种复杂功能，不过一些简单的功能可能没有QPlainTextEdit使用的那么方便。

两者我都简单试用后，发现还是QPlainTextEdit用着比较方便，于是就决定采用QPlainTextEdit了。

然后关于代码框和代码行数的实现，QT的官方demo里面好像有现成的（不得不说，qt的demo是真的多！）。

![image](https://img2018.cnblogs.com/blog/1470456/201905/1470456-20190508223249653-563185469.jpg)

所以，这里的实现我就不解释啥了。

## markdown编辑器

这里官方也有个markdown的demo，下载就行了。不过要注意的是，这里由于用到了web引擎，所以这里必须使用vs来编译运行，安装vs环境这里不懂的还是自行百度吧。

官方demo中好像实现的都挺全的，我只是做了个小修改，然后就转移到LightMD来了。
其中markdown预览流程是先将markdown内容转换为对应的html内容，然后web引擎来显示HTML页面。
其中转换官方也全部为我们做好了。
![image](https://img2018.cnblogs.com/blog/1470456/201905/1470456-20190508224007661-1374021705.jpg)
由于时间关系，当然是直接套用了。不过以后有空的话，自己再去实现一下吧。

最后，LightMd项目地址：[LightMD](https://github.com/asche910/LightMD)