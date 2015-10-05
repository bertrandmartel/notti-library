/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Bertrand Martel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.android.bluetooth.notti;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattCharacteristic;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import fr.bmartel.android.bluetooth.ICharacteristicListener;
import fr.bmartel.android.bluetooth.IDeviceInitListener;
import fr.bmartel.android.bluetooth.connection.BluetoothDeviceAbstr;
import fr.bmartel.android.bluetooth.connection.IBluetoothDeviceConn;
import fr.bmartel.android.utils.ByteUtils;

/**
 * Notti Bluetooth device management
 *
 * @author Bertrand Martel
 */
public class NottiDevice extends BluetoothDeviceAbstr implements INottiDevice {

    private String TAG = NottiDevice.this.getClass().getName();

    private String notti_service="0000fff0-0000-1000-8000-00805f9b34fb";
    private String notti_charac="0000fff3-0000-1000-8000-00805f9b34fb";
    private String notti_charac2="0000fff4-0000-1000-8000-00805f9b34fb";

    private ArrayList<INottiListener> flowerPowerListenerList = new ArrayList<>();
    private ArrayList<IDeviceInitListener> initListenerList = new ArrayList<>();

    private boolean init = false;

    /**
     * @param conn
     */
    @SuppressLint("NewApi")
    public NottiDevice(IBluetoothDeviceConn conn) {
        super(conn);
        setCharacteristicListener(new ICharacteristicListener() {

            @Override
            public void onCharacteristicReadReceived(BluetoothGattCharacteristic charac) {

                if (charac.getUuid().toString().equals(notti_charac)) {

                    System.out.println(ByteUtils.byteArrayToStringMessage("test",charac.getValue(),'|'));

                } else if (charac.getUuid().toString().equals(notti_charac2)) {

                    System.out.println(ByteUtils.byteArrayToStringMessage("test2",charac.getValue(),'|'));

                }
            }

            @Override
            public void onCharacteristicChangeReceived(BluetoothGattCharacteristic charac) {

                if (charac.getUuid().toString().equals(notti_charac)) {

                    System.out.println(ByteUtils.byteArrayToStringMessage("test",charac.getValue(),'|'));

                } else if (charac.getUuid().toString().equals(notti_charac2)) {

                    System.out.println(ByteUtils.byteArrayToStringMessage("test2",charac.getValue(),'|'));

                }
            }

            @Override
            public void onCharacteristicWriteReceived(BluetoothGattCharacteristic charac) {

                if (charac.getUuid().toString().equals(notti_charac)) {

                    System.out.println(ByteUtils.byteArrayToStringMessage("test",charac.getValue(),'|'));

                }
                else if (charac.getUuid().toString().equals(notti_charac2)) {

                    System.out.println(ByteUtils.byteArrayToStringMessage("test2",charac.getValue(),'|'));

                }
            }
        });
    }

    @Override
    public void init() {

        System.out.println("initializing Notti");

        conn.enableDisableNotification(UUID.fromString(notti_service), UUID.fromString(notti_charac), true);
        conn.enableDisableNotification(UUID.fromString(notti_service), UUID.fromString(notti_charac2), true);

        System.out.println(Color.red(-1) + " et " + Color.green(-1) + " et " + Color.blue(-1));

        System.out.println(Color.red((byte) 0xff000000) + " et " + Color.green((byte) 0xff000000) + " et " + Color.blue((byte) 0xff000000));

        getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte)0xFF, (byte)0x00, (byte)0x00});
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte)0x00, (byte)0x00, (byte)0x00});


        for (int i = 0; i  < initListenerList.size();i++){
            initListenerList.get(i).onInit();
        }
    }

    /**
     * switch led state
     *
     * @param state
     */
    @Override
    public void setOnOff(boolean state) {

        if (state)
            getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        else
            getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) 0x00, (byte) 0x00, (byte) 0x00});

    }

    @Override
    public void setRGBColor(int red, int green, int blue) {

        getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte)red, (byte)green, (byte)blue});

    }

    @Override
    public void setLuminosityForColor(int value,int red,int green,int blue) {

        if (value>=0 && value<=100) {

            value=100-value;

            getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) ((1f-value/100f)* red), (byte) ((1f-value/100f)*green), (byte) ((1f-value/100f)*blue)});

        }
        else{
            Log.e(TAG, "Error luminosity must be set between 0 and 100");
        }
    }

    @Override
    public boolean isInit() {
        return init;
    }

    @Override
    public void addInitListener(IDeviceInitListener listener) {
        initListenerList.add(listener);
    }
}