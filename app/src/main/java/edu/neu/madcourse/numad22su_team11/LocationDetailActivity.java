package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.numad22su_team11.Adapter.EventAdapter;
import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.Model.Location;

public class LocationDetailActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
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
    private List<String> likedBy;
    private int locationCategory;
    private MaterialCheckBox checkBox;
    private String userid;

    FirebaseUser firebaseUser;
    DatabaseReference eventDatabaseReference;
    DatabaseReference locationDatabaseReference;
    EventAdapter eventAdapter;

    // for create_event_window dialog
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button btn_time, btn_date, btn_save, btn_cancel;
    private EditText et_eventName;

    // for datePicker
    private DatePickerDialog datePickerDialog;
    private int year, month, day;

    // for timePicker
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        eventList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_event_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        iv_locationImg = findViewById(R.id.iv_location_img);
        tv_locationName = findViewById(R.id.tv_location_name);
        tv_locationDescription = findViewById(R.id.tv_description);
        tv_locationAddress = findViewById(R.id.tv_address);
        tv_num_liked = findViewById(R.id.tv_num_like);
        checkBox = findViewById(R.id.checkBox);


        Bundle bundle = getIntent().getExtras();
        locationId = bundle.getString("locationId");

        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        locationDatabaseReference = FirebaseDatabase.getInstance().getReference("Locations").child(locationId);
        locationDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Location location = snapshot.getValue(Location.class);
                locationName = location.getName();
                locationCategory = location.getCategory();
                locationImgUrl = location.getImgUrl();
                locationAddress = location.getAddress();
                locationDescription = location.getDescription();
                locationLiked = location.getNumOfLike();

                likedBy = location.getLikedBy();

                Picasso.get().load(locationImgUrl).into(iv_locationImg);
                tv_locationName.setText(locationName);
                tv_locationDescription.setText(locationDescription);
                tv_locationAddress.setText(locationAddress);
                tv_num_liked.setText(String.valueOf(locationLiked));

                if (likedBy != null && likedBy.contains(userid)) {
                    checkBox.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !likedBy.contains(userid)) {
                    int numOfLike = Integer.valueOf(locationLiked) + 1;
                    // update firebase locations locationId likedBy, add current user to list
                    likedBy.add(userid);
                    HashMap<String, Object> update = new HashMap<>();
                    update.put("likedBy", likedBy);
                    update.put("numOfLike", numOfLike);
                    locationDatabaseReference.updateChildren(update);
                    tv_num_liked.setText(String.valueOf(numOfLike));

                } else if (!isChecked && likedBy.contains(userid)){
                    int numOfLike = Integer.valueOf(locationLiked) - 1;
                    // remove current user from likedBy
                    likedBy.remove(userid);
                    HashMap<String, Object> update = new HashMap<>();
                    update.put("likedBy", likedBy);
                    update.put("numOfLike", numOfLike);
                    locationDatabaseReference.updateChildren(update);
                    tv_num_liked.setText(String.valueOf(numOfLike));
                }
            }
        });


        eventDatabaseReference = FirebaseDatabase.getInstance().getReference("Events");
        eventDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event.getLocationId().equals(locationId) && !event.isPastEvent()){
                        eventList.add(event);
                    }
                }

                Collections.sort(eventList, Event.eventTimeOldToNew);
                eventAdapter = new EventAdapter(getApplicationContext(), eventList);
                recyclerView.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void createEventDialog(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View createEventView = getLayoutInflater().inflate(R.layout.create_event_window, null);

        et_eventName = createEventView.findViewById(R.id.et_name);
        btn_date = createEventView.findViewById(R.id.btn_date);
        btn_time = createEventView.findViewById(R.id.btn_time);
        btn_save = createEventView.findViewById(R.id.btn_save);
        btn_cancel = createEventView.findViewById(R.id.btn_cancel);

        initDatePicker();
        btn_date.setText(getTodaysDate());

        dialogBuilder.setView(createEventView);
        dialog = dialogBuilder.create();
        dialog.show();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = et_eventName.getText().toString();
                String time = year + "/" + month + "/" + day + " " + hour + ":" + minute;
                long timeStamp = convertToTimestamp(time);
                Log.d(TAG, time);
                if (eventName.equals("")) {
                    Toast.makeText(getApplicationContext(), "Event Name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (year == 0 || month == 0 || day == 0) {
                    Toast.makeText(getApplicationContext(), "You have to choose a valid date", Toast.LENGTH_SHORT).show();
                } else {
                    addEventToDB(eventName, timeStamp);
                    dialog.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void addEventToDB(String eventName, long timeStamp) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        List<String> peopleJoined = new ArrayList<>();
        peopleJoined.add(userid);
        String eventId = databaseReference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", eventName);
        hashMap.put("locationId", locationId);
        hashMap.put("creatorId", userid);
        hashMap.put("eventId", eventId);
        hashMap.put("category", locationCategory);
        hashMap.put("peopleJoined", peopleJoined);
        hashMap.put("timeStamp", timeStamp);

        databaseReference.child(eventId).setValue(hashMap);

    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                selectedMonth = selectedMonth + 1;
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                String date = makeDateString(selectedDay, selectedMonth, selectedYear);
                btn_date.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, currentYear, currentMonth, currentDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        String str_month;
        switch (month) {
            case 1:
                str_month = "Jan";
                break;
            case 2:
                str_month = "Feb";
                break;
            case 3:
                str_month = "MAR";
                break;
            case 4:
                str_month = "APR";
                break;
            case 5:
                str_month = "MAY";
                break;
            case 6:
                str_month = "JUN";
                break;
            case 7:
                str_month = "JUL";
                break;
            case 8:
                str_month = "AUG";
                break;
            case 9:
                str_month = "SEP";
                break;
            case 10:
                str_month = "OCT";
                break;
            case 11:
                str_month = "NOV";
                break;
            case 12:
                str_month = "DEC";
                break;
            default:
                str_month = "N/A";
        }
        return str_month;
    }


    public void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                btn_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private long convertToTimestamp(String time) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeStamp = date.getTime();
        return timeStamp;
    }
}