package com.kl3jvi.pomodroid.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.kl3jvi.pomodroid.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        List<EventDay> events = new ArrayList<>();
        List<Calendar> days = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon2));
        days.add(calendar);

        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setEvents(events);
        calendarView.setHighlightedDays(days);
    }
}