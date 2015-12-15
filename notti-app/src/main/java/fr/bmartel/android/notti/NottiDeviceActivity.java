/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Bertrand Martel
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.android.notti;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.larswerkman.holocolorpicker.ColorPicker;

import fr.bmartel.android.notti.service.NottiBtService;
import fr.bmartel.android.notti.service.bluetooth.listener.IPushListener;
import fr.bmartel.android.notti.service.bluetooth.notti.INottiDevice;

/**
 * Flower Power device description activity
 *
 * @author Bertrand Martel
 */
public class NottiDeviceActivity extends Activity implements ColorPicker.OnColorChangedListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    /**
     * dialog shown when user tap on icon slot
     */
    private Dialog dialogIconSelect = null;

    private String TAG = NottiDeviceActivity.this.getClass().getName();

    /**
     * BLE wrapper service
     */
    private NottiBtService currentService = null;

    /**
     * onOff state for Notti device
     */
    private boolean state = false;

    /**
     * define if one command has already been sent (we block until command completion reached)
     */
    private boolean waitingForResponse = false;

    /**
     * Dotti device object we can use api from
     */
    private INottiDevice device = null;

    /**
     * device address
     */
    private String address = "";

    /**
     * progress bar
     */
    private ProgressDialog progress;

    /**
     * Called when user click on icon slot
     *
     * @param v icon button view
     */
    public void pictureClick(View v) {

        Log.i(TAG, "picture click");
        String ressouceNme = getResources().getResourceEntryName(v.getId());
        dialogIconSelect.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notti_device);

        Intent intent = getIntent();
        address = intent.getStringExtra("deviceAddr");
        String deviceName = intent.getStringExtra("deviceName");

        setTitle(deviceName.trim() + " [ " + address + " ] ");

        //init toggle button
        ToggleButton onOff = (ToggleButton) findViewById(R.id.ledButton);
        onOff.setOnClickListener(this);

        //init color picker
        ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
        picker.setOnColorChangedListener(this);
        picker.setShowOldCenterColor(false);

        //init seekbar
        SeekBar luminosityBar = (SeekBar) findViewById(R.id.intensity_bar);
        luminosityBar.setOnSeekBarChangeListener(this);

        Intent intentMain = new Intent(this, NottiBtService.class);

        // bind the service to current activity and create it if it didnt exist before
        startService(intentMain);
        bindService(intentMain, mServiceConnection, BIND_AUTO_CREATE);
    }

    /**
     * Manage Bluetooth Service lifecycle
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(final ComponentName name, IBinder service) {

            Log.i(TAG, "Connected to service");

            currentService = ((NottiBtService.LocalBinder) service).getService();

            if (currentService.getConnectionList().get(address) != null) {

                if (currentService.getConnectionList().get(address).getDevice() instanceof INottiDevice) {

                    device = (INottiDevice) currentService.getConnectionList().get(address).getDevice();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister receiver on pause
        //unregisterReceiver(mGattUpdateReceiver);

        try {
            // unregister receiver or you will have strong exception
            unbindService(mServiceConnection);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onColorChanged(final int i) {

        Log.i(TAG, "color changed : " + Color.red(i) + " - " + Color.green(i) + " - " + Color.blue(i));
        if (device != null) {

            if (!waitingForResponse) {

                waitingForResponse = true;

                device.setRGBColor(Color.red(i), Color.green(i), Color.blue(i), new IPushListener() {
                    @Override
                    public void onPushFailure() {
                        waitingForResponse = false;
                    }

                    @Override
                    public void onPushSuccess() {

                        waitingForResponse = false;
                    }
                });
            }
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {

        Log.i(TAG, "intensity changed : " + progress);

        if (device != null) {

            if (!waitingForResponse) {

                waitingForResponse = true;

                final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
                device.setLuminosityForColor(progress, Color.red(picker.getColor()), Color.green(picker.getColor()), Color.blue(picker.getColor()), new IPushListener() {
                    @Override
                    public void onPushFailure() {
                        waitingForResponse = false;
                    }

                    @Override
                    public void onPushSuccess() {

                        waitingForResponse = false;
                    }
                });
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {

        if (device != null) {

            if (!waitingForResponse) {

                waitingForResponse = true;
                device.setOnOff(!state, new IPushListener() {
                    @Override
                    public void onPushFailure() {
                        waitingForResponse = false;
                    }

                    @Override
                    public void onPushSuccess() {

                        waitingForResponse = false;
                    }
                });
                state = !state;

            } else {

                ToggleButton onOff = (ToggleButton) findViewById(R.id.ledButton);
                onOff.toggle();

            }
        }
    }
}