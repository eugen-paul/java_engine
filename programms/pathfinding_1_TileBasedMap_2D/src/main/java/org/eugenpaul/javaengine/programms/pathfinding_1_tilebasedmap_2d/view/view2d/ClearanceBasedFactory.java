package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.ClearanceMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapRepresentation;
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

  private TileBasedPanelControl controlPanel;

  private ClearanceMapPanel settingPanel;

  private ClearanceMap map;
  private int x = 50;
  private int y = 50;

  private boolean paramPrintValue = false;

  public ClearanceBasedFactory(DefaultController controller) {
    super(NAME);

    paintPanel = new TileBasedPanelMap();
    paintPanel.setParam(TileBasedPanelMap.PARAM_PRINT_VALUE, paramPrintValue);

    controlPanel = new TileBasedPanelControl(controller);

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
    map = new ClearanceMap(x, y);
    return map;
  }

  public void setMapParameter(int x, int y, boolean printValue) {
    this.x = x;
    this.y = y;
    paintPanel.setParam(TileBasedPanelMap.PARAM_PRINT_VALUE, printValue);
  }
}
