package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.neu.madcourse.numad22su_team11.Adapter.LocationAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Location;

public class SearchDisplayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Location> locationList;
    private LocationAdapter locationAdapter;
    private int selectedFilter = -1;
    private double latitude;
    private double longitude;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        intent = getIntent();
        selectedFilter = intent.getIntExtra("selectedFilter", -1);
        latitude = intent.getDoubleExtra("latitude", 37.3387);
        longitude = intent.getDoubleExtra("longitude", -121.8853);


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
                locationAdapter = new LocationAdapter(getApplicationContext(), locationList, latitude, longitude);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_recom:
                // sort by most recommended
                //Collections.sort(locationList, President.PresidentNameAZComparator);
                Toast.makeText(this, "Sort by recommended", Toast.LENGTH_SHORT).show();
                locationAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_dist:
                // sort by closest
                //Collections.sort(locationList, President.PresidentNameZAComparator);
                Toast.makeText(this, "Sort by distance", Toast.LENGTH_SHORT).show();
                locationAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_like:
                // sort by most liked
                //Collections.sort(locationList, President.PresidentNameDateAscendingComparator);
                Toast.makeText(this, "Sort by most liked", Toast.LENGTH_SHORT).show();
                locationAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}