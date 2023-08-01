package com.example.finalproject_.activities;

import static com.example.finalproject_.MyProperties.btn_Refund_received_b;
import static com.example.finalproject_.MyProperties.btn_Submitted_b;
import static com.example.finalproject_.MyProperties.btn_kabala_b;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.finalproject_.CustomSpinner;
import com.example.finalproject_.DataSpiner;
import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.TypeMeetingAdapter;
import com.example.finalproject_.interfaces.NetworkResponseCallback;
import com.example.finalproject_.mappers.EventTypeMapper;
import com.example.finalproject_.models.DriveFile;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.network.EventRequestsExecutor;
import com.example.finalproject_.notifications.NotificationScheduler;
import com.example.finalproject_.utils.DateTimeUtils;
import com.example.finalproject_.utils.DriveRequestUtils;
import com.example.finalproject_.utils.LoaderUtils;
import com.example.finalproject_.utils.MyApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMeetingActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {

    private EventRequestsExecutor eventRequestsExecutor;
    private EventTypeMapper eventTypeMapper;
    private NotificationScheduler notificationScheduler;
    private DriveRequestUtils driveRequestUtils;
    public CustomSpinner spinner_t_m;

    private TypeMeetingAdapter adapter;
    private String mCurrentPhotoPath;//לפונקציה ששומרת Path

    Button addMeetting;//כפתור הוספת פגישה
    ImageView btn_date;//כפתור לוח שנה
    ImageView btn_time;//כפתור שעה

    ImageView btn_x_name;//כפתור x
    ImageView btn_x_time;//כפתור x
    ImageView btn_x_date;//כפתור x
    ImageView btn_x_loc;//כפתור x
    ImageView btn_x_4;//כפתור x

    ImageView imageView_delete_kabala;//כפתור פח
    ImageView imageView_delete_sub;//כפתור פח
    ImageView imageView_delete_refund;//כפתור פח

    Button btn_receipt;//כפתור קבלה
    Button btn_Submitted;//כפתור הוגש לקופ"ח
    Button btn_Refund_received;//כפתור התקבל החזר

    Button btn_tookplace;//כפתור התקיים
    //////////////////////////////////////////  in the meeting
    boolean took_place = false;//לפגישה
    boolean kabala = false;//לפגישה
    boolean submitted = false;//לפגישה
    boolean refund_received = false;//לפגישה
    ////////////////////////////////////////
    boolean kabala_btn_open = true;//אם הכפתור דלוק
    boolean submitted_btn_open = true;//אם הכפתור דלוק
    boolean refund_received_btn_open = true;//אם הכפתור דלוק


    FloatingActionButton img_kabala, img_submitted, img_refund_received;//הכפתורים של כל סוגי בקשת קבצים
    FloatingActionButton kabala_v, sub_v, refund_received_v;//הכפתורים של כל סוגי בקשת קבצים


    ImageView imageFilterView_kabala;//תמונה של סוג קובץ שעולה
    ImageView imageFilterView_submitted;//תמונה של סוג קובץ שעולה
    ImageView imageFilterView_Refund_received;//תמונה של סוג קובץ שעולה
    DatePickerDialog.OnDateSetListener setListener;//משתנה שקשור לתאריך
    int t_hour, t_minute;//משתנים שקשורים לשעה

    TextView meeting_name_text;// שם הפגישה
    TextView date_text;//תאריך ושעה
    TextView time_text;//שעה
    TextView location_text;//מיקום
    TextView caregiver_details_text;//פרטי המטפל

    Uri uri_kabala;
    Uri uri_Submitted;
    Uri uri_received;

    String receiptWebLink;
    String submittedWebLink;
    String refundWebLink;


    //Boolean isOpen=false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        eventRequestsExecutor = new EventRequestsExecutor();
        eventTypeMapper = new EventTypeMapper();
        notificationScheduler = new NotificationScheduler(MyApplication.getInstance());
        driveRequestUtils = new DriveRequestUtils();
        getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#E0DAED"));
        setCharacterLimit(meeting_name_text, 22);
        setCharacterLimit(location_text, 22);
        setCharacterLimit(caregiver_details_text, 25);
//        initEventTypeSpinner();
        spinner_t_m = findViewById(R.id.spinner_fruits);

        spinner_t_m.setSpinnerEventsListener(this);

        adapter = new TypeMeetingAdapter(AddMeetingActivity.this, DataSpiner.getTypeMeetingList());
        spinner_t_m.setAdapter((SpinnerAdapter) adapter);
        addMeetting = findViewById(R.id.btn_change_mee);


        imageView_delete_kabala = (ImageView) findViewById(R.id.imageView_delete_kabala);
        imageView_delete_sub = (ImageView) findViewById(R.id.imageView_delete_submitted);
        imageView_delete_refund = (ImageView) findViewById(R.id.imageView_delete_refund);


        imageFilterView_kabala = (ImageView) findViewById(R.id.imageFilterView_kabala);
        imageFilterView_submitted = (ImageView) findViewById(R.id.imageFilterView_submitted);
        imageFilterView_Refund_received = (ImageView) findViewById(R.id.imageFilterView_Refund_received);

        kabala_v = (FloatingActionButton) findViewById(R.id.float_kabala_V);
        img_kabala = (FloatingActionButton) findViewById(R.id.float_kabala_img);
        img_submitted = (FloatingActionButton) findViewById(R.id.float_submitted_img);
        sub_v = (FloatingActionButton) findViewById(R.id.float_sub_V);
        refund_received_v = (FloatingActionButton) findViewById(R.id.float_refund_received_V);
        img_refund_received = (FloatingActionButton) findViewById(R.id.float_Refund_received_img);

        kabala_v.hide();
        img_kabala.hide();

        sub_v.hide();
        img_submitted.hide();

        refund_received_v.hide();
        img_refund_received.hide();


        btn_tookplace = findViewById(R.id.btn_took_place);
        btn_receipt = findViewById(R.id.btn_add_files_kabala);
        btn_Submitted = findViewById(R.id.btn_Submitted_to_KOFH);
        btn_Refund_received = findViewById(R.id.btn_Refund_received);

//        text_kabala=findViewById(R.id.textView_kabala);
//        text_Submitted_to_KOFH=findViewById(R.id.textView_Submitted_to_KOFH);
//        text_Refund_received=findViewById(R.id.textView_Refund_received);

        btn_x_date = (ImageView) findViewById(R.id.imageView_X_date);
        btn_x_time = (ImageView) findViewById(R.id.imageView_X_time);
        btn_x_loc = (ImageView) findViewById(R.id.imageView_X_loc);
        btn_x_4 = (ImageView) findViewById(R.id.imageView_X_4);
        btn_x_name = (ImageView) findViewById(R.id.imageView_X_name);


/////////////////////////////////////////time
        btn_time = (ImageView) findViewById(R.id.time_21_c);
        meeting_name_text = (TextView) findViewById(R.id.text_MeetingName_c);
        time_text = (TextView) findViewById(R.id.textTime_c);
        location_text = (TextView) findViewById(R.id.textLoc_c);
        caregiver_details_text = (TextView) findViewById(R.id.textCaregiverDetails_c);


        time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!submitted_btn_open) {
                    img_submitted.hide();

                    sub_v.hide();
                    submitted_btn_open = true;
                }
                if (!kabala_btn_open) {
                    img_kabala.hide();

                    kabala_v.hide();
                    kabala_btn_open = true;
                }
                if (!refund_received_btn_open) {
                    img_refund_received.hide();

                    refund_received_v.hide();
                    refund_received_btn_open = true;
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (
                                AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                t_hour = hourOfDay;
                                t_minute = minute;
                                String time = t_hour + ":" + t_minute;
                                SimpleDateFormat f12 = new SimpleDateFormat
                                        (
                                                "HH:mm"
                                        );
                                try {
                                    Date date = f12.parse(time);
                                    SimpleDateFormat f24 = new SimpleDateFormat("HH:mm ");
                                    time_text.setText(f24.format(date));
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
                if (!submitted_btn_open) {
                    img_submitted.hide();

                    sub_v.hide();
                    submitted_btn_open = true;
                }
                if (!kabala_btn_open) {
                    img_kabala.hide();

                    kabala_v.hide();
                    kabala_btn_open = true;
                }
                if (!refund_received_btn_open) {
                    img_refund_received.hide();

                    refund_received_v.hide();
                    refund_received_btn_open = true;
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (
                                AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                t_hour = hourOfDay;
                                t_minute = minute;
                                String time = t_hour + ":" + t_minute;
                                SimpleDateFormat f24 = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24.parse(time);
                                    SimpleDateFormat f12 = new SimpleDateFormat("HH:mm");
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
        btn_date = (ImageView) findViewById(R.id.date_2_c);
        date_text = (TextView) findViewById(R.id.textDate_c);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!submitted_btn_open) {
                    img_submitted.hide();

                    sub_v.hide();
                    submitted_btn_open = true;
                }
                if (!kabala_btn_open) {
                    img_kabala.hide();

                    kabala_v.hide();
                    kabala_btn_open = true;
                }
                if (!refund_received_btn_open) {
                    img_refund_received.hide();

                    refund_received_v.hide();
                    refund_received_btn_open = true;
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog
                        (AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!submitted_btn_open) {
                    img_submitted.hide();

                    sub_v.hide();
                    submitted_btn_open = true;
                }
                if (!kabala_btn_open) {
                    img_kabala.hide();

                    kabala_v.hide();
                    kabala_btn_open = true;
                }
                if (!refund_received_btn_open) {
                    img_refund_received.hide();

                    refund_received_v.hide();
                    refund_received_btn_open = true;
                }
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
                String date = dayOfMonth + "/" + month + "/" + year;
                date_text.setText(date);
            }
        };


        //add file
        //add files

        imageFilterView_kabala.setOnClickListener(new View.OnClickListener() {//OPEN FILE KABALA
            @Override
            public void onClick(View v) {
                if (Objects.nonNull(receiptWebLink)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(receiptWebLink));
                    startActivity(intent);
                }
            }
        });
        imageFilterView_submitted.setOnClickListener(new View.OnClickListener() {//OPEN FILE SUB
            @Override
            public void onClick(View v) {
                if (Objects.nonNull(submittedWebLink)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(submittedWebLink));
                    startActivity(intent);
                }
            }
        });
        imageFilterView_Refund_received.setOnClickListener(new View.OnClickListener() {//OPEN FILE Refund received
            @Override
            public void onClick(View v) {
                if (Objects.nonNull(refundWebLink)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(refundWebLink));
                    startActivity(intent);
                }
            }
        });

        btn_tookplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (took_place == false) {
                    took_place = true;
                    btn_tookplace.setBackgroundResource(R.drawable.round_button_green);
                } else {
                    took_place = false;
                    btn_tookplace.setBackgroundResource(R.drawable.round_button);
                }
            }
        });
        kabala_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kabala == false) {
                    kabala = true;
                    btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                    kabala_v.hide();
                    img_kabala.hide();

                    kabala_btn_open = true;
                } else {
                    kabala = false;
                    btn_kabala_b = false;
                    imageFilterView_kabala.setImageBitmap(null);
                    imageView_delete_kabala.setVisibility(View.INVISIBLE);
                    btn_receipt.setBackgroundResource(R.drawable.round_button);
                    kabala_v.hide();
                    img_kabala.hide();

                    kabala_btn_open = true;
                }
            }
        });
        sub_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitted == false) {
                    submitted = true;
                    btn_Submitted.setBackgroundResource(R.drawable.round_button_green);
                    sub_v.hide();
                    img_submitted.hide();

                    submitted_btn_open = true;
                } else {
                    submitted = false;
                    btn_Submitted_b = false;
                    imageFilterView_submitted.setImageBitmap(null);
                    imageView_delete_sub.setVisibility(View.INVISIBLE);
                    btn_Submitted.setBackgroundResource(R.drawable.round_button);
                    sub_v.hide();
                    img_submitted.hide();

                    submitted_btn_open = true;
                }
            }
        });
        refund_received_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (refund_received == false) {
                    refund_received = true;
                    btn_Refund_received.setBackgroundResource(R.drawable.round_button_green);
                    refund_received_v.hide();
                    img_refund_received.hide();

                    refund_received_btn_open = true;
                } else {
                    refund_received = false;
                    btn_Refund_received.setBackgroundResource(R.drawable.round_button);
                    refund_received_v.hide();
                    img_refund_received.hide();

                    btn_Refund_received_b = false;

                    imageFilterView_Refund_received.setImageBitmap(null);
                    imageView_delete_refund.setVisibility(View.INVISIBLE);
                    refund_received_btn_open = true;

                }
            }
        });

        imageView_delete_kabala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_kabala_b = false;
                kabala = false;
                uri_kabala = null;
                imageFilterView_kabala.setImageBitmap(null);
                btn_receipt.setBackgroundResource(R.drawable.round_button);
                imageView_delete_kabala.setVisibility(View.INVISIBLE);
                kabala_v.setImageResource(R.drawable.baseline_check_24);

            }
        });

        imageView_delete_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Submitted_b = false;
                submitted = false;
                uri_Submitted = null;
                imageFilterView_submitted.setImageBitmap(null);
                btn_Submitted.setBackgroundResource(R.drawable.round_button);
                imageView_delete_sub.setVisibility(View.INVISIBLE);
                sub_v.setImageResource(R.drawable.baseline_check_24);
            }
        });
        imageView_delete_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Refund_received_b = false;
                refund_received = false;
                uri_received = null;
                imageFilterView_Refund_received.setImageBitmap(null);
                btn_Refund_received.setBackgroundResource(R.drawable.round_button);
                imageView_delete_refund.setVisibility(View.INVISIBLE);
                refund_received_v.setImageResource(R.drawable.baseline_check_24);
            }
        });


        meeting_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btn_x_name.setVisibility(View.VISIBLE); // כפתור יופיע כאשר הטקסט ב-TextView אינו ריק
                } else {
                    btn_x_name.setVisibility(View.INVISIBLE); // כפתור ייחבא כאשר הטקסט ב-TextView ריק
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
        date_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btn_x_date.setVisibility(View.VISIBLE); // כפתור יופיע כאשר הטקסט ב-TextView אינו ריק
                } else {
                    btn_x_date.setVisibility(View.INVISIBLE); // כפתור ייחבא כאשר הטקסט ב-TextView ריק
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
        time_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btn_x_time.setVisibility(View.VISIBLE); // כפתור יופיע כאשר הטקסט ב-TextView אינו ריק
                } else {
                    btn_x_time.setVisibility(View.INVISIBLE); // כפתור ייחבא כאשר הטקסט ב-TextView ריק
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
        location_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btn_x_loc.setVisibility(View.VISIBLE); // כפתור יופיע כאשר הטקסט ב-TextView אינו ריק
                } else {
                    btn_x_loc.setVisibility(View.INVISIBLE); // כפתור ייחבא כאשר הטקסט ב-TextView ריק
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
        caregiver_details_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btn_x_4.setVisibility(View.VISIBLE); // כפתור יופיע כאשר הטקסט ב-TextView אינו ריק
                } else {
                    btn_x_4.setVisibility(View.INVISIBLE); // כפתור ייחבא כאשר הטקסט ב-TextView ריק
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
        btn_x_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meeting_name_text.setText("");
            }
        });
        btn_x_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_text.setText("");
            }
        });
        btn_x_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_text.setText("");
            }
        });
        btn_x_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_text.setText("");
            }
        });
        btn_x_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caregiver_details_text.setText("");
            }
        });

        btn_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_submitted.hide();

                sub_v.hide();
                submitted_btn_open = true;
                img_refund_received.hide();

                refund_received_v.hide();
                refund_received_btn_open = true;

                if (kabala_btn_open == false) {
                    kabala_btn_open = true;
                    img_kabala.hide();

                    kabala_v.hide();
                    if (kabala == true) {
                        kabala_v.setImageResource(R.drawable.cancel);


                    } else {
                        kabala_v.setImageResource(R.drawable.baseline_check_24);
                    }
                } else {
                    kabala_btn_open = false;
                    img_kabala.show();

                    kabala_v.show();

                    if (kabala == true) {
                        kabala_v.setImageResource(R.drawable.cancel);
                    } else {
                        kabala_v.setImageResource(R.drawable.baseline_check_24);
                    }
                }

            }
        });
        btn_Submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_kabala.hide();

                kabala_v.hide();
                kabala_btn_open = true;
                img_refund_received.hide();
                refund_received_v.hide();

                refund_received_v.hide();
                refund_received_btn_open = true;

                if (submitted_btn_open == false) {
                    submitted_btn_open = true;
                    img_submitted.hide();

                    sub_v.hide();

                    if (submitted == true) {
                        sub_v.setImageResource(R.drawable.cancel);


                    } else {
                        sub_v.setImageResource(R.drawable.baseline_check_24);

                    }

                } else {

                    submitted_btn_open = false;

                    img_submitted.show();

                    sub_v.show();


                    if (submitted == true) {
                        sub_v.setImageResource(R.drawable.cancel);


                    } else {
                        sub_v.setImageResource(R.drawable.baseline_check_24);

                    }


                }


            }
        });
        btn_Refund_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_submitted.hide();

                sub_v.hide();
                submitted_btn_open = true;
                img_kabala.hide();

                kabala_v.hide();
                kabala_btn_open = true;

                if (refund_received_btn_open == false) {
                    refund_received_btn_open = true;

                    img_refund_received.hide();

                    refund_received_v.hide();

                    if (refund_received == true) {
                        refund_received_v.setImageResource(R.drawable.cancel);

                    } else {
                        refund_received_v.setImageResource(R.drawable.baseline_check_24);

                    }

                } else {
                    refund_received_btn_open = false;

                    img_refund_received.show();

                    refund_received_v.show();

                    if (refund_received == true) {
                        refund_received_v.setImageResource(R.drawable.cancel);

                    } else {
                        refund_received_v.setImageResource(R.drawable.baseline_check_24);

                    }

                }


            }
        });

        img_kabala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProperties.getInstance().btn_kabala_b = true;
                callChooseFileFromDevice();
            }
        });
        img_submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProperties.getInstance().btn_Submitted_b = true;
                callChooseFileFromDevice();
            }
        });
        img_refund_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProperties.getInstance().btn_Refund_received_b = true;
                callChooseFileFromDevice();
            }
        });

