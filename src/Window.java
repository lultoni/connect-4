import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class Window implements MouseListener {

    static JFrame frame = new JFrame("Connect Four by CallMeLee");
    static Color whiteplayer = new Color(246, 72, 41);
    static Color background = new Color(86, 78, 78);
    static Color blackplayer = new Color(253, 167, 0);
    static Color emptyplayer = new Color(129, 125, 125);

    static void startMenu() {
        frame.setBounds(10, 10, 820, 620);
        AtomicInteger wp = new AtomicInteger();
        AtomicInteger bp = new AtomicInteger();
        JSlider dsl = new JSlider();
        dsl.setBounds(210, 500, 300, 10);
        dsl.setMinimum(0);
        dsl.setMaximum(20);
        dsl.setValue(10);
        JPanel pn = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setFont(new Font( "SansSerif", Font.BOLD, 50));
                g.drawString("Connect Four by CallMeLee", 90, 100);
                g.setFont(new Font( "SansSerif", Font.BOLD, 20));
                g.drawString("0", 200, 500);
                g.drawString("20", 520, 500);
                String dsv = String.valueOf(dsl.getValue());
                g.drawString(dsv, 350, 525);
            }
        };
        dsl.addChangeListener(l -> frame.repaint());
        JToggleButton jtb_white_human = new JToggleButton("W: Human Player");
        jtb_white_human.setBounds(225, 250, 150, 50);
        jtb_white_human.addChangeListener(e -> wp.set(0));
        JToggleButton jtb_white_bot_random = new JToggleButton("W: Bot Random");
        jtb_white_bot_random.setBounds(225, 300, 150, 50);
        jtb_white_bot_random.addChangeListener(e -> wp.set(1));
        JToggleButton jtb_white_bot_minimax = new JToggleButton("W: Bot Minimax");
        jtb_white_bot_minimax.setBounds(225, 350, 150, 50);
        jtb_white_bot_minimax.addChangeListener(e -> wp.set(2));
        JToggleButton jtb_white_bot_mcts = new JToggleButton("W: Bot MCTS");
        jtb_white_bot_mcts.setBounds(225, 400, 150, 50);
        jtb_white_bot_mcts.addChangeListener(e -> wp.set(3));
        JToggleButton jtb_black_human = new JToggleButton("B: Human Player");
        jtb_black_human.setBounds(425, 250, 150, 50);
        jtb_black_human.addChangeListener(e -> bp.set(0));
        JToggleButton jtb_black_bot_random = new JToggleButton("B: Bot Random");
        jtb_black_bot_random.setBounds(425, 300, 150, 50);
        jtb_black_bot_random.addChangeListener(e -> bp.set(1));
        JToggleButton jtb_black_bot_minimax= new JToggleButton("B: Bot Minimax");
        jtb_black_bot_minimax.setBounds(425, 350, 150, 50);
        jtb_black_bot_minimax.addChangeListener(e -> bp.set(2));
        JToggleButton jtb_black_bot_mcts= new JToggleButton("B: Bot MCTS");
        jtb_black_bot_mcts.setBounds(425, 400, 150, 50);
        jtb_black_bot_mcts.addChangeListener(e -> bp.set(3));
        JButton jb_start = new JButton("Start Game");
        jb_start.setBounds(350, 150, 100, 50);
        jb_start.addActionListener(e -> {
            if (!wp.equals(new AtomicInteger()) && !wp.equals(new AtomicInteger())) {
                Main.pt1 = wp.get();
                Main.pt2 = bp.get();
                Main.depth = dsl.getValue();
                frame.remove(jtb_black_human);
                frame.remove(jtb_black_bot_random);
                frame.remove(jtb_black_bot_minimax);
                frame.remove(jtb_black_bot_mcts);
                frame.remove(jtb_white_human);
                frame.remove(jtb_white_bot_random);
                frame.remove(jtb_white_bot_minimax);
                frame.remove(jtb_white_bot_mcts);
                frame.remove(jb_start);
                frame.remove(dsl);
                frame.remove(pn);
                frame.repaint();
                Main.gameStart = true;
            }
        });
        frame.add(jtb_black_human);
        frame.add(jtb_black_bot_random);
        frame.add(jtb_black_bot_minimax);
        frame.add(jtb_white_bot_mcts);
        frame.add(jtb_black_bot_mcts);
        frame.add(jtb_white_human);
        frame.add(jtb_white_bot_random);
        frame.add(jtb_white_bot_minimax);
        frame.add(jb_start);
        frame.add(dsl);
        frame.add(pn);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static void gameWindow() {
        int[] playingBoard = Board.getInstance().getPlayingBoard();
//        frame.setBounds(10, 10, 820, 620);
        JPanel pn = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setFont(new Font( "SansSerif", Font.BOLD, 50));
                // BOARD BACKGROUND
                g.setColor(background);
                g.fillRect(10, 10, 350, 300);
                // BOARD LINES
                g.setColor(Color.black);
                g.drawLine(10, 10, 360, 10);
                g.drawLine(10, 60, 360, 60);
                g.drawLine(10, 110, 360, 110);
                g.drawLine(10, 160, 360, 160);
                g.drawLine(10, 210, 360, 210);
                g.drawLine(10, 260, 360, 260);
                g.drawLine(10, 310, 360, 310);
                g.drawLine(10, 10, 10, 310);
                g.drawLine(60, 10, 60, 310);
                g.drawLine(110, 10, 110, 310);
                g.drawLine(160, 10, 160, 310);
                g.drawLine(210, 10, 210, 310);
                g.drawLine(260, 10, 260, 310);
                g.drawLine(310, 10, 310, 310);
                g.drawLine(360, 10, 360, 310);
                // BOARD PIECES
                for (int row = 0; row < 6; row++) {
                    for (int column = 0; column < 7; column++) {
                        if (playingBoard[row * 6 + column + row] == 1) {
                            g.setColor(whiteplayer);
                        } else if (playingBoard[row * 6 + column + row] == -1) {
                            g.setColor(blackplayer);
                        } else {
                            g.setColor(emptyplayer);
                        }
                        g.fillOval(column * 50 + 13, row * 50 + 13, 44, 44);
                    }
                }
                // WINNER TEXT
                String[] winArr = winStonesFinder(Board.getInstance().getPlayingBoard());
                int row = Integer.parseInt(winArr[0]) * 50 + 13 + 12;
                int column = Integer.parseInt(winArr[1]) * 50 + 13 + 32;
                if (row != -1 && column != -1) {
                    g.setFont(new Font( "SansSerif", Font.BOLD, 30));
                    g.setColor(Color.black);
                    switch (winArr[2]) {
                        case "row" -> {
                            g.drawString("X", row, column);
                            g.drawString("X", row + 50, column);
                            g.drawString("X", row + 100, column);
                            g.drawString("X", row + 150, column);
                        }
                        case "column" -> {
                            g.drawString("X", row, column);
                            g.drawString("X", row, column + 50);
                            g.drawString("X", row, column + 100);
                            g.drawString("X", row, column + 150);
                        }
                        case "dia_l" -> {
                            g.drawString("X", row, column);
                            g.drawString("X", row + 50, column + 50);
                            g.drawString("X", row + 100, column + 100);
                            g.drawString("X", row + 150, column + 150);
                        }
                        case "dia_r" -> {
                            g.drawString("X", row, column + 150);
                            g.drawString("X", row + 50, column + 100);
                            g.drawString("X", row + 100, column + 50);
                            g.drawString("X", row + 150, column);
                        }
                    }
                }
                if (Board.getInstance().evaluate(playingBoard) == 999999) {
                    g.setColor(whiteplayer);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 30));
                    g.drawString("Red won.", 385, 75);
                } else if (Board.getInstance().evaluate(playingBoard) == -999999) {
                    g.setColor(blackplayer);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 30));
                    g.drawString("Orange won.", 385, 75);
                } else if (!Board.getInstance().areFieldsLeft(playingBoard)) {
                    g.setColor(Color.black);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 30));
                    g.drawString("Draw.", 385, 75);
                }
                // TURN BOX & FREE FIELDS
                int dif = -10;
                int ff = 0;
                for (int f: playingBoard) {
                    if (f == 0) {
                        ff++;
                    }
                }
                if (Board.getInstance().getTurn()) {
                    g.setFont(new Font( "SansSerif", Font.BOLD, 20));
                    g.setColor(whiteplayer);
                    g.drawString("Red", 400 + dif, 30);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 15));
                    g.drawString(String.valueOf(ff), 460 + dif, 25);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 10));
                    g.setColor(blackplayer);
                    g.drawString("Orange", 400 + dif, 40);
                } else {
                    g.setFont(new Font( "SansSerif", Font.BOLD, 10));
                    g.setColor(whiteplayer);
                    g.drawString("Red", 400 + dif, 23);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 18));
                    g.setColor(blackplayer);
                    g.drawString("Orange", 400 + dif, 40);
                    g.setFont(new Font( "SansSerif", Font.BOLD, 15));
                    g.drawString(String.valueOf(ff), 460 + dif, 25);
                }
                g.setColor(Color.black);
                g.drawLine(395 + dif, 10, 395 + dif, 45);
                g.drawLine(395 + dif, 45, 480 + dif, 45);
                g.drawLine(480 + dif, 45, 480 + dif, 10);
                g.drawLine(395 + dif, 10, 480 + dif, 10);
                // POSITION ID & POSITION & EVALUATION
                g.setFont(new Font( "SansSerif", Font.PLAIN, 10));
                String pid = "Position-ID: " + new HashRep().decode(playingBoard);
                g.drawString(pid, 10, 325);
                String p = "Position: " + Arrays.toString(playingBoard);
                g.drawString(p, 10, 335);
                String eval = "Evaluation: " + Board.getInstance().evaluate(playingBoard);
                g.drawString(eval, 10, 345);
            }
        };
        // PLAY AGAIN BUTTON
        JButton play_again = new JButton("Start New Game");
        play_again.setBounds(10, 370, 200, 50);
        play_again.addActionListener(e -> {
            System.out.println("\n\n#---# Started New Game:\n");
            Board.getInstance().loadPosition(new int[42]);
            Board.getInstance().setTurn(true);
            Board.skip_out_of_game_loop = true;
            frame.remove(play_again);
            frame.remove(pn);
            frame.repaint();
            gameWindow();
        });
        frame.add(play_again);
        frame.add(pn);
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = columnFinder(e);
                if (col != -1 && !Board.wasInput && Board.getInstance().isColNotFull(col)) {
                    System.out.println("Sending through " + col);
                    Board.getInstance().playerInput = col;
                    Board.getInstance().makeMove(col, Board.getInstance().getTurn());
                    Board.wasInput = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static int columnFinder(MouseEvent e) {
        for (int i = 0; i < 7; i++) {
            int xb1 = i * 50 + 15;
            int xb2 = i * 50 + 65;
            int yb1 = 20;
            int yb2 = 340;
            if (e.getX() > xb1 && e.getX() < xb2 && e.getY() > yb1 && e.getY() < yb2) {
                return i + 1;
            }
        }
        return -1;
    }

    static String[] winStonesFinder(int[] position) {
        String[] outArr = new String[]{"-1", "-1", "null"};
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 7; row++) {
                int index = column * 6 + column + row;
                // Row Winner, Forces
                if (row < 4) switch (position[index] + position[index + 1] + position[index + 2] + position[index + 3]) {
                    case 4, -4 -> {
                        return new String[]{String.valueOf(row), String.valueOf(column), "row"};
                    }
                }
                // Column Winner, Forces
                if (column < 3) switch (position[index] + position[index + 7] + position[index + 14] + position[index + 21]) {
                    case 4, -4 -> {
                        return new String[]{String.valueOf(row), String.valueOf(column), "column"};
                    }
                }
                // Diagonal Winner, Forces
                //   - Left Top Right Bottom
                if (row < 4 && column < 3) switch (position[index] + position[index + 8] + position[index + 16] + position[index + 24]) {
                    case 4, -4 -> {
                        return new String[]{String.valueOf(row), String.valueOf(column), "dia_l"};
                    }
                }
                //   - Left Bottom Right Top
                if (row < 4 && column < 3) switch (position[index + 21] + position[index + 15] + position[index + 9] + position[index + 3]) {
                    case 4, -4 -> {
                        return new String[]{String.valueOf(row), String.valueOf(column), "dia_r"};
                    }
                }
            }
        }
        return outArr;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}