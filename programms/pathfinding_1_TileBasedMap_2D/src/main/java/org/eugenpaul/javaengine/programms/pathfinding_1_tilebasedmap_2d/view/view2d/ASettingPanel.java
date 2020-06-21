package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import javax.swing.JPanel;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;

/**
 * PaintPanel Interface.
 * 
 * @author Eugen Paul
 *
 */
public abstract class ASettingPanel extends JPanel {
  private static final long serialVersionUID = -4892073402607341975L;

  public abstract void clickOk(DefaultController controller);
}
