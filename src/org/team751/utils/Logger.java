/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author sambaumgarten
 */
public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public void catchError(Throwable e) {
        println(e.getClass().toString() + " :: " + e.getMessage(), ANSI_RED);
        if (getDebugLevel() <= 1) {
            e.printStackTrace();
        }
    }

    public void println(String text, String color) {
        System.out.println(color + text);
    }
    
    public void println(String text) {
        System.out.println(text);
    }
    
    public static void staticPrintln(String text, String color) {
        System.out.println(color + text);
    }
    
    public static void staticPrintln(String text) {
        System.out.println(text);
    }

    public double getDebugLevel() {
        return SmartDashboard.getNumber("debugLevel", 1.0);
    }
}
