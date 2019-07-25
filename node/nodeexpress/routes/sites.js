var express = require('express');
var router = express.Router();
var request = require('request');
var fs = require('fs');
var writerStream = fs.createWriteStream("agencias.txt");
var file = require('file-system');
fs.writeFileSync('agencias.txt',"[]");





router.get('/search', function(req, res) {
    var site = req.query.site;
    var agency = req.query.agency.toString();
    var latitud = req.query.latitud;
    var longitud = req.query.longitud;
    var offset = req.query.limit;


    request.get("https://api.mercadolibre.com/sites/" +site+"/payment_methods/agencies/search?payment_method="+agency+"&near_to="+latitud+","+longitud +"&limit=5&offset="+offset, function (error, response, body) {
        if(error){
            res.send(error)
        }
        res.send(JSON.parse(body))
    })
});


    router.get('/', function(req, res) {
        request.get("https://api.mercadolibre.com/sites", function (error, response, body) {
            if(error){
                res.send(error)
            }
            res.send(JSON.parse(body))
        })
    });

router.get('/payment_methods', function(req, res) {
    var site = req.query.site;
    console.log(site)

    request.get("https://api.mercadolibre.com/sites/"+site+"/payment_methods", function (error, response, body) {
        if(error){
            res.send(error)
        }
        res.send(JSON.parse(body))
    })
});

router.post('/add_favorite', function(req, res) {
    const pagencia = req.body
    const stat = fs.statSync('./agencias.txt');
    if(stat.size == 0){
        let array = [];
        array.push(pagencia)
        fs.writeFileSync('agencias.txt',JSON.stringify(array));
        res.send("Agencia cargada correctamente")
    } else{
        var bandera = false;
       var a = fs.readFileSync('./agencias.txt');
       var b = JSON.parse(a)
        b.forEach((agencia) =>{
            if(agencia.agency_code == pagencia.agency_code ){
                bandera = true;
                res.send("La agencia ya existe");
            }
        });
       if(!bandera){
           b.push(pagencia);
           fs.writeFileSync('agencias.txt',JSON.stringify(b));
           res.send("Agencia cargada correctamente")
       }
    }
});

router.post('/remove_favorite', function(req, res) {
    const pagencia = req.body
    const stat = fs.statSync('./agencias.txt');
    if(stat.size == 0){
        res.send("No existe agencias cargadas")
    } else{
        var bandera = false;
        var a = fs.readFileSync('./agencias.txt');
        var b = JSON.parse(a)
        b.forEach((agencia, index) =>{
            if(agencia.agency_code == pagencia.agency_code ){
                bandera = true;
                b.splice(index, 1);
                fs.writeFileSync('agencias.txt',JSON.stringify(b));
                res.send("La agencia fue removida como favorita");
            }
        });
        if(!bandera){
            res.send("Agencia no se encontraba como favorita")
        }
    }
});

router.get('/get_favorites', function(req, res, next) {
    res.send(fs.readFileSync('./agencias.txt'));
})


module.exports = router;
