package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyViewModel model;

    private ArrayList<Integer> arrayList;
    private ArrayAdapter<Integer> arrayAdapter;
    private ActivityMainBinding binding;

//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        if (requestCode == RESULT_OK) {
//// came back from SecondActivity
//            String data = intent.getStringExtra("newNumber");
//            String position = intent.getStringExtra("position");
//            model.getList().set(Integer.parseInt(position), Integer.parseInt(data));
//            arrayList.set(Integer.parseInt(position), Integer.parseInt(data));
//            arrayAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<Integer>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        binding.lvCount.setAdapter(arrayAdapter);

        model = new ViewModelProvider(this).get(MyViewModel.class);
        model.getNumbers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCount.setText("" + integer);
                if (model.getList().size() > arrayList.size()) {
                    for (int i: model.getList()) {
                        arrayList.add(i);
                    }
                } else {
                    arrayList.add(integer);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.increaseNumber();
            }
        });

        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.decreseNumber();
            }
        });

        binding.lvCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                model.getList().remove(position);
                arrayAdapter.notifyDataSetChanged();

                return false;
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult activityResult) {
                                int requestCode = activityResult.getResultCode();
                                Intent intent = activityResult.getData();

                                if (requestCode == RESULT_OK) {
                                    String data = intent.getStringExtra("newNumber");
                                    String position = intent.getStringExtra("position");
                                    model.getList().set(Integer.parseInt(position), Integer.parseInt(data));
                                    arrayList.set(Integer.parseInt(position), Integer.parseInt(data));
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                );

        binding.lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("number", arrayList.get(position).toString());
                intent.putExtra("position", "" + arrayList.indexOf(position));

                //startActivityForResult(intent, 0);
                activityResultLauncher.launch(intent);
            }
        });
    }
}