///camera


///////////////////////////////////////////////////
        addMeetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (meeting_name_text.getText().toString().isEmpty()) {
                    Toast.makeText(AddMeetingActivity.this, "שם הפגישה לא מוגדר", Toast.LENGTH_SHORT).show();
                } else if (date_text.getText().toString().isEmpty() || time_text.getText().toString().isEmpty() || isTimeValid(time_text.getText().toString()) == false || isDateValid(date_text.getText().toString())) {
                    Toast.makeText(AddMeetingActivity.this, "התאריך או השעה לא מוגדרים", Toast.LENGTH_SHORT).show();
                } else {
                    String meeting_name_str = meeting_name_text.getText().toString();
                    String date_str = date_text.getText().toString();
                    String time_str = time_text.getText().toString();
                    String location_str = location_text.getText().toString();
                    String caregiver_details_str = caregiver_details_text.getText().toString();
                    Spinner spinner = findViewById(R.id.spinner_fruits);
                    int eventType = (int) spinner.getSelectedItem();
                    resetForm();

                    EventModel createEventRequestModel = new EventModel();

                    String formattedDateTime = DateTimeUtils.dateAndTimeToDateTime(date_str, time_str);

                    createEventRequestModel.setDescription(meeting_name_str);
                    createEventRequestModel.setStartTime(formattedDateTime);
                    createEventRequestModel.setEndTime(formattedDateTime);
                    createEventRequestModel.setLocation(location_str);
                    createEventRequestModel.setCaregiverDetails(caregiver_details_str);
                    createEventRequestModel.setTookPlace(took_place);
                    createEventRequestModel.setHasReceipt(kabala);
                    createEventRequestModel.setSubmitted(submitted);
                    createEventRequestModel.setMoneyRefund(refund_received);
                    createEventRequestModel.setReceiptFile(receiptWebLink);
                    createEventRequestModel.setMoneyRefundFile(refundWebLink);
                    createEventRequestModel.setSubmittedFile(submittedWebLink);
                    if (Objects.nonNull(eventType))
                        createEventRequestModel.setColorId(eventType);

                    Call<EventModel> call = eventRequestsExecutor.createEvent(createEventRequestModel);
                    call.enqueue(createEventsCallBack());
                }
            }
        });

    }

    public void callChooseFileFromDevice() {
        if (ContextCompat.checkSelfPermission(AddMeetingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMeetingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        } else {
            img_kabala.hide();

            kabala_v.hide();
            img_submitted.hide();

            sub_v.hide();
            img_refund_received.hide();

            refund_received_v.hide();

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            String[] mimetypes = {"application/pdf", "image/jpeg", "image/jpg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            startActivityForResult(intent, 1001);

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1001 && resultCode == RESULT_OK) {
                if (data != null) {
                    if (btn_kabala_b) {
                        btn_kabala_b = false;

                        uri_kabala = data.getData();
                        String type_of_file = getFileType(uri_kabala);
                        uploadFileToDrive(uri_kabala, new NetworkResponseCallback() {
                            @Override
                            public void onSuccess(okhttp3.Response response) throws IOException {
                                String res = response.body().string();
                                Gson gson = new Gson();
                                DriveFile file = gson.fromJson(res, DriveFile.class);
                                receiptWebLink = file.getWebViewLink();
                                LoaderUtils.hideLoader();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // Handle the error here
                                // 'throwable' contains the exception or error that occurred during the request
                            }
                        });
                        if (type_of_file.equals("pdf")) {
                            int imageInt = R.drawable.pdf;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_kabala.setImageBitmap(bitmap);
                            imageView_delete_kabala.setVisibility(View.VISIBLE);
                        } else if (type_of_file.equals("jpg") || type_of_file.equals("jpeg")) {

                            int imageInt = R.drawable.jpg;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_kabala.setImageBitmap(bitmap);
                            imageView_delete_kabala.setVisibility(View.VISIBLE);
                        } else if (type_of_file.equals("png")) {

                            int imageInt = R.drawable.png;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_kabala.setImageBitmap(bitmap);
                            imageView_delete_kabala.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(AddMeetingActivity.this, "קובץ זה אינו נתמך", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                        kabala = true;
                        kabala_v.setImageResource(R.drawable.cancel);
                        kabala_btn_open = true;


                    } else if (btn_Submitted_b) {
                        btn_Submitted_b = false;

                        uri_Submitted = data.getData();
                        String type_of_file = getFileType(uri_Submitted);
                        uploadFileToDrive(uri_Submitted, new NetworkResponseCallback() {
                            @Override
                            public void onSuccess(okhttp3.Response response) throws IOException {
                                String res = response.body().string();
                                Gson gson = new Gson();
                                DriveFile file = gson.fromJson(res, DriveFile.class);
                                submittedWebLink = file.getWebViewLink();
                                LoaderUtils.hideLoader();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // Handle the error here
                                // 'throwable' contains the exception or error that occurred during the request
                            }
                        });
                        if (type_of_file.equals("pdf")) {

                            int imageInt = R.drawable.pdf;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_submitted.setImageBitmap(bitmap);
                            imageView_delete_sub.setVisibility(View.VISIBLE);

                        } else if (type_of_file.equals("jpg") || type_of_file.equals("jpeg")) {

                            int imageInt = R.drawable.jpg;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_submitted.setImageBitmap(bitmap);
                            imageView_delete_sub.setVisibility(View.VISIBLE);

                        } else if (type_of_file.equals("png")) {

                            int imageInt = R.drawable.png;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_submitted.setImageBitmap(bitmap);
                            imageView_delete_sub.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(AddMeetingActivity.this, "קובץ זה אינו נתמך", Toast.LENGTH_SHORT).show();
                        }
                        submitted_btn_open = true;
                        btn_Submitted.setBackgroundResource(R.drawable.round_button_green);
                        sub_v.setImageResource(R.drawable.cancel);
                        submitted = true;

                    } else if (btn_Refund_received_b) {
                        uri_received = data.getData();
                        String type_of_file = getFileType(uri_received);
                        uploadFileToDrive(uri_received, new NetworkResponseCallback() {
                            @Override
                            public void onSuccess(okhttp3.Response response) throws IOException {
                                String res = response.body().string();
                                Gson gson = new Gson();
                                DriveFile file = gson.fromJson(res, DriveFile.class);
                                refundWebLink = file.getWebViewLink();
                                LoaderUtils.hideLoader();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // Handle the error here
                                // 'throwable' contains the exception or error that occurred during the request
                            }
                        });
                        if (type_of_file.equals("pdf")) {

                            int imageInt = R.drawable.pdf;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_Refund_received.setImageBitmap(bitmap);
                            imageView_delete_refund.setVisibility(View.VISIBLE);


                        } else if (type_of_file.equals("jpg") || type_of_file.equals("jpeg")) {

                            int imageInt = R.drawable.jpg;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_Refund_received.setImageBitmap(bitmap);
                            imageView_delete_refund.setVisibility(View.VISIBLE);

                        } else if (type_of_file.equals("png")) {

                            int imageInt = R.drawable.png;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                            imageFilterView_Refund_received.setImageBitmap(bitmap);
                            imageView_delete_refund.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(AddMeetingActivity.this, "קובץ זה אינו נתמך", Toast.LENGTH_SHORT).show();
                        }
                        refund_received_btn_open = true;
                        btn_Refund_received.setBackgroundResource(R.drawable.round_button_green);
                        refund_received = true;
                        refund_received_v.setImageResource(R.drawable.cancel);
                    }
                }

            } else {
//                String mCurrentPhotoPath;

                if (btn_kabala_b) {
                    btn_kabala_b = false;

                    File imageFile = null;
                    if (requestCode == 1 && resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        try {
                            imageFile = createImageFile();
                            FileOutputStream fos = new FileOutputStream(imageFile);
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    uri_kabala = Uri.fromFile(imageFile);
                    uploadFileToDrive(uri_kabala, new NetworkResponseCallback() {
                        @Override
                        public void onSuccess(okhttp3.Response response) throws IOException {
                            String res = response.body().string();
                            Gson gson = new Gson();
                            DriveFile file = gson.fromJson(res, DriveFile.class);
                            receiptWebLink = file.getWebViewLink();
                            LoaderUtils.hideLoader();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // Handle the error here
                            // 'throwable' contains the exception or error that occurred during the request
                        }
                    });


                    int imageInt = R.drawable.jpg;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                    imageFilterView_kabala.setImageBitmap(bitmap);

                    btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                    imageView_delete_kabala.setVisibility(View.VISIBLE);
                    kabala_v.hide();
                    img_kabala.hide();

                    kabala = true;
                    kabala_v.setImageResource(R.drawable.cancel);


                } else if (btn_Submitted_b) {
                    btn_Submitted_b = false;

                    File imageFile = null;
                    if (requestCode == 1 && resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        try {
                            imageFile = createImageFile();
                            FileOutputStream fos = new FileOutputStream(imageFile);
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    uri_Submitted = Uri.fromFile(imageFile);
                    uploadFileToDrive(uri_received, new NetworkResponseCallback() {
                        @Override
                        public void onSuccess(okhttp3.Response response) throws IOException {
                            String res = response.body().string();
                            Gson gson = new Gson();
                            DriveFile file = gson.fromJson(res, DriveFile.class);
                            refundWebLink = file.getWebViewLink();
                            LoaderUtils.hideLoader();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // Handle the error here
                            // 'throwable' contains the exception or error that occurred during the request
                        }
                    });

                    int imageInt = R.drawable.jpg;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                    imageFilterView_submitted.setImageBitmap(bitmap);

                    btn_Submitted.setBackgroundResource(R.drawable.round_button_green);
                    imageView_delete_sub.setVisibility(View.VISIBLE);
                    sub_v.hide();
                    img_submitted.hide();

                    sub_v.setImageResource(R.drawable.cancel);
                    submitted = true;
                } else if (btn_Refund_received_b) {
                    btn_Refund_received_b = false;

                    File imageFile = null;
                    if (requestCode == 1 && resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        try {
                            imageFile = createImageFile();
                            FileOutputStream fos = new FileOutputStream(imageFile);
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    uri_received = Uri.fromFile(imageFile);
                    uploadFileToDrive(uri_received, new NetworkResponseCallback() {
                        @Override
                        public void onSuccess(okhttp3.Response response) throws IOException {
                            String res = response.body().string();
                            Gson gson = new Gson();
                            DriveFile file = gson.fromJson(res, DriveFile.class);
                            refundWebLink = file.getWebViewLink();
                            LoaderUtils.hideLoader();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // Handle the error here
                            // 'throwable' contains the exception or error that occurred during the request
                        }
                    });

                    int imageInt = R.drawable.jpg;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                    imageFilterView_Refund_received.setImageBitmap(bitmap);

                    btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                    btn_Refund_received.setBackgroundResource(R.drawable.round_button_green);
                    imageView_delete_refund.setVisibility(View.VISIBLE);
                    refund_received_v.hide();
                    img_refund_received.hide();

                    refund_received_v.setImageResource(R.drawable.cancel);
                    refund_received = true;
                }


            }

        } catch (Exception not_pdf_or_img) {
            LoaderUtils.hideLoader();
            Toast.makeText(AddMeetingActivity.this, "יש שגיאה", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = new File(storageDir, imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public String getFileType(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private Callback<EventModel> createEventsCallBack() {
        return new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                if (!Objects.isNull(response.body())) {
                    notificationScheduler.addNotification(response.body());
                    MyProperties.getInstance().getEventList().addEvent(response.body());
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(MyApplication.getInstance(), "Create event failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                Toast.makeText(MyApplication.getInstance(), "Create event failed", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void resetForm() {
        meeting_name_text = null;
        date_text = null;
        time_text = null;
        location_text = null;
        caregiver_details_text = null;
    }

    private void uploadFileToDrive(Uri uri, NetworkResponseCallback callback) {
        LoaderUtils.showLoader(this);
        OkHttpClient client = driveRequestUtils.createClient();
        Request request = driveRequestUtils.createRequest(uri);
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@androidx.annotation.NonNull okhttp3.Call call, @androidx.annotation.NonNull IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(@androidx.annotation.NonNull okhttp3.Call call, @androidx.annotation.NonNull okhttp3.Response response) throws IOException {
                callback.onSuccess(response);
            }
        });
    }

    public boolean isTimeValid(String time) {
        // בדיקה שאורך השעה והדקות תקין
        if (time.length() != 5) {
            return false;
        }

        // בדיקה שהתווים בתוך השעה והדקות הם תווים מספריים
        for (int i = 0; i < 5; i++) {
            char c = time.charAt(i);
            if (i == 2) {
                // בדיקה שהתו במקום ה-2 הוא נקודה פסיק
                if (c != ':') {
                    return false;
                }
            } else if (!Character.isDigit(c)) {
                // בדיקה שכל התווים בשאר המחרוזת הם מספרים
                return false;
            }
        }

        // כאשר אנחנו מגיעים לכאן, המחרוזת היא בפורמט "HH:mm" והתווים הם מספריים תקינים
        // עכשיו נבדוק את הערכים של השעה והדקות בנפרד כדי לוודא שהם בטווח התקין
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59) {
            // השעה והדקות נמצאים בטווח התקין
            return true;
        } else {
            // השעה או הדקות מחוץ לטווח התקין
            return false;
        }
    }

    public boolean isDateValid(String date) {
        // בדיקה שאורך התאריך תקין
        if (date.length() != 10) {
            return false;
        }

        // בדיקה שהתווים בתוך התאריך הם תווים מספריים ונקודות פסיק
        for (int i = 0; i < 10; i++) {
            char c = date.charAt(i);
            if (i == 2 || i == 5) {
                // בדיקה שהתווים במקום ה-2 וה-5 הם נקודות פסיק
                if (c != '/') {
                    return false;
                }
            } else if (!Character.isDigit(c)) {
                // בדיקה שכל התווים בשאר המחרוזת הם מספרים
                return false;
            }
        }

        // כאשר אנחנו מגיעים לכאן, המחרוזת היא בפורמט "dd/MM/yyyy" והתווים הם מספריים תקינים
        // עכשיו נבדוק את הערכים של היום, החודש והשנה כדי לוודא שהם בטווח התקין
        String[] parts = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1900 && year <= 9999) {
            // התאריך נמצא בטווח התקין
            return true;
        } else {
            // התאריך מחוץ לטווח התקין
            return false;
        }
    }

    public void setCharacterLimit(TextView editText, int maxCharacters) {
//        InputFilter[] filters = new InputFilter[1];
//        filters[0] = new InputFilter.LengthFilter(maxCharacters);
//        editText.setFilters(filters);
    }

    public void onPopupWindowOpened(Spinner spinner) {
        //spinner_t_m.setBackground(getResources().getDrawable(R.drawable.custom_shapes_green));
    }


    public void onPopupWindowClosed(Spinner spinner) {
        // spinner_t_m.setBackground(getResources().getDrawable(R.drawable.custom_shapes_green));
    }
}