import Recursos.RecursoApiMeli;
import Servicios.ItemServiceFileImpl;
import Servicios.ItemServiceMapImpl;
import Interfaces.ItemService;
import Modelos.Item;
import Response.StandardResponse;
import Response.StatusResponse;
import com.google.gson.Gson;

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

        post("/add_item", (request, response) -> {
            response.type("application/json");
            Item item =  new Gson().fromJson(request.body(),Item.class);
            itemService.addItem(item);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/get_item/:id", (request, response) -> {
            response.type("application/json");
            String item = request.params(":id");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.getItem(item))));
        });


    }




}



