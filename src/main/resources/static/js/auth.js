$(document).ready(function () {
    $("#auth").click(function () {
        location.href = $(this).data("url");
    });
});