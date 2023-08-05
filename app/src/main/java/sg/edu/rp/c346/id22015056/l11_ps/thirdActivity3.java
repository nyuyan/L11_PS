package sg.edu.rp.c346.id22015056.l11_ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class thirdActivity3 extends AppCompatActivity {
    EditText etTitle, etGenre, etYear, etSongID;
    Button btnCancel, btnUpdate, btnDelete;
    Spinner spinnerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdactivity3);

        etTitle = findViewById(R.id.etTitleM);
        etGenre = findViewById(R.id.etGenreM);
        etYear = findViewById(R.id.etYearM);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        spinnerRating = findViewById(R.id.spinner2);
        etSongID = findViewById(R.id.etIDm);

        Intent i = getIntent();
        Movie data = (Movie) i.getSerializableExtra("movie");
        etSongID.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));
        spinnerRating.setSelection(getIndex(spinnerRating, data.getRating()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(thirdActivity3.this);
                String selectedRate = spinnerRating.getSelectedItem().toString();
                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setRating(selectedRate);
                db.updateMovie(data);
                db.close();

                Intent intent = new Intent();
                intent.putExtra("updatedMovie", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(thirdActivity3.this);
                dbh.deleteMovies(data.getId());
                dbh.close();

                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private int getIndex(Spinner spinnerRating, String rating) {
        for (int i = 0; i < spinnerRating.getCount(); i++) {
            if (spinnerRating.getItemAtPosition(i).toString().equalsIgnoreCase(rating)) {
                return i;
            }
        }
        return 0;
    }
}
