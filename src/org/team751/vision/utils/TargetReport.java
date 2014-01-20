/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team751.vision.utils;

/**
 *
 * @author sambaumgarten
 */
public class TargetReport {
  public int verticalIndex;
  public int horizontalIndex;
  public boolean Hot;
  public boolean leftHot; // is hot target to left of vertical target
  public double totalScore;
  public double leftScore;
  public double rightScore;
  public double tapeWidthScore;
  public double verticalScore;
  public double distance;
  public double angle;
};
