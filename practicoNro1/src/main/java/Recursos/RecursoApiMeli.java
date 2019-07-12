package Recursos;

import Modelos.Currency;
import Modelos.Item;
import Modelos.ItemResultado;
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

import static java.util.stream.Collectors.toList;

public class RecursoApiMeli {
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
