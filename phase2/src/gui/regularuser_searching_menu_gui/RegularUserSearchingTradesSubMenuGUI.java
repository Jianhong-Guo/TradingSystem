package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import managers.trademanager.Trade;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserSearchingTradesSubMenuGUI {
    private JPanel rootPanel;
    private JButton incompleteTradesButton;
    private JButton completeTradesButton;

    public RegularUserSearchingTradesSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                                GUIDemo guiDemo, SystemMessage systemMessage) {
        incompleteTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Trade> filter1 = regularUserSearchingMenuController.filterIncompleteTrade();

                if (filter1.size() == 0) {
                    systemMessage.msgForNothing();
                }else {
                    systemMessage.printResult(new ArrayList<>(filter1));
                }
                //close this window
                guiDemo.closeWindow(rootPanel);

            }
        });
        completeTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Trade> filter = regularUserSearchingMenuController.filterCompleteTrade();
                if (filter.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(filter));
                }

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
                guiDemo.runRegularUserSearchingMenuGUI();


            }
        });
    }

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingTradesSubMenu");
        frame.setContentPane(new RegularUserSearchingTradesSubMenuGUI(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private JButton backButton;
}
