# react-native-bluetooth-secure

Secure Bluetooth Communication with Authenticated Encryption. This secure Bluetooth communication does not need pairing. The **iOS** is still in progress. 

## Installation

```sh
npm install react-native-bluetooth-secure
```

## Usage

```js
import BluetoothApi from "react-native-bluetooth-secure";
```

For the receiving side:

```js
var params = JSON.parse(BluetoothApi.getConnectionParameters())
console.log(params)

// Use any out-of-band mechanism to communicate the value of params to the transmitting device.
// For example, you can use a QR code generator library to display params by line of sight.
// Or the two communicating parties already have a pre-shared value of params.

BluetoothApi.receive(params.cid, (decryptedMsg) => {
  console.log(decryptedMsg)
})
```

For the transmitting side:

```js
// Use any out-of-band mechanism to get the value of params of the receiving device.
// For example, you can use a QR code scanning library to scan the QR code if the receiver is using QR code to communicate this value.
// Or the two communicating parties already have a pre-shared value of params.

var msg = "Hello, World!"

BluetoothApi.discover(params.cid, (x) => {
  // Encrypt and transmit msg
  BluetoothApi.transmit(msg, params.pk, (x) => {
      console.log("the message has been transmitted")
  })
})
```

This secure Bluetooth communication library needs to be used with another mechanism, for example a QR code, for the [cryptographic](https://doc.libsodium.org/) public key exchange. The public key is used to secure the payload with [Authenticated Encryption](https://en.wikipedia.org/wiki/Authenticated_encryption). 

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
