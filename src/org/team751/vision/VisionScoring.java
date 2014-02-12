/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import org.team751.utils.Logger;
import org.team751.vision.utils.Rect;
import org.team751.vision.utils.Scores;

/**
 *
 * @author sambaumgarten
 */
public class VisionScoring {
    public static Rect[] getRectsFromArray(NumberArray rectanglesArray) {
        int numOfRects = rectanglesArray.size() / 8;
        Rect rects[] = new Rect[numOfRects];
        
        for (int i = 0; i < numOfRects; i++) {
            rects[i] = new Rect(rectanglesArray, i);
        }
        
        // Returns an array of arrays.  Each array store the x and y value of the rect
        return rects;
    }
    
    public static double getCenterMass(Rect rectangle, boolean getY) {
        int modifier;
        modifier = getY ? 1 : 0;
        double val = 0;
        
        for (int i = 0; i < 4; i++) {
            val += rectangle.points[i * 2 + modifier];
        }
        
        return val / 4;
    }
    
    public static void populateRectangleBounds(Rect rectangle) {
        rectangle.bbLeft = 1000;
        rectangle.bbRight = 0;
        rectangle.bbTop = 0;
        rectangle.bbBottom = 1000;
        
        for (int i = 0; i < 4; i++) {
            if (rectangle.x[i] > rectangle.bbRight)
                rectangle.bbRight = rectangle.x[i];
            if (rectangle.x[i] < rectangle.bbLeft)
                rectangle.bbLeft = rectangle.x[i];
            if (rectangle.y[i] > rectangle.bbTop)
                rectangle.bbTop = rectangle.y[i];
            if (rectangle.y[i] < rectangle.bbBottom)
                rectangle.bbBottom = rectangle.y[i];
        }
        
        rectangle.bbWidth = rectangle.bbRight - rectangle.bbLeft;
        rectangle.bbHeight = rectangle.bbTop - rectangle.bbBottom;
    }
    
    public static double getRectangleLength(Rect rectangle, boolean shortSide) {
        // assume 4 coordinates are in order
        double lengths[] = new double[4];
        double averageLength = 0.0;
        
        for (int i = 0; i < 4; i++) {
            double firstX = rectangle.x[(i) % 4];
            double firstY = rectangle.y[(i) % 4];
            double secondX = rectangle.x[(i + 1) % 4];
            double secondY = rectangle.y[(i + 1) % 4];
            lengths[i] = Math.sqrt((firstX - secondX)*(firstX - secondX) + (firstY - secondY) * (firstY - secondY));
            averageLength += lengths[i];
        }
        
        averageLength /= 4.0;
        
        if (shortSide) {
            if (lengths[0] < averageLength) {
                return (lengths[0] + lengths[2]) / 2.0;
            }
            else {
                return (lengths[1] + lengths[3]) / 2.0;
            }
        }
        else {
            if (lengths[0] > averageLength) {
                return (lengths[0] + lengths[2]) / 2.0;
            }
            else {
                return (lengths[1] + lengths[3]) / 2.0;
            }
        }
        
    }
    
    public static void populateRectangleSizes(Rect rectangle) {
        populateRectangleBounds(rectangle);
        rectangle.rectLong = getRectangleLength(rectangle, false);
        rectangle.rectShort = getRectangleLength(rectangle, true);        
        rectangle.center_mass_x = getCenterMass(rectangle, false);
        rectangle.center_mass_y = getCenterMass(rectangle, true);
        
        if (VisionConstants.DEBUG_LEVEL >= 4) {
            System.out.print("rect values:");
            for (int i = 0; i < 8; i++) {
                System.out.print(" " + rectangle.points[i]);
            }
            Logger.staticPrintln("");
            System.out.print("x values: ");
            for (int i = 0; i < 4; i++) {
                System.out.print(" " + rectangle.x[i]);
            }
            Logger.staticPrintln("");
            System.out.print("y values: ");
            for (int i = 0; i < 4; i++) {
                System.out.print(" " + rectangle.y[i]);
            }
        }
    }
    
    public static double getRectangleSize(Rect rectangle, boolean height) {
        // We want to average the top values and bottom values (and left/right respectively)
        // This won't work if the image is *too* tilted
        double lowSum = 0;
        double highSum = 0;
        double sum = 0;
        
        int modifier = height ? 1 : 0;
        
        // cycle through the 4 points in the "rectangle"
        for (int i = 0; i < 4; i++) {
            sum += rectangle.points[i * 2 + modifier];
        }
        
        for (int i = 0; i < 4; i++) {
            double val = rectangle.points[i * 2 + modifier];
            if (val < sum / 4) {
                lowSum += val;
            }
            else {
                highSum += val;
            }
        }
        return (highSum - lowSum) / 2;
    }
    
