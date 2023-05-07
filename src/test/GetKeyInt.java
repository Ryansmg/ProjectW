package test;

import main.Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GetKeyInt implements KeyListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(150,100);
        frame.setTitle("ProjectW | GetKeyInt");
        frame.addKeyListener(new GetKeyInt());
        if(Main.user != Main.DEV_USER){
            return;
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
