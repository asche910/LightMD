#include <QVBoxLayout>
#include <QPushButton>
#include <QtWidgets>
#include "mainwindow.h"


MainWindow::MainWindow(QWidget *parent)
    : QPlainTextEdit (parent)
{

    resize(QSize(800, 600));
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
//    palette.setColor(QPalette::Inactive, QPalette::Base, Qt::gray);
    palette.setColor(QPalette::Text, Qt::green);

    setPalette(palette);

    zoomOut(-2);

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
    painter.fillRect(event->rect(), Qt::lightGray);

    QTextBlock block = firstVisibleBlock();
    int blockNum = block.blockNumber();
    int top = (int) blockBoundingGeometry(block).translated(contentOffset()).top();
    int bottom = top + (int) blockBoundingRect(block).height();

    while (block.isValid() && top <= event->rect().bottom()) {
        if (block.isVisible() && bottom >= event->rect().top()) {
            QString number = QString::number(blockNum + 1);
            painter.setPen(Qt::black);
            painter.drawText(0, top, lineNumberArea->width(), fontMetrics().height(),
                             Qt::AlignRight, number);

        }

        block = block.next();
        top = bottom;
        bottom = top + (int) blockBoundingRect(block).height();
        ++blockNum;
    }
}

