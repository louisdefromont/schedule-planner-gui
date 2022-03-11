package me.louisdefromont;

import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.github.lgooddatepicker.components.DatePicker;

public class ScheduleFrame extends JFrame {
    private Schedule schedule;
    private String[] columnNames;
    private LocalDate currentDate;
    private ScheduleDate currentScheduleDate;
    private JTable scheduleTable;
    private AbstractTableModel atm;

    public ScheduleFrame(Schedule schedule) {
        this.schedule = schedule;
        setTitle("Schedule");
        setSize(800, 600);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        addDatePicker();
        addTable();
        setVisible(true);
    }

    public void addDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setDateToToday();
        datePicker.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("date")) {
                currentDate = (LocalDate) e.getNewValue();
                if (currentDate.isAfter(schedule.getEndDate()))
                    currentDate = schedule.getEndDate();
                currentScheduleDate = schedule.getScheduleDate(currentDate);
                atm.fireTableDataChanged();
            }
        });
        add(datePicker);
    }

    public void addTable() {
        currentDate = LocalDate.now();
        currentScheduleDate = schedule.getScheduleDate(currentDate);

        columnNames = new String[] {"Event", "Start", "End"};
        atm = new AbstractTableModel() {
            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }
            @Override
            public int getRowCount() {
                return currentScheduleDate.getEvents().size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                case 0:
                    return currentScheduleDate.getEvents().get(rowIndex).getName();
                case 1:
                    return currentScheduleDate.getEvents().get(rowIndex).getStartDateTime().toLocalTime();
                case 2:
                    return currentScheduleDate.getEvents().get(rowIndex).getEndDateTime().toLocalTime();
                default:
                    return null;
                }
            }
        };
        scheduleTable = new JTable(atm);
        add(scheduleTable);
    }
}
