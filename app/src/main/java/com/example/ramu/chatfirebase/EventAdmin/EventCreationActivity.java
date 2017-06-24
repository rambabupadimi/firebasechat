package com.example.ramu.chatfirebase.EventAdmin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ramu.chatfirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class EventCreationActivity extends AppCompatActivity {

    EditText eventTitle,eventDescription,eventStartDate,eventEndDate,
        eventLocation,eventOrganizers,eventGuests;

    ImageView eventLocationIcon,eventposter;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    Uri mresultUri=null;
    TextView createEvent;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    private final static int PLACE_PICKER_REQUEST_ONE = 1;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    CreateEvent createEventObject;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String eventSDate,eventSTime,eventEDate,eventETime;
    String eventUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        eventTitle              = (EditText) findViewById(R.id.event_title);
        eventDescription        = (EditText) findViewById(R.id.event_description);
        eventStartDate          =   (EditText) findViewById(R.id.event_start_time);
        eventEndDate            =   (EditText)findViewById(R.id.event_end_time);
        eventGuests             =   (EditText)findViewById(R.id.event_guests);
        eventLocationIcon       =   (ImageView) findViewById(R.id.event_location_icon);
        eventLocation           =   (EditText)findViewById(R.id.event_address);
        eventOrganizers         =   (EditText)findViewById(R.id.event_organizers);

        createEvent             =   (TextView) findViewById(R.id.save_icon);
        eventposter             =   (ImageView)findViewById(R.id.event_picture);

        HashMap hashMap = new HashMap();
        requestPremission();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events");
        databaseReference.keepSynced(true);

        mAuth = FirebaseAuth.getInstance();

        try {
            eventUserId = mAuth.getCurrentUser().getUid();

        } catch (Exception e) {
            e.printStackTrace();
        }



        eventposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });


        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EventCreationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eventSDate = ""+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                // Get Current Time
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(EventCreationActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                eventSTime = hourOfDay + ":" + minute;
                                                eventSDate += "/"+eventSTime;

                                                eventStartDate.setText(eventSDate);

                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EventCreationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eventEDate = ""+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                // Get Current Time
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(EventCreationActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                eventETime = hourOfDay + ":" + minute;
                                                eventEDate += "/"+eventETime;

                                                eventEndDate.setText(eventEDate);

                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        eventLocationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        try {
                            Intent intent = builder.build(EventCreationActivity.this);
                            startActivityForResult(intent,PLACE_PICKER_REQUEST_ONE);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
            }
        });


        createEventObject = new CreateEvent();
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventTitle!=null && eventTitle.toString().length()>0)
                {
                    createEventObject.setEventUserId(eventUserId);
                    createEventObject.setEventTitle(eventTitle.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter title",Toast.LENGTH_LONG).show();
                    return;
                }

                if(eventDescription!=null && eventDescription.getText().toString().length()>0)
                {
                    createEventObject.setEventDescription(eventDescription.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter description",Toast.LENGTH_LONG).show();
                    return;
                }
                if(eventStartDate!=null && eventStartDate.getText().toString().length()>0)
                {
                    createEventObject.setEventStartDate(eventSDate);
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter event start data",Toast.LENGTH_LONG).show();
                    return;
                }

                if(eventOrganizers!=null && eventOrganizers.getText().toString().length()>0)
                {
                    createEventObject.setEventOrganizers(eventOrganizers.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter organizers",Toast.LENGTH_LONG).show();
                    return;
                }

                if(eventEndDate!=null && eventEndDate.getText().toString().length()>0)
                {
                    createEventObject.setEventEndDate(eventEDate);
                }

                if(eventGuests!=null && eventGuests.getText().toString().length()>0)
                {
                    createEventObject.setEventGuests(eventGuests.getText().toString());
                }

                if(eventOrganizers!=null && eventOrganizers.getText().toString().length()>0)
                {
                    createEventObject.setEventOrganizers(eventOrganizers.getText().toString());
                }


                if (filePath != null) {
                    //displaying a progress dialog while upload is going on
                    final ProgressDialog progressDialog = new ProgressDialog(EventCreationActivity.this);
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();


                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images");
                    StorageReference riversRef = storageRef.child(mresultUri.getLastPathSegment());
                    riversRef.putFile(mresultUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //if the upload is successfull
                                    //hiding the progress dialog
                                    progressDialog.dismiss();
                                        // HashMap hashMap = new HashMap();
                                        final Uri  downloadUrl = taskSnapshot.getDownloadUrl();

                                        String imgurl="images/"+filePath.getLastPathSegment();
                                        // hashMap.put("imgurl",""+imgurl);
                                        createEventObject.setEventPosterURL(downloadUrl.toString());

                                    databaseReference.push().setValue(createEventObject);
                                    databaseReference.keepSynced(true);

                                    Intent intent = new Intent(EventCreationActivity.this,EventsDisplay.class);
                                    startActivity(intent);


                                    //and displaying a success toast
                                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //if the upload is not successfull
                                    //hiding the progress dialog
                                    progressDialog.dismiss();


                                    databaseReference.push().setValue(createEventObject);
                                    databaseReference.keepSynced(true);

                                    Intent intent = new Intent(EventCreationActivity.this,EventsDisplay.class);
                                    startActivity(intent);


                                    //and displaying error message
                                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    //calculating progress percentage
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                    //displaying percentage in progress dialog
                                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                }
                            });
                }
                //if there is not any file
                else {
                    //you can display an error toast
                }



            }
        });
    }



    private void requestPremission()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {

                //   Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //   uploadImage.setImageBitmap(bitmap);

                mresultUri= data.getData();
                eventposter.setImageURI(mresultUri);

                CropImage.activity(filePath)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result= CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK)
            {
                mresultUri= result.getUri();
                eventposter.setImageURI(mresultUri);
            }
        }
*/

        else if(requestCode == PLACE_PICKER_REQUEST_ONE)
        {
            if(resultCode == RESULT_OK)
            {
                Place place = PlacePicker.getPlace(this,data);
                eventLocation.setText(place.getAddress());
                createEventObject.setEventLocation(place.getAddress().toString());
                if(place.getAttributions() == null)
                {
                    //   webView.loadData("No Attributions","text/html; charset-utf-8","UFT-8");
                }
                else
                {
                    //  webView.loadData(place.getAttributions().toString(),"text/html; charset-utf-8","UFT-8");
                }
            }
        }


    }

}
