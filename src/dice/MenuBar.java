/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author WIN7x64
 */
public class MenuBar {
    private enum gameType {SCORE, GUESS} ;
    private gameType gameTypeFlag;
    private ArrayList<MenuBarListener> listeners = new ArrayList<>();
    
    public MenuBar(){
        gameTypeFlag = gameType.SCORE;
    }
    public JMenuBar getMenuBar(){
        JMenuBar menuBar;
        JMenu menuGameType;
        JRadioButtonMenuItem scoreGame,guessGame;
        
        menuBar = new JMenuBar();
        menuGameType = new JMenu();
        menuBar.add(menuGameType);
        //f.setJMenuBar(menuBar);
        ButtonGroup group = new ButtonGroup();
        scoreGame = new JRadioButtonMenuItem("Максимальный счёт");
        guessGame = new JRadioButtonMenuItem("Интуиция");
        group.add(scoreGame);
        group.add(guessGame);
        scoreGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameTypeFlag = gameType.SCORE;
                //setMenu("Максимальный счёт");
                menuGameType.setText("Максимальный счёт");
                fireListeners();
                System.out.println("Menu changed to Максимальный счёт");
            }
        });
        guessGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameTypeFlag = gameType.GUESS;
                //setMenu("Максимальный счёт");
                menuGameType.setText("Интуиция");
                fireListeners();
                System.out.println("Menu changed to Интуиция");
            }
        });
        menuGameType.add(scoreGame);
        menuGameType.add(guessGame);
        
        if(gameTypeFlag == gameType.SCORE){
            scoreGame.setSelected(true);
            menuGameType.setText("Максимальный счёт");
        }
        else{
            guessGame.setSelected(true);
            menuGameType.setText("Интуиция");        
        }
        return menuBar;
    }
    
    boolean isScoreGame(){
        return gameTypeFlag == gameType.SCORE;
    }
    boolean isGuessGame(){
        return gameTypeFlag == gameType.GUESS;
    }
    
    public void addListener(MenuBarListener listener){
        listeners.add(listener);
    }
    public void removeListener(MenuBarListener listener){
        listeners.remove(listener);
    }
    public void fireListeners(){
        for(MenuBarListener listener :listeners){
            listener.onGameTypeChange();
        }
    }
}

interface MenuBarListener{
    void onGameTypeChange();
}