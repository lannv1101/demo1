 $('.button-add').click(function () {
     $('.alert').removeClass("hide");
     $('.alert').addClass("show");
     $('.alert').addClass("showAlert");
     setTimeout(function () {
         $('.alert').removeClass("show");
         $('.alert').addClass("hide");
     }, 5000)
     // hide alert automatically after 5sec
 });
 $('.close-btn').click(function () {
     $('.alert').removeClass("show");
     $('.alert').addClass("hide");
 });