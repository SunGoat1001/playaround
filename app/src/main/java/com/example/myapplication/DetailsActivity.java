package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.databinding.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent receivedIntent = getIntent();

        if (receivedIntent != null) {
            String data = receivedIntent.getStringExtra("number");
            binding.tvDetail.setText(data);
        }

        binding.randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNumber = (int) (Math.random() * 100);
                binding.tvDetail.setText("" + randomNumber);

                    String position = (receivedIntent.getStringExtra("position"));

                    Intent intent = new Intent();
                    intent.putExtra("newNumber", "" + randomNumber);
                    intent.putExtra("position", position);
                    setResult(RESULT_OK, intent);
            }
        });
    }
}