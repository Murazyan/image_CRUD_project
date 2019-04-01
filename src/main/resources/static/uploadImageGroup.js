$(document).ready(function () {
    var namesArr = [];
    $('.group-delete').off('click').on('click', function () {
        var names = $('.required__photo_select--bg-act').children();
        $(names).each(function (index, value) {
            $(value).parent().parent().prev().remove();
            namesArr.push($(value).val())
        })
        console.log('1', namesArr);

        var data = {
            'imageNames': namesArr
        }
        console.log()
        console.log('2', namesArr);
        console.log(JSON.stringify(data), 'JSON.stringify(data)')
        $.ajax({
            url: "http://localhost:8080/image/many",
            method: 'DELETE',
            // data:form_data,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',
            // contentType:true,
            // cache:false,
            // processData:false
        })
    })

    $('.upload-group').off('click').on('click', function () {
        $(this).prev().click();
        var photo = $('.required__photo_select--bg-act').parent().prev();
        var names = $('.required__photo_select--bg-act').children();
        $(names).each(function (index, value) {
            namesArr.push($(value).val())
        })
        $($(this).prev()).change(function () {
            console.log($(this), 'this')
            readURL($(this));
        });
    })
    $('.load-group').off('click').on('click', function () {
        var photo = $('.required__photo_select--bg-act').parent();
        $(photo).each(function (index, value) {
            $(value).next().children().children().children()[1].click();
        })
    })


    function readURL(input) {
        console.log('input', input);
        if ($(input)[0].files && $(input)[0].files) {
            // console.log($(input)[0].files,'$(input)[0].files')

            var reader = new FileReader();
            reader.onload = function (e) {
                // $(imgPrevue).attr('src', e.target.result);
            };
            // reader.readAsDataURL($(input)[0].files);
            // console.log($(input)[0].files,'$(input)[0].files')


        }
        var form_data = new FormData();
        var filesArr = [];

        $($(input)[0].files).each(function (index, value) {
            filesArr.push($(value))
        })
        console.log(filesArr[0])
        form_data.append('newPictures', filesArr[0]);
        form_data.append('oldPicNames', namesArr);
        console.log('newPictures', $(input)[0].files[0], $(input));
        $.ajax({
            url: "http://localhost:8080/image/addImages",
            method: 'POST',
            data: form_data,
            contentType: 'multipart/form-data',
            cache: false,
            processData: false
        })
    }

})
