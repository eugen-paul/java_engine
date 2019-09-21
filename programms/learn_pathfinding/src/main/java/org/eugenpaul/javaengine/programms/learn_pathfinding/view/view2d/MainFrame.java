package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;

import javax.swing.JSplitPane;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;

public class MainFrame extends JFrame implements AbstractViewPanel {

  private MapElements selector = MapElements.NOPE;

  /**
   * 
   */
  private static final long serialVersionUID = -2589772721955644662L;
  private JPanel contentPane;
  private DefaultController controller;
  private PanelMap panelMap;
  
  private int mapSizeX;
  private int mapSizeY;
  private JComboBox<MoverTyp> jCbMoverSelector;

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
    setBounds(100, 100, 860, 700);
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
    panelSettings.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    JButton btnSetStart = new JButton("Set Start");
    btnSetStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selector = MapElements.START;
      }
    });
    panelSettings.add(btnSetStart);

    JButton btnSetEnd = new JButton("Set End");
    btnSetEnd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selector = MapElements.END;
      }
    });
    panelSettings.add(btnSetEnd);

    JButton btnSetWall = new JButton("Set Wall");
    btnSetWall.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selector = MapElements.WALL;
      }
    });
    panelSettings.add(btnSetWall);

    JButton btnSetPit = new JButton("Set Pit");
    btnSetPit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selector = MapElements.MUD;
      }
    });
    panelSettings.add(btnSetPit);

    JButton btnClearPoint = new JButton("Clear Point");
    btnClearPoint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selector = MapElements.NOPE;
      }
    });
    panelSettings.add(btnClearPoint);

    JButton btnClearMap = new JButton("Clear Map");
    panelSettings.add(btnClearMap);
    
    jCbMoverSelector = new JComboBox<>();
    for(MoverTyp typ: MoverTyp.values()) {
      jCbMoverSelector.addItem(typ);
    }
    jCbMoverSelector.setSelectedIndex(0);
    jCbMoverSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editMover();
      }
    });
    panelSettings.add(jCbMoverSelector);

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
    splitPane.setRightComponent(panelMap);
    GroupLayout gl_panelMap = new GroupLayout(panelMap);
    gl_panelMap.setHorizontalGroup(gl_panelMap.createParallelGroup(Alignment.LEADING).addGap(0, 711, Short.MAX_VALUE));
    gl_panelMap.setVerticalGroup(gl_panelMap.createParallelGroup(Alignment.LEADING).addGap(0, 599, Short.MAX_VALUE));
    panelMap.setLayout(gl_panelMap);
    splitPane.setDividerLocation(120);
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

}
