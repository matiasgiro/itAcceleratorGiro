import Servicios.ItemServiceFileImpl;
import Servicios.ItemServiceMapImpl;
import Interfaces.ItemService;
import Modelos.Currency;
import Modelos.Item;
import Modelos.ItemResultado;
import Response.StandardResponse;
import Response.StatusResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;
import static spark.Spark.*;

public class main {
    public static void main(String[] args) {

        final ItemService itemService = new ItemServiceMapImpl();
        final ItemService itemServiceFile = new ItemServiceFileImpl();

        get("/order_items", (request, response) -> {
            response.type("application/json");
            String atribute = request.queryParams("atribute");
            String valor = request.queryParams("valor");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.orderItems(atribute, valor))));
        });

        get("/filter_items", (request, response) -> {
            response.type("application/json");
            Double desde = Double.valueOf(request.queryParams("since"));
            Double hasta = Double.valueOf(request.queryParams("until"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.filterItems(desde, hasta))));
        });

        get("/conteins_items", (request, response) -> {
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.conteinsItem())));
        });

        get("/get_items/:search", (request, response) -> {
            response.type("application/json");
            String search = request.params(":search");
            Item items[] = get_items(search);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.getAllItems(items))));
        });
    }

    public static Item[] get_items(String search) throws Exception {


        URL url = new URL("https://api.mercadolibre.com/sites/MLA/search?q="+ search);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
        } else {
            System.out.println("URL inválida");
            return new Item[0];
        }
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current = null;
        while ((current = in.readLine()) != null) {
            urlString += current;
        }
        JsonObject jobj = new Gson().fromJson(urlString, JsonObject.class);
        JsonElement aux = jobj.get("results");
        ItemResultado[] itemsResultado = new Gson().fromJson(aux, ItemResultado[].class);

        Item items[] = new Item[itemsResultado.length];


        List<String> currenciesId = Arrays.stream(itemsResultado).map(s-> s.getCurrency_id())
                .collect(toList());


        Currency currencies[] = get_currencies();

        for (int i = 0; i < itemsResultado.length; i++) {


            int bandera = i;
            String idCurrencie = currenciesId.get(bandera);
            Currency currency = Arrays.stream(currencies).filter(x -> x.currency_id().equals(idCurrencie)).limit(1).findFirst().get();

            items[i] = new Item(itemsResultado[bandera]);
            items[i].setCurrency(currency);

        }

        return items;
    }
    public static Currency[] get_currencies() throws Exception {

        URL url = new URL("https://api.mercadolibre.com/currencies");
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
        } else {
            System.out.println("URL inválida");
            return new Currency[0];
        }
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current = null;
        while ((current = in.readLine()) != null) {
            urlString += current;
        }

        Currency[] currencies = new Gson().fromJson(urlString, Currency[].class);



        return currencies;
    }


}



