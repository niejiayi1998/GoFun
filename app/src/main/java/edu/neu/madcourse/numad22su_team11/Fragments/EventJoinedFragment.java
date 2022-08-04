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
import edu.neu.madcourse.numad22su_team11.R;

/**

 */
public class EventJoinedFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList joinedEventList;
    private EventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_joined, container, false);

        recyclerView = view.findViewById(R.id.rv_joinedEventFrag);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        joinedEventList = new ArrayList<>();

        return view;
    }

}