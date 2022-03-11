package me.louisdefromont;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import kong.unirest.GenericType;

/**
 * Hello world!
 *
 */
public class App 
{
    private static String schedulePlannerBackendURL = "http://localhost:8080";
    public static EventRepository<PlannedEvent> plannedEventRepository = new EventRepository<PlannedEvent>(schedulePlannerBackendURL, "/plannedEvents", PlannedEvent.class);
    public static EventRepository<RepeatableEvent> repeatableEventRepository = new EventRepository<RepeatableEvent>(schedulePlannerBackendURL, "/repeatableEvents", RepeatableEvent.class);
    public static EventRepository<ToDoEvent> toDoEventRepository = new EventRepository<ToDoEvent>(schedulePlannerBackendURL, "/toDoEvents", ToDoEvent.class);
    private static RemoteScheduleGenerator remoteScheduleGenerator = new RemoteScheduleGenerator(schedulePlannerBackendURL);

    public static void main( String[] args )
    {
        JFrame frame = new JFrame("Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JButton plannedEventsButton = new JButton("Planned events");
        plannedEventsButton.addActionListener(e -> {
            new EventManagerFrame<PlannedEvent>(plannedEventRepository, PlannedEventEditor.class, PlannedEvent.class);
        });
        frame.add(plannedEventsButton);

        JButton repeatableEventsButton = new JButton("Repeatable events");
        repeatableEventsButton.addActionListener(e -> {
            new EventManagerFrame<RepeatableEvent>(repeatableEventRepository, RepeatableEventEditor.class, RepeatableEvent.class);
        });
        frame.add(repeatableEventsButton);

        JButton toDoEventsButton = new JButton("To do events");
        toDoEventsButton.addActionListener(e -> {
            new EventManagerFrame<ToDoEvent>(toDoEventRepository, ToDoEventEditor.class, ToDoEvent.class);
        });
        frame.add(toDoEventsButton);

        JButton viewScheduleButton = new JButton("View schedule");
        viewScheduleButton.addActionListener(e -> {
            new ScheduleFrame(remoteScheduleGenerator.generate());
        });
        frame.add(viewScheduleButton);
        
        frame.setVisible(true);
    }
}
