# Notti Testing Android app #

<i>Last update on 05/10/2015</i>

Notti is a small bluetooth led light with built in battery made by Witti

<h3>Description</h3>

Android application to manage your Notti bluetooth device from BLE gatt interface :

* set ON/OFF (0x000000 to 0xFFFFFF)
* set RGB color
* set color intensity

Characteristics on Notti cant be read on current device firmware. Previous state should be memorized to maintain statefull processing.

<hr/>

<h3>Build</h3>

* Build with Android Studio

* Compatible from API lvl 17+

``gradlew clean build``

<hr/>

<h3>External Lib</h3>

* Color picker by Lars Werkman : https://github.com/LarsWerkman/HoloColorPicker

<hr/>

Device specification : http://wittidesign.com/en/notti/

![screenshot](https://raw.github.com/akinaru/notti-bluetooth-android/master/screenshot.png)

![screenshot](https://raw.github.com/akinaru/notti-bluetooth-android/master/device.png)