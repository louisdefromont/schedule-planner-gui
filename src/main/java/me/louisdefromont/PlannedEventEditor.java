package me.louisdefromont;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class PlannedEventEditor extends EventEditorFrame<PlannedEvent> {
    String eventName;
    LocalDate date;
    LocalTime startingTime;
    LocalTime endingTime;
    public PlannedEventEditor() {
        super(PlannedEvent.class);
    }

    public PlannedEventEditor(PlannedEvent event) {
        super(event);
    }

    @Override
    public void addComponenets() {
        JTextField eventNameTextField = new JTextField();
        DatePicker datePicker = new DatePicker();
        TimePicker startingTimePicker = new TimePicker();
        TimePicker endingTimePicker = new TimePicker();

        if (getEvent().getId() == null) {
            eventNameTextField.setText("Event name");
            datePicker.setDateToToday();
            startingTimePicker.setTime(LocalTime.of(0, 0));
            endingTimePicker.setTime(LocalTime.of(23, 59));

        } else {
            eventNameTextField.setText(getEvent().getName());
            if (getEvent().getStartTime() == null) {
                datePicker.setDate(LocalDate.now());
                startingTimePicker.setTime(LocalTime.of(0, 0));
            } else {
                datePicker.setDate(getEvent().getStartTime().toLocalDate());
                startingTimePicker.setTime(getEvent().getStartTime().toLocalTime());
            }
            if (getEvent().getEndTime() == null) {
                endingTimePicker.setTime(LocalTime.of(23, 59));
            } else {
                endingTimePicker.setTime(getEvent().getEndTime().toLocalTime());
            }
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
            getEvent().setName(eventName);
            getEvent().setStartTime(date.atTime(startingTime));
            getEvent().setEndTime(date.atTime(endingTime));
            App.plannedEventRepository.saveEvent(getEvent());
            this.dispose();
        });
        add(createButton);
        
    }
}
