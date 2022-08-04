package edu.neu.madcourse.numad22su_team11.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.neu.madcourse.numad22su_team11.Adapter.EventAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.R;

public class EventCreateFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Event> createEventList;
    private EventAdapter eventAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_create, container, false);

        recyclerView = view.findViewById(R.id.rv_createEventFrag);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        createEventList = new ArrayList<>();

        return view;
    }
}