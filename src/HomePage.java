import helper.VisibleCaretListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;

import static javax.swing.JOptionPane.showMessageDialog;
import static util.FileUtils.getContent;
import static util.PrintUtils.println;

@SuppressWarnings("ALL")
public class HomePage extends JFrame {

    private JTextPane textPane;

    public HomePage() throws HeadlessException {

        UIManager.put("TextPane.inactiveBackground", Color.black);

        try {

//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");//Motif风格，是蓝黑

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        initMenu();
        initContent();


        textPane.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                println(e.getButton());
                if (e.getButton() == MouseEvent.BUTTON3) {
                    initPopMenu(e.getX(), e.getY());
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);

    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // 文件(新建、打开、保存、设置、退出)、修改、视图、关于
        JMenu menuFile = new JMenu("File");
        JMenuItem itemNew = new JMenuItem("New");
        JMenuItem itemOpen = new JMenuItem("Open", new ImageIcon("images/ic_file.png"));
        JMenuItem itemSave = new JMenuItem("Save");
        JMenuItem itemSetting = new JMenuItem("Setting", new ImageIcon("images/ic_setting.png"));
        JMenuItem itemExit = new JMenuItem("Exit");
        menuFile.add(itemNew);
        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemSetting);
        menuFile.add(itemExit);

        JMenu menuEdit = new JMenu("Edit");
        JMenuItem itemUndo = new JMenuItem("Undo");
        JMenuItem itemRedo = new JMenuItem("Redo");
        JMenuItem itemCopy = new JMenuItem("Copy");
        JMenuItem itemPaste = new JMenuItem("Paste");
        menuEdit.add(itemUndo);
        menuEdit.add(itemRedo);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);

        JMenu menuView = new JMenu("View");
        JMenuItem itemToolbar = new JCheckBoxMenuItem("Toolbar");
        JMenuItem itemLineNum = new JCheckBoxMenuItem("LineNumber");
        menuView.add(itemToolbar);
        menuView.add(itemLineNum);

