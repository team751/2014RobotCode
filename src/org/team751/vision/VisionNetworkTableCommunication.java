/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 *
 * @author sambaumgarten
 */
public class VisionNetworkTableCommunication {
    private static NetworkTable netTable;
    
    /**
     * Sets up network table connection
     */
    public VisionNetworkTableCommunication() {
        netTable = NetworkTable.getTable("SmartDashboard");
    }
    
    /**
     * Check if a connection with the RoboRealm computer has been established
     * @return connected
     */
    public boolean isConnectionAvailable() {
        return getImageCount() != -1;
    }
    
    /**
     * Gets the image count
     * @return the image count
     */
    // Also used to check if connection is established
    public double getImageCount() {
        try {
            return netTable.getNumber("IMAGE_COUNT", 0.0);
        } catch (TableKeyNotDefinedException e) {
            return -1;
        }
    }
    
    /**
     * Gets the rectangles in the vision processing output
     * @return array of targets
     */
    public NumberArray getRectangles() {
        final NumberArray targetArray = new NumberArray();
        netTable.retrieveValue("BFR_COORDINATES", targetArray);
        
        return targetArray;
    }
    
    /**
     * Gets the desired distance to the goal
     * @return
     */
    public double getDesiredDistance() {
        return SmartDashboard.getNumber("DesiredDistance", 90);
    }
    
    /**
     * Gets the desired distance epsilon
     * @return
     */
    public double getDesiredDistanceEpsilon() {
        return netTable.getNumber("DesiredDistanceEpsilon");
    }
    
    /**
     * Gets the slow down distance
     * @return
     */
    public double getSlowDownDistance() {
        return netTable.getNumber("SlowDownDistance");
    }
    
    /**
     * Gets the maximum angle
     * @return
     */
    public double getMaxAngle() {
        return netTable.getNumber("MaxAngle");
    }
    
    /**
     * Gets the max speed
     * @return
     */
    public double getMaxForwardSpeed() {
        return netTable.getNumber("MaxForwardSpeed");
    }
    
    /**
     *
     * @return
     */
    public double getMinForwardSpeed() {
        return netTable.getNumber("MinForwardSpeed");
    }
    
    /**
     *
     * @return
     */
    public double getMaxAngleSpeed() {
        return SmartDashboard.getNumber("MaxAngleSpeed", 0);
    }
    
    /**
     *
     * @return
     */
    public double getLiveMode() {
        return netTable.getNumber("LiveMode");
    }
    
    /**
     *
     * @return
     */
    public double getShootMode() {
        return netTable.getNumber("ShootMode");
    }
    
    /**
     *
     * @return
     */
    public double getStrafeMode() {
        return netTable.getNumber("StrafeMode");
    }
    
    /**
     *
     * @param distance
     */
    public void setVisionDistance(double distance) {
        netTable.putNumber("VisionDistance", distance);
    }
    
    /**
     *
     * @param angle
     */
    public void setVisionAngle(double angle) {
        netTable.putNumber("VisionAngle", angle);
    }
    
    /**
     *
     * @param desiredDistance
     */
    public void setDesiredDistance(double desiredDistance) {
        netTable.putNumber("DesiredDistance", desiredDistance);
    }

    /**
     *
     * @param desiredDistanceEpsilon
     */
    public void setDesiredDistanceEpsilon(double desiredDistanceEpsilon) {
        netTable.putNumber("DesiredDistanceEpsilon", desiredDistanceEpsilon);
    }

    /**
     *
     * @param slowDownDistance
     */
    public void setSlowDownDistance(double slowDownDistance) {
        netTable.putNumber("SlowDownDistance", slowDownDistance);
    }

    /**
     *
     * @param maxAngle
     */
    public void setMaxAngle(double maxAngle) {
        netTable.putNumber("MaxAngle", maxAngle);
    }

    /**
     *
     * @param maxForwardSpeed
     */
    public void setMaxForwardSpeed(double maxForwardSpeed) {
        netTable.putNumber("MaxForwardSpeed", maxForwardSpeed);
    }

    /**
     *
     * @param minForwardSpeed
     */
    public void setMinForwardSpeed(double minForwardSpeed) {
        netTable.putNumber("MinForwardSpeed", minForwardSpeed);
    }

    /**
     *
     * @param maxAngleSpeed
     */
    public void setMaxAngleSpeed(double maxAngleSpeed) {
        netTable.putNumber("MaxAngleSpeed", maxAngleSpeed);
    }

    /**
     *
     * @param liveMode
     */
    public void setLiveMode(boolean liveMode) {
        netTable.putBoolean("LiveMode", liveMode);
    }

    /**
     *
     * @param shootMode
     */
    public void setShootMode(boolean shootMode) {
        netTable.putBoolean("ShootMode", shootMode);
    }

    /**
     *
     * @param strafeMode
     */
    public void setStrafeMode(boolean strafeMode) {
        netTable.putBoolean("StrafeMode", strafeMode);
    }
}
