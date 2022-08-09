package edu.neu.madcourse.numad22su_team11.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.R;

public class EventJoinedAdapter extends RecyclerView.Adapter<EventJoinedAdapter.ViewHolder>{
    private Context mContext;
    private List<Event> eventList;

    public EventJoinedAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventJoinedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_joined_card_item, parent, false);
        return new EventJoinedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventJoinedAdapter.ViewHolder holder, int position) {
        String eventId = eventList.get(position).getEventId();
        holder.bindThisData(eventList.get(position));
        holder.btn_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveEvent(eventId);
            }
        });
    }

    private void leaveEvent(String eventId) {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Events").child(eventId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                List<String> peopleJoined = event.getPeopleJoined();
                peopleJoined.remove(userid);

                HashMap<String, Object> update = new HashMap<>();
                update.put("peopleJoined", peopleJoined);
                databaseReference.updateChildren(update);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView time;
        public TextView numberPeopleJoined;
        public Button btn_leave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_event_img);
            name = itemView.findViewById(R.id.tv_event_name);
            time = itemView.findViewById(R.id.tv_event_time);
            numberPeopleJoined = itemView.findViewById(R.id.tv_num_people_joined);
            btn_leave = itemView.findViewById(R.id.btn_leave);
        }

        private void bindThisData (Event eventToBind){
            name.setText(eventToBind.getName());
            time.setText(eventToBind.convertTimestamp());
            numberPeopleJoined.setText(String.valueOf(eventToBind.getNumPeopleJoined()));

            if (eventToBind.isPastEvent()) {
                btn_leave.setEnabled(false);
            }
        }

    }
}
