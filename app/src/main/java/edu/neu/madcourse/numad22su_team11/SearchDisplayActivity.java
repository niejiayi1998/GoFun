package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.numad22su_team11.Adapter.LocationAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Location;

public class SearchDisplayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Location> locationList;
    private LocationAdapter locationAdapter;
    private int selectedFilter = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        recyclerView = findViewById(R.id.rv_locations);
        locationList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        filterList(selectedFilter);
    }

    private void filterList(int category) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Locations");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Location location = dataSnapshot.getValue(Location.class);
                    if (category == -1) {
                        locationList.add(location);
                    } else if (location.getCategory() == category) {
                        locationList.add(location);
                    }
                }
                locationAdapter = new LocationAdapter(getApplicationContext(), locationList);
                recyclerView.setAdapter(locationAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void allFilterTapped(View view) {
        filterList(-1);
    }

    public void hikingFilterTapped(View view) {
        filterList(0);
    }

    public void sportsFilterTapped(View view) {
        filterList(1);
    }

    public void amusementFilterTapped(View view) {
        filterList(2);
    }

    public void artsFilterTapped(View view) {
        filterList(3);
    }

    public void scenicFilterTapped(View view) {
        filterList(4);
    }

    public void entertainmentFilterTapped(View view) {
        filterList(5);
    }
}