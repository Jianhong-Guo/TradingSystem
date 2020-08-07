package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.UserInputGUI;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserSearchingItemsSubMenuGUI {
    private JButton filterByCategoryButton;
    private JPanel rootPanel;
    private JButton searchItemByNameButton;
    private JButton searchItemByIdButton;
    private JButton sortByNumberOfButton;
    private JButton backButton;

    public RegularUserSearchingItemsSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                               GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo, ItemManager itemManager,
                                               SystemMessage systemMessage) {
        filterByCategoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = regularUserSearchingMenuController.listCategory();
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                String category = guiUserInputInfo.getTempUserInput();
                try {
                    Category ca = Category.valueOf(category);
                    ArrayList<Integer> c = itemManager.getCategoryItem(ca);
                if (c.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(c));
                }
                }catch(Exception ex){
                    systemMessage.invalidInput();
                }

                //close this window
                guiDemo.closeWindow(rootPanel);
            }
        });
        searchItemByNameButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "Please enter the item name:";

                UserInputGUI userInputGUI1 = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI1.run(string, guiUserInputInfo);

                String name = guiUserInputInfo.getTempUserInput();
                UserInputGUI userInputGUI2 = new UserInputGUI(name, guiUserInputInfo);
                userInputGUI2.run(name, guiUserInputInfo);

                ArrayList<Integer> c = regularUserSearchingMenuController.searchItemByName(guiDemo.getUserInput());

                if (c.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(c));
                }

                //close this window
                guiDemo.closeWindow(rootPanel);
            }
        });
        searchItemByIdButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "Please enter the item ID:";

                // Get the id from user
                UserInputGUI userInputGUI1 = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI1.run(string, guiUserInputInfo);

                //String ID = guiUserInputInfo.getTempUserInput();
                //UserInputGUI userInputGUI2 = new UserInputGUI(ID, guiUserInputInfo);
                //userInputGUI2.run(ID, guiUserInputInfo);

                // print the info of the item
                try{
                    int id = Integer.parseInt(guiDemo.getUserInput());
                    String description = regularUserSearchingMenuController.getItemById(id);
                    guiDemo.printNotification(description);

                } catch (NumberFormatException  ex){
                    guiDemo.printNotification("Please enter number!");
                }


                //close this window
                guiDemo.closeWindow(rootPanel);
            }
        });
        sortByNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> items = regularUserSearchingMenuController.sortItemByFollows();
                systemMessage.printItemResult(items);

                //close this window
                guiDemo.closeWindow(rootPanel);
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close this window
                guiDemo.closeWindow(rootPanel);

                // call next window
                guiDemo.runRegularUserSearchingMenuGUI();
            }
        });
    }

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo, ItemManager itemManager,
                    SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingItemsSubMenu");
        frame.setContentPane(new RegularUserSearchingItemsSubMenuGUI(regularUserSearchingMenuController, guiDemo,
                guiUserInputInfo, itemManager, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}