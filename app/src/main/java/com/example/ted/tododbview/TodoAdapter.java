package com.example.ted.tododbview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by ted on 14/04/2016.
 */
public class TodoAdapter extends CursorAdapter {
  public TodoAdapter(Context context, Cursor c, boolean autoRequery) {
    super(context, c, autoRequery);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.todo_items, parent, false);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {

    TextView tvstatus = (TextView) view.findViewById(R.id.txtStatus);
    TextView tvtask = (TextView) view.findViewById(R.id.txtTask);
    TextView tvdate = (TextView) view.findViewById(R.id.txtDate);

    tvstatus.setText(cursor.getString(1));
    tvtask.setText(cursor.getString(2));
    tvdate.setText(cursor.getString(3));

  }
}
