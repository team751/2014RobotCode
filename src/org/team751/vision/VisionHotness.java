/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import org.team751.vision.utils.TargetReport;

/**
 *
 * @author sambaumgarten
 */
public class VisionHotness {
    public boolean isHotGoal(TargetReport target) {
        return target.Hot;
    }
    
    /**
     * Takes in a report on a target and compares the scores to the defined score limits to evaluate
     * if the target is a hot target or not.
     *
     * Returns True if the target is hot. False if it is not.
     * @param target
     * @return 
     */
    public static boolean hotOrNot(TargetReport target) {
        boolean isHot = true;	
        isHot &= target.tapeWidthScore >= VisionConstants.TAPE_WIDTH_LIMIT;
        isHot &= target.verticalScore >= VisionConstants.VERTICAL_SCORE_LIMIT;
        isHot &= (target.leftScore > VisionConstants.LR_SCORE_LIMIT) | (target.rightScore > VisionConstants.LR_SCORE_LIMIT);	
        return isHot;
    }
    
    public static boolean leftHot(TargetReport target) {
        return target.leftScore > target.rightScore;
    }
}
