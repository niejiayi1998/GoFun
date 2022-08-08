package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.neu.madcourse.numad22su_team11.Adapter.LocationAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Location;
import edu.neu.madcourse.numad22su_team11.Model.User;

public class SearchDisplayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Location> locationList;
    private LocationAdapter locationAdapter;
    private int selectedFilter = -1;
    double latitude;
    double longitude;
    List<Integer> userPreference;

    Intent intent;

    private BottomSheetDialog bottomSheetDialog;
    private RadioButton rb_recommend;
    private RadioButton rb_distance;
    private RadioButton rb_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        getUserPreference();
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
                            sortByRecommend();
                            locationAdapter.notifyDataSetChanged();
                        }
                    }
                });

                rb_distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            sortByDistance();
                            locationAdapter.notifyDataSetChanged();
                        }
                    }
                });

                rb_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Collections.sort(locationList, Location.locationLikeComparator);
                            locationAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });



    }

    /**
     * Implement sorting algorithm based on user's survey, distance, and most liked
     */
    private void sortByRecommend() {
        Collections.sort(locationList, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                if (o1.getScore(latitude, longitude, userPreference.get(o1.getCategory())) - o2.getScore(latitude, longitude, userPreference.get(o2.getCategory())) >= 0) {
                    return -1;
                }
                return 1;
            }
        });
    }

    private void getUserPreference() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userPreference = user.getPreferences();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sortByDistance() {
        Collections.sort(locationList, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                if (o1.getDistance(latitude, longitude) - o2.getDistance(latitude, longitude) >= 0) {
                    return 1;
                }
                return -1;
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