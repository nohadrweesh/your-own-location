package com.example.yourownlocation;

/**
 * Created by Speed on 13/10/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

class LocationManipulating {
    private static final String TAG = "LocationManipulating";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //private  LocationObject retObject;;
    private Context mContext;
    //private Activity mActivity;

    public LocationManipulating(Context context) {
        mContext = context;
    }
    public LocationObject getLocation(){

         final LocationObject retObject=new LocationObject();


        if(checkGettingLocationPermission()) {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        Toast.makeText(mContext.getApplicationContext(), "your location is set", Toast.LENGTH_LONG).show();

                        double latitude=location.getLatitude();
                        double longitude=location.getLongitude();
                        double altitude=location.getAltitude();
                        retObject.setLongitude(longitude);
                        retObject.setLatitude(latitude);
                        retObject.setAltitude(altitude);

                    }

                }
            });
        }else{
            Toast.makeText(mContext.getApplicationContext(),"please accept location permission",Toast.LENGTH_LONG).show();
        }
return  retObject;
    }

    private boolean checkGettingLocationPermission(){
        Log.d(TAG, "checkGettingLocationPermission: starts");
        String permission ="android.permission.ACCESS_COARSE_LOCATION";
        int res=mContext.getApplicationContext().checkCallingOrSelfPermission(permission);

        if (res== PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkGettingLocationPermission: ends with res :"+res);
            return true;
        }else{
            ActivityCompat
                    .requestPermissions((Activity) mContext, new String[]{permission}, REQUEST_CODE_ASK_PERMISSIONS);
            res=mContext.getApplicationContext().checkCallingOrSelfPermission(permission);
            Log.d(TAG, "checkGettingLocationPermission: ends with res :"+res);
            return (res== PackageManager.PERMISSION_GRANTED);
        }
    }
}
