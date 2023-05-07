package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Methods {
    public static void wasdMove(Object object){
        if(KeyStroke.isPressed('W')){ object.changePosition(0,-3); }
        if(KeyStroke.isPressed('A')){ object.changePosition(-3,0); }
        if(KeyStroke.isPressed('S')){ object.changePosition(0,3); }
        if(KeyStroke.isPressed('D')){ object.changePosition(3,0); }
    }
    @SuppressWarnings("unused")
    public static void wasdMove(Object object, int speed){
        if(KeyStroke.isPressed('W')){ object.changePosition(0,-3 * speed); }
        if(KeyStroke.isPressed('A')){ object.changePosition(-3 * speed, 0); }
        if(KeyStroke.isPressed('S')){ object.changePosition(0,3 * speed); }
        if(KeyStroke.isPressed('D')){ object.changePosition(3 * speed, 0); }
    }
    /**
     * moves the given Object into the bounds.
     * returns if the action is performed. (left, ceiling, right, floor)
     */
    public static boolean[] restrictBound(Object o){
        boolean[] actionPerformed = {false, false, false, false};
        int x = o.getX();
        int y = o.getY();
        int width = o.getWidth();
        int height = o.getHeight();
        if(o.getX() < 0){ x = 0; actionPerformed[0] = true;}
        if(o.getY() < 0){ y = 0; actionPerformed[1] = true;}
        if(o.getX() + o.getWidth() > Main.window.getWidth() - 14){ x = Main.window.getWidth() - width - 14; actionPerformed[2] = true;}
        if(o.getY() + o.getHeight() > Main.window.getHeight() - 60){ y = Main.window.getHeight() - height - 60; actionPerformed[3] = true;}
        o.setPrePosition(x, y);
        return actionPerformed;
    }
    /**
     * @return returns true when the object is fully in the screen.
     * else: returns false.
     */
    @SuppressWarnings("unused")
    public static boolean boundCheck(Object o){
        if(o.getX()<0 || o.getY()<0){ return false; }
        if(o.getX() + o.getWidth() > Main.window.getWidth() - 14){ return false; }
        if(o.getY() + o.getHeight() > Main.window.getHeight() - 60){ return false; }
        System.out.print("");
        return true;
    }

    public static String readFile(File file){
        try {
            FileReader reader = new FileReader(file);
            int i = reader.read();
            StringBuilder builder = new StringBuilder();
            while(i != -1){
                builder.append((char) i);
                i = reader.read();
            }
            reader.close();
            return new String(builder);
        } catch (IOException e) {
            Main.exit(2, "Methods48");
        }
        Main.exit(2, "Methods50");
        return "";
    }
}
