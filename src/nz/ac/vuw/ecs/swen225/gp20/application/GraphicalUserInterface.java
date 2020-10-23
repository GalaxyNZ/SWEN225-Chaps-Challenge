package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import nz.ac.vuw.ecs.swen225.gp20.maze.InfoTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;

public abstract class GraphicalUserInterface {

  private final JFrame frame = new JFrame("Chaps Challenge");
  private JComponent drawing;
  private static final int DEFAULT_DISPLAY_SIZE = 800;
  private static final int GAP_SIZE = 25;
  private static final int BORDER_SIZE = 25;
  private boolean asking = false;
  JPanel helpBox;
  private JLabel timeLeft;
  private JPanel inventory;
  private JLabel chipsRemaining;

  public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }


  /**
   * Initialises the Graphical user interface by
   * creating Swing components such as JPanels, JButtons,
   * and JMenus to interact with the game.
   */
  public void initialise() {

    // Border for all the JPanels
    Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY);

    // Drawing component to display board
    drawing = new JComponent() {
      protected void paintComponent(Graphics g) {
        redraw(g, getDrawingAreaDimension());
      }
    };
    drawing.setVisible(true);
    drawing.setBorder(border);

    GridLayout gl = new GridLayout(2, 0, 0, 0);

    final JLabel timeText = new JLabel("Time");
    timeLeft = new JLabel("60.0");

    setTimeLeft(timeLeft);

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

    chipsRemaining = new JLabel("0");
    chips.add(chipsRemaining, BorderLayout.CENTER);

    JLabel itemsText = new JLabel("Items");

    JPanel items = new JPanel(gl);
    items.setBackground(Color.LIGHT_GRAY);
    items.setBorder(border);
    items.add(itemsText);

    inventory = new JPanel(new GridLayout(1, 4, 5, 5));
    inventory.setBackground(Color.LIGHT_GRAY);

    JLabel itemOne = new JLabel();
    itemOne.setVisible(false);
    JLabel itemTwo = new JLabel();
    itemTwo.setVisible(false);
    JLabel itemThree = new JLabel();
    itemThree.setVisible(false);
    JLabel itemFour = new JLabel();
    itemFour.setVisible(false);

    inventory.add(itemOne);
    inventory.add(itemTwo);
    inventory.add(itemThree);
    inventory.add(itemFour);
    items.add(inventory);

    inventory.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateInventory(inventory);
      }
    });

    final JLabel lvlText = new JLabel("Level");
    JLabel lvlNumber = new JLabel("0");
    
    setLvlLabel(lvlNumber);

    JPanel lvl = new JPanel(gl);
    lvl.setBackground(Color.LIGHT_GRAY);
    lvl.setBorder(border);
    lvl.add(lvlText);
    lvl.add(lvlNumber);

    // Creates Actions so the JButtons can use shortcut keys
    final Action moveUp = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(Direction.UP);
        updateInfo();
      }
    };

    final Action moveDown = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(Direction.DOWN);
        updateInfo();
      }
    };

    final Action moveRight = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(Direction.RIGHT);
        updateInfo();
      }
    };

    final Action moveLeft = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer(Direction.LEFT);
        updateInfo();
      }
    };


    final Action startRecord = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        startRec();
      }
    };

    final Action stopRecord = new AbstractAction() {
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

    // Creates a JPanel for all the buttons
    JPanel buttons = new JPanel(new GridLayout(2, 3, 5, 5));
    buttons.setBackground(Color.LIGHT_GRAY);
    buttons.setBorder(border);
    buttons.add(stopRec);
    buttons.add(up);
    buttons.add(record);
    buttons.add(left);
    buttons.add(down);
    buttons.add(right);

    // Resizes the buttons based on the frame size
    buttons.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        try {
          Component[] comp = buttons.getComponents();
          for (Component component : comp) {
            if (component instanceof JButton) {
              JButton btn = (JButton) component;
              if (btn.getName() == null) {
                continue;
              }
              Dimension size = btn.getSize();
              Insets insets = btn.getInsets();
              size.width -= insets.left + insets.right;
              size.height -= insets.top + insets.bottom;
              Image img = ImageIO.read(getClass().getResource("/assets/" + btn.getName() + ".png"));
              Image scaled = img.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
              btn.setIcon(new ImageIcon(scaled));
            }
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    // This stops space bar activating buttons
    InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
    im.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
    im.put(KeyStroke.getKeyStroke("released SPACE"), "none");

    // Adds quick keys for the JButtons
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

    // Creates the Info JPanel to display the levels info on the right
    JPanel info = new JPanel(new GridLayout(5, 0, 0, 0));
    info.setBackground(Color.LIGHT_GRAY);
    info.setBorder(border);
    info.add(lvl);
    info.add(time);
    info.add(chips);
    info.add(items);
    info.add(buttons);

    // Creates the main display for the frame
    JPanel display = new JPanel();
    display.setBackground(new Color(31, 4, 56, 235));
    display.setPreferredSize(new Dimension(DEFAULT_DISPLAY_SIZE,
            (int) (DEFAULT_DISPLAY_SIZE / 1.6)));
    display.setLayout(null);

    display.add(drawing);
    display.add(info);
    int start = (DEFAULT_DISPLAY_SIZE
            - (DEFAULT_DISPLAY_SIZE / 2 + DEFAULT_DISPLAY_SIZE / 4 + GAP_SIZE)) / 2;
    drawing.setBounds(start, BORDER_SIZE, DEFAULT_DISPLAY_SIZE / 2,
            DEFAULT_DISPLAY_SIZE / 2);

    info.setBounds(DEFAULT_DISPLAY_SIZE / 2 + start + GAP_SIZE,
            BORDER_SIZE, DEFAULT_DISPLAY_SIZE / 4, DEFAULT_DISPLAY_SIZE / 2);


    JMenuItem newGameOne = new JMenuItem("New Game");
    KeyStroke newGameKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK);
    newGameOne.setAccelerator(newGameKeyStroke);
    newGameOne.addActionListener(ev -> {
      newGame(timeLeft);
      updateInventory(inventory);
      updateChips(chipsRemaining);
      redraw();
    });

    JMenuItem restart = new JMenuItem("Restart");
    KeyStroke res = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
    restart.setAccelerator(res);
    restart.addActionListener(ev -> {
      restartRound(timeLeft);
      updateInventory(inventory);
      updateChips(chipsRemaining);
      redraw();
    });

    JMenuItem exit = new JMenuItem("Exit");
    KeyStroke ex = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
    exit.setAccelerator(ex);
    exit.addActionListener(ev -> {
      exitGame();
      redraw();
    });

    JMenuItem saveExit = new JMenuItem("Save & Exit");
    KeyStroke saveEx = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
    saveExit.setAccelerator(saveEx);
    saveExit.addActionListener(ev -> {
      exitSaveGame();
      redraw();
    });

    JMenuItem load = new JMenuItem("Load");
    KeyStroke lo = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
    load.setAccelerator(lo);
    load.addActionListener(ev -> {
      loadGame(timeLeft);
      updateInventory(inventory);
      updateChips(chipsRemaining);
      redraw();
    });


    // Creates the Menu menu
    JMenu menu = new JMenu("Menu");
    menu.setPreferredSize(new Dimension(45, 15));
    menu.add(newGameOne);
    menu.add(saveExit);
    menu.add(restart);
    menu.add(load);
    menu.add(exit);


    JMenuItem pause = new JMenuItem("Pause");
    KeyStroke pa = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
    pause.setAccelerator(pa);
    pause.addActionListener(ev -> {
      pauseGame();
      redraw();
    });

    JMenuItem resume = new JMenuItem("Resume");
    KeyStroke resu = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    resume.setAccelerator(resu);
    resume.addActionListener(ev -> {
      resumeGame();
      redraw();
    });

    // Creates the Options Menu
    JMenu options = new JMenu("Options");
    options.setPreferredSize(new Dimension(60, 15));
    options.add(resume);
    options.add(pause);

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

    JMenuItem replay = new JMenuItem("Replay");
    replay.addActionListener(ev -> {
      replayGame(timeLeft);
      redraw();
    });

    JMenuItem iterateRep = new JMenuItem("Iterate Replay");
    KeyStroke iterate = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
    iterateRep.setAccelerator(iterate);
    iterateRep.addActionListener(ev -> {
      iterateReplay();
      redraw();
    });

    JMenuItem autoRep = new JMenuItem("Auto Replay");
    autoRep.addActionListener(ev -> {
      if (getCurrentState() == Main.State.REPLAYING) {
        boolean validInput = false;
        while (!validInput) {
          String delayString = JOptionPane.showInputDialog(frame,
                  "How quick do you want the replay speed to be? (1000-3000 milliseconds)", null);
          if (delayString == null) {
            break;
          }
          if (isNumeric(delayString)) {
            int delay = Integer.parseInt(delayString);
            if (delay >= 1000 && delay <= 3000) {
              validInput = true;
              autoReplay(delay);
              redraw();
            }
          }
        }
      }
    });

    // Creates the Record and Replay Menu
    JMenu replayMenu = new JMenu("Rec & Replay");
    menu.setPreferredSize(new Dimension(45, 15));
    replayMenu.add(replay);
    replayMenu.add(recStart);
    replayMenu.add(recEnd);
    replayMenu.add(autoRep);
    replayMenu.add(iterateRep);

    JPanel helpControls = new JPanel(new GridLayout(5, 1, 5, 5));
    helpControls.add(new JLabel("   CONTROLS"));
    JLabel arrow = new JLabel("     - Use the arrow keys or the buttons move");
    JLabel rec = new JLabel("     - Click the right button (next to the UP arrow) "
            + "to start recording");
    JLabel stop = new JLabel("     - Click the left button (next to the UP arrow) "
            + "to stop recording");
    try {
      final Class<?> thisClass = getClass();
      Image img = ImageIO.read(thisClass.getResource("/assets/up.png"));
      Image scaled = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
      arrow.setIcon(new ImageIcon(scaled));
      img = ImageIO.read(thisClass.getResource("/assets/record.png"));
      scaled = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
      rec.setIcon(new ImageIcon(scaled));
      img = ImageIO.read(thisClass.getResource("/assets/stop_record.png"));
      scaled = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
      stop.setIcon(new ImageIcon(scaled));
    } catch (IOException e) {
      e.printStackTrace();
    }
    helpControls.add(arrow);
    helpControls.add(rec);
    helpControls.add(stop);

    JPanel helpInstructions = new JPanel(new GridLayout(5, 1, 5, 5));
    helpInstructions.add(new JLabel("HOW TO PLAY"));
    JLabel key = new JLabel("  - Doors can only be opened with the key corresponding to "
            + "its colour");
    JLabel token = new JLabel("  - Collect all the tokens to open up the exit and then leave");
    try {
      final Class<?> thisClass = getClass();
      Image img = ImageIO.read(thisClass.getResource("/assets/RKey.png"));
      Image scaled = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
      key.setIcon(new ImageIcon(scaled));
      img = ImageIO.read(thisClass.getResource("/assets/Treasure.png"));
      scaled = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
      token.setIcon(new ImageIcon(scaled));
    } catch (IOException e) {
      e.printStackTrace();
    }

    helpInstructions.add(token);
    helpInstructions.add(key);
    helpInstructions.add(new JLabel("  - Watch out for the enemies, they can kill you"));

    helpBox = new JPanel(new GridLayout(1, 2, 5, 5));
    helpBox.setPreferredSize(new Dimension(1000, 200));
    helpBox.add(helpControls);
    helpBox.add(helpInstructions);

    JMenuItem showHelp = new JMenuItem("Show Help");
    showHelp.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, helpBox, "Help", JOptionPane.PLAIN_MESSAGE);
      }
    });

    JMenu help = new JMenu("Help");
    help.setPreferredSize(new Dimension(45, 15));
    help.add(showHelp);

    // creates the Menu at the top and adds all menu items
    JMenuBar controls = new JMenuBar();
    controls.add(menu);
    controls.add(options);
    controls.add(replayMenu);
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
        int value = Math.min(displayWidth, displayHeight) - BORDER_SIZE * 2;
        if (value + value / 2 + BORDER_SIZE * 2 + GAP_SIZE > displayWidth) {
          value = (displayWidth - BORDER_SIZE * 2 - GAP_SIZE) * 2 / 3;
        }
        int positionX = (displayWidth - (value + value / 2 + GAP_SIZE)) / 2;
        int positionY = (displayHeight - value) / 2;
        drawing.setBounds(positionX, positionY, value, value);
        info.setBounds(value + positionX + GAP_SIZE, positionY, value / 2, value);
        int textFontSize = info.getWidth() / 12;
        int infoFontSize = info.getWidth() / 8;

        setLabel(lvlText, textFontSize);
        setLabel(chipsText, textFontSize);
        setLabel(chipsRemaining, infoFontSize);
        setLabel(timeText, textFontSize);
        setLabel(timeLeft, infoFontSize);
        setLabel(itemsText, textFontSize);
        setLabel(lvlNumber, infoFontSize);
      }
    });

    // Sets the initial frame and adds all components
    frame.setMinimumSize(new Dimension(DEFAULT_DISPLAY_SIZE, (int) (DEFAULT_DISPLAY_SIZE / 1.6)));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(controls, BorderLayout.NORTH);
    frame.add(display, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Updates the current info and checks if
   * info should be displayed.
   */
  private void updateInfo() {
    updateInventory(inventory);
    updateChips(chipsRemaining);
    redraw();
    if (getCurrentTile() instanceof InfoTile) {
      JOptionPane.showMessageDialog(frame, helpBox, "Help", JOptionPane.PLAIN_MESSAGE);
    }
  }

  /**
   * Sets the JLabel to display level number.
   *
   * @param lvlNumber is JLabel that will be the
   *                  result of the step.
   */
  protected abstract void setLvlLabel(JLabel lvlNumber);

  /**
   * Checks the current game state to determine
   * if the game is over and whether they have
   * won. If game is over it ask if they want
   * to play again and either restarts the
   * level/game or closes the game.
   *
   * @param timeLeft is the JLabel to display
   *                 time left.
   */
  private void checkGameState(JLabel timeLeft) {
    if (!asking && (getCurrentState() == Main.State.GAME_OVER
            || getCurrentState() == Main.State.GAME_WON)) {
      int result = JOptionPane.NO_OPTION;
      while (result == JOptionPane.NO_OPTION) {
        asking = true;
        if (getCurrentState() == Main.State.GAME_OVER) {
          result = JOptionPane.showConfirmDialog(null,
                  "You Lost! Would you like to try again?", "Game Over",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE);
        } else {
          result = JOptionPane.showConfirmDialog(null,
                  "You Won! Would you like to play again?", "Game Won",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE);
        }
        if (result == JOptionPane.NO_OPTION) {
          System.exit(0); // cleanly end the program.
        }
        if (getCurrentState() == Main.State.GAME_OVER) {
          restartRound(timeLeft);
        } else {
          newGame(timeLeft);
        }
        asking = false;
      }
    }
  }

  /**
   * Updates the JLabel to display the current number
   * of chips left to collect on the GUI.
   *
   * @param chipsRemaining is a JLabel that displays the
   *                       number of chips remaining.
   */
  private void updateChips(JLabel chipsRemaining) {
    chipsRemaining.setText(Integer.toString(getChipsRemaining()));
  }

  /**
   * Updates the inventory in the GUI by drawing
   * the items on the JPanel, scaling applies.
   *
   * @param inventory is a JPanel that displays
   *                  the players inventory.
   */
  private void updateInventory(JPanel inventory) {
    ArrayList<Item> items = getItems();
    if (items == null) {
      return;
    }
    Component[] components = inventory.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (i < items.size()) {
        String itemColour = getItems().get(i).getColor();
        try {
          final Class<?> thisClass = getClass();
          Image img = ImageIO.read(thisClass.getResource("/assets/" + itemColour + "Key.png"));
          Image scaled = img.getScaledInstance(components[i].getWidth(),
                  components[i].getHeight(), java.awt.Image.SCALE_SMOOTH);
          if (components[i] instanceof JLabel) {
            ((JLabel) components[i]).setIcon(new ImageIcon(scaled));
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        components[i].setVisible(true);
      } else {
        components[i].setVisible(false);
      }
    }
  }

  /**
   * redraws the frame.
   */
  public void redraw() {
    updateChips(chipsRemaining);
    updateInventory(inventory);
    checkGameState(timeLeft);
    frame.repaint();
  }

  /**
   * Redraws the entire board when called.
   *
   * @param g the graphics to draw the board.
   * @param d dimension of the drawing area.
   */
  protected abstract void redraw(Graphics g, Dimension d);

  /**
   * Sets the JLabel in main to the JLabel in
   * the GUI so it can be update frequently.
   *
   * @param timeLeft is the JLabel to display
   *                 time left.
   */
  protected abstract void setTimeLeft(JLabel timeLeft);

  /**
   * Gets the current tile that the player is standing
   * on at the time of being called.
   *
   * @return the current tile that the player is on
   */
  protected abstract Tile getCurrentTile();

  /**
   * Restarts the game at the current level.
   *
   * @param timeLeft is the JLabel to display
   *                 time left.
   */
  protected abstract void restartRound(JLabel timeLeft);

  /**
   * Replays the recording automatically so user
   * doesn't have to iterate through each step.
   *
   * @param delay is the time in milliseconds
   *              that it will replay each step.
   */
  protected abstract void autoReplay(int delay);

  /**
   * Gets the current state of the game from main.
   *
   * @return the current state.
   */
  protected abstract Main.State getCurrentState();

  /**
   * Iterates through one movement in when
   * replaying.
   */
  protected abstract void iterateReplay();

  /**
   * Replays the game from a save.
   */
  protected abstract void replayGame(JLabel timeLeft);

  /**
   * Gets the number of chips remaining for
   * the current level.
   *
   * @return the number of chips remaining.
   */
  protected abstract int getChipsRemaining();

  /**
   * Gets the items that the player is holding.
   *
   * @return an ArrayList of items.
   */
  protected abstract ArrayList<Item> getItems();

  /**
   * Moves the player on the board in the given
   * direction.
   *
   * @param dir is the direction that the player will
   *            move based on key pressed.
   */
  protected abstract void movePlayer(Direction dir);

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
   * Calls load game inside Persistence to allow the user
   * to select which file they want to load.
   *
   * @param timeLeft is the Label to display time left
   *                 for current level.
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
   * Creates a new game starting from level one by
   * calling a new game method insider Persistence.
   *
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
   * @param size  is font size of the scaled text
   */
  private void setLabel(JLabel label, int size) {
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(new Font(label.getName(), Font.BOLD, size));
  }

  /**
   * Exits the game when the player chooses to, but
   * makes sure that the player is sure first.
   */
  private void exitGame() {
    int result = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to quit? Your game will not be saved.", "Exit Game",
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
  public GraphicalUserInterface() {
    initialise();
  }

  /**
   * Reutns the dimension of the drawing area
   * so that Renderer can display the board.
   *
   * @return the dimension of the drawing area.
   */
  public Dimension getDrawingAreaDimension() {
    return drawing.getSize();
  }

  /**
   * Checks if the current string is a number.
   * Used when checking user input.
   *
   * @param str is a String that is being checked
   *            if it is a number.
   * @return true if str is a number.
   */
  public static boolean isNumeric(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}