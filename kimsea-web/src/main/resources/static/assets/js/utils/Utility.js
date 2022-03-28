const Utility = /** @class */ (function () {

    function Utility() {
    }

    Utility.prototype.htmlAvator = function(user, text) {
        return `<span class="text-info">
            <img class="avatar avatar-5xl rounded-circle border border-500 shadow-sm mr-3" src="` + user.profileImgUrl + `" alt="" />`
            + '<br/>'
            + '<span class="fs-2 font-weight-bold mt-2 mb-2">' + text + '</span>'
            + `</span>`;
    }

    Utility.prototype.getAssetRouteType = function (path) {
        if (path.indexOf("/assets/") == 0) return "ASSETS";
        else if (path.indexOf("/myassets/") == 0) return "MYASSETS";
        else if (path.indexOf("/owner/") == 0) return "OWNER";
        else if (path.indexOf("/creator/") == 0) return "CREATOR";
        else null;
    }

    Utility.prototype.scrolledLoad = function(callback) {
        $(window).scroll(function(e){
            e.preventDefault();
            e.stopPropagation();
            const scrollTop = $(window).scrollTop();
            const innerHeight = $(window).height();
            const scrollHeight = document.documentElement.scrollHeight;

            if(scrollTop + innerHeight + 1 >= scrollHeight) {
                callback();
            }
        });
    }

    Utility.prototype.scrolledLoadById = function(id, callback) {
        $("#" + id).scroll(function(e){
            e.preventDefault();
            e.stopPropagation();
            const scrollTop = $("#"+id).scrollTop();
            const innerHeight = $("#"+id).height();
            const scrollHeight = document.getElementById(id).scrollHeight;
            //console.log(scrollTop + " , " + innerHeight + " , " + scrollHeight);
            if(scrollTop + innerHeight + 1 >= scrollHeight) {
                callback();
            }
        });
    }

    Utility.prototype.scrollableResultFetchById = function($this, result, target, isNext, pageNo, isAppend) {
        if (result.data.length <= 0) {
            isNext = false;
            return [target, isNext, pageNo];
        } else {
            pageNo++;
            isNext = true;

            if (isAppend == false) {
                target = result.data;
            } else {
                if (target[target.length-1].id == result.data[0].id) return [target, isNext, pageNo];
                target = target.concat(result.data);
            }
            return [target, isNext, pageNo];
        }
    }

    Utility.prototype.scrollableResultFetch = function($this, result, target, isAppend) {
        if (result.data.length <= 0) {
            $this.isNext = false;
            return target;
        } else {
            $this.pageNo++;
            $this.isNext = true;

            if (isAppend == false) {
                target = result.data;
            } else {
                if (target[target.length-1].id == result.data[0].id) return target;
                target = target.concat(result.data);
            }
            return target;
        }
    }


    Utility.prototype.reload = function($this, urlInfo = null) {
       if (urlInfo == null) {
           $this.$router.go($this.$router.currentRoute);
       } else {
           $this.$router.push(urlInfo);
       }
    }

    Utility.prototype.styleLoadingDiv = function(isLoading) {
        if (isLoading == true) return "wrap-loading"; else "wrap-loading display-none";
    }

    Utility.prototype.resizeImage = function(callback, srcId, MAX_WIDTH = 400, MAX_HEIGHT = 400) {
        if (window.File && window.FileReader && window.FileList && window.Blob) {
            var filesToUploads = document.getElementById(srcId).files;
            var file = filesToUploads[0];
            if (file) {

                var reader = new FileReader();
                // Set the image once loaded into file reader
                reader.onload = function (e) {

                    var img = document.createElement("img");
                    img.src = e.target.result;

                    var canvas = document.createElement("canvas");
                    var ctx = canvas.getContext("2d");
                    ctx.drawImage(img, 0, 0);

                    var width = img.width;
                    var height = img.height;

                    if (width > height) {
                        if (width > MAX_WIDTH) {
                            height *= MAX_WIDTH / width;
                            width = MAX_WIDTH;
                        }
                    } else {
                        if (height > MAX_HEIGHT) {
                            width *= MAX_HEIGHT / height;
                            height = MAX_HEIGHT;
                        }
                    }
                    canvas.width = width;
                    canvas.height = height;
                    var ctx = canvas.getContext("2d");
                    ctx.drawImage(img, 0, 0, width, height);

                    canvas.toBlob(function (blob) {
                        var resizedFile = new File([blob], 'resized_' + file.name, file);
                        callback(resizedFile);
                    });
                }
                reader.readAsDataURL(file);
            }
        } else {
            alert('The File APIs are not fully supported in this browser.');
        }
    }

    Utility.prototype.resizeImageWithDisplay = function(callback, srcId, destId, MAX_WIDTH = 400, MAX_HEIGHT = 400) {
        if (window.File && window.FileReader && window.FileList && window.Blob) {
            var filesToUploads = document.getElementById(srcId).files;
            var file = filesToUploads[0];
            if (file) {

                var reader = new FileReader();
                // Set the image once loaded into file reader
                reader.onload = function (e) {

                    var img = document.createElement("img");
                    img.src = e.target.result;

                    var canvas = document.createElement("canvas");
                    var ctx = canvas.getContext("2d");
                    ctx.drawImage(img, 0, 0);

                    var width = img.width;
                    var height = img.height;

                    if (width > height) {
                        if (width > MAX_WIDTH) {
                            height *= MAX_WIDTH / width;
                            width = MAX_WIDTH;
                        }
                    } else {
                        if (height > MAX_HEIGHT) {
                            width *= MAX_HEIGHT / height;
                            height = MAX_HEIGHT;
                        }
                    }
                    canvas.width = width;
                    canvas.height = height;
                    var ctx = canvas.getContext("2d");
                    ctx.drawImage(img, 0, 0, width, height);
                    dataurl = canvas.toDataURL(file.type);
                    document.getElementById(destId).src = dataurl;

                    canvas.toBlob(function (blob) {
                        var resizedFile = new File([blob], 'resized_' + file.name, file);
                        callback(resizedFile);
                    });
                }
                reader.readAsDataURL(file);
            }
        } else {
            alert('The File APIs are not fully supported in this browser.');
        }
    }

    Utility.prototype.onImageDeleteClick = function(index, e) {
        const file = document.getElementById('image-file' + index);
        file.value = '';

        const output = document.getElementById('previewImg' + index + 'Id');
        output.src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfbhJFBdWHMrQVXoMvKA-SC5alUZmRh7A0c43o6Q317rp-ZsD2pw&s";
        output.onload = function() {
            output.onload = null;
        };
    }

    Utility.prototype.onAddImageClick = function(index, e) {
        e.preventDefault();
        $("#attach-file" + index).click();
    }

    Utility.prototype.onAddImageChange = function(index, e) {
        if(Math.round(e.target.files[0].size) > 20 * (1024*1024)){ /*10MB*/
            utility.errorAlert("에러", "업로드 가능용량은 20MB 이하입니다.");
            const file = document.getElementById('image-file' + index);
            file.value = '';
            return false;
        }

        if (e.target.files[0].type.indexOf("image/") != 0) {
            const output = document.getElementById('previewImg' + index + 'Id');
            output.src = "./assets/img/mediadocument.png";
            return;
        }

        const reader = new FileReader();
        reader.onload = function(){
            const output = document.getElementById('previewImg' + index + 'Id');
            output.src = reader.result;
            output.onload = function() {
            };
        };
        reader.readAsDataURL(e.target.files[0]);
    }

    Utility.prototype.stockWithMarker = function(percent, value, isPlus = false,  isMinus = false) {
        if (percent > 0) {
            if (isPlus == true) {
                return "+" + utility.toComma(value);
            } else
                return value;
        } else {
            if (isMinus == true) {
                return "-" + utility.toComma(value);
            } else
                return utility.toComma(value);
        }
    }

    Utility.prototype.stockTextStyle = function(value) {
        if (value > 0) {
            return "text-danger";
        } else if (value < 0) {
            return "text-primary";
        } else {
            return "text-white";
        }
    }

    Utility.prototype.desc = function(category) {
        return category.desc_kr;
    }

    Utility.prototype.category = function(category) {
        return category.title_kr;
    }

    Utility.prototype.ellipse = function(text, len) {
        if (text.length >= len) {
            return text.substr(0, len) + "..";
        }
        return text;
    }

    Utility.prototype.toComma = function(x) {
        var parts = x.toString().split(".");
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        return parts.join(".");
    }

    Utility.prototype.settingRating = function(element, rating) {
        var ratingJS = raterJs( {
            starSize:20,
            step:0.5,
            element:element
        });
        ratingJS.setRating(rating);
    }

    Utility.prototype.settingRatings = function(rating, friendly, convenience, clean, feel) {
        var ratingRatingJS = raterJs( {
            starSize:20,
            step:0.5,
            element:document.querySelector("#rating-rater-step")
        });
        ratingRatingJS.setRating(rating);

        var friendlyRatingJS = raterJs( {
            starSize:20,
            step:0.5,
            element:document.querySelector("#friendly-rater-step")
        });
        friendlyRatingJS.setRating(friendly);

        var cleanRatingJS = raterJs( {
            starSize:20,
            step:0.5,
            element:document.querySelector("#clean-rater-step")
        });
        cleanRatingJS.setRating(convenience);

        var convenienceRatingJS = raterJs( {
            starSize:20,
            step:0.5,
            element:document.querySelector("#convenience-rater-step")
        });
        convenienceRatingJS.setRating(clean);

        var feelRatingJS = raterJs( {
            starSize:20,
            step:0.5,
            element:document.querySelector("#feel-rater-step")
        });
        feelRatingJS.setRating(feel);
    }

    Utility.prototype.searchParam = function(key) {
        return new URLSearchParams(location.search).get(key);
    };

    Utility.prototype.changedDeviceGoBackUrl = function(url) {
        if( /Android/i.test(navigator.userAgent)) {
            // 안드로이드
            return "hohocamping://back?url=" + url;
        } else if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
            // iOS 아이폰, 아이패드, 아이팟
            return "hohocamping://back?url=" + url;
        } else {
            // 그 외 디바이스
            return url;
        }
    }

    Utility.prototype.changedDeviceNewUrl = function(url) {
        if( /Android/i.test(navigator.userAgent)) {
            // 안드로이드
            return "hohocamping://newpage?url=" + url;
        } else if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
            // iOS 아이폰, 아이패드, 아이팟
            return "hohocamping://newpage?url=" + url;
        } else {
            // 그 외 디바이스
            return url;
        }
    }

    Utility.prototype.changedDeviceUrl = function(url) {
        if( /Android/i.test(navigator.userAgent)) {
            // 안드로이드
            return "hohocamping://page?url=" + url;
        } else if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
            // iOS 아이폰, 아이패드, 아이팟
            return "hohocamping://page?url=" + url;
        } else {
            // 그 외 디바이스
            return url;
        }
    }

    Utility.prototype.getRandomIntInclusive = function(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    Utility.prototype.bounceEffect = function(thing) {
        thing.effect("bounce", {to: {top: -30}}, 800 + utility.getRandomIntInclusive(1,3) * 500, function () {
            thing.clearQueue();
        });
        //thing.effect("highlight", {color:"#a4bbed"}, 300);
    }

    Utility.prototype.stopEffect = function(thing) {
        thing.clearQueue();
    }

    Utility.prototype.changedDeviceImageViewUrl = function(url) {
        if( /Android/i.test(navigator.userAgent)) {
            // 안드로이드
            return "hohocamping://image?url=" + url;
        } else if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
            // iOS 아이폰, 아이패드, 아이팟
            return "hohocamping://image?url=" + url;
        } else {
            // 그 외 디바이스
            return url;
        }
    }

    Utility.prototype.changedDeviceNewImageViewUrl = function(url) {
        if( /Android/i.test(navigator.userAgent)) {
            // 안드로이드
            return "hohocamping://newimage?url=" + url;
        } else if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
            // iOS 아이폰, 아이패드, 아이팟
            return "hohocamping://newimage?url=" + url;
        } else {
            // 그 외 디바이스
            return url;
        }
    }

    Utility.prototype.isValidDate = function(dateString) {
        var regEx = /^\d{4}-\d{2}-\d{2}$/;
        return dateString.match(regEx) != null;
    };

    Utility.prototype.loginReqAlert = function() {
        swal({
            title: "로그인이 필요합니다.",
            icon: 'error',
            showConfirmButton: true,
            showCancelButton: true,
            allowOutsideClick: false,
            confirmButtonColor: "#FEE500",
            confirmButtonText: '<span class="text-black"><span class="fas fa-comment mr-2"></span>카카오 로그인</span>',
            cancelButtonText: '취소',
            preConfirm: function () {
                utility.writeCookie("referer", window.location.pathname);
                window.location = "/oauth2/authorization/kakao";
            }
        });
    };



    Utility.prototype.setLang = function (value) {
        utility.writeCookie("Language", value, 100);
    };

    Utility.prototype.getLang = function () {
        var lang = utility.readCookie("Language");
        if (lang == null) {
            utility.setLang('en');
            return "en";
        }
        return lang;
    };

    Utility.prototype.removeCookie = function (name, days = 9999) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + 1000);
            expires = "; expires=" + date.toUTCString();
        }
        var v = "";

        document.cookie = name + "=" + (v || "")  + expires + "; path=/";
    };

    Utility.prototype.writeCookie = function (name, value, days = 9999, isStr=false) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days*24*60*60*1000));
            expires = "; expires=" + date.toUTCString();
        }
        var v = encodeURIComponent(value);
        if (isStr) {
            //v = v.replace(/,/g, '|').replace(/"/g, "'");
        }

        document.cookie = name + "=" + (v || "")  + expires + "; path=/";
    };

    Utility.prototype.readCookie = function(name, isStr=false) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) {
                if (isStr) {
                    return decodeURIComponent(c.substring(nameEQ.length,c.length));
                } else {
                    return decodeURIComponent(c.substring(nameEQ.length,c.length));
                }
            }
        }
        return null;
    };

    Utility.prototype.getPostSearchType = function() {
        return utility.readCookie("POST_SEARCH_TYPE") != null ? utility.readCookie("POST_SEARCH_TYPE") : 'RECENT_DATE_TYPE'
    }

    Utility.prototype.setPostSearchType = function(type) {
        utility.writeCookie("POST_SEARCH_TYPE", type, 9999999);
        return type;
    }

    Utility.prototype.escapeWhitespace = function (str)
    {
        return str.toString().replace(/\s/g, "")
    };

    Utility.prototype.escapeEOF = function (str)
    {
        return str.toString().replace(/\n/g, "<br/>")
    }

    Utility.prototype.validateEmail = function (email)
    {
        email = utils.escapeWhitespace(email);
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    };

    Utility.prototype.gup = function (name, url) {
        if (!url) url = location.href;
        name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
        var regexS = "[\\?&]"+name+"=([^&#]*)";
        var regex = new RegExp( regexS );
        var results = regex.exec( url );
        return results == null ? null : results[1];
    };

    Utility.prototype.validatePassword = function (val) {
        if (val == null || val.length < 8) {
            return false;
        }

        var dressedVal = utils.escapeWhitespace(val);
        if (dressedVal == null || dressedVal.length < 8) {
            return false;
        }

        var pwdReg = /^[A-za-z0-9]{8,15}/g;
        if( !pwdReg.test(dressedVal)) {
            return false;
        }
        return true;
    };

    Utility.prototype.isEmpty = function (val) {
        if (val == null || val.length <= 0) {
            return true;
        }

        var dressedVal = utils.escapeWhitespace(val);
        if (dressedVal == null || dressedVal.length <= 0) {
            return true;
        }
        return false;
    };


    Utility.prototype.showInfoAlert = function (content) {
        $.Notification.notify("info",'top right', "INFORMATION", content);
    };

    Utility.prototype.showErrorAlert = function (content) {
        $.Notification.notify("error",'top right', "(>_<) ERROR", content);
    };

    Utility.prototype.successToast = function (title, text) {
        toastr.success(text, title);
    };

    Utility.prototype.walletReloadToast = function (title, text) {
        $.Notification.notify('success','top right',title, text)
    };

    Utility.prototype.successAlert = function (title, text, doneFunction) {
        Swal.fire({
            title: title,
            html: text,
            type: "success",
            /*customClass: 'alert-popup-show',*/
            showCancelButton: false,
            confirmButtonClass: 'btn-success waves-effect waves-light',
            confirmButtonText: '닫기',
            allowOutsideClick: false,
            preConfirm: doneFunction
        });
    };

    Utility.prototype.errorCodeAlert = function (title, code, text, doneFunction) {
        Swal.fire({
            title: title,
            html: "[" + code + "] " + text,
            type: "error",
            showCancelButton: false,
            confirmButtonClass: 'btn-success waves-effect waves-light',
            confirmButtonText: '닫기',
            allowOutsideClick: false,
            preConfirm: doneFunction
        });
    };

    Utility.prototype.errorAlert = function (title, text, doneFunction) {
        Swal.fire({
            title: title,
            html: text,
            type: "error",
            showCancelButton: false,
            confirmButtonClass: 'btn-success waves-effect waves-light',
            confirmButtonText: '닫기',
            allowOutsideClick: false,
            preConfirm: doneFunction
        });
    };

    Utility.prototype.errorAlert2 = function (title, text, doneFunction) {
        Swal.fire({
            title: title,
            html: text,
            type: "error",
            showCancelButton: false,
            confirmButtonClass: 'btn-success waves-effect waves-light',
            confirmButtonText: '닫기',
            allowOutsideClick: false,
            preConfirm: doneFunction
        });
    };


    Utility.prototype.componentPopup = function (title, component, data, popupCss = "popup_show") {
       Swal.fire({
            title: '<div class="mt-3">' + title + '</div>',
            html: component.template,
            customClass: {
                popup: popupCss,
                htmlContainer : 'custom-swal2-html-container',
            },
            showCancelButton: false,
            allowOutsideClick: false,
            showConfirmButton: false,
            didOpen: () => {
                component.data = data;
                new Vue(component);
            }
        });
    };

    Utility.prototype.attachEditor = function(id, _callback) {

        var colors = new Object();
        colors["#FFFFFF"] = 'White';
        colors["#000000"] = 'Black';

        const editor = $('#' + id).richText({
            bold: true,
            italic: true,
            underline: true,
            leftAlign: false,
            centerAlign: false,
            rightAlign: false,
            justify: false,
            ol: false,
            ul: false,
            heading: false,
            fonts: true,
            fontColor: true,
            fontSize: true,
            imageUpload:false,
            fileUpload:false,
            urls:false,
            table:false,
            removeStyles:false,
            code:false,
            embed:false,
            videoEmbed:false,
            colors: colors,
            maxlength: 0,
            height: 240,
            callback: _callback
        });
        return editor;
    };

    Utility.prototype.drawStars = function(rating) {
        var a = parseInt(rating / 1);
        var b = parseFloat(rating - a);

        var content = "";
        for (var i=0;i<a;i++) {
            content += '<span class="fas fa-star text-yellow"></span>';
        }

        if (b >= 0.5) {
            a++;
            content += '<span class="fas fa-star-half-alt text-yellow"></span>';
        }

        for (var j=0;j<(5 - a);j++) {
            content += '<span class="far fa-star text-yellow"></span>';
        }

        return content;
    };

    Utility.prototype.readableTags = function(tags) {
        return '#' + (tags.split(',').join(', #'));
    };

    Utility.prototype.readableDateString = function(date) {
        var currentDate = new Date();
        var gapMin = ((currentDate - new Date(date)) / 1000 / 60).toFixed(0); //분

        if (gapMin <= 0) {
            return "방금전";
        } else {
            var gapHour = (gapMin / 60).toFixed(1); //시
            if (gapHour >= 0 && gapHour < 1) {
                return parseInt(gapMin) + "분전";
            } else if (gapHour >= 1 && gapHour <= 23) {
                return parseInt(gapHour) + "시간전";
            } else {
                var gapDay = (gapHour / 24).toFixed(1); //일
                if (gapDay >= 0 && gapDay < 31) {
                    return parseInt(gapDay + 1) + "일전";
                } else {
                    var gapMonth = (gapDay / 31).toFixed(1); //월
                    return parseInt(gapMonth + 1) + "개월전";
                }
            }
        }
    };

    Utility.prototype.isNewContent = function(date) {
        var currentDate = new Date();
        var gapMin = ((currentDate - new Date(date)) / 1000 / 60).toFixed(0); //분

        if (gapMin <= 59) {
            return true;
        } else {
            var gapHour = (gapMin / 60).toFixed(1); //시
            if (gapHour > 0 && gapHour <= 1) {
                return true;
            } else if (gapHour > 1 && gapHour <= 23) {
                return true;
            } else {
                var gapDay = (gapHour / 24).toFixed(1); //일
                if (gapDay >= 0 && gapDay < 3) {
                    return false;
                } else {
                    var gapMonth = (gapDay / 31).toFixed(1); //월
                    return false;
                }
            }
        }
    };

    Utility.prototype.extendMethods = function(vueComponent) {
        vueComponent.methods.isNew = function(date) {
            return utility.isNewContent(date);
        }

        vueComponent.methods.readableDateString = function(date) {
            return utility.readableDateString(date);
        }

        return vueComponent.methods;
    }
    return Utility;
}());

const utility = new Utility();