/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.team751.vision.VisionNetworkTableCommunication;

/**
 *
 * @author sambaumgarten
 */
public class Diagnostic {    
    public static void runAllDiagnosticTests() {
        NetworkTable nt = NetworkTable.getTable("coproccessor");
        boolean visionPass = runVisionNetworkTablesDiagnostic();
        nt.putBoolean("vision_passed", visionPass);
    }

    public static boolean runVisionNetworkTablesDiagnostic() {
        Logger.staticPrintln(Logger.ANSI_BLUE, "STARTING VISION NET TABLES DIAGNOSTIC");
        Logger.staticPrintln(Logger.ANSI_BLUE + "VisionNetworkTableCommunication Initialized");
        VisionNetworkTableCommunication vntc = new VisionNetworkTableCommunication();
        Logger.staticPrintln(Logger.ANSI_PURPLE + "CHECK if the nettables connection was established successfully");
        if (!vntc.isConnectionAvailable()) {
            Logger.staticPrintln(Logger.ANSI_RED + "FAILED VISION NET TABLES DIAGNOSTIC");
            return false;
        } else {
            Logger.staticPrintln(Logger.ANSI_GREEN + "PASSED VISION NET TABLES DIAGNOSTIC");
            return true;
        }
    }
}
