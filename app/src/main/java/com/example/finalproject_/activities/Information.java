package com.example.finalproject_.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_.R;


public class Information extends AppCompatActivity {

    TextView mail_rainbow, itamar, amitai, levi, big;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        getSupportActionBar().hide();

        mail_rainbow = findViewById(R.id.mail_rainbow);
        itamar = findViewById(R.id.textView_ita);
        amitai = findViewById(R.id.textView_a);
        levi = findViewById(R.id.textView_l);
        big = findViewById(R.id.textView_b);

        mail_rainbow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = mail_rainbow.getText().toString();

                // Create a new Intent to send an email.
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                intent.putExtra(Intent.EXTRA_SUBJECT, "This is the subject line.");
                intent.putExtra(Intent.EXTRA_TEXT, "This is the body of the email.");

                // Start the email composer activity.
                startActivity(Intent.createChooser(intent, "Choose an email client"));
            }
        });


        itamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the website in the browser.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/itamarshekter/"));
                startActivity(intent);
            }
        });
        amitai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the website in the browser.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/amitai-weiss/"));
                startActivity(intent);
            }
        });
        levi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the website in the browser.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/levi-vorst-16076620a/"));
                startActivity(intent);
            }
        });
        big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the website in the browser.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/dov-perelman-b539481b3/"));
                startActivity(intent);
            }
        });
    }
}