package com.study.views;

import com.study.controller.GroupController;
import com.study.controller.MyClassController;
import com.study.controller.RandomSelectionController;
import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.entity.MyClass;
import com.study.entity.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {
    private final GroupController groupController;
    private final MyClassController myClassController;
    private final RandomSelectionController randomSelectionController;

    private JTabbedPane tabbedPane;
    private JPanel randomPanel, groupPanel, classPanel;
    private JTable studentTable, groupTable, classStudentTable;
    private DefaultTableModel studentModel, groupModel, classStudentModel;
    private JLabel selectedStudentLabel;
    private Timer flashTimer;
    private JComboBox<String> classSelector;
    private JButton randomButton;

    public MainGUI(GroupController groupController,
                   MyClassController myClassController,
                   RandomSelectionController randomSelectionController) {
        this.groupController = groupController;
        this.myClassController = myClassController;
        this.randomSelectionController = randomSelectionController;

        initializeFrame();
        createTabbedPane();
        createRandomPanel();
        createGroupPanel();
        createClassPanel();

        // 添加面板到tabbedPane
        tabbedPane.addTab("随机点名", randomPanel);
        tabbedPane.addTab("小组管理", groupPanel);
        tabbedPane.addTab("班级学生", classPanel);

        refreshData();
    }

    private void initializeFrame() {
        setTitle("班级管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置现代化的Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        add(tabbedPane);
    }

    private void createRandomPanel() {
        randomPanel = new JPanel(new BorderLayout(10, 10));
        randomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 顶部面板：班级选择和随机按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // 添加班级管理按钮
        JButton manageClassBtn = new JButton("班级管理");
        manageClassBtn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        manageClassBtn.addActionListener(e -> showClassManagementDialog());

        classSelector = new JComboBox<>();
        classSelector.setPreferredSize(new Dimension(200, 30));
        updateClassSelector();

        randomButton = new JButton("随机抽号");
        randomButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        randomButton.setPreferredSize(new Dimension(120, 30));

        JButton randomGroupBtn = new JButton("随机小组");
        randomGroupBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        randomGroupBtn.setPreferredSize(new Dimension(120, 30));
        randomGroupBtn.addActionListener(e -> showRandomGroup());

        JButton randomGroupMemberBtn = new JButton("随机组员");
        randomGroupMemberBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        randomGroupMemberBtn.setPreferredSize(new Dimension(120, 30));
        randomGroupMemberBtn.addActionListener(e -> showRandomGroupMember());

        topPanel.add(manageClassBtn);
        topPanel.add(new JLabel("选择班级："));
        topPanel.add(classSelector);
        topPanel.add(randomButton);
        topPanel.add(randomGroupBtn);
        topPanel.add(randomGroupMemberBtn);

        // 中间面板：显示被选中的学生
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        selectedStudentLabel = new JLabel("等待抽号...");
        selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        selectedStudentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(selectedStudentLabel);
        centerPanel.add(Box.createVerticalGlue());

        randomPanel.add(topPanel, BorderLayout.NORTH);
        randomPanel.add(centerPanel, BorderLayout.CENTER);

        // 添加随机抽号功能
        randomButton.addActionListener(e -> startRandomSelection());

        tabbedPane.addTab("随机抽号", new ImageIcon(), randomPanel, "随机抽号功能");
    }

    private void createGroupPanel() {
        groupPanel = new JPanel(new BorderLayout(10, 10));
        groupPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 创建工具栏
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton addGroupBtn = new JButton("新建小组");
        JButton deleteGroupBtn = new JButton("删除小组");
        JButton addStudentBtn = new JButton("添加成员");
        JButton refreshBtn = new JButton("刷新");

        toolBar.add(addGroupBtn);
        toolBar.add(deleteGroupBtn);
        toolBar.add(addStudentBtn);
        toolBar.add(refreshBtn);

        // 创建分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);

        // 左侧：小组列表
        String[] groupColumns = {"小组ID", "小组名称", "成员数量"};
        groupModel = new DefaultTableModel(groupColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        groupTable = new JTable(groupModel);
        JScrollPane groupScrollPane = new JScrollPane(groupTable);

        // 右侧：成员列表
        String[] studentColumns = {"学号", "姓名"};
        studentModel = new DefaultTableModel(studentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(studentModel);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);

        splitPane.setLeftComponent(groupScrollPane);
        splitPane.setRightComponent(studentScrollPane);

        groupPanel.add(toolBar, BorderLayout.NORTH);
        groupPanel.add(splitPane, BorderLayout.CENTER);

        // 添加事件监听器
        addGroupBtn.addActionListener(e -> showAddGroupDialog());
        deleteGroupBtn.addActionListener(e -> deleteSelectedGroup());
        addStudentBtn.addActionListener(e -> showAddStudentToGroupDialog());
        refreshBtn.addActionListener(e -> refreshData());

        groupTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateStudentTable();
            }
        });

        tabbedPane.addTab("小组管理", new ImageIcon(), groupPanel, "小组管理功能");
    }

    private void createClassPanel() {
        classPanel = new JPanel(new BorderLayout());
        classPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 创建顶部面板，包含班级选择和按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel classLabel = new JLabel("选择班级：");
        JComboBox<MyClass> classComboBox = new JComboBox<>();
        JButton refreshButton = new JButton("刷新");
        JButton addStudentButton = new JButton("添加学生");
        JButton removeStudentButton = new JButton("删除学生");

        // 添加班级数据
        List<MyClass> classes = myClassController.getAllClass();
        for (MyClass myClass : classes) {
            classComboBox.addItem(myClass);
        }

        // 创建表格
        String[] columnNames = {"学号", "姓名"};
        classStudentModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        classStudentTable = new JTable(classStudentModel);
        JScrollPane scrollPane = new JScrollPane(classStudentTable);

        // 添加事件监听
        classComboBox.addActionListener(e -> {
            MyClass selectedClass = (MyClass) classComboBox.getSelectedItem();
            if (selectedClass != null) {
                updateClassStudentTable(selectedClass.getMyClassId());
            }
        });

        refreshButton.addActionListener(e -> {
            // 刷新班级列表
            classComboBox.removeAllItems();
            List<MyClass> updatedClasses = myClassController.getAllClass();
            for (MyClass myClass : updatedClasses) {
                classComboBox.addItem(myClass);
            }
            // 刷新当前选中班级的学生列表
            MyClass selectedClass = (MyClass) classComboBox.getSelectedItem();
            if (selectedClass != null) {
                updateClassStudentTable(selectedClass.getMyClassId());
            }
        });

        addStudentButton.addActionListener(e -> {
            MyClass selectedClass = (MyClass) classComboBox.getSelectedItem();
            if (selectedClass == null) {
                JOptionPane.showMessageDialog(this, "请先选择一个班级");
                return;
            }
            showAddStudentToClassDialog(selectedClass);
        });

        removeStudentButton.addActionListener(e -> {
            MyClass selectedClass = (MyClass) classComboBox.getSelectedItem();
            if (selectedClass == null) {
                JOptionPane.showMessageDialog(this, "请先选择一个班级");
                return;
            }

            int selectedRow = classStudentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请先选择要删除的学生");
                return;
            }

            int studentId = (Integer) classStudentModel.getValueAt(selectedRow, 0);
            String studentName = (String) classStudentModel.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定要将学生 " + studentName + " (学号: " + studentId + ") 从班级中删除吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String result = myClassController.removeStudentFromClass(
                    selectedClass.getMyClassId(),
                    studentId
                );
                JOptionPane.showMessageDialog(this, result);
                if (result.equals("删除成功")) {
                    updateClassStudentTable(selectedClass.getMyClassId());
                }
            }
        });

        // 组装界面
        topPanel.add(classLabel);
        topPanel.add(classComboBox);
        topPanel.add(refreshButton);
        topPanel.add(addStudentButton);
        topPanel.add(removeStudentButton);

        classPanel.add(topPanel, BorderLayout.NORTH);
        classPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void showAddStudentToClassDialog(MyClass selectedClass) {
        JDialog dialog = new JDialog(this, "添加学生到班级", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField studentIdField = new JTextField();
        inputPanel.add(new JLabel("学号："));
        inputPanel.add(studentIdField);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        confirmButton.addActionListener(e -> {
            try {
                Integer studentId = Integer.parseInt(studentIdField.getText().trim());
                String result = myClassController.addStudentToClass(
                    selectedClass.getMyClassId(),
                    studentId
                );
                JOptionPane.showMessageDialog(dialog, result);
                if (result.equals("添加成功")) {
                    updateClassStudentTable(selectedClass.getMyClassId());
                    dialog.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "请输入有效的学号");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showAddGroupDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        Object[] message = {
            "小组ID:", idField,
            "小组名称:", nameField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "新建小组",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "小组名称不能为空");
                    return;
                }

                Group group = new Group();
                group.setGroupId(id);
                group.setGroupName(name);

                String result = groupController.addGroup(group);
                JOptionPane.showMessageDialog(this, result);
                refreshData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "小组ID必须是数字");
            }
        }
    }

    private void deleteSelectedGroup() {
        int selectedRow = groupTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的小组");
            return;
        }

        int groupId = (Integer) groupModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "确定要删除这个小组吗？这将同时删除所有成员关系。",
            "确认删除", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String result = groupController.deleteGroup(groupId);
            JOptionPane.showMessageDialog(this, result);
            refreshData();
        }
    }

    private void showAddStudentToGroupDialog() {
        int selectedRow = groupTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要添加成员的小组");
            return;
        }

        int groupId = (Integer) groupModel.getValueAt(selectedRow, 0);

        JTextField studentIdField = new JTextField();
        Object[] message = {
            "学生学号:", studentIdField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "添加成员",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim());

                GroupStudent groupStudent = new GroupStudent();
                groupStudent.setGroupId(groupId);
                groupStudent.setStudentId(studentId);

                String result = groupController.addStudent(groupStudent);
                JOptionPane.showMessageDialog(this, result);
                updateStudentTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "学号必须是数字");
            }
        }
    }

    private void startRandomSelection() {
        String selectedClass = (String) classSelector.getSelectedItem();
        if (selectedClass == null || selectedClass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先选择班级！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 获取班级ID
            int classId = Integer.parseInt(selectedClass.split("-")[0]);

            // 使用RandomSelectionController随机抽取学生
            Student selectedStudent = randomSelectionController.getRandomStudentFromClass(classId);

            if (selectedStudent == null) {
                JOptionPane.showMessageDialog(this, "该班级没有学生！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 禁用按钮，防止动画过程中重复点击
            randomButton.setEnabled(false);

            // 开始动画
            startRollingAnimation(selectedStudent);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "无法解析班级ID，请重新选择班级", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startRollingAnimation(Student finalStudent) {
        // 创建动画计时器
        if (flashTimer != null && flashTimer.isRunning()) {
            flashTimer.stop();
        }

        // 获取班级内所有学生
        String selectedClass = (String) classSelector.getSelectedItem();
        int classId = Integer.parseInt(selectedClass.split("-")[0]);
        List<Student> allStudents = myClassController.getAllStudentInClass(classId);
        if (allStudents.isEmpty()) {
            return;
        }

        final int[] counter = {0};
        final int totalFrames = 30; // 总帧数
        final int delay = 50; // 每帧延迟（毫秒）

        flashTimer = new Timer(delay, e -> {
            counter[0]++;

            // 从实际学生列表中随机选择
            Student randomStudent = allStudents.get((int) (Math.random() * allStudents.size()));
            selectedStudentLabel.setText("学号: " + randomStudent.getStudentId());

            // 调整动画速度（越到后面越慢）
            if (counter[0] > totalFrames * 0.7) {
                Timer timer = (Timer) e.getSource();
                timer.setDelay((int) (delay * 1.5));
            }

            // 动画结束
            if (counter[0] >= totalFrames) {
                Timer timer = (Timer) e.getSource();
                timer.stop();

                // 显示最终结果，带有放大效果
                new Timer(50, evt -> {
                    selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 60));
                    selectedStudentLabel.setText("学号: " + finalStudent.getStudentId());
                    ((Timer) evt.getSource()).stop();

                    // 0.5秒后恢复正常大小
                    new Timer(500, evt2 -> {
                        selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
                        ((Timer) evt2.getSource()).stop();
                        // 重新启用按钮
                        randomButton.setEnabled(true);
                    }).start();
                }).start();
            }
        });

        // 开始动画
        flashTimer.start();
    }

    private void showRandomGroup() {
        Group randomGroup = randomSelectionController.getRandomGroup();
        if (randomGroup == null) {
            JOptionPane.showMessageDialog(this, "没有可用的小组！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 开始动画
        startGroupRollingAnimation(randomGroup);
    }

    private void startGroupRollingAnimation(Group finalGroup) {
        if (flashTimer != null && flashTimer.isRunning()) {
            flashTimer.stop();
        }

        // 获取所有小组
        List<Group> allGroups = groupController.getAllGroup();
        if (allGroups.isEmpty()) {
            return;
        }

        final int[] counter = {0};
        final int totalFrames = 20; // 总帧数
        final int delay = 100; // 每帧延迟（毫秒）

        flashTimer = new Timer(delay, e -> {
            counter[0]++;

            // 从实际小组列表中随机选择
            Group randomGroup = allGroups.get((int) (Math.random() * allGroups.size()));
            selectedStudentLabel.setText("小组: " + randomGroup.getGroupId());

            // 动画结束
            if (counter[0] >= totalFrames) {
                Timer timer = (Timer) e.getSource();
                timer.stop();

                // 显示最终结果，带有放大效果
                new Timer(50, evt -> {
                    selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 60));
                    selectedStudentLabel.setText("小组: " + finalGroup.getGroupId());
                    ((Timer) evt.getSource()).stop();

                    // 0.5秒后恢复正常大小
                    new Timer(500, evt2 -> {
                        selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
                        ((Timer) evt2.getSource()).stop();
                    }).start();
                }).start();
            }
        });

        // 开始动画
        flashTimer.start();
    }

    private void showRandomGroupMember() {
        String selectedClass = (String) classSelector.getSelectedItem();
        if (selectedClass == null || selectedClass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先选择班级！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 获取班级ID
        int classId = Integer.parseInt(selectedClass.split("-")[0]);

        // 获取随机小组
        Group randomGroup = randomSelectionController.getRandomGroup();
        if (randomGroup == null) {
            JOptionPane.showMessageDialog(this, "没有可用的小组！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 从选中的小组中随机抽取学生
        Student selectedStudent = randomSelectionController.getRandomStudentFromGroup(randomGroup.getGroupId());
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "该小组没有学生！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 开始组合动画
        startCombinedRollingAnimation(randomGroup, selectedStudent);
    }

    private void startCombinedRollingAnimation(Group finalGroup, Student finalStudent) {
        if (flashTimer != null && flashTimer.isRunning()) {
            flashTimer.stop();
        }

        // 获取所有小组和该小组的所有学生
        List<Group> allGroups = groupController.getAllGroup();
        List<Student> groupStudents = groupController.getAllStudentInGroup(finalGroup.getGroupId());
        if (allGroups.isEmpty() || groupStudents.isEmpty()) {
            return;
        }

        final int[] counter = {0};
        final int totalFrames = 25; // 总帧数
        final int delay = 80; // 每帧延迟（毫秒）

        flashTimer = new Timer(delay, e -> {
            counter[0]++;

            // 从实际列表中随机选择
            Group randomGroup = allGroups.get((int) (Math.random() * allGroups.size()));
            Student randomStudent = groupStudents.get((int) (Math.random() * groupStudents.size()));
            selectedStudentLabel.setText("小组 " + randomGroup.getGroupId() + " 学号: " + randomStudent.getStudentId());

            // 动画结束
            if (counter[0] >= totalFrames) {
                Timer timer = (Timer) e.getSource();
                timer.stop();

                // 显示最终结果，带有放大效果
                new Timer(50, evt -> {
                    selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 60));
                    selectedStudentLabel.setText("小组 " + finalGroup.getGroupId() + " 学号: " + finalStudent.getStudentId());
                    ((Timer) evt.getSource()).stop();

                    // 0.5秒后恢复正常大小
                    new Timer(500, evt2 -> {
                        selectedStudentLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
                        ((Timer) evt2.getSource()).stop();
                    }).start();
                }).start();
            }
        });

        // 开始动画
        flashTimer.start();
    }

    private void updateClassSelector() {
        classSelector.removeAllItems();
        List<MyClass> classes = myClassController.getAllClass();
        for (MyClass myClass : classes) {
            classSelector.addItem(myClass.getMyClassId() + "-" + myClass.getMyClassName());
        }
    }

    private void refreshData() {
        updateClassSelector();
        refreshGroupTable();
        updateStudentTable();
    }

    private void refreshGroupTable() {
        groupModel.setRowCount(0);
        List<Group> groups = groupController.getAllGroup();
        for (Group group : groups) {
            List<Student> students = groupController.getAllStudentInGroup(group.getGroupId());
            groupModel.addRow(new Object[]{
                group.getGroupId(),
                group.getGroupName(),
                students.size()
            });
        }
    }

    private void updateStudentTable() {
        studentModel.setRowCount(0);
        int selectedRow = groupTable.getSelectedRow();
        if (selectedRow != -1) {
            int groupId = (Integer) groupModel.getValueAt(selectedRow, 0);
            List<Student> students = groupController.getAllStudentInGroup(groupId);
            for (Student student : students) {
                studentModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getStudentName()
                });
            }
        }
    }

    private void updateClassStudentTable(Integer classId) {
        classStudentModel.setRowCount(0);
        List<Student> students = myClassController.getAllStudentInClass(classId);
        for (Student student : students) {
            classStudentModel.addRow(new Object[]{
                student.getStudentId(),
                student.getStudentName()
            });
        }
    }

    private void showClassManagementDialog() {
        JDialog dialog = new JDialog(this, "班级管理", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        // 创建班级列表
        DefaultTableModel classModel = new DefaultTableModel(
            new String[]{"班级ID", "班级名称"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable classTable = new JTable(classModel);
        JScrollPane scrollPane = new JScrollPane(classTable);

        // 工具栏
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("添加班级");
        JButton deleteBtn = new JButton("删除班级");
        JButton refreshBtn = new JButton("刷新");

        toolBar.add(addBtn);
        toolBar.add(deleteBtn);
        toolBar.add(refreshBtn);

        // 添加事件监听器
        addBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            Object[] message = {
                "班级ID:", idField,
                "班级名称:", nameField
            };

            int option = JOptionPane.showConfirmDialog(dialog, message, "添加班级",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();

                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "班级名称不能为空");
                        return;
                    }

                    MyClass myClass = new MyClass();
                    myClass.setMyClassId(id);
                    myClass.setMyClassName(name);

                    String result = myClassController.addClass(myClass);
                    JOptionPane.showMessageDialog(dialog, result);
                    refreshClassTable(classModel);
                    updateClassSelector(); // 更新主界面的班级选择器
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "班级ID必须是数字");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = classTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "请先选择要删除的班级");
                return;
            }

            int classId = (Integer) classModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(dialog,
                "确定要删除这个班级吗？这将同时删除所有学生关系。",
                "确认删除", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String result = myClassController.deleteClass(classId);
                JOptionPane.showMessageDialog(dialog, result);
                refreshClassTable(classModel);
                updateClassSelector(); // 更新主界面的班级选择器
            }
        });

        refreshBtn.addActionListener(e -> refreshClassTable(classModel));

        // 初始加载数据
        refreshClassTable(classModel);

        dialog.add(toolBar, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void refreshClassTable(DefaultTableModel model) {
        model.setRowCount(0);
        List<MyClass> classes = myClassController.getAllClass();
        for (MyClass myClass : classes) {
            model.addRow(new Object[]{
                myClass.getMyClassId(),
                myClass.getMyClassName()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 这里需要注入实际的service实现
            MainGUI gui = new MainGUI(null, null, null);
            gui.setVisible(true);
        });
    }
}