        JMenu menuAbout = new JMenu("Help");
        JMenuItem itemAbout = new JMenuItem("About");
        JMenuItem itemUpdate = new JMenuItem("Check for updates");
        menuAbout.add(itemAbout);
        menuAbout.add(itemUpdate);


        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuView);
        menuBar.add(menuAbout);

        ActionListener menuFileListener = e -> {
            println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "New":
                    showMessageDialog(this, "New");
                    break;
                case "Open":
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.showDialog(this, "确定");
                    jFileChooser.setVisible(true);

                    File file = jFileChooser.getSelectedFile();
                    if (file != null) {
                        showMessageDialog(this, "You select " + file.getName());
                    }
                    break;
                case "Save":
                    showMessageDialog(this, "Save");
                    break;
                case "Setting":
                    showMessageDialog(this, "Setting");
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        };

        ActionListener menuEditListener = e -> {
            showMessageDialog(this, e.getActionCommand());
            switch (e.getActionCommand()) {
                case "Undo":
                    break;
                case "Redo":
                    break;
                case "Copy":
                    break;
                case "Paste":
                    break;
            }
        };

        ActionListener menuViewListener = e -> {
            showMessageDialog(this, e.getActionCommand());
            switch (e.getActionCommand()) {
                case "Toolbar":
                    break;
                case "LineNumber":
                    break;
            }
        };

        ActionListener menuHelpListener = e -> {
            showMessageDialog(this, e.getActionCommand());
            switch (e.getActionCommand()) {
                case "About":
                    break;
                case "Check for updates":
                    break;
            }
        };


        itemNew.addActionListener(menuFileListener);
        itemOpen.addActionListener(menuFileListener);
        itemSave.addActionListener(menuFileListener);
        itemSetting.addActionListener(menuFileListener);
        itemExit.addActionListener(menuFileListener);

        itemUndo.addActionListener(menuEditListener);
        itemRedo.addActionListener(menuEditListener);
        itemCopy.addActionListener(menuEditListener);
        itemPaste.addActionListener(menuEditListener);

        itemToolbar.addActionListener(menuViewListener);
        itemLineNum.addActionListener(menuViewListener);

        itemAbout.addActionListener(menuHelpListener);
        itemUpdate.addActionListener(menuHelpListener);
    }

    private void initContent() {
        JPanel noWrapPanel = new JPanel(new BorderLayout());

        textPane = new JTextPane(){
      /*      @Override
            public boolean getScrollableTracksViewportWidth() {
                // Only track viewport width when the viewport is wider than the preferred width
                return getUI().getPreferredSize(this).width <= getParent().getSize().width;
            };

            @Override
            public Dimension getPreferredSize() {
                // Avoid substituting the minimum width for the preferred width when the viewport is too narrow
                return getUI().getPreferredSize(this);
            };*/
        };

        noWrapPanel.add(textPane);

        JScrollPane jScrollPane = new JScrollPane(noWrapPanel);

        add(jScrollPane);
        textPane.addCaretListener(new VisibleCaretListener());

        // textPane.setBackground(Color.black); // doesn't work
        textPane.setForeground(new Color(242, 242, 242));
        textPane.setFont(new Font("斜体", Font.ITALIC, 16));

        textPane.setContentType("text/html");
        // 直接setText会导致空格换行被清除
//        textPane.setText("<html><head></head><body style=\"background-color: #444444;\"><p>Hello world</p></body></html>");
        try {
            textPane.getStyledDocument().insertString(0, getContent(new File("as_.txt")), null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        SimpleAttributeSet bgAttr = new SimpleAttributeSet();
        StyleConstants.setBackground(bgAttr, new Color(61, 61, 61));
        textPane.getStyledDocument().setParagraphAttributes(0, textPane.getDocument().getLength(), bgAttr, false);


        textPane.setBorder(BorderFactory.createEmptyBorder());
        // textPane.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));

/*

        ActionListener colorActionListener = new StyledEditorKit.ForegroundAction("set-foreground-", Color.BLACK);
        colorActionListener.actionPerformed(new ActionEvent(textPane, 0, ""));

        Style def = textPane.getStyledDocument().addStyle(null, null);
        StyleConstants.setFontFamily(def, "verdana");
        StyleConstants.setFontSize(def, 12);

        Style normal = textPane.addStyle("normal", def);
        Style s = textPane.addStyle("grayLight", normal);
        StyleConstants.setForeground(s, Color.RED);
        textPane.setParagraphAttributes(normal, true);
*/

        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBackground(attributeSet, new Color(90, 86, 90));
        StyleConstants.setForeground(attributeSet, new Color(34, 253, 24));
        StyleConstants.setFontSize(attributeSet, 14);
        for (int i = 0; i < 100; i++) {
            try {
//                textPane.getDocument().insertString(textPane.getDocument().getLength(), "Hello, World!", textPane.getStyle(i % 2 == 0 ? "grayLight" : "normal"));
                textPane.getDocument().insertString(textPane.getDocument().getLength(),
                        "Hello, World!      ", attributeSet);
            } catch (Exception e1) {
            }
        }


        StyledDocument styledDocument = textPane.getStyledDocument();
        String content = null;
        try {
            content = styledDocument.getText(0, styledDocument.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        println(content);


/*
        String htmlStyle = "background-color: #444444";
        SimpleAttributeSet attr = new SimpleAttributeSet();
        attr.addAttribute(HTML.Attribute.STYLE, htmlStyle);
        MutableAttributeSet outerAttr = new SimpleAttributeSet();
        outerAttr.addAttribute(HTML.Tag.SPAN, attr);
        //Next line is just an instruction to editor to change color
        StyleConstants.setBackground(outerAttr, Color.RED);
        textPane.setCharacterAttributes(outerAttr, false);
*/


        // ((StyledDocument) textPane.getDocument()).setCharacterAttributes(0, textPane.getDocument().getLength(), outerAttr, false);


//        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), attributeSet, true);

    }

    private void initPopMenu(int x, int y){
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemCopy = new JMenuItem( "Copy         Ctrl + C");
        JMenuItem itemPaste = new JMenuItem("Paste        Ctrl + V");
        JMenuItem itemCut = new JMenuItem(  "Cut          Ctrl + X");


        popupMenu.add(itemCopy);
        popupMenu.add(itemPaste);
        popupMenu.add(itemCut);
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        popupMenu.show(this, x, y);
    }


}
