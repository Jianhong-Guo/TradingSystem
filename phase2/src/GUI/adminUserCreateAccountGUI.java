package GUI;

import controllers.AccountCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton createButton;
    private JButton cancelButton;

    public adminUserCreateAccountGUI(AccountCreator accountCreator, GUI gui) {
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                accountCreator.createAccount("Admin", usernameTextField.getText(),
                        new String(passwordField1.getPassword()), "None", "None");
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
                //TODO: Back to Admin User Main Menu

            }
        });
    }

    public void run(AccountCreator accountCreator, GUI gui) {
        JFrame frame = new JFrame("createAccountGUI");
        frame.setContentPane(new adminUserCreateAccountGUI(accountCreator, gui).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
