package com.example.finalproject_.activities;


import static com.example.finalproject_.MyProperties.btn_Refund_received_b;
import static com.example.finalproject_.MyProperties.btn_Submitted_b;
import static com.example.finalproject_.MyProperties.btn_kabala_b;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.adapters.CustomAdapter;
import com.example.finalproject_.interfaces.EventAPIInterface;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.UpdateEventRequestModel;
import com.example.finalproject_.network.EventAPIClient;
import com.example.finalproject_.utils.LoaderUtils;
import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.RealPathUtil;
import com.example.finalproject_.utils.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeMeetingActivity extends AppCompatActivity {

    private EventAPIInterface eventAPIInterface;
    private String mCurrentPhotoPath;//לפונקציה ששומרת Path
    ImageView btn_x_name;//כפתור x
    ImageView btn_x_time;//כפתור x
    ImageView btn_x_date;//כפתור x
    ImageView btn_x_loc;//כפתור x
    ImageView btn_x_4;//כפתור x

    ImageView imageView_delete_kabala;//כפתור פח
    ImageView imageView_delete_sub;//כפתור פח
    ImageView imageView_delete_refund;//כפתור פח

    ImageView imageFilterView_kabala;//תמונה של סוג קובץ שעולה
    ImageView imageFilterView_submitted;//תמונה של סוג קובץ שעולה
    ImageView imageFilterView_Refund_received;//תמונה של סוג קובץ שעולה

    FloatingActionButton img_kabala, img_submitted, img_refund_received, camera_kabala, camera_submitted, camera_Refund_received;//הכפתורים של כל סוגי בקשת קבצים
    FloatingActionButton kabala_v, sub_v, refund_received_v;//הכפתורים של כל סוגי בקשת קבצים


    Button btn_receipt;//כפתור קבלה
    Button btn_Submitted;//כפתור הוגש לקופ"ח
    Button btn_Refund_received;//כפתור התקבל החזר


    Button updateButton;
    Button btn_date;//כפתור לוח שנה
    Button btn_time;//כפתור שעה
    DatePickerDialog.OnDateSetListener setListener;
    int t_hour, t_minute;//משתנים שקשורים לשעה


    TextView meeting_name_text;
    TextView date_text;//תאריך ושעה
    TextView time_text;//שעה
    TextView location_text;//מיקום
    TextView caregiver_details_text;//פרטי המטפל
    Button btn_tookplace;//כפתור התקיים


    String kabala_f;
    String Submitted_f;
    String Refund_received_f;


    boolean took_place = false;//לפגישה
    boolean kabala = false;//לפגישה
    boolean submitted = false;//לפגישה
    boolean refund_received = false;//לפגישה
    ////////////////////////////////////////
    boolean kabala_btn_open = true;//אם הכפתור דלוק
    boolean submitted_btn_open = true;//אם הכפתור דלוק
    boolean refund_received_btn_open = true;//אם הכפתור דלוק


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_meeting);
        eventAPIInterface = EventAPIClient.getClient().create(EventAPIInterface.class);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#E0DAED"));
