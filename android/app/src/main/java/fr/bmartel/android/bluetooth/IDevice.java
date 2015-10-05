package fr.bmartel.android.bluetooth;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Generic template for bluetooth device
 *
 * @author Bertrand Martel
 */
public interface IDevice {

    /**
     * check if device is fully intitialized
     * @return
     */
    public boolean isInit();

    /**
     * add a device initialization listener
     *
     * @param listener
     */
    public void addInitListener(IDeviceInitListener listener);

    public void init();

    public void notifyCharacteristicReadReceived(BluetoothGattCharacteristic characteristic);

    public void notifyCharacteristicChangeReceived(BluetoothGattCharacteristic characteristic);

    public void notifyCharacteristicWriteReceived(BluetoothGattCharacteristic characteristic);
}
