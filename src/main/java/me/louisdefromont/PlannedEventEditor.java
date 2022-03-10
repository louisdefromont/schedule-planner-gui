package me.louisdefromont;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class PlannedEventEditor extends JFrame {
    PlannedEvent plannedEvent;
    String eventName;
    LocalDate date;
    LocalTime startingTime;
    LocalTime endingTime;
    public PlannedEventEditor() {
        this.plannedEvent = new PlannedEvent();
        setTitle("Planned event creator");
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
                endingTimePicker.setTime(startingTimePicker.getTime());
            }
        });

        endingTimePicker.addPropertyChangeListener(e -> {
            if (startingTimePicker.getTime().isAfter(endingTimePicker.getTime())) {
                endingTimePicker.setTime(startingTimePicker.getTime());
            }
        });

        add(startingTimePicker);
        add(endingTimePicker);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            eventName = eventNameTextField.getText();
            date = datePicker.getDate();
            startingTime = startingTimePicker.getTime();
            endingTime = endingTimePicker.getTime();
            if (! plannedEvent.getEvent().getName().equals(eventName)) {
                Event event = new Event();
                event.setName(eventName);
                plannedEvent.setEvent(event);
            }
            plannedEvent.setStartTime(date.atTime(startingTime));
            plannedEvent.setEndTime(date.atTime(endingTime));
            App.plannedEventRepository.createPlannedEvent(plannedEvent);
            this.dispose();
        });
        add(createButton);
        
    }
}
