package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfindingAlgo;

import javax.swing.JSplitPane;
import java.awt.Dimension;
import javax.swing.JButton;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;

public class MainFrame extends JFrame implements AbstractViewPanel {

  private MapElements selector = MapElements.NOPE;

  /**
   * 
   */
  private static final long serialVersionUID = -2589772721955644662L;
  private JPanel contentPane;
  private DefaultController controller;
  private PanelMap panelMap = null;
  
  private int mapSizeX;
  private int mapSizeY;
  private JComboBox<MoverTyp> jCbMoverSelector;
  private JComboBox<PathfindingAlgo> jCbPathfindingAlgoSelector;
  private JButton btnDoStep;
  private JButton btnStartSearch;
  private JButton btnStopSearch;
  private JSpinner spnMsProStep;
  private JButton btnResetSearch;
  private JCheckBox chckbxAutoSearching;
  private JTextArea textAreaDebugInfo;

  /**
   * Create the frame.
   */
  public MainFrame(DefaultController controller, int x, int y) {

    this.controller = controller;

    mapSizeX = x;
    mapSizeY = y;
    
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
    splitPane.setPreferredSize(new Dimension(183, 100));
    contentPane.add(splitPane, BorderLayout.CENTER);

    JPanel panelSettings = new JPanel();
    splitPane.setLeftComponent(panelSettings);
    panelSettings.setLayout(new BorderLayout(0, 0));
    
    JSplitPane splitPane_1 = new JSplitPane();
    splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    panelSettings.add(splitPane_1);
                            
                            JPanel panelMoverSettings = new JPanel();
                            splitPane_1.setLeftComponent(panelMoverSettings);
                            
                                JButton btnSetStart = new JButton("Set Start");
                                btnSetStart.setToolTipText("Set Startpoint");
                                
                                    JButton btnSetEnd = new JButton("Set End");
                                    btnSetEnd.setToolTipText("Set Endpoint");
                                    
                                        JButton btnSetWall = new JButton("Set Wall");
                                        btnSetWall.setToolTipText("Set Wall");
                                        
                                            JButton btnSetPit = new JButton("Set Pit");
                                            btnSetPit.setEnabled(false);
                                            btnSetPit.setToolTipText("Set Pit");
                                            
                                                JButton btnClearPoint = new JButton("Clear Point");
                                                btnClearPoint.setToolTipText("Clear Point");
                                                
                                                    JButton btnClearMap = new JButton("Clear Map");
                                                    btnClearMap.setToolTipText("Clear Map");
                                                    
                                                    jCbMoverSelector = new JComboBox<>();
                                                    jCbMoverSelector.setToolTipText("Select Mover");
                                                    
                                                    JPanel panel = new JPanel();
                                                    splitPane_1.setRightComponent(panel);
                                                    
                                                    JLabel lblAlgorithm = new JLabel("Algorithm");
                                                    
                                                    jCbPathfindingAlgoSelector = new JComboBox<>();
                                                    
                                                    chckbxAutoSearching = new JCheckBox("Auto searching");
                                                    chckbxAutoSearching.addItemListener(new ItemListener() {
                                                      public void itemStateChanged(ItemEvent e) {
                                                        setAutoPathfinding(e.getStateChange() == ItemEvent.SELECTED);
                                                      }
                                                    });
                                                    
                                                    btnDoStep = new JButton("do Step");
                                                    btnDoStep.addActionListener(new ActionListener() {
                                                      public void actionPerformed(ActionEvent e) {
                                                        doPathfindingStep();
                                                      }
                                                    });
                                                    btnDoStep.setEnabled(false);
                                                    
                                                    btnStartSearch = new JButton("Start search");
                                                    btnStartSearch.addActionListener(new ActionListener() {
                                                      public void actionPerformed(ActionEvent e) {
                                                        startAutoPathfindung();
                                                      }
                                                    });
                                                    btnStartSearch.setEnabled(false);
                                                    
                                                    btnStopSearch = new JButton("Stop search");
                                                    btnStopSearch.addActionListener(new ActionListener() {
                                                      public void actionPerformed(ActionEvent e) {
                                                        stopAutoPathfindung();
                                                      }
                                                    });
                                                    btnStopSearch.setEnabled(false);
                                                    
                                                    spnMsProStep = new JSpinner();
                                                    spnMsProStep.setModel(new SpinnerNumberModel(Integer.valueOf(100), Integer.valueOf(1), null, Integer.valueOf(1)));
                                                    spnMsProStep.setEnabled(false);
                                                    
                                                    JLabel lblMillisecondsPerStep = new JLabel("Milliseconds per step");
                                                    
                                                    btnResetSearch = new JButton("Reset seach");
                                                    btnResetSearch.addActionListener(new ActionListener() {
                                                      public void actionPerformed(ActionEvent e) {
                                                        resetPathfinding();
                                                      }
                                                    });
                                                    btnResetSearch.setEnabled(false);
                                                    GroupLayout gl_panel = new GroupLayout(panel);
                                                    gl_panel.setHorizontalGroup(
                                                      gl_panel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                          .addContainerGap()
                                                          .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                                            .addComponent(chckbxAutoSearching)
                                                            .addComponent(lblAlgorithm)
                                                            .addComponent(jCbPathfindingAlgoSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(btnDoStep)
                                                            .addComponent(btnStartSearch)
                                                            .addComponent(btnStopSearch)
                                                            .addComponent(lblMillisecondsPerStep)
                                                            .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                                              .addComponent(spnMsProStep, Alignment.LEADING)
                                                              .addComponent(btnResetSearch, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                          .addContainerGap(35, Short.MAX_VALUE))
                                                    );
                                                    gl_panel.setVerticalGroup(
                                                      gl_panel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                          .addContainerGap()
                                                          .addComponent(lblAlgorithm)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(jCbPathfindingAlgoSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                          .addGap(18)
                                                          .addComponent(chckbxAutoSearching)
                                                          .addGap(18)
                                                          .addComponent(btnDoStep)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(btnStartSearch)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(btnStopSearch)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(lblMillisecondsPerStep)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(spnMsProStep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(btnResetSearch)
                                                          .addContainerGap(96, Short.MAX_VALUE))
                                                    );
                                                    panel.setLayout(gl_panel);
                            btnClearPoint.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                selector = MapElements.NOPE;
                              }
                            });
                            btnSetPit.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                selector = MapElements.MUD;
                              }
                            });
                            btnSetWall.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                selector = MapElements.WALL;
                              }
                            });
                            btnSetEnd.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                selector = MapElements.END;
                              }
                            });
                            btnSetStart.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                selector = MapElements.START;
                              }
                            });
    for(MoverTyp typ: MoverTyp.values()) {
      jCbMoverSelector.addItem(typ);
    }
    jCbMoverSelector.setSelectedIndex(0);
    for(PathfindingAlgo algo: PathfindingAlgo.values()) {
      jCbPathfindingAlgoSelector.addItem(algo);
    }
    jCbPathfindingAlgoSelector.setSelectedIndex(0);
    
    chckbxAutoSearching.setSelected(true);
    
    GroupLayout gl_panelMoverSettings = new GroupLayout(panelMoverSettings);
    gl_panelMoverSettings.setHorizontalGroup(
      gl_panelMoverSettings.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panelMoverSettings.createSequentialGroup()
          .addGroup(gl_panelMoverSettings.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panelMoverSettings.createSequentialGroup()
              .addGap(16)
              .addGroup(gl_panelMoverSettings.createParallelGroup(Alignment.LEADING)
                .addComponent(btnSetWall)
                .addComponent(btnSetEnd)
                .addComponent(btnSetStart)))
            .addGroup(gl_panelMoverSettings.createSequentialGroup()
              .addGap(16)
              .addGroup(gl_panelMoverSettings.createParallelGroup(Alignment.LEADING)
                .addComponent(btnSetPit)
                .addComponent(btnClearPoint)
                .addComponent(btnClearMap)
                .addComponent(jCbMoverSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
          .addContainerGap(14, Short.MAX_VALUE))
    );
    gl_panelMoverSettings.setVerticalGroup(
      gl_panelMoverSettings.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panelMoverSettings.createSequentialGroup()
          .addGap(11)
          .addComponent(btnSetStart)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addComponent(btnSetEnd)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addComponent(btnSetWall)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addComponent(btnSetPit)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addComponent(btnClearPoint)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addComponent(btnClearMap)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addComponent(jCbMoverSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addGap(12))
    );
    panelMoverSettings.setLayout(gl_panelMoverSettings);
    
    jCbMoverSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editMover();
      }
    });
    
    jCbPathfindingAlgoSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editPathfindingAlgo();
      }
    });

    PanelMap panelHelper = new PanelMap();
    
    panelMap = new PanelMap();
    panelMap.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        sendPointToController(e.getX(), e.getY(), selector);
