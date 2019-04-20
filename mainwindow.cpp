#include <QVBoxLayout>
#include <QPushButton>
#include <QtWidgets>
#include "mainwindow.h"

Home::Home(): codeEditor(new MainWindow(this)){
//    QWidget *centralWindow = new QWidget;
//    setCentralWidget(centralWindow);


//    QDockWidget *dockEdit = new QDockWidget("CodeEditor");
//    addDockWidget(Qt::TopDockWidgetArea, dockEdit);


//    MainWindow w;
//    w.show();

    resize(QSize(800, 600));

    QSplitter *centralSplitter = new QSplitter(this);
    setCentralWidget(centralSplitter);

    centralSplitter->addWidget(codeEditor);


    highlighter = new Highlighter(codeEditor->document());

    createMenu();


    QString message = tr("A context menu is available by right-clicking");
    statusBar()->showMessage(message);
    QLabel *label = new QLabel("Hello, World!");
    label->setAlignment(Qt::AlignRight);
    statusBar()->addWidget(label, 1);
//    dockEdit->addAction(w);

//    centralWindow->setLayout(w);

}


MainWindow::MainWindow(QWidget *parent)
    : QPlainTextEdit (parent)
{

    lineNumberArea = new LineNumberArea(this);

    //  添加两个按钮
//    QPushButton *okBtn  = new QPushButton;
//    okBtn ->setText(tr("我在上面, 我最牛"));
//    QPushButton *celBtn = new QPushButton;
//    celBtn->setText(tr("我在下面, 我不服"));

//    //  创建一个垂直箱式布局, 将两个按钮扔进去
//    QVBoxLayout *layout = new QVBoxLayout;
//    layout->addWidget(okBtn);
//    layout->addWidget(celBtn);

//      设置界面的布局为垂直箱式布局
//    setCentralWidget(okBtn);


    connect(this, SIGNAL(blockCountChanged(int)), this, SLOT(updateLineNumberAreaWidth(int)));
    connect(this, SIGNAL(updateRequest(QRect, int)), this, SLOT(updateLineNumberArea(QRect, int)));
    connect(this, SIGNAL(cursorPositionChanged()), this, SLOT(hightlightCurrentLine()));


    updateLineNumberAreaWidth(0);
    hightlightCurrentLine();

    QPalette palette = this->palette();
    palette.setColor(QPalette::Active, QPalette::Base, QColor("#333"));
    palette.setColor(QPalette::Inactive, QPalette::Base, QColor("#333"));
    palette.setColor(QPalette::Text, Qt::green);

    setPalette(palette);

    QFont editFont;
    editFont.setFamily("Courier");
    editFont.setStyleHint(QFont::Monospace);
    editFont.setFixedPitch(true);
    editFont.setPointSize(13);

    setFont(editFont);
    QFontMetrics metrics(editFont);
    setTabStopWidth(4 * metrics.width(' '));


//    zoomOut(-2);


}

int MainWindow::lineNumberAreaWidth()
{
    int digits = 1;
    int max = qMax(1, blockCount());
    while(max >= 10){
        max /= 10;
        ++digits;
    }

    int space = 3 + fontMetrics().horizontalAdvance(QLatin1Char('9')) * digits;
    return space;
}

void MainWindow::updateLineNumberAreaWidth(int)
{
    setViewportMargins(lineNumberAreaWidth(), 0, 0, 0);
}

void MainWindow::updateLineNumberArea(const QRect &rect, int dy)
{
    if(dy)
        lineNumberArea->scroll(0, dy);
    else
        lineNumberArea->update(0, rect.y(), lineNumberArea->width(), rect.height());

    if(rect.contains(viewport()->rect()))
        updateLineNumberAreaWidth(0);
}

void MainWindow::resizeEvent(QResizeEvent *e)
{
    QPlainTextEdit::resizeEvent(e);

    QRect cr = contentsRect();
    lineNumberArea->setGeometry(QRect(cr.left(), cr.top(), lineNumberAreaWidth(), cr.height()));
}

void MainWindow::hightlightCurrentLine()
{
    QList<QTextEdit::ExtraSelection> extraSelects;

    if(!isReadOnly())
    {
        QTextEdit::ExtraSelection selection;
        QColor color = QColor("#666");
        selection.format.setBackground(color);
        selection.format.setProperty(QTextFormat::FullWidthSelection, true);
        selection.cursor = textCursor();
//        selection.cursor.clearSelection();
        extraSelects.append(selection);
    }
    setExtraSelections(extraSelects);

//    printf("Hightlight invoked!\n");
//    fflush(stdout);
}

