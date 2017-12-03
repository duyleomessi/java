let csv = require('fast-csv');
let fs = require('fs');

function saveOrder(order, callback) {
  let result = [];

  csv.fromPath('./src/static/order.csv')
  .on('data', (data) => {
    result.push(data);
  })
  .on('end', () => {
    result.push(order);
    let ws = fs.createWriteStream("./src/static/order.csv");
    csv
    .write(result, {headers: true})
    .pipe(ws);
    callback();
  })
}

module.exports = {
  saveOrder
}
