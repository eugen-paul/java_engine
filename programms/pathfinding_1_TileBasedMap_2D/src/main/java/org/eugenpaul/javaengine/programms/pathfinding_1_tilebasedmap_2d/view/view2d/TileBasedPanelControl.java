package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapMover;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

public class TileBasedPanelControl extends AControlPanel {

  private static final long serialVersionUID = -220053717720994820L;
  private DefaultController controller;

  private MapElements selector = MapElements.NOPE;

  private JComboBox<IMapMover> jCbMoverSelector;

  public TileBasedPanelControl(DefaultController controller) {
    this.controller = controller;

    JButton btnSetStart = new JButton("Set Start");
    btnSetStart.setToolTipText("Set Startpoint");

    JButton btnSetEnd = new JButton("Set End");
    btnSetEnd.setToolTipText("Set Endpoint");

    JButton btnSetWall = new JButton("Set Wall");
    btnSetWall.setToolTipText("Set Wall");

    JButton btnSetPit = new JButton("Set Pit");
    btnSetPit.setToolTipText("Set Pit");

    JButton btnClearPoint = new JButton("Clear Point");
    btnClearPoint.setToolTipText("Clear Point");

    JButton btnClearMap = new JButton("Clear Map");
    btnClearMap.setEnabled(false);
    btnClearMap.setToolTipText("Clear Map");

    jCbMoverSelector = new JComboBox<>();
    jCbMoverSelector.setToolTipText("Select Mover");

    btnClearPoint.addActionListener(e -> selector = MapElements.NOPE);
    btnSetPit.addActionListener(e -> selector = MapElements.MUD);
    btnSetWall.addActionListener(e -> selector = MapElements.WALL);
    btnSetEnd.addActionListener(e -> selector = MapElements.END);
    btnSetStart.addActionListener(e -> selector = MapElements.START);

    GroupLayout glPanelMoverSettings = new GroupLayout(this);
    glPanelMoverSettings
        .setHorizontalGroup(
            glPanelMoverSettings.createParallelGroup(Alignment.LEADING)
                .addGroup(glPanelMoverSettings.createSequentialGroup()
                    .addGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPanelMoverSettings.createSequentialGroup().addGap(16)
                            .addGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING).addComponent(btnSetWall).addComponent(btnSetEnd).addComponent(btnSetStart)))
                        .addGroup(glPanelMoverSettings.createSequentialGroup().addGap(16)
                            .addGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING).addComponent(btnSetPit).addComponent(btnClearPoint).addComponent(btnClearMap)
                                .addComponent(jCbMoverSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(14, Short.MAX_VALUE)));
    glPanelMoverSettings.setVerticalGroup(glPanelMoverSettings.createParallelGroup(Alignment.LEADING)
        .addGroup(glPanelMoverSettings.createSequentialGroup().addGap(11).addComponent(btnSetStart).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSetEnd)
            .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSetWall).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSetPit)
            .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClearPoint).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClearMap)
            .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(jCbMoverSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(12)));
    this.setLayout(glPanelMoverSettings);

    jCbMoverSelector.addActionListener(e -> editMover());
  }
  
  public void init(IMapMover[] moverList, int std) {
    for (IMapMover typ : moverList) {
      jCbMoverSelector.addItem(typ);
    }
    jCbMoverSelector.setSelectedIndex(std);
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
  protected void reset() {
    super.reset();
    if (isAktiv()) {
      jCbMoverSelector.setSelectedIndex(0);
    }
  }
}
