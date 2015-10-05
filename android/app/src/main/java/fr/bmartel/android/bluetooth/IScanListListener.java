package fr.bmartel.android.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Template for scan list listener
 *
 * @author Bertrand Martel
 */
public interface IScanListListener {

    public void onItemAddedInList(BluetoothDevice device);

    public void onNotifyChangeInList();
}
