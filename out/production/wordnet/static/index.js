/**
 * Created by chien on 28/02/2018.
 */

function find() {
    var word = $('#word').val();
    word = word.split(/(\s+)/).filter(function(e) { return e.trim().length > 0; });
    word = word.join('_');
    var objectFind = {'word': word.toLowerCase()};
    $.ajax({
        url: 'http://localhost:8080/find',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(objectFind),
        success: function (data) {
            var len = data.length;
            var str = '';
            for (var i = 0; i < len; i++) {
                var subStr = '(' + data[i].synsetId + ') <ul><li>' + data[i].words + ': ';
                subStr += data[i].gloss + '</li>';
                var subLen = data[i].means.length;
                for (var j = 0; j < subLen; j++) {
                    subStr += '<li>' + data[i].means[j] + '</li>';
                }
                subStr += '</ul>';
                str += '<li>' +
                    subStr +
                    '</li>';
            }
            $('#listSynset').append(str);
        }
    });
}

function clear() {
    $('#listSynset').children().remove();
}

$('document').ready(function () {
    $('.search').click(function (e) {
        e.preventDefault();
        clear();
        find();
    });

    $('body').on('click', '.redirect', function (e) {
        e.preventDefault();
        var text = $(this).text();
        $('#word').val(text);
        clear();
        find();
    });

});