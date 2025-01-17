let addOrUpdate;

window.onload = function () {

    // add event handlers for buttons
    document.querySelector("#GetButton").addEventListener("click", getAllItems);
    document.querySelector("#AddButton").addEventListener("click", addItem);
    document.querySelector("#DeleteButton").addEventListener("click", deleteItem);
    document.querySelector("#UpdateButton").addEventListener("click", updateItem);
    document.querySelector("#DoneButton").addEventListener("click", processForm);
    document.querySelector("#CancelButton").addEventListener("click", cancelAddUpdate);

    // add event handler for selections on the table
    document.querySelector("table").addEventListener("click", handleRowClick);

    loadMenuItemCategories();
    hideUpdatePanel();
};

function clearSelections() {
    let trs = document.querySelectorAll("tr");
    for (let i = 0; i < trs.length; i++) {
        trs[i].classList.remove("highlighted");
    }
}

function handleRowClick(e) {
    //add style to parent of clicked cell
    clearSelections();
    e.target.parentElement.classList.add("highlighted");

    // enable Delete and Update buttons
    document.querySelector("#DeleteButton").removeAttribute("disabled");
    document.querySelector("#UpdateButton").removeAttribute("disabled");
}

function cancelAddUpdate() {
    hideUpdatePanel();
}

// this function handles adds and updates
function processForm() {
    let movieID = document.querySelector("#movieIDInput").value;
    let genre = document.querySelector("#genreSelect").value;
    let title = document.querySelector("#titleInput").value;
    let price = document.querySelector("#priceInput").value;

    let obj = {
        "movieID": movieID,
        "movieCategoryID": genre,
        "title": title,
        "price": price
    };

    let url = "MovieService/movies/" + movieID;
    let method = (addOrUpdate === "add") ? "POST" : "PUT";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete " + addOrUpdate + " request");
            } else {
                alert(addOrUpdate + " request completed successfully");
                getAllItems();
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send(JSON.stringify(obj));
}

function deleteItem() {
    let id = document.querySelector(".highlighted").querySelector("td").innerHTML;
    let url = "MovieService/movies/" + id; // entity, not action
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete delete request");
            } else {
                alert("delete request completed successfully");
                getAllItems();
            }
        }
    };
    xmlhttp.open("DELETE", url, true); // "DELETE" is the action, "url" is the entity
    xmlhttp.send();
}

function addItem() {
    // Show panel, panel handler takes care of the rest
    addOrUpdate = "add";
    resetUpdatePanel();
    showUpdatePanel();
}

function updateItem() {
    addOrUpdate = "update";
    resetUpdatePanel();
    populateUpdatePanelWithSelectedItem();
    showUpdatePanel();
}

function showUpdatePanel() {
    document.getElementById("AddUpdatePanel").classList.remove("hidden");
}

function hideUpdatePanel() {
    document.getElementById("AddUpdatePanel").classList.add("hidden");
}

function loadMenuItemCategories() {
    let url = "MovieService/genres";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText;
            //console.log(resp);
            if (resp.search("ERROR") >= 0) {
                alert("could not complete request");
                console.log(resp);
            } else {
                initUpdatePanel(resp);
            }
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function initUpdatePanel(text) {
    let g = JSON.parse(text);
    let html = "";
    for (let i = 0; i < g.length; i++) {
        let id = g[i].movieCategoryID;
        let desc = g[i].movieCategoryDescription;
        html += "<option value='" + id + "'>" + desc + "</option>";
    }
    document.querySelector("#genreSelect").innerHTML = html;
    resetUpdatePanel();
}

function resetUpdatePanel() {
    document.querySelector("#movieIDInput").value = "";
    document.querySelectorAll("option")[0].selected = true; // select first one
    document.querySelector("#titleInput").value = "";
    document.querySelector("#priceInput").value = 0;
}

function populateUpdatePanelWithSelectedItem() {
    let tds = document.querySelector(".highlighted").querySelectorAll("td");
    document.querySelector("#movieIDInput").value = tds[0].innerHTML;
    let options = document.querySelectorAll("option");
    for (let i = 0; i < options.length; i++) {
        options[i].selected = options[i].value === tds[1].innerHTML;
    }
    document.querySelector("#titleInput").value = tds[2].innerHTML;
    document.querySelector("#priceInput").value = +tds[3].innerHTML;
}

function getAllItems() {
    let url = "MovieService/movies"; // REST-style: URL refers to an entity or collection, not an action
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText;
            console.log(resp);
            if (resp.search("ERROR") >= 0) {
                alert("could not complete request");
                console.log(resp);
            } else {
                buildTable(xmlhttp.responseText);
                clearSelections();
                resetUpdatePanel();
                hideUpdatePanel();
            }
        }
    };
    xmlhttp.open("GET", url, true); // HTTP verb says what action to take; URL says which item(s) to act upon
    xmlhttp.send();

    // disable Delete and Update buttons
    document.querySelector("#DeleteButton").setAttribute("disabled", "disabled");
    document.querySelector("#UpdateButton").setAttribute("disabled", "disabled");
}

function buildTable(text) {
    let data = JSON.parse(text);
    console.log(data);
    let theTable = document.querySelector("table");
    let html = theTable.querySelector("tr").innerHTML;
    for (let i = 0; i < data.length; i++) {
        let temp = data[i];
        html += "<tr>";
        html += "<td>" + temp.movieID + "</td>";
        html += "<td>" + temp.movieCategoryID + "</td>";
        html += "<td>" + temp.title + "</td>";
        html += "<td>" + temp.price + "</td>";
        html += "</tr>";
    }
    theTable.innerHTML = html;
}


