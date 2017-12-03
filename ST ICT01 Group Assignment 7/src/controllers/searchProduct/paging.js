// (Single responsibility principle)
// paging.js module is used to divide list of products into pages 

// Open/closed principle
// OPEN: sorting can be extended with new maximum number of products per page
// CLOSE: new max can be applied without modifying the code

// Liskov substitution principle
// not count in this case

// Interface segregation principle
// not count in this case

// Dependency Inversion principle
// Depend on both

// paging rule (max products on a page)
max = 10;

// get part of products corresponding to page #
function paging (page, products) {
  return products.slice(max * page, max * page + max);
}
// get maxPage of prodcuts
function getMaxPage (products) {
  return (products.length % max === 0) ? (products.length / max) : ((products.length - (products.length % max))/max + 1);
}

// public paging, and getMaxpage to be called by Controller
module.exports = {
  paging,
  getMaxPage
}
