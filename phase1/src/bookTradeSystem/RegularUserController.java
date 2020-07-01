package bookTradeSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter.
 */
public class RegularUserController implements Serializable, Controllable {
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private FilesReaderWriter rw; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param rw       The gateway class used to read or write to file.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserController(DisplaySystem ds, FilesReaderWriter rw,
                                 TradeManager tm, MeetingManager mm,
                                 UserManager um, String username) {
        this.ds = ds;
        this.rw = rw;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(username);
    }

    /**
     * This method gathers all the necessary notifications
     * for the regular user.
     *
     * @return Notifications as properly formatted strings.
     * @throws FileNotFoundException In case the file can't be found.
     */
    @Override
    public String alerts() throws FileNotFoundException {
        //Read this in from file
        //Exception needs to be resolved in main or TradingSystem.
        User regUser = um.findUser(username);
        StringBuilder notification = new StringBuilder();
        String filepath = "UserAlerts.csv"; // move it to src and not the bookTradeSystem
        notification.append(rw.readFromMenu(filepath) + "/n");
        // Your current status:   (frozen / unfrozen) + corresponding messages.
        notification.append("Your current status:" + regUser.getIfFrozen() + "/n");
        notification.append("You have borrowed:" + regUser.getNumBorrowed());
        notification.append("You have lent:" + regUser.getNumLent());
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES");
        notification.append("Max number of transactions a week = "
                + User.getMaxNumTransactionsAllowedAWeek());
        notification.append("Max number of transactions that can be incomplete before the account is frozen = "
                + User.getMaxNumTransactionIncomplete());
        notification.append("Max umber of books you must lend before you can borrow = "
                + User.getNumLendBeforeBorrow());
        notification.append("Max edits per user for meeting’s date + time = "
                + User.getMaxMeetingDateTimeEdits());
        return notification.toString();
    }

