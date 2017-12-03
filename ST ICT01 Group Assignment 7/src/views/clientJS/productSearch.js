function recommend(value){
  document.getElementById(`searchSuggest1`).innerHTML = ''
  document.getElementById(`searchSuggest2`).innerHTML = ''
  document.getElementById(`searchSuggest3`).innerHTML = ''
  document.getElementById(`searchSuggest4`).innerHTML = ''
  document.getElementById(`searchSuggest5`).innerHTML = ''

  var xmlHttp = new XMLHttpRequest()
  xmlHttp.onreadystatechange = function() {
    if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
      console.log(xmlHttp.responseText)
      let values = JSON.parse(xmlHttp.responseText)
      for (let i = 0; i < 5; i++) {
        if (values[i]) {
          document.getElementById(`searchSuggest${ i + 1 }`).style = "text-decoration: none !important;"
          document.getElementById(`searchSuggest${ i + 1 }`).href = `/product/${ values[i][0] }`
          document.getElementById(`searchSuggest${ i + 1 }`).innerHTML = values[i][1]
        }
      }
    }
  }
  xmlHttp.open('GET', `./api/productSuggestion?query=${ value }`, true)
  xmlHttp.send()
}