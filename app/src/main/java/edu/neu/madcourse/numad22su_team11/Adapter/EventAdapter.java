package edu.neu.madcourse.numad22su_team11.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context mContext;
    private List<Event> eventList;

    public EventAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_create_card_item, parent, false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        holder.bindThisData(eventList.get(position));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_event_img);
            name = itemView.findViewById(R.id.tv_event_name);
            time = itemView.findViewById(R.id.tv_event_time);
            numberPeopleJoined = itemView.findViewById(R.id.tv_num_people_joined);
        }

        private void bindThisData (Event eventToBind){
            Picasso.get().load(eventToBind.getImgUrl()).into(image);
            name.setText(eventToBind.getName());
            time.setText(eventToBind.getTime());
            numberPeopleJoined.setText((eventToBind.getNumPeopleJoined()));
        }

    }
}
