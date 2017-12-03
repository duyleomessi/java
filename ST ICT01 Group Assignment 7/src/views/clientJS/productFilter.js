function getSort(value){
  document.getElementById(`searchSuggest1`).innerHTML = ''
  document.getElementById(`searchSuggest2`).innerHTML = ''
  document.getElementById(`searchSuggest3`).innerHTML = ''
  document.getElementById(`searchSuggest4`).innerHTML = ''
  document.getElementById(`searchSuggest5`).innerHTML = ''

  var url = new URL(window.location.href)
  var name = url.searchParams.get("name")
  if (name === null){
    window.open("./?sort=" + value, "_self")
  } else {
    window.open("./?name=" + name + "&sort=" + value, "_self")
  }
}

var options = document.getElementById('orderBy').options
for (let i = 0; i < options.length; i++) {
  if (options[i].value === '<%= sortType %>') {
    options[i].selected = 'selected'
  }
}