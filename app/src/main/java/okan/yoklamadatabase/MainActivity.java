package okan.yoklamadatabase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editTextId=(EditText)findViewById(R.id.editTextId);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll=(Button)findViewById(R.id.button_ViewAll);
        btnviewUpdate=(Button)findViewById(R.id.buttonUpdate);
        btnDelete=(Button)findViewById(R.id.buttonDelete);
        AddData();
        viewAll();
        Update();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void Update(){
        btnviewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateDate(editTextId.getText().toString(),editName.getText().toString(),
                                                    editSurname.getText().toString(),editMarks.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getAllData();
                if (res.getCount()==0){
                    //show message
                    showMessage("Error","Veri bulunamadi");
                    return;
                }
                    StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id:"+ res.getString(0)+"\n");
                    buffer.append("Ad:"+ res.getString(1)+"\n");
                    buffer.append("Soyad:"+ res.getString(2)+"\n");
                    buffer.append("Numara:"+ res.getString(3)+"\n\n");
                }
                    //show all data
                    showMessage("Data",buffer.toString());

            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}


// Source Code: https://www.youtube.com/playlist?list=PLS1QulWo1RIaRdy16cOzBO5Jr6kEagA07