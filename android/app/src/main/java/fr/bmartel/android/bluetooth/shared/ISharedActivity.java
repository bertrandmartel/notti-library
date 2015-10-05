package fr.bmartel.android.bluetooth.shared;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

/**
 * Generic interface for sharing list view adapter between activity and child class
 *
 * @author Bertrand Martel
 */
public interface ISharedActivity {

    /**
     * get list view adapter of current activity
     * @return
     */
    public StableArrayAdapter getListViewAdapter();

    /**
     * retrieve activity application context
     *
     * @return
     */
    public Context getContext();

    public void addDeviceToList(BluetoothDevice runnable);

    public void notifyChangeInList();
}
