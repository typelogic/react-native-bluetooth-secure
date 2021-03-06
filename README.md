## Description
A secure & pairless Bluetooth transport layer with Authenticated Encryption. 

## Notes

[SecureSend](https://github.com/typelogic/securesend) is a test React Native application that uses this library.

## Installation

```
npm install --save react-native-bluetooth-secure
```

In your main `App.tsx` file:

```javascript
import BluetoothApi from "react-native-bluetooth-secure"
```

## API Description

The app that *displays the QR code* shall generate the ephemeral connection parameters:

```javascript
var params = BluetoothApi.getConnectionParameters()
console.log(params)

// Use any out-of-band mechanism to communicate the value of params to the other device.
// For example, you can use a QR code generator library to display params.
// Or the two communicating parties already have a pre-shared value of params.
```
The `params` looks like:

```javascript
{
  "cid": "1ejpu",
  "pk": "819176777955C098B78BAF949084A4484AEC5A769CED2307D59E46DC85A0F758"
}
```

The app that *scans the QR code* shall set its connection parameters:

```javascript
// Use any out-of-band mechanism to get the value of params of the other device.
// For example, you can use a QR code scanning library to scan the QR code if the receiver is using QR code to communicate this value.
// Or the two communicating parties already have a pre-shared value of params.

BluetoothApi.setConnectionParameters(params)
```

Both apps shall setup the connection:

```javascript
BluetoothApi.createConnection("dual", () => {
  // A secure Bluetooth connection is created
  // Anytime, either app may call BluetoothApi.send()
})
```

Once the connection is created, either app can send a string message. 

```javascript
BluetoothApi.send(msg, () => {
  // message sent
})
```

The app can intentionally tear down the connection. 

```javascript
BluetoothApi.destroyConnection()
```

The app must handle *Nearby* events in order to receive **messages**, to monitor transfer **status** of incoming/outgoing messages, or to monitor connection related **events**:

```javascript
BluetoothApi.handleNearbyEvents((event) => {
    console.log(event.type)
    switch (event.type) {
    case "msg":
    this.setState({
      msg: event.data,
    });
    break;

    case "onDisconnected":
    console.log("onDisconnected:" + event.data)
    this.setState({
      msg: "",
    });
    break;

    case "transferupdate":
    console.log("transferupdate:" + event.data)
    break;

    default:
    break;
  }

})
```

The app can print native debug **logs** by:

```javascript
BluetoothApi.handleLogEvents((event) => {
  console.log(event.log)
})
```

This secure Bluetooth communication library needs to be used with another mechanism, for example a QR code, for the [cryptographic](https://doc.libsodium.org/) public key exchange. The public key is used to secure the payload with [Authenticated Encryption](https://en.wikipedia.org/wiki/Authenticated_encryption).
