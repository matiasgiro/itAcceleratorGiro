package Interfaces;

import Modelos.Item;

import java.util.Collection;

public interface ItemService {
    public void addItem(Item item);
    public Collection<String> getAllItems(Item[] items);
    public Collection<Item> orderItems(String atributo, String valor);
    public Collection<Item> filterItems(Double desde, Double hasta);
    public Collection<Item> conteinsItem();


}
