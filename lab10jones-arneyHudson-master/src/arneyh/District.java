/*
 * Course: CS1011 - 011
 * Fall 2020
 * Lab 10 - Lots of Parking Revisited
 * Name: Hudson Arney
 * Created: 11/8/2021
 */
package arneyh;

import java.util.ArrayList;

/**
 * Manages parking lots within a district.
 * @author Hudson Arney
 */
public class District {

    private ArrayList<ParkingLot> lots;
    private int numLots;
    private int initialClosing;
    private int totalMinutesClosed;

    /**
     * District constructor that sets lots
     * equal to the constant from the beginning
     * of the class
     */
    public District() {
        lots = new ArrayList<>();
    }

    /**
     * Add Lots method that takes in the name of the district and the
     * capacity of the parking lot.
     * This method returns the number of lots the parking lot should have
     * into the ArrayList lots
     * @param name Given in the Driver class that will classify a specific lot
     * @param capacity The amount of spaces in the parking lot
     * @return The index of the new lot created if lots are added
     */
    public int addLot(String name, int capacity) {
        int newIndex = numLots;
        lots.add(new ParkingLot(name, capacity));
        numLots++;
        return newIndex;
    }

    /**
     * The get lot method takes in an index value and
     * will return the actual value in the array at that index.
     * @param index The index of the array given in the driver class
     * @return the value at the given index
     */
    public ParkingLot getLot(int index) {
        return lots.get(index);
    }

    /**
     * Returns the number of remaining parking spots in the district
     * @return the number of remaining parking spots in the district
     */
    public int getNumberOfSpotsRemaining() {
        int numSpots = 0;
        for(int i = 0; i < numLots; i++) {
            numSpots += getLot(i).getNumberOfSpotsRemaining();
        }
        return numSpots;
    }

    /**
     * Returns the amount of time all three lots have been
     * simultaneously closed.
     * @return number of minutes all three lots have been closed
     */
    public int getMinutesClosed() {
        return totalMinutesClosed;
    }

    /**
     * Checks the status of all three lots in the district and
     * returns true if they are all closed and false otherwise.
     * @return whether all three lots in the district are closed
     */
    public boolean isClosed() {
        for(int i = 0; i < numLots; i++) {
            if(!getLot(i).isClosed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Record a vehicle entering a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleEntry for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleEntry on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be 1, 2, or 3)
     * @param timestamp Entry timestamp in minutes since all lots were opened.
     */
    public void markVehicleEntry(int lotNumber, int timestamp) {
        boolean initialStatus = isClosed();
        for(int i = 0; i < numLots; i++) {
            if(i == lotNumber) {
                getLot(i).markVehicleEntry(timestamp);
            }
        }
        boolean finalStatus = isClosed();
        if (!initialStatus && finalStatus){
            initialClosing = timestamp;
        }

    }

    /**
     * Record a vehicle exiting a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleExit for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleExit on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be 1, 2, or 3)
     * @param timestamp Exit timestamp in minutes since all lots were opened.
     */
    public void markVehicleExit(int lotNumber, int timestamp) {
        boolean initialStatus = isClosed();
        for(int i = 0; i < numLots; i++) {
            if(i == lotNumber) {
                getLot(i).markVehicleExit(timestamp);
            }
        }
        boolean finalStatus = isClosed();
        if (initialStatus && !finalStatus){
            totalMinutesClosed += timestamp - initialClosing;
        }
    }

    @Override
    public String toString() {
        String lotsName = "District status:\n";
        for(int i = 0; i < numLots; i++) {
            lotsName += "  " + getLot(i).toString() + "\n";
        }
        return lotsName;
    }
}