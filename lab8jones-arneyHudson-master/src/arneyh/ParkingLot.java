/*
 * Course: CS1011 - 011
 * Fall 2020
 * Lab 8 - ParkingLots
 * Name: Hudson Arney
 * Created: 10/26/2021
 */

package arneyh;

/**
 * Parking Lot class is supposed to keep track of one lot.
 * It looks when cars enter and exit and the time it takes
 * once the parking lot is full until enough cars leave again
 * and more can move back in.
 */
public class ParkingLot {

    /**
     * Closed Threshold is a variable that is used throughout the class
     * It represents the percent limit the parking lot can hold before
     * being "closed"
     */
    public static final double CLOSED_THRESHOLD = 80.0;
    private final String name;
    private final int capacity;
    private int numCars;
    private int totalMinClosed = 0;
    private boolean closed = false;
    private int initialClosing;
    private int closingTime;
    private int highestTime;

    /**
     * First parking lot constructor
     * Used if the parking lot is not given a name which defaults to "test"
     * still takes in a capacity and calls the default constructor
     * @param capacity the limit amount of cars that can fit in the lot
     */
    public ParkingLot(int capacity) {
        this("test", capacity);
    }

    /**
     * Main constructor of the class.
     * initializes capacity and name
     * @param name given for the parking lot
     * @param capacity the limit amount of cars that can fit in the lot
     */
    public ParkingLot(String name, int capacity) {
        this.capacity = capacity;
        this.name = name;
    }

    /**
     * Displays the name of the parking lot along with its status
     * whether it is completely full (CLOSED) or the percent full
     */
    public void displayStatus() {
        if(CLOSED_THRESHOLD > getPercentFull()) {
            System.out.printf(name + " parking lot status: %.1f%%", getPercentFull());
            System.out.println();
        } else {
            System.out.println(name + " parking lot status: CLOSED");
        }
    }

    public int getMinutesClosed() {
        return totalMinClosed;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfSpotsRemaining() {
        return capacity - numCars;
    }

    /**
     * divides the number of cars in the lot by the capacity of the lot
     * @return the percent of cars in the parking lot
     */
    public double getPercentFull() {
        final int oneHundred = 100;
        return ((double) numCars / capacity) * oneHundred;
    }

    /**
     * Finds if the lot is closed by seeing if the number
     * of cars are less than or equal to the 80% limit
     * @return true if the limit is above or equal too 80%
     */
    public boolean isClosed() {
        if(getPercentFull() >= CLOSED_THRESHOLD) {
            closed = true;
        }
        return closed;
    }

    /**
     * Method that looks for when cars enter the parking lot
     * when a car exits it subtracts that from the total capacity
     * @param timestamp The time when a car enters
     */
    public void markVehicleEntry(int timestamp){
        if(timestamp >= highestTime) {
            numCars++;
            highestTime = timestamp;
            if (!closed) {
                if(getPercentFull() >= CLOSED_THRESHOLD) {
                    closed = true;
                    this.closingTime = timestamp;
                }
            }
        }
    }

    /**
     * Method that finds when cars exit the parking lot.
     * When a car exits it subtracts that from the total capacity
     * @param timestamp The time when a car exits
     */
    public void markVehicleExit(int timestamp) {
        if(timestamp >= highestTime) {
            highestTime = timestamp;
            numCars--;
            if (closed) {
                if(getPercentFull() < CLOSED_THRESHOLD) {
                    closed = false;
                    this.initialClosing = timestamp;
                    totalMinClosed += initialClosing - closingTime;
                }
            }
        }
    }
}
