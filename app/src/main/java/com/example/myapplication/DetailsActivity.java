package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private TextView tvDetail;

    private FloatingActionButton btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);

        tvDetail = findViewById(R.id.tv_detail);

        btnRandom = findViewById(R.id.random_btn);

        Intent receivedIntent = getIntent();

        if (receivedIntent != null) {
            String data = receivedIntent.getStringExtra("number");
            tvDetail.setText(data);
        }

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNumber = (int) (Math.random() * 100);
                tvDetail.setText("" + randomNumber);

                    String position = (receivedIntent.getStringExtra("position"));
                    Log.i("TAG", "onClickPosition: " + position);

                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                    intent.putExtra("newNumber", "" + randomNumber);
                    intent.putExtra("position", position);

                    MainActivity mainActivity = new MainActivity();
                    mainActivity.updateData();
//                    startActivity(intent);
            }
        });
    }
}