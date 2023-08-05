package sg.edu.rp.c346.id22015056.l11_ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnShow;
    EditText etTitle, etGenre,etYear;
    Spinner spinnerRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);
        etTitle = findViewById(R.id.etTitleM);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYearM);
        spinnerRating = findViewById(R.id.spinner);


        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                String insTitle = etTitle.getText().toString();
                String insGenre = etGenre.getText().toString();
                String insYear = etYear.getText().toString();
                int finalYear = Integer.parseInt(insYear);
                String selectedItem = spinnerRating.getSelectedItem().toString();

                db.insertSong(insTitle,insGenre,finalYear,selectedItem);
                Toast toast = Toast.makeText(btnInsert.getContext(), "Movie added successfully", Toast.LENGTH_LONG);
                toast.show();

            }
        });
        btnShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,secondActivity2.class);
                startActivity(intent);
            }
        });

    }

}
