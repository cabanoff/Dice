/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dice;

import javax.swing.JLabel;
import java.util.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author WIN7x64
 */
public class TimerLabel extends JLabel{
    private Timer timer = new Timer();
    private TimerTask timerTask;
    
    public TimerLabel (){
        TimerLabel.this.setText(String.format("%02d:%02d", 0,0));
        if(timerTask != null)timerTask.cancel();     
    }
    
    public void restartTimer(){
        stopTimer();
        timerTask = new TimerTask(){
          private volatile int time = -1;
          @Override
          public void run(){
              time++;
              SwingUtilities.invokeLater(new Runnable(){
                  @Override
                  public void run() {
                     int t = time;
                     TimerLabel.this.setText(String.format("%02d:%02d", t/60,t%60));
                  }  
              });
          }
        };
        timer.schedule(timerTask, 0, 1000);
    }    
        
    
    public void stopTimer(){
        if(timerTask != null)timerTask.cancel();
    }
        
}
