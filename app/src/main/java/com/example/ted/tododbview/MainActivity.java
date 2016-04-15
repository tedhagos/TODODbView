package com.example.ted.tododbview;

import android.app.LauncherActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  /*
  * SCHEMA OF todo table in todo.db
  *
  * _id       : INTEGER PRIMARY KEY AUTOINCREMENT
  * status    : INTEGER
  * task      : VARCHAR
  * timestamp : DATETIME DEFAULT CURRENT_TIMESTAMP
  *
  * CREATE IF NOT EXISTS TABLE todo (
  *           _id INTEGER PRIMARY AUTOINCREMENT,
  *           task VARCHAR,
  *           timestamp DEFAULT CURRENT_TIMESTAMP);
  *
  * */

  private final static String DBNAME = "todo.db";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btnAdd = (Button) findViewById(R.id.btnAdd);
    final EditText txtTodo = (EditText) findViewById(R.id.editText);

    assert btnAdd != null;
    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        assert txtTodo != null;
        addRecord(txtTodo.getText().toString());
        txtTodo.setText("");
      }
    });

    ListView listview = (ListView) findViewById(R.id.listView);
    assert listview != null;
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView task  = ((TextView) view.findViewById(R.id.txtTask));
        String tasktext = task.getText().toString();
        String message = String.format("Position: %d, Text: %s", position, tasktext );
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
      }
    });

  }


  public void addRecord(String todo) {
    SQLiteDatabase sqldb = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);

    String sql = String.format("INSERT INTO todo (status, task) VALUES (0, '%s');", todo);
    sqldb.execSQL(sql);
    fetchRecords(sqldb);
    sqldb.close();

  }

  private void fetchRecords(SQLiteDatabase sqldb) {

    String sqlquery = "SELECT _id,status, task, strftime('%m.%d.%Y', timestamp) FROM todo ORDER BY timestamp DESC;";
    Cursor cur = sqldb.rawQuery(sqlquery, null);

    TodoAdapter adapter = new TodoAdapter(this, cur, false);

    ListView listview = (ListView) findViewById(R.id.listView);
    listview.setAdapter(adapter);
  }

}
