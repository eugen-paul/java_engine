package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view3d;

import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.MapElements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.controls.FocusGainedEvent;
import de.lessvoid.nifty.controls.FocusLostEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GuiController implements ScreenController {

  private final MainApplication app;

  public GuiController(MainApplication app) {
    this.app = app;
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

    CheckBox autoSearchElem = (CheckBox) screen.findNiftyControl("autoSearchCheckBox", CheckBox.class);
    autoSearchElem.setChecked(true);

    TextField msProFrameField = (TextField) screen.findNiftyControl("msProStepFeld", TextField.class);
    msProFrameField.getElement().setIgnoreKeyboardEvents(false);
  }

  @Override
  public void onStartScreen() {
//    System.out.println("onStartScreen");
  }

  @Override
  public void onEndScreen() {
//    System.out.println("onEndScreen");
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
      app.setMoverTyp((MoverTyp) selectedItem);
    }
  }

  @NiftyEventSubscriber(id = "algoSelector")
  public void algoSelectorChange(String id, DropDownSelectionChangedEvent<?> event) {
    Object selectedItem = event.getSelection();
    if (selectedItem instanceof PathfindingAlgo) {
      app.setAlgo((PathfindingAlgo) selectedItem);
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
    app.setAutoPathfinding(event.isChecked());
  }

  @NiftyEventSubscriber(id = "msProStepFeld")
  public void ms(String id, TextFieldChangedEvent event) {
//    System.out.println("id: " + id);
//    System.out.println(" -currentText : " + event.getText());
//    app.setAutoPathfinding(event.isChecked());
  }

  @NiftyEventSubscriber(id = "msProStepFeld")
  public void msGetFocus(String id, FocusGainedEvent event) {
    System.out.println("id getFocus: ");
  }

  @NiftyEventSubscriber(id = "msProStepFeld")
  public void msLostFocus(String id, FocusLostEvent event) {
    System.out.println("id lostFocus: ");
  }

  public void doStepButtonClick() {
    app.doStep();
  }

  public void doStartButtonClick() {
    app.doStart();
  }

  public void doStopButtonClick() {
    app.doStop();
  }

  public void doResetButtonClick() {
    app.doReset();
  }

  public void textFieldFocus(String text) {
    System.out.println(text);
  }

}
