package edu.neu.madcourse.numad22su_team11.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.neu.madcourse.numad22su_team11.LocationDetailActivity;
import edu.neu.madcourse.numad22su_team11.Model.Location;
import edu.neu.madcourse.numad22su_team11.R;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context mContext;
    private List<Location> locationList;
    private double latitude;
    private double longitude;

    public LocationAdapter(Context mContext, List<Location> locationList, double latitude, double longitude) {
        this.mContext = mContext;
        this.locationList = locationList;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.location_card_item, parent, false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        String locationId = locationList.get(position).getLocationId();
        holder.bindThisData(locationList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LocationDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("locationId", locationId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView numOfLike;
        public TextView tv_distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.locationImage);
            name = itemView.findViewById(R.id.locationName);
            numOfLike = itemView.findViewById(R.id.tv_numOfLike);
            tv_distance = itemView.findViewById(R.id.distance);
        }

        private void bindThisData (Location locationToBind) {
            Picasso.get().load(locationToBind.getImgUrl()).into(image);
            name.setText(locationToBind.getName());
            numOfLike.setText(String.valueOf(locationToBind.getNumOfLike()));

            float[] result = new float[1];
            android.location.Location.distanceBetween(latitude, longitude, locationToBind.getLatitude(), locationToBind.getLongitude(), result);
            double distance = result[0] * 0.000621371;
            String str_dist = String.format("%.2f", distance) + " mi";
            tv_distance.setText(String.valueOf(str_dist));
        }
    }
}
