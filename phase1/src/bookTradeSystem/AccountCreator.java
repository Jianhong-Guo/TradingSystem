package bookTradeSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class AccountCreator {
    private UserManager um;
    private DisplaySystem ds;
    private FilesReaderWriter fr;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     * @param ds The initial Display system
     */
    public AccountCreator(UserManager um, DisplaySystem ds, FilesReaderWriter fr){
        this.um = um;
        this.ds = ds;
        this.fr = fr;
    }

    /**
     * Creates and adds a new User
     * @param type The type of account: normal user or admin
     * @return true if the User was successfully added, false otherwise
     */
    public boolean createAccount(String type) throws IOException {
        boolean out = false;
        HashMap<String, String> info = um.userPasswords();
        ArrayList<User> listPeople = um.getListUser();

        String username;
        String password;
        String email;


        username = ds.getUsername();
        password = ds.getPassword();
        email = ds.getEmail();

        if (!info.containsKey(username)) {
            User toAdd = new User(username, password, email);
            listPeople.add(toAdd);
            um.setListUser(listPeople);
            out = true;
            //Write the UserManger into ser file in order to save the data
            FilesReaderWriter.saveUserManagerToFile(um, "./src/Managers/SerializedUserManager.ser");

            if (type.equals("Regular")) {
                fr.saveUserInfoToCSVFile("./src/Managers/RegularUserUsernameAndPassword.csv", username, password, email );
            } else if (type.equals("Admin")) {
                fr.saveUserInfoToCSVFile("./src/Managers/AdminUserUsernameAndPassword.csv", username, password, email);
            }
        }

        return out;
    }
}