//        System.out.println(e.getX() + " " + e.getY());
      }
    });
    panelMap.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        sendPointToController(e.getX(), e.getY(), selector);
//        System.out.println(e.getX() + " " + e.getY());
      }
    });
    splitPane.setRightComponent(panelHelper);
    
    JSplitPane splitPane_2 = new JSplitPane();
    splitPane_2.setPreferredSize(new Dimension(183, 100));
    splitPane_2.setEnabled(false);
    splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
    GroupLayout gl_panelMap = new GroupLayout(panelHelper);
    gl_panelMap.setHorizontalGroup(
      gl_panelMap.createParallelGroup(Alignment.LEADING)
        .addComponent(splitPane_2, GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
    );
    gl_panelMap.setVerticalGroup(
      gl_panelMap.createParallelGroup(Alignment.LEADING)
        .addComponent(splitPane_2, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
    );
    panelHelper.setLayout(gl_panelMap);
    
    splitPane_2.setLeftComponent(panelMap);
    
    JPanel panelDebug = new JPanel();
    splitPane_2.setRightComponent(panelDebug);
    
    textAreaDebugInfo = new JTextArea();
    textAreaDebugInfo.setEditable(false);
    GroupLayout gl_panelDebug = new GroupLayout(panelDebug);
    gl_panelDebug.setHorizontalGroup(
      gl_panelDebug.createParallelGroup(Alignment.LEADING)
        .addComponent(textAreaDebugInfo, GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
    );
    gl_panelDebug.setVerticalGroup(
      gl_panelDebug.createParallelGroup(Alignment.LEADING)
        .addComponent(textAreaDebugInfo, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
    );
    panelDebug.setLayout(gl_panelDebug);
    splitPane_2.setDividerLocation(601);
    splitPane.setDividerLocation(150);
  }

  @Override
  public void modelPropertyChange(final PropertyChangeEvent evt) {
//    if (evt.getPropertyName().equals(DefaultController.ELEMENT_X_PROPERTY)) {
//      String newStringValue = evt.getNewValue().toString();
//      xPositionTextField.setText(newStringValue);
//    } else if (evt.getPropertyName().equals(DefaultController.ELEMENT_Y_PROPERTY)) {
//      String newStringValue = evt.getNewValue().toString();
//      yPositionTextField.setText(newStringValue);
//    }
    if (evt.getPropertyName().equals(DefaultController.ELEMENT_MAP)) {
      int[][] newStringValue = (int[][]) evt.getNewValue();
      paintMap(newStringValue);
    } else if(evt.getPropertyName().equals(DefaultController.ELEMENT_DEBUG_INFO)) {
      String debugInfo = (String) evt.getNewValue();
      textAreaDebugInfo.setText(debugInfo);
    } else if(evt.getPropertyName().equals(DefaultController.ELEMENT_PATHWAYFINDING_RUNNING)) {
      if(!chckbxAutoSearching.isSelected()) {
        Boolean runnung = (Boolean) evt.getNewValue();
        if(runnung.booleanValue()) {
          spnMsProStep.setEnabled(false);
          btnStartSearch.setEnabled(false);
          btnStopSearch.setEnabled(true);
        }else {
          spnMsProStep.setEnabled(true);
          btnStartSearch.setEnabled(true);
          btnStopSearch.setEnabled(false);
        }
      }
    }
  }

  private void paintMap(int grid[][]) {
    panelMap.setGrid(grid);
  }

  private void sendPointToController(int panel_x, int panel_y, MapElements value) {
    int blockWidth = panelMap.getWidth() / mapSizeX;
    int blockHeight = panelMap.getHeight() / mapSizeY;

    int blockX = panel_x / blockWidth;
    int blockY = panel_y / blockHeight;

    if (blockX < 0) {
      blockX = 0;
    }

    if (blockY < 0) {
      blockY = 0;
    }

    if (blockX >= mapSizeX) {
      blockX = mapSizeX - 1;
    }
    if (blockY >= mapSizeY) {
      blockY = mapSizeY - 1;
    }

    controller.setPointOnMap(blockX, blockY, value);
  }
  
  private void editMover() {
    controller.setMover(jCbMoverSelector.getItemAt(jCbMoverSelector.getSelectedIndex()));
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
    controller.startPathfinding((Integer)spnMsProStep.getValue());
  }
  
  private void stopAutoPathfindung() {
    controller.stopPathfinding();
  }
}
