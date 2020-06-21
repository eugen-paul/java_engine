package org.eugenpaul.javaengine.core.data.statistics;

import java.util.Arrays;

import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

import lombok.Getter;
import lombok.Setter;

/**
 * Info of current state of path finding.
 * 
 * @author Eugen Paul
 *
 */
public class InfoPathfinding {
  @Getter
  @Setter
  private String stepDescription = null;

  @Getter
  @Setter
  private long stepsCount = 0;

  private InfoPathfindingMapStatus[][][] stepsmap = null;

  /**
   * C'tor
   * 
   * @param mapSize size of map
   */
  public InfoPathfinding(Immutable3dTilePoint mapSize) {
    this(mapSize.getX(), mapSize.getY(), mapSize.getZ());
  }

  /**
   * C'tor
   * 
   * @param x - x size of map
   * @param y - y size of map
   * @param z - z size of map
   */
  public InfoPathfinding(int x, int y, int z) {
    stepDescription = "";

    stepsmap = new InfoPathfindingMapStatus[x][y][z];
    resetInfo();
  }

  /**
   * reset all info
   */
  public void resetInfo() {
    if (stepsmap == null) {
      return;
    }

    stepsCount = 0;

    int xSize = stepsmap.length;
    int ySize = stepsmap[0].length;

    for (int i = 0; i < xSize; i++) {
      for (int k = 0; k < ySize; k++) {
        Arrays.fill(stepsmap[i][k], InfoPathfindingMapStatus.CLEAR);
      }
    }
  }

  /**
   * get info of current state
   * 
   * @return
   */
  public InfoPathfindingMapStatus[][][] getInfoMap() {
    return stepsmap;
  }

  /**
   * set info
   * 
   * @param coordinates
   * @param info
   */
  public void setPoint(Immutable3dTilePoint coordinates, InfoPathfindingMapStatus info) {
    setPoint(coordinates.getX(), coordinates.getY(), coordinates.getZ(), info);
  }

  /**
   * set info
   * 
   * @param x
   * @param y
   * @param z
   * @param info
   */
  public void setPoint(int x, int y, int z, InfoPathfindingMapStatus info) {
    if (stepsmap == null) {
      return;
    }

    int xSize = stepsmap.length;
    int ySize = stepsmap[0].length;
    int zSize = stepsmap[0][0].length;

    if (x < 0 || xSize <= x //
        || y < 0 || ySize <= y //
        || z < 0 || zSize <= z //
    ) {
      return;
    }

    stepsmap[x][y][z] = info;
  }

  /**
   * All info point with states "new_steps" and "check_point" will be set to old_step.
   */
  public void nextStep() {
    int xSize = stepsmap.length;
    int ySize = stepsmap[0].length;
    int zSize = stepsmap[0][0].length;

    for (int x = 0; x < xSize; x++) {
      for (int y = 0; y < ySize; y++) {
        for (int z = 0; z < zSize; z++) {
          switch (stepsmap[x][y][z]) {
          case CHECKING_POINT:
            stepsmap[x][y][z] = InfoPathfindingMapStatus.CHECKED_POINT;
            break;
          case NEW_STEP:
          case CLEAR:
          case POINT_TO_CHECK:
          case CHECKED_POINT:
            break;
          }
        }
      }
    }
  }

  /**
   * Increase number of path finding steps
   */
  public void incStepsCount() {
    stepsCount++;
  }

}
