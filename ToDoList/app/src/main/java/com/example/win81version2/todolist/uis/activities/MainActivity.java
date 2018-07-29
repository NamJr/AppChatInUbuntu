package com.example.win81version2.todolist.uis.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.win81version2.todolist.R;
import com.example.win81version2.todolist.adapter.TaskAdapter;
import com.example.win81version2.todolist.model.TaskInfo;
import com.example.win81version2.todolist.model.Request;
import com.example.win81version2.todolist.model.TaskPOJO;
import com.example.win81version2.todolist.rest.APIGets;
import com.example.win81version2.todolist.rest.RestClientGet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView lvTask;
    private ArrayList<TaskInfo> tasks;
    private List<TaskPOJO> taskPOJOs;
    private List<String> orders;
    private List<Request> requests;
    private TaskAdapter adapter;
    private String orderGet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTask = findViewById(R.id.lvTasks);
        tasks = new ArrayList<>();
        orders = new ArrayList<>();
        requests = new ArrayList<>();
        taskPOJOs = new ArrayList<>();
//        getOrders();
        getTask();
        adapter = new TaskAdapter(MainActivity.this, R.layout.item_task, tasks);
        lvTask.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                TaskInfo taskInfo = tasks.get(i);
                intent.putExtra("Task", (Serializable) taskInfo);
                startActivity(intent);
                finish();
            }
        });
        lvTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskInfo taskInfo = tasks.get(i);
                delete(taskInfo);
                return false;
            }
        });

    }

    private void delete(final TaskInfo taskInfo) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage("Are you sure?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://150.95.111.30:4444/api/delete/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIGets apiService = retrofit.create(APIGets.class);
                Call<String> call = apiService.delete(taskInfo.getId().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        tasks.remove(taskInfo);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Delete Succeed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void getTask() {
        Call<List<TaskPOJO>> call = RestClientGet.getRestClient().getContacts();
        call.enqueue(new Callback<List<TaskPOJO>>() {
            @Override
            public void onResponse(Call<List<TaskPOJO>> call, Response<List<TaskPOJO>> response) {

                taskPOJOs = response.body();
                for (int i = 0; i < taskPOJOs.size(); i++) {
                    TaskPOJO taskPOJO = taskPOJOs.get(i);
                    TaskInfo taskInfo = new TaskInfo();
                    taskInfo.setId(taskPOJO.getId() + "");
                    taskInfo.setV(taskPOJO.getV());
                    taskInfo.setName(taskPOJO.getName());
                    taskInfo.setDone(taskPOJO.getDone());
                    tasks.add(taskInfo);
//                    tasks.add(new TaskInfo(taskPOJO.getName(),taskPOJO.getDone(),taskPOJO.getId(),taskPOJO.getV()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TaskPOJO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Khong the tai du lieu Task", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void getOrders() {
//
//        Call<POJO> call = RestClient.getRestClient().getContacts();
//        call.enqueue(new Callback<POJO>() {
//            @Override
//            public void onResponse(Call<POJO> call, Response<POJO> response) {
//
//                // lay ve 1 list orders
//                orders = response.body().getOrder();
//                requests = response.body().getRequests();
//                orderGet = orders.get(0).toString();
////                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<POJO> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Khong the tai du lieu", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
