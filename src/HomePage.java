import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import static javax.swing.JComponent.setDefaultLocale;
import static javax.swing.JOptionPane.showMessageDialog;
import static util.FileUtils.getContent;
import static util.PrintUtils.println;

@SuppressWarnings("ALL")
public class HomePage extends JFrame {

    public HomePage() throws HeadlessException {

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setSize(600, 600);
        setVisible(true);

    }

    private void initMenu() {
        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);

        // 文件(新建、打开、保存、设置、退出)、修改、视图、关于
        Menu menuFile = new Menu("File");
        MenuItem itemNew = new MenuItem("New");
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemSave = new MenuItem("Save");
        MenuItem itemSetting = new MenuItem("Setting");
        MenuItem itemExit = new MenuItem("Exit");
        menuFile.add(itemNew);
        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemSetting);
        menuFile.add(itemExit);

        Menu menuEdit = new Menu("Edit");
        MenuItem itemUndo = new MenuItem("Undo");
        MenuItem itemRedo = new MenuItem("Redo");
        MenuItem itemCopy = new MenuItem("Copy");
        MenuItem itemPaste = new MenuItem("Paste");
        menuEdit.add(itemUndo);
        menuEdit.add(itemRedo);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);

        Menu menuView = new Menu("View");
        MenuItem itemToolbar = new CheckboxMenuItem("Toolbar");
        MenuItem itemLineNum = new CheckboxMenuItem("LineNumber");
        menuView.add(itemToolbar);
        menuView.add(itemLineNum);

        Menu menuAbout = new Menu("Help");
        MenuItem itemAbout = new MenuItem("About");
        MenuItem itemUpdate = new MenuItem("Check for updates");
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

                    File file  = jFileChooser.getSelectedFile();
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
            switch (e.getActionCommand()){
                case "Toolbar":
                    break;
                case "LineNumber":
                    break;
            }
        };

        ActionListener menuHelpListener = e -> {
            showMessageDialog(this, e.getActionCommand());
            switch (e.getActionCommand()){
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

    private void initContent(){
        JScrollPane jScrollPane = new JScrollPane();
        JTextPane textPane = new JTextPane();
        textPane.setBackground(Color.darkGray);

        textPane.setForeground(Color.lightGray);


        textPane.setText(getContent(new File("as_.txt")));

        jScrollPane.setViewportView(textPane);
        add(jScrollPane);
    }

}
