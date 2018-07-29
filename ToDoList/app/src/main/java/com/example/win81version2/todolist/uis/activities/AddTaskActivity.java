package com.example.win81version2.todolist.uis.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.win81version2.todolist.R;
import com.example.win81version2.todolist.model.TaskPOJO;
import com.example.win81version2.todolist.rest.APIPosts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtTaskName;
    private ToggleButton toggleButton;
    private Button btnOK;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        AnhXa();
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTaskName.getText().toString().equals("")) {
                    Toast.makeText(AddTaskActivity.this, "Enter Task Name", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    postNewTask();
                    Toast.makeText(AddTaskActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void AnhXa() {
        edtTaskName = findViewById(R.id.edtAddTaskName);
        toggleButton = findViewById(R.id.btnIsDone);
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

    }

    private void postNewTask() {
        String name = edtTaskName.getText().toString();
        Boolean check = toggleButton.isChecked();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://150.95.111.30:4444/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPosts apiService = retrofit.create(APIPosts.class);

        Call<TaskPOJO> call = apiService.insertNewTask(name, check);
        call.enqueue(new Callback<TaskPOJO>() {
            @Override
            public void onResponse(Call<TaskPOJO> call, Response<TaskPOJO> response) {
                Toast.makeText(AddTaskActivity.this, "Succeeded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TaskPOJO> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(AddTaskActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
