/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package automail;

import java.util.Map;
import java.util.TreeMap;

// import java.util.UUID;

/**
 * Represents a mail item
 */
public class MailItem {
	
    /** Represents the destination floor to which the mail is intended to go */
    protected final int destination_floor;
    /** The mail identifier */
    protected final String id;
    /** The time the mail item arrived */
    protected final int arrival_time;
    /** The weight in grams of the mail item */
    protected final int weight;
    /** Amount of lookups typically charged */
    protected static final int NUM_LOOKUPS_CHARGED = 1;
    /** Amount charged per floor moved */
    protected static final int CHARGE_PER_FLOOR = 5;
    /** Amount charged per lookup */
    protected static final double CHARGE_PER_LOOKUP = 0.1;
    // Amount charged per unit of weight
    // protected static final double CHARGE_PER_WEIGHT = 0;
    protected double serviceFee;
    protected double activityCost;
    protected double charge;
    protected double activity;

    /**
     * Constructor for a MailItem
     * @param dest_floor the destination floor intended for this mail item
     * @param arrival_time the time that the mail arrived
     * @param weight the weight of this mail item
     */
    public MailItem(int dest_floor, int arrival_time, int weight){
        this.destination_floor = dest_floor;
        this.id = String.valueOf(hashCode());
        this.arrival_time = arrival_time;
        this.weight = weight;
        this.serviceFee = 0;
        this.activityCost = 0;
        this.charge = 0;
        this.activity = 0;
    }

    /** *  Calculates the activity cost using the formula in the PDF
     *
     * @param numFloorsTraversed
     * @param activityUnitCost
     * @param numLookups
     */
    public double calculateActivityCost(int numFloorsTraversed,
                                        double activityUnitCost,
                                        int numLookups) {
        double activityCost = ((numFloorsTraversed * CHARGE_PER_FLOOR)
                + (numLookups * CHARGE_PER_LOOKUP)) * activityUnitCost;
        // If weight is factored into the activity cost, include it here
        // activityCost += weight * CHARGE_PER_WEIGHT;

        // If delivery delay penalty is factored in, make the
        // calculateDeliveryDelay method in Simulation public,
        // and then include it here
        // activityCost += Simulation.calculateDeliveryDelay();
        return activityCost;
    }

    /** Calculates the charge for the item after delivery using
     *  the formula in the PDF
     *
     * @param serviceFee
     * @param markUp
     * @param numFloors
     * @param activityUnitCost
     * @return
     */
    public double calculateCharge(double serviceFee, double markUp,
                                  int numFloors, double activityUnitCost) {
        double charge = (serviceFee + calculateActivityCost(numFloors,
                activityUnitCost, NUM_LOOKUPS_CHARGED) * (1 + markUp));
        return charge;
    }

    /** *  Sets all the charge information when the item is delivered
     * @param serviceFee
     * @param activityCost
     * @param activity
     */
    public void setDeliveryInfo(double serviceFee, double activityCost,
                                double activity) {
        this.serviceFee = serviceFee;
        this.activityCost = activityCost;
        this.charge = (serviceFee + activityCost) *
                (1 + Automail.markUpPercentage);
        this.activity = activity;
    }

    @Override
    public String toString() {
        return String.format("Mail Item:: ID: %6s | Arrival: %4d |" +
                " Destination: %2d | Weight: %4d", id, arrival_time,
                destination_floor, weight);
    }

    /**
     * This is printing the charge information for the item
     */
    public String printChargeInfo() {
        double cost = activityCost + serviceFee;
        return String.format(" | Charge: %.2f | Cost: %.2f |" +
                        " Fee: %.2f | Activity: %.2f", charge,
                cost, serviceFee, activity);
    }

    /**
     *
     * @return the destination floor of the mail item
     */
    public int getDestFloor() {
        return destination_floor;
    }
    
    /**
     *
     * @return the ID of the mail item
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the arrival time of the mail item
     */
    public int getArrivalTime(){
        return arrival_time;
    }

    /**
     *
     * @return the weight of the mail item
     */
    public int getWeight(){
       return weight;
   }
   
	static private int count = 0;
	static private Map<Integer, Integer> hashMap =
            new TreeMap<Integer, Integer>();

	@Override
	public int hashCode() {
		Integer hash0 = super.hashCode();
		Integer hash = hashMap.get(hash0);
		if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
		return hash;
	}
}
