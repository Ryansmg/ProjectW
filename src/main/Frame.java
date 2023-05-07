package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Frame extends JFrame implements Runnable{
    @SuppressWarnings("unused")
    public static final int LOW_RESOLUTION = 0; //600, 400
    public static final int DEFAULT_RESOLUTION = 1; //900, 600
    @SuppressWarnings("unused")
    public static final int HIGH_RESOLUTION = 2; //1200, 800
    public static final int FULLSCREEN_RESOLUTION = -1;
    private static int selectedResolution;
    public boolean play = true;
    public static boolean fullScreenSupported;
    public static boolean changeResolutionSupported;
    public static boolean fpsChangeSupported;
    private static final KeyStroke keyStroke = new KeyStroke();
    static {
        switch(Main.mode){
            case "BrainLeagueTimeTable" -> {
                //fullScreenSupported = false;
                fpsChangeSupported = false;
                changeResolutionSupported = false;
                maxFps = 10;
            }
            case "flower" -> {
                //fullScreenSupported = false;
                fpsChangeSupported = false;
                changeResolutionSupported = false;
                maxFps = 900;
            }
            case "gameW" -> {
                //fullScreenSupported = false;
                fpsChangeSupported = false;
                changeResolutionSupported = true;
                maxFps = 60;
            }
            default -> {
                //fullScreenSupported = true;
                fpsChangeSupported = true;
                changeResolutionSupported = true;
                maxFps = 300;
            }
        }
        fullScreenSupported = false;
        //fullScreenMode has few annoying bugs (ex: JOptionPane spawns behind JFrame) that aren't fixed yet
        //therefore, fullScreenMode is not Supported yet.
    }
    public static int fps = 30;
    static {
        switch(Main.mode){
            case "flower" -> fps = 900;
            case "BrainLeagueTimeTable" -> fps = 10;
            case "gameW", "Library" -> fps = 60;
        }
    }
    public static int maxFps;
    public static JFrame VPK; //viewPressedKeys
    public static JFrame VF; //viewFrames
    Frame(int resolution, Color bg){
        boolean fullScreen = (resolution == -1);
        if(!fullScreen){
            selectedResolution = resolution;
        }
        Main.log("Generating frame");
        //Main.logM("fullScreen: ");
        //Main.logN(fullScreen + "");/
        this.setName("Frame.this");
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("ProjectW");
        if(Main.user == Main.DEV_USER){
            this.setTitle("ProjectW | " + Main.mode);
        }
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation(0,0);
        this.setIconImage(new ImageIcon("./sources/logo.png").getImage());
        this.setContentPane(Main.panel);
        Main.panel.setBackground(bg);
        switch(resolution){
            case 0 -> this.setSize(600, 400);
            case 1 -> this.setSize(900, 600);
            case 2 -> this.setSize(1200, 800);
            case -1 -> {
                switch (selectedResolution) {
                    case 0 -> this.setSize(600, 400);
                    case 1 -> this.setSize(900, 600);
                    case 2 -> this.setSize(1200, 800);
                }
            }
        }
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(Main.yesNoInput("Close ProjectW?", "ProjectW", JOptionPane.QUESTION_MESSAGE) == 0){
                    Main.exit(0, "frame42 (manual exit)");
                }
            }
        });
        //enable fullScreenMode
        if(fullScreen) {
            this.setUndecorated(true);
            GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
            if(devices.length == 1){
                devices[0].setFullScreenWindow(this);
            } else if(devices.length == 2){
                String[] options = {"device1", "device2"};
                try {
                    int deviceN = JOptionPane.showOptionDialog(null, "Select fullscreen device", "ProjectW", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    devices[deviceN].setFullScreenWindow(this);
                    Main.logM("Full screen device: ");
                    Main.logN(deviceN + "");
                } catch(ArrayIndexOutOfBoundsException e){
                    this.setVisible(false);
                    this.play = false;
                    new Frame(selectedResolution, bg);
                    this.dispose();
                    return;
                }
            } else {
                Main.showMessage("Too many graphics devices!", "ProjectW", JOptionPane.ERROR_MESSAGE);
                Main.exit(2, "frame68");
            }
        }
        //generating menuBar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");
        JMenuItem fullScreenMenuItem;
        if(fullScreen){
            fullScreenMenuItem = new JMenuItem("Disable Full Screen Mode");
        } else {
            fullScreenMenuItem = new JMenuItem("Enable Full Screen Mode");
        }
        fullScreenMenuItem.addActionListener(e -> {
            this.setVisible(false);
            this.play = false;
            if(fullScreen) {
                new Frame(selectedResolution, bg);
            } else {
                new Frame(FULLSCREEN_RESOLUTION, bg);
            }
            this.dispose();
        });
        if(fullScreenSupported || Main.user == Main.DEV_USER) {
            viewMenu.add(fullScreenMenuItem);
        }
        JMenuItem resolutionMenuItem = new JMenuItem("Resolution");
        resolutionMenuItem.addActionListener(e -> {
            String[] options = {"Low", "Default", "High", "Cancel"};
            Integer resolutionChoice = JOptionPane.showOptionDialog(null, "Select a resolution", "ProjectW", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if(resolutionChoice == 3){ return; }
            if(resolutionChoice == -1){ return; }
            System.out.println(resolutionChoice);
            this.setVisible(false);
            this.play = false;
            new Frame(resolutionChoice, bg);
            this.dispose();
        });
        if(changeResolutionSupported || Main.user == Main.DEV_USER) { viewMenu.add(resolutionMenuItem); }
        jMenuBar.add(viewMenu);
        if(Main.user == Main.DEV_USER){ //TODO make object.isSystem true
            JMenu devMenu = new JMenu("dev");
            JMenuItem viewObjInfoItem = new JMenuItem("viewObjInfo");
            viewObjInfoItem.addActionListener(e -> {
                String objName = Main.input("ObjectName", "ProjectW", JOptionPane.PLAIN_MESSAGE);
                Object viewObj = Object.getByName(objName);
                Main.showMessage("name : " + viewObj.getName() + "\nx : " + viewObj.getX() + "\ny : " + viewObj.getY() + "\nwidth : " + viewObj.getWidth() + "\nheight : " + viewObj.getHeight() + "\nvisible : " + viewObj.isVisible() + "\ncontainer : " + viewObj.getParent().getName() + "\ntext : " + viewObj.getText() , "ProjectW", JOptionPane.PLAIN_MESSAGE);
            });
            JMenuItem viewBtnObjInfoItem = new JMenuItem("viewBtnObjInfo");
            viewBtnObjInfoItem.addActionListener(e -> {
                String objName = Main.input("ObjectName", "ProjectW", JOptionPane.PLAIN_MESSAGE);
                BtnObj viewObj = BtnObj.getByName(objName);
                Main.showMessage("name : " + viewObj.getName() + "\nx : " + viewObj.getX() + "\ny : " + viewObj.getY() + "\nwidth : " + viewObj.getWidth() + "\nheight : " + viewObj.getHeight() + "\nvisible : " + viewObj.isVisible() + "\ncontainer : " + viewObj.getParent().getName() + "\ntext : " + viewObj.getText() , "ProjectW", JOptionPane.PLAIN_MESSAGE);
            });
            JMenuItem viewPressedKeysItem = new JMenuItem("viewPressedKeys");
            viewPressedKeysItem.addActionListener(e -> {
                VPK = new JFrame("ProjectW");
                VPK.setSize(250,155);
                VPK.setResizable(false);
                VPK.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                VPK.setLayout(null);
                Object.getByName("devVPKText").remove();
                Object.getByName("devVPKText2").remove();
                new TextObj("devVPKText", new Rectangle(12,0,200,55), KeyStroke.pressedKeys[0] + " " + KeyStroke.pressedKeys[1] + " " + KeyStroke.pressedKeys[2] + " " + KeyStroke.pressedKeys[3] + " " + KeyStroke.pressedKeys[4], VPK, true, new Font("맑은 고딕", Font.PLAIN, 15), true);
                new TextObj("devVPKText2", new Rectangle(12,55,200,55), ((char) KeyStroke.pressedKeys[0]) + " " + ((char) KeyStroke.pressedKeys[1]) + " " + ((char) KeyStroke.pressedKeys[2]) + " " + ((char) KeyStroke.pressedKeys[3]) + " " + ((char) KeyStroke.pressedKeys[4]), VPK, true, new Font("맑은 고딕", Font.PLAIN, 15), true);
                VPK.setVisible(true);
            });
            JMenuItem viewFramesItem = new JMenuItem("viewFrames");
            viewFramesItem.addActionListener(e -> {
                VF = new JFrame("FPS : " + fps);
                VF.setSize(250,100);
                VF.setResizable(false);
                VF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                VF.setLayout(null);
                Object.getByName("devVFText").remove();
                new TextObj("devVFText", new Rectangle(12,0,200,55), Main.frames + "", VF, true, new Font("맑은 고딕", Font.PLAIN, 15), true);
                VF.setVisible(true);
            });
            JMenuItem viewObjectsItem = new JMenuItem("viewObjects");
            viewObjectsItem.addActionListener(e -> {
                StringBuilder builder = new StringBuilder();
                for(Object o : Object.getClonedObjectList()){
                    builder.append(o.getName()).append("\n");
                }
                Main.showMessage(new String(builder), "ProjectW | ObjList", JOptionPane.PLAIN_MESSAGE);
            });
            devMenu.add(viewObjInfoItem);
            devMenu.add(viewBtnObjInfoItem);
            devMenu.add(viewPressedKeysItem);
            devMenu.add(viewFramesItem);
            devMenu.add(viewObjectsItem);
            jMenuBar.add(devMenu);
        }
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem fpsItem = new JMenuItem("FPS");
        fpsItem.addActionListener(e -> {
            try {
                String in = Main.input("Enter the fps number (Current fps : "+fps+")", "ProjectW", JOptionPane.QUESTION_MESSAGE);
                if(in == null){
                    return;
                }
                Main.logM("fps changed: from " + fps);
                fps = Integer.parseInt(in);
                Main.logN(" to " + fps);
            } catch(NumberFormatException exception){
                Main.showMessage("Not a number", "ProjectW", JOptionPane.WARNING_MESSAGE);
            }
        });
        if(fpsChangeSupported || Main.user == Main.DEV_USER) { settingsMenu.add(fpsItem); }
        JMenuItem resetPressedKeysItem = new JMenuItem("Reset pressed keys");
        resetPressedKeysItem.addActionListener(e -> KeyStroke.resetPressed());
        settingsMenu.add(resetPressedKeysItem);
        JMenuItem infoItem = new JMenuItem("Info");
        infoItem.addActionListener(e -> Main.showMessage("ProjectW\nVersion : " + Main.version + "\nMade by Ryanson\nMore info : bit.ly/ryanson", "ProjectW", JOptionPane.INFORMATION_MESSAGE));
        settingsMenu.add(infoItem);
        jMenuBar.add(settingsMenu);
        for(JMenu menu : MenuConfig.getMenus()){
            jMenuBar.add(menu);
        }
        JMenu exitMenu = new JMenu("Exit");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {
            if(Main.yesNoInput("Close ProjectW?", "ProjectW", JOptionPane.QUESTION_MESSAGE) == 0){
                Main.exit(0, "frame104 (manual exit)");
            }
        });
        exitMenu.add(exitMenuItem);
        jMenuBar.add(exitMenu);
        jMenuBar.setFocusable(false);
        this.setJMenuBar(jMenuBar);
        this.addKeyListener(keyStroke);
        //finally
        this.requestFocus();
        Main.window = this;
        Action.preAction();
        //this.setVisible(visible);
    }

    public void refresh(){
        Main.window.revalidate();
        Main.window.repaint();
        Main.panel.revalidate();
        Main.panel.repaint();
        if(Main.user == Main.DEV_USER){
            try {
                Object.getByName("devVPKText").setText(KeyStroke.pressedKeys[0] + " " + KeyStroke.pressedKeys[1] + " " + KeyStroke.pressedKeys[2] + " " + KeyStroke.pressedKeys[3] + " " + KeyStroke.pressedKeys[4]);
                Object.getByName("devVPKText2").setText(((char) KeyStroke.pressedKeys[0]) + " " + ((char) KeyStroke.pressedKeys[1]) + " " + ((char) KeyStroke.pressedKeys[2]) + " " + ((char) KeyStroke.pressedKeys[3]) + " " + ((char) KeyStroke.pressedKeys[4]));
                Frame.VPK.validate();
                Frame.VPK.repaint();
            } catch (NullPointerException e){
                System.out.print("");
            }
            try{
                Object.getByName("devVFText").setText(Main.frames + "");
                Frame.VF.setTitle("FPS : " + Frame.fps);
                Frame.VF.validate();
                Frame.VF.repaint();
            } catch (NullPointerException e){
                System.out.print("");
            }
        }
    }

    Do doAction = new Do();

    public static boolean pauseAction = false;
    private static final RuntimeException pauseEx = new RuntimeException();

    public static ArrayList<Object> movedObjects = new ArrayList<>();
    public static ArrayList<Integer> moveX = new ArrayList<>();
    public static ArrayList<Integer> moveY = new ArrayList<>();
    public static ArrayList<Object> repositionedObjects = new ArrayList<>();
    public static ArrayList<Integer> reposX = new ArrayList<>();
    public static ArrayList<Integer> reposY = new ArrayList<>();
    public static ArrayList<Integer> reposWidth = new ArrayList<>();
    public static ArrayList<Integer> reposHeight = new ArrayList<>();
    public static ArrayList<Object> preRepositionedObjects = new ArrayList<>();
    public static ArrayList<Integer> preReposX = new ArrayList<>();
    public static ArrayList<Integer> preReposY = new ArrayList<>();
    public static ArrayList<Integer> preReposWidth = new ArrayList<>();
    public static ArrayList<Integer> preReposHeight = new ArrayList<>();
    @Override
    @SuppressWarnings({"BusyWait", "unchecked"})
    public void run() {
        Main.log("Starting Play thread");
        while(play){
            try {
                Thread.sleep(1000/fps);
                if (pauseAction) { throw pauseEx; }
                new Thread(doAction).start();
                int i = 0;
                ArrayList<Integer> preX = (ArrayList<Integer>) preReposX.clone();
                ArrayList<Integer> preY = (ArrayList<Integer>) preReposY.clone();
                ArrayList<Integer> preW = (ArrayList<Integer>) preReposWidth.clone();
                ArrayList<Integer> preH = (ArrayList<Integer>) preReposHeight.clone();
                ArrayList<Object> preO = (ArrayList<Object>) preRepositionedObjects.clone();
                for(Object o : preO){
                    o.setBounds(preX.get(i), preY.get(i), preW.get(i), preH.get(i));
                    preRepositionedObjects.remove(0);
                    preReposX.remove(0);
                    preReposY.remove(0);
                    preReposWidth.remove(0);
                    preReposHeight.remove(0);
                    i++;
                }
                i = 0;
                ArrayList<Integer> x = (ArrayList<Integer>) moveX.clone();
                ArrayList<Integer> y = (ArrayList<Integer>) moveY.clone();
                ArrayList<Object> oc = (ArrayList<Object>) movedObjects.clone();
                for(Object o : oc){
                    //System.out.println(x.get(i) + " " + y.get(i));
                    o.setBounds(o.getX() + x.get(i), o.getY() + y.get(i), o.getWidth(), o.getHeight());
                    movedObjects.remove(0);
                    moveX.remove(0);
                    moveY.remove(0);
                    i++;
                }
                i = 0;
                ArrayList<Integer> px = (ArrayList<Integer>) reposX.clone();
                ArrayList<Integer> py = (ArrayList<Integer>) reposY.clone();
                ArrayList<Integer> pw = (ArrayList<Integer>) reposWidth.clone();
                ArrayList<Integer> ph = (ArrayList<Integer>) reposHeight.clone();
                ArrayList<Object> po = (ArrayList<Object>) repositionedObjects.clone();
                for(Object o : po){
                    o.setBounds(px.get(i), py.get(i), pw.get(i), ph.get(i));
                    repositionedObjects.remove(0);
                    reposX.remove(0);
                    reposY.remove(0);
                    reposWidth.remove(0);
                    reposHeight.remove(0);
                    i++;
                }
                refresh();
                Main.frames++;
                Main.libFrames[0]++;
                Main.libFrames[1]++;
                Main.libFrames[2]++;
                Main.libFrames[3]++;
                Main.libFrames[4]++;
            } catch (InterruptedException | NullPointerException e) {
                Main.logM("Exception at frame139: ");
                Main.logN(e.toString());
            } catch (RuntimeException e){
                System.out.print("");
            }
        }
        Main.log("Play thread ended");
    }

    @Override
    public void dispose(){
        this.removeKeyListener(keyStroke);
        super.dispose();
    }
}
