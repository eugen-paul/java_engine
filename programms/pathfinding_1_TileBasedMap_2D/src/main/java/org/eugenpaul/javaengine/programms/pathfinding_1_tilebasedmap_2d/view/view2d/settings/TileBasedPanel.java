package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.settings;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.text.ParseException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.ASettingPanel;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.TileBasedFactory;

public class TileBasedPanel extends ASettingPanel {
  private static final long serialVersionUID = -4260925238869533180L;
  private JSpinner spinnerMapSizeX;
  private JSpinner spinnerMapSizeY;
  private TileBasedFactory factory;

  public TileBasedPanel(TileBasedFactory factory) {
    this.factory = factory;

    setLayout(new BorderLayout(0, 0));
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    add(tabbedPane);

    JPanel mapPanel = new JPanel();
    tabbedPane.addTab("Map", null, mapPanel, null);
    mapPanel.setLayout(null);

    JLabel mapSizeLabel = new JLabel("Map Size:");
    mapSizeLabel.setBounds(10, 11, 84, 14);
    mapPanel.add(mapSizeLabel);

    JLabel widthLabel = new JLabel("Width:");
    widthLabel.setBounds(10, 36, 33, 14);
    mapPanel.add(widthLabel);

    spinnerMapSizeX = new JSpinner();
    spinnerMapSizeX.setBounds(53, 33, 41, 20);
    spinnerMapSizeX.setModel(new SpinnerNumberModel(50, 0, 50, 1));
    mapPanel.add(spinnerMapSizeX);

    JLabel heightLabel = new JLabel("Height:");
    heightLabel.setBounds(10, 61, 36, 14);
    mapPanel.add(heightLabel);

    spinnerMapSizeY = new JSpinner();
    spinnerMapSizeY.setModel(new SpinnerNumberModel(50, 0, 50, 1));
    spinnerMapSizeY.setBounds(53, 58, 41, 20);
    mapPanel.add(spinnerMapSizeY);

    JPanel moverPanel = new JPanel();
    tabbedPane.addTab("Mover", null, moverPanel, null);
  }

  @Override
  public void clickOk(DefaultController controller) {
    try {
      spinnerMapSizeX.commitEdit();
      spinnerMapSizeY.commitEdit();
    } catch (ParseException e) {
      e.printStackTrace();
      return;
    }

    factory.setMapParameter( //
        ((Number) spinnerMapSizeX.getValue()).intValue(), //
        ((Number) spinnerMapSizeY.getValue()).intValue() //
    );
    controller.set2dFactory(factory.getName());
  }

}
