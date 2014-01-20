/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import org.team751.base.Robot;
import org.team751.vision.utils.Rect;
import org.team751.vision.utils.Scores;
import org.team751.vision.utils.TargetReport;

/**
 *
 * @author sambaumgarten
 */
public class VisionDistanceCalculations {
    public static final int cRIOVision = 0;
    public static final int RoboRealmVision = 1;
    private static CriteriaCollection cc;
    private static AxisCamera camera;          // the axis camera object (connected to the switch)
    private static int visionType = -1;
    private static VisionNetworkTableCommunication vntc = new VisionNetworkTableCommunication();
    
    public VisionDistanceCalculations(int visionTypeTemp) {
        visionType = visionTypeTemp;
        if (visionType == cRIOVision) {
            camera = AxisCamera.getInstance();  // get an instance of the camera
        }
    }
    
    public static double getLateralDistanceToGoal(TargetReport target) {
        return Math.tan(target.angle * VisionConstants.PI / 180) * target.distance;
    }
    
    public static double getDistanceToGoal() {
        // TODO: Cleanup if statements
        if (visionType == -1) {
            return getDistanceToGoalFromNetworkTables();
        } else if (visionType == cRIOVision) {
            return getDistanceToGoalFromCRIO();
        } else {
            return getDistanceToGoalFromNetworkTables();
        }
    }
    