    /**
     * This method calls appropriate methods based on user input
     * of the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     *
     * @param mainMenuOption The main menu option chosen by the regular user.
     * @param subMenuOption  The sub menu option for a particular sub menu chosen by the regular user.
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) {
       /*
        1. decide the menu options
        1.5 decide how to read in user's input
        2. decide what use case method to call for each menu option
        3. decide what presenter method to call to print the results for each menu option

        */
        User thisUser = um.findUser(userId);
        switch (mainMenuOption) {
            case 1:
                userAccountMenuResponse(subMenuOption);
                break;
            case 2:
                //TODO: lock here or in the options
                if (thisUser.getIfFrozen()){
                    ds.printOut("This menu is locked");}
                else{
                        userTradingMenuResponse(subMenuOption);
                    }
                break;
            case 3:
                //TODO: lock here or in the options
                if (thisUser.getIfFrozen()){
                    ds.printOut("This menu is locked");}
                else{
                userMeetingMenuResponse(subMenuOption);
                }
                break;
        }

    }

    private void userAccountMenuResponse(int subMenuOption) {
        /*
        1.Browse all the books in other users inventories
        2.Add to own Wish List
        3.Search item
        4.Remove from own Wish List
        5.Remove from own Inventory
        6.Request to unfreeze account
        7.Request that an item be added to your inventory
        8.See most recent three items traded
        0.Exit menu
         */
        ArrayList<Item> allOtherItems = um.allItems(userId);
        switch (subMenuOption) {
            case 1:
                // print items in all users inventory except this user
                ds.printResult(allOtherItems);
                break;
            case 2:
                // add the id to user's wishlist
                ds.printResult(um.addItemWishlist(getItemID(allOtherItems, 1), username));
                break;
            case 3:
                // print all the items being searched for
                ds.printResult(um.searchItem(getItemName()));
            case 4:
                // remove the item id from wishlist
                ds.printResult(um.removeItemWishlist(getItemID(allOtherItems, 0), username));
                break;
            case 5:
                ArrayList<Item> userInventory = um.findUser(userId).getInventory();
                ds.printResult(userInventory);
                ds.printResult(um.removeItemInventory(getItemID(userInventory, 1), username));
                break;
            case 6:
                ds.printResult(um.requestUnfreeze(username, getMessage("Leave your unfreeze request message")));
                break;
            case 7:
                ds.printResult(um.requestAddItem(getItemName(), getMessage("Enter the description of the item"), userId);
                break;
            case 8:
                List<Item> threeItems = new ArrayList<>();
                List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
                for (int id: recentThreeTradedIds) {
                   threeItems.add(idToItem(id));
                }
                ds.printResult(threeItems);
                break;
        }
    }

    private void userTradingMenuResponse(int subMenuOption) {
        /*
          1.Request a trade (lend / borrow / two-way) !!!!- NEED to remove item from wishlist &/ inventory (maybe in constructor???)
          2.Respond to trade requests (agree / disagree)
          3.View open trades
          4.View closed trades
          5.Confirm that a trade has been completed
          6.See top three most frequent trading partners
          7.View transactions that have been cancelled
         */
        switch (subMenuOption) {
            case 1:
//              let user enter borrower id
//              let user enter lender id
//              let user enter item id
//              let user enter tradeType ('Permanent' OR 'Temporary')
                //get info
                int borrowerId = getUserID("borrower or borrower-and-lender 1 (if two-way-trade)");
                int lenderId = getUserID("lender or borrower-and-lender 2 (if two-way-trade)");
                //FIXME: FIX THE PARAM(***)
                int itemId = getItemID(getItemsIDs());
                String tradeType = askForTradeType();
                Trade newTrade = new Trade(borrowerId, lenderId, itemId, tradeType);
//              set status for the person who requested the trade
                if (borrowerId == userId){
                    newTrade.setBorrowerStatus(userId,"Agree");
                }
                else{
                    newTrade.setLenderStatus(userId, "Disagree");
                }
//              add trade
                tm.addTrade(newTrade);
//              TODO: do I need to check user's numBorrowed and numLend to make sure lend>=borrowed???
//              TODO: what if the other person disagrees -- do we keep the trade in tm?
                break;
            case 2:
//              ASKS THE USER TO ENTER TRADE ID AND ENTER AGREE OR DISAGREE
                ds.printResult(tm.getWaitTrade(userId));
                Trade trade = tm.getTradeById(getTradeId());
                String tradeStatus = getAgreeOrNot();
//              TODO: add getBorrowerid and getLenderid methods in the Trade class
                if (trade.getBorrowerId() == userId){
                    trade.setBorrowerStatus(userId, tradeStatus);
                }
                else{
                    trade.setLenderStatus(userId, tradeStatus);
                }
                //remove items -- if agree
                if (tradeStatus.equals("Agree")){
                    um.removeItemInventory(idToItem(itemId), um.idToUsername(lenderId));
                    um.removeItemWishlist(idToItem(itemId), um.idToUsername(borrowerId));}
                ds.printResult(true);
                break;
            case 3:
                ds.printResult(tm.getOpenTrade(userId));
                break;
            case 4:
                ds.printResult(tm.getClosedTrade(userId));
                break;
            case 5:
                ds.printResult(tm.getOpenTrade(userId));
                tradeId = getTradeID();
//              let user enter trade id and we use it to confirm complete
                ds.printResult(tm.confirmComplete(tradeId));
                break;
            case 6:
//              TODO: id for the top three partners???
//              TODO: with that, userManager can have a searchUser function (id - user instance) and then
//              TODO: I'll pass in the ids, get the user instance and then pass it to the presenter class
                break;
            case 7:
                ds.printResult(tm.getCancelledTrade(userId));
                break;

        }
    }

    private void userMeetingMenuResponse(int subMenuOption) {
       /*
    1.Suggest/edit time and place for meetings
    2.Confirm time and place for meetings
    3.Confirm the meeting took place
    4.See the list of meetings need to be confirmed
    5.See the list of meetings that have been confirmed
    6.View to-be-opened trades and set up first meeting
        */

        switch (subMenuOption) {
            case 1:
                Meeting meeting = getMeeting();
                Date time = getTime();
                String place = getPlace();
//              call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
                ds.printResult(meeting.setTimePlaceEdit(userId, time));
                break;
            case 2:
                Meeting meeting2 = getMeeting();
                ds.printResult(meeting2.setTimePlaceConfirm(userId));
                break;
            case 3:
//              "confirmed" means the meeting haven't take place but time and place are confirmed
                ds.printResult(mm.getUnConfirmMeeting(userId));
                Meeting meeting3 = getMeeting();
                ds.printResult(mm.setMeetingConfirm(tm, meeting3, userId));
                break;
            case 4:
                ds.printResult(mm.getUnConfirmMeeting(userId));
                break;
            case 5:
                ds.printResult(mm.getCompleteMeeting(userId));
                break;
            case 6:
                // print a list of trades waiting to be opened -- to have the 1st meeting
                // because once the meeting is set up --> open
                // so need to set up first meeting for the waiting to be opened trades
                ds.printResult(tm.getWaitTrade(userId));
                //public Meeting(int tradeId, int userId1, int userId2, int meetingNum)
                int tradeId = getTradeId();
                int userId1 = getUserID("borrower or borrower-and-lender 1 (if two-way-trade)");
                int userId2 = getUserID("lender or borrower-and-lender 2 (if two-way-trade)");
                ds.printResult(mm.addMeeting(tradeId, userId1, userId2,1, tm));
                break;
        }

    }

    private Meeting getMeeting() {
        ds.printResult(mm.getUnConfirmTimePlace(userId, tm));
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = getTradeId();
        int numMeeting = getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }

    /**
     * Other ask-user-for-input methods
     */
    private int getItemID(ArrayList<Item> potentialItems, int type) {
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        // all possible ids the user can pick from
        ArrayList<Integer> potentialIds;
        // depends on the option the user chooses
        if (type == 1) {
            potentialIds = getItemsIDs(potentialItems);
        } else {
            potentialIds = um.findUser(userId).getWishList();
        }
        Scanner sc = new Scanner(System.in);
        int itemId = 0;
        do {
            ds.printOut("Please enter the id of the item: ");
            // if the input is int
            if (sc.hasNextInt()) {
                itemId = sc.nextInt();
                // if the input is valid
                if (potentialIds.contains(itemId)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return itemId;
    }

    //TODO maybe put this somewhere else
    private ArrayList<Integer> getItemsIDs(ArrayList<Item> allOtherItems) {
        ArrayList<Integer> potentialIds = new ArrayList<>();
        //get the id of all the items in the given arraylist
        for (Item item : allOtherItems) {
            potentialIds.add(item.getItemId());
        }
        return potentialIds;
    }

    private String getItemName() {
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the prefix of the item being searched for: ");
        String itemName = sc.nextLine();
        return itemName;
    }

    //TODO maybe put this somewhere else
    //TODO MAKE SURE ALL IDS IN RECENTTHREEITEM METHOD EXISTS IN THE ARRAYLIST
    private Item idToItem(int id) {
        //Get all the items in the system
        ArrayList<Item> allOtherItems = um.allItems(userId);
        allOtherItems.addAll(um.findUser(userId).getInventory());
        //find the item with <id>
        for (Item item : allOtherItems) {
            if (item.getOwnerId() == id) {
                return item;
            }
        }
        return null;
    }

    private String getMessage(String TypeOfMessage){
        Scanner sc = new Scanner(System.in);
        ds.printOut(TypeOfMessage + "" + "[enter OK to stop]: ");
        StringBuilder fullMsg = null;
        //prevent the null pointer exception
        fullMsg.append("");
        //read the first line
        String msg = sc.nextLine();
        //read in + append until user enters "OK"
        while(!msg.equals("OK")){
            fullMsg.append(msg);
            msg = sc.nextLine();
        }
        return fullMsg.toString();
    }

    private int getUserID(String type){
        Scanner sc = new Scanner(System.in);
        int userId = 0;
        boolean okInput = false;
        do {
            ds.printOut("Please enter the userId of the " + type + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                userId = sc.nextInt();
                // if the input is valid
                if (um.checkUser(um.idToUsername(userId))) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return userId;
    }

    private


}