//////////////////////////////////////////////////////////////TIME

        time_text = (TextView) findViewById(R.id.textTime_c);
        btn_time = (Button) findViewById(R.id.time_21);

        btn_tookplace = findViewById(R.id.btn_took_place);


        imageView_delete_kabala = (ImageView) findViewById(R.id.imageView_delete_kabala_c);
        imageView_delete_sub = (ImageView) findViewById(R.id.imageView_delete_submitted_c);
        imageView_delete_refund = (ImageView) findViewById(R.id.imageView_delete_refund_c);

        imageFilterView_kabala = (ImageView) findViewById(R.id.imageFilterView_kabala_c);
        imageFilterView_submitted = (ImageView) findViewById(R.id.imageFilterView_submitted_c);
        imageFilterView_Refund_received = (ImageView) findViewById(R.id.imageFilterView_Refund_received_c);

        kabala_v = (FloatingActionButton) findViewById(R.id.float_kabala_V_c);
        img_kabala = (FloatingActionButton) findViewById(R.id.float_kabala_img_c);
        camera_kabala = (FloatingActionButton) findViewById(R.id.float_kabala_camera_c);
        img_submitted = (FloatingActionButton) findViewById(R.id.float_submitted_img_c);
        sub_v = (FloatingActionButton) findViewById(R.id.float_sub_V_c);
        camera_submitted = (FloatingActionButton) findViewById(R.id.float_submitted_camera_c);
        refund_received_v = (FloatingActionButton) findViewById(R.id.float_refund_received_V_c);
        img_refund_received = (FloatingActionButton) findViewById(R.id.float_Refund_received_img_c);
        camera_Refund_received = (FloatingActionButton) findViewById(R.id.float_Refund_received_camera_c);

        btn_tookplace = findViewById(R.id.btn_took_place);
        btn_receipt = findViewById(R.id.btn_add_files_kabala);
        btn_Submitted = findViewById(R.id.btn_Submitted_to_KOFH);
        btn_Refund_received = findViewById(R.id.btn_Refund_received);

        btn_x_date = (ImageView) findViewById(R.id.imageView_X_date_c3);
        btn_x_time = (ImageView) findViewById(R.id.imageView_X_time_c2);
        btn_x_loc = (ImageView) findViewById(R.id.imageView_X_loc_c2);
        btn_x_4 = (ImageView) findViewById(R.id.imageView_X_4_c2);
        btn_x_name = (ImageView) findViewById(R.id.imageView_X_name_c2);


        meeting_name_text = findViewById(R.id.text_MeetingName_c);
        date_text = findViewById(R.id.textDate_c);
        time_text = findViewById(R.id.textTime_c);
        location_text = findViewById(R.id.textLoc_c);
        caregiver_details_text = findViewById(R.id.textCaregiverDetails_c);


        kabala_v.hide();
        img_kabala.hide();
        camera_kabala.hide();
        sub_v.hide();
        img_submitted.hide();
        camera_submitted.hide();
        refund_received_v.hide();
        img_refund_received.hide();
        camera_Refund_received.hide();

