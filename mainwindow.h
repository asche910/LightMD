#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPlainTextEdit>
#include<highlighter.h>
#include <QtWidgets>
#include "previewpage.h"
#include <QWebEngineView>
#include "document.h"


QT_BEGIN_NAMESPACE
class QPaintEvent;
class QResizeEvent;
class QSize;
class QWidget;
QT_END_NAMESPACE

class LineNumberArea;
class CodeEditor;
class Preview;

class Home: public QMainWindow
{
    Q_OBJECT

public:
    Home();


    void undo();
    void redo();
    void copy();
    void paste();

    QMenu *menuView;

private slots:
    void updateLengthAndLine();
    void saveFile();
    void newFile();
    void quit();
    void handleMenuView();
    void about();
    void checkUpdate();

private:
    CodeEditor *codeEditor;
    Highlighter *highlighter;
    Preview *preview;
    Document m_content;
    void createMenu();
    void openFileSlot();

    // 状态栏文本
    QLabel *label;
    QLabel *textType;
    QLabel *codeLength;
    QLabel *codeLines;

};

class CodeEditor : public QPlainTextEdit
{
    Q_OBJECT

public:
    CodeEditor(Home *parent = 0);
    void init();
    void lineNumberAreaPaintEvent(QPaintEvent *event);
    int lineNumberAreaWidth();
    int getFirstVisibleBlockId();

    QWidget *lineNumberArea;
    QString fileName;
    bool isChanged;

protected:
    void resizeEvent(QResizeEvent *event) override;

public slots:
    void updateLineNumberAreaWidth(int newBolckCount);
    void hightlightCurrentLine();
    void updateLineNumberArea(const QRect &, int);
    void hideLineNumber();

private:
    Home *home;

};


class LineNumberArea : public QWidget
{
public:
    LineNumberArea(CodeEditor *mainWindow) : QWidget(mainWindow){
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
    CodeEditor *mainWindow;
};


class Preview : public QWebEngineView
{
public:
    Preview(Home *parent);

private:
    Home *home;
};

#endif // MAINWINDOW_H
