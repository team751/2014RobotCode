/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.utils;

import org.team751.vision.VisionNetworkTableCommunication;

/**
 *
 * @author sambaumgarten
 */
public class Diagnostic {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static void runAllDiagnosticTests() {
        boolean visionPass = runVisionNetworkTablesDiagnostic();
    }

    public static boolean runVisionNetworkTablesDiagnostic() {
        System.out.println(ANSI_BLUE + "STARTING VISION NET TABLES DIAGNOSTIC");
        System.out.println(ANSI_BLUE + "VisionNetworkTableCommunication Initialized");
        VisionNetworkTableCommunication vntc = new VisionNetworkTableCommunication();
        System.out.println(ANSI_PURPLE + "CHECK if the nettables connection was established successfully");
        if (!vntc.isConnectionAvailable()) {
            System.out.println(ANSI_RED + "FAILED VISION NET TABLES DIAGNOSTIC");
            return false;
        } else {
            System.out.println(ANSI_GREEN + "PASSED VISION NET TABLES DIAGNOSTIC");
            return true;
        }
    }
}
