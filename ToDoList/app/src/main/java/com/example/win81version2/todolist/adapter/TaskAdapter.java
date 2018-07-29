package com.example.win81version2.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.win81version2.todolist.R;
import com.example.win81version2.todolist.model.TaskInfo;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskInfo> {
    private ArrayList<TaskInfo> tasks;
    private Context context;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, int resource, ArrayList<TaskInfo> objects) {
        super(context, resource, objects);
        this.tasks = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public TaskInfo getItem(int position) {
        return tasks.get(position);
    }

    public class ViewHolder {
        public TextView tvTaskName;
        public TextView tvIsDone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_task, parent, false);
            holder.tvTaskName = convertView.findViewById(R.id.tvTaskName);
            holder.tvIsDone = convertView.findViewById(R.id.tvIsDone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final TaskInfo taskInfo = tasks.get(position);
        if (taskInfo != null) {
            holder.tvTaskName.setText(taskInfo.getName());
            if (taskInfo.isDone())
                holder.tvIsDone.setText("Done");
            else
                holder.tvIsDone.setText("Not finished yet");
        }

        return convertView;
    }


}