void MainWindow::lineNumberAreaPaintEvent(QPaintEvent *event)
{
    QPainter painter(lineNumberArea);
    painter.fillRect(event->rect(), QColor("#404244"));


    QTextBlock block = firstVisibleBlock();
    int blockNum = block.blockNumber();
    int top = viewport()->geometry().top();
    int bottom = top + (int) blockBoundingRect(block).height();

    while (block.isValid() && top <= event->rect().bottom()) {
        if (block.isVisible() && bottom >= event->rect().top()) {
            QString number = QString::number(blockNum + 1);
            painter.setPen(QColor("#ddd"));
            painter.drawText(0, top, lineNumberArea->width(), fontMetrics().height(),
                             Qt::AlignRight, number);

        }

        block = block.next();
        top = bottom;
        bottom = top + (int) blockBoundingRect(block).height();
        ++blockNum;
    }
}


void Home::createMenu()
{
    QMenu *menuFile = menuBar()->addMenu(tr("&File"));

    QAction *itemNew = new QAction(tr("&New"), this);
    itemNew->setStatusTip(tr("Create a new file"));
    QAction *itemOpen = new QAction(tr("&Open"), this);
    itemOpen->setShortcut(QKeySequence::Save);
    itemOpen->setStatusTip(tr("Open an existing file"));
    connect(itemOpen, &QAction::triggered, this, &Home::openFileSlot);
    QAction *itemSave = new QAction(tr("&Save"), this);
    itemSave->setStatusTip(tr("Save changes"));
    QAction *itemSetting = new QAction(tr("&Setting"), this);
    itemSetting->setStatusTip(tr("Open setting"));
    QAction *itemExit = new QAction(tr("&Exit"), this);
    itemExit->setStatusTip(tr("Exit"));
    menuFile->addAction(itemNew);
    menuFile->addAction(itemOpen);
    menuFile->addAction(itemSave);
    menuFile->addAction(itemSetting);
    menuFile->addAction(itemExit);


    QMenu *menuEdit = menuBar()->addMenu(tr("&Edit"));
    QAction *itemUndo = new QAction(tr("&Undo"), this);
    itemUndo->setStatusTip(tr("Undo change"));
    QAction *itemRedo = new QAction(tr("&Redo"), this);
    itemRedo->setStatusTip(tr("Redo change"));
    QAction *itemCopy = new QAction(tr("&Copy"), this);
    itemCopy->setStatusTip(tr("Copy selection"));
    QAction *itemPaste = new QAction(tr("&Paste"), this);
    itemPaste->setStatusTip(tr("Paste"));

    menuEdit->addAction(itemUndo);
    menuEdit->addAction(itemRedo);
    menuEdit->addAction(itemCopy);
    menuEdit->addAction(itemPaste);

    QMenu *menuView = menuBar()->addMenu(tr("&View"));
    QAction *itemToolbar = new QAction(tr("&Toolbar"), this);
    itemToolbar->setStatusTip(tr("Show toolbar"));
    QAction *itemLineNum = new QAction(tr("&LineNumber"), this);
    itemLineNum->setStatusTip(tr("Show LineNumber"));

    menuView->addAction(itemToolbar);
    menuView->addAction(itemLineNum);

    QMenu *menuHelp = menuBar()->addMenu(tr("&Help"));
    QAction *itemAbout = new QAction(tr("&About"), this);
    itemAbout->setStatusTip(tr("About"));
    QAction *itemUpdate = new QAction(tr("&Check for updates"), this);
    itemUpdate->setStatusTip(tr("Check for updates"));

    menuHelp->addAction(itemAbout);
    menuHelp->addAction(itemUpdate);

}



void Home::openFileSlot()
{
    QFileDialog dlg(this);
    dlg.setDirectory(QDir::currentPath());//设置默认目录
    dlg.setAcceptMode(QFileDialog::AcceptOpen);//允许打开文件
    dlg.setFileMode(QFileDialog::ExistingFile );//选择单个文件
//    dlg.setFilter("Image(*.jpg *.xmp *.bmp);;Text(*.txt)");

    if(dlg.exec() == QFileDialog::Accepted)
    {
       QStringList fs = dlg.selectedFiles();

       for(int i = 0; i < fs.count(); i++)
       {
           qDebug() << fs[i];
           QFile file(fs[i]);
           if(!file.open(QFile::ReadOnly | QFile::Text)) break;
           QTextStream stream(&file);
           QTextCodec *codec = QTextCodec::codecForName("utf-8");
           stream.setCodec(codec);
           QString content(stream.readAll());

//           qDebug() << file.size() << content;
           codeEditor->setPlainText(content);
       }
    }
}

