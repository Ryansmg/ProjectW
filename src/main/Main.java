package main;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Calendar;

public class Main {
    public static final String version = "22.05.22.00";
    @SuppressWarnings("unused")
    public static final int versionInt = 22052200;
    @SuppressWarnings("unused")
    public static final int STANDARD_USER = 0;
    @SuppressWarnings("unused")
    public static final int TESTER_USER = 1;
    public static final int DEV_USER = 2;
    public static final boolean log = true;
    public static String mode = "Library";
    /*
    flower
    BrainLeagueTimeTable
    gameW
    Library
     */
    public static int user = DEV_USER;
    public static Calendar calendar;
    public static Frame window;
    public static JPanel panel = new JPanel(null);
    public static long frames = 0L;
    /**
     * 0:
     * 1:
     * 2:
     * 3:
     * 4:
     */
    public static long[] libFrames = {0L, 0L, 0L, 0L, 0L};
    private static String macAddress;
    public static String getMacAddress(){ return macAddress; }
    static FileWriter logWriter;
    static FileReader logReader;
    static String preLog;

    static {
        StringBuilder builder = new StringBuilder();
        try {
            logReader = new FileReader("./sources/log.txt");
            int read = logReader.read();
            while (read != -1) {
                builder.append((char) read);
                read = logReader.read();
            }
            macAddress = Arrays.toString(Inet6Address.getLocalHost().getAddress()).replace(" ", "").replace("[", "").replace("]", "").replace(",", "");
            File logFile = new File("./sources/log.txt");
            logWriter = new FileWriter(logFile);
            logWriter.write("");
            preLog = new String(builder);
        } catch (IOException ioException) {
            exit(2, "main26");
        }
    }

    public static void main(String[] args) {
        log("Program start");
        calendar = Calendar.getInstance();
        logM("Time: ");
        logN(calendar.getTime().toString());
        logM("User: ");
        if (user == 0) {
            logN("STANDARD_USER");
        } else if (user == 1) {
            JOptionPane.showMessageDialog(null, "You are using a test version of this program.\n이 버전은 테스트용 버전입니다.", "ProjectW", JOptionPane.WARNING_MESSAGE);
            logN("TESTER_USER");
            String input = input("Enter a mode", "ProjectW", JOptionPane.QUESTION_MESSAGE).replace(" ", "");
            switch(input){
                case "BrainLeagueTimeTable", "flower", "gameW", "Library" -> mode = input;
                case "" -> {}
                default -> exit(0, "main72(unsupportedMode)");
            }
        } else if (user == 2) {
            logN("DEV_USER");
            String verificationString = input( /*"Verification Process"*/ macAddress, "ProjectW", JOptionPane.QUESTION_MESSAGE); //TODO
            if(verificationString == null){
                exit(2, "Main61");
            }
            assert verificationString != null;
            if (!verificationString.equals(macAddress)) {
                showMessage("Verification failed.", "ProjectW", JOptionPane.ERROR_MESSAGE);
                exit(0, "main42 (dev Verification failed)");
            }
            String input = input("Enter a mode", "ProjectW", JOptionPane.QUESTION_MESSAGE).replace(" ", "");
            switch(input){
                case "BrainLeagueTimeTable", "flower", "gameW", "Library" -> mode = input;
                case "" -> {}
                default -> exit(0, "main88(unsupportedMode)");
            }
        }
        //
        switch(mode){
            case "BrainLeagueTimeTable" -> new Frame(Frame.LOW_RESOLUTION, null);
            case "gameW", "Library" -> new Frame(Frame.HIGH_RESOLUTION, null);
            default -> new Frame(Frame.DEFAULT_RESOLUTION, null);
        }
        panel.setName("Main.panel");
        window.setName("Main.window");
    }

    public static String logTime(){
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "." + calendar.get(Calendar.MILLISECOND);
    }
    public static void log(String s) {
        if(!log){return;}
        try {
            calendar = Calendar.getInstance();
            logWriter.append(logTime()).append(" | ").append(s).append("\n");
            System.out.println(logTime() + " | " + s);
        } catch (IOException e) {
            exit(2, "main58");
        }
    }

    /**
     * log without append(\n)
     */
    public static void logM(String s) {
        if(!log){
            return;
        }
        try {
            calendar = Calendar.getInstance();
            logWriter.append(logTime()).append(" | ").append(s);
            System.out.print(logTime() + " | " + s);
        } catch (IOException e) {
            exit(2, "main58");
        }
    }

    /**
     * log() but used after logM()
     */
    public static void logN(String s){
        if(!log){
            return;
        }
        try {
            logWriter.append(s).append("\n");
            System.out.println(s);
        } catch (IOException e) {
            exit(2, "main58");
        }
    }

    public static void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }

    public static String input(String message, String title, int type) {
        return JOptionPane.showInputDialog(null, message, title, type);
    }

    public static int yesNoInput(String message, String title, int type) {
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, type, null, null, null);
    }

    /**
     * @param status 0: normal close | 1: error during close | 2: error during running
     */
    public static void exit(int status, String closeLocation) {
        try {
            calendar = Calendar.getInstance();
            logReader.close();
            logM("Frames: ");
            logN(Main.frames + "");
            logWriter.append(logTime()).append(" | ").append("Program close at ").append(closeLocation).append("\n").append("========================================================\n").append(preLog);
            logWriter.close();
            System.exit(status);
        } catch (IOException e) {
            showMessage("An unexpected error has occurred during closing the program.", "ProjectW", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}