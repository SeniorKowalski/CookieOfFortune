$(document).ready(function () {
//$(".blur").addClass("blur-hidden");
$("#cookie-broken").hide();
$("#fortune").hide();
  $("#cookie").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/fortuneCookies/random-prediction",
      success: function (data) {
        $("#cookie").hide();
        $("#cookie-broken").show();
        $("#fortune").show();

        var fortune = $("<div>").addClass("fortune").text(JSON.stringify(data));
        $("body").append(fortune);

      },
        error: function () {
           alert("Произошла ошибка при выполнении запроса. Попробуйте еще раз.");
        }
    });
  });
});