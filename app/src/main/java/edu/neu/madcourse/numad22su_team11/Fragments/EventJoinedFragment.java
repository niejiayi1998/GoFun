package edu.neu.madcourse.numad22su_team11.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import edu.neu.madcourse.numad22su_team11.Adapter.EventJoinedAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.R;

public class EventJoinedFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Event> joinedEventList;
    private EventJoinedAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_joined, container, false);

        joinedEventList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_joinedEventFrag);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getEvents();

        return view;
    }

    private void getEvents() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joinedEventList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);

                    if (event.getPeopleJoined().contains(userid) && !event.getCreatorId().equals(userid)) {
                        joinedEventList.add(event);
                    }
                }

                Collections.sort(joinedEventList, Event.eventTimeNewToOld);
                eventAdapter = new EventJoinedAdapter(getContext(), joinedEventList);
                recyclerView.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}