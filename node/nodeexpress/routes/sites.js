var express = require('express');
var router = express.Router();
var request = require('request');
var fs = require("fs");
var writerStream = fs.createWriteStream("agencias.txt");



router.get('/search', function(req, res) {
    var site = req.query.site;
    var agency = req.query.agency.toString();
    var latitud = req.query.latitud;
    var longitud = req.query.longitud;


    request.get("https://api.mercadolibre.com/sites/" +site+"/payment_methods/agencies/search?payment_method="+agency+"&near_to="+latitud+","+longitud, function (error, response, body) {
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

router.post('/file', function(req, res) {
    console.log(req.body);
    var readerStream = fs.createReadStream("agencias.txt");

    writerStream.write(JSON.stringify(req.body));

    var readerStream = fs.createReadStream("agencias.txt");
    console.log("1 " + readerStream);





});
module.exports = router;
