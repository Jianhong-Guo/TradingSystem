package presenter;

import controllers.regularusersubcontrollers.RegularUserThresholdController;
import managers.actionmanager.Action;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.messagemanger.Message;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;


import java.util.ArrayList;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class SystemMessage {

    /**
     * Constructs a SystemMessage instance.
     */
    public SystemMessage(){

    }


    /**
     * Prints the message for when there is nothing to be shown.
     */
    public String msgForNothing(){
        return "There's nothing here.";
    }


    public String printInvalidID(){
        return "This is invalid ID.";
    }

    public String invalidInput() {
        return "Invalid put in, please type again.";
    }

    public String invalidNumber(){
       return "Invalid put in, please type a number.";
    }



    /**
     * print out the result of action with object type
     *
     * @param obj the list of objects need to be printed
     */
    public String printResult(ArrayList<Object> obj) {
        StringBuilder string = new StringBuilder();

        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return string.toString();
    }

    public String printOut(String description) {
        return description;
    }


    public String printItemResult(ArrayList<Item> obj) {
        StringBuilder string = new StringBuilder();

        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return string.toString();
    }

    public void printResult(boolean result){
        if (result){
            printOut("Success");
        }else{
            printOut("Fail");
        }
        printOut("\n");
    }

    public void printResult(String string, boolean result) {
        if (result) {
            printOut(string + " is sent successfully");
            printOut("\n");
        } else {
            printOut(string + " fails to be sent");
            printOut("\n");
        }
    }



    /**
     * Gathers all the necessary messages
     * for the regular user.
     *
     * @return messages as properly formatted strings.
     */
    public String regUserAlerts(UserManager um, RegularUserThresholdController tc, String username, String
                                menuPartOfAlert, ArrayList<Integer> thresholdValues) {
        StringBuilder notification;
        notification = new StringBuilder();
        notification.append(menuPartOfAlert).append("\n");
        activeAlerts(notification, um, tc, username, thresholdValues);
        return notification.toString();
    }

    private void activeAlerts(StringBuilder notification, UserManager um, RegularUserThresholdController tc, String username, ArrayList<Integer> thresholdValues) {
        if (!um.getFrozenStatus(username)) {
            // this check if for the uncompletedTransactions one
            if (tc.freezeUserOrNot(thresholdValues.get(1))){
                notification.append("You are frozen because you have exceeded the maximum number of uncompleted transactions limit.").append("\n");
            }
        }
        notification.append("Your username is ").append(username).append("\n");
        notification.append("Your userId is ").append(um.usernameToID(username)).append("\n");
        notification.append("The answer to you're frozen is ").append(um.getFrozenStatus(username)).append("\n");
        notification.append("You have borrowed:").append(um.getInfo(username, "NumBorrowed")).append("\n");
        notification.append("You have lent:").append(um.getInfo(username, "NumLent")).append("\n");
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES").append("\n");
        notification.append("Max number of transactions a week = ").append(thresholdValues.get(0)).append("\n");
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(thresholdValues.get(1)).append("\n");
        notification.append("Max number of books you must lend before you can borrow = ").append(thresholdValues.get(2)).append("\n");
        notification.append("Max edits per user for meeting’s time + place = ").append(thresholdValues.get(3)).append("\n");
    }
    

    /**
     * Returns the message for a meeting that doesn't exist.
     */
    public String msgForMeetingDNE() {
        return "This meeting doesn't exist in the system." + "\n";
    }


    /**
     * Put together the message for when the option is locked for the user
     * due to the threshold.
     * @return The message described above.
     */
    public String lockMessageForThreshold(int maxNumTransactionAWeek) {
        return "This option is locked \n" +
                "You have reached the" + maxNumTransactionAWeek + "transactions a week limit" +
                "\n";
    }

    /**
     * Prints the current threshold value.
     * @param currentVal The current threshold value.
     */
    public String msgForThresholdValue(int currentVal)
    {
        return "The current threshold value is " + currentVal;
    }


    /**
     * Prints the message for the guest that tries to access options
     * that are meant for users.
     */
    public String msgForGuest(){return "Please login, guest account can't do this."; }

    /**
     * Prints the message for the user that tries to
     * access menus that can't access because of their
     * frozen status.
     */
    public String lockMessageForFrozen(){
        return "This menu is locked because you're frozen.";
    }


    /**
     * Put together the message for the user who tries
     * to set the tradable status for an item that is
     * already in the status that he/she is trying to
     * set it in.
     * @param tradableStatus The current tradable status of the item.
     * @return The message described above.
     */
    public String msgNoNeedToSetTradableStatus(boolean tradableStatus){
        return "The tradable status for this item is already " + tradableStatus + ". ";
    }

    /**
     * Put together the message for the user who tries
     * to access menus that he/she can't access because
     * their status is on-vacation.
     * @return The message described above.
     */
    public String lockMessageForVacation() {
        return"Because you're on vacation, you can't be involved in trade/meeting. \n " +
                "If you're back from vacation, please change your on-vacation status in the Account Menu.";
    }

    public String msgForResult(boolean validator){
        if (validator){
            return "Success";
        }
        else {
            return "Fail";
        }
    }

    public String msgForRequestProcess(boolean validator){
        if (validator) {
            return "Your response to request is sent successfully";
        }
        else{
            return "Your response to request is sent unsuccessfully";
        }
    }

    public String msgForRequestResult(boolean validator){
        if (validator) {
            return "Your request is successful.";
        }
        else{
            return "Your request is unsuccessful.";
        }
    }

    public String failMessageForFrozen(){
        return "You're frozen because you borrowed more than you lend";
    }


    public String lockMessageForTPLimit() {
        return "You can't edit any more because the time and place edits limit is reached.";}

    public String msgForFriendRequest(boolean validator, int userToID){
        if (validator){
            return "Your friend request has been sent to user id " + userToID + " successfully.";
        }
        else {
            return "Failed to send friend request to " + userToID + ".\n" +
                    "It seems that you are trying to send friend request twice to " + userToID +
                    " or " + userToID + " is already in your list of friends.";
        }
    }

    public String msgForMessage(String userTo){
        return "Please leave a message for " + userTo + ".";
    }

    /** get a string to describe that nothing inside
     * @param string a string to describe the type
     * @return a string to describe that nothing inside.
     */
    public String msgForNothing(String string){
        return "There is nothing in " + string + " .";
    }

    /**
     * Print out the list of username of the Regular Users
     *
     * @param listOfUser the list of Regular Users
     */
    public String printListUser(ArrayList<TradableUser> listOfUser) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TradableUser user : listOfUser) {
            stringBuilder.append("#").append( user.getId()).append(". ").append(user.getUsername()).append("\n");
        } return stringBuilder.toString();
    }


    /** get a string for the list of users
     * @param listOfUser a list of tradable user
     * @return a string to describe the list of users
     */
    public String printFriendRequest(ArrayList<TradableUser> listOfUser){
        return new String("You get friend requests from the " +
                "following users: \n") + printListUser(listOfUser) +
                "Please enter an id of the friend that you want to add. \n";
    }


    public String printListObject(ArrayList<Object> objects){
        StringBuilder out = new StringBuilder();
        for (Object object: objects){
            out.append(object.toString());
        }
        return out.toString();
    }

    public String printListNumberedObject(ArrayList<Object> objects){
        int i = 0;
        StringBuilder out = new StringBuilder();
        for (Object object: objects){
            out.append("#").append(i).append(". ").append(object.toString());
            i ++;
        }
        return out.toString();
    }


    public String tryAgainMsgForWrongInput(){
       return "Please try again, one or more input(s) are invalid";
    }

    public String tryAgainMsgForWrongFormatInput(){
        return "One or more of your input(s) were in the incorrect format (ex. we ask for int and you entered a word)";
    }

    public String msgForNotYourTurn(){
        return "Sorry, it's not your turn to edit/confirm.";
    }

    public String msgForNo(String string){
        return "There is no " + string + " .";
    }

    public String printAllMessages(ArrayList<Message> messages){
        StringBuilder out = new StringBuilder();
        for (Message msg: messages){
            out.append(msg.toString());
        }
        return out.toString();
    }

    public String msgFortradeCompletedOrNot(boolean result){
        if (result){
            return "This trade is completed.";
        }
        else{
            return "This trade is Incomplete.";
        }
    }

    public String msgForCategory(){
        ArrayList<String> categories = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (Category category : Category.values()) {
            str.append(category.name()).append("\n");
        }
        return str.toString();
    }


    public String getNumKindOfResponse(String option1, String option2){
        return "Please enter an integer (1 - " + option1 + ", 2 - " + option2 + " + : ";
    }

    public String msgForSetTradable(boolean validator, int status){
        String tradable_status;
        if (status == 1){
            tradable_status = "tradable";
        }
        else {
            tradable_status = "non-tradable";
        }
        if (validator){
            return "Set item's tradable status to " + tradable_status + ".";
        }
        return "The tradable status for this item is already " + tradable_status + ". ";
    }

    public String printHistoricalAction(ArrayList<Action> listOfAction) {
        StringBuilder string = new StringBuilder();
        for (Action action : listOfAction) {
            String userType = action.getUserType();
            String menuOption = action.getMenuOption();

            RegularUserActionMessage regularUserActionMessage = new RegularUserActionMessage();
            AdminUserActionMessage adminUserActionMessage = new AdminUserActionMessage();

            switch (userType) {
                case "regularUser":
                    string.append(regularUserActionMessage.regularUserAction(action)).append("\n");
                    break;
                case "adminUser":
                    string.append(adminUserActionMessage.adminUserAction(action)).append("\n");
                    break;
            }
        }
        return string.toString();
    }


}
