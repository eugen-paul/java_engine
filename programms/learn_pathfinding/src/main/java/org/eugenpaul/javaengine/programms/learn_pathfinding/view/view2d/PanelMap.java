package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PanelMap extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 3518239133683265379L;

  private int grid[][] = null;

  public void setGrid(int grid[][]) {
    this.grid = grid;

    revalidate();
    repaint();
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
        switch (MapElements.fromInt(grid[x][y])) {
        case NOPE:
          color = Color.LIGHT_GRAY;
          break;
        case END:
          color = Color.BLUE;
          break;
        case MUD:
          color = Color.ORANGE;
          break;
        case WALL:
          color = Color.BLACK;
          break;
        case START:
          color = Color.GREEN;
          break;
        case STEP:
          color = Color.CYAN;
          break;
        }

        g.setColor(color);
        g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
      }
    }
  }

}
