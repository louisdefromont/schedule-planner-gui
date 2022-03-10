package me.louisdefromont;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class ToDoEventEditor extends EventEditor<ToDoEvent> {
    private ToDoEvent toDoEvent;
    private String eventName;
    private LocalDateTime dueDateTime;
    private int estimatedMinutes;

    public ToDoEventEditor() {
        this.toDoEvent = new ToDoEvent();
        setTitle("ToDo event creator");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(800, 600);
        setVisible(true);
        addComponenets();
    }

    private void addComponenets() {
        JTextField eventNameTextField = new JTextField();
        DatePicker datePicker = new DatePicker();
        TimePicker timePicker = new TimePicker();
        JSpinner estimatedMinutesSpinner = new JSpinner();

        if (toDoEvent.getId() == null) {
            eventNameTextField.setText("Event name");
            datePicker.setDateToToday();
            timePicker.setTime(LocalTime.of(0, 0));
            estimatedMinutesSpinner.setValue(0);

        } else {
            eventNameTextField.setText(toDoEvent.getName());
            datePicker.setDate(toDoEvent.getDueDateTime().toLocalDate());
            timePicker.setTime(toDoEvent.getDueDateTime().toLocalTime());
            estimatedMinutesSpinner.setValue(toDoEvent.getEstimatedMinutes());
        }

        add(eventNameTextField);
        add(datePicker);
        add(timePicker);
        add(estimatedMinutesSpinner);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            eventName = eventNameTextField.getText();
            dueDateTime = LocalDateTime.of(datePicker.getDate(), timePicker.getTime());
            estimatedMinutes = (int) estimatedMinutesSpinner.getValue();
            toDoEvent = new ToDoEvent();
            toDoEvent.setName(eventName);
            toDoEvent.setDueDateTime(dueDateTime);
            toDoEvent.setEstimatedMinutes(estimatedMinutes);
            toDoEvent.setCompleted(false);
            System.out.println(toDoEvent);
            App.toDoEventRepository.saveEvent(toDoEvent);
            this.dispose();
        });
        add(saveButton);
    }
}
