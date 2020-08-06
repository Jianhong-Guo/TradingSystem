package demomanager;

import controllers.AccountCreator;
import controllers.LoginValidator;
import gateway.FilesReaderWriter;
import gui.GUIDemo;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.messagemanger.MessageManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemoManager {
    /**
     * This is the class that the main class of the whole system will initialize.
     *
     * @author  Jiaqi Gong, Chengle Yang
     * @version IntelliJ IDEA 2020.1.1
     */

    public DemoManager(){

    }

    public void run() {
        // File path
        String userAccountInfoFilePath = "./configs/secureinfofiles/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./configs/secureinfofiles/AdminUserUsernameAndPassword.csv";
        String serializedUserManagerFilePath = "./configs/serializedmanagersfiles/SerializedUserManager.ser";
        String serializedTradeManagerFilePath = "./configs/serializedmanagersfiles/SerializedTradeManager.ser";
        String serializedMeetingManagerFilePath = "./configs/serializedmanagersfiles/SerializedMeetingManager.ser";
        String serializedItemManagerFilePath = "./configs/serializedmanagersfiles/SerializedMeetingManager.ser";
        String serializedFeedbackManagerFilePath = "./configs/serializedmanagersfiles/SerializedFeedbackManager.ser";
        String serializedActionManagerFilePath = "./configs/serializedmanagersfiles/SerializedActionManager.ser";
        String serializedMessageManagerFilePath = "./configs/serializedmanagersfiles/SerializedMessageManager.ser";
        String regUserAlertFilePath = "./src/Alerts/UserAlerts.csv";
        String thresholdValuesFilePath = "./configs/thresholdvaluesfile/ThresholdValues.csv";


        // Start trading system
        while (true){


            try {
                // Create all use classes
                FilesReaderWriter frw = new FilesReaderWriter();
                // Create the new UserManager or Read it from file
                UserManager um;
                if (frw.check_file_empty_or_not(serializedUserManagerFilePath)) { um = new UserManager();}
                else { um = (UserManager) frw.readManagerFromFile(serializedUserManagerFilePath);}

                // Create the new MeetingManager or Read it from file
                MeetingManager mm;
                if (frw.check_file_empty_or_not(serializedMeetingManagerFilePath)) { mm = new MeetingManager();}
                else {mm = (MeetingManager) frw.readManagerFromFile(serializedMeetingManagerFilePath);}

                // Create the new TradeManager or Read it from file
                TradeManager tm;
                if (frw.check_file_empty_or_not(serializedTradeManagerFilePath)) {tm = new TradeManager();}
                else {tm = (TradeManager) frw.readManagerFromFile(serializedTradeManagerFilePath);}

                // Create the new ItemManager or Read it from file
                ItemManager im;
                if (frw.check_file_empty_or_not(serializedItemManagerFilePath)) {im = new ItemManager();}
                else {im = (ItemManager) frw.readManagerFromFile(serializedItemManagerFilePath);}

                // Create the new FeedbackManager or Read it from file
                FeedbackManager fm;
                if (frw.check_file_empty_or_not(serializedFeedbackManagerFilePath)) {fm = new FeedbackManager();}
                else {fm = (FeedbackManager) frw.readManagerFromFile(serializedFeedbackManagerFilePath);}

                // Create the new ActionManager or Read it from file
                ActionManager am;
                if (frw.check_file_empty_or_not(serializedActionManagerFilePath)) {am = new ActionManager();}
                else {am = (ActionManager) frw.readManagerFromFile(serializedActionManagerFilePath);}

                // Create the new MessageManager or Read it from file
                MessageManager messageManager;
                if (frw.check_file_empty_or_not(serializedActionManagerFilePath)) {messageManager = new MessageManager();}
                else {messageManager = (MessageManager) frw.readManagerFromFile(serializedMessageManagerFilePath);}

                // Create the new DisplaySystem
                DisplaySystem ds = new DisplaySystem();

                //Create the new AccountCreator
                AccountCreator ac = new AccountCreator(um, ds);

                // Load accounts data from CSV file to initial login validator
                Map<String, String> userLoginInfo = frw.readUserInfoFromCSVFile(userAccountInfoFilePath);
                Map<String, String> adminUserLoginInfo = frw.readUserInfoFromCSVFile(adminAccountInfoFilePath);
                LoginValidator lv = new LoginValidator(userLoginInfo, adminUserLoginInfo);
                String partsOfUserAlerts = frw.readFromMenu(regUserAlertFilePath);
                List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);

                // Create trading system
                //TradingSystem tradingSystem = new TradingSystem(um, mm, loginValidator, tm, ds, ac, im, fm);

                // Run trading system
                //condition = tradingSystem.tradingSystemInital();

                // Run trading system
                //tradingSystemInitMenuGUI tradingSystemInitMenuGUI  = new tradingSystemInitMenuGUI();

                // Run GUI
                GUIDemo guiController = new GUIDemo(um, mm, tm, im, fm, ac, lv, am, partsOfUserAlerts, thresholdValues);
                guiController.runTradingSystemInitMenuGUI();

            } catch (FileNotFoundException ex) {
                System.out.println("Can not find file, Please check the root of the program and README file.");
                break;
            }catch (ClassNotFoundException ex){
                System.out.println("Can not find Manager in related file, Please check README file or rerun the program.");
                break;
            } catch (IOException ex){
                System.out.println("Please try to clean the content of the files in Managers folder");
                break;
            }
        }

    }
}
