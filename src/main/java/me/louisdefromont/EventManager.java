package me.louisdefromont;

import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventManager <T extends Event> extends JFrame {
    private EventRepository<T> eventRepository;
    private Class<EventEditor<T>> eventEditor;
    private JPanel eventsPanel;

    public EventManager(EventRepository<T> eventRepository, Class eventEditor, Class<T> eventClass) {
        if (! eventEditor.getClass().equals(eventClass.getClass())) {
            throw new IllegalArgumentException("EventEditor class must be of the same type as the event class");
        }
        this.eventRepository = eventRepository;
        this.eventEditor = eventEditor;
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
        JButton newEventButton = new JButton("New event");
        newEventButton.addActionListener(e -> {
            try {
                eventEditor.getDeclaredConstructor().newInstance();
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
        eventRepository.getAllEvents().forEach((T event) -> {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.X_AXIS));
            JLabel eventName = new JLabel(event.getName());
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");
            eventPanel.add(eventName);
            eventPanel.add(editButton);
            eventPanel.add(deleteButton);

            eventsPanel.add(eventPanel);
        });
    }

    public void refreshEventsPanel() {
        eventsPanel.removeAll();
        addEventsToPanel();
    }
}