    private static double getDistanceToGoalFromNetworkTables() {
        NumberArray rectanglesArray = VisionNetworkTableCommunication.getRectangles();
            if (VisionConstants.DEBUG_LEVEL >= 3)
                System.out.println("Number of points in array: " + rectanglesArray.size());
            
            TargetReport target = new TargetReport();
            if (rectanglesArray.size() > 0) {
                if (rectanglesArray.size() % 8 != 0) {
                    System.out.println("Error: Number of points in array not divisible by 8!");
                }
                else {
                    int verticalTargets[] = new int[VisionConstants.MAX_PARTICLES];
                    int horizontalTargets[] = new int[VisionConstants.MAX_PARTICLES];
                    int verticalTargetCount, horizontalTargetCount;
                    
                    //iterate through each particle and score to see if it is a target
                    Rect rectangles[] = VisionScoring.getRectsFromArray(rectanglesArray);
                    int numParticles = rectangles.length;
                    Scores scores[] = new Scores[numParticles];
                    horizontalTargetCount = verticalTargetCount = 0;
                    
                    for (int i = 0; i < VisionConstants.MAX_PARTICLES && i < numParticles; i++) {
                        VisionScoring.populateRectangleSizes(rectangles[i]);
                        scores[i] = new Scores();
                        //Score each particle on rectangularity and aspect ratio
                        // set rectangularity to 1.0 for now since we don't have this image/data from NetTable
                        scores[i].rectangularity = 100; //scoreRectangularity(bbWidth, bbHeight);
                        scores[i].aspectRatioVertical = VisionScoring.scoreAspectRatio(rectangles[i], true);
                        scores[i].aspectRatioHorizontal = VisionScoring.scoreAspectRatio(rectangles[i], false);
                        if (VisionConstants.DEBUG_LEVEL >= 3)
                            System.out.println("AspectRatios: " + scores[i].aspectRatioVertical + " " + scores[i].aspectRatioHorizontal);
                        //Check if the particle is a horizontal target, if not, check if it's a vertical target
                        if(VisionScoring.scoreCompare(scores[i], false))
                        {
                            if (VisionConstants.DEBUG_LEVEL >= 3)
                                System.out.println("particle: " + i + "is a Horizontal Target centerX: " + rectangles[i].center_mass_x + "centerY: " + rectangles[i].center_mass_y);
                            horizontalTargets[horizontalTargetCount++] = i; //Add particle to target array and increment count
                        } else if (VisionScoring.scoreCompare(scores[i], true)) {
                            if (VisionConstants.DEBUG_LEVEL >= 3)
                                System.out.println("particle: " + i + "is a Vertical Target centerX: " + rectangles[i].center_mass_x + "centerY: " + rectangles[i].center_mass_y);
                            verticalTargets[verticalTargetCount++] = i;  //Add particle to target array and increment count
                        } else {
                            if (VisionConstants.DEBUG_LEVEL >= 3)
                                System.out.println("particle: " + i + "is not a Target centerX: " + rectangles[i].center_mass_x + "centerY: " + rectangles[i].center_mass_y);
                        }
                            if (VisionConstants.DEBUG_LEVEL >= 3)
                                System.out.println("rect: " + scores[i].rectangularity + "ARHoriz: " + scores[i].aspectRatioHorizontal);
                            if (VisionConstants.DEBUG_LEVEL >= 3)
                                System.out.println("ARVert: " + scores[i].aspectRatioVertical);	
                        }
                        //Zero out scores and set verticalIndex to first target in case there are no horizontal targets
                        target.totalScore = target.leftScore = target.rightScore = target.tapeWidthScore = target.verticalScore = 0;
                        target.verticalIndex = verticalTargets[0];
                        for (int i = 0; i < verticalTargetCount; i++)
                        {
                                Rect verticalRect = rectangles[verticalTargets[i]];
                                for (int j = 0; j < horizontalTargetCount; j++)
                                {
                                    Rect horizontalRect = rectangles[horizontalTargets[j]];
                                    double horizWidth, horizHeight, vertWidth, leftScore, rightScore, tapeWidthScore, verticalScore, total;
                                    //Measure equivalent rectangle sides for use in score calculation
                                    horizWidth = horizontalRect.rectLong;
                                    vertWidth = verticalRect.rectShort;
                                    horizHeight = horizontalRect.rectShort;
                                    //Determine if the horizontal target is in the expected location to the left of the vertical target
                                    leftScore = VisionScoring.ratioToScore(1.2*(verticalRect.bbLeft - horizontalRect.center_mass_x)/horizWidth);
                                    //Determine if the horizontal target is in the expected location to the right of the  vertical target
                                    rightScore = VisionScoring.ratioToScore(1.2*(horizontalRect.center_mass_x - verticalRect.bbLeft - verticalRect.bbWidth)/horizWidth);
                                    //Determine if the width of the tape on the two targets appears to be the same
                                    tapeWidthScore = VisionScoring.ratioToScore(vertWidth/horizHeight);
                                    //Determine if the vertical location of the horizontal target appears to be correct
                                    verticalScore = VisionScoring.ratioToScore(1-(verticalRect.bbTop - horizontalRect.center_mass_y)/(4*horizHeight));
                                    total = leftScore > rightScore ? leftScore:rightScore;
                                    total += tapeWidthScore + verticalScore;
                                    //If the target is the best detected so far store the information about it
                                    if(total > target.totalScore)
                                    {
                                            target.horizontalIndex = horizontalTargets[j];
                                            target.verticalIndex = verticalTargets[i];
                                            target.totalScore = total;
                                            target.leftScore = leftScore;
                                            target.rightScore = rightScore;
                                            target.tapeWidthScore = tapeWidthScore;
                                            target.verticalScore = verticalScore;
                                    }
                                }
                                //Determine if the best target is a Hot target
                                target.Hot = VisionHotness.hotOrNot(target);
                                target.leftHot = VisionHotness.leftHot(target);
                                Robot.lastTarget = target;
                            }
                            if(verticalTargetCount > 0)
                            {
                                    //Information about the target is contained in the "target" structure
                                    //To get measurement information such as sizes or locations use the
                                    //horizontal or vertical index to get the particle report as shown below
                                    target.distance = computeDistance(rectangles[target.verticalIndex]);
                                    if (VisionConstants.DEBUG_LEVEL >= 2) {
                                    if(target.Hot)
                                    {
                                            System.out.println("Hot target located");
                                            System.out.println("Distance: " + target.distance);
                                            System.out.println("LeftHot?: " + target.leftHot);
                                    } else {
                                            System.out.println("No hot target present");
                                            System.out.println("Distance: " + target.distance);
                                    }
                                    }
                                    
                                    target.angle = VisionAngleCalculations.computeAngle(rectangles[target.verticalIndex]);
                                    vntc.setVisionDistance(target.distance);
                                    vntc.setVisionAngle(target.angle);
                                    if (VisionConstants.DEBUG_LEVEL >= 2) {
                                        System.out.println("Angle: " + target.angle);
                                        System.out.println("Lateral Distance: " + Math.tan(target.angle) * target.distance);
                                    }
                            }
                        
                }
            }
            return target.distance;
    }
    
