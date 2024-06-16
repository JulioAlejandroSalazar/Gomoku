import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Gomoku implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel westBorder = new JPanel();
    JPanel eastBorder = new JPanel();
    JPanel southBorder = new JPanel();
    JLabel textLabel = new JLabel();
    JButton[] buttons = new JButton[362];
    JButton resetbutton = new JButton();
    boolean player1Turn;

    Gomoku() {
        
        ImageIcon icon = new ImageIcon("Images/gomoku_icon.png");

        frame.setSize(1200,1200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(253,216,133));
        frame.setLayout(new BorderLayout());
        frame.setTitle("Gomoku");
        frame.setIconImage(icon.getImage());

        textLabel.setBackground(new Color(253,216,133));
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Segoe Script", Font.BOLD,55));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Gomoku");
        textLabel.setOpaque(true);

        resetbutton.setFocusable(false);
        resetbutton.addActionListener(this);
        resetbutton.setBounds(950, 20, 125, 50);
        resetbutton.setFont(new Font("Arial", Font.PLAIN,25));
        resetbutton.setText("Reset");

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0,0,1200,100);

        westBorder.setBackground(new Color(253,216,133));
        westBorder.setPreferredSize(new Dimension(100,100));

        eastBorder.setBackground(new Color(253,216,133));
        eastBorder.setPreferredSize(new Dimension(100,100));

        southBorder.setBackground(new Color(253,216,133));
        southBorder.setPreferredSize(new Dimension(100,100));

        buttonPanel.setLayout(new GridLayout(19,19));
        buttonPanel.setBackground(new Color(253,216,133));

        int x = 0;
        int y = 1;

        for(int i = 1; i <= 361; i++) {

            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setBackground(new Color(253,216,133));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);

            if(x < 19) {
                x++;
            } else {
                x = 1;
            }

            if(i % 19 == 0) {
                buttons[i].setName(String.valueOf(x) + "," + String.valueOf(y));
                y++;                              
            } else {
                buttons[i].setName(String.valueOf(x) + "," + String.valueOf(y));
            }
            
        }
       
        titlePanel.add(textLabel);
        frame.add(resetbutton);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(westBorder, BorderLayout.WEST);
        frame.add(eastBorder, BorderLayout.EAST);
        frame.add(southBorder, BorderLayout.SOUTH);

        frame.setVisible(true);

        firstTurn();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == resetbutton) {
            reset();
        }
        
        ImageIcon blackCircle = new ImageIcon("Images/black_circle.png"); // load the image to a imageIcon
        Image image = blackCircle.getImage(); // transform it 
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        blackCircle = new ImageIcon(newimg, "black");  // transform it back

        ImageIcon whiteCircle = new ImageIcon("Images/white_circle.png");
        Image image2 = whiteCircle.getImage();
        Image newimg1 = image2.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);  
        whiteCircle = new ImageIcon(newimg1, "white");

        for(int i = 1; i <= 361; i++) {
            if(e.getSource() == buttons[i]) {
                //System.out.println(buttons[i].getName());
                if(player1Turn == true) {
                    if(buttons[i].getIcon() == null) {
                        buttons[i].setIcon(whiteCircle);
                        player1Turn = false;
                        textLabel.setText("Black turn");
                        checkHorizontal(i);
                        checkVertical(i);
                        checkDiagonal1(i);
                        checkDiagonal2(i);
                    }
                } else {
                    if(buttons[i].getIcon() == null) {
                        buttons[i].setIcon(blackCircle);
                        player1Turn = true;
                        textLabel.setText("White turn");
                        checkHorizontal(i);
                        checkVertical(i);
                        checkDiagonal1(i);
                        checkDiagonal2(i);
                    }
                }
            }
        }
    }

    public void firstTurn() {

        /*try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        if (random.nextInt(2) == 0) {
            player1Turn = true;
            textLabel.setText("White turn");
        } else {
            player1Turn = false;
            textLabel.setText("Black turn");
        }

    }
   
        //HWW -> Horizontal White Wins
        //HBW -> Horizontal Black Wins
        //VWW -> Vertical White Wins
        //VBW -> Vertical Black Wins
        //DWW -> Diagonal White Wins             
        //DBW -> Diagonal Black Wins

    public void checkHorizontal(int n) {

        try {   //white
            
                int yHWW = ((getXRow(buttons[n])) * 19);       //get the last value of the x row
                for(int xHWW = (yHWW - 18); xHWW <= (yHWW - 4); xHWW++) {        //check the x row that was clicked and iterate between bounds                     
                    if(buttons[xHWW].getIcon() != null) {
                        boolean isValid = true;
                        for (int i = 0; i < 5; i++) {       //check for combinations of 5           
                            if (!buttons[xHWW + i].getIcon().toString().equals("white")) {
                                isValid = false;
                                break;
                            }
                        }
                        if(isValid) {
                            whiteWins(xHWW, xHWW + 1, xHWW + 2, xHWW + 3, xHWW + 4);
                        }   
                    }                   
                }

        } catch (Exception e) {
            
        }

        try {   //black
            
                int yHBW = ((getXRow(buttons[n])) * 19);       //get the last value of the x row
                for(int xHBW = (yHBW - 18); xHBW <= (yHBW - 4); xHBW++) {        //check the x row that was clicked and iterate between bounds                     
                    if(buttons[xHBW].getIcon() != null) {
                        boolean isValid = true;
                        for (int i = 0; i < 5; i++) {       //check for combinations of 5           
                            if (!buttons[xHBW + i].getIcon().toString().equals("black")) {
                                isValid = false;
                                break;
                            }
                        }
                        if(isValid) {
                            blackWins(xHBW, xHBW + 1, xHBW + 2, xHBW + 3, xHBW + 4);
                        }   
                    }                   
                }

        } catch (Exception e) {
            
        }
    }

    public void checkVertical(int n) {

        try {   //white
            
                for(int yVWW = (getYColumn(buttons[n])); yVWW <= 285; yVWW += 19) {        //check the y column that was clicked and iterate between bounds                     
                    if(buttons[yVWW].getIcon() != null) {
                        boolean isValid = true;
                        for (int i = 0; i <= 76; i += 19) {       //check for combinations of 5           
                            if (!buttons[yVWW + i].getIcon().toString().equals("white")) {
                                isValid = false;
                                break;
                            }
                        }
                        if(isValid) {
                            whiteWins(yVWW, yVWW + 19, yVWW + 38, yVWW + 57, yVWW + 76);
                        }   
                    }                   
                }

        } catch (Exception e) {
            
        }

        try {   //black
            
                for(int yVBW = (getYColumn(buttons[n])); yVBW <= 285; yVBW += 19) {        //check the y column that was clicked and iterate between bounds                     
                    if(buttons[yVBW].getIcon() != null) {
                        boolean isValid = true;
                        for (int i = 0; i <= 76; i += 19) {       //check for combinations of 5           
                            if (!buttons[yVBW + i].getIcon().toString().equals("black")) {
                                isValid = false;
                                break;
                            }
                        }
                        if(isValid) {
                            blackWins(yVBW, yVBW + 19, yVBW + 38, yVBW + 57, yVBW + 76);
                        }   
                    }                   
                }

        } catch (Exception e) {
            
        }

    }

    public void checkDiagonal1(int n) {

        try {   //white
            
            int yDWW = getXRow(buttons[n]);
            int xDWW = getYColumn(buttons[n]);
             
            while(xDWW != 1 && yDWW != 1) {     //get to the top-left button of the diagonal clicked
                xDWW--;
                yDWW--;
            }

            while(xDWW != 19 && yDWW != 19) {       //check the diagonal until it hits the bottom-right button of the diagonal
                if(buttons[coordinatesToIndex(xDWW, yDWW)].getIcon() != null) {
                    boolean isValid = true;
                    for (int i = 0; i < 5; i++) {       //check for combinations of 5           
                        if (!buttons[coordinatesToIndex(xDWW, yDWW)].getIcon().toString().equals("white")) {
                            isValid = false;
                            break;                        
                        }
                        xDWW++;
                        yDWW++;
                    }
                    if(isValid) {
                        whiteWins(
                        coordinatesToIndex(xDWW - 1, yDWW - 1),
                        coordinatesToIndex(xDWW - 2, yDWW - 2),
                        coordinatesToIndex(xDWW - 3, yDWW - 3),
                        coordinatesToIndex(xDWW - 4, yDWW - 4),
                        coordinatesToIndex(xDWW - 5, yDWW - 5));
                    }   
                }       
                xDWW++;
                yDWW++;
            }
        } catch (Exception e) {
            
        }

        try {   //black
            
            int yDBW = getXRow(buttons[n]);
            int xDBW = getYColumn(buttons[n]);
             
            while(xDBW != 1 && yDBW != 1) {
                xDBW--;
                yDBW--;
            }

            while(xDBW != 19 && yDBW != 19) {
                if(buttons[coordinatesToIndex(xDBW, yDBW)].getIcon() != null) {
                    boolean isValid = true;
                    for (int i = 0; i < 5; i++) {       //check for combinations of 5           
                        if (!buttons[coordinatesToIndex(xDBW, yDBW)].getIcon().toString().equals("black")) {
                            isValid = false;
                            break;                        
                        }
                        xDBW++;
                        yDBW++;
                    }
                    if(isValid) {
                        blackWins(
                        coordinatesToIndex(xDBW - 1, yDBW - 1),
                        coordinatesToIndex(xDBW - 2, yDBW - 2),
                        coordinatesToIndex(xDBW - 3, yDBW - 3),
                        coordinatesToIndex(xDBW - 4, yDBW - 4),
                        coordinatesToIndex(xDBW - 5, yDBW - 5));
                    }   
                }       
                xDBW++;
                yDBW++;
            }
        } catch (Exception e) {
            
        }
    }

    public void checkDiagonal2(int n) {

        try {   //white
            
            int yDWW = getXRow(buttons[n]);
            int xDWW = getYColumn(buttons[n]);
             
            while(xDWW != 19 && yDWW != 1) {     //get to the top-right button of the diagonal clicked
                xDWW++;
                yDWW--;
            }

            while(xDWW != 1 && yDWW != 19) {       //check the diagonal until it hits the bottom-left button of the diagonal
                if(buttons[coordinatesToIndex(xDWW, yDWW)].getIcon() != null) {
                    boolean isValid = true;
                    for (int i = 0; i < 5; i++) {       //check for combinations of 5           
                        if (!buttons[coordinatesToIndex(xDWW, yDWW)].getIcon().toString().equals("white")) {
                            isValid = false;
                            break;                        
                        }
                        xDWW--;
                        yDWW++;
                    }
                    if(isValid) {
                        whiteWins(
                        coordinatesToIndex(xDWW + 1, yDWW - 1),
                        coordinatesToIndex(xDWW + 2, yDWW - 2),
                        coordinatesToIndex(xDWW + 3, yDWW - 3),
                        coordinatesToIndex(xDWW + 4, yDWW - 4),
                        coordinatesToIndex(xDWW + 5, yDWW - 5));
                    }   
                }       
                xDWW--;
                yDWW++;
            }
        } catch (Exception e) {
            
        }

        try {   //black
            
            int yDBW = getXRow(buttons[n]);
            int xDBW = getYColumn(buttons[n]);
             
            while(xDBW != 19 && yDBW != 1) {     //get to the top-right button of the diagonal clicked
                xDBW++;
                yDBW--;
            }

            while(xDBW != 1 && yDBW != 19) {       //check the diagonal until it hits the bottom-left button of the diagonal
                if(buttons[coordinatesToIndex(xDBW, yDBW)].getIcon() != null) {
                    boolean isValid = true;
                    for (int i = 0; i < 5; i++) {       //check for combinations of 5           
                        if (!buttons[coordinatesToIndex(xDBW, yDBW)].getIcon().toString().equals("black")) {
                            isValid = false;
                            break;                        
                        }
                        xDBW--;
                        yDBW++;
                    }
                    if(isValid) {
                        blackWins(
                        coordinatesToIndex(xDBW + 1, yDBW - 1),
                        coordinatesToIndex(xDBW + 2, yDBW - 2),
                        coordinatesToIndex(xDBW + 3, yDBW - 3),
                        coordinatesToIndex(xDBW + 4, yDBW - 4),
                        coordinatesToIndex(xDBW + 5, yDBW - 5));
                    }   
                }       
                xDBW--;
                yDBW++;
            }
        } catch (Exception e) {
            
        }

    }

    public int coordinatesToIndex(int x, int y) {
        return (y - 1) * 19 + x;
    }

    public int getXRow(JButton button) {
        String[] xyarray = button.getName().split(",",2);
        int x = Integer.valueOf(xyarray[1]);
        return x;
    }

    public int getYColumn(JButton button) {
        String[] xyarray = button.getName().split(",",2);
        int y = Integer.valueOf(xyarray[0]);
        return y;
    }

    public void reset() {

        for(int i = 1; i <= 361; i++) {
            buttons[i].setIcon(null);
            buttons[i].setBackground(new Color(253,216,133));
            buttons[i].setEnabled(true);
        }

        firstTurn();

    }

    public void blackWins(int a, int b, int c, int d, int e) {

        buttons[a].setBackground(new Color(219, 253, 155 ));
        buttons[b].setBackground(new Color(219, 253, 155 ));
        buttons[c].setBackground(new Color(219, 253, 155 ));
        buttons[d].setBackground(new Color(219, 253, 155 ));
        buttons[e].setBackground(new Color(219, 253, 155 ));

        for(int i = 1; i <= 361; i++) {
            buttons[i].setEnabled(false);
        }

        textLabel.setText("Black wins");

    }

    public void whiteWins(int a, int b, int c, int d, int e) {

        buttons[a].setBackground(new Color(219, 253, 155 ));
        buttons[b].setBackground(new Color(219, 253, 155 ));
        buttons[c].setBackground(new Color(219, 253, 155 ));
        buttons[d].setBackground(new Color(219, 253, 155 ));
        buttons[e].setBackground(new Color(219, 253, 155 ));

        for(int i = 1; i <= 361; i++) {
            buttons[i].setEnabled(false);
        }

        textLabel.setText("White wins");

    }

}