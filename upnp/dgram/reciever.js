var dgram = require('dgram');

const socket = dgram.createSocket('udp4');

socket.on('message', (message, rinfor) => {
    console.log(`${rinfor.address}: ${message.toString()}`);
});

socket.bind(59999);