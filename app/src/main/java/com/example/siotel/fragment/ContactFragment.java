package com.example.siotel.fragment;

import com.example.siotel.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    private EditText nameInput, emailInput, messageInput;
    private Button sendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        nameInput = view.findViewById(R.id.name);
        emailInput = view.findViewById(R.id.email);
        messageInput = view.findViewById(R.id.message);
        sendButton = view.findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            sendEmail();
        });


        return view;
    }

    private void sendEmail() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String message = messageInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields before sending.", Toast.LENGTH_SHORT).show();
            return;
        }

        String subject = "Contact Form Submission";
        String recipientEmail = "marketing@sstpl.net.in"; // New email recipient

        String mailTo = "mailto:" + recipientEmail +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode("Name: " + name + "\nEmail: " + email + "\nMessage: " + message);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailTo));

        try {
            startActivity(emailIntent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Failed to send email. Please try again later.", Toast.LENGTH_LONG).show();
        }
    }
}

