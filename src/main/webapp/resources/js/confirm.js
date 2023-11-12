$(document).ready(function(){
  $(".deleteForm").submit(function(e){
    var confirmation = confirm("Уверены, что хотите удалить пользователя?");
    if (!confirmation) {
      e.preventDefault();
    }
  });
});

$(document).ready(function(){
  $(".updateUser").submit(function(e){
    var confirmation = confirm("Уверены, что хотите обновить пользователя?");
    if (!confirmation) {
      e.preventDefault();
    }
  });
});

$(document).ready(function(){
  $(".addUser").submit(function(e){
    var confirmation = confirm("Уверены, что хотите добавить пользователя?");
    if (!confirmation) {
      e.preventDefault();
    }
  });
});

$(document).ready(function(){
  $(".addVisit").submit(function(e){
    var confirmation = confirm("Уверены, что хотите добавить посещение пользователю?");
    if (!confirmation) {
      e.preventDefault();
    }
  });
});