package managers.itemmanager;

import managers.usermanager.User;

import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * An instance of this class represents an item in the system.
 *
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1.1
 */
public class Item extends Observable implements Serializable, Observer {
    private String name;
    private String description;
    private int itemId;
    private int ownerId;
    private int currHolderId;
    private boolean tradable;
    private Category category;

    /**
     * Constructor of item.
     * Set this name with name, set this description with description, set this ownerId with ownerId, set this
     * currHolderId with ownerId by default, and set itemId with idNumber.
     *
     * @param name        The name of this item
     * @param description The description of this item
     * @param ownerId     The owner's id of this item
     */
    public Item(String name, String description, int ownerId, int itemID, Category category) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        currHolderId = ownerId;
        this.itemId = itemID;
        this.category = category;
        this.tradable = true;
    }

    /**
     * Set the flag indicates whether the item is tradable
     *
     * @param tradable the flag indicates whether the item is tradable
     */
    public void setTradable(boolean tradable) {
        this.tradable = tradable;
        setChanged();
        notifyObserver("Update the item's tradable status to " + tradable + ".");
    }

    /**
     * Get the flag indicates whether the item is tradable
     *
     * @return tradable the flag indicates whether the item is tradable
     */
    public boolean getTradable() {
        return this.tradable;
    }

    /**
     * Get the name of this item
     *
     * @return The name of this item
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this item
     *
     * @param name The name of this item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of this item
     *
     * @return the description of this item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this item
     *
     * @param description The description of this item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the category of this item
     *
     * @return the category of item
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Return the description of the item
     *
     * @return the description of this item
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get the ID of this item
     *
     * @return The ID of this item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Get Owner's ID of this item
     *
     * @return The owner's ID of this item
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Get current holder's ID of this item
     *
     * @return The current holder's ID of this item
     */
    public int getCurrHolderId() {
        return currHolderId;
    }

    /**
     * Set the current holder's ID of this item
     *
     * @param currHolderId The current holder's ID of this item
     */
    public void setCurrHolderId(int currHolderId) {
        this.currHolderId = currHolderId;
    }

    /**
     * Override the to string to describe the item
     *
     * @return A string description of this item
     */
    public String toString() {
        return "This " + name + " with ID " + itemId + " is: " + description + ".\n" +
                "Owner's ID is " + ownerId + " and current holder's ID is " + currHolderId + "."
                + " Tradable = " + getTradable() + ". ";
    }

    /**
     * Update accordingly after subject calls notifyObservers()
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Set change for Item
     */
    public void setChanged() {
    }

    /**
     * Notify the observer about this item's trading status
     *
     * @param tradeStatus new tradable status of this item
     */
    public void notifyObserver(String tradeStatus) {
        update(this, tradeStatus);
    }
}