    private static double getDistanceToGoalFromCRIO() {
        TargetReport target = new TargetReport();
        int verticalTargets[] = new int[VisionConstants.MAX_PARTICLES];
        int horizontalTargets[] = new int[VisionConstants.MAX_PARTICLES];
        int verticalTargetCount, horizontalTargetCount;
        double distance = -1.0;

        try {
            /**
             * Do the image capture with the camera and apply the algorithm described above. This
             * sample will either get images from the camera or from an image file stored in the top
             * level directory in the flash memory on the cRIO. The file name in this case is "testImage.jpg"
             * 
             */
            ColorImage image = camera.getImage();     // comment if using stored images
            //ColorImage image;                           // next 2 lines read image from flash on cRIO
            //image = new RGBImage("/testImage.jpg");		// get the sample image from the cRIO flash
            BinaryImage thresholdImage = image.thresholdHSV(105, 137, 230, 255, 133, 183);   // keep only green objects
            //thresholdImage.write("/threshold.bmp");
            BinaryImage filteredImage = thresholdImage.particleFilter(cc);           // filter out small particles
            //filteredImage.write("/filteredImage.bmp");
            //iterate through each particle and score to see if it is a target
            Scores scores[] = new Scores[filteredImage.getNumberParticles()];
            horizontalTargetCount = verticalTargetCount = 0;
            if (filteredImage.getNumberParticles() > 0) {
              for (int i = 0; i < VisionConstants.MAX_PARTICLES && i < filteredImage.getNumberParticles(); i++) {
                ParticleAnalysisReport report = filteredImage.getParticleAnalysisReport(i);
                scores[i] = new Scores();
                //Score each particle on rectangularity and aspect ratio
                scores[i].rectangularity = VisionScoring.scoreRectangularity(report);
                scores[i].aspectRatioVertical = VisionScoring.scoreAspectRatio(filteredImage, report, i, true);
                scores[i].aspectRatioHorizontal = VisionScoring.scoreAspectRatio(filteredImage, report, i, false);			
                //Check if the particle is a horizontal target, if not, check if it's a vertical target
                if (VisionScoring.scoreCompare(scores[i], false)) {
                    System.out.println("particle: " + i + "is a Horizontal Target centerX: " + report.center_mass_x + "centerY: " + report.center_mass_y);
                    horizontalTargets[horizontalTargetCount++] = i; //Add particle to target array and increment count
                } else if (VisionScoring.scoreCompare(scores[i], true)) {
                    System.out.println("particle: " + i + "is a Vertical Target centerX: " + report.center_mass_x + "centerY: " + report.center_mass_y);
                    verticalTargets[verticalTargetCount++] = i;  //Add particle to target array and increment count
                } else {
                    System.out.println("particle: " + i + "is not a Target centerX: " + report.center_mass_x + "centerY: " + report.center_mass_y);
                }
    
                System.out.println("rect: " + scores[i].rectangularity + "ARHoriz: " + scores[i].aspectRatioHorizontal);
                System.out.println("ARVert: " + scores[i].aspectRatioVertical);	
              }
              //Zero out scores and set verticalIndex to first target in case there are no horizontal targets
              target.totalScore = target.leftScore = target.rightScore = target.tapeWidthScore = target.verticalScore = 0;
              target.verticalIndex = verticalTargets[0];
              for (int i = 0; i < verticalTargetCount; i++) {
                ParticleAnalysisReport verticalReport = filteredImage.getParticleAnalysisReport(verticalTargets[i]);
                for (int j = 0; j < horizontalTargetCount; j++) {
                  ParticleAnalysisReport horizontalReport = filteredImage.getParticleAnalysisReport(horizontalTargets[j]);
                  double horizWidth, horizHeight, vertWidth, leftScore, rightScore, tapeWidthScore, verticalScore, total;
                  //Measure equivalent rectangle sides for use in score calculation
                  horizWidth = NIVision.MeasureParticle(filteredImage.image, horizontalTargets[j], false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
                  vertWidth = NIVision.MeasureParticle(filteredImage.image, verticalTargets[i], false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
                  horizHeight = NIVision.MeasureParticle(filteredImage.image, horizontalTargets[j], false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
                  //Determine if the horizontal target is in the expected location to the left of the vertical target
                  leftScore = VisionScoring.ratioToScore(1.2*(verticalReport.boundingRectLeft - horizontalReport.center_mass_x)/horizWidth);
                  //Determine if the horizontal target is in the expected location to the right of the  vertical target
                  rightScore = VisionScoring.ratioToScore(1.2*(horizontalReport.center_mass_x - verticalReport.boundingRectLeft - verticalReport.boundingRectWidth)/horizWidth);
                  //Determine if the width of the tape on the two targets appears to be the same
                  tapeWidthScore = VisionScoring.ratioToScore(vertWidth/horizHeight);
                  //Determine if the vertical location of the horizontal target appears to be correct
                  verticalScore = VisionScoring.ratioToScore(1-(verticalReport.boundingRectTop - horizontalReport.center_mass_y)/(4*horizHeight));
                  total = leftScore > rightScore ? leftScore:rightScore;
                  total += tapeWidthScore + verticalScore;
                  //If the target is the best detected so far store the information about it
                  if (total > target.totalScore) {
                    target.horizontalIndex = horizontalTargets[j];
                    target.verticalIndex = verticalTargets[i];
                    target.totalScore = total;
                    target.leftScore = leftScore;
                    target.rightScore = rightScore;
                    target.tapeWidthScore = tapeWidthScore;
                    target.verticalScore = verticalScore;
                  }
                }
                //Determine if the best target is a Hot target
                target.Hot = VisionHotness.hotOrNot(target);
              }
              if (verticalTargetCount > 0) {
                //Information about the target is contained in the "target" structure
                //To get measurement information such as sizes or locations use the
                //horizontal or vertical index to get the particle report as shown below
                ParticleAnalysisReport distanceReport = filteredImage.getParticleAnalysisReport(target.verticalIndex);
                distance = computeDistance(filteredImage, distanceReport, target.verticalIndex);
                if(target.Hot) {
                        System.out.println("Hot target located");
                        System.out.println("Distance: " + distance);
                } else {
                        System.out.println("No hot target present");
                        System.out.println("Distance: " + distance);
                }
              }
            }
            /**
             * all images in Java must be freed after they are used since they are allocated out
             * of C data structures. Not calling free() will cause the memory to accumulate over
             * each pass of this loop.
             */
            filteredImage.free();
            thresholdImage.free();
            image.free();
          } catch (AxisCameraException ex) {        // this is needed if the camera.getImage() is called
            ex.printStackTrace();
          } catch (NIVisionException ex) {
            ex.printStackTrace();
          }
        return distance;
    }
    
    /**
     * Computes the estimated distance to a target using the height of the particle in the image. For more information and graphics
     * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
     * 
     * @param image The image to use for measuring the particle estimated rectangle
     * @param report The Particle Analysis Report for the particle
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * @return The estimated distance to the target in Inches.
     */
    static double computeDistance(BinaryImage image, ParticleAnalysisReport report, int particleNumber) throws NIVisionException {
            double rectLong, height;
            int targetHeight;
            rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
            //using the smaller of the estimated rectangle long side and the bounding rectangle height results in better performance
            //on skewed rectangles
            height = Math.min(report.boundingRectHeight, rectLong);
            targetHeight = 32;
            return VisionConstants.Y_IMAGE_RES * targetHeight / (height * 2 * Math.tan(VisionConstants.VIEW_ANGLE*Math.PI/(180*2)));
    }
    static double computeDistance(Rect verticalRect) {
            double height;
            int targetHeight;
            //using the smaller of the estimated rectangle long side and the bounding rectangle height results in better performance
            //on skewed rectangles
            height = Math.min(verticalRect.bbHeight, verticalRect.rectLong);
            targetHeight = 32;
            return VisionConstants.Y_IMAGE_RES * targetHeight / (height * 2 * Math.tan(VisionConstants.VIEW_ANGLE*Math.PI/(180*2))); // actually in inches
    }
}
