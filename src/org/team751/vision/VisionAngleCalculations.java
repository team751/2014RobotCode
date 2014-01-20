/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import org.team751.vision.utils.Rect;
import org.team751.vision.utils.TargetReport;

/**
 *
 * @author sambaumgarten
 */
public class VisionAngleCalculations {
    public static double getAngleToGoal(TargetReport target) {
        return target.angle;
    }
    
    public static double computeAngle(Rect verticalRect) {
        return VisionConstants.HORIZONTAL_VIEW_ANGLE * (VisionConstants.X_IMAGE_RES / 2 - verticalRect.center_mass_x) / (VisionConstants.X_IMAGE_RES);
    }
}
