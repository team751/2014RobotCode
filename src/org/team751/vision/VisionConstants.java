/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author sambaumgarten
 */
public class VisionConstants {        
    //Camera constants used for distance calculation
    public static final int Y_IMAGE_RES = 240;		//Y Image resolution in pixels, should be 120, 240 or 480
    public static final int X_IMAGE_RES = 320;
    public static final double VIEW_ANGLE = 49;		//Axis M1013
    public static final double HORIZONTAL_VIEW_ANGLE = 65;
    
    //final double VIEW_ANGLE = 41.7;		//Axis 206 camera
    //final double VIEW_ANGLE = 37.4;  //Axis M1011 camera
    public static final double PI = 3.141592653;
    
    //Score limits used for target identification
    public static final int  RECTANGULARITY_LIMIT = 40;
    public static final int ASPECT_RATIO_LIMIT = 55;
    
    //Score limits used for hot target determination
    public static final int TAPE_WIDTH_LIMIT = 50;
    public static final int  VERTICAL_SCORE_LIMIT = 50;
    public static final int LR_SCORE_LIMIT = 50;
    
    //Minimum area of particles to be considered
    public static final int AREA_MINIMUM = 150;
    
    //Maximum number of particles to process
    public static final int MAX_PARTICLES = 8;
    
//    public static final int DEBUG_LEVEL = Preferences.getInstance().getInt("VisionDebugLevel", 1);
    public static final int DEBUG_LEVEL = 4; // Set to max debug level
}
