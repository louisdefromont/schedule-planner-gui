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

public class RepeatableEventEditor extends JFrame {
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
        if (eventName != null) {
            eventNameTextField.setText(eventName);
        } else {
            eventNameTextField.setText("Event Name");
        }
        add(eventNameTextField);

        DatePicker datePicker = new DatePicker();
        if (date != null) {
            datePicker.setDate(date);
        } else {
            datePicker.setDateToToday();
        }
        add(datePicker);

        TimePicker startingTimePicker = new TimePicker();
        if (startingTime != null) {
            startingTimePicker.setTime(startingTime);
        } else {
            startingTimePicker.setTime(LocalTime.of(0, 0));
        }

        TimePicker endingTimePicker = new TimePicker();
        if (endingTime != null) {
            endingTimePicker.setTime(endingTime);
        } else {
            endingTimePicker.setTime(LocalTime.of(23, 59));
        }

        startingTimePicker.addPropertyChangeListener(e -> {
            if (startingTimePicker.getTime().isAfter(endingTimePicker.getTime())) {
            }
        });

        endingTimePicker.addPropertyChangeListener(e -> {
            if (startingTimePicker.getTime().isAfter(endingTimePicker.getTime())) {
            }
        });

        add(startingTimePicker);

        add(endingTimePicker);

        JSpinner repeatIntervalSpinner = new JSpinner();
        if (repeatableEvent.getRepeatInterval() != 0) {
            repeatIntervalSpinner.setValue(repeatableEvent.getRepeatInterval());
        } else {
            repeatIntervalSpinner.setValue(0);
        }
        add(repeatIntervalSpinner);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            eventName = eventNameTextField.getText();
            date = datePicker.getDate();
            startingTime = startingTimePicker.getTime();
            endingTime = endingTimePicker.getTime();
            repeatInterval = (int) repeatIntervalSpinner.getValue();
            if (repeatableEvent.getEvent().getName() != eventName) {
                Event event = new Event();
                event.setName(eventName);
                repeatableEvent.setEvent(event);
            }
            repeatableEvent.setStartDate(date);
            repeatableEvent.setStartTime(startingTime);
            repeatableEvent.setEndTime(endingTime);
            repeatableEvent.setRepeatInterval(repeatInterval);
            
        });
    }
}
