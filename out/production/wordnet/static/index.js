/**
 * Created by chien on 28/02/2018.
 */

function find() {
    var word = $('#word').val();
    console.log(word);
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
                var subStr = '';
                var subLen = data[i].words.length;
                for (var j = 0; j < subLen; j++) {
                    data[i].words[j] = data[i].words[j].split('_').join(' ');
                    subStr +=
                        (word.toLowerCase() == data[i].words[j].toLowerCase() ? data[i].words[j] : '<button class="redirect">' + data[i].words[j] + '</button>')
                        + (j == subLen - 1 ? '' : ', ');
                }
                str += '<li>' +
                    '\(' + data[i].resultId + '\)' +
                    '\t' + subStr +
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