////////////////////////////////////////////////////////////////////
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
/////////////////////////////////////////////////////////////////////////////

        btn_date = (Button) findViewById(R.id.date_2);
        date_text = (TextView) findViewById(R.id.textDate_c);
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

        imageView_delete_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Submitted_b = false;
                submitted = false;
                Submitted_f = null;
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
                Refund_received_f = null;
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
                camera_submitted.hide();
                sub_v.hide();
                submitted_btn_open = true;
                img_refund_received.hide();
                camera_Refund_received.hide();
                refund_received_v.hide();
                refund_received_btn_open = true;

                if (kabala_btn_open == false) {
                    kabala_btn_open = true;
                    img_kabala.hide();
                    camera_kabala.hide();
                    kabala_v.hide();
                    if (kabala == true) {
                        kabala_v.setImageResource(R.drawable.cancel);


                    } else {
                        kabala_v.setImageResource(R.drawable.baseline_check_24);
                    }
                } else {
                    kabala_btn_open = false;
                    img_kabala.show();
                    camera_kabala.show();
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
                camera_kabala.hide();
                kabala_v.hide();
                kabala_btn_open = true;
                img_refund_received.hide();
                refund_received_v.hide();
                camera_Refund_received.hide();
                refund_received_v.hide();
                refund_received_btn_open = true;

                if (submitted_btn_open == false) {
                    submitted_btn_open = true;
                    img_submitted.hide();
                    camera_submitted.hide();
                    sub_v.hide();

                    if (submitted == true) {
                        sub_v.setImageResource(R.drawable.cancel);


                    } else {
                        sub_v.setImageResource(R.drawable.baseline_check_24);

                    }

                } else {

                    submitted_btn_open = false;

                    img_submitted.show();
                    camera_submitted.show();
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
                camera_submitted.hide();
                sub_v.hide();
                submitted_btn_open = true;
                img_kabala.hide();
                camera_kabala.hide();
                kabala_v.hide();
                kabala_btn_open = true;

                if (refund_received_btn_open == false) {
                    refund_received_btn_open = true;

                    img_refund_received.hide();
                    camera_Refund_received.hide();
                    refund_received_v.hide();

                    if (refund_received == true) {
                        refund_received_v.setImageResource(R.drawable.cancel);

                    } else {
                        refund_received_v.setImageResource(R.drawable.baseline_check_24);

                    }

                } else {
                    refund_received_btn_open = false;

                    img_refund_received.show();
                    camera_Refund_received.show();
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

        try {
            camera_kabala.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);

                    if (ContextCompat.checkSelfPermission(ChangeMeetingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        MyProperties.getInstance().btn_kabala_b = true;
                        startActivityForResult(open_camera, 1);
                    } else {

                        ActivityCompat.requestPermissions(ChangeMeetingActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
                    }
                }
            });

            camera_submitted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(ChangeMeetingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        // אם יש גישה למצלמה, נפעיל את המצלמה
                        Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        MyProperties.getInstance().btn_Submitted_b = true;
                        startActivityForResult(open_camera, 1);
                    } else {
                        // אם אין גישה למצלמה, נבקש גישה מהמשתמש
                        ActivityCompat.requestPermissions(ChangeMeetingActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
                    }
                }
            });
            camera_Refund_received.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(ChangeMeetingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        // אם יש גישה למצלמה, נפעיל את המצלמה
                        Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        MyProperties.getInstance().btn_Refund_received_b = true;
                        startActivityForResult(open_camera, 1);
                    } else {
                        // אם אין גישה למצלמה, נבקש גישה מהמשתמש
                        ActivityCompat.requestPermissions(ChangeMeetingActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
                    }
                }
            });

        } catch (Exception exception_camera) {
            Toast.makeText(ChangeMeetingActivity.this, "המצלמה לא תקינה", Toast.LENGTH_SHORT).show();

        }


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
                    camera_kabala.hide();
                    kabala_btn_open = true;
                } else {
                    kabala = false;
                    btn_kabala_b = false;
                    imageFilterView_kabala.setImageBitmap(null);
                    imageView_delete_kabala.setVisibility(View.INVISIBLE);
                    btn_receipt.setBackgroundResource(R.drawable.round_button);
                    kabala_v.hide();
                    img_kabala.hide();
                    camera_kabala.hide();
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
                    camera_submitted.hide();
                    submitted_btn_open = true;
                } else {
                    submitted = false;
                    btn_Submitted_b = false;
                    imageFilterView_submitted.setImageBitmap(null);
                    imageView_delete_sub.setVisibility(View.INVISIBLE);
                    btn_Submitted.setBackgroundResource(R.drawable.round_button);
                    sub_v.hide();
                    img_submitted.hide();
                    camera_submitted.hide();
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
                    camera_Refund_received.hide();
                    refund_received_btn_open = true;
                } else {
                    refund_received = false;
                    btn_Refund_received.setBackgroundResource(R.drawable.round_button);
                    refund_received_v.hide();
                    img_refund_received.hide();
                    camera_Refund_received.hide();
                    btn_Refund_received_b = false;

                    imageFilterView_Refund_received.setImageBitmap(null);
                    imageView_delete_refund.setVisibility(View.INVISIBLE);
                    refund_received_btn_open = true;

                }
            }
        });


