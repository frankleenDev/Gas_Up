package app.weconnect.gasappgasup.location;

import android.location.Location;

public interface LocationView {
    void next(Location location);
    void error();
}
