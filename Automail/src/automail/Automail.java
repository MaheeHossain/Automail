/**
 * Workshop 10 Group 9: Aidan Western (1083407),
 * Yunhao Fang (1067868), Mahee Hossain (1080102)
 */

package automail;

import simulation.IMailDelivery;

public class Automail {
	      
    public Robot[] robots;
    public MailPool mailPool;

    public static double markUpPercentage;
    public static double activityUnitPrice;
    
    public Automail(MailPool mailPool, IMailDelivery delivery, int numRobots,
                    double percentage, double unitPrice) {
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;

    	markUpPercentage = percentage;
    	activityUnitPrice = unitPrice;
    	
    	/** Initialize robots */
    	robots = new Robot[numRobots];
    	for (int i = 0; i < numRobots; i++) robots[i] = new Robot(delivery,
                mailPool, i);
    }


}
