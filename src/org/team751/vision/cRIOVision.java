/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;

/**
 *
 * @author sambaumgarten
 */
public class cRIOVision {
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    
    public cRIOVision() {
        camera = AxisCamera.getInstance();  // get an instance of the camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, VisionConstants.AREA_MINIMUM, 65535, false);
    }
    
    public NumberArray getRectangles() {
        final NumberArray targetArray = new NumberArray();
        // TODO: Implement with 2014VisionSampleProject
        return targetArray;
    }
}
