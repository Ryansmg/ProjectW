package main;

import javax.swing.*;
import java.util.ArrayList;

public class MenuConfig {
    public static ArrayList<JMenu> getMenus(){
        ArrayList<JMenu> menus = new ArrayList<>();
        switch(Main.mode){
            case "gameW" -> {
                JMenu showEditor = new JMenu("Editor");
                JMenuItem showEditorI = new JMenuItem("showEditor");
                showEditorI.addActionListener( e -> {
                    Main.showMessage("WIP", "ProjectW", JOptionPane.QUESTION_MESSAGE);
                });
                showEditor.add(showEditorI);
                menus.add(showEditor);
            }
            case "flower" -> {}
            case "BrainLeagueTimeTable" -> {
                JMenu chooseClass = new JMenu("반 선택");
                JMenuItem science = new JMenuItem("과학");
                science.addActionListener(e -> {
                    String[] options = {"2SB", "2SR", "2SA"};
                    main.Action.bltt_science = JOptionPane.showOptionDialog(null, "Choose your class", "ProjectW", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    main.Action.preAction();
                });
                chooseClass.add(science);
                JMenuItem math = new JMenuItem("수학");
                math.addActionListener(e -> {
                    String[] options = {"2B2", "2B1A", "2B1B"};
                    main.Action.bltt_math = JOptionPane.showOptionDialog(null, "Choose your class", "ProjectW", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    main.Action.preAction();
                });
                chooseClass.add(math);
                JMenuItem kmo = new JMenuItem("KMO");
                kmo.addActionListener(e -> {
                    String[] options = {"K1A", "K1B", "K1C"};
                    main.Action.bltt_kmo = JOptionPane.showOptionDialog(null, "Choose your class", "ProjectW", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    Action.preAction();
                });
                chooseClass.add(kmo);
                menus.add(chooseClass);
            }
        }
        return menus;
    }
}
