/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;
//import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author WIN7x64
 */
public class Dice {
    JLabel label;
    JComboBox numOfDice;
    ArrayList<String> allJpg, animationPic;
    JFrame f;
    JPanel upPanel, lowPanel;
    DataPanel midPanel;
    JLabel labelNumOfRolls,labelSumm,labelOverAllSumm;
    TimerLabel timerLabel;
    Animation diceRolling;
    Thread thread;
    MenuBar menuBar;
       
    private int overAllSumm = 0;
    private int numOfRolls = 0;
    
    

    
    

    /**
     * @param args the command line arguments
    */
    public static void main(String[] args) {
        // TODO code application logic here
        Dice dice = new Dice();
        dice.go();
        /*
         java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Dice().go();
            }
        });
        */
    }
    
    public void go(){
        
        f = new JFrame(JOptionPane.showInputDialog("Имя игрока"));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(true);
        
        menuBar = new MenuBar();
        f.setJMenuBar(menuBar.getMenuBar());
        upPanel = getGuiPanel();
        
        midPanel = new DataPanel();
        /*
        midPanel = new JPanel(new SpringLayout());
        midPanel.add(new JLabel("Броски"));
        labelNumOfRolls = new JLabel("0");
        labelNumOfRolls.setFont(new Font("Courier New", Font.BOLD,50));
        labelNumOfRolls.setForeground(Color.blue);
        labelNumOfRolls.setHorizontalAlignment(SwingConstants.CENTER);
        midPanel.add(labelNumOfRolls);
        midPanel.add(new JLabel("Очки"));
        labelSumm = new JLabel("0");
        labelSumm.setFont(new Font("Courier New", Font.BOLD,50));
        labelSumm.setForeground(Color.green);
        labelSumm.setHorizontalAlignment(SwingConstants.CENTER);
        midPanel.add(labelSumm);
        midPanel.add(new JLabel("Сумма"));
        labelOverAllSumm = new JLabel("0");
        labelOverAllSumm.setFont(new Font("Courier New", Font.BOLD,50));
        labelOverAllSumm.setForeground(Color.red);
        labelOverAllSumm.setHorizontalAlignment(SwingConstants.CENTER);
        midPanel.add(labelOverAllSumm);
        midPanel.setBackground(Color.white);
         //Lay out the panel.
        SpringUtilities.makeGrid(midPanel,
                                 3, 2, //rows, cols
                                 150, 20, //initialX, initialY
                                 10, 5);//xPad, yPad

        */
        lowPanel = new JPanel();
        lowPanel.setBackground(Color.white);
        
        f.getContentPane().add(BorderLayout.NORTH,upPanel);
        f.getContentPane().add(BorderLayout.CENTER,midPanel);
        f.getContentPane().add(BorderLayout.SOUTH,lowPanel);
        f.setSize(450,360);
        f.setLocationRelativeTo(null);
        //f.getContentPane().setBackground(Color.white);
        //f.pack();
        f.setVisible(true);
        
        allJpg = new ArrayList<>();
        allJpg.add("/org/me/myimageapp/newpackage/ndice-1.jpg");
        //allJpg.add("/org/me/myimageapp/pic4animation/frame1.png");
        allJpg.add("/org/me/myimageapp/newpackage/ndice-2.jpg");
        allJpg.add("/org/me/myimageapp/newpackage/ndice-3.jpg");
        allJpg.add("/org/me/myimageapp/newpackage/ndice-4.jpg");
        allJpg.add("/org/me/myimageapp/newpackage/ndice-5.jpg");
        allJpg.add("/org/me/myimageapp/newpackage/ndice-6.jpg");
        
        
        
    }
   
    
    public JPanel getGuiPanel() {
        JPanel panel = new JPanel();
        JButton buttonStop = new JButton("Стоп");
        JButton buttonStart = new JButton("Старт");
        //timer = new JLabel();
        String[] choices = {"1","2","3","4","5","6","7","8","9","10"};
        timerLabel = new TimerLabel();
        timerLabel.setFont(new Font("Courier New", Font.BOLD,20));
        timerLabel.setForeground(new Color(51,153,0));
        
            
        numOfDice = new JComboBox(choices);
        
        buttonStart.addActionListener(new StartListener());
        buttonStop.addActionListener(new RollEmListener());
   
        panel.add(numOfDice);
        
        panel.add(buttonStart);
        panel.add(buttonStop);
        panel.add(timerLabel);
        panel.setBackground(Color.white);
        //panel.add(timer);
       
        return panel;
    }
    public class StartListener implements ActionListener {
       
