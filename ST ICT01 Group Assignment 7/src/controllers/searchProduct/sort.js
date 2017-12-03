// (Single responsibility principle)
// sort.js module is used to sort product list corresponding to a sort_rule 
// only sorting is callable
// sub-function: according to each sort_rule

// Open/closed principle
// OPEN: sorting can be extended with new sort_rule
// CLOSE: new sort_rule can be applied without modifying the code

// Liskov substitution principle
// not count in this case

// Interface segregation principle
// not count in this case

// Dependency Inversion principle
// function in searchProductController uses interface sorting to interact with concrete sort_rule functions
// -> depend on Abstract.

// rules and their correspoding function
list_rule = [
  {rule: "lowPrice", func : sort_by_low_price},
  {rule: "highPrice", func : sort_by_high_price}
]

// interface: sort products followed a sort_rule
function sorting (sort_rule, products){
  for (i = 0; i< list_rule.length; i++){
    if (list_rule[i].rule === sort_rule)
      products = list_rule[i].func(products)
  }
  return products
}

// function to sort products by Price from low to high
function sort_by_low_price(products){
  return products.sort(function(a, b){
    return parseInt(a[2]) - parseInt(b[2])
  })
}

// function to sort products by Price from high to low
function sort_by_high_price(products){
  return products.sort(function(a, b){
    return parseInt(b[2]) - parseInt(a[2])
  })
}


// public sorting to be called by controller
module.exports = {
  sorting
}
