package GUI;

import controllers.AccountCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class regularUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JTextField emailTextField;
    private JTextField cityTextField;
    private JButton createButton;
    private JButton cancelButton;

    public regularUserCreateAccountGUI(AccountCreator accountCreator, GUI gui) {
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                accountCreator.createAccount("Regular", usernameTextField.getText(),
                        new String(passwordField1.getPassword()), emailTextField.getText(), cityTextField.getText());
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.runTradingSystemInitMenuGUI();
            }
        });
    }

    public void run(AccountCreator accountCreator, GUI gui) {
        JFrame frame = new JFrame("regularUserCreateAccount");
        frame.setContentPane(new regularUserCreateAccountGUI(accountCreator, gui).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
