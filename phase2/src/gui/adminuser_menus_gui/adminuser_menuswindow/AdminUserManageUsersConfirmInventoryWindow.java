package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminUserManageUsersConfirmInventoryWindow {
    private JTextPane textPane1;
    private JTextField userInput;
    private JRadioButton notApprovedRadioButton;
    private JRadioButton approvedRadioButton;
    private JButton cancelButton;
    private JButton confirmButton;
    private JPanel rootPanel;

    public AdminUserManageUsersConfirmInventoryWindow(String string, ArrayList<Item> itemsToAdd, AdminUserManagerUsersController muc,
                                                      GUIDemo guiDemo, SystemMessage sm, RegularUserIDChecker idc,
                                                      AdminUserOtherInfoChecker oic){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        ButtonGroup group = new ButtonGroup();
        group.add(approvedRadioButton);
        group.add(notApprovedRadioButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText();
                if (idc.checkInt(input)){
                    int itemToAddNum = Integer.parseInt(input);
                    if (oic.checkItemToAddNum(itemsToAdd.size(), itemToAddNum)){
                        if (approvedRadioButton.isSelected()){
                            muc.addItemOrNot(itemToAddNum, true);
                            guiDemo.printNotification(sm.msgForResult(true));
                        }
                        else if (notApprovedRadioButton.isSelected()){
                            muc.addItemOrNot(itemToAddNum, false);
                            guiDemo.printNotification(sm.msgForResult(true));
                        }
                        else{
                            guiDemo.printNotification("Please select approve or not.");
                        }
                    }
                    else{
                        guiDemo.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else{
                    guiDemo.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiDemo.closeWindow(rootPanel);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    public void run(String string, ArrayList<Item> itemsToAdd, AdminUserManagerUsersController muc,
                    GUIDemo guiDemo, SystemMessage sm, RegularUserIDChecker idc,
                    AdminUserOtherInfoChecker oic){
        JFrame frame = new JFrame("Confirm item to be added to inventory");
        frame.setContentPane(new AdminUserManageUsersConfirmInventoryWindow(string, itemsToAdd, muc, guiDemo, sm, idc, oic).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}