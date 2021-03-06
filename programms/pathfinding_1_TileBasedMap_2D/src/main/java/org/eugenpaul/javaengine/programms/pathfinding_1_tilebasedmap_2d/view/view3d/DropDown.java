package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view3d;

import java.util.Set;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.ListBox;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.component.TbtQuadBackgroundComponent;
import com.simsilica.lemur.core.GuiControl;
import com.simsilica.lemur.core.VersionedList;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.event.PopupState.ClickMode;
import com.simsilica.lemur.list.SelectionModel;
import com.simsilica.lemur.style.ElementId;

/**
 * Very simple Drop-Down Menu. Not the best implementation. Don't use it.
 * 
 * @author Eugen Paul
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class DropDown<T> extends Panel {

  public static final String ELEMENT_ID = "dropdown";
  private boolean popupShown = false;
  private final Label chosenElement;
  private final Button collapseButton;
  private final ListBox<T> listBox;
  private VersionedReference<Set<Integer>> selectionRef;

  public DropDown() {
    this(null);
  }

  public DropDown(String style) {
    this(true, new ElementId(ELEMENT_ID), style);
  }

  public DropDown(ElementId elementId, String style) {
    this(true, elementId, style);
  }

  @Override
  public void updateLogicalState(float tpf) {
    super.updateLogicalState(tpf);
    if (selectionRef.update()) {
      Integer selection = listBox.getSelectionModel().getSelection();
      if (selection != null) {
        chosenElement.setText(listBox.getModel().get(selection).toString());
      }
    }
  }

  protected DropDown(boolean applyStyles, ElementId elementId, String style) {
    super(false, elementId, style);
//    BoxLayout layout = new BoxLayout(Axis.X, FillMode.None);
//    getControl(GuiControl.class).setLayout(layout);
    SpringGridLayout layout = new SpringGridLayout(Axis.X, Axis.Y, FillMode.First, FillMode.None);
    getControl(GuiControl.class).setLayout(layout);
    this.collapseButton = new Button("V", elementId.child("button"), style);
    this.chosenElement = new Label("", elementId.child("selection"), style);

    layout.addChild(chosenElement);
    layout.addChild(collapseButton);
    collapseButton.setTextVAlignment(VAlignment.Center);
    collapseButton.addClickCommands((s) -> {
      resetOpen();
    });
    chosenElement.setTextHAlignment(HAlignment.Left);
    chosenElement.setTextVAlignment(VAlignment.Center);

    // TODO remove this dirty Background Initialisation
    chosenElement.setInsets(new Insets3f(2, 2, 2, 2));
    Texture texture = GuiGlobals.getInstance().loadTexture("/com/simsilica/lemur/icons/bordered-gradient.png", true,
        false);
    TbtQuadBackgroundComponent bg = TbtQuadBackgroundComponent.create(texture, 1, 1, 1, 126, 126, 1f, false);
    chosenElement.setBackground(bg);
    bg.setColor(new ColorRGBA(0f, 0.75f, 0.75f, 0.5f));

    listBox = new ListBox<>();

    listBox.setBackground(new QuadBackgroundComponent(ColorRGBA.Black));
    listBox.setAlpha(1, true);

    this.selectionRef = listBox.getSelectionModel().createReference();
    if (applyStyles) {
      GuiGlobals.getInstance().getStyles().applyStyles(this, elementId, style);
    }
  }

  @SuppressWarnings("rawtypes")
  public void addClickCommands(Command<? super ListBox>... commands) {
    listBox.addClickCommands(commands);
    listBox.addClickCommands(source -> resetOpen());
  }

  protected void resetOpen() {
    final PopupState popupState = GuiGlobals.getInstance().getPopupState();
    if (popupShown) {
      if (popupState.isPopup(listBox)) {
        popupState.closePopup(listBox);
      }
      popupShown = false;
    } else {
      Vector3f preferredSize = new Vector3f(Math.max(getSize().x, 100f), 100f, 1);
      listBox.setSize(preferredSize);
      listBox.setPreferredSize(preferredSize);
      Vector3f localTranslation = getWorldTranslation();
      listBox.setLocalTranslation(localTranslation.x, localTranslation.y - getSize().y, localTranslation.z);

      popupState.showPopup(listBox, ClickMode.ConsumeAndClose, source -> {
        Integer selection = listBox.getSelectionModel().getSelection();
        if (selection != null) {
          chosenElement.setText(listBox.getModel().get(selection).toString());
        }
        popupShown = false;
      }, null);
      GuiGlobals.getInstance().requestFocus(listBox);
      popupShown = true;
    }
  }

  public VersionedList<T> getModel() {
    return listBox.getModel();
  }

  public SelectionModel getSelectionModel() {
    return listBox.getSelectionModel();
  }

}