///////////////////////////////////////////////////////////////////////////////////////


        int ID = getIntent().getIntExtra("id_int", 0);

        EventModel currentEvent = (EventModel) getIntent().getSerializableExtra("event");

        meeting_name_text.setText(currentEvent.getDescription());
        date_text.setText(currentEvent.getStartTime());
        time_text.setText(currentEvent.getStartTime());
        location_text.setText(currentEvent.getLocation());
        caregiver_details_text.setText(currentEvent.getCaregiverDetails());

        took_place = currentEvent.isTookPlace();
        kabala = currentEvent.isHasReceipt();
        submitted = currentEvent.isSubmitted();
        refund_received = currentEvent.isMoneyRefund();
        setButtonBackground(btn_tookplace, took_place);
        setButtonBackground(btn_tookplace, took_place);
        setButtonBackground(btn_receipt, kabala);
        setButtonBackground(btn_Submitted, submitted);
        setButtonBackground(btn_Refund_received, refund_received);

        updateButton = findViewById(R.id.btn_update_meeting);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meeting_name_str = meeting_name_text.getText().toString();
                String date_str = date_text.getText().toString();
                String time_str = time_text.getText().toString();
                String location_str = location_text.getText().toString();
                String caregiver_details_str = caregiver_details_text.getText().toString();
                resetForm();

                UpdateEventRequestModel updateEventRequestModel = new UpdateEventRequestModel();
                String calendarId = SharedPreferencesUtils.getString(getApplicationContext(), "calendar_id", "");
                String token = SharedPreferencesUtils.getString(getApplicationContext(), "token", "");
                updateEventRequestModel.setToken(token);
                updateEventRequestModel.setCalendarId(calendarId);
                updateEventRequestModel.setId(currentEvent.getId());

                updateEventRequestModel.setDescription(meeting_name_str);
                updateEventRequestModel.setStartTime("2023-08-03T09:00:00+03:00");
                updateEventRequestModel.setEndTime("2023-08-03T09:00:30+03:00");
                updateEventRequestModel.setLocation(location_str);
                updateEventRequestModel.setCaregiverDetails(caregiver_details_str);
                updateEventRequestModel.setTookPlace(took_place);
                updateEventRequestModel.setHasReceipt(kabala);
                updateEventRequestModel.setSubmitted(submitted);
                updateEventRequestModel.setMoneyRefund(refund_received);

