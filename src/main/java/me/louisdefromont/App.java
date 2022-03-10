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
    public static PlannedEventRepository plannedEventRepository = new PlannedEventRepository("http://localhost:8080");
    public static void main( String[] args )
    {
        JFrame frame = new JFrame("Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JButton newPlannedEventButton = new JButton("New planned event");
        newPlannedEventButton.addActionListener(e -> {
            new PlannedEventEditor();
        });
        frame.add(newPlannedEventButton);
        JButton newRepeatableEventButton = new JButton("New repeatable event");
        frame.add(newRepeatableEventButton);
        JButton newToDoEvenButton = new JButton("New to-do event");
        frame.add(newToDoEvenButton);
        frame.setVisible(true);
    }
}
