package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.util.ArrayList;

public class BtnObj extends JButton {
    private static final ArrayList<BtnObj> objects = new ArrayList<>();
    private static final BtnObj nullBtnObj = new BtnObj("nullBtnObj", new Rectangle(-1,-1,0,0), null, null, null, Main.panel, false, e -> {}, new Font("ButtonObject", Font.PLAIN, 1));
    @SuppressWarnings("unchecked")
    public static ArrayList<BtnObj> getClonedObjectList(){
        return (ArrayList<BtnObj>) objects.clone();
    }
    public static void showAll(){
        for (BtnObj o : objects) {
            o.setVisible(true);
        }
    }
    public static void hideAll(){
        for (BtnObj o : objects) {
            o.setVisible(false);
        }
    }
    public static void removeAllObj(){
        for(BtnObj o : getClonedObjectList()){ o.remove(); }
    }
    public BtnObj(String name, Rectangle bounds, ImageIcon icon, Color bg, String text, Container container, boolean visible, ActionListener action, Font font){
        Main.logM("Spawning buttonObj: ");
        Main.logN(name);
        this.setBounds(bounds);
        try {
            this.setIcon(new ImageIcon(icon.getImage().getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH)));
        } catch (NullPointerException e){
            System.out.print("");
        }
        this.setOpaque(true);
        this.setBackground(bg);
        this.setText(text);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.addActionListener(action);
        this.setName(name);
        this.setFont(font);
        this.setFocusable(false);
        objects.add(this);
        container.add(this);
        this.setVisible(visible);
    }

    public static BtnObj getByName(String name){
        for(BtnObj object: objects){
            if(object.getName().equals(name)){
                return object;
            }
        }
        return nullBtnObj;
    }

    public void remove() {
        if(this.equals(nullBtnObj)){ return; }
        objects.remove(this);
        Main.window.remove(this);
        Main.logM("Removed buttonObj: ");
        Main.logN(this.getName());
    }

    @SuppressWarnings("unused")
    public void changePosition(int x, int y){
        this.setBounds(this.getX()+x, this.getY()+y, this.getWidth(), this.getHeight());
    }
    @SuppressWarnings("unused")
    public void setPosition(int x, int y){
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }
    @SuppressWarnings("unused")
    public boolean collisionCheck(BtnObj o){
        return new Area(this.getBounds()).intersects(new Area(o.getBounds()).getBounds2D());
    }
}
