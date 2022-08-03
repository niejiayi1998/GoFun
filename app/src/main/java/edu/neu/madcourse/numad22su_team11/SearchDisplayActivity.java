package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        recyclerView = findViewById(R.id.rv_locations);
        locationList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        fetchLocationData();
    }

    private void fetchLocationData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Locations");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Location location = dataSnapshot.getValue(Location.class);
                    locationList.add(location);
                }
                locationAdapter = new LocationAdapter(getApplicationContext(), locationList);
                recyclerView.setAdapter(locationAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}