package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.settings;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.ASettingPanel;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.AlgoFactory;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Settings frame. Change global pathfinding properties.
 * 
 * @author Eugen Paul
 *
 */
public class SettingsFrame extends JDialog {

  private static final long serialVersionUID = -561409987927620183L;
  private JPanel contentPane;
  private JComboBox<String> comboBoxMapSelector;
  private List<AlgoFactory> factoryList;

  DefaultController controller;
  private JPanel settingPanelPlace;
  private ASettingPanel currentSettingPanel = null;

  /**
   * Create the frame.
   */
  public SettingsFrame(DefaultController controller) {
    this.controller = controller;
    this.factoryList = controller.get2dFactories();

    setModal(true);
    setResizable(false);
    setTitle("Settings");
    setBounds(100, 100, 389, 299);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JPanel mapSelectorPanel = new JPanel();
    contentPane.add(mapSelectorPanel, BorderLayout.NORTH);
    FlowLayout flMapSelectorPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
    mapSelectorPanel.setLayout(flMapSelectorPanel);

    JLabel labelSelectMap = new JLabel("Select map type");
    mapSelectorPanel.add(labelSelectMap);

    settingPanelPlace = new JPanel();
    contentPane.add(settingPanelPlace, BorderLayout.CENTER);
    settingPanelPlace.setLayout(new BorderLayout(0, 0));

    initMapSelector();
    mapSelectorPanel.add(comboBoxMapSelector);

    JPanel panel = new JPanel();
    contentPane.add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    JButton buttonOk = new JButton("Apply an Close");
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

  private void initMapSelector() {
    comboBoxMapSelector = new JComboBox<>();
    comboBoxMapSelector.addActionListener(e -> mapSelectorAction());

    for (AlgoFactory factory : factoryList) {
      comboBoxMapSelector.addItem(factory.getName());
    }
    comboBoxMapSelector.setSelectedIndex(0);

    comboBoxMapSelector.setLightWeightPopupEnabled(false);
  }

  private void mapSelectorAction() {
    settingPanelPlace.removeAll();
    settingPanelPlace.setLayout(new BorderLayout(0, 0));

    String typeName = comboBoxMapSelector.getItemAt(comboBoxMapSelector.getSelectedIndex());
    for (AlgoFactory factory : factoryList) {
      if (factory.getName().equals(typeName)) {
        currentSettingPanel = factory.createSettingsPanel();
        settingPanelPlace.add(currentSettingPanel, BorderLayout.CENTER);
        break;
      }
    }

    invalidate();
    validate();
    repaint();
  }

  private void clickOnOk() {
    if (null != currentSettingPanel) {
      currentSettingPanel.clickOk(controller);
    }
    dispose();
  }

  private void clickOnCancel() {
    dispose();
  }
}
