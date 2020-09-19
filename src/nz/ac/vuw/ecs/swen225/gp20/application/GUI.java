package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import java.awt.*;

public abstract class GUI {

  protected abstract void redraw(Graphics g, Dimension d);

  private JFrame frame = new JFrame("Chaps Challenge");
  private JComponent drawing;

  private static final int DEFAULT_DRAWING_HEIGHT = 550;
  private static final int DEFAULT_DRAWING_WIDTH = 600;

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


    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(drawing);

    frame.pack();
    frame.setVisible(true);

  }
}