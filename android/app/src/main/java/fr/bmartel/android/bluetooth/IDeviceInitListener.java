package fr.bmartel.android.bluetooth;

/**
 * Listener called when bluetooth device is fully initialized and can be displayed/used for end user interface
 *
 * @author  Bertrand Martel
 */
public interface IDeviceInitListener {

    /**
     * called when device is fully initialized
     */
    public void onInit();

}
