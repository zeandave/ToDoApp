package xyz.anata.testing;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoList;
    ArrayAdapter arrayAdapter;
    ListView showTodo;
    EditText addTextItem;
    public void addItem(View view){
        addTextItem =findViewById(R.id.addTextItem);
        String newItem =addTextItem.getText().toString();
        todoList.add(newItem);
        addTextItem.setText("");
        writeItem();
        showTodo =  findViewById(R.id.listViewItem);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoList);
        showTodo.setAdapter(arrayAdapter);
    }
    public void setUpListViewListener(){
        showTodo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoList.remove(i);
                arrayAdapter.notifyDataSetChanged();
                writeItem();
            }
        });
    }
    public void readItem() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.txt");
        try{
            todoList = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            todoList = new ArrayList<String>();
        }

    }
    public void writeItem(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,todoList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItem();
        showTodo =  findViewById(R.id.listViewItem);
//        todoList = new ArrayList<>(); It's deleted because We're using a file
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoList);
        showTodo.setAdapter(arrayAdapter);
        setUpListViewListener();
    }
}
