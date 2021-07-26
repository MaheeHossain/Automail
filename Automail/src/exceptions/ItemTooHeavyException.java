/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package exceptions;

/**
 * This exception is thrown when a robot takes a MailItem from its
 * StorageTube which is too heavy for that robot
 */
public class ItemTooHeavyException extends Exception {
    public ItemTooHeavyException(){
        super("Item too heavy! Dropped by robot.");
    }
}
