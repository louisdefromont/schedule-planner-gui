package me.louisdefromont;

import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventManagerFrame <T extends Event> extends JFrame {
    private EventRepository<T> eventRepository;
    private Class<T> eventClass;
    private Class<EventEditorFrame<T>> eventEditorFrameClass;
    private JPanel eventsPanel;

    public EventManagerFrame(EventRepository<T> eventRepository, Class eventEditorFrameClass, Class<T> eventClass) {
        if (! eventEditorFrameClass.getClass().equals(eventClass.getClass())) {
            throw new IllegalArgumentException("EventEditor class must be of the same type as the event class");
        }
        this.eventRepository = eventRepository;
        this.eventClass = eventClass;
        this.eventEditorFrameClass = eventEditorFrameClass;
        setTitle("Event manager");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(800, 600);
        setVisible(true);
        addComponenets();
    }

    public void addComponenets() {
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        addEventsToPanel();
        add(eventsPanel);
        eventsPanel.revalidate();
        eventsPanel.repaint();
        JButton newEventButton = new JButton("New event");
        newEventButton.addActionListener(e -> {
            try {
                eventEditorFrameClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        add(newEventButton);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            refreshEventsPanel();
        });
        add(refreshButton);
    }

    public void addEventsToPanel() {
        Iterable<T> events = eventRepository.getAllEvents();
        if (events == null) {
            return;
        }
        events.forEach((T event) -> {
            eventsPanel.add(new EventActionsPanel(event, eventClass, eventEditorFrameClass));
        });
    }

    public void refreshEventsPanel() {
        eventsPanel.removeAll();
        eventsPanel.revalidate();
        addEventsToPanel();
    }
}
