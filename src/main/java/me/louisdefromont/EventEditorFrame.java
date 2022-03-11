package me.louisdefromont;

import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public abstract class EventEditorFrame<T extends Event> extends JFrame {
    public T event;

    public EventEditorFrame(Class<T> eventClass) {
        try {
            this.event = eventClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setup();
    }

    public EventEditorFrame(T event) {
        this.event = event;
        setup();
    }

    public T getEvent() {
        return event;
    }

    public void setup() {
        setTitle("Event editor");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(800, 600);
        setVisible(true);
        addComponenets();
    }

    public abstract void addComponenets();

}
