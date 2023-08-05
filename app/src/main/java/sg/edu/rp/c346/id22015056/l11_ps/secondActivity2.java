package sg.edu.rp.c346.id22015056.l11_ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class secondActivity2 extends AppCompatActivity {
    Button btnback, btnShowPG13;
    ListView lv;
    ArrayList<Movie> al;
    CustomAdapter caMovie;
    Spinner spinnerRatingFilter;

    @Override
    protected void onResume() {
        super.onResume();
        updateMovieList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity2);

        lv = findViewById(R.id.lv);
        btnback = findViewById(R.id.btnBack);
        spinnerRatingFilter = findViewById(R.id.spinnerRatingFilter);
        btnShowPG13 = findViewById(R.id.btnShowPG13);

        al = new ArrayList<>();
        caMovie = new CustomAdapter(this, R.layout.row, al);
        lv.setAdapter(caMovie);

        updateMovieList();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.ratings_array_with_prompt,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRatingFilter.setAdapter(spinnerAdapter);

        spinnerRatingFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRating = parent.getItemAtPosition(position).toString();
                if (!selectedRating.equals(getString(R.string.prompt_rating))) {
                    DBHelper dbh = new DBHelper(secondActivity2.this);
                    al.clear();
                    if (selectedRating.equals("All")) {
                        al.addAll(dbh.getMovies());
                    } else {
                        al.addAll(dbh.getAllMovies(selectedRating));
                    }
                    caMovie.notifyDataSetChanged();
                    dbh.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = al.get(position);
                Intent i = new Intent(secondActivity2.this, thirdActivity3.class);
                i.putExtra("movie", movie);
                startActivityForResult(i, 1);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnShowPG13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(secondActivity2.this);
                String filterText = "PG13";
                al.clear();
                al.addAll(dbh.getAllMovies(filterText));
                caMovie.notifyDataSetChanged();
                dbh.close();
            }
        });
    }


    private void updateMovieList() {
        DBHelper db = new DBHelper(secondActivity2.this);
        al.clear();
        al.addAll(db.getMovies());
        caMovie.notifyDataSetChanged();
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Movie updatedMovie = (Movie) data.getSerializableExtra("updatedMovie");

            for (int i = 0; i < al.size(); i++) {
                if (al.get(i).getId() == updatedMovie.getId()) {
                    al.set(i, updatedMovie);
                    break;
                }
            }

            caMovie.notifyDataSetChanged();
        }
    }
}
