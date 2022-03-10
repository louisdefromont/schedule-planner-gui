package me.louisdefromont;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    private static String schedulePlannerBackendURL = "http://localhost:8080";
    public static PlannedEventRepository plannedEventRepository = new PlannedEventRepository(schedulePlannerBackendURL);
    public static RepeatableEventRepository repeatableEventRepository = new RepeatableEventRepository(schedulePlannerBackendURL);
    public static ToDoEventRepository toDoEventRepository = new ToDoEventRepository(schedulePlannerBackendURL);

    public static void main( String[] args )
    {
        JFrame frame = new JFrame("Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JButton plannedEventsButton = new JButton("Planned events");
        plannedEventsButton.addActionListener(e -> {
            new EventManager<PlannedEvent>(plannedEventRepository, PlannedEventEditor.class, PlannedEvent.class);
        });
        frame.add(plannedEventsButton);

        JButton newPlannedEventButton = new JButton("New planned event");
        newPlannedEventButton.addActionListener(e -> {
            new PlannedEventEditor();
        });
        frame.add(newPlannedEventButton);
        JButton newRepeatableEventButton = new JButton("New repeatable event");
        newRepeatableEventButton.addActionListener(e -> {
            new RepeatableEventEditor();
        });
        frame.add(newRepeatableEventButton);
        JButton newToDoEvenButton = new JButton("New to-do event");
        newToDoEvenButton.addActionListener(e -> {
            new ToDoEventEditor();
        });
        frame.add(newToDoEvenButton);
        frame.setVisible(true);
    }
}
