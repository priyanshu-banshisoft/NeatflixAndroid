package com.neatflix.utils;

import android.content.Context;
import android.location.LocationManager;



public class PermissionUtils {
    public static boolean isGpsDialogShow = false;

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isGpsDialogShow = true;
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static void showGPSNotEnabledDialog(final Context context) {
        if (!isGpsDialogShow) {

            isGpsDialogShow = true;
        }
    }

}
