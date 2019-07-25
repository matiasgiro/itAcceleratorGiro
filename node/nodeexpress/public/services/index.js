var sitePC, paymentPC,latitudPC, longitudPC,radioPC;

function primeraConsulta(site,payment, latitud, longitud, radio) {
    if(sitePC != site){
        sitePC = site;
        return true
    }
    if(paymentPC != payment){
        paymentPC = payment;
        return true
    }
    if(latitudPC != latitud){
        latitudPC = latitud;
        return true
    }
    if(longitudPC != longitud){
        longitudPC = longitud;
        return true
    }
    if(radioPC != radio){
        radioPC = radio;
        return true
    }
    return false;

}

function cargarSites(){

    var select = document.getElementById("sitesSelect");

    var requestConsultarSites = new XMLHttpRequest();

    requestConsultarSites.open("GET","http://localhost:3000/sites", true);

    requestConsultarSites.onload = function () {
        var data = JSON.parse(this.response);
        if(requestConsultarSites.status >= 200 && requestConsultarSites.status < 400){
            var ordenados = data.sort(function (a,b) {
                if(a.name > b.name){
                    return 1;
                }
                if (a.name < b.name) {
                    return -1;
                }
                // a must be equal to b
                return 0
            });
            ordenados.forEach(function (site, index) {
                if(index == 0){
                    cargarPaymentMethods(site.id);

                }
                var option = document.createElement("option");
                option.text = site.name;
                option.value = site.id;
                select.add(option);
            })
        } else{
            var errorMessage = document.createElement("marquee");
            errorMessage.textContent = "Status code igual a " + data.status + " - message" + data.message;
            app.appendChild(errorMessage)
        }
    };

    requestConsultarSites.send()

}
function cargarPaymentMethods(psite){
    var site;
    if(psite != ''){
        site = psite
    }else{
        site = document.getElementById("sitesSelect").value;
    }
    var cod = document.getElementById("paymentSelect");
    var length = cod.options.length;
    var i;
    for (i = 0; i < length; i++) {
        cod.options[i] = null;
    }

    var requestPaymentMethods = new XMLHttpRequest();
    requestPaymentMethods.open("GET","http://localhost:3000/sites/payment_methods?site="+site+"", true);

    requestPaymentMethods.onload = function () {
        var data = JSON.parse(this.response);
        if(requestPaymentMethods.status >= 200 && requestPaymentMethods.status < 400){

            var filtrados = data.filter(function (value) {
                return value.payment_type_id == "ticket";
            });

           filtrados.forEach(function (payment) {

                var option = document.createElement("option");
                option.text = payment.name;
               option.value = payment.id;

               cod.add(option);
            })
        } else{
            var errorMessage = document.createElement("marquee");
            errorMessage.textContent = "Status code igual a " + data.status + " - message" + data.message;
            app.appendChild(errorMessage)
        }
    };

    requestPaymentMethods.send()

}
function cargarAgencias(limit){

    var site = document.getElementById("sitesSelect").value;

    if(site == ""){
        alert("Debe ingresar un sitio");
        return
    }
    var agency = document.getElementById("paymentSelect").value;
    if(agency == ""){
        alert("Debe ingresar una agencia");
        return
    }
    var latitud = document.getElementById("latitud").value;
    if(latitud == ""){
        alert("Debe ingresar una latitud");
        return
    }
    var longitud = document.getElementById("longitud").value;
    if(longitud == ""){
        alert("Debe ingresar una longitud");
        return
    }
    var radio = document.getElementById("radio").value;
    if(radio == ""){
        alert("Debe ingresar un radio");
        return
    }
    var tbody = document.getElementById("tbody");
    tbody.innerHTML = '';
    var filtro = document.getElementById("filtro").value;


    var requestAgencias = new XMLHttpRequest();
    var url = "http://localhost:3000/sites/search?site="+site+"&agency="+ agency + "&latitud=" + latitud + "&longitud=" + longitud+","
        + radio + "&limit="+ (limit) *5;

    requestAgencias.open("GET",url, true);

    requestAgencias.onload = function () {
        var data = JSON.parse(this.response);

        var ordenados = data.results.sort(function (a,b) {
            switch (filtro) {
                case "address_line":
                    if(a.address_line > b.address_line){
                        return 1;
                    }
                    if (a.address_line < b.address_line) {
                        return -1;
                    }
                    return 0
                    break
                case "agency_code":
                    if(parseInt(a.agency_code) > parseInt(b.agency_code)){
                        return 1;
                    }
                    if (parseInt(a.agency_code) < parseInt(b.agency_code)) {
                        return -1;
                    }
                    return 0
                break
                case "distance":
                    if(parseFloat(a.distance) > parseFloat(b.distance)){
                        return 1;
                    }
                    if (parseFloat(a.distance) < parseFloat(b.distance)) {
                        return -1;
                    }
                    return 0
                    break
            }

        });

        if(requestAgencias.status >= 200 && requestAgencias.status < 400){
            let aux = Math.ceil(data.paging.total / 5);
            if(primeraConsulta(site,agency,latitud,longitud,radio)){
                let array = new Array(aux);
               for (let i =0; i < aux; i++){
                   array[i]=i;
               }
                cargarPaginacion(array);

            }

            ordenados.forEach(function (agency, index) {

                var hilera = document.createElement("tr");

                var address = document.createElement("td");
                var agency_code = document.createElement("td");
                var description = document.createElement("td");
                var distance = document.createElement("td");

                var buttons = document.createElement("td");
                var buttonsPositive = document.createElement("button");
                var buttonsNegative = document.createElement("button");
                var ipossitive = document.createElement("i");
                var inegative = document.createElement("i");

                buttons.appendChild(buttonsPositive)
                buttons.appendChild(buttonsNegative)

                buttonsPositive.appendChild(ipossitive)
                buttonsNegative.appendChild(inegative)

                buttonsPositive.className += 'btn button buttonPositive';
                ipossitive.className += 'fa fa-thumbs-o-up';

                buttonsNegative.className += 'btn button buttonNegative';
                inegative.className += 'fa fa-thumbs-o-down';

                buttonsPositive.onclick = function(e) {
                   addFavorite(agency)
                };
                buttonsNegative.onclick = function(e) {
                    removeFavorite(agency)
                };

                var txtaddress = document.createTextNode(agency.address.address_line);
                var txtagency_code = document.createTextNode(agency.agency_code);
                var txtdescription = document.createTextNode(agency.description);
                var txtdistance = document.createTextNode(agency.distance);

                address.appendChild(txtaddress);
                agency_code.appendChild(txtagency_code);
                description.appendChild(txtdescription);
                distance.appendChild(txtdistance);

                    hilera.appendChild(address);
                    hilera.appendChild(agency_code);
                    hilera.appendChild(description);
                    hilera.appendChild(distance);
                    hilera.appendChild(buttons);

                tbody.appendChild(hilera);



            })
        } else{
            var errorMessage = document.createElement("marquee");
            errorMessage.textContent = "Status code igual a " + data.status + " - message" + data.message;
            app.appendChild(errorMessage)
        }
    };

    requestAgencias.send()

}
function cargarPaginacion(array) {
    var paginacion = document.getElementById("pag");
    paginacion.innerHTML = ''
    array.forEach(function (valor, index) {
        var hilera = document.createElement("li");
        hilera.onclick = function(e) {
            cargarAgencias(valor);
        };

        var celdaId = document.createElement("a");
        var txtceldaid = document.createTextNode(index + 1);
        celdaId.appendChild(txtceldaid);
        hilera.appendChild(celdaId);



        hilera.className += 'page-item';
        celdaId.className += 'page-link';


        paginacion.appendChild(hilera);

    })




}


