# react-native-bluetooth-secure

Secure Bluetooth Communication with Authenticated Encryption. The **iOS** is still in progress.

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

// Use any QR code generator library to display **params** json string as a QR code, and then enter 
// into advertising mode

BluetoothApi.receive(params.cid, (decryptedMsg) => {
  console.log(decryptedMsg)
})
```

For the transmitting side:

```js
// Use any QR code scanning library to scan the QR code of the receiver. And then enter into
// discovery mode

var msg = "Hello, World!"

BluetoothApi.discover(params.cid, (x) => {
  // Encrypt and transmit msg
  BluetoothApi.transmit(msg, params.pk, (x) => {
      console.log("the message has been transmitted")
  })
})
```

This library currently uses a QR code for [cryptographic](https://doc.libsodium.org/) public key exchange and secures the payload with [Authenticated Encryption](https://en.wikipedia.org/wiki/Authenticated_encryption). All keys are ephemeral per QR code.

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
