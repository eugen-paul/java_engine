package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.SwingUtilities;

import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.GridElement;

/**
 * Panel to print map
 */
public class TileBasedPanelMap extends APaintPanel {

  private static final long serialVersionUID = 3518239133683265379L;

  public static final String PARAM_PRINT_VALUE = "PRINT_VALUE";

  private GridElement[][] grid = null;
  private boolean visibleValue = false;

  @Override
  public void setGrid(GridElement[][] grid) {
    SwingUtilities.invokeLater(() -> {
      setDataToPain(grid);
      revalidate();
      repaint();
    });
  }

  @Override
  public void setParam(String key, String value) {
    // NIX
  }

  @Override
  public void setParam(String key, boolean value) {
    if (key.equals(PARAM_PRINT_VALUE)) {
      SwingUtilities.invokeLater(() -> {
        visibleValue = value;
        revalidate();
        repaint();
      });
    }
  }

  @Override
  public void setParam(String key, int value) {
    // NIX
  }

  @Override
  public Immutable3dTilePoint mouseToWorld(int panelX, int panelY) {
    if (null == grid) {
      return null;
    }

    int mapSizeX = grid.length;
    int mapSizeY = grid[0].length;

    int blockWidth = getWidth() / mapSizeX;
    int blockHeight = getHeight() / mapSizeY;

    int blockX = panelX / blockWidth;
    int blockY = panelY / blockHeight;

    if (blockX < 0) {
      blockX = 0;
    }

    if (blockY < 0) {
      blockY = 0;
    }

    if (blockX >= mapSizeX) {
      blockX = mapSizeX - 1;
    }
    if (blockY >= mapSizeY) {
      blockY = mapSizeY - 1;
    }

    return new Immutable3dTilePoint(blockX, blockY, 0);
  }

  private void setDataToPain(GridElement[][] grid) {
    this.grid = grid;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (grid == null) {
      return;
    }

    int blockWidth = getWidth() / grid.length;
    int blockHeight = getHeight() / grid[0].length;

    Color color = Color.YELLOW;

    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[0].length; y++) {
        switch (grid[x][y].getMapElement()) {
        case NOPE:
          color = Color.WHITE;
          break;
        case END:
          color = Color.BLUE;
          break;
        case MUD:
          color = Color.DARK_GRAY;
          break;
        case WALL:
          color = Color.BLACK;
          break;
        case START:
          color = Color.GREEN;
          break;
        case WAY:
          color = Color.CYAN;
          break;
        case STEP_OLD:
          color = Color.LIGHT_GRAY;
          break;
        case STEP_NEW:
          color = Color.ORANGE;
          break;
        case STEP_CHECKPOINT:
          color = Color.RED;
          break;
        case STEP_TO_CHECK:
          color = Color.GRAY;
          break;
        }

        g.setColor(color);
        g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
        g.setColor(Color.BLACK);
        if (visibleValue) {
          g.drawString(Integer.toString(grid[x][y].getClearanceValue()), x * blockWidth, (y + 1) * blockHeight);
        }
      }
    }
  }

}
