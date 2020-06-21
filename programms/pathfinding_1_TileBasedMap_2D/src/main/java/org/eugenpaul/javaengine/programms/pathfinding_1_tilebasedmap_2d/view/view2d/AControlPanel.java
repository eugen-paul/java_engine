package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import javax.swing.JPanel;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

/**
 * ControlPanel Interface
 * 
 * @author Eugen Paul
 *
 */
public abstract class AControlPanel extends JPanel {
  private static final long serialVersionUID = 1216323605528379561L;

  public abstract MapElements getSelector();
}
