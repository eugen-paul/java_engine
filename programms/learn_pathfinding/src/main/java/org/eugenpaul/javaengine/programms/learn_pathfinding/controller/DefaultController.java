package org.eugenpaul.javaengine.programms.learn_pathfinding.controller;

import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.WorldElements;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d.MapElements;

public class DefaultController extends AbstractController {

  public static final String ELEMENT_MAP = "MAP";

  public void changeElementMap(int map[][]) {
    setModelProperty(ELEMENT_MAP, map);
  }

  public void setPointOnMap(int x, int y, MapElements value) {
    switch (value) {
    case START:
      world.setStart(x, y);
      break;
    case END:
      world.setEnd(x, y);
      break;
    case NOPE:
      world.setPosition(x, y, WorldElements.NOPE);
      break;
    case MUD:
      world.setPosition(x, y, WorldElements.DIRT);
      break;
    case WALL:
      world.setPosition(x, y, WorldElements.WALL);
      break;
    default:
      break;
    }
  }
  
  public void setMover(MoverTyp mover) {
    world.setMover(mover);
  }

}
