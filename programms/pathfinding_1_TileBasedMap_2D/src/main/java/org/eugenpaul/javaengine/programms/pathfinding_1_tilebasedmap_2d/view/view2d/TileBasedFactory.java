package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapMover;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapRepresentation;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.tile.TileBasedMoverTyp;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.tile.TileMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.settings.TileBasedPanel;

/**
 * Factory to create TileBased-View-Components
 * 
 * @author Eugen Paul
 *
 */
public class TileBasedFactory extends AlgoFactory {

  private static final String NAME = "TileBased";

  private TileBasedPanelMap paintPanel;

  private TileBasedPanelControl controlPanel;

  private TileBasedPanel settingPanel;

  private TileMap map;
  private int x = 50;
  private int y = 50;

  public TileBasedFactory(DefaultController controller) {
    super(NAME);

    paintPanel = new TileBasedPanelMap();
    paintPanel.setParam(TileBasedPanelMap.PARAM_PRINT_VALUE, false);

    controlPanel = new TileBasedPanelControl(controller);
    controlPanel.init(TileBasedMoverTyp.values(), 0);

    settingPanel = new TileBasedPanel(this);
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
    map = new TileMap(x, y);
    return map;
  }

  public void setMapParameter(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public IMapMover getDefaultMapMover() {
    return TileBasedMoverTyp.values()[0];
  }

}
