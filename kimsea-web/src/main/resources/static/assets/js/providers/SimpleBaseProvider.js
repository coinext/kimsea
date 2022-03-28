const SimpleBaseProvider = /** @class */ (function () {

    function SimpleBaseProvider() {
    }

    SimpleBaseProvider.prototype.form = function (uri, $this, callback, formData) {

        var token = $('meta[name="_csrf"]').attr('content');
        var header = $('meta[name="_csrf_header"]').attr('content');
        var lang = utility.getLang();


        $.ajax({
            url: uri
            , type: "POST"
            , enctype: 'multipart/form-data'
            , data: formData
            , processData: false
            , contentType: false
            , cache: false
            , beforeSend: function(xhr) {
                    if (token != undefined)
                        xhr.setRequestHeader(header, token);
                    if (lang != undefined)
                        xhr.setRequestHeader('lang', lang);
            }
            , success: function (result) {
                return callback($this, result);
            }
            , error:function(e){
                console.log(e.responseText);
                return callback($this, JSON.parse(e.responseText));
            }
        });
        return true;
    };

    SimpleBaseProvider.prototype.post = function (uri, $this, callback, params) {
        var token = $('meta[name="_csrf"]').attr('content');
        var header = $('meta[name="_csrf_header"]').attr('content');

        var lang = utility.getLang();

        $.ajax({
            url: uri
            , type: "POST"
            , dataType: 'json'
            , contentType:"application/json; charset=UTF-8"
            , data: JSON.stringify(params)
            , beforeSend: function(xhr) {
                if (token != undefined)
                    xhr.setRequestHeader(header, token);
                if (lang != undefined)
                    xhr.setRequestHeader('lang', lang);
                }
            , success: function (result) {
                return callback($this, result);
            }
            , error:function(e){
                console.log(e.responseText);
                return callback($this, JSON.parse(e.responseText));
            }
        });
        return true;
    };

    SimpleBaseProvider.prototype.get = function (uri, $this, _callback, params) {
        var lang = utility.getLang();

        $.ajax({
            url: uri
            , type: "GET"
            , dataType: 'json'
            , contentType:"application/json; charset=UTF-8"
            , data: JSON.stringify(params)
            , beforeSend: function(xhr) {
                if (lang != undefined)
                    xhr.setRequestHeader('lang', lang);
            }
            , success: function (result) {
                return _callback($this, result);
            }
            , error:function(e){
                console.log(e.responseText);
                return _callback($this, JSON.parse(e.responseText));
            }
        });
        return true;
    };

    return SimpleBaseProvider;
}());