package Servicios;

import Enum.OrderAtribute;
import Enum.OrderValor;
import Interfaces.ItemService;
import Modelos.Item;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class ItemServiceFileImpl implements ItemService {
    private HashMap<String, Item> itemMap;
    public static OrderAtribute criterioAtribute;
    public static OrderValor criterioValor;



    public ItemServiceFileImpl() {
    }

    @Override
    public void addItem(Item item) {

    }


    @Override
    public Collection<String> getAllItems(Item[] items) {

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("itAcceleratorF", false)){
            gson.toJson(items, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> a = Arrays.asList(items).stream().map(Item::getTitle).collect(toList());

        return a;
    }
    @Override
    public Collection<Item> orderItems(String atribute, String valor) {
        List<Item> aux = null;
        Gson gson = new Gson();
        List<Item> a = readFile();

        criterioAtribute = OrderAtribute.valueOf(atribute);
        criterioValor = OrderValor.valueOf(valor);


        switch (criterioAtribute) {
            case PRICING:
                if(criterioValor == OrderValor.ASC){
                    aux = a.stream().sorted(Comparator.comparingDouble(Item::getPrice)).collect(toList());
                }
                else{
                    aux= a.stream().sorted(Comparator.comparingDouble(Item::getPrice).reversed()).collect(toList());
                }
                break;
            case LISTING_TYPE:
                if(criterioValor == OrderValor.ASC){
                    aux = a.stream().sorted(Comparator.comparing(Item::getListing_type_id)).collect(toList());
                }
                else{
                    aux= a.stream().sorted(Comparator.comparing(Item::getListing_type_id).reversed()).collect(toList());
                }
                break;
        }

        return aux;
    }
    @Override
    public Collection<Item> filterItems(Double desde, Double hasta) {
        List<Item> c = null;
        List<Item> a = readFile();
        c = a.stream().filter(x -> x.getPrice()> desde && x.getPrice() < hasta).collect(toList());


        return c;
    }

    @Override
    public Collection<Item> conteinsItem() {
        List<Item> d = null;
        List<Item> a = readFile();

        d = a.stream().filter(x -> Arrays.asList(x.getTags()).contains("good_quality_thumbnail")).collect(toList());


        return d;
    }

    public List<Item> readFile(){
        List<Item> a = null;
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("itAcceleratorF")){
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            a= gson.fromJson(reader, type);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
