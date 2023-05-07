package Library;

import main.KeyStroke;
import main.Main;
import main.Methods;
import main.Object;

public class LibAction {
    static boolean started = false;
    static boolean plGenerated = false;
    static Object player;
    static boolean isJumping = false;
    static boolean isOnGround = true;
    public static void libAction(){
        if(!plGenerated){ player = Object.getByName("player"); }
        if(!started){ return; }
        if(KeyStroke.isPressed('A') || KeyStroke.isPressed(37)){ player.changePosition(-3,0); }
        if(KeyStroke.isPressed('D') || KeyStroke.isPressed(39)){ player.changePosition(3,0); }
        if(KeyStroke.isPressed(' ') && !isJumping && isOnGround){ vidAction.start("jump"); }
        if(Methods.restrictBound(Object.getByName("player"))[3]){ LibAction.isOnGround = true; }
    }
}

record vidAction(String actionName) implements Runnable {
    @Override
    public void run() {
        switch(actionName){
            case "jump" -> {
                LibAction.isJumping = true;
                LibAction.isOnGround = false;
                for(int i=0; i<20; i++){
                    LibAction.player.changePosition(0, -(int) (120.0 * Math.sin(Math.toRadians(90 - (i*4.5))) / 15.0));
                    waitFrame();
                }
                for(int i=0; i<20; i++){
                    LibAction.player.changePosition(0, (int) (120.0 * Math.sin(Math.toRadians(i*4.5)) / 15.0));
                    waitFrame();
                }
                while(!LibAction.isOnGround){
                    LibAction.player.changePosition(0, (int) (14.0/3.0));
                    waitFrame();
                }
                LibAction.isJumping = false;
            }
            case "scale" -> {
                System.out.println("WIP");
                System.out.println("WIP");
            }
            default -> System.out.println("An undefined action is performed.");
        }
    }

    public static void start(String actionName) {
        new Thread(new vidAction(actionName)).start();
    }
    void waitFrame(){
        long firstFrame = Main.frames;
        while(firstFrame == Main.frames){
            System.out.print("");
        }
    }
    void waitFrame(int i){
        for(int j=0; j<i; j++) {
            long firstFrame = Main.frames;
            while (firstFrame == Main.frames) {
                System.out.print("");
            }
        }
    }
}