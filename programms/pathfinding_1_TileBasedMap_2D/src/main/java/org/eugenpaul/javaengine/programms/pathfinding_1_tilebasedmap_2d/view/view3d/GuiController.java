package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view3d;

import java.beans.PropertyChangeEvent;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.tile.TileBasedMoverTyp;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.AbstractViewPanel;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;

/**
 * GUI
 * 
 * @author Eugen Paul
 *
 */
public class GuiController implements AbstractViewPanel {

  private final MainApplication app;
  private final DefaultController controller;

  private DropDown<TileBasedMoverTyp> moverList;
  private DropDown<PathfindingAlgo> algoList;

  private Label debugText = null;

  private Button doStep;
  private boolean doStepStatus;

  private Button startSearch;
  private boolean startSearchStatus;

  private Button stopSearch;
  private boolean stopSearchStatus;

  private Button resetSearch;
  private boolean resetSearchStatus;

  private TextField msTextField;

  private Checkbox autoPathfinding;

  /**
   * C'tor. Element will be added to controller.
   * 
   * @param controller
   * @param app
   */
  public GuiController(DefaultController controller, MainApplication app) {
    this.app = app;
    this.controller = controller;
  }

  @SuppressWarnings({ "unchecked" })
  public void init() {
    // Create a simple container for our elements
    Container myWindow = new Container();
    app.getGuiNode().attachChild(myWindow);

    debugText = new Label("");

    // Put it somewhere that we will see it.
    // Note: Lemur GUI elements grow down from the upper left corner.
    myWindow.setLocalTranslation(0, app.getContext().getSettings().getHeight(), 0);

    // Add some elements
    Button startButton = myWindow.addChild(new Button("Set Start"));
    startButton.addClickCommands(source -> startButtonClick());

    Button stopButton = myWindow.addChild(new Button("Set Stop"));
    stopButton.addClickCommands(source -> stopButtonClick());

    Button wallButton = myWindow.addChild(new Button("Set Wall"));
    wallButton.addClickCommands(source -> wallButtonClick());

    Button pitButton = myWindow.addChild(new Button("Set Pit"));
    pitButton.addClickCommands(source -> pitButtonClick());

    Button clearButton = myWindow.addChild(new Button("Clear point"));
    clearButton.addClickCommands(source -> clearButtonClick());

    Label moverLabel = new Label("Walker");
    myWindow.addChild(moverLabel);

    moverList = new DropDown<>();
    for (TileBasedMoverTyp typ : TileBasedMoverTyp.values()) {
      moverList.getModel().add(typ);
    }
    moverList.getSelectionModel().setSelection(0);
    myWindow.addChild(moverList);

    moverList.addClickCommands(source -> moverChange());

    Label algoLabel = new Label("Algorithm");
    myWindow.addChild(algoLabel);

    algoList = new DropDown<>();
    for (PathfindingAlgo typ : PathfindingAlgo.values()) {
      algoList.getModel().add(typ);
    }
    algoList.getSelectionModel().setSelection(0);
    myWindow.addChild(algoList);

    algoList.addClickCommands(source -> algoChange());

    autoPathfinding = myWindow.addChild(new Checkbox("Auto Searching"));
    autoPathfinding.setChecked(true);

    autoPathfinding.addClickCommands(button -> {
      Checkbox check = (Checkbox) button;
      setAutoPathfinding(check.isChecked());
    });

    doStep = myWindow.addChild(new Button("do step"));
    doStep.setEnabled(false);
    doStepStatus = false;
    doStep.addClickCommands(source -> doStepButtonClick());

    startSearch = myWindow.addChild(new Button("Start search"));
    startSearch.setEnabled(false);
    startSearchStatus = false;
    startSearch.addClickCommands(source -> doStartButtonClick());

    stopSearch = myWindow.addChild(new Button("Stop search"));
    stopSearch.setEnabled(false);
    stopSearchStatus = false;
    stopSearch.addClickCommands(source -> doStopButtonClick());

    Label msPerStep = new Label("Milliseconds per step");
    myWindow.addChild(msPerStep);

    msTextField = new TextField("");
    msTextField.setPreferredLineCount(1);
    msTextField.setPreferredWidth(150);
    msTextField.setText("100");
    myWindow.addChild(msTextField);

    resetSearch = myWindow.addChild(new Button("Reset search"));
    resetSearch.setEnabled(false);
    resetSearch.addClickCommands(source -> doResetButtonClick());
  }

  public void startButtonClick() {
    app.clickElement = MapElements.START;
  }

  public void stopButtonClick() {
    app.clickElement = MapElements.END;
  }

  public void wallButtonClick() {
    app.clickElement = MapElements.WALL;
  }

  public void pitButtonClick() {
    app.clickElement = MapElements.MUD;
  }

  public void clearButtonClick() {
    app.clickElement = MapElements.NOPE;
  }

  public void moverSelectorChange(TileBasedMoverTyp mover) {
    if (null != mover) {
      controller.setMover(mover);
    }
  }

  public void update() {
    doStep.setEnabled(doStepStatus);
    startSearch.setEnabled(startSearchStatus);
    stopSearch.setEnabled(stopSearchStatus);
    resetSearch.setEnabled(resetSearchStatus);
  }

  private void moverChange() {
    Integer selection = moverList.getSelectionModel().getSelection();
    if (selection != null) {
      TileBasedMoverTyp mover = moverList.getModel().get(selection);
      controller.setMover(mover);
    }
  }

  private void algoChange() {
    Integer selection = algoList.getSelectionModel().getSelection();
    if (selection != null) {
      PathfindingAlgo algo = algoList.getModel().get(selection);
      controller.setPathfindingAlgo(algo);
    }
  }

  private void setAutoPathfinding(boolean checked) {
    controller.setAutoPathfinding(checked);

    if (checked) {
      doStepStatus = false;
      startSearchStatus = false;
      stopSearchStatus = false;
      resetSearchStatus = false;
    } else {
      doStepStatus = true;
      startSearchStatus = true;
      stopSearchStatus = true;
      resetSearchStatus = true;
    }
  }

  private void doStepButtonClick() {
    controller.doPathfindingStep();
  }

  private void doStartButtonClick() {

    try {
      Integer ms = Integer.parseInt(msTextField.getText());
      controller.startPathfinding(ms.intValue());
    } catch (NumberFormatException e) {
      controller.startPathfinding(20);
    }

  }

  private void doStopButtonClick() {
    controller.stopPathfinding();
  }

  private void doResetButtonClick() {
    controller.resetPathfinding();
  }

  public void textFieldFocus(String text) {
//    System.out.println(text);
  }

  @Override
  public void modelPropertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(DefaultController.ELEMENT_DEBUG_INFO)) {
      if (null != debugText) {
        String debugInfo = (String) evt.getNewValue();
        debugText.setText(debugInfo);
      }
    } else if (evt.getPropertyName().equals(DefaultController.ELEMENT_PATHWAYFINDING_RUNNING)) {
      if (!autoPathfinding.isChecked()) {
        Boolean runnung = (Boolean) evt.getNewValue();
        if (runnung.booleanValue()) {
          startSearchStatus = false;
          stopSearchStatus = true;
        } else {
          startSearchStatus = true;
          stopSearchStatus = false;
        }
      } else {
        startSearchStatus = false;
        stopSearchStatus = false;
      }
    }
  }

}
