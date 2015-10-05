package fr.bmartel.android.bluetooth;

/**
 * Generic interface for custom bluetooth manager
 *
 * @author
 *      Bertrand Martel
 */
public interface IBluetoothManagerEventListener {

    /**
     * called when Bluetooth adapter is not enabled on this Android device or is null => you must check for Android SDK support for this Android API
     */
    public void onBluetoothAdapterNotEnabled();

    public void onEndOfScan();

    public void onStartOfScan();
}
