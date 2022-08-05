package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

import edu.neu.madcourse.numad22su_team11.Adapter.EventAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.Model.Location;

public class LocationDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String locationImgUrl;
    private ImageView iv_locationImg;
    private String locationName;
    private TextView tv_locationName;
    private String locationDescription;
    private TextView tv_locationDescription;
    private String locationAddress;
    private TextView tv_locationAddress;
    private int locationLiked;
    private TextView tv_num_liked;
    private String locationId;
    private List<Event> eventList;

    FirebaseUser firebaseUser;
    DatabaseReference eventDatabaseReference;
    DatabaseReference locationDatabaseReference;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        recyclerView = findViewById(R.id.rv_event_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        iv_locationImg = findViewById(R.id.iv_location_img);
        tv_locationName = findViewById(R.id.tv_location_name);
        tv_locationDescription = findViewById(R.id.tv_description);
        tv_locationAddress = findViewById(R.id.tv_address);
        tv_num_liked = findViewById(R.id.tv_num_like);


        Bundle bundle = getIntent().getExtras();
        locationId = bundle.getString("locationId");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        locationDatabaseReference = FirebaseDatabase.getInstance().getReference("Locations");
        locationDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Location location = dataSnapshot.getValue(Location.class);
                    if (location.getLocationId().equals(locationId)){
                        locationName = location.getName();
                        locationImgUrl = location.getImgUrl();
                        locationAddress = location.getAddress();
                        locationDescription = location.getDescription();
                        locationLiked = location.getNumOfLike();
                        tv_locationName.setText(locationName);
                        tv_locationDescription.setText(locationDescription);
                        tv_locationAddress.setText(locationAddress);
                        tv_num_liked.setText(String.valueOf(locationLiked));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//    tv_locationName.setText(locationName);
//    tv_locationDescription.setText(locationDescription);
//    tv_locationAddress.setText(locationAddress);
//    tv_num_liked.setText(locationLiked);

//        eventDatabaseReference = FirebaseDatabase.getInstance().getReference("Events");
//        eventDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                eventList.clear();
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Event event = dataSnapshot.getValue(Event.class);
//                    if (!event.getLocationId().equals(locationId)){
//                        eventList.add(event);
//                    }
//                }
//
//                eventAdapter = new EventAdapter(getApplicationContext(), eventList);
//                recyclerView.setAdapter(eventAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public void onClick (View view) {
        startActivity(new Intent(LocationDetailActivity.this, UserProfileActivity.class));
    }
}