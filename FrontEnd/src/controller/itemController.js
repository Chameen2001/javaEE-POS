refresh_item_table();
$("#item_btn_Save_Or_Update").attr("disabled", true);

//Add Or Update Function
$("#item_btn_Save_Or_Update").click(item_save);

function item_save() {
  // Get Value From Text Fields
  let item_image = $("#txtItemImg").val();

  let item={
    id: $("#txtItmId").val(),
    name: $("#txtItmName").val(),
    qty: $("#txtItmQty").val(),
    price: $("#txtItmPrice").val()
  };

  if ($("#item_btn_Save_Or_Update").text() == "Save") {
    $.ajax({
      url: "http://localhost:8080/JavEE_POS/item",
      method: "POST",
      contentType:"application/json",
      data:JSON.stringify(item),
      success:function (response) {
        alert(response.message);
        refresh_item_table();
      },
      error:function (error) {
        alert(error.message);
      }
    })
  } else {

    $.ajax({
      url:"http://localhost:8080/JavEE_POS/item",
      method:"PUT",
      contentType: "applicaion/json",
      data:JSON.stringify(item),
      success:function (response) {
        alert(response.message);
        refresh_item_table();
      },
      error:function (error) {
        alert(response.message);
      }
    })
  }

  refresh_item_table();
  refresh_item_cmb();

  let txt_item_id = $("#txtItmId");
  let txt_item_name = $("#txtItmName");
  let txt_item_quantity = $("#txtItmQty");
  let txt_item_price = $("#txtItmPrice");
  let txt_item_image = $("#txtItemImg");

  // clear Text Field After Adding Row

  txt_item_id.val("");
  txt_item_name.val("");
  txt_item_quantity.val("");
  txt_item_price.val("");
  txt_item_image.val("");
}

function refresh_item_table() {

  $.ajax({
    url:"http://localhost:8080/JavEE_POS/item",
    method:"GET",
    success:function (response) {
      let items = response.data;
      $("#item_tbl_body").empty();
      for (let index = 0; index < items.length; index++) {
        let newRaw = `<tr><th scope="row">${items[
            index
            ].id}</th><td>${items[index].name}</td><td>${items[
            index
            ].qty}</td><td>${items[
            index
            ].price}</td><td><button type="button" class="btn btn-danger ms-3">Delete</button></td></tr>`;

        // Add New Row
        $("#item_tbl_body").append(newRaw);

        $("#item_tbl_body>tr").off();

        // Row Click Function
        $("#item_tbl_body>tr").click(function () {
          // Add Data To Text Field
          txt_item_id.val($(this).children(":eq(0)").text());
          txt_item_name.val($(this).children(":eq(1)").text());
          txt_item_quantity.val($(this).children(":eq(2)").text());
          txt_item_price.val($(this).children(":eq(3)").text());

          change_button_to_update();
        });

        $("#item_tbl_body>tr>td:nth-child(5)").off();

        // Delete A Row
        $("#item_tbl_body>tr>td:nth-child(5)").on("click", function () {
          delete_item($(this).parent().children("th").text());
        });
      }
    },
    error: function (error) {

    }
  });

}

function delete_item(itemId) {
  console.log("delete pressed")
  $.ajax({
    url: "http://localhost:8080/JavEE_POS/item?itemId=" + itemId,
    method: "DELETE",
    success: function (response) {
      alert(response.message);
      refresh_item_table();
    },
    error: function (error) {
      alert(error.message);
    }
  })
}

let txt_item_id = $("#txtItmId");
let txt_item_name = $("#txtItmName");
let txt_item_quantity = $("#txtItmQty");
let txt_item_price = $("#txtItmPrice");
let txt_item_image = $("#txtItemImg");

$("#item_tbl_body>tr").click(function () {
  // Add Data To Text Field
  txt_item_id.val($(this).children(":eq(0)").text());
  txt_item_name.val($(this).children(":eq(1)").text());
  txt_item_quantity.val($(this).children(":eq(2)").text());
  txt_item_price.val($(this).children(":eq(3)").text());
  change_button_to_update();
});

function change_button_to_update() {
  $("#item_btn_Save_Or_Update").text("Update");
}

// ---------------------------------------------------- keyPress Event------------------------------------------

$("#txtItmId").on("keyup", function (event) {
  check_item_collision(
    /^I[0-9]{3}$/,
    /^[A-Z][A-z]+(\s[A-Z][A-z]+)?$/,
    "#txtItmId",
    "#txtItmName"
  );
  check_button_state_in_item($("#txtItmId").val());
});

function check_button_state_in_item(value) {
  let items = $("#tblBody").children("tr").children("th");
  if (items.length == 0) {
    $("#item_btn_Save_Or_Update").text("Save");
  } else {

    for (let index = 0; index < items.length; index++) {
      if (items.eq(index).text() == value) {
        change_button_to_update();
        break;
      } else {
        $("#item_btn_Save_Or_Update").text("Save");
      }
    }
  }
}

$("#txtItmName").on("keyup", function (event) {
  check_item_collision(
    /^[A-Z][A-z]+(\s[A-Z][A-z]+)?$/,
    /^[0-9]{0,4}$/,
    "#txtItmName",
    "#txtItmQty"
  );
});

$("#txtItmQty").on("keyup", function (event) {
  check_item_collision(/^[0-9]{0,4}$/, /^[0-9]{0,5}$/, "#txtItmQty", "#txtItmPrice");
});

$("#txtItmPrice").on("keyup", function (event) {
  check_item_collision(/^[0-9]{0,5}$/, 0, "#txtItmPrice", 0);
});

//----------------------------------Collision Checking Function----------------------------------------------

function check_item_collision(regExPara, nextReg, thisField, nextField) {
  var regEx = regExPara;

  if (regEx.test($(thisField).val())) {
    $(thisField).css("border-color", "green");
    if (nextField != 0) {
      checkNext(nextReg, nextField);
      $("#error").text("");
    }
  } else {
    $(thisField).css("border-color", "red");
    $("#btnSaveOrUpdate").attr("disabled", true);
  }

  if (event.key == "Enter") {
    if (nextField != 0) {
      if ($(thisField).css("border-color") == "rgb(0, 128, 0)") {
        $(nextField).focus();
        $("#error").text("");
      } else {
        $("#error").text("Invalid");
      }
    } else {
      var ar = ["#txtItmId", "#txtItmName", "#txtItmQty", "#txtItmPrice"];
      let check = 0;
      for (let index = 0; index < ar.length; index++) {
        if ($(ar[index]).css("border-color") != "rgb(0, 128, 0)") {
          check = 1;
          break;
        }
      }
      if (check == 0) {
        item_save();
        for (let index = 0; index < ar.length; index++) {
          $(ar[index]).css("border-color", "red");
        }
        $("#item_btn_Save_Or_Update").attr("disabled", true);
      } else {
        alert("Fill All Details");
      }
    }
  }

  setTimeout(function () {
    check_button_disable_in_item();
  }, 150);
}

function checkNext(nextReg, nextField) {
  if (nextReg.test($(nextField).val())) {
    $(nextField).css("border-color", "green");

    $("#error").text("");
  } else {
    $(nextField).css("border-color", "red");
  }
}

function check_button_disable_in_item() {
  var ar = ["#txtItmId", "#txtItmName", "#txtItmQty", "#txtItmPrice"];
  var check = false;
  for (let index = 0; index < ar.length; index++) {
    if ($(ar[index]).css("border-color") != "rgb(0, 128, 0)") {
      check = true;
      break;
    }
  }
  if (check == false) {
    $("#item_btn_Save_Or_Update").attr("disabled", false);
  }
}
