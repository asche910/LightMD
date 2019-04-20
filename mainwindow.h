#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTextEdit>
#include <QPlainTextEdit>
#include<highlighter.h>

QT_BEGIN_NAMESPACE
class QPaintEvent;
class QResizeEvent;
class QSize;
class QWidget;
QT_END_NAMESPACE

class LineNumberArea;
class MainWindow;

class Home: public QMainWindow
{
    Q_OBJECT

public:
    Home();

private:
    MainWindow *codeEditor;
    Highlighter *highlighter;
    void createMenu();
    void openFileSlot();
};

class MainWindow : public QPlainTextEdit
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = 0);
    void hello();
    void lineNumberAreaPaintEvent(QPaintEvent *event);
    int lineNumberAreaWidth();
    int getFirstVisibleBlockId();

protected:
    void resizeEvent(QResizeEvent *event) override;

private slots:
    void updateLineNumberAreaWidth(int newBolckCount);
    void hightlightCurrentLine();
    void updateLineNumberArea(const QRect &, int);

private:
    QWidget *lineNumberArea;
};


class LineNumberArea : public QWidget
{
public:
    LineNumberArea(MainWindow *mainWindow) : QWidget(mainWindow){
        this->mainWindow = mainWindow;
    }

    QSize sizeHint() const override {
        return QSize(mainWindow-> lineNumberAreaWidth(), 0);
    }

protected:
    void paintEvent(QPaintEvent *event) override {
        mainWindow->lineNumberAreaPaintEvent(event);
    }

private:
    MainWindow *mainWindow;
};


#endif // MAINWINDOW_H
