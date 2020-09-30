package game.mode.crudsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText editName,editSurname,editMarks,editID;
    Button btnAdd,btnReadAll,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb= new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.idname);
        editSurname = (EditText) findViewById(R.id.idsurname);
        editMarks = (EditText) findViewById(R.id.idmarks);
        editID = (EditText)findViewById(R.id.idId);
        btnAdd = (Button) findViewById(R.id.idAdd);
        btnReadAll = (Button)findViewById(R.id.idreadAll);
        btnUpdate = (Button)findViewById(R.id.idUpdate);
        btnDelete = (Button)findViewById(R.id.idDelete);
        AddData();
        readAll();
        updateData();
        DeleteId();
    }

    public void AddData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    clearInputs();
                }else{
                    Toast.makeText(MainActivity.this, "Data no Inserted", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
    public void readAll(){
        btnReadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res= myDb.getAllData();
               if(res.getCount()==0){
                   showMessage("Error", "Nothing found");
                   return;
               }
               StringBuffer buffer = new StringBuffer();
               while(res.moveToNext()){
                   buffer.append("ID: "+res.getString(0)+"\n");
                   buffer.append("NAME: "+res.getString(1)+"\n");
                   buffer.append("SURNAME: "+res.getString(2)+"\n");
                   buffer.append("MARKS: "+ res.getString(3)+"\n\n");
               }
                showMessage("Data", buffer.toString());
            }
        });
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editID.getText().toString(),editName.getText().toString()
                        ,editSurname.getText().toString()
                        ,editMarks.getText().toString());
                if(isUpdate == true){
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                    clearInputs();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                    clearInputs();
                }
            }
        });
    }
    public void DeleteId(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.Delete(editID.getText().toString());
                if (deleteRows>0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                    editID.setText("");
                }else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clearInputs(){
        editName.setText("");
        editSurname.setText("");
        editMarks.setText("");
        editID.setText("");
    }
}
