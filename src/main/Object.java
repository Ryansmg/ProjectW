package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

public class Object extends JLabel {
    private static final ArrayList<Object> objects = new ArrayList<>();
    private static final Object nullObj = new Object("nullObj", new Rectangle(-1,-1,0,0), null, null, null, Main.panel, false, true);
    @SuppressWarnings("unchecked")
    public static ArrayList<Object> getClonedObjectList(){
        return (ArrayList<Object>) objects.clone();
    }
    public final boolean isSystem;
    public static void showAll(){
        for (Object o : objects) {
            if(!o.isSystem) {
                o.setVisible(true);
            }
        }
        BtnObj.showAll();
    }
    public static void hideAll(){
        for (Object o : objects) {
            if(!o.isSystem) {
                o.setVisible(false);
            }
        }
        BtnObj.hideAll();
    }
    public static void removeAllObj(){
        for(Object o : getClonedObjectList()){ if(!o.isSystem) { o.remove(); } }
        BtnObj.removeAllObj();
    }
    public Object(String name, Rectangle bounds, ImageIcon icon, Color bg, String text, Container container, boolean visible, boolean isSystem){
        Main.logM("Spawning object: ");
        Main.logN(name);
        this.isSystem = isSystem;
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
        this.setName(name);
        this.setFocusable(false);
        objects.add(this);
        container.add(this);
        this.setVisible(visible);
    }

    public static Object getByName(String name){
        for(Object object: objects){
            if(object.getName().equals(name)){
                return object;
            }
        }
        if(!BtnObj.getByName(name).equals(BtnObj.getByName("nullBtnObj"))){
            System.out.println("Requested Obj Type : Object | Found Obj : BtnObj");
        }
        return nullObj;
    }

    public void remove() {
        if(this.equals(nullObj)){ return; }
        objects.remove(this);
        Main.window.remove(this);
        Main.logM("Removed Object: ");
        Main.logN(this.getName());
    }

    public void changePosition(int x, int y){
        if(Frame.movedObjects.contains(this)){
            int ind = Frame.movedObjects.indexOf(this);
            Frame.moveX.set(ind, Frame.moveX.get(ind) + x);
            Frame.moveY.set(ind, Frame.moveY.get(ind) + y);
        } else {
            Frame.movedObjects.add(this);
            Frame.moveX.add(x);
            Frame.moveY.add(y);
        }
    }
    //TODO make changeBounds

    /**
     * sets object position before it moves
     * @param x : object's x pos
     * @param y : object's y pos
     */
    public void setPrePosition(int x, int y){
        this.setPreBounds(x, y, this.getWidth(), this.getHeight());
    }
    public void setPreBounds(int x, int y, int width, int height){
        if(Frame.preRepositionedObjects.contains(this)){
            try {
                int ind = Frame.preRepositionedObjects.indexOf(this);
                Frame.preReposX.set(ind, Frame.preReposX.get(ind));
                Frame.preReposY.set(ind, Frame.preReposY.get(ind));
                Frame.preReposWidth.set(ind, Frame.preReposWidth.get(ind));
                Frame.preReposHeight.set(ind, Frame.preReposHeight.get(ind));
            } catch (IndexOutOfBoundsException e){
                Frame.preRepositionedObjects.add(this);
                Frame.preReposX.add(x);
                Frame.preReposY.add(y);
                Frame.preReposWidth.add(width);
                Frame.preReposHeight.add(height);
            }
        } else {
            Frame.preRepositionedObjects.add(this);
            Frame.preReposX.add(x);
            Frame.preReposY.add(y);
            Frame.preReposWidth.add(width);
            Frame.preReposHeight.add(height);
        }
    }
    public void setPosition(int x, int y){
        this.setBounds(x, y, this.getWidth(), this.getHeight(), false);
    }
    public void setBounds(int x, int y, int width, int height, boolean superMethod){
        if(superMethod){
            super.setBounds(x, y, width, height);
            return;
        }
        if(Frame.repositionedObjects.contains(this)){
            try {
                int ind = Frame.repositionedObjects.indexOf(this);
                Frame.reposX.set(ind, Frame.reposX.get(ind));
                Frame.reposY.set(ind, Frame.reposY.get(ind));
                Frame.reposWidth.set(ind, Frame.reposWidth.get(ind));
                Frame.reposHeight.set(ind, Frame.reposHeight.get(ind));
            } catch (IndexOutOfBoundsException e){
                Frame.repositionedObjects.add(this);
                Frame.reposX.add(x);
                Frame.reposY.add(y);
                Frame.reposWidth.add(width);
                Frame.reposHeight.add(height);
            }
        } else {
            Frame.repositionedObjects.add(this);
            Frame.reposX.add(x);
            Frame.reposY.add(y);
            Frame.reposWidth.add(width);
            Frame.reposHeight.add(height);
        }
    }
    public boolean collisionCheck(Object o){
        return new Area(this.getBounds()).intersects(new Area(o.getBounds()).getBounds2D());
    }
}
