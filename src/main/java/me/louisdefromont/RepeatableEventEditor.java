package me.louisdefromont;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class RepeatableEventEditor extends EventEditorFrame<RepeatableEvent> {
    String eventName;
    LocalDate date;
    LocalTime startingTime;
    LocalTime endingTime;
    int repeatInterval;

    public RepeatableEventEditor() {
        super(RepeatableEvent.class);
    }

    public RepeatableEventEditor(RepeatableEvent event) {
        super(event);
    }

    @Override
    public void addComponenets() {
        JTextField eventNameTextField = new JTextField();
        DatePicker datePicker = new DatePicker();
        TimePicker startingTimePicker = new TimePicker();
        TimePicker endingTimePicker = new TimePicker();
        JSpinner repeatIntervalSpinner = new JSpinner();

        if (getEvent().getId() == null) {
            eventNameTextField.setText("Event name");
            datePicker.setDateToToday();
            startingTimePicker.setTime(LocalTime.of(0, 0));
            endingTimePicker.setTime(LocalTime.of(23, 59));
            repeatIntervalSpinner.setValue(1);

        } else {
            eventNameTextField.setText(getEvent().getName());
            datePicker.setDate(getEvent().getStartDate());
            startingTimePicker.setTime(getEvent().getStartTime());
            endingTimePicker.setTime(getEvent().getEndTime());
            repeatIntervalSpinner.setValue(getEvent().getRepeatInterval());
        }

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
            getEvent().setName(eventName);
            getEvent().setStartDate(date);
            getEvent().setStartTime(startingTime);
            getEvent().setEndTime(endingTime);
            getEvent().setRepeatInterval(repeatInterval);
            App.repeatableEventRepository.saveEvent(getEvent());
            this.dispose();
        });
        add(saveButton);
    }
}
