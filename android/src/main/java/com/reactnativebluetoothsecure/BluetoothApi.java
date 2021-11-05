/*
 * Copyright (C) 2021 Newlogic Pte. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reactnativebluetoothsecure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Callback;

import org.idpass.smartshare.bluetooth.BluetoothSecure;

public class BluetoothApi extends ReactContextBaseJavaModule {

    private static final String TAG = BluetoothApi.class.getName();
    private BluetoothSecure bluetoothSecure;

    public BluetoothApi(ReactApplicationContext reactContext) {
        super(reactContext);
        bluetoothSecure = new BluetoothSecure();
    }

    @NonNull
    @Override
    public String getName() {
        return "BluetoothApi";
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }

    private void console_log(String msg) {
        WritableMap params = Arguments.createMap();
        params.putString("what", msg);
        sendEvent(getReactApplicationContext(), "TRACELOG", params);
    }

    private void init() {
        Utils.checkPermissions(getCurrentActivity());
        bluetoothSecure.init(getCurrentActivity(), (logmsg) -> {
            console_log(logmsg);
        });
    }

      ///////////////////////////////////
     // BluetoothApi.tsx Javascript APIs
    ///////////////////////////////////
    @ReactMethod
    public void transmit(String message, String pubkey, Callback callback) {
        bluetoothSecure.transmit(message, pubkey, () -> {
            callback.invoke("*** TXRX_SUCCESS ***");
        });
    }

    @ReactMethod
    public void discover(String connectionId, Callback callback) {
        init();
        console_log("startDiscovery:"+connectionId);
        bluetoothSecure.discover(connectionId, () -> {
            callback.invoke("connection established");
        });
    }

    @ReactMethod
    public void receive(String connectionId, Callback callback) {
        init();
        console_log("startAdvertising:"+connectionId);

        bluetoothSecure.receive(connectionId, (m) -> {
            callback.invoke(m);
        });
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getConnectionParameters() {
        String params = bluetoothSecure.getConnectionParameters();
        return params;
    }
}
