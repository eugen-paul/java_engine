package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapFactory;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapRepresentation;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.MapTypes;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

public class SettingsFrame extends JDialog {

  private static final long serialVersionUID = -561409987927620183L;
  private JPanel contentPane;
  private JComboBox<MapTypes> comboBoxMapSelector;

  DefaultController controller;
  private JSpinner spinnerMapSizeX;
  private JSpinner spinnerMapSizeY;

  /**
   * Create the frame.
   */
  public SettingsFrame(DefaultController controller) {
    this.controller = controller;

    setModal(true);
    setResizable(false);
    setTitle("Settings");
    setBounds(100, 100, 389, 299);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    contentPane.add(tabbedPane, BorderLayout.CENTER);

    JPanel mapPane = new JPanel();
    tabbedPane.addTab("Map", null, mapPane, null);
    mapPane.setLayout(new BorderLayout(0, 0));

    JPanel mapPanel = new JPanel();
    mapPane.add(mapPanel);
    mapPanel.setLayout(null);

    JLabel labelSelectMap = new JLabel("Select map type");
    labelSelectMap.setBounds(10, 11, 100, 14);
    mapPanel.add(labelSelectMap);

    comboBoxMapSelector = new JComboBox<>();
    comboBoxMapSelector.setBounds(10, 36, 220, 22);
    mapPanel.add(comboBoxMapSelector);

    for (MapTypes typ : MapTypes.values()) {
      comboBoxMapSelector.addItem(typ);
    }
    comboBoxMapSelector.setSelectedIndex(0);

    JLabel labelMapSize = new JLabel("Map size");
    labelMapSize.setBounds(10, 69, 48, 14);
    mapPanel.add(labelMapSize);

    JLabel labelMapSizeX = new JLabel("X:");
    labelMapSizeX.setBounds(10, 94, 15, 14);
    mapPanel.add(labelMapSizeX);

    spinnerMapSizeX = new JSpinner();
    spinnerMapSizeX.setModel(new SpinnerNumberModel(50, 10, 50, 1));
    spinnerMapSizeX.setBounds(28, 91, 55, 20);
    mapPanel.add(spinnerMapSizeX);

    JLabel labelMapSizeY = new JLabel("Y:");
    labelMapSizeY.setBounds(10, 125, 15, 14);
    mapPanel.add(labelMapSizeY);

    spinnerMapSizeY = new JSpinner();
    spinnerMapSizeY.setModel(new SpinnerNumberModel(50, 10, 50, 1));
    spinnerMapSizeY.setBounds(28, 122, 55, 20);
    mapPanel.add(spinnerMapSizeY);

    JPanel moverPane = new JPanel();
    tabbedPane.addTab("Mover", null, moverPane, null);
    moverPane.setLayout(new BorderLayout(0, 0));

    JPanel moverPanel = new JPanel();
    moverPane.add(moverPanel);

    JPanel panel = new JPanel();
    contentPane.add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    JButton buttonOk = new JButton("OK");
    buttonOk.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        clickOnOk();
      }
    });
    panel.add(buttonOk);

    JButton buttonCancel = new JButton("Cancel");
    buttonCancel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        clickOnCancel();
      }
    });
    panel.add(buttonCancel);
  }

  private void clickOnOk() {
    MapTypes mapType = comboBoxMapSelector.getItemAt(comboBoxMapSelector.getSelectedIndex());
    IMapFactory factory = mapType.getFactory();

    try {
      spinnerMapSizeX.commitEdit();
      spinnerMapSizeY.commitEdit();
    } catch (ParseException e) {
      clickOnCancel();
      return;
    }

    IMapRepresentation newMap = factory.generateMap(//
        ((Number) spinnerMapSizeX.getValue()).intValue(), //
        ((Number) spinnerMapSizeY.getValue()).intValue() //
    );
    controller.setMap(newMap);

    dispose();
  }

  private void clickOnCancel() {
    dispose();
  }
}