//                MyProperties.getInstance().getEventList().updateEvent(currentEvent);
                Call<EventModel> call = eventAPIInterface.updateEvent(updateEventRequestModel);
                call.enqueue(updateEventsCallBack());
            }
        });

    }

    public void callChooseFileFromDevice() {

        if (ContextCompat.checkSelfPermission(ChangeMeetingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChangeMeetingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        } else {
            img_kabala.hide();
            camera_kabala.hide();
            kabala_v.hide();
            img_submitted.hide();
            camera_submitted.hide();
            sub_v.hide();
            img_refund_received.hide();
            camera_Refund_received.hide();
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
                //the result data contains a URI for the document od directory that  the user selected.
                if (data != null) {
                    Log.d("Main Activity", "on ActivityResult:" + data.getData());
                    System.out.println(data);
//            System.out.println(data.getData());
                    if (btn_kabala_b == true) {
                        btn_kabala_b = false;
                        String receipt = String.valueOf(data.getData());//קבלה
                        String type_of_file = getFileType(receipt);


                        Uri uri = data.getData();
                        Context context = ChangeMeetingActivity.this;
                        receipt = RealPathUtil.getRealPath(context, uri);
                        System.out.println(receipt);


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
                            Toast.makeText(ChangeMeetingActivity.this, "קובץ זה אינו נתמך", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                        kabala = true;
                        kabala_v.setImageResource(R.drawable.cancel);
                        kabala_btn_open = true;


                    } else if (btn_Submitted_b == true) {
                        btn_Submitted_b = false;
                        String sub = String.valueOf(data.getData());//// הוגש לקופת חולים
                        String type_of_file = getFileType(sub);


                        Uri uri = data.getData();
                        Context context = ChangeMeetingActivity.this;
                        sub = RealPathUtil.getRealPath(context, uri);
                        System.out.println(sub);

                        System.out.println(sub);
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
                            Toast.makeText(ChangeMeetingActivity.this, "קובץ זה אינו נתמך", Toast.LENGTH_SHORT).show();
                        }
                        submitted_btn_open = true;
                        btn_Submitted.setBackgroundResource(R.drawable.round_button_green);
                        sub_v.setImageResource(R.drawable.cancel);
                        submitted = true;

                    } else if (btn_Refund_received_b == true) {
                        btn_Refund_received_b = false;
                        String returned = String.valueOf(data.getData());//הוחזר הכסף
                        String type_of_file = getFileType(returned);

                        Uri uri = data.getData();
                        Context context = ChangeMeetingActivity.this;
                        returned = RealPathUtil.getRealPath(context, uri);
                        System.out.println(returned);

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
                            Toast.makeText(ChangeMeetingActivity.this, "קובץ זה אינו נתמך", Toast.LENGTH_SHORT).show();
                        }
                        refund_received_btn_open = true;
                        btn_Refund_received.setBackgroundResource(R.drawable.round_button_green);
                        refund_received = true;
                        refund_received_v.setImageResource(R.drawable.cancel);
                    }
                }

            } else//if its image from camera
            {
                String mCurrentPhotoPath;
                File imageFile = null;
                if (btn_kabala_b == true) {
                    btn_kabala_b = false;
                    if (requestCode == 1 && resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        System.out.println(data);

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

                    Context context = getApplicationContext();
                    mCurrentPhotoPath = RealPathUtil.getRealPath(context, Uri.fromFile(imageFile));
                    File imgFile = new File(mCurrentPhotoPath);

                    System.out.println(mCurrentPhotoPath);

                    if (imageFile.exists() && !imageFile.isDirectory()) {
                        Toast.makeText(ChangeMeetingActivity.this, "no path", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangeMeetingActivity.this, "siiiiiiiiiii path", Toast.LENGTH_SHORT).show();
                    }
                    int imageInt = R.drawable.jpg;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                    imageFilterView_kabala.setImageBitmap(bitmap);

                    btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                    imageView_delete_kabala.setVisibility(View.VISIBLE);
                    kabala_v.hide();
                    img_kabala.hide();
                    camera_kabala.hide();
                    kabala = true;
                    kabala_v.setImageResource(R.drawable.cancel);


                } else if (btn_Submitted_b == true) {
                    btn_Submitted_b = false;
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
                    mCurrentPhotoPath = imageFile.getAbsolutePath();
                    System.out.println(mCurrentPhotoPath);
                    int imageInt = R.drawable.jpg;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                    imageFilterView_submitted.setImageBitmap(bitmap);

                    btn_Submitted.setBackgroundResource(R.drawable.round_button_green);
                    imageView_delete_sub.setVisibility(View.VISIBLE);
                    sub_v.hide();
                    img_submitted.hide();
                    camera_submitted.hide();
                    sub_v.setImageResource(R.drawable.cancel);
                    submitted = true;
                } else if (btn_Refund_received_b == true) {
                    btn_Refund_received_b = false;
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
                    mCurrentPhotoPath = imageFile.getAbsolutePath();
                    System.out.println(mCurrentPhotoPath);
                    int imageInt = R.drawable.jpg;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageInt);
                    imageFilterView_Refund_received.setImageBitmap(bitmap);

                    btn_receipt.setBackgroundResource(R.drawable.round_button_green);
                    btn_Refund_received.setBackgroundResource(R.drawable.round_button_green);
                    imageView_delete_refund.setVisibility(View.VISIBLE);
                    refund_received_v.hide();
                    img_refund_received.hide();
                    camera_Refund_received.hide();
                    refund_received_v.setImageResource(R.drawable.cancel);
                    refund_received = true;
                }


            }

        } catch (Exception not_pdf_or_img) {
            //Toast.makeText(AddMeetingActivity.this, "יש שגיאה", Toast.LENGTH_SHORT).show();
        }


    }

    public static String getFileExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            extension = fileName.substring(i + 1).toLowerCase();
        }
        return extension;
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

    public static String getFileType(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String fileType = Files.probeContentType(path);

        if (fileType == null) {
            return "unknown file type";
        }
        String extension = fileType.substring(fileType.lastIndexOf('/') + 1);

        return extension;
    }

    private void resetForm() {
        meeting_name_text = null;
        date_text = null;
        time_text = null;
        location_text = null;
        caregiver_details_text = null;
    }

    private Callback<EventModel> updateEventsCallBack() {
        return new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                if (!Objects.isNull(response.body())) {
                    Intent intent = new Intent(ChangeMeetingActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MyApplication.getInstance(), "Update event failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                Toast.makeText(MyApplication.getInstance(), "Update event failed", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setButtonBackground(Button button, boolean condition) {
        if (condition) {
            button.setBackgroundResource(R.drawable.round_button_green);
        }
    }
}
