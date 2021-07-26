/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package automail;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import com.unimelb.swen30006.wifimodem.WifiModem;
import exceptions.ItemTooHeavyException;
import simulation.Building;

/**
 * addToPool is called when there are mail items newly arrived at the building
 * to add to the MailPool or if a robot returns with some undelivered items -
 * these are added back to the MailPool. The data structure and algorithms used
 * in the MailPool is your choice.
 * 
 */
public class MailPool {

	private class Item {
		int destination;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}
	
	private LinkedList<Item> highPriority;
	private LinkedList<Item> lowPriority;
	private LinkedList<Robot> robots;
	private double threshold;

	public MailPool(int nrobots, double threshold) {
		// Start empty
		highPriority = new LinkedList<Item>();
		lowPriority = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		this.threshold = threshold;
	}

	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
	public void addToPool(MailItem mailItem) {
		// Install the modem & turn on the modem
		WifiModem modem = null;
		try {
			modem = WifiModem.getInstance(Building.MAILROOM_LOCATION);
		} catch (Exception mException) {
			mException.printStackTrace();
		}
		double serviceFee = 0;
		// Check if the connection has worked, if not try connect again
		int connected = 0;
		while (connected == 0) {
			serviceFee = modem.forwardCallToAPI_LookupPrice(
					mailItem.destination_floor);
			StatisticTracker.updateLookups();
			if (serviceFee < 0) {
				StatisticTracker.updateFailedLookups();
			} else {
				connected = 1;
			}
		}
		double expectedCharge = mailItem.calculateCharge(serviceFee,
				Automail.markUpPercentage, (mailItem.getDestFloor() -
						Building.MAILROOM_LOCATION) * 2,
				Automail.activityUnitPrice);
		Item item = new Item(mailItem);
		// If expected charge is above threshold move to high priority,
		// and if below move to low priority
		if (expectedCharge > threshold) {
			highPriority.add(item);
			highPriority.sort(new ItemComparator());
		} else {
			lowPriority.add(item);
			lowPriority.sort(new ItemComparator());
		}

	}

	
	/**
     * load up any waiting robots with mailItems, if any.
     */
	public void loadItemsToRobot() throws ItemTooHeavyException {
		//List available robots
		ListIterator<Robot> i = robots.listIterator();
		while (i.hasNext()) loadItem(i);
	}
	
	//load items to the robot
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		Robot robot = i.next();
		assert(robot.isEmpty());
		// System.out.printf("P: %3d%n", pool.size())
		LinkedList<Item> pool = new LinkedList<Item>();
		pool.addAll(highPriority);
		pool.addAll(lowPriority);
		ListIterator<Item> j = pool.listIterator();
		if (pool.size() > 0) {
			Item current;
			try {
				current = j.next();
				if (highPriority.contains(current)) {
					highPriority.remove();
				} else {
					lowPriority.remove();
				}
				// hand first as we want higher priority delivered first
				robot.addToHand(current.mailItem);
				j.remove();
				if (pool.size() > 0) {
					current = j.next();
					robot.addToTube(current.mailItem);
					if (highPriority.contains(current)) {
						highPriority.remove();
					} else {
						lowPriority.remove();
					}
					j.remove();
				}
				// send the robot off if it has any items to deliver
				robot.dispatch();
				i.remove();       // remove from mailPool queue
			} catch (Exception e) { 
	            throw e; 
	        } 
		}
	}

	/**
     * @param robot refers to a robot which has arrived back
	 *             ready for more mailItems to deliver
     */	
	public void registerWaiting(Robot robot) {
		// assumes won't be there already
		robots.add(robot);
	}

}
