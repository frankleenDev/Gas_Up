package app.weconnect.gasappgasup.location;

import android.content.Context;
import android.location.Location;

public interface LocationManagerInteractor {
    interface OnLocationManagerListener {
        void onGetManagerLocation(Location location);
    }
    void getLocation(Context context, OnLocationManagerListener onLocationManagerListener);
}
