//124
refreshTable()
$("#btnSaveOrUpdate").attr("disabled", true);

//Add Or Update Function
$("#btnSaveOrUpdate").click(save);

function save() {
    if ($("#btnSaveOrUpdate").text() == "Save") {
        let customer = {
            id: $("#txtCusId").val(),
            name: $("#txtCusName").val(),
            address: $("#txtCusAddress").val(),
            tel: $("#txtCusTel").val()
        }

        $.ajax({
            url: "http://localhost:8080/JavEE_POS/customer",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(customer),
            success: function (res) {
                console.log(res)
                if (res.status == 200) {
                    refreshTable();
                    alert(res.message);
                }
            },
            error: function (res) {

            }
        })


    } else {
        let customer = {
            id: $("#txtCusId").val(),
            name: $("#txtCusName").val(),
            address: $("#txtCusAddress").val(),
            tel: $("#txtCusTel").val()
        }

        $.ajax({
            url: "http://localhost:8080/JavEE_POS/customer",
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(customer),
            success: function (response) {
                console.log("updated")
                refreshTable()
                alert(response.message)
            },
            error: function (error) {
                alert(error.message)
            }
        })
    }

    refresh_customer_cmb();

    let txtCusId = $("#txtCusId");
    let txtCusName = $("#txtCusName");
    let txtCusAddress = $("#txtCusAddress");
    let txtCusTel = $("#txtCusTel");

    // clear Text Field After Adding Row

    txtCusId.val("");
    txtCusName.val("");
    txtCusAddress.val("");
    txtCusTel.val("");
}

function refreshTable() {
    $.ajax({
        url: "http://localhost:8080/JavEE_POS/customer",
        method: "GET",
        success: function (response) {
            let customers = response.data;

            $("#tblBody").empty();
            for (let index = 0; index < customers.length; index++) {
                let newRaw = `<tr><th scope="row">${customers[
                    index
                    ].id}</th><td>${customers[index].name}</td><td>${customers[
                    index
                    ].address}</td><td>${customers[
                    index
                    ].tel}</td><td><button type="button" class="btn btn-danger ms-3">Delete</button></td></tr>`;

                // Add New Row
                $("#tblBody").append(newRaw);

                $("#tblBody>tr").off();

                // Row Click Function
                $("#tblBody>tr").click(function () {
                    // Add Data To Text Field
                    txtCusId.val($(this).children(":eq(0)").text());
                    txtCusName.val($(this).children(":eq(1)").text());
                    txtCusAddress.val($(this).children(":eq(2)").text());
                    txtCusTel.val($(this).children(":eq(3)").text());
                    set_button_to_update();
                });

                $("#tblBody>tr>td:nth-child(5)").off();

                // Delete A Row
                $("#tblBody>tr>td:nth-child(5)").on("click", function () {
                  delete_customer($(this).parent().children("th").text())
                });
            }
        },
        error: function (error) {

        }
    });
}

function delete_customer(cusId){
    $.ajax({
        url:"http://localhost:8080/JavEE_POS/customer?cusId="+cusId,
        method:"DELETE",
        success:function (response) {
            alert(response.message);
            refreshTable();
        },
        error:function (error) {
            alert(error.message);
        }
    })
}

let txtCusId = $("#txtCusId");
let txtCusName = $("#txtCusName");
let txtCusAddress = $("#txtCusAddress");
let txtCusTel = $("#txtCusTel");

$("#tblBody>tr").click(function () {
    // Add Data To Text Field
    txtCusId.val($(this).children(":eq(0)").text());
    txtCusName.val($(this).children(":eq(1)").text());
    txtCusAddress.val($(this).children(":eq(2)").text());
    txtCusTel.val($(this).children(":eq(3)").text());

    set_button_to_update();
});

function set_button_to_update() {
    $("#btnSaveOrUpdate").text("Update");
}

// ---------------------------------------------------- keyPress Event------------------------------------------

$("#txtCusId").on("keyup", function (event) {
    checkCollision(
        /^(C00-)[0-9]{3}$/,
        /^[A-Z][A-z]+(\s[A-Z][A-z]+)?$/,
        "#txtCusId",
        "#txtCusName"
    );
    check_button_state($("#txtCusId").val());
});

function check_button_state(value) {
    /*$($("#tblBody").children("tr").children("th")[0]).text();*/
    let customers = $("#tblBody").children("tr").children("th");
    if (customers.length === 0) {
        $("#btnSaveOrUpdate").text("Save");
    } else {
        for (let index = 0; index < customers.length; index++) {
            if (customers.eq(index).text() == value) {
                set_button_to_update();
                break;
            } else {
                $("#btnSaveOrUpdate").text("Save");
            }
        }
    }
}

$("#txtCusName").on("keyup", function (event) {
    checkCollision(
        /^[A-Z][A-z]+(\s[A-Z][A-z]+)?$/,
        /[A-z]|[0-9]/,
        "#txtCusName",
        "#txtCusAddress"
    );
});

$("#txtCusAddress").on("keyup", function (event) {
    checkCollision(/[A-z]|[0-9]/, /[0-9]{10}/, "#txtCusAddress", "#txtCusTel");
});

$("#txtCusTel").on("keyup", function (event) {
    checkCollision(/^[0-9]{10}$|^[0-9]{9}$/, 0, "#txtCusTel", 0);
});

//----------------------------------Collision Checking Function----------------------------------------------

function checkCollision(regExPara, nextReg, thisField, nextField) {
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
            var ar = ["#txtCusId", "#txtCusName", "#txtCusAddress", "#txtCusTel"];
            let check = 0;
            for (let index = 0; index < ar.length; index++) {
                if ($(ar[index]).css("border-color") != "rgb(0, 128, 0)") {
                    check = 1;
                    break;
                }
            }
            if (check == 0) {
                save();
                for (let index = 0; index < ar.length; index++) {
                    $(ar[index]).css("border-color", "red");
                }
                $("#btnSaveOrUpdate").attr("disabled", true);
            } else {
                alert("Fill All Details");
            }
        }
    }

    setTimeout(function () {
        checkButtonDisable();
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

function checkButtonDisable() {
    var ar = ["#txtCusId", "#txtCusName", "#txtCusAddress", "#txtCusTel"];
    var check = false;
    for (let index = 0; index < ar.length; index++) {
        if ($(ar[index]).css("border-color") != "rgb(0, 128, 0)") {
            check = true;

            break;
        }
    }
    console.log(check);
    if (check == false) {
        $("#btnSaveOrUpdate").attr("disabled", false);
    }
}

//----------------------------------Collision Checking Function----------------------------------------------

// ---------------------------------------------------- keyPress Event------------------------------------------
