/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 *
 * @author sambaumgarten
 */
public class VisionNetworkTableCommunication {
    private static NetworkTable netTable;
    
    public VisionNetworkTableCommunication() {
        netTable = NetworkTable.getTable("SmartDashboard");
    }
    
    public static boolean isConnectionAvailable() {
        try {
            getImageCount();
            return true;
        } catch (TableKeyNotDefinedException e) {
            return false;
        }
    }
    
    public static double getImageCount() {
        try {
            return netTable.getNumber("IMAGE_COUNT", 0.0);
        } catch (TableKeyNotDefinedException e) {
            return 0;
        }
    }
    
    public static NumberArray getRectangles() {
        final NumberArray targetArray = new NumberArray();
        netTable.retrieveValue("BFR_COORDINATES", targetArray);
        
        return targetArray;
    }
    
    public static double getDesiredDistance() {
        return netTable.getNumber("DesiredDistance");
    }
    
    public static double getDesiredDistanceEpsilon() {
        return netTable.getNumber("DesiredDistanceEpsilon");
    }
    
    public static double getSlowDownDistance() {
        return netTable.getNumber("SlowDownDistance");
    }
    
    public static double getMaxAngle() {
        return netTable.getNumber("MaxAngle");
    }
    
    public static double getMaxForwardSpeed() {
        return netTable.getNumber("MaxForwardSpeed");
    }
    
    public static double getMinForwardSpeed() {
        return netTable.getNumber("MinForwardSpeed");
    }
    
    public static double getMaxAngleSpeed() {
        return netTable.getNumber("MaxAngleSpeed");
    }
    
    public static double getLiveMode() {
        return netTable.getNumber("LiveMode");
    }
    
    public static double getShootMode() {
        return netTable.getNumber("ShootMode");
    }
    
    public static double getStrafeMode() {
        return netTable.getNumber("StrafeMode");
    }
    
    public static void setVisionDistance(double distance) {
        netTable.putNumber("VisionDistance", distance);
    }
    
    public static void setVisionAngle(double angle) {
        netTable.putNumber("VisionAngle", angle);
    }
    
    public static void setDesiredDistance(double desiredDistance) {
        netTable.putNumber("DesiredDistance", desiredDistance);
    }

    public static void setDesiredDistanceEpsilon(double desiredDistanceEpsilon) {
        netTable.putNumber("DesiredDistanceEpsilon", desiredDistanceEpsilon);
    }

    public static void setSlowDownDistance(double slowDownDistance) {
        netTable.putNumber("SlowDownDistance", slowDownDistance);
    }

    public static void setMaxAngle(double maxAngle) {
        netTable.putNumber("MaxAngle", maxAngle);
    }

    public static void setMaxForwardSpeed(double maxForwardSpeed) {
        netTable.putNumber("MaxForwardSpeed", maxForwardSpeed);
    }

    public static void setMinForwardSpeed(double minForwardSpeed) {
        netTable.putNumber("MinForwardSpeed", minForwardSpeed);
    }

    public static void setMaxAngleSpeed(double maxAngleSpeed) {
        netTable.putNumber("MaxAngleSpeed", maxAngleSpeed);
    }

    public static void setLiveMode(boolean liveMode) {
        netTable.putBoolean("LiveMode", liveMode);
    }

    public static void setShootMode(boolean shootMode) {
        netTable.putBoolean("ShootMode", shootMode);
    }

    public static void setStrafeMode(boolean strafeMode) {
        netTable.putBoolean("StrafeMode", strafeMode);
    }
}