function addFavorite(agency) {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/sites/add_favorite");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify(agency));

    xmlhttp.onload = function () {
        var data = this.response;
        alert(data);
    }

}
function removeFavorite(agency) {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/sites/remove_favorite");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify(agency));

    xmlhttp.onload = function () {
        var data = this.response;
        alert(data);
    }

}
function verFavoritos() {
    var requestConsultarSites = new XMLHttpRequest();
    var tbodyFavorites = document.getElementById("tbodyFavorites");

    tbodyFavorites.innerHTML = '';


    requestConsultarSites.open("GET","http://localhost:3000/sites/get_favorites", true);

    requestConsultarSites.onload = function () {
        var data = JSON.parse(this.response);
        if(data.length != 0){
            data.forEach(function (agency, index) {

                var hilera = document.createElement("tr");

                var celdaId = document.createElement("td");
                var txtceldaid = document.createTextNode(index);

                var address = document.createElement("td");
                var agency_code = document.createElement("td");
                var description = document.createElement("td");
                var distance = document.createElement("td");


                var txtaddress = document.createTextNode(agency.address.address_line);
                var txtagency_code = document.createTextNode(agency.agency_code);
                var txtdescription = document.createTextNode(agency.description);
                var txtdistance = document.createTextNode(agency.distance);

                celdaId.appendChild(txtceldaid);
                address.appendChild(txtaddress);
                agency_code.appendChild(txtagency_code);
                description.appendChild(txtdescription);
                distance.appendChild(txtdistance);

                hilera.appendChild(celdaId);
                hilera.appendChild(address);
                hilera.appendChild(agency_code);
                hilera.appendChild(description);
                hilera.appendChild(distance);

                tbodyFavorites.appendChild(hilera);
                document.getElementById('id01').style.display='block'



            })


        } else{
            alert("No hay agencias favoritas cargadas")
        }


    };

    requestConsultarSites.send()

}
