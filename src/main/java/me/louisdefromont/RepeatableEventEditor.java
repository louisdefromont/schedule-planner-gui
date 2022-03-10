package me.louisdefromont;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class RepeatableEventEditor extends EventEditorFrame<RepeatableEvent> {
    RepeatableEvent repeatableEvent;
    String eventName;
    LocalDate date;
    LocalTime startingTime;
    LocalTime endingTime;
    int repeatInterval;

    public RepeatableEventEditor() {
        this.repeatableEvent = new RepeatableEvent();
        setTitle("Repeatable event creator");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(800, 600);
        setVisible(true);
        addComponenets();
    }

    public void addComponenets() {
        JTextField eventNameTextField = new JTextField();
        DatePicker datePicker = new DatePicker();
        TimePicker startingTimePicker = new TimePicker();
        TimePicker endingTimePicker = new TimePicker();
        JSpinner repeatIntervalSpinner = new JSpinner();

        if (repeatableEvent.getId() == null) {
            eventNameTextField.setText("Event name");
            datePicker.setDateToToday();
            startingTimePicker.setTime(LocalTime.of(0, 0));
            endingTimePicker.setTime(LocalTime.of(23, 59));
            repeatIntervalSpinner.setValue(1);

        } else {
            eventNameTextField.setText(repeatableEvent.getName());
            datePicker.setDate(repeatableEvent.getStartDate());
            startingTimePicker.setTime(repeatableEvent.getStartTime());
            endingTimePicker.setTime(repeatableEvent.getEndTime());
            repeatIntervalSpinner.setValue(repeatableEvent.getRepeatInterval());
        }

        

        startingTimePicker.addPropertyChangeListener(e -> {
            if (startingTimePicker.getTime().isAfter(endingTimePicker.getTime())) {
            }
        });

        endingTimePicker.addPropertyChangeListener(e -> {
            if (startingTimePicker.getTime().isAfter(endingTimePicker.getTime())) {
            }
        });

        add(eventNameTextField);
        add(datePicker);
        add(startingTimePicker);
        add(endingTimePicker);
        add(repeatIntervalSpinner);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            eventName = eventNameTextField.getText();
            date = datePicker.getDate();
            startingTime = startingTimePicker.getTime();
            endingTime = endingTimePicker.getTime();
            repeatInterval = (int) repeatIntervalSpinner.getValue();
            repeatableEvent.setName(eventName);
            repeatableEvent.setStartDate(date);
            repeatableEvent.setStartTime(startingTime);
            repeatableEvent.setEndTime(endingTime);
            repeatableEvent.setRepeatInterval(repeatInterval);
            App.repeatableEventRepository.saveEvent(repeatableEvent);
            this.dispose();
        });
        add(saveButton);
    }
}
