package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    double latitude;
    double longitude;

    Intent intent;

    private BottomSheetDialog bottomSheetDialog;
    private RadioButton rb_recommend;
    private RadioButton rb_distance;
    private RadioButton rb_like;

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


        Button btn_sort = findViewById(R.id.btn_sort);
        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sort_bottom_sheet, null);
                bottomSheetDialog = new BottomSheetDialog(SearchDisplayActivity.this);
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();
                rb_recommend = dialogView.findViewById(R.id.rb_recommend);
                rb_distance = dialogView.findViewById(R.id.rb_distance);
                rb_like = dialogView.findViewById(R.id.rb_like);

                rb_recommend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(getApplicationContext(), "Sort by recommend", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                rb_distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(getApplicationContext(), "Sort by distance", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                rb_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(getApplicationContext(), "Sort by most liked", Toast.LENGTH_SHORT).show();
                            Collections.sort(locationList, Location.locationLikeComparator);
                            locationAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });



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

}