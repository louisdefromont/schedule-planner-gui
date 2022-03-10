package me.louisdefromont;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class PlannedEventEditor extends EventEditorFrame<PlannedEvent> {
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
        DatePicker datePicker = new DatePicker();
        TimePicker startingTimePicker = new TimePicker();
        TimePicker endingTimePicker = new TimePicker();

        if (plannedEvent.getId() == null) {
            eventNameTextField.setText("Event name");
            datePicker.setDateToToday();
            startingTimePicker.setTime(LocalTime.of(0, 0));
            endingTimePicker.setTime(LocalTime.of(23, 59));

        } else {
            eventNameTextField.setText(plannedEvent.getName());
            datePicker.setDate(plannedEvent.getStartTime().toLocalDate());
            startingTimePicker.setTime(plannedEvent.getStartTime().toLocalTime());
            endingTimePicker.setTime(plannedEvent.getEndTime().toLocalTime());
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

        add(eventNameTextField);
        add(datePicker);
        add(startingTimePicker);
        add(endingTimePicker);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            eventName = eventNameTextField.getText();
            date = datePicker.getDate();
            startingTime = startingTimePicker.getTime();
            endingTime = endingTimePicker.getTime();
            plannedEvent.setName(eventName);
            plannedEvent.setStartTime(date.atTime(startingTime));
            plannedEvent.setEndTime(date.atTime(endingTime));
            App.plannedEventRepository.saveEvent(plannedEvent);
            this.dispose();
        });
        add(createButton);
        
    }
}
