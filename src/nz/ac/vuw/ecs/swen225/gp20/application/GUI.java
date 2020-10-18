package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public abstract class GUI {

  private final JFrame frame = new JFrame("Chaps Challenge");
  private JComponent drawing;
  private static final int DEFAULT_DISPLAY_SIZE = 800;
  private static final int GAP_SIZE = 25;
  private static final int BORDER_SIZE = 25;

  public enum direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }

  public void initialise() {

    Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY);

    drawing = new JComponent() {
      protected void paintComponent(Graphics g) {
        redraw(g, getDrawingAreaDimension());
      }
    };
    drawing.setVisible(true);
    drawing.setBorder(border);

    GridLayout gl = new GridLayout(2, 0, 0, 0);

    JLabel timeText = new JLabel("Time");
    JLabel timeLeft = new JLabel("60.0");


    JPanel time = new JPanel(gl);
    time.setBackground(Color.LIGHT_GRAY);
    time.setBorder(border);
    time.add(timeText);
    time.add(timeLeft);

    JLabel chipsText = new JLabel("Chips");

    JPanel chips = new JPanel(gl);
    chips.setBackground(Color.LIGHT_GRAY);
    chips.setBorder(border);
    chips.add(chipsText);

    JLabel itemsText = new JLabel("Items");

    JPanel items = new JPanel(gl);
    items.setBackground(Color.LIGHT_GRAY);
    items.setBorder(border);
    items.add(itemsText);

    JLabel lvlText = new JLabel("Level");

    JPanel lvl = new JPanel(gl);
    lvl.setBackground(Color.LIGHT_GRAY);
    lvl.setBorder(border);
    lvl.add(lvlText);

    Action moveUp = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(direction.UP);
      }
    };

    Action moveDown = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(direction.DOWN);
      }
    };

    Action moveRight = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(direction.RIGHT);
      }
    };

    Action moveLeft = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(direction.LEFT);
      }
    };

    Action startRecord = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        startRec();
      }
    };

    Action stopRecord = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        endRec();
      }
    };

    JButton up = new JButton();
    up.setBorderPainted(false);
    up.setName("up");
    up.setBackground(Color.LIGHT_GRAY);
    up.setFocusPainted(false);
    up.addActionListener(moveUp);

    JButton down = new JButton();
    down.setBorderPainted(false);
    down.setName("down");
    down.setBackground(Color.LIGHT_GRAY);
    down.setFocusPainted(false);
    down.addActionListener(moveDown);

    JButton left = new JButton();
    left.setBorderPainted(false);
    left.setName("left");
    left.setBackground(Color.LIGHT_GRAY);
    left.setFocusPainted(false);
    left.addActionListener(moveLeft);

    JButton right = new JButton();
    right.setBorderPainted(false);
    right.setName("right");
    right.setBackground(Color.LIGHT_GRAY);
    right.setFocusPainted(false);
    right.addActionListener(moveRight);

    JButton record = new JButton();
    record.setBorderPainted(false);
    record.setToolTipText("Start recording");
    record.setName("record");
    record.setBackground(Color.LIGHT_GRAY);
    record.setFocusPainted(false);
    record.addActionListener(startRecord);

    JButton stopRec = new JButton();
    stopRec.setBorderPainted(false);
    stopRec.setToolTipText("Stop recording");
    stopRec.setName("stop_record");
    stopRec.setBackground(Color.LIGHT_GRAY);
    stopRec.setFocusPainted(false);
    stopRec.addActionListener(stopRecord);

    JPanel buttons = new JPanel(new GridLayout(2, 3, 5, 5));
    buttons.setBackground(Color.LIGHT_GRAY);
    buttons.setBorder(border);
    buttons.add(stopRec);
    buttons.add(up);
    buttons.add(record);
    buttons.add(left);
    buttons.add(down);
    buttons.add(right);

    buttons.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        try {
          Component[] comp = buttons.getComponents();
          for (int i = 0; i < comp.length; i++) {
            if (comp[i] instanceof JButton) {
              JButton btn = (JButton) comp[i];
              if (btn.getName() == null) continue;
              Dimension size = btn.getSize();
              Insets insets = btn.getInsets();
              size.width -= insets.left + insets.right;
              size.height -= insets.top + insets.bottom;
              Image img = ImageIO.read(getClass().getResource("/assets/" + btn.getName() + ".png"));
              Image scaled = img.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
              btn.setIcon(new ImageIcon(scaled));
            }
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    InputMap inputMap = buttons.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "up");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, true), "record");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "stop");
    buttons.getActionMap().put("right", moveRight);
    buttons.getActionMap().put("left", moveLeft);
    buttons.getActionMap().put("down", moveDown);
    buttons.getActionMap().put("up", moveUp);
    buttons.getActionMap().put("record", startRecord);
    buttons.getActionMap().put("stop", stopRecord);
    buttons.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");


    JPanel info = new JPanel(new GridLayout(5, 0, 0, 0));
    info.setBackground(Color.LIGHT_GRAY);
    info.setBorder(border);
    info.add(lvl);
    info.add(time);
    info.add(chips);
    info.add(items);
    info.add(buttons);

    JPanel display = new JPanel();
    display.setBackground(new Color(0,204,0));
    display.setPreferredSize(new Dimension(DEFAULT_DISPLAY_SIZE, (int) (DEFAULT_DISPLAY_SIZE/1.6)));
    display.setLayout(null);

    display.add(drawing);
    display.add(info);
    int start = (DEFAULT_DISPLAY_SIZE-(DEFAULT_DISPLAY_SIZE/2+DEFAULT_DISPLAY_SIZE/4+GAP_SIZE))/2;
    drawing.setBounds(start, BORDER_SIZE, DEFAULT_DISPLAY_SIZE/2,
            DEFAULT_DISPLAY_SIZE/2);

    info.setBounds(DEFAULT_DISPLAY_SIZE/2+start+GAP_SIZE, BORDER_SIZE, DEFAULT_DISPLAY_SIZE/4,
            DEFAULT_DISPLAY_SIZE/2);



    JMenuItem newGameOne = new JMenuItem("New Game");
    KeyStroke NGO = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK);
    newGameOne.setAccelerator(NGO);
    newGameOne.addActionListener(ev -> {
      newGame(timeLeft);
      redraw();
    });

    JMenuItem restart = new JMenuItem("Restart");
    KeyStroke res = KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK);
    restart.setAccelerator(res);
    restart.addActionListener(ev -> {
      restartRound();
      redraw();
    });

    JMenuItem exit = new JMenuItem("Exit");
    KeyStroke ex = KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK);
    exit.setAccelerator(ex);
    exit.addActionListener(ev -> {
      exitGame();
      redraw();
    });

    JMenuItem saveExit = new JMenuItem("Save & Exit");
    KeyStroke saveEx = KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK);
    saveExit.setAccelerator(saveEx);
    saveExit.addActionListener(ev -> {
      exitSaveGame();
      redraw();
    });

    JMenuItem load = new JMenuItem("Load");
    KeyStroke lo = KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_DOWN_MASK);
    load.setAccelerator(lo);
    load.addActionListener(ev -> {
      loadGame(timeLeft);
      redraw();
    });

    JMenuItem replay = new JMenuItem("Save & Exit");
    KeyStroke rep = KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK);
    replay.setAccelerator(rep);
    replay.addActionListener(ev -> {
      replayGame();
      redraw();
    });


    JMenu menu = new JMenu("Menu");
    menu.setPreferredSize(new Dimension(45, 15));
    menu.add(newGameOne);
    menu.add(saveExit);
    menu.add(restart);
    menu.add(load);
    menu.add(exit);


    JMenuItem pause = new JMenuItem("Pause");
    KeyStroke pa = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0);
    pause.setAccelerator(pa);
    pause.addActionListener(ev -> {
      pauseGame();
      redraw();
    });

    JMenuItem resume = new JMenuItem("Resume");
    KeyStroke resu = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
    resume.setAccelerator(resu);
    resume.addActionListener(ev -> {
      resumeGame();
      redraw();
    });

    JMenuItem recStart = new JMenuItem("Start Recording");
    recStart.addActionListener(ev -> {
      startRec();
      redraw();
    });

    JMenuItem recEnd = new JMenuItem("End Recording");
    recEnd.addActionListener(ev -> {
      endRec();
      redraw();
    });



    JMenu options = new JMenu("Options");
    options.setPreferredSize(new Dimension(60, 15));
    options.add(resume);
    options.add(pause);
    options.add(recStart);
    options.add(recEnd);

    JMenuItem levelOne = new JMenuItem("Level One");
    levelOne.addActionListener(ev -> System.out.println("Play Level One"));

    JMenuItem levelTwo = new JMenuItem("Level Two");
    load.addActionListener(ev -> System.out.println("Play Level Two"));

    JMenu level = new JMenu("Level");
    level.setPreferredSize(new Dimension(45, 15));
    level.add(levelOne);
    level.add(levelTwo);

    /*JMenuItem up = new JMenuItem("Press the UP key to move up");
    KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
    up.setAccelerator(upKey);
    up.addActionListener(e -> {
      movePlayer(direction.UP);
      redraw();
    });

    JMenuItem down = new JMenuItem("Press the DOWN key to move down");
    KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
    down.setAccelerator(downKey);
    down.addActionListener(e -> {
      movePlayer(direction.DOWN);
      redraw();
    });

    JMenuItem right = new JMenuItem("Press the RIGHT key to move right");
    KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
    right.setAccelerator(rightKey);
    right.addActionListener(e -> {
      movePlayer(direction.RIGHT);
      redraw();
    });

    JMenuItem left = new JMenuItem("Press the LEFT key to move left");
    KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
    left.setAccelerator(leftKey);
    left.addActionListener(e -> {
      movePlayer(direction.LEFT);
      redraw();
    });*/

    JMenu help = new JMenu("Help");
    help.setPreferredSize(new Dimension(45, 15));
    /*help.add(up);
    help.add(down);
    help.add(right);
    help.add(left);*/

    JMenuBar controls = new JMenuBar();
    controls.add(menu);
    controls.add(options);
    controls.add(level);
    controls.add(help);
    controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

    Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    controls.setBorder(edge);

    /*
      Scales the window so that board is always a square and
      so that font scales as well.
     */

    frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();
        int value = Math.min(displayWidth, displayHeight)-BORDER_SIZE*2;
        if (value+value/2+BORDER_SIZE*2+GAP_SIZE > displayWidth) value = (displayWidth-BORDER_SIZE*2-GAP_SIZE)*2/3;
        int xPos = (displayWidth-(value+value/2+GAP_SIZE))/2;
        int yPos = (displayHeight-value)/2;
        drawing.setBounds(xPos, yPos, value, value);
        info.setBounds(value+xPos+GAP_SIZE, yPos, value/2, value);
        int textFontSize = info.getWidth()/12;setJLabel(lvlText, textFontSize);
        int infoFontSize = info.getWidth()/8;setJLabel(lvlText, textFontSize);
        setJLabel(chipsText, textFontSize);
        setJLabel(timeText, textFontSize);
        setJLabel(timeLeft, infoFontSize);
        setJLabel(itemsText, textFontSize);
        info.updateUI();
      }
    });

    frame.setMinimumSize(new Dimension(DEFAULT_DISPLAY_SIZE, (int) (DEFAULT_DISPLAY_SIZE/1.6)));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(controls, BorderLayout.NORTH);
    frame.add(display, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Replays the game from a save.
   */
  protected abstract void replayGame();

  /**
   * Redraws the entire board when called.
   *
   * @param g the graphics to draw the board.
   * @param d dimension of the drawing area.
   */
  protected abstract void redraw(Graphics g, Dimension d);

  /**
   * Moves the player on the board in the given
   * direction.
   *
   * @param dir is the direction that the player will
   *            move based on key pressed.
   */
  protected abstract void movePlayer(direction dir);

  /**
   * Ends the recording of moves and saves it to a
   * JSON file.
   */
  protected abstract void endRec();

  /**
   * Starts recording the moves taken.
   */
  protected abstract void startRec();

  /**
   * @param timeLeft is the Label to display time left
   *                 for current level.
   *
   */
  protected abstract void loadGame(JLabel timeLeft);

  /**
   * Resumes the game when called so timer continues
   * and player can move.
   */
  protected abstract void resumeGame();

  /**
   * Pauses the game when called so the timer stops
   * and the player can't move.
   */
  protected abstract void pauseGame();

  /**
   * @param timeLeft is the Label to display the current
   *                 time left.
   */
  protected abstract void newGame(JLabel timeLeft);


  /**
   * Saves the current state of the game to a JSON file
   * and then closes the game.
   */

  protected abstract void exitSaveGame();

  /**
   * Helper method used to scale the labels and the text
   * inside.
   *
   * @param label to scale
   * @param size is font size of the scaled text
   */
  private void setJLabel(JLabel label, int size) {
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(new Font(label.getName(), Font.BOLD, size));
  }

  /**
   * Restarts the current level that the player is on.
   */
  private void restartRound() {
    System.out.println("Restarts current level");
  }

  /**
   * Exits the game when the player chooses to, but
   * makes sure that the player is sure first.
   */
  private void exitGame() {
    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit? Your game will not be saved.", "Exit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      System.exit(0); // cleanly end the program.
    }
  }

  /**
   * Constructs the GUI object which initialises
   * all components such as JPanels and MenuItems.
   */
  public GUI() {
    initialise();
  }

  /**
   * redraws the frame.
   */
  public void redraw() {
    frame.repaint();
  }

  /**
   * @return the dimension of the drawing area.
   */
  public Dimension getDrawingAreaDimension() {
    return drawing.getSize();
  }
}