package org.team751.vision.utils;

import edu.wpi.first.wpilibj.networktables2.type.NumberArray;

/**
 *
 * @author sambaumgarten
 */
public class Rect {
    // Store some information about the rectangle
    public double points[] = new double[8];
    public double x[] = new double[4];
    public double y[] = new double[4];
    public double bbWidth;
    public double bbHeight;
    public double bbLeft;
    public double bbRight;
    public double bbTop;
    public double bbBottom;
    public double rectLong;
    public double rectShort;
    public double center_mass_x;
    public double center_mass_y;
    
    public Rect(NumberArray numberArray, int index) {
        for (int i = 0; i < 8; i++) {
            points[i] = numberArray.get(index * 8 + i);
        }
        for (int i = 0; i < 4; i++) {
            x[i] = numberArray.get(index * 8 + i * 2);
        }
        for (int i = 0; i < 4; i++) {
            y[i] = numberArray.get(index * 8 + i * 2 + 1);
        }
    }
}
