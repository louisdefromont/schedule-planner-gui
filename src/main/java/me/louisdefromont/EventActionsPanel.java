package me.louisdefromont;

import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventActionsPanel<T extends Event> extends JPanel {
    private T event;
    private Class<T> eventClass;
    private Class<EventEditorFrame<T>> eventEditorFrameClass;
    private EventRepository<T> eventRepository;

    public EventActionsPanel(T event, Class<T> eventClass, Class eventEditorFrameClass, EventRepository<T> eventRepository) {
        this.event = event;
        this.eventClass = eventClass;
        this.eventEditorFrameClass = eventEditorFrameClass;
        this.eventRepository = eventRepository;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel eventName = new JLabel(event.getName());
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        editButton.addActionListener(e -> {
            try {
                this.eventEditorFrameClass.getDeclaredConstructor(this.eventClass).newInstance(this.event);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        deleteButton.addActionListener(e -> {
            eventRepository.deleteEvent(event);
        });

        add(eventName);
        add(editButton);
        add(deleteButton);
    }
}
