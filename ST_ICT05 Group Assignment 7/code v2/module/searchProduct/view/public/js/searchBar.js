/**
 * Created by samhv on 11/30/16.
 */
$(document).ready(function () {
    var host = location.protocol + "//" + window.location.hostname + (location.port ? ':' + location.port : '');
    var searchType = 1;
    $('#search-input').val("");
    $('#search-type').val("product");

    $('.search-panel .dropdown-menu').find('a').click(function (e) {
        e.preventDefault();
        var param = $(this).attr("href").replace("#", "");
        var concept = $(this).text();
        $('.search-panel span#search_concept').text(concept);
        $('#search-type').val(param);
        if(param === 'product') {
            // product
            searchType = 1;
        } else {
            // category
            searchType = 2;
        }

    });

    $('#search-btn').click(function (e) {
        search();
    });


    $('#search-input').on('keyup', function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });


    var search = function () {
        // when user click login button
        // get user's input
        var search_type = $("#search-type").val();
        var data = $('#search-input').val();
        var url = host + '/api/search/' + search_type + "/" + data;
        // send request
        $.ajax({
            type: "GET",
            url: url,
            data: data,
            success: onSuccess,
            error: onError,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
        });

        // when login Successfully
        function onSuccess(data, status, jqXHR) {
            $('#welcome').remove();
            $('#search-result').empty();

            console.log(data);

            if (data.status == false) {
                $('#search-result').append("<h2>" + data.error + "</h2>");
                return;
            }

            
            var items = data.products;

            for (i = 0; i < items.length; i++) {
                var html = dataToHtmlCode(items[i].name, items[i].category.name);

                $('#search-result').append(html);
            }
            
            
        }

        function onError() {
            console.log("error");
        }
    }

    var dataToHtmlCode = function (name, category) {
        var ret = `
            <div class="card" style="width: 20rem;">
                <img class="card-img-top" style="width: 100px; height:auto;" src="/static/images/default-image.png" alt="Card image cap">
                    <div class="card-block">
                        <h3 class="card-title">` + name + `</h3>
                        <h4 class="card-title">` + category + `</h4>
                        <p class="card-text">Ab nisi amet porro omnis nisi omnis.</p>
                        <p> 100 $ </p>
                    </div>
            </div>`
        return ret;

    }

});