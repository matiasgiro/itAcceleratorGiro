import Recursos.RecursoApiMeli;
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
            Item items[] = RecursoApiMeli.get_items(search);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.getAllItems(items))));
        });
    }




}



