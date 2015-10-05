package fr.bmartel.android.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

import java.util.ArrayList;
import java.util.HashMap;

import fr.bmartel.android.bluetooth.connection.IBluetoothDeviceConn;
import fr.bmartel.android.utils.ManualResetEvent;

/**
 * Generic interface for bluetooth custom manager
 *
 * @author  Bertrand Martel
 */
public interface IBluetoothCustomManager {

    public ManualResetEvent getEventManager();

    public void broadcastUpdate(String action);

    public void broadcastUpdateStringList(String action, ArrayList<String> strList);

    public void writeCharacteristic(BluetoothGattCharacteristic charac, byte[] value, BluetoothGatt gatt);

    public void readCharacteristic(BluetoothGattCharacteristic charac, BluetoothGatt gatt);

    public void writeDescriptor(BluetoothGattDescriptor descriptor, BluetoothGatt gatt);

    public HashMap<String,IBluetoothDeviceConn> getConnectionList();
}
