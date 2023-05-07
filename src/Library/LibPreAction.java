package Library;

import main.BtnObj;
import main.Main;
import main.TextObj;
import main.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LibPreAction {
    public static void libPreAction(){
        new TextObj("title0", new Rectangle(0,150,1200,100), "The Library Of The Morning Sun", Main.panel, true, new Font("Malgun Gothic", Font.PLAIN, 55));
        ActionListener playButtonListener = e -> {
            main.Object.hideAll();
            Object.getByName("player").setVisible(true);
            LibAction.started = true;
        };
        new BtnObj("playButton", new Rectangle(250, 450, 300, 60), null, null, "Play", Main.panel, true, playButtonListener, new Font("Malgun Gothic", Font.PLAIN, 30));
        new BtnObj("exitButton", new Rectangle(650, 450, 300, 60), null, null, "Exit", Main.panel, true, e -> Main.exit(0, "LibPreAction18(Manual exit on menu)"), new Font("Malgun Gothic", Font.PLAIN, 30));
        new Object("player", new Rectangle(0,720,80,80), new ImageIcon("./sources/logo.png"), null, "", Main.panel, false, false);
    }
}
