package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStroke implements KeyListener {

    public static char pressedKey = (char) -1;
    public static int[] pressedKeys = {-1,-1,-1,-1,-1};

    /**
     * @param c UPPERCASE only
     */
    public static boolean isPressed(char c){
        for(int i=0; i<5; i++){
            if(pressedKeys[i] == (int) c){
                return true;
            }
        }
        return false;
    }
    public static final int CTRL_KEYCODE = 17;
    public static boolean isPressed(int keyI){
        for(int i=0; i<5; i++){
            if(pressedKeys[i] == keyI){
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKey = e.getKeyChar();
        if(!isPressed(e.getKeyCode()) && pressedKeys[4] == -1) {
            pressedKeys[4] = pressedKeys[3];
            pressedKeys[3] = pressedKeys[2];
            pressedKeys[2] = pressedKeys[1];
            pressedKeys[1] = pressedKeys[0];
            pressedKeys[0] = e.getKeyCode();
        }
        //Main.logM("Pressed Key: ");
        //Main.logN("'"+e.getKeyChar() + "'");/
        Action.keyAction(pressedKey);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == pressedKey){
            pressedKey = (char) -1;
        }
        for(int i = 0; i < 5; i++){
            if(pressedKeys[i] == e.getKeyCode() || pressedKeys[i] == 0 || pressedKeys[i] == 263 || pressedKeys[i] == 121){
                System.arraycopy(pressedKeys, i + 1, pressedKeys, i, 4 - i);
                pressedKeys[4] = -1;
            }
        }
        //Main.log("Key Released");
    }

    public static void resetPressed(){
        KeyStroke.pressedKeys[0] = -1;
        KeyStroke.pressedKeys[1] = -1;
        KeyStroke.pressedKeys[2] = -1;
        KeyStroke.pressedKeys[3] = -1;
        KeyStroke.pressedKeys[4] = -1;
        KeyStroke.pressedKey = (char) -1;
    }
}
