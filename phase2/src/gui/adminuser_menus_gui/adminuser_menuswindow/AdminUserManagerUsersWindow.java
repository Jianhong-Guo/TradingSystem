package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import gui.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserManagerUsersWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton cancleButton;
    private JButton confirmButton;
    private JTextArea textArea;

    public void run(int option, GUIDemo guiDemo, String info, AdminUserManagerUsersController muc) {
        JFrame frame = new JFrame("AdminUserManagerUsersWindow");
        frame.setContentPane(new AdminUserManagerUsersWindow(option, guiDemo, info, muc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }



    public AdminUserManagerUsersWindow(int option, GUIDemo guiDemo, String info, AdminUserManagerUsersController muc) {
        this.textArea.setText(info);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));



        cancleButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runAdminUserManageUsersSubMenu();
                guiDemo.closeWindow(rootPanel);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String regularUserName = textField.getText();
                if (option == 1){
                    if (regularUserName != null) {String result = muc.freezeUser(regularUserName);
                        guiDemo.printNotification(result);}
                }
                if (option == 2){
                    if (regularUserName != null) {String result = muc.unfreezeUser(regularUserName);
                        guiDemo.printNotification(result);}
                }
                guiDemo.closeWindow(rootPanel);
            }
        });
    }
}