$(document).ready(function () {

    $("#processing-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        processing_ajax_submit();

    });

    $("#statistics-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        statistics_ajax_submit();

    });

    $("#getall-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        getall_ajax_submit();

    });

});

function processing_ajax_submit() {

    $("#btn-processing").prop("disabled", true);

    $.ajax({
        type: "GET",
        url: "/pics/processing",
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-processing").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-processing").prop("disabled", false);

        }
    });
}

function statistics_ajax_submit() {

    $("#btn-statistics").prop("disabled", true);

    $.ajax({
        type: "GET",
        url: "/statistics",
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-statistics").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-statistics").prop("disabled", false);

        }
    });
}
    function getall_ajax_submit() {

        $("#btn-getall").prop("disabled", true);

        $.ajax({
            type: "GET",
            url: "/pics/all",
            cache: false,
            timeout: 600000,
            success: function (data) {

                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);

                console.log("SUCCESS : ", data);
                $("#btn-getall").prop("disabled", false);

            },
            error: function (e) {

                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);

                console.log("ERROR : ", e);
                $("#btn-getall").prop("disabled", false);

            }
        });
}