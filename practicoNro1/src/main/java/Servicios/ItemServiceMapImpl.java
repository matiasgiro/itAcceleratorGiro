package Servicios;

import Interfaces.ItemService;
import Modelos.Item;
import Enum.OrderAtribute;
import Enum.OrderValor;


import java.util.*;

import static java.util.stream.Collectors.toList;

public class ItemServiceMapImpl implements ItemService {
    private HashMap<String, Item> itemMap;
    public static OrderAtribute criterioAtribute;
    public static OrderValor criterioValor;



    public ItemServiceMapImpl() {
        itemMap = new HashMap<String, Item>();
    }

    @Override
    public void addItem(Item item) {
        itemMap.put(item.getId(), item);
    }


    @Override
    public Collection<String> getAllItems(Item[] items) {
        itemMap.clear();
        Arrays.stream(items).forEach(this::addItem);

        List<String> b = itemMap.values().stream().map(Item::getTitle).collect(toList());


        return b;
    }
    @Override
    public Collection<Item> orderItems(String atribute, String valor) {
        List<Item> a = null;

        criterioAtribute = OrderAtribute.valueOf(atribute);
        criterioValor = OrderValor.valueOf(valor);

        switch (criterioAtribute) {
            case PRICING:
                if(criterioValor == OrderValor.ASC){
                    a = itemMap.values().stream().sorted(Comparator.comparingDouble(Item::getPrice)).collect(toList());
                }
                else{
                    a= itemMap.values().stream().sorted(Comparator.comparingDouble(Item::getPrice).reversed()).collect(toList());
                }
                break;
            case LISTING_TYPE:
                if(criterioValor == OrderValor.ASC){
                    a = itemMap.values().stream().sorted(Comparator.comparing(Item::getListing_type_id)).collect(toList());
                }
                else{
                    a= itemMap.values().stream().sorted(Comparator.comparing(Item::getListing_type_id).reversed()).collect(toList());
                }
                break;
        }

        return a;
    }
    @Override
    public Collection<Item> filterItems(Double desde, Double hasta) {
        List<Item> c = null;
        c = itemMap.values().stream().filter(x -> x.getPrice()> desde && x.getPrice() < hasta).collect(toList());


        return c;
    }

    @Override
    public Collection<Item> conteinsItem() {
        List<Item> d = null;
        d = itemMap.values().stream().filter(x -> Arrays.asList(x.getTags()).contains("good_quality_thumbnail")).collect(toList());


        return d;    }
}
