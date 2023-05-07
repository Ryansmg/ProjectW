package main;

import Library.LibAction;
import Library.LibPreAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Action {
    public static int bltt_science = 1; //2SB, 2SR, 2SA (0,1,2)
    public static int bltt_math = 1; //2B2, 2B1A, 2B1B
    public static int bltt_kmo = 1; //K1A, K1B, K1C
    /**
     * Number of Objects (except player and nullObj)
     */
    public static int gameW_objectNum = 0;
    public static void preAction(){
        Main.window.play = false;
        JFrame loadingFrame = new JFrame("Loading ProjectW...");
        loadingFrame.setIconImage(new ImageIcon("./sources/logo.png").getImage());
        loadingFrame.setSize(300,0);
        loadingFrame.setResizable(false);
        loadingFrame.setVisible(true);
        loadingFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(Main.yesNoInput("Close ProjectW?", "ProjectW", JOptionPane.QUESTION_MESSAGE) == 0){
                    Main.exit(0, "action24 (manual exit during loading)");
                }
            }
        });
        loadingFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        int y = Object.getByName("player").getY();
        int x = Object.getByName("player").getX();
        Object.removeAllObj();
        switch(Main.mode){
            case "Library" -> {
                LibPreAction.libPreAction();
                Main.window.play = true;
                new Thread(Main.window).start();
            }
            case "gameW" -> {
                new Object("player", new Rectangle(x, y,50,50), new ImageIcon("./sources/logo.png"), null, null, Main.panel, true, false);
                //new Object("obs1", new Rectangle(150, 150, 100, 100), null, Color.BLACK, null, Main.panel, true); //obstacle
                //generates obstacles
                String[] lvl1raw = (Methods.readFile(new File("./sources/gameW/gameWLvl1"))).replace(" ", "").replace("\r", "").split("\n");
                ArrayList<String> lvl1 = new ArrayList<>();
                for(String line : lvl1raw){
                    if(!line.startsWith("//")){
                        lvl1.add(line);
                    }
                }
                for(String obstacles : lvl1){
                    String[] obsInfo = obstacles.split(",");
                    new Object("obs"+gameW_objectNum, new Rectangle(Integer.parseInt(obsInfo[0]),Integer.parseInt(obsInfo[1]),Integer.parseInt(obsInfo[2]),Integer.parseInt(obsInfo[3])), null, Color.BLACK, null, Main.panel, true, false);
                    gameW_objectNum++;
                }
                Main.window.play = true;
                new Thread(Main.window).start();
            }
            case "BrainLeagueTimeTable" -> {
                String websiteString = Objects.requireNonNull(ReadUrl.read("https://ryansmg6496.wixsite.com/ryan/bltt")).replace("\n", "").replace("\r", "").replace("<br />", "");
                String[] timeTableArray = websiteString.split(";");
                for(int i=0; i<timeTableArray.length; i++){
                    timeTableArray[i] = timeTableArray[i].replace("Che", "화현").replace("Bio", "생주").replace("Phy2", "물서").replace("Phy", "물허").replace("Num", "정수최").replace("CombK", "조합진").replace("Comb", "조합춘").replace("Alge", "대수호").replace("Geom2", "기하윤").replace("Geom", "기하주").replace("Geo", "지철");
                }
                int i = 0;
                for(String s: timeTableArray){
                    new Object(i+"", new Rectangle((i/6) *90 + 20, (i%6)*20, 70, 20), null, null, s, Main.panel, true, false);
                    i++;
                }
                Main.window.setVisible(true);
                Main.window.play = true;
                new Thread(Main.window).start();
            }
            case "flower" -> {
                for (int a = 0; a < 5; a++) {
                    for (int b = 0; b < 5; b++) {
                        new Object("panel" + a + b, new Rectangle(150 + (120 * a), 108 * b, 120, 108), null, Color.BLACK, null, Main.panel, false, false);
                    }
                }
                new Object("flower", new Rectangle(150, -60, 600, 600), new ImageIcon("./sources/flower.png"), Color.WHITE, null, Main.panel, false, false);
                Object.showAll();
            }
        }
        Main.window.setVisible(true);
        Main.window.play = true;
        loadingFrame.setVisible(false);
        loadingFrame.dispose();
    }
    public static void action(){
        try {
            switch (Main.mode) {
                case "Library" -> LibAction.libAction();
                case "gameW" -> {
                    Methods.wasdMove(Object.getByName("player"));
                    if(KeyStroke.isPressed(KeyStroke.CTRL_KEYCODE)){ Methods.wasdMove(Object.getByName("player")); }
                    Methods.restrictBound(Object.getByName("player"));
                    boolean collisionWithObs = false;
                    for(int i=0; i<gameW_objectNum; i++){
                        if(Object.getByName("player").collisionCheck(Object.getByName("obs"+i))){
                            collisionWithObs = true;
                        }
                    }
                    if(collisionWithObs){
                        KeyStroke.resetPressed();
                        Frame.pauseAction = true;
                        Main.showMessage("You died.", "ProjectW", JOptionPane.INFORMATION_MESSAGE);
                        Object.getByName("player").setPosition(0,0);
                        Frame.pauseAction = false;
                    }
                }
                case "BrainLeagueTimeTable" -> System.out.print("");
                case "flower" -> {
                    switch ((int) Main.frames) {
                        case 30 -> Objects.requireNonNull(Object.getByName("panel00")).remove();
                        case 60 -> Objects.requireNonNull(Object.getByName("panel23")).remove();
                        case 90 -> Objects.requireNonNull(Object.getByName("panel42")).remove();
                        case 120 -> Objects.requireNonNull(Object.getByName("panel11")).remove();
                        case 150 -> Objects.requireNonNull(Object.getByName("panel31")).remove();
                        case 180 -> Objects.requireNonNull(Object.getByName("panel12")).remove();
                        case 210 -> Objects.requireNonNull(Object.getByName("panel03")).remove();
                        case 240 -> Objects.requireNonNull(Object.getByName("panel34")).remove();
                        case 270 -> Objects.requireNonNull(Object.getByName("panel43")).remove();
                        case 300 -> Objects.requireNonNull(Object.getByName("panel20")).remove();
                        case 330 -> Objects.requireNonNull(Object.getByName("panel14")).remove();
                        case 360 -> Objects.requireNonNull(Object.getByName("panel22")).remove();
                        case 390 -> Objects.requireNonNull(Object.getByName("panel44")).remove();
                        case 420 -> Objects.requireNonNull(Object.getByName("panel04")).remove();
                        case 450 -> Objects.requireNonNull(Object.getByName("panel30")).remove();
                        case 480 -> Objects.requireNonNull(Object.getByName("panel41")).remove();
                        case 510 -> Objects.requireNonNull(Object.getByName("panel33")).remove();
                        case 540 -> Objects.requireNonNull(Object.getByName("panel13")).remove();
                        case 570 -> Objects.requireNonNull(Object.getByName("panel01")).remove();
                        case 600 -> Objects.requireNonNull(Object.getByName("panel32")).remove();
                        case 630 -> Objects.requireNonNull(Object.getByName("panel10")).remove();
                        case 660 -> Objects.requireNonNull(Object.getByName("panel02")).remove();
                        case 690 -> Objects.requireNonNull(Object.getByName("panel21")).remove();
                        case 720 -> Objects.requireNonNull(Object.getByName("panel24")).remove();
                        case 750 -> Objects.requireNonNull(Object.getByName("panel40")).remove();
                    }
                }
            }
        } catch (NullPointerException e) {
            Main.logM("Exception at frame139: ");
            Main.logN(e.toString());
        }
    }

    public static boolean flower_started = false;
    public static void keyAction(char keyChar){
        switch(Main.mode){
            case "BrainLeagueTimeTable" -> {

            }
            case "flower" -> {
                switch(keyChar){
                    case ' ' -> {
                        if(flower_started){return;}
                        flower_started = true;
                        new Thread(Main.window).start();
                    }
                    case '\\' -> System.out.print("");
                }
            }
        }
    }
}

class Do implements Runnable {
    @Override
    public void run() {
        Action.action();
    }
}