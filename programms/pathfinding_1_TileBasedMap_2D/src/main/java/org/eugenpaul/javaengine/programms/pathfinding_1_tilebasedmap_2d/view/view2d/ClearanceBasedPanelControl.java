package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapMover;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.clearance.ClearanceBasedMoverTyp;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

public class ClearanceBasedPanelControl extends AControlPanel {

  private static final long serialVersionUID = -220053717720994820L;
  private DefaultController controller;

  private MapElements selector = MapElements.NOPE;

  private JComboBox<ClearanceBasedMoverTyp> jCbMoverSelector;

  public ClearanceBasedPanelControl(DefaultController controller) {
    this.controller = controller;

    JButton btnSetStart = new JButton("Set Start");
    btnSetStart.setToolTipText("Set Startpoint");

    JButton btnSetEnd = new JButton("Set End");
    btnSetEnd.setToolTipText("Set Endpoint");

    JButton btnSetWall = new JButton("Set Wall");
    btnSetWall.setToolTipText("Set Wall");

    JButton btnClearPoint = new JButton("Clear Point");
    btnClearPoint.setToolTipText("Clear Point");

    JButton btnClearMap = new JButton("Clear Map");
    btnClearMap.setEnabled(false);
    btnClearMap.setToolTipText("Clear Map");

    jCbMoverSelector = new JComboBox<>();
    jCbMoverSelector.setToolTipText("Select Mover");

    btnClearPoint.addActionListener(e -> selector = MapElements.NOPE);
    btnSetWall.addActionListener(e -> selector = MapElements.WALL);
    btnSetEnd.addActionListener(e -> selector = MapElements.END);
    btnSetStart.addActionListener(e -> selector = MapElements.START);

    for (ClearanceBasedMoverTyp typ : ClearanceBasedMoverTyp.values()) {
      jCbMoverSelector.addItem(typ);
    }
    jCbMoverSelector.setSelectedIndex(0);

    GroupLayout glPanelMoverSettings = new GroupLayout(this);
    glPanelMoverSettings.setHorizontalGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING)
        .addGroup(glPanelMoverSettings.createSequentialGroup().addGap(16)
            .addGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING)
                .addComponent(jCbMoverSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnClearMap)
                .addComponent(btnClearPoint).addComponent(btnSetWall).addComponent(btnSetEnd).addComponent(btnSetStart))
            .addContainerGap(347, Short.MAX_VALUE)));
    glPanelMoverSettings.setVerticalGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING)
        .addGroup(glPanelMoverSettings.createSequentialGroup().addGap(11).addComponent(btnSetStart).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSetEnd)
            .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSetWall).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClearPoint)
            .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClearMap).addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(jCbMoverSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(97)));
    this.setLayout(glPanelMoverSettings);

    jCbMoverSelector.addActionListener(e -> editMover());
  }

  private void editMover() {
    if (isAktiv()) {
      controller.setMover(jCbMoverSelector.getItemAt(jCbMoverSelector.getSelectedIndex()));
    }
  }

  @Override
  public MapElements getSelector() {
    return selector;
  }

  @Override
  public IMapMover getDefaultMover() {
    return ClearanceBasedMoverTyp.values()[0];
  }
  
  @Override
  protected void reset() {
    super.reset();
    if(isAktiv()) {
      jCbMoverSelector.setSelectedIndex(0);
    }
  }
}
