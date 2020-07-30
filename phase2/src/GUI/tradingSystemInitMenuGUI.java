package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.maincontrollers.TradingSystem;

public class tradingSystemInitMenuGUI{
    private JButton a1LoginButton;
    private JPanel panel1;
    private JButton a2LoginAsGuestButton;
    private JButton a3CreateAccountButton;
    private JButton exitButton;




    public void run(GUI gui) {
        JFrame frame = new JFrame("Trading System");
        frame.setContentPane(new tradingSystemInitMenuGUI(gui).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public tradingSystemInitMenuGUI(GUI gui){

        a1LoginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //login
                gui.runLogin();
            }
        });

        a2LoginAsGuestButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //login as guest
                //TODO: Implement login as guest

            }
        });
        a3CreateAccountButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create account
                gui.runRegularUserCreateAccount();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit system
                System.exit(0);
            }
        });

    }

}
