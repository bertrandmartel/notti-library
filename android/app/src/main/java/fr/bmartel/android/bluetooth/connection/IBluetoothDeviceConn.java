package fr.bmartel.android.bluetooth.connection;

import android.bluetooth.BluetoothGatt;

import java.util.TimerTask;
import java.util.UUID;

import fr.bmartel.android.bluetooth.IBluetoothCustomManager;
import fr.bmartel.android.bluetooth.IDevice;

/**
 * Generic template for bluetooth device gatt connection
 *
 * @author Bertrand Martel
 */
public interface IBluetoothDeviceConn {

    /**
     * retrieve bluetooth device address
     *
     * @return
     */
    public String getAddress();

    public String getDeviceName();

    public BluetoothGatt getBluetoothGatt();

    public boolean isConnected();

    /**
     * write to a characteristic
     * @param serviceSmartliteControlUUID
     * @param characteristicSmartliteSettingsUUID
     * @param value
     */
    public void writeCharacteristic(String serviceSmartliteControlUUID, String characteristicSmartliteSettingsUUID, byte[] value);

    /**
     * read from a characteristic
     * @param serviceName
     * @param characteristicName
     */
    public void readCharacteristic(String serviceName, String characteristicName);

    public void enableDisableNotification(UUID service, UUID charac, boolean enable);

    public void enableGattNotifications(UUID service, UUID charac);

    public IBluetoothCustomManager getManager();

    public IDevice getDevice();

    public void disconnect();
}
