/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dice;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author WIN7x64
 */
public class StructDiceThread {
    private final DiceThread diceThread;
    private final Thread thread;
    
    public StructDiceThread(JLabel label, int pause){
        diceThread = new DiceThread(label, pause);
        thread = new Thread(diceThread);       
    }
    
    public void start(){
        thread.start();
        //thread.isAlive();
    }
    
    public boolean isAlive(){
        return thread.isAlive();
    }
    public void stop(){
        diceThread.stop();
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }       
    }
    
}

/**
 * this class responsible for rolling a single die
 * @author WIN7x64
 */

class DiceThread implements Runnable {
    private final JLabel label;
    private final int pause;
    private volatile boolean trigger;
    private final ArrayList<String> animationPic;
    private static int taskCount = 0;
    private final int id = taskCount++;
    
    public DiceThread ( JLabel label, int pause){
        this.label = label;
        this.pause = pause;
        animationPic = new ArrayList<>();
        animationPic.add("/org/me/myimageapp/pic4animation/frame1.jpg");
        animationPic.add("/org/me/myimageapp/pic4animation/frame2.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame3.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame4.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame5.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame6.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame7.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame8.png");
        animationPic.add("/org/me/myimageapp/pic4animation/frame9.png");
        
    }
    
    public void stop(){
        trigger = false;
    }

    @Override
    public void run() {
        trigger = true;
        int counter = new Random().nextInt(animationPic.size());
        while(trigger == true){
            label.setIcon(new ImageIcon(getClass().getResource(animationPic.get(counter))));
            if(++counter == animationPic.size())counter = 0;
            try{
                Thread.sleep(pause);
            }catch (InterruptedException e){}
        }
        System.out.println("thread " + id + " stopped");
    }
    
    
}


