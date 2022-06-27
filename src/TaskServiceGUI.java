import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.View;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.SwingConstants.CENTER;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskServiceGUI {
    TaskService ts;

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Task Service");  // creates frame
        JPanel tablePanel = new JPanel(new BorderLayout()); // creates table
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620,540);

        //test values
        TaskService ts = new TaskService();
        ts.newTask("6038675399", "Deep Think", "Like Einstein!");
        ts.newTask("6098634309", "Feed Pets", "Marley and Owen");
        ts.newTask("6038675309", "Pay Bills", "Before Collections!");
        ts.newTask("6094675304", "Clean", "Yuck");
        ts.newTask("6038365399", "Play Yoyo", "Walk the dog!");
        ts.newTask("6098634309", "Work", "Fill Prescriptions");
        ts.newTask("6038675319", "Fix Car", "Oil Change");
        ts.newTask("6094674304", "Train Dog", "Sit");

        // gets tasks ready to be loaded
        DoubleLinkedList<Task1> t = ts.getTasks();
        DLL_Iterator it = new DLL_Iterator(t);
        String[][] data = new String[t.size()][3];
        Node temp;
        int i = 0;
        int size = t.size();
        int k =0;
        it.current = t.getHead();



        // adds table components
        String[] column ={"Task ID","Task Name","Task Description"};
        JTable jt=new JTable(new DefaultTableModel(new Object[]{"Task ID", "Task Name","Task Description"},0));
        jt.setSize(500, 100);
        JScrollPane sp=new JScrollPane(jt);
        sp.setSize(500,200);
        tablePanel.add(sp,BorderLayout.NORTH);
        tablePanel.setSize(sp.getSize());


        tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,4),"Current Tasks", TitledBorder.CENTER,
                TitledBorder.TOP));
        jt.setFont(new Font("San Serif", Font.TYPE1_FONT, 12));
        DefaultTableModel model = (DefaultTableModel) jt.getModel();
        // allows for task to be selected in the table
        jt.setSelectionMode(SINGLE_SELECTION);
        jt.getTableHeader().setFont(new Font("San Serif", Font.BOLD, 16));
        jt.setSelectionBackground(Color.DARK_GRAY);
        jt.setGridColor(Color.DARK_GRAY);
        jt.setRowSelectionAllowed(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
        jt.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));


        // loads table with tasks
        while(it.hasNext())
        {
            Task1 te = (Task1) it.current.element;
            it.next();
            model.addRow(new Object[]{te.getID(), te.getName(), te.getTaskDescript()});

        }
        it.current = t.getHead();

        frame.add(tablePanel);


        // adds user buttons
        JPanel buttons = new JPanel(new FlowLayout());
        JButton addTask = new JButton("Add Task");
        buttons.add(addTask,FlowLayout.LEFT);
        addTask.setFont(new Font("San Serif", Font.BOLD, 16));

        // creates a popup window so the user can enter new task info
        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = new JFrame("Add Item");
                JPanel textfields = new JPanel(new BorderLayout());
                textfields.setSize(100,200);
                popup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                popup.setSize(200,120);
                popup.setLocation(250,250);
                popup.setLocationRelativeTo(frame);
                JTextField enterName = new JTextField("Enter Task Name",21);
                enterName.setSize(50,200);
                enterName.setBounds(20,20,150, 20);
                JTextField enterDescription = new JTextField("Enter Task Description Name",51);
                enterDescription.setBounds(20,20,150, 20);
                enterDescription.setSize(50,200);
                JLabel whiteSpace = new JLabel("");
                JButton submit = new JButton("Submit");
                textfields.add(enterName,BorderLayout.NORTH);
                textfields.add(enterDescription, BorderLayout.SOUTH);
                popup.add(textfields, BorderLayout.NORTH);
                popup.add(whiteSpace,BorderLayout.CENTER);
                popup.add(submit, BorderLayout.SOUTH);
                popup.setVisible(true);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name= enterName.getText();
                        String desc = enterDescription.getText();
                        popup.setVisible(false);

                        Random rd = new Random();
                        String id="";
                        for(int i =1; i<11; i++)
                        {
                            id+=rd.nextInt(10);

                        }
                        try {
                            ts.newTask(id,name,desc);
                            // loads table after addition of new task
                            DefaultTableModel model = (DefaultTableModel) jt.getModel();
                            model.addRow(new Object[]{id, name, desc});
                            jt.repaint();

                            // throws an error if there is an exception
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), exception.getMessage(),
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                });




            }
        });
        JButton delete = new JButton("Delete Task");
        buttons.add(delete,FlowLayout.CENTER);
        delete.setFont(new Font("San Serif", Font.BOLD, 16));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jt.getSelectedRow()>0)
                {
                    int selected = jt.getSelectedRow();
                    try {
                        ts.deleteTask(jt.getValueAt(selected,0).toString());
                        DefaultTableModel model = (DefaultTableModel) jt.getModel();
                        model.removeRow(selected);
                        model.fireTableDataChanged();

                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), exception.getMessage(),
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        JButton sortByName = new JButton("Sort By Name");
        buttons.add(sortByName,FlowLayout.LEFT);
        JButton sortByDescription = new JButton("Sort By Description");
        sortByDescription.setFont(new Font("San Serif", Font.BOLD, 16));
        sortByName.setFont(new Font("San Serif", Font.BOLD, 16));
        frame.add(buttons,BorderLayout.SOUTH);
        sortByName.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    ts.sort("name");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                int k =0;
                it.current = t.getHead();
                DefaultTableModel model = (DefaultTableModel) jt.getModel();
                model.setRowCount(0);
                while(it.hasNext())
                {
                    Task1 te = (Task1) it.current.element;
                    it.next();
                    model.addRow(new Object[]{te.getID(), te.getName(), te.getTaskDescript()});

                } jt.repaint();
                sortByName.setEnabled(false);
                sortByDescription.setEnabled(true);
            }
        });

        buttons.add(sortByDescription,FlowLayout.CENTER);
        sortByDescription.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    ts.sort("desc");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                model.setRowCount(0);
                int k =0;
                it.current = t.getHead();
                DefaultTableModel model = (DefaultTableModel) jt.getModel();
                while(it.hasNext())
                {
                    Task1 te = (Task1) it.current.element;
                    it.next();

                    model.addRow(new Object[]{te.getID(), te.getName(), te.getTaskDescript()});
                }
                jt.repaint();
                sortByDescription.setEnabled(false);
                sortByName.setEnabled(true);
            }

        });

        frame.setVisible(true);
    }


}