        @Override
        public void actionPerformed(ActionEvent ae) {
            if((thread == null)||(!thread.isAlive())){
                diceRolling = new Animation();
                thread = new Thread(diceRolling);
                thread.start(); 
            }
        }
        
    }
    public class RollEmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            if((thread != null)&&(thread.isAlive())){
                
                diceRolling.stop();
                thread.interrupt();
                try {
                        thread.join();
                } catch (InterruptedException ex) {
                        System.out.println(ex);
                }
            }
        }
    } 
    /**
     * starts all action 
     */
    
    class Animation implements Runnable{
        private volatile boolean trigger;
        private ArrayList<StructDiceThread> diceRollingArr;
        
        
        public void stop(){
            trigger = false;
        }

        @Override
        public void run() {
            diceRollingArr = new ArrayList<>();  //stores thereads of one rolling die
            String selection = (String)numOfDice.getSelectedItem();
            int numOfDiceToRoll = Integer.parseInt(selection);
            
            //set midPanel
            if(menuBar.isGuessGame())midPanel.setGuess();
            else midPanel.setScore();
            
            midPanel.setNumOfDice(numOfDiceToRoll);
            
            
            lowPanel.removeAll();
             
            for(int i = 0; i < numOfDiceToRoll; i++){  //fill array with thread rolling dice
                 JLabel label = new JLabel();
                 lowPanel.add(label); 
                 StructDiceThread structDiceThread = new StructDiceThread(label,90 + new Random().nextInt(50));                
                 diceRollingArr.add(structDiceThread);                 
                 structDiceThread.start();      //start thread structDiceThread.thread
            }
            
            lowPanel.updateUI();
            
            trigger = true;
            timerLabel.restartTimer();
            while(trigger == true){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){}
            }
            diceRollingArr.stream().filter((rollingDiceThread) -> (rollingDiceThread.isAlive())).forEach((rollingDiceThread) -> {
                rollingDiceThread.stop();
            }); //stop all sub threads rolling dice
            
            ArrayList<JLabel> dice = new ArrayList<>();
            int summ = 0;
                lowPanel.removeAll();
                for(int i = 0; i < numOfDiceToRoll; i++){
                    int r = (int)(Math.random()*allJpg.size());
                    dice.add(i,new JLabel());
                    summ += (r+1);
                    try{
                        dice.get(i).setIcon(new ImageIcon(getClass().getResource(allJpg.get(r))));
                        lowPanel.add(dice.get(i));    
                    }catch(Exception ex){System.out.println(ex);}
                }
                
            lowPanel.updateUI();
            
            midPanel.recalculate(summ);
            //overAllSumm += summ;
            //numOfRolls++;
            
            //f.getContentPane().remove(midPanel);
           
            
            //labelNumOfRolls.setText(Integer.toString(numOfRolls));
            //labelSumm.setText(Integer.toString(summ));
            //labelOverAllSumm.setText(Integer.toString(overAllSumm));

            
            //midPanel.repaint();
            
            //f.getContentPane().add(BorderLayout.CENTER,midPanel);
            midPanel.updateUI();
           
            timerLabel.stopTimer();   
            System.out.println("main thread stopped");
        }
    } 
       
}


class DataPanel extends JPanel{
    private enum gameType {SCORE, GUESS} ;
    private gameType gameTypeFlag;
    private int points, overAllSum, numOfRolls, numOfDice;
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
        numOfDice = 1;
        setMargins(numOfDice);
        
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
        
        
        slider = new JSlider(SwingConstants.HORIZONTAL,minPoints,maxPoints,1);
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
        guessPoints = (maxPoints - minPoints)/2;
        guessDiff = 0;
        
    }
    public void setNumOfDice(int numOfDice){
        this.numOfDice = numOfDice;
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
        
        guessPanel1.repaint();
        guessPanel2.repaint();
        scorePanel.repaint();
    }
    
}

