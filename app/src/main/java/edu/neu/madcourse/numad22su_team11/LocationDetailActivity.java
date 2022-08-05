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
    private ImageView locationImg;
    private TextView locationName;
    private TextView locationDescription;
    private TextView locationAddress;
    private String locationId;
    private List<Event> eventList;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReferencer;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        recyclerView = findViewById(R.id.rv_event_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        databaseReferencer = FirebaseDatabase.getInstance().getReference("Events");
//        databaseReferencer.addValueEventListener(new ValueEventListener() {
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