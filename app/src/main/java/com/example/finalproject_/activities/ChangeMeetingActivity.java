package com.example.finalproject_.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeMeetingActivity extends AppCompatActivity {
    Button button;
    Button btn_date;//כפתור לוח שנה
    Button btn_time;//כפתור שעה
    DatePickerDialog.OnDateSetListener setListener;
    int t_hour, t_minute;//משתנים שקשורים לשעה


    TextView meeting_name_text;
    TextView date_text;//תאריך ושעה
    TextView time_text;//שעה
    TextView location_text;//מיקום
    TextView caregiver_details_text;//פרטי המטפל


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_meeting);
        getSupportActionBar().hide();
//////////////////////////////////////////////////////////////TIME
        btn_time = (Button) findViewById(R.id.time_2);
        time_text = (TextView) findViewById(R.id.textTime);


        time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (
                                ChangeMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                t_hour = hourOfDay;
                                t_minute = minute;
                                String time = t_hour + ":" + t_minute;
                                SimpleDateFormat f24 = new SimpleDateFormat
                                        (
                                                "HH:mm"
                                        );
                                try {
                                    Date date = f24.parse(time);
                                    SimpleDateFormat f12 = new SimpleDateFormat("hh:mm");
                                    time_text.setText(f12.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 24, 0, true
                        );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t_hour, t_minute);
                timePickerDialog.show();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (
                                ChangeMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                t_hour = hourOfDay;
                                t_minute = minute;
                                String time = t_hour + ":" + t_minute;
                                SimpleDateFormat f24 = new SimpleDateFormat
                                        (
                                                "HH:mm"
                                        );
                                try {
                                    Date date = f24.parse(time);
                                    SimpleDateFormat f12 = new SimpleDateFormat("hh:mm");
                                    time_text.setText(f12.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 24, 0, true
                        );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t_hour, t_minute);
                timePickerDialog.show();
            }
        });
/////////////////////////////////////////////////////////////////////////////


        btn_date = (Button) findViewById(R.id.date_2);
        date_text = (TextView) findViewById(R.id.textDate);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog
                        (ChangeMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog
                        (ChangeMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                date_text.setText(date);
            }
        };
///////////////////////////////////////////////////////////////////////////////////////

        meeting_name_text = findViewById(R.id.text_MeetingName);
        date_text = findViewById(R.id.textDate);
        time_text = findViewById(R.id.textTime);
        location_text = findViewById(R.id.textLoc);
        caregiver_details_text = findViewById(R.id.textCaregiverDetails);

        meeting_name_text.setText(getIntent().getExtras().getString("meeting_name_str"));
        date_text.setText(getIntent().getExtras().getString("date_str"));
        time_text.setText(getIntent().getExtras().getString("time_str"));
        location_text.setText(getIntent().getExtras().getString("location_str"));
        caregiver_details_text.setText(getIntent().getExtras().getString("caregiver_details_str"));

        button = findViewById(R.id.btn_change_mee);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView meeting_name_text;// שם הפגישה
                TextView date_text;//תאריך ושעה
                TextView time_text;//שעה
                TextView location_text;//מיקום
                TextView caregiver_details_text;//פרטי המטפל
                boolean took_place;//התקיימה
                String receipt;//קבלה
                String submitted;//הוגש לקופת חולים
                String returned;//הוחזר הכסף

                meeting_name_text = (TextView) findViewById(R.id.text_MeetingName);
                date_text = (TextView) findViewById(R.id.textDate);
                time_text = (TextView) findViewById(R.id.textTime);
                location_text = (TextView) findViewById(R.id.textLoc);
                caregiver_details_text = (TextView) findViewById(R.id.textCaregiverDetails);


                //to String && delete TextView


                String meeting_name_str = meeting_name_text.getText().toString();
                meeting_name_text = null;
                String date_str = date_text.getText().toString();
                date_text = null;
                String time_str = time_text.getText().toString();
                time_text = null;
                String location_str = location_text.getText().toString();
                location_text = null;
                String caregiver_details_str = caregiver_details_text.getText().toString();
                caregiver_details_text = null;

                //new Meeting


                //Meeting new_meeting=new Meeting(meeting_name_str,date_str,time_str,location_str,caregiver_details_str,false,"","","");

                Intent intent = new Intent(ChangeMeetingActivity.this, MainActivity.class);

                intent.putExtra("meeting_name_str", meeting_name_str);
                intent.putExtra("date_str", date_str);
                intent.putExtra("time_str", time_str);
                intent.putExtra("location_str", location_str);
                intent.putExtra("caregiver_details_str", caregiver_details_str);

                MyProperties.getInstance().changeMeeting = true;
                startActivity(intent);

            }
        });

    }
}
