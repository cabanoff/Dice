/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author WIN7x64
 */
public class DataPanel extends JPanel{
    private enum gameType {SCORE, GUESS} ;
    private gameType gameTypeFlag;
    private int points, overAllSum, numOfRolls, numOfDiceToRoll;
    private int minPoints, maxPoints, guessPoints, guessDiff;
    private final JPanel guessPanel1, guessPanel2, scorePanel;
    private JSlider slider;
    private final JLabel labelNumOfRolls, labelSumm, labelOverAllSumm, labelGuess;
    private final JLabel labelNumOfRollsForGuess, labelSummForGuess, labelPrecision;
   
    
    public DataPanel(){
        super(new BorderLayout());
        gameTypeFlag = gameType.SCORE;
        points = 0;
        overAllSum = 0;
        numOfRolls = 0;
        numOfDiceToRoll = 1;
        
        minPoints = numOfDiceToRoll;
        maxPoints = numOfDiceToRoll*6;
        guessPoints = (maxPoints + minPoints)/2; // initial guessPoints is in the middle 
        
        //dataPanel = new JPanel();
        guessPanel1 = new JPanel(new SpringLayout());
        guessPanel2 = new JPanel(new BorderLayout());
        //guessPanel2 = new JPanel(new SpringLayout());
        scorePanel = new JPanel(new SpringLayout());
        
        scorePanel.add(new JLabel("Броски"));
        
        labelNumOfRolls = new JLabel(Integer.toString(numOfRolls));
        labelNumOfRolls.setFont(new Font("Courier New", Font.BOLD,50));
        labelNumOfRolls.setForeground(Color.blue);
        labelNumOfRolls.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(labelNumOfRolls);
        
        scorePanel.add(new JLabel("Очки"));
        
        labelSumm = new JLabel(Integer.toString(points));
        labelSumm.setFont(new Font("Courier New", Font.BOLD,50));
        labelSumm.setForeground(Color.green);
        labelSumm.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(labelSumm);
        
        scorePanel.add(new JLabel("Сумма"));
        
        labelOverAllSumm = new JLabel(Integer.toString(overAllSum));
        labelOverAllSumm.setFont(new Font("Courier New", Font.BOLD,50));
        labelOverAllSumm.setForeground(Color.red);
        labelOverAllSumm.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(labelOverAllSumm);
        
        scorePanel.setBackground(Color.white);
         //Lay out the panel.
         SpringUtilities.makeGrid(scorePanel,
                                 3, 2, //rows, cols
                                 150, 20, //initialX, initialY
                                 10, 5);//xPad, yPad
         
         
        guessPanel1.add(new JLabel("Броски"));
        
        labelNumOfRollsForGuess = new JLabel(Integer.toString(numOfRolls));
        labelNumOfRollsForGuess.setFont(new Font("Courier New", Font.BOLD,50));
        labelNumOfRollsForGuess.setForeground(Color.blue);
        labelNumOfRollsForGuess.setHorizontalAlignment(SwingConstants.CENTER);
        guessPanel1.add(labelNumOfRollsForGuess);
        
        guessPanel1.add(new JLabel("Очки"));
        
        labelSummForGuess = new JLabel(Integer.toString(points));
        labelSummForGuess.setFont(new Font("Courier New", Font.BOLD,50));
        labelSummForGuess.setForeground(Color.green);
        labelSummForGuess.setHorizontalAlignment(SwingConstants.CENTER);
        guessPanel1.add(labelSummForGuess);
        
        guessPanel1.add(new JLabel("Ошибка"));
        
        labelPrecision = new JLabel(Integer.toString(guessDiff));
        labelPrecision.setFont(new Font("Courier New", Font.BOLD,50));
        labelPrecision.setForeground(Color.red);
        labelPrecision.setHorizontalAlignment(SwingConstants.CENTER);
        guessPanel1.add(labelPrecision);
        
        guessPanel1.setBackground(Color.white);
         
        SpringUtilities.makeGrid(guessPanel1,
                                 3, 2, //rows, cols
                                 120, 20, //initialX, initialY
                                 10, 5);//xPad, yPad
        
        
        labelGuess = new JLabel(Integer.toString(guessPoints));
        labelGuess.setFont(new Font("Courier New", Font.BOLD,60));
        labelGuess.setForeground(Color.green);
        labelGuess.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        slider = new JSlider(SwingConstants.HORIZONTAL,minPoints,maxPoints,guessPoints);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        
        slider.addChangeListener(
            new ChangeListener(){
                @Override
                public void stateChanged(ChangeEvent e){
                    guessPoints = slider.getValue();
                    labelGuess.setText(Integer.toString(guessPoints));
                }
            }
        );
        guessPanel2.add(BorderLayout.CENTER,labelGuess);
        guessPanel2.add(BorderLayout.SOUTH,slider);
        
        
        
        guessPanel2.setBackground(Color.white);
        
        setPanel();
        
    }
    
    private void setMargins(int numOfDice){
        minPoints = numOfDice;
        maxPoints = numOfDice*6;
        //if(guessPoints == 0)guessPoints = (maxPoints + minPoints)/2; // initial guessPoints is in the middle 
        //guessDiff = 0;
        slider.setMaximum(maxPoints);
        slider.setMinimum(minPoints);
        
    }
    public void setNumOfDice(int numOfDice){
        numOfDiceToRoll = numOfDice;
        setMargins(numOfDice);
    }
    public JPanel getDataPanel(){
        return this;
    }
    public void setGuess(){
        gameTypeFlag = gameType.GUESS;
        setPanel();
    }
    public void setScore(){
        gameTypeFlag = gameType.SCORE;
        setPanel();
    }
    private void setPanel(){
        this.removeAll();
        //this.validate();
        //this.repaint();
        if(gameTypeFlag == gameType.SCORE){
            this.add(BorderLayout.CENTER,scorePanel);
        }
        else {
            this.add(BorderLayout.WEST,guessPanel1);
            this.add(BorderLayout.CENTER,guessPanel2);
            //this.add(BorderLayout.EAST,guessPanel2);
        }
        this.setBackground(Color.yellow);
        this.validate();
        this.repaint();
    }
    /**
     * update all fields
     * int points, overAllSum, numOfRolls, numOfDice;
       int minPoints, maxPoints, guessPoints, guessDiff;
       JPanel guessPanel1, guessPanel2, scorePanel;
       JLabel labelNumOfRolls, labelSumm, labelOverAllSumm, labelGuess, labelPrecision;
     * JLabel labelNumOfRollsForGuess, labelSummForGuess, labelPrecision;
    */
    public void recalculate(int points){
        this.points = points;
        overAllSum += points;
        numOfRolls++;
        guessDiff = Math.abs(this.points - guessPoints);
        
        labelNumOfRolls.setText(Integer.toString(numOfRolls));
        labelNumOfRollsForGuess.setText(Integer.toString(numOfRolls));
        labelSumm.setText(Integer.toString(this.points));
        labelSummForGuess.setText(Integer.toString(this.points));
        labelOverAllSumm.setText(Integer.toString(overAllSum));
        labelPrecision.setText(Integer.toString(guessDiff));
        
        setPanel();
        
        //guessPanel1.repaint();
        //guessPanel2.repaint();
        //scorePanel.repaint();
    }
    
}

