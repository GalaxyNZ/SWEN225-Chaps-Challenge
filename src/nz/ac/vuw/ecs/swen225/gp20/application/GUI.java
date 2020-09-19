package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUI {

  protected abstract void redraw(Graphics g, Dimension d);

  private JFrame frame = new JFrame("Chaps Challenge");
  private JComponent drawing;

  private static final int DEFAULT_DRAWING_HEIGHT = 500;
  private static final int DEFAULT_DRAWING_WIDTH = 500;

  public GUI() {
    initialise();
  }

  public void redraw() {
    frame.repaint();
  }

  public Dimension getDrawingAreaDimension() {
    return drawing.getSize();
  }


  public void initialise() {
    drawing = new JComponent() {
      protected void paintComponent(Graphics g) {
        redraw(g, getDrawingAreaDimension());
      }
    };

    drawing.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH,
            DEFAULT_DRAWING_HEIGHT));

    drawing.setVisible(true);

    JPanel info = new JPanel(new GridLayout(3, 0, 5, 5));
    info.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH/3, DEFAULT_DRAWING_HEIGHT));
    info.setBackground(Color.BLACK);

    JPanel display = new JPanel();
    display.setBackground(Color.GREEN);

    display.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
    display.add(drawing);
    display.add(info);

    JMenuItem newGameOne = new JMenuItem("New Game");
    newGameOne.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Starts new game at level 1");
      }
    });

    JMenuItem restart = new JMenuItem("Restart");
    restart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Restarts current level");
      }
    });

    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Exit without saving");
      }
    });

    JMenuItem saveExit = new JMenuItem("Save & Exit");
    saveExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Save and exit");
      }
    });

    JMenuItem load = new JMenuItem("Load");
    load.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Loads a saved game");
      }
    });

    JMenu menu = new JMenu("Menu");
    menu.setPreferredSize(new Dimension(45, 15));
    menu.add(newGameOne);
    menu.add(restart);
    menu.add(exit);
    menu.add(saveExit);
    menu.add(load);

    JMenuItem pause = new JMenuItem("Pause");
    pause.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Pauses the game");
      }
    });

    JMenuItem resume = new JMenuItem("Resume");
    resume.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.out.println("Resume the game");
      }
    });

    JMenu options = new JMenu("Options");
    options.setPreferredSize(new Dimension(60, 15));
    options.add(pause);
    options.add(resume);

    JMenu level = new JMenu("Level");
    level.setPreferredSize(new Dimension(45, 15));

    JMenu help = new JMenu("Help");
    help.setPreferredSize(new Dimension(45, 15));

    JMenuBar controls = new JMenuBar();
    controls.add(menu);
    controls.add(options);
    controls.add(level);
    controls.add(help);
    controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

    Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    controls.setBorder(edge);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(controls, BorderLayout.NORTH);
    frame.add(display, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);

  }
}