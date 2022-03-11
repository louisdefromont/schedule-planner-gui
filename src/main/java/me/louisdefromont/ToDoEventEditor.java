package me.louisdefromont;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class ToDoEventEditor extends EventEditorFrame<ToDoEvent> {
    private String eventName;
    private LocalDateTime dueDateTime;
    private int estimatedMinutes;

    public ToDoEventEditor() {
        super(ToDoEvent.class);
    }

    public ToDoEventEditor(ToDoEvent event) {
        super(event);
    }

    @Override
    public void addComponenets() {
        JTextField eventNameTextField = new JTextField();
        DatePicker datePicker = new DatePicker();
        TimePicker timePicker = new TimePicker();
        JSpinner estimatedMinutesSpinner = new JSpinner();

        if (getEvent().getId() == null) {
            eventNameTextField.setText("Event name");
            datePicker.setDateToToday();
            timePicker.setTime(LocalTime.of(0, 0));
            estimatedMinutesSpinner.setValue(0);

        } else {
            eventNameTextField.setText(getEvent().getName());
            datePicker.setDate(getEvent().getDueDateTime().toLocalDate());
            timePicker.setTime(getEvent().getDueDateTime().toLocalTime());
            estimatedMinutesSpinner.setValue(getEvent().getEstimatedMinutes());
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
            getEvent().setName(eventName);
            getEvent().setDueDateTime(dueDateTime);
            getEvent().setEstimatedMinutes(estimatedMinutes);
            getEvent().setCompleted(false);
            System.out.println(getEvent());
            App.toDoEventRepository.saveEvent(getEvent());
            this.dispose();
        });
        add(saveButton);
    }
}
