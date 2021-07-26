/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package exceptions;

/**
 * An exception thrown when a mail that is already delivered
 * attempts to be delivered again.
 */
public class MailAlreadyDeliveredException extends Throwable    {
    public MailAlreadyDeliveredException(){
        super("This mail has already been delivered!");
    }
}
