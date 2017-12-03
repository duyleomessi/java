var dgram =  require('dgram');

const socket = dgram.createSocket('udp4');
const message = new Buffer('Hello world');

function send() {
    socket.send(message, 0, message.length, 59999, 'localhost'); // (msg, starting offset, Buffer, port, host)
}

setInterval(send, 5000);
send();