package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapMover;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapRepresentation;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.clearance.ClearanceBasedMoverTyp;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.clearance.ClearanceMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.settings.ClearanceMapPanel;

/**
 * Factory to create TileBased-View-Components
 * 
 * @author Eugen Paul
 *
 */
public class ClearanceBasedFactory extends AlgoFactory {

  private static final String NAME = "ClearanceBased";

  private TileBasedPanelMap paintPanel;

  private ClearanceBasedPanelControl controlPanel;

  private ClearanceMapPanel settingPanel;

  private int x = 50;
  private int y = 50;

  private boolean paramPrintValue = false;

  public ClearanceBasedFactory(DefaultController controller) {
    super(NAME);

    paintPanel = new TileBasedPanelMap();
    paintPanel.setParam(TileBasedPanelMap.PARAM_PRINT_VALUE, paramPrintValue);

    controlPanel = new ClearanceBasedPanelControl(controller);
    controlPanel.init(ClearanceBasedMoverTyp.values(), 0);
    
    settingPanel = new ClearanceMapPanel(this);
  }

  @Override
  public APaintPanel createPaintPanel() {
    return paintPanel;
  }

  @Override
  public AControlPanel createControlPanel() {
    return controlPanel;
  }

  @Override
  public ASettingPanel createSettingsPanel() {
    return settingPanel;
  }

  @Override
  public IMapRepresentation getMap() {
    return new ClearanceMap(x, y);
  }

  public void setMapParameter(int x, int y, boolean printValue) {
    this.x = x;
    this.y = y;
    paintPanel.setParam(TileBasedPanelMap.PARAM_PRINT_VALUE, printValue);
  }

  @Override
  public IMapMover getDefaultMapMover() {
    return ClearanceBasedMoverTyp.values()[0];
  }

}
