package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CreatePdmPartUI extends JDialog {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -279332465989743454L;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JScrollPane centerScrollPane;
    private GridBagLayout centerLayout;
    private GridBagConstraints centerConstraints;
    private ArrayList<JComboBox> flList;

    public CreatePdmPartUI(JFrame parent) {
        super(parent, true);
        initData();
        initUI();

    }

    private void initUI() {
        BorderLayout mainLayout = new BorderLayout();
        getContentPane().setLayout(mainLayout);
        northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(750, 20));
        centerPanel = new JPanel();
        centerScrollPane = new JScrollPane();
        centerScrollPane.setViewportView(centerPanel);
        northPanel.setPreferredSize(new Dimension(750, 400));
        southPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(750, 100));

        /**
         * 上部部分内容
         */
        {
            GridLayout northLayout = new GridLayout(3, 2);
            northPanel.setLayout(northLayout);
            JLabel cpL = new JLabel("产品：");
            JTextField cpTF = new JTextField("测试产品");
            JLabel lxL = new JLabel("*类型：");
            JComboBox lxCB = new JComboBox(new String[] { "--请选择一个类型",
                    "Slider", "Spinner", "Widget", "部件" });
            JLabel isOrNotCpL = new JLabel("*创建为产品：");
            JComboBox isOrNotCpCB = new JComboBox(new String[] { "", "是", "否" });

            northPanel.add(cpL);
            northPanel.add(cpTF);
            northPanel.add(lxL);
            northPanel.add(lxCB);
            northPanel.add(isOrNotCpL);
            northPanel.add(isOrNotCpCB);
        }

        /**
         * 中部部分
         */
        {
            centerPanel.setBorder(BorderFactory.createTitledBorder("部件属性"));
            centerLayout = new GridBagLayout();
            centerPanel.setLayout(centerLayout);

            centerConstraints = new GridBagConstraints();
            centerConstraints.fill = GridBagConstraints.BOTH;
            // centerConstraints.anchor = GridBagConstraints.WEST;
            // centerConstraints.weightx=0;
            // centerConstraints.weighty=0;

            {
                JLabel bhL = new JLabel("*编号:");
                JTextField bhTF = new JTextField();
                centerPanel.add(bhL);
                centerPanel.add(bhTF);
                add(centerLayout, bhL, centerConstraints, 0, 0, 1);
                add(centerLayout, bhTF, centerConstraints, 1, 0, 0);
            }

            {
                JLabel mcL = new JLabel("*名称:");
                JTextField mcTF = new JTextField();
                centerPanel.add(mcL);
                centerPanel.add(mcTF);
                add(centerLayout, mcL, centerConstraints, 0, 0, 1);
                add(centerLayout, mcTF, centerConstraints, 1, 0, 0);
            }

            {

                JLabel zpmsL = new JLabel("*装配模式：");
                JComboBox zpmsCB = new JComboBox(new String[] { "", "可分",
                        "不可分", "组件" });
                centerPanel.add(zpmsL);
                centerPanel.add(zpmsCB);
                add(centerLayout, zpmsL, centerConstraints, 0, 0, 1);
                add(centerLayout, zpmsCB, centerConstraints, 1, 0, 0);
            }

            {

                JLabel yL = new JLabel("*源：");
                JComboBox yCB = new JComboBox(new String[] { "", "制造", "购买",
                        "购买-单一供应源" });
                centerPanel.add(yL);
                centerPanel.add(yCB);
                add(centerLayout, yL, centerConstraints, 0, 0, 1);
                add(centerLayout, yCB, centerConstraints, 1, 0, 0);
            }

            {

                JLabel mrzzdmL = new JLabel("*默认追踪代码：");
                JComboBox mrzzdmCB = new JComboBox(new String[] { "", "批号",
                        "批号/序列号", "序列号", "未追踪" });
                centerPanel.add(mrzzdmL);
                centerPanel.add(mrzzdmCB);
                add(centerLayout, mrzzdmL, centerConstraints, 0, 0, 1);
                add(centerLayout, mrzzdmCB, centerConstraints, 1, 0, 0);
            }

            {

                JLabel mrdwL = new JLabel("*默认单位：");
                JComboBox mrdwCB = new JComboBox(new String[] { "", "每个",
                        "根据需要", "千克", "米", "升", "平方米", "立方米" });
                centerPanel.add(mrdwL);
                centerPanel.add(mrdwCB);
                add(centerLayout, mrdwL, centerConstraints, 0, 0, 1);
                add(centerLayout, mrdwCB, centerConstraints, 1, 0, 0);
            }

            {

                JLabel sjbjL = new JLabel("*收集部件：");
                JComboBox sjbjCB = new JComboBox(new String[] { "", "是", "否" });
                centerPanel.add(sjbjL);
                centerPanel.add(sjbjCB);
                add(centerLayout, sjbjL, centerConstraints, 0, 0, 1);
                add(centerLayout, sjbjCB, centerConstraints, 1, 0, 0);
            }

            {

                JLabel smzqmbL = new JLabel("*生命周期模版：");
                JTextField smzqmbTF = new JTextField("(已生成)");
                centerPanel.add(smzqmbL);
                centerPanel.add(smzqmbTF);
                add(centerLayout, smzqmbL, centerConstraints, 0, 0, 1);
                add(centerLayout, smzqmbTF, centerConstraints, 1, 0, 0);
            }

            {

                JLabel wzL = new JLabel("*位置：");
                JRadioButton zdxzwjjCB = new JRadioButton("自动选择文件夹");
                JTextField zdxzwjjTF = new JTextField("/测试产品");
                JLabel tempL = new JLabel();
                JRadioButton xzwjjCB = new JRadioButton("选择文件夹");
                JTextField xzwjjTF = new JTextField("");
                JTree wjjTree = new JTree();
                centerPanel.add(wzL);

                centerPanel.add(zdxzwjjCB);
                centerPanel.add(zdxzwjjTF);
                centerPanel.add(tempL);
                centerPanel.add(xzwjjCB);
                centerPanel.add(xzwjjTF);
                centerPanel.add(wjjTree);
                add(centerLayout, wzL, centerConstraints, 0, 0, 1);
                add(centerLayout, zdxzwjjCB, centerConstraints, 0, 0, 1);
                add(centerLayout, zdxzwjjTF, centerConstraints, 1, 0, 0);

                add(centerLayout, tempL, centerConstraints, 0, 0, 1);
                add(centerLayout, xzwjjCB, centerConstraints, 0, 0, 1);
                add(centerLayout, xzwjjTF, centerConstraints, 1, 0, 1);
                add(centerLayout, wjjTree, centerConstraints, 0, 0, 0);
            }

            {

                JLabel flL = new JLabel("*分类：");
                JComboBox flCB = new JComboBox(new String[] { "", "B", "L",
                        "S", "W", "C", "F", "T" });
                JButton flB = new JButton("+");
                flB.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        final JLabel flL = new JLabel("*分类：");
                        final JComboBox flCB = new JComboBox(new String[] { "", "B", "L",
                                "S", "W", "C", "F", "T" });
                        final JButton flB = new JButton("-");
                        flB.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                centerPanel.remove(flL);
                                centerPanel.remove(flCB);
                                centerPanel.remove(flB);
                                flList.remove(flCB);
                                System.out.println("-------------------");
                                System.out.println("删除组件后，分类可选数量>>>"+flList.size());
                                for (JComboBox cb : flList) {
                                    System.out.println(cb.toString());
                                }
                                System.out.println("-------------------");
                                centerPanel.validate();//调用容器的validate()方法
                                CreatePdmPartUI.this.validate();
                                CreatePdmPartUI.this.repaint();
                            }
                        });
                        centerPanel.add(flL);
                        centerPanel.add(flCB);
                        centerPanel.add(flB);
                        flList.add(flCB);
                        System.out.println("+++++++++++++++");
                        System.out.println("增加组件后，分类可选数量>>>"+flList.size());
                        for (JComboBox cb : flList) {
                            System.out.println(cb.toString());
                        }
                        System.out.println("+++++++++++++++");
                        add(centerLayout, flL, centerConstraints, 0, 0, 1);
                        add(centerLayout, flCB, centerConstraints, 1, 0, 1);
                        add(centerLayout, flB, centerConstraints, 0, 0, 0);
                        centerPanel.validate();// 调用容器的validate()方法
                        CreatePdmPartUI.this.validate();
                        CreatePdmPartUI.this.repaint();

                    }
                });
                centerPanel.add(flL);
                centerPanel.add(flCB);
                centerPanel.add(flB);
                flList = new ArrayList<JComboBox>();
                flList.add(flCB);
                System.out.println("+++++++++++++++");
                System.out.println("增加组件后，分类可选数量>>>"+flList.size());
                for (JComboBox cb : flList) {
                    System.out.println(cb.toString());
                }
                System.out.println("+++++++++++++++");
                add(centerLayout, flL, centerConstraints, 0, 0, 1);
                add(centerLayout, flCB, centerConstraints, 1, 0, 1);
                add(centerLayout, flB, centerConstraints, 0, 0, 0);
            }

        }

        /**
         * 下部内容：按钮。
         */
        {
            GridLayout southLayout = new GridLayout(1, 2);
            southPanel.setLayout(southLayout);
            JButton createPdmPartB = new JButton("创建部件");
            JButton closeB = new JButton("关闭");
            closeB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CreatePdmPartUI.this.dispose();
                }
            });

            southPanel.add(createPdmPartB);
            southPanel.add(closeB);
        }
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(centerScrollPane, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        /**
         * 设置界面大小和居中
         */
        {
            this.setResizable(true);
            this.setSize(800, 600);
            // 屏幕居中
            int windowWidth = this.getWidth(); // 获得窗口宽
            int windowHeight = this.getHeight(); // 获得窗口高
            Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
            Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
            int screenWidth = screenSize.width; // 获取屏幕的宽
            int screenHeight = screenSize.height; // 获取屏幕的高
            this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight
                    / 2 - windowHeight / 2);
        }

    }

    /**
     *
     * @Title: add
     * @Description: TODO(添加控件到容器)
     * @param @param container
     * @param @param c
     * @param @param constraints
     * @param @param x
     * @param @param y
     * @param @param w
     * @param @param h 设定文件
     * @return void 返回类型
     * @throws
     */
    public void add(GridBagLayout layout, Component c,
                    GridBagConstraints constraints, int weightx, int weighty,
                    int gridwidth) {
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.gridwidth = gridwidth;
        layout.setConstraints(c, constraints);
    }

    private void initData() {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
        CreatePdmPartUI cpu = new CreatePdmPartUI(null);
        cpu.setVisible(true);
    }

}
