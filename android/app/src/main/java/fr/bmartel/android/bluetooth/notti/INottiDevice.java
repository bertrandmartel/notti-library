package fr.bmartel.android.bluetooth.notti;

/**
 * Generic interface for Notti device
 */
public interface INottiDevice {

    /**
     * switch notti ON/OFF
     *
     * @param ledState
     */
    public void setOnOff(boolean ledState);

    /**
     * Set RGB color
     *
     * @param red
     * @param green
     * @param blue
     */
    public void setRGBColor(int red,int green,int blue);

    /**
     * Set intensity for a specific color
     *
     * @param value
     *      0-100 %
     * @param red
     * @param green
     * @param blue
     */
    public void setLuminosityForColor(int value,int red,int green,int blue);
}

