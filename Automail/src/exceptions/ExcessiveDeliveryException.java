/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package exceptions;

/**
 * An exception thrown when the robot tries to deliver more
 * items than its tube capacity without refilling.
 */
public class ExcessiveDeliveryException extends Throwable {
	public ExcessiveDeliveryException(){
		super("Attempting to deliver more than 4 items in a single trip!!");
	}
}
