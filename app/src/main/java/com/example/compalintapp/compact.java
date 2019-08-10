package com.example.compalintapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class compact extends AppCompatActivity implements  LocationListener {

    public static LatLng lpass;
    Geocoder geocoder;
    List<Address> address;
    static String add,area,city,country,pincode,fulladress;

    Button cur,last,sms,vieww,dev;
    EditText name,comp,num;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compact);

        cur=findViewById(R.id.button2);
        last=findViewById(R.id.button3);
        sms=findViewById(R.id.button4);
        name=findViewById(R.id.editText4);
        comp=findViewById(R.id.editText6);
        num=findViewById(R.id.editText7);
        vieww=findViewById(R.id.button5);
        dev=findViewById(R.id.button6);

        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(compact.this,developer.class);
                startActivity(intent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Dexter.withActivity(compact.this)
                        .withPermission(Manifest.permission.SEND_SMS)
                        .withListener(new PermissionListener() {
                            @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                                Toast.makeText(compact.this,"Permission Granted!",Toast.LENGTH_SHORT).show();
                               //getLocationName();

                                SmsManager smsManager=SmsManager.getDefault();
                                smsManager.sendTextMessage(num.getText().toString(),null, "Name:"+name.getText().toString()+"\nComplaint:" +
                                        comp.getText().toString()+"\n Address: LPU Open Audi Rd,Punjab 144411,India" +
                                        "\n",null,null);

                                DBHelper dbHelper=new DBHelper(compact.this);
                                Storage storage=new Storage();

                                storage.setName(name.getText().toString());
                                storage.setComplaint(comp.getText().toString());
                                storage.setNum(num.getText().toString());
                                storage.setId(1);
                                long id=dbHelper.addComplaint(storage);
                                Toast.makeText(compact.this,"DATABASE"+id,Toast.LENGTH_SHORT).show();


                            }
                            @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(compact.this,"Permission Denied!",Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                                new AlertDialog.Builder(compact.this).setTitle("Rationale Box").setMessage("Please give it!")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                token.continuePermissionRequest();
                                            }
                                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        token.cancelPermissionRequest();
                                    }
                                });
                            }
                        }).check();

            }
        });
        cur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(compact.this);
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(compact.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                        getLocationName(location);
                        Intent intent=new Intent(compact.this,MapsActivity.class);
                        MapsActivity.latLngfetch=latLng;
                        startActivity(intent);
                    }
                }).addOnFailureListener(compact.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getLocation();
                    }
                });
                Intent intent=new Intent(compact.this,developer.class);
                startActivity(intent);
            }
        });


        vieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(compact.this,viewcomplaint.class);
                startActivity(intent);
            }
        });
        //Address

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,200, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        Intent intent=new Intent(compact.this,MapsActivity.class);
        MapsActivity.latLngfetch=latLng;
        lpass=latLng;
        getLocationName(location);
           }

    public void getLocationName(Location location) {
        geocoder= new Geocoder(compact.this, Locale.getDefault());

        try {
            address= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            add=address.get(0).getAddressLine(0);
            area=address.get(0).getLocality();
            city=address.get(0).getAdminArea();
            country=address.get(0).getCountryName();
            pincode=address.get(0).getPostalCode();
            fulladress=address+","+add+","+area+","+city+","+country+","+pincode;
            Log.d("LOCATION",fulladress);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
