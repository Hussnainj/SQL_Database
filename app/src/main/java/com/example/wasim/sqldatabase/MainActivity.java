package com.example.wasim.sqldatabase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText etname, etsurname, etmarks,etId;
    Button btnadddata, btnviewall,btnupdate,btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        etId=(EditText)findViewById(R.id.editText4);
        etname = (EditText) findViewById(R.id.editText);
        etsurname = (EditText) findViewById(R.id.editText2);
        etmarks = (EditText) findViewById(R.id.editText3);
        btnadddata = (Button) findViewById(R.id.button);
        btnviewall = (Button) findViewById(R.id.button2);
        btnupdate=(Button)findViewById(R.id.button3);
        btndelete=(Button)findViewById(R.id.button4);

        AddData();
        viewAll();
        updatedata();
        deletedata();


    }

    public void updatedata(){
        btnupdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isupdate=myDb.updateData(etId.getText().toString(),etname.getText().toString(),
                                etsurname.getText().toString(),etmarks.getText().toString());
                        if (isupdate==true)
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void deletedata(){
        btndelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Integer deleterows=myDb.deleteData(etId.getText().toString());
                        if (deleterows>0)
                            Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void AddData() {
        btnadddata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insetData(etname.getText().toString(), etsurname.getText().toString(),
                                etmarks.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnviewall.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :" + res.getString(0) + "\n");
                            buffer.append("NAME :" + res.getString(1) + "\n");
                            buffer.append("SURNAME :" + res.getString(2) + "\n");
                            buffer.append("MARKS :" + res.getString(3) + "\n\n");
                        }
                        // show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
