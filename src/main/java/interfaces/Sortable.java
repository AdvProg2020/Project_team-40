package interfaces;

import exceptions.MenuException;
import model.Product;

import java.util.ArrayList;

public interface Sortable {
    ArrayList<String> getAvailableSorts();

    ArrayList<Product> addSort(String sort) throws MenuException;

    void disableSort();

    String getCurrentSort();
}
