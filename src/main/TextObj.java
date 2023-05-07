package main;

import java.awt.*;

public class TextObj extends Object {
    public TextObj(String name, Rectangle bounds, String text, Container container, boolean visible, Font font, boolean isSystem){
        super(name, bounds, null, null, text, container, visible, isSystem);
        this.setFont(font);
        this.setHorizontalTextPosition(Object.CENTER);
        this.setVerticalTextPosition(Object.CENTER);
        this.setHorizontalAlignment(Object.CENTER);
        this.setVerticalTextPosition(Object.CENTER);
    }
    public TextObj(String name, Rectangle bounds, String text, Container container, boolean visible, Font font) {
        super(name, bounds, null, null, text, container, visible, false);
        this.setFont(font);
        this.setHorizontalTextPosition(Object.CENTER);
        this.setVerticalTextPosition(Object.CENTER);
        this.setHorizontalAlignment(Object.CENTER);
        this.setVerticalTextPosition(Object.CENTER);
    }
}