    /**
     * Computes a score (0-100) comparing the aspect ratio to the ideal aspect ratio for the target. This method uses
     * the equivalent rectangle sides to determine aspect ratio as it performs better as the target gets skewed by moving
     * to the left or right. The equivalent rectangle is the rectangle with sides x and y where particle area= x*y
     * and particle perimeter= 2x+2y
     * 
     * @param image The image containing the particle to score, needed to perform additional measurements
     * @param report The Particle Analysis Report for the particle, used for the width, height, and particle number
     * @param outer	Indicates whether the particle aspect ratio should be compared to the ratio for the inner target or the outer
     * @return The aspect ratio score (0-100)
     */
    public static double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean vertical) throws NIVisionException {
        double rectLong, rectShort, aspectRatio, idealAspectRatio;
        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = vertical ? (4.0/32) : (23.5/4);	//Vertical reflector 4" wide x 32" tall, horizontal 23.5" wide x 4" tall
	
        //Divide width by height to measure aspect ratio
        if (report.boundingRectWidth > report.boundingRectHeight) {
            //particle is wider than it is tall, divide long by short
            aspectRatio = ratioToScore((rectLong/rectShort)/idealAspectRatio);
        } else {
            //particle is taller than it is wide, divide short by long
            aspectRatio = ratioToScore((rectShort/rectLong)/idealAspectRatio);
        }
	return aspectRatio;
    }
    
    // Simplified implementation for NetTable processing
    public static double scoreAspectRatio(Rect rectangle, boolean vertical) {
        double aspectRatio, idealAspectRatio;
        idealAspectRatio = vertical ? (4.0/32) : (23.5/4);	//Vertical reflector 4" wide x 32" tall, horizontal 23.5" wide x 4" tall
	
        if (VisionConstants.DEBUG_LEVEL >= 4)
            Logger.staticPrintln("width, height: " + rectangle.bbWidth + " " + rectangle.bbHeight);
        if (VisionConstants.DEBUG_LEVEL >= 4)
            Logger.staticPrintln("long, short: " + rectangle.rectLong + " " + rectangle.rectShort);
                        
        //Divide width by height to measure aspect ratio
        if(rectangle.bbWidth > rectangle.bbHeight) {
            aspectRatio = ratioToScore((rectangle.rectLong/rectangle.rectShort)/idealAspectRatio);
        } else {
            aspectRatio = ratioToScore((rectangle.rectShort/rectangle.rectLong)/idealAspectRatio);
        }
	return aspectRatio;
    }
    
    /**
     * Compares scores to defined limits and returns true if the particle appears to be a target
     * 
     * @param scores The structure containing the scores to compare
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * 
     * @return True if the particle meets all limits, false otherwise
     */
    public static boolean scoreCompare(Scores scores, boolean vertical) {
	boolean isTarget = true;
	isTarget &= scores.rectangularity > VisionConstants.RECTANGULARITY_LIMIT;
	if(vertical) {
            isTarget &= scores.aspectRatioVertical > VisionConstants.ASPECT_RATIO_LIMIT;
	} else {
            isTarget &= scores.aspectRatioHorizontal > VisionConstants.ASPECT_RATIO_LIMIT;
	}
	return isTarget;
    }
    
    /**
     * Computes a score (0-100) estimating how rectangular the particle is by comparing the area of the particle
     * to the area of the bounding box surrounding it. A perfect rectangle would cover the entire bounding box.
     * 
     * @param report The Particle Analysis Report for the particle to score
     * @return The rectangularity score (0-100)
     */
    public static double scoreRectangularity(ParticleAnalysisReport report) {
        if (report.boundingRectWidth*report.boundingRectHeight !=0) {
            return 100*report.particleArea/(report.boundingRectWidth*report.boundingRectHeight);
        } else {
            return 0;
        }	
    }
    public double scoreRectangularity(double particleArea, double boundingRectWidth, double boundingRectHeight) {
        if (boundingRectWidth*boundingRectHeight !=0) {
            return 100*particleArea/(boundingRectWidth*boundingRectHeight);
        } else {
            return 0;
        }	
    }
    
    /**
     * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
     * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
     */
    public static double ratioToScore(double ratio) {
        return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
    }
}
