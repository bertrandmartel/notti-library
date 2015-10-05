package fr.bmartel.android.bluetooth.connection;

import android.bluetooth.BluetoothGattCharacteristic;

import java.util.UUID;

import fr.bmartel.android.bluetooth.ICharacteristicListener;
import fr.bmartel.android.bluetooth.IDevice;
import fr.bmartel.android.bluetooth.connection.IBluetoothDeviceConn;

/**
 * Bluetooth device implementation abstraction
 *
 * @author  Bertrand Martel
 */
public abstract class BluetoothDeviceAbstr implements IDevice {


    private ICharacteristicListener characteristicListener;

    /**
     * bluetooth device gatt connection management
     */
    protected IBluetoothDeviceConn conn = null;

    /**
     * Give bluetooth device connection to device implementation object
     * @param conn
     */
    public BluetoothDeviceAbstr(IBluetoothDeviceConn conn)
    {
        this.conn=conn;
    }

    /**
     * enable gatt notification for a specific service and a specific characteristic
     *
     * @param service
     * @param charac
     */
    public void enableNotification(String service,String charac)
    {
        conn.enableGattNotifications(UUID.fromString(service), UUID.fromString(charac));
        conn.enableDisableNotification(UUID.fromString(service), UUID.fromString(charac),true);
    }

    /**
     * getter for bluetooth connection
     * @return
     */
    public IBluetoothDeviceConn getConn()
    {
        return conn;
    }

    /**
     * notify characteristic read event
     *
     * @param characteristic
     *      Bluetooth characteristic
     */
    @Override
    public void notifyCharacteristicReadReceived(BluetoothGattCharacteristic characteristic)
    {
        characteristicListener.onCharacteristicReadReceived(characteristic);
    }

    @Override
    public void notifyCharacteristicWriteReceived(BluetoothGattCharacteristic characteristic)
    {
        characteristicListener.onCharacteristicWriteReceived(characteristic);
    }

    /**
     * notify characteritistic change event
     *
     * @param characteristic
     *      Bluetooth characteristic
     */
    @Override
    public void notifyCharacteristicChangeReceived(BluetoothGattCharacteristic characteristic)
    {
        characteristicListener.onCharacteristicChangeReceived(characteristic);
    }

    /**
     * getter for characteristic listener
     *
     * @return
     */
    public ICharacteristicListener getCharacteristicListener()
    {
        return characteristicListener;
    }

    /**
     * setter for characteristic listener
     *
     * @param listener
     */
    public void setCharacteristicListener(ICharacteristicListener listener)
    {
        characteristicListener=listener;
    }
}
