package com.example.win81version2.todolist.uis.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.win81version2.todolist.R;
import com.example.win81version2.todolist.model.TaskInfo;
import com.example.win81version2.todolist.model.TaskPOJO;
import com.example.win81version2.todolist.rest.APIPosts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvIsDone;
    private Button btnEdit;
    private ToggleButton toggleButton;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        tvName = findViewById(R.id.tvTaskDetailName);
        tvIsDone = findViewById(R.id.tvTaskDetailIsDone);
        btnEdit = findViewById(R.id.btnEdit);
        toggleButton = findViewById(R.id.btnIsDoneDetail);
        btnBack = findViewById(R.id.btnBack);


        final Intent intent = getIntent();
        final TaskInfo taskInfo = (TaskInfo) intent.getSerializableExtra("Task");
        tvName.setText(taskInfo.getName());
        if (taskInfo.isDone())
            tvIsDone.setText("Done");
        else
            tvIsDone.setText("Not finished yet");
        toggleButton.setChecked(taskInfo.isDone());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask(taskInfo.getId());
            }
        });
        toggleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggleButton.isChecked())
                            tvIsDone.setText("Done");
                        else
                            tvIsDone.setText("Not finished yet");
                        postUpdateTask( taskInfo.getId(),tvName.getText().toString(), toggleButton.isChecked());
                    }
                }
        );
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TaskDetailActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    public void updateTask(final String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edtChange = dialogView.findViewById(R.id.edtChangeTask);

        dialogBuilder.setTitle("Update");
        dialogBuilder.setMessage("Enter name below");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                if (edtChange.getText().toString().equals("")) {
                    Toast.makeText(TaskDetailActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Boolean check = false;
                    tvName.setText(edtChange.getText().toString());
                    if (tvIsDone.getText().toString().equals("Done"))
                        check = true;
                    else
                        check = false;
                    postUpdateTask(id, edtChange.getText().toString(), check);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void postUpdateTask(String id, final String name, Boolean check) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://150.95.111.30:4444/api/update/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPosts apiService = retrofit.create(APIPosts.class);

        Call<TaskPOJO> call = apiService.updateTaskName(id, name, check);
        call.enqueue(new Callback<TaskPOJO>() {
            @Override
            public void onResponse(Call<TaskPOJO> call, Response<TaskPOJO> response) {
                Toast.makeText(TaskDetailActivity.this, "Update Succeeded", Toast.LENGTH_SHORT).show();
                tvName.setText(name);
            }

            @Override
            public void onFailure(Call<TaskPOJO> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(TaskDetailActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
