package fr.bmartel.android.bluetooth;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Characteritic listener template to be used in device implementation
 *
 * @author Bertrand Martel
 */
public interface ICharacteristicListener {

    /**
     * called when onCharacteristicRead() gatt callback has been received
     *
     * @param charac characteristic that has been read
     */
    public void onCharacteristicReadReceived(BluetoothGattCharacteristic charac);

    /**
     * called chane onCharacteristicChange() gatt callback has been received
     *
     * @param charac characteristic whose value has changed
     */
    public void onCharacteristicChangeReceived(BluetoothGattCharacteristic charac);

    /**
     * called when onCharacteristicWrite() gatt callback is received
     *
     * @param charac
     */
    public void onCharacteristicWriteReceived(BluetoothGattCharacteristic charac);
}
