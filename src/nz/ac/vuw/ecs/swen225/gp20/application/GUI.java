package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public abstract class GUI {

  protected abstract void redraw(Graphics g, Dimension d);

  private JFrame frame = new JFrame("Chaps Challenge");
  private JComponent drawing;

  private static final int DEFAULT_DISPLAY_SIZE = 800;

  public GUI() {
    initialise();
  }

  public void redraw() {
    frame.repaint();
  }

  public Dimension getDrawingAreaDimension() {
    return drawing.getSize();
  }

  private static final int GAP_SIZE = 25;
  private static final int BORDER_SIZE = 25;
  private static final float timePerLevel = 60f;
  public JLabel timeLeft;
  public Timer timer;
  public boolean gamePaused = false;

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
    timeLeft = new JLabel("60.0");


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


    JPanel info = new JPanel(new GridLayout(4, 0, 0, 0));
    info.setBackground(Color.LIGHT_GRAY);
    info.setBorder(border);
    info.add(lvl);
    info.add(time);
    info.add(chips);
    info.add(items);

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
      restartGame();
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
      loadGame();
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

    JMenu options = new JMenu("Options");
    options.setPreferredSize(new Dimension(60, 15));
    options.add(resume);
    options.add(pause);

    JMenuItem levelOne = new JMenuItem("Level One");
    levelOne.addActionListener(ev -> System.out.println("Play Level One"));

    JMenuItem levelTwo = new JMenuItem("Level Two");
    load.addActionListener(ev -> System.out.println("Play Level Two"));

    JMenu level = new JMenu("Level");
    level.setPreferredSize(new Dimension(45, 15));
    level.add(levelOne);
    level.add(levelTwo);

    JMenuItem up = new JMenuItem("Press the UP key to move up");
    JMenuItem down = new JMenuItem("Press the DOWN key to move down");
    JMenuItem right = new JMenuItem("Press the RIGHT key to move right");
    JMenuItem left = new JMenuItem("Press the LEFT key to move left");

    JMenu help = new JMenu("Help");
    help.setPreferredSize(new Dimension(45, 15));
    help.add(up);
    help.add(down);
    help.add(right);
    help.add(left);

    JMenuBar controls = new JMenuBar();
    controls.add(menu);
    controls.add(options);
    controls.add(level);
    controls.add(help);
    controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

    Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    controls.setBorder(edge);


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

  private void setJLabel(JLabel label, int size) {
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(new Font(label.getName(), Font.BOLD, size));
  }

  public float timeElapsed = 0f; // Current time elapsed since start
  private void restartGame() {
    System.out.println("Starts new game at level 1");
    if (timeElapsed > 0f) {
      timer.stop();
      timeElapsed = 0f;
    }
    timer = new Timer(100, e -> {
      if (!gamePaused) {
        timeLeft.setText(String.valueOf(String.format("%.1f", timePerLevel - timeElapsed)));
        timeElapsed += 0.1f;
        if (timePerLevel - timeElapsed < 30) timeLeft.setForeground(new Color(227, 115, 14));
        if (timePerLevel - timeElapsed < 15) timeLeft.setForeground(Color.RED);
        if (timePerLevel - timeElapsed < 0) timer.stop();
      }
    });
    timer.start();
  }

  private void restartRound() {
    System.out.println("Restarts current level");
  }

  private void loadGame() {
    System.out.println("Loads a saved game");
  }

  private void exitSaveGame() {
    System.out.println("Save and exit");
  }

  private void exitGame() {
    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit? Your game will not be saved.", "Exit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      System.exit(0); // cleanly end the program.
    }
  }

  private void resumeGame() {
    System.out.println("Resume the game");
    gamePaused = false;
  }

  private void pauseGame() {
    System.out.println("Pauses the game");
    gamePaused = true;
  }
}