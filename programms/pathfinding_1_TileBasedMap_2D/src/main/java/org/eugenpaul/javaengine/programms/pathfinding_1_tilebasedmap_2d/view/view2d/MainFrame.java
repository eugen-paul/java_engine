package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.GridElement;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.AbstractViewPanel;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.settings.SettingsFrame;

import javax.swing.JSplitPane;
import java.awt.Dimension;
import javax.swing.JButton;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import java.awt.event.ItemEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame implements AbstractViewPanel {

  private static final long serialVersionUID = -2589772721955644662L;
  private JPanel contentPane;
  private DefaultController controller;

  private JComboBox<PathfindingAlgo> jCbPathfindingAlgoSelector;
  private JButton btnDoStep;
  private JButton btnStartSearch;
  private JButton btnStopSearch;
  private JSpinner spnMsProStep;
  private JButton btnResetSearch;
  private JCheckBox chckbxAutoSearching;
  private JTextArea textAreaDebugInfo;

  private SettingsFrame settingFrame;

  private JPanel panelControlPlace;
  private AControlPanel panelControl = null;

  private JPanel panelPaintPlace;
  private APaintPanel panelPaint = null;

  private MouseListener clickOnPaint;
  private MouseMotionListener moveOnPaint;

  /**
   * Create the frame.
   */
  public MainFrame(DefaultController controller) {
    this.controller = controller;

    settingFrame = new SettingsFrame(controller);

    setResizable(false);
    setTitle("Pathfinding");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 833, 748);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JSplitPane splitPane = new JSplitPane();
    splitPane.setEnabled(false);
    splitPane.setDividerSize(2);
//    splitPane.setPreferredSize(new Dimension(183, 100));
    contentPane.add(splitPane, BorderLayout.CENTER);

    JPanel panelSettings = new JPanel();
    splitPane.setLeftComponent(panelSettings);
    panelSettings.setLayout(new BorderLayout(0, 0));

    JSplitPane splitPaneSetting = new JSplitPane();
    splitPaneSetting.setOrientation(JSplitPane.VERTICAL_SPLIT);
    panelSettings.add(splitPaneSetting);

    panelControlPlace = new JPanel();
    splitPaneSetting.setLeftComponent(panelControlPlace);

    JPanel panel = new JPanel();
    splitPaneSetting.setRightComponent(panel);

    JLabel lblAlgorithm = new JLabel("Algorithm");

    jCbPathfindingAlgoSelector = new JComboBox<>();

    chckbxAutoSearching = new JCheckBox("Auto searching");
    chckbxAutoSearching.addItemListener(e -> setAutoPathfinding(e.getStateChange() == ItemEvent.SELECTED));

    btnDoStep = new JButton("do Step");
    btnDoStep.addActionListener(e -> doPathfindingStep());
    btnDoStep.setEnabled(false);

    btnStartSearch = new JButton("Start search");
    btnStartSearch.addActionListener(e -> startAutoPathfindung());
    btnStartSearch.setEnabled(false);

    btnStopSearch = new JButton("Stop search");
    btnStopSearch.addActionListener(e -> stopAutoPathfindung());
    btnStopSearch.setEnabled(false);

    spnMsProStep = new JSpinner();
    spnMsProStep.setModel(new SpinnerNumberModel(Integer.valueOf(100), Integer.valueOf(1), null, Integer.valueOf(1)));
    spnMsProStep.setEnabled(false);

    JLabel lblMillisecondsPerStep = new JLabel("Milliseconds per step");

    btnResetSearch = new JButton("Reset seach");
    btnResetSearch.addActionListener(e -> resetPathfinding());
    btnResetSearch.setEnabled(false);

    GroupLayout glPanel = new GroupLayout(panel);
    glPanel.setHorizontalGroup(glPanel.createParallelGroup(Alignment.LEADING).addGroup(glPanel.createSequentialGroup().addContainerGap().addGroup(glPanel
        .createParallelGroup(Alignment.LEADING).addComponent(chckbxAutoSearching).addComponent(lblAlgorithm)
        .addComponent(jCbPathfindingAlgoSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnDoStep)
        .addComponent(btnStartSearch).addComponent(btnStopSearch).addComponent(lblMillisecondsPerStep).addGroup(glPanel.createParallelGroup(Alignment.TRAILING, false)
            .addComponent(spnMsProStep, Alignment.LEADING).addComponent(btnResetSearch, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addContainerGap(35, Short.MAX_VALUE)));
    glPanel.setVerticalGroup(glPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(glPanel.createSequentialGroup().addContainerGap().addComponent(lblAlgorithm).addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(jCbPathfindingAlgoSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(chckbxAutoSearching)
            .addGap(18).addComponent(btnDoStep).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnStartSearch).addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(btnStopSearch).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblMillisecondsPerStep).addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(spnMsProStep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(btnResetSearch).addContainerGap(96, Short.MAX_VALUE)));
    panel.setLayout(glPanel);

    for (PathfindingAlgo algo : PathfindingAlgo.values()) {
      jCbPathfindingAlgoSelector.addItem(algo);
    }
    jCbPathfindingAlgoSelector.setSelectedIndex(0);

    chckbxAutoSearching.setSelected(true);

    jCbPathfindingAlgoSelector.addActionListener(e -> editPathfindingAlgo());

    TileBasedPanelMap panelHelper = new TileBasedPanelMap();

    splitPane.setRightComponent(panelHelper);

    JSplitPane splitPane2 = new JSplitPane();
    splitPane2.setMaximumSize(new Dimension(183, 100));
    splitPane2.setPreferredSize(new Dimension(183, 100));
    splitPane2.setEnabled(false);
    splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
    GroupLayout glPanelMap = new GroupLayout(panelHelper);
    glPanelMap.setHorizontalGroup(glPanelMap.createParallelGroup(Alignment.LEADING).addComponent(splitPane2, GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE));
    glPanelMap.setVerticalGroup(glPanelMap.createParallelGroup(Alignment.LEADING).addComponent(splitPane2, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE));
    panelHelper.setLayout(glPanelMap);

    panelPaintPlace = new JPanel();
    splitPane2.setLeftComponent(panelPaintPlace);
    panelPaintPlace.setLayout(new BorderLayout(0, 0));

    JPanel panelDebug = new JPanel();
    splitPane2.setRightComponent(panelDebug);

    textAreaDebugInfo = new JTextArea();
    textAreaDebugInfo.setEditable(false);
    GroupLayout glPanelDebug = new GroupLayout(panelDebug);
    glPanelDebug.setHorizontalGroup(glPanelDebug.createParallelGroup(Alignment.LEADING).addComponent(textAreaDebugInfo, GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE));
    glPanelDebug.setVerticalGroup(glPanelDebug.createParallelGroup(Alignment.LEADING).addComponent(textAreaDebugInfo, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE));
    panelDebug.setLayout(glPanelDebug);
    splitPane2.setDividerLocation(601);
    splitPane.setDividerLocation(150);

    JMenuBar menuBar = new JMenuBar();
    contentPane.add(menuBar, BorderLayout.NORTH);

    JMenu menuEdit = new JMenu("Edit");
    menuBar.add(menuEdit);

    JMenuItem menuItemSettings = new JMenuItem("Settings");
    menuItemSettings.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        settingFrame.setVisible(true);
      }
    });
    menuEdit.add(menuItemSettings);

    JMenu menuHelp = new JMenu("Help");
    menuBar.add(menuHelp);

    JMenuItem menuItemAbout = new JMenuItem("About");
    menuHelp.add(menuItemAbout);

    clickOnPaint = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Immutable3dTilePoint coord = panelPaint.mouseToWorld(e.getX(), e.getY());
        controller.setPointOnMap(coord.getX(), coord.getY(), panelControl.getSelector());
      }
    };

    moveOnPaint = new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        Immutable3dTilePoint coord = panelPaint.mouseToWorld(e.getX(), e.getY());
        controller.setPointOnMap(coord.getX(), coord.getY(), panelControl.getSelector());
      }
    };

    rebuild();
  }

  private void rebuild() {
    if (null != panelControl) {
      panelControlPlace.removeAll();
    }

    panelControl = controller.getCurrent2dFactory().createControlPanel();
    panelControlPlace.add(panelControl);

    if (null != panelPaint) {
      panelPaint.removeMouseListener(clickOnPaint);
      panelPaint.removeMouseMotionListener(moveOnPaint);
      panelPaintPlace.removeAll();
    }

    panelPaint = controller.getCurrent2dFactory().createPaintPanel();
    panelPaintPlace.setLayout(new BorderLayout(0, 0));
    panelPaintPlace.add(panelPaint, BorderLayout.CENTER);
    panelPaint.addMouseListener(clickOnPaint);

    panelPaint.addMouseMotionListener(moveOnPaint);

    invalidate();
    validate();
  }

  @Override
  public void modelPropertyChange(final PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(DefaultController.ELEMENT_MAP)) {
      GridElement[][] newStringValue = (GridElement[][]) evt.getNewValue();
      paintMap(newStringValue);
    } else if (evt.getPropertyName().equals(DefaultController.ELEMENT_REBUILD)) {
      rebuild();
      GridElement[][] newStringValue = (GridElement[][]) evt.getNewValue();
      paintMap(newStringValue);
    } else if (evt.getPropertyName().equals(DefaultController.ELEMENT_DEBUG_INFO)) {
      String debugInfo = (String) evt.getNewValue();
      textAreaDebugInfo.setText(debugInfo);
    } else if (evt.getPropertyName().equals(DefaultController.ELEMENT_PATHWAYFINDING_RUNNING)) {
      if (!chckbxAutoSearching.isSelected()) {
        Boolean runnung = (Boolean) evt.getNewValue();
        if (runnung.booleanValue()) {
          spnMsProStep.setEnabled(false);
          btnStartSearch.setEnabled(false);
          btnStopSearch.setEnabled(true);
        } else {
          spnMsProStep.setEnabled(true);
          btnStartSearch.setEnabled(true);
          btnStopSearch.setEnabled(false);
        }
      }
    }
  }

  private void paintMap(GridElement[][] grid) {
    panelPaint.setGrid(grid);
  }

  private void editPathfindingAlgo() {
    controller.setPathfindingAlgo(jCbPathfindingAlgoSelector.getItemAt(jCbPathfindingAlgoSelector.getSelectedIndex()));
  }

  private void setAutoPathfinding(boolean autoPathfinding) {
    controller.setAutoPathfinding(autoPathfinding);

    btnDoStep.setEnabled(!autoPathfinding);
    btnStartSearch.setEnabled(!autoPathfinding);
    btnStopSearch.setEnabled(!autoPathfinding);
    btnResetSearch.setEnabled(!autoPathfinding);
    spnMsProStep.setEnabled(!autoPathfinding);
  }

  private void doPathfindingStep() {
    controller.doPathfindingStep();
  }

  private void resetPathfinding() {
    controller.resetPathfinding();
  }

  private void startAutoPathfindung() {
    controller.startPathfinding((Integer) spnMsProStep.getValue());
  }

  private void stopAutoPathfindung() {
    controller.stopPathfinding();
  }
}
