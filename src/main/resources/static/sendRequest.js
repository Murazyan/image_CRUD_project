function deleteImageByImageNmae(event) {
    var imageName = event.target.querySelector('input').value;
    $.ajax({
        url: "http://localhost:8080/image/",
        data: {
            "imageName": imageName
            // "eventId": document.getElementById("current-eventId").value,
            // "inputValue": document.getElementById("searchByUsername").value
        },
        method: "DELETE"
    })

    
}

$(document).ready(function() {
    var imgPrevue;
    var lastImage;
    $('.my-class').off('click').on('click',function () {
        $(this).prev().click();
        lastImage = $(this).children().val();
        console.log(lastImage,'lastImage')
        imgPrevue = $(this).parents().eq(3).children()[0];
        $($(this).prev()).change(function() {
            console.log($(this))
            readURL($(this));
        });
    })
    function readURL(input) {
        console.log(input, 'input', imgPrevue, 'imgPrevue')
        if ($(input)[0].files && $(input)[0].files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $(imgPrevue).attr('src', e.target.result);
            };
            reader.readAsDataURL($(input)[0].files[0]);
            console.log($(input)[0].files,'$(input)[0].files')
        }
        var form_data = new FormData();
        console.log($(input)[0].files,'$(input)[0].files')
        form_data.append('picture',$(input)[0].files[0]);
        form_data.append('oldPicName',lastImage);
        $.ajax({
            url: "http://localhost:8080/image/addimage",
            method:'POST',
            data:form_data,
            contentType:false,
            cache:false,
            processData:false
        })

        // var imageName = event.target.querySelector('input').value;
        // $(this).last().click();


        // $.ajax({
        //     url: "http://localhost:8080/image/addImage",
        //     data: {
        //         "oldPicName": imageName,
        //         "picture"
        //
        //     },
        //     method: "DELETE"
        // })

    }

})






// $(document).ready(function() {
//
//     $("#but_upload").click(function () {
//
//         var fd = new FormData();
//         var files = $('#file')[0].files[0];
//         fd.append('picture', files);
//
//         $.ajax({
//             url: 'http://localhost:8080/image',
//             type: 'post',
//             data: fd,
//             contentType: false,
//             processData: false,
//             success: function (response) {
//                 if (response != 0) {
//                     $("#img").attr("src", response);
//                     $(".preview img").show(); // Display image element
//                 } else {
//                     alert('file not uploaded');
//                 }
//             },
//         });
//     });
// })