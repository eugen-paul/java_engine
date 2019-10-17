package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view3d;

import java.beans.PropertyChangeEvent;

import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.AbstractViewPanel;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.MapElements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.controls.FocusGainedEvent;
import de.lessvoid.nifty.controls.FocusLostEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GuiController implements ScreenController, AbstractViewPanel {

  private final MainApplication app;
  private final DefaultController controller;

  private Element debugText = null;
  private CheckBox autoSearchElem = null;

  private Button stepBtn = null;
  private boolean stepBtnState = false;

  private Button startBtn = null;
  private boolean startBtnState = false;

  private Button stopBtn = null;
  private boolean stopBtnState = false;

  private Button resetBtn = null;
  private boolean resetBtnState = false;

  /**
   * C'tor. Element will be added to controller.
   * 
   * @param controller
   * @param app
   */
  public GuiController(DefaultController controller, MainApplication app) {
    this.app = app;
    this.controller = controller;
    this.controller.addView(this);
  }

  @Override
  public void bind(Nifty nifty, Screen screen) {
//    System.out.println("bind( " + screen.getScreenId() + ")");
    if (null == screen) {
      return;
    }
    Element element = screen.findElementById("PitButton");
    if (null == element) {
      return;
    }
    element.disable();

    @SuppressWarnings("unchecked")
    DropDown<MoverTyp> dropDownMoverElem = (DropDown<MoverTyp>) screen.findNiftyControl("moverSelector", DropDown.class);
    for (MoverTyp typ : MoverTyp.values()) {
      dropDownMoverElem.addItem(typ);
    }
    dropDownMoverElem.selectItemByIndex(0);

    @SuppressWarnings("unchecked")
    DropDown<PathfindingAlgo> dropDownAlgoElem = (DropDown<PathfindingAlgo>) screen.findNiftyControl("algoSelector", DropDown.class);
    for (PathfindingAlgo typ : PathfindingAlgo.values()) {
      dropDownAlgoElem.addItem(typ);
    }
    dropDownAlgoElem.selectItemByIndex(0);

    autoSearchElem = (CheckBox) screen.findNiftyControl("autoSearchCheckBox", CheckBox.class);
    autoSearchElem.setChecked(true);

    stepBtn = (Button) screen.findNiftyControl("doStepButton", Button.class);
    stepBtn.setEnabled(false);
    stepBtnState = false;

    startBtn = (Button) screen.findNiftyControl("doStartButton", Button.class);
    startBtn.setEnabled(false);
    startBtnState = false;

    stopBtn = (Button) screen.findNiftyControl("doStopButton", Button.class);
    stopBtn.setEnabled(false);
    stopBtnState = false;

    resetBtn = (Button) screen.findNiftyControl("doRestetButton", Button.class);
    resetBtn.setEnabled(false);
    resetBtnState = false;

    TextField msProFrameField = (TextField) screen.findNiftyControl("msProStepFeld", TextField.class);
    msProFrameField.getElement().setIgnoreKeyboardEvents(false);

    debugText = nifty.getCurrentScreen().findElementById("debugText");
  }

  /**
   * update gui
   */
  public void update() {
    if (stepBtnState) {
      stepBtn.enable();
    } else {
      stepBtn.disable();
    }

    if (startBtnState) {
      startBtn.enable();
    } else {
      startBtn.disable();
    }

    if (stopBtnState) {
      stopBtn.enable();
    } else {
      stopBtn.disable();
    }

    if (resetBtnState) {
      resetBtn.enable();
    } else {
      resetBtn.disable();
    }

  }

  @Override
  public void onStartScreen() {
//    System.out.println("onStartScreen");
  }

  @Override
  public void onEndScreen() {
//    System.out.println("onEndScreen");
  }

  public void clickTester() {
    System.out.println("clickTester");
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

  public void clearButtonClick() {
    app.clickElement = MapElements.NOPE;
  }

  @NiftyEventSubscriber(id = "moverSelector")
  public void moverSelectorChange(String id, DropDownSelectionChangedEvent<?> event) {
    Object selectedItem = event.getSelection();
    if (selectedItem instanceof MoverTyp) {
      controller.setMover((MoverTyp) selectedItem);
    }
  }

  @NiftyEventSubscriber(id = "algoSelector")
  public void algoSelectorChange(String id, DropDownSelectionChangedEvent<?> event) {
    Object selectedItem = event.getSelection();
    if (selectedItem instanceof PathfindingAlgo) {
      controller.setPathfindingAlgo((PathfindingAlgo) selectedItem);
    }
  }

//  @NiftyEventSubscriber(pattern = ".*Selector")
//  public void selectorChange(String id, DropDownSelectionChangedEvent<?> event) {
//
//    Object selectedItem = event.getSelection();
//    switch (id) {
//    case "moverSelector":
//      if (selectedItem instanceof MoverTyp) {
//        app.setMoverTyp((MoverTyp) selectedItem);
//      }
//      break;
//    case "algoSelector":
//      if (selectedItem instanceof PathfindingAlgo) {
//        app.setAlgo((PathfindingAlgo) selectedItem);
//      }
//      break;
//    default:
//      break;
//    }
//  }

  @NiftyEventSubscriber(id = "autoSearchCheckBox")
  public void onRememberMeCheck(String id, CheckBoxStateChangedEvent event) {
    boolean checked = event.isChecked();
    controller.setAutoPathfinding(checked);

    if (checked) {
//      stepBtn.setEnabled(false);
//      startBtn.setEnabled(false);
//      stopBtn.setEnabled(false);
//      resetBtn.setEnabled(false);
      stepBtnState = false;
      startBtnState = false;
      stopBtnState = false;
      resetBtnState = false;
    } else {
//      stepBtn.setEnabled(true);
//      startBtn.setEnabled(true);
//      stopBtn.setEnabled(true);
//      resetBtn.setEnabled(true);
      stepBtnState = true;
      startBtnState = true;
      stopBtnState = true;
      resetBtnState = true;
    }
  }

  @NiftyEventSubscriber(id = "msProStepFeld")
  public void ms(String id, TextFieldChangedEvent event) {
//    System.out.println("id: " + id);
//    System.out.println(" -currentText : " + event.getText());
//    app.setAutoPathfinding(event.isChecked());
  }

  @NiftyEventSubscriber(id = "msProStepFeld")
  public void msGetFocus(String id, FocusGainedEvent event) {
  }

  @NiftyEventSubscriber(id = "msProStepFeld")
  public void msLostFocus(String id, FocusLostEvent event) {
  }

  public void doStepButtonClick() {
    controller.doPathfindingStep();
  }

  public void doStartButtonClick() {
    controller.startPathfinding(20);
  }

  public void doStopButtonClick() {
    controller.stopPathfinding();
  }

  public void doResetButtonClick() {
    controller.resetPathfinding();
  }

  public void textFieldFocus(String text) {
    System.out.println(text);
  }

  @Override
  public void modelPropertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(DefaultController.ELEMENT_DEBUG_INFO)) {
      if (null != debugText) {
        String debugInfo = (String) evt.getNewValue();
        debugText.getRenderer(TextRenderer.class).setText(debugInfo);
      }
    } else if (evt.getPropertyName().equals(DefaultController.ELEMENT_PATHWAYFINDING_RUNNING)) {
      if (!autoSearchElem.isChecked()) {
        Boolean runnung = (Boolean) evt.getNewValue();
        if (runnung.booleanValue()) {
//          spnMsProStep.setEnabled(false);
//          startBtn.setEnabled(false);
          startBtnState = false;
//          stopBtn.setEnabled(true);
          stopBtnState = true;
        } else {
//          spnMsProStep.setEnabled(true);
//          startBtn.setEnabled(true);
          startBtnState = true;
//          stopBtn.setEnabled(false);
          stopBtnState = false;
        }
      }
    }
  }

}
