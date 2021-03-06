package com.example.myapplication.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Contact extends AppCompatActivity {
    private static final String DATE_FORMAT = "yyyy-MM-dd, HH:mm";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_layout);

        final EditText userName = findViewById(R.id.contactus_name);
        final EditText message = findViewById(R.id.contactus_message);
        Button sendBtn = findViewById(R.id.contactus_sendButton);

        final String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        final String currentTime = df.format(new Date());

        sendBtn.setOnClickListener(v -> {
            final String msg = message.getText().toString();
            final String user = userName.getText().toString();

            if (user.isEmpty() || msg.isEmpty()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillfields), Toast.LENGTH_SHORT).show();
            }
            else{
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference pushContact = db.getReference("message");
                pushContact.push().setValue(new Post(currentTime, userEmail, user, msg));
                finish();
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.msgsent) , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
