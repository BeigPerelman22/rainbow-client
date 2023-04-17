package com.example.finalproject_;

import static com.example.finalproject_.MyProperties.btn_Refund_received_b;
import static com.example.finalproject_.MyProperties.btn_Submitted_b;
import static com.example.finalproject_.MyProperties.btn_kabala_b;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMeetingActivity extends AppCompatActivity {

    private final int CHOOSE_PDF_FROM_DEVICE = 1001;
    private static final String TAG = "Main Activity";

    Button addMeetting;//כפתור הוספת פגישה
    Button btn_date;//כפתור לוח שנה
    Button btn_time;//כפתור שעה

    Button btn_receipt;//כפתור קבלה
    Button btn_Submitted;//כפתור הוגש לקופ"ח
    Button btn_Refund_received;//כפתור התקבל החזר

    boolean kabala_btn_open = true;


    FloatingActionButton img_kabala, img_submitted, img_refund_received, camera_kabala, camera_submitted, camera_Refund_received;
    DatePickerDialog.OnDateSetListener setListener;//משתנה שקשור לתאריך
    int t_hour, t_minute;//משתנים שקשורים לשעה


    TextView meeting_name_text;// שם הפגישה
    TextView date_text;//תאריך ושעה
    TextView time_text;//שעה
    TextView location_text;//מיקום
    TextView caregiver_details_text;//פרטי המטפל

    TextView text_kabala;

    TextView text_Submitted_to_KOFH;

    TextView text_Refund_received;
    boolean took_place;//התקיימה


    //Boolean isOpen=false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().hide();

        addMeetting = findViewById(R.id.btn_change_mee);


/////////////////////////////////////////time
        btn_time = (Button) findViewById(R.id.time_2);
        time_text = (TextView) findViewById(R.id.textTime);


        time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (
                                AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
                                AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
////////////////////////////////////////
////////////////////////////////////////////date
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
                        (AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog
                        (AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
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
        //add file
        //add files

        boolean submitted_btn_open = true;
        boolean refund_received_btn_open = true;

        img_kabala = (FloatingActionButton) findViewById(R.id.float_kabala_img);
        camera_kabala = (FloatingActionButton) findViewById(R.id.float_kabala_camera);
        img_kabala.hide();
        camera_kabala.hide();

        btn_receipt = findViewById(R.id.btn_add_files_kabala);
        btn_Submitted = findViewById(R.id.btn_Submitted_to_KOFH);
        btn_Refund_received = findViewById(R.id.btn_Refund_received);

        text_kabala = findViewById(R.id.textView_kabala);
        text_Submitted_to_KOFH = findViewById(R.id.textView_Submitted_to_KOFH);
        text_Refund_received = findViewById(R.id.textView_Refund_received);


        btn_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kabala_btn_open) {
                    img_kabala.show();
                    camera_kabala.show();
                    kabala_btn_open = false;
                } else {
                    img_kabala.hide();
                    camera_kabala.hide();
                    kabala_btn_open = true;
                }


//                MyProperties.getInstance().btn_kabala_b=true;
//                callChooseFileFromDevice();
            }
        });


        btn_Submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProperties.getInstance().btn_Submitted_b = true;
                callChooseFileFromDevice();
            }
        });
        btn_Refund_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProperties.getInstance().btn_Refund_received_b = true;
                callChooseFileFromDevice();
            }
        });

///////////////////////////////////////////////////
        addMeetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //refers to the activity


                meeting_name_text = (TextView) findViewById(R.id.text_MeetingName);
                location_text = (TextView) findViewById(R.id.textLoc);
                caregiver_details_text = (TextView) findViewById(R.id.textCaregiverDetails);

                text_kabala = (TextView) findViewById(R.id.textView_kabala);
                text_Submitted_to_KOFH = (TextView) findViewById(R.id.textView_Submitted_to_KOFH);
                text_Refund_received = (TextView) findViewById(R.id.textView_Refund_received);


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

                String kabala_str = text_kabala.getText().toString();
                String submitted_str = text_Submitted_to_KOFH.getText().toString();
                String refund_str = text_Refund_received.getText().toString();


                //new Meeting

                Intent intent = new Intent(AddMeetingActivity.this, MainActivity.class);

                intent.putExtra("meeting_name_str", meeting_name_str);
                intent.putExtra("date_str", date_str);
                intent.putExtra("time_str", time_str);
                intent.putExtra("location_str", location_str);
                intent.putExtra("caregiver_details_str", caregiver_details_str);

                intent.putExtra("kabala_str", kabala_str);
                intent.putExtra("submitted_str", submitted_str);
                intent.putExtra("refund_str", refund_str);

                MyProperties.getInstance().addNewMeeting = true;

                startActivity(intent);

            }
        });

    }

    public void callChooseFileFromDevice() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, CHOOSE_PDF_FROM_DEVICE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_PDF_FROM_DEVICE && resultCode == RESULT_OK) {
            //the result data contains a URI for the document od directory that  the user selected.
            if (resultData != null) {
                Log.d(TAG, "on ActivityResult:" + resultData.getData());
//            System.out.println(resultData.getData());
                if (btn_kabala_b == true) {
                    btn_kabala_b = false;
                    String receipt = String.valueOf(resultData.getData());//קבלה
                    text_kabala.setText(receipt);


                } else if (btn_Submitted_b == true) {
                    btn_Submitted_b = false;
                    String submitted = String.valueOf(resultData.getData());//// הוגש לקופת חולים
                    text_Submitted_to_KOFH.setText(submitted);

                } else if (btn_Refund_received_b == true) {
                    btn_Refund_received_b = false;
                    String returned = String.valueOf(resultData.getData());//הוחזר הכסף
                    text_Refund_received.setText(returned);

                }
            }

        }

    }

}