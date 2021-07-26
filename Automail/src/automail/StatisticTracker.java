/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package automail;

public class StatisticTracker {
    /** Statistics tracking variables */
    static int totalDeliveries;
    static double totalBillableActivity;
    static double totalActivityCost;
    static double totalServiceFee;
    static int totalLookups;
    static int totalFailedLookups;

    public StatisticTracker() {
        totalActivityCost = 0.0;
        totalDeliveries = 0;
        totalBillableActivity = 0.0;
        totalServiceFee = 0.0;
        totalLookups = 0;
        totalFailedLookups = 0;
    }

    /** * Updates the amount of total lookups
     */
    public static void updateLookups() {
        totalLookups++;
    }

    /** * Updates the amount of total failed lookups
     */
    public static void updateFailedLookups() {
        totalFailedLookups++;
    }

    /** * Updates the charge statistics
     * @param billableActivity
     * @param activityCost
     * @param serviceFee
     */
    public static void updateStatistics(double billableActivity,
                                        double activityCost,
                                        double serviceFee) {
        totalBillableActivity += billableActivity;
        totalActivityCost += activityCost;
        totalServiceFee += serviceFee;
    }

    /** * Updates the total deliveries
     */
    public static void updateDeliveries() {
        totalDeliveries++;
    }

    /** * Prints the final statistics
     */
    public static void printStatisticsTracking() {
        System.out.println("Total number of items delivered: " +
                totalDeliveries);
        System.out.printf("Total billable activity: %.2f\n",
                totalBillableActivity);
        System.out.printf("Total activity cost: %.2f\n", totalActivityCost);
        System.out.printf("Total service cost: %.2f\n", totalServiceFee);
        System.out.println("Total lookups: " + totalLookups);
        System.out.println("Total successful lookups: " + (totalLookups -
                totalFailedLookups));
        System.out.println("Total failed lookups: " + totalFailedLookups);
    }

}
