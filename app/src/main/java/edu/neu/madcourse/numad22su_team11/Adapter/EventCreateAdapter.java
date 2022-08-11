package edu.neu.madcourse.numad22su_team11.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.neu.madcourse.numad22su_team11.LocationDetailActivity;
import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.R;

public class EventCreateAdapter extends RecyclerView.Adapter<EventCreateAdapter.ViewHolder>{
    private Context mContext;
    private List<Event> eventList;

    public EventCreateAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventCreateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_create_card_item, parent, false);
        return new EventCreateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCreateAdapter.ViewHolder holder, int position) {
        String eventId = eventList.get(position).getEventId();
        String locationId = eventList.get(position).getLocationId();
        holder.bindThisData(eventList.get(position));
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(eventId);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LocationDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("locationId", locationId);
                mContext.startActivity(intent);
            }
        });
    }

    private void deleteEvent(String eventId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Events").child(eventId);
        databaseReference.removeValue();
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
        public Button btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_event_img);
            name = itemView.findViewById(R.id.tv_event_name);
            time = itemView.findViewById(R.id.tv_event_time);
            numberPeopleJoined = itemView.findViewById(R.id.tv_num_people_joined);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }

        private void bindThisData (Event eventToBind){
            name.setText(eventToBind.getName());
            time.setText(eventToBind.convertTimestamp());
            numberPeopleJoined.setText(String.valueOf(eventToBind.getNumPeopleJoined()));

            int category = eventToBind.getCategory();
            switch (category) {
                case 0:
                    image.setImageResource(R.drawable.hiking);
                    break;
                case 1:
                    image.setImageResource(R.drawable.sports);
                    break;
                case 2:
                    image.setImageResource(R.drawable.amusement_park);
                    break;
                case 3:
                    image.setImageResource(R.drawable.arts);
                    break;
                case 4:
                    image.setImageResource(R.drawable.scenicview);
                    break;
                case 5:
                    image.setImageResource(R.drawable.entertainment);
                    break;
                default:
                    image.setImageResource(R.mipmap.ic_launcher);

            }

            if (eventToBind.isPastEvent()) {
                btn_delete.setEnabled(false);
            }
        }

    }
}
