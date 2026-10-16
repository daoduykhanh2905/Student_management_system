package com.example.Students;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentWindow {

    private TableRowSorter<DefaultTableModel> sorter;
    private JTable Student_information;
    private JButton edit;
    private JPanel panel1;
    private JButton searchButton;
    private JTextField SearchText;
    private JTextField textField2;
    private JButton editButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JButton addButton;
    private JComboBox<SearchType> selectType;

    public JTextField getSearchText() {
        return SearchText;
    }

    public JPanel getPanel() {
        return panel1;
    }

    private DefaultTableModel model;
    static Student_Infor[] ifm = new Student_Infor[100];
    static int ifmCount = 0;

//    static {
//        ifm[ifmCount] = new Student_Infor(String userName; String ID; String department; String status );
//        ifmCount++;
//    }


    public StudentWindow() {
        model = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Student_information.setModel(model);
//        model.addRow(new Object[]{"0001", "Khanh", "abcd", "  "});
//        model.addRow(new Object[]{"0002", "Minh", "xyzt", "  "});
        selectType.addItem(SearchType.ID);
        selectType.addItem(SearchType.Name);
        selectType.addItem(SearchType.Department);
        selectType.addItem(SearchType.Status);
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                String search = SearchText.getText();
                SearchType select = (SearchType) selectType.getSelectedItem();
                model.setRowCount(0);
                for (int i = 0; i < ifmCount; i++) {
                    Student_Infor student = ifm[i];
                    if (ifm[i] != null) {
                        boolean found = false;
                        switch (select) {
                            case ID:
                                found = ifm[i].getID().toLowerCase().contains(search.toLowerCase());
                                break;
                            case Name:
                                found = ifm[i].getUserName().toLowerCase().contains(search.toLowerCase());
                                break;
                            case Department:
                                found = ifm[i].getDepartment().toLowerCase().contains(search.toLowerCase());
                                break;
                            case Status:
                                found = ifm[i].getStatus().toLowerCase().contains(search.toLowerCase());
                                break;
                        }
                        if (found) {
                            model.addRow(new Object[]{ifm[i].getID(), ifm[i].getUserName(), ifm[i].getDepartment(), ifm[i].getStatus()});
                        }
                    }
                }
                if (model.getRowCount() == 0)
                    JOptionPane.showMessageDialog(null, "No student found");
                super.mouseClicked(e);
            }
        });
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = Student_information.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student.");
                    return;
                }
                String oldID = model.getValueAt(row, 0).toString();
                String oldName = model.getValueAt(row, 1).toString();
                String oldDepartment = model.getValueAt(row, 2).toString();
                String oldStatus = model.getValueAt(row, 3).toString();
                String id = JOptionPane.showInputDialog(null, "Enter new ID:", oldID);
                if (id == null) {
                    JOptionPane.showMessageDialog(null, "This field is empty! Please enter.");
                    return;
                }
                String name = JOptionPane.showInputDialog(null, "Enter new Name:", oldName);
                if (name == null) {
                    JOptionPane.showMessageDialog(null, "This field is empty! Please enter.");
                    return;
                }
                String department = JOptionPane.showInputDialog(null, "Enter new Department:", oldDepartment);
                if (department == null) {
                    JOptionPane.showMessageDialog(null, "This field is empty! Please enter.");
                    return;
                }
                String status = JOptionPane.showInputDialog(null, "Enter new Status:", oldStatus);
                if (status == null) {
                    JOptionPane.showMessageDialog(null, "This field is empty! Please enter.");
                    return;
                }
                ifm[row] = new Student_Infor(name, id, department, status);
                model.setValueAt(id, row, 0);
                model.setValueAt(name, row, 1);
                model.setValueAt(department, row, 2);
                model.setValueAt(status, row, 3);
                JOptionPane.showMessageDialog(null, "Student updated successful!");
                super.mouseClicked(e);
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = Student_information.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student to delete.");
                    return;
                }
                String id = Student_information.getValueAt(row, 0).toString();
                int result = JOptionPane.showConfirmDialog(null, "Delete student " + id + "?",
                        "Delete Student", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) Student_information.getModel();
                    model.removeRow(row);
                }
                super.mouseClicked(e);
            }
        });
        viewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) Student_information.getModel();
                model.setRowCount(0);
                for (int i = 0; i < ifmCount; i++) {
                    if (ifm[i] != null) {
                        model.addRow(new Object[]{ifm[i].getID(), ifm[i].getUserName(), ifm[i].getDepartment(), ifm[i].getStatus()});
                    }
                }
                System.out.println("view complete");
                Student_information.repaint();

                super.mouseClicked(e);
            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String id = JOptionPane.showInputDialog("Enter ID:");
                String name = JOptionPane.showInputDialog("Enter Name:");
                String department = JOptionPane.showInputDialog("Enter Department:");
                String status = JOptionPane.showInputDialog("Enter Status:");
                if (id != null && name != null && department != null && status != null) {
                    DefaultTableModel model = (DefaultTableModel) Student_information.getModel();
                    model.addRow(new Object[]{id, name, department, status});
                    Student_Infor student = new Student_Infor(name, id, department, status);
                    ifm[ifmCount] = student;
                    ifmCount++;
                    System.out.println("add complete!");
                }
                super.mouseClicked(e);
            }
        });
    }


    public Student_Infor Searchifm(String userName, String ID, String department, String status) {
        for (int i = 0; i < ifmCount; i++) {
            if (getSearchText().equals(ifm[i].userName)) {
                return ifm[i];
            }
        }
        return null;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(2, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Student_information = new JTable();
        scrollPane1.setViewportView(Student_information);
        SearchText = new JTextField();
        panel1.add(SearchText, new GridConstraints(1, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField2 = new JTextField();
        panel1.add(textField2, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        panel1.add(searchButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit");
        panel1.add(editButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        panel1.add(deleteButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewButton = new JButton();
        viewButton.setText("View");
        panel1.add(viewButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        panel1.add(addButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectType = new JComboBox();
        panel1.add(selectType, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
