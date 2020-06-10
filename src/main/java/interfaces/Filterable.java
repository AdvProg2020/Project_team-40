package interfaces;

import exceptions.MenuException;
import model.Product;

import java.util.ArrayList;

public interface Filterable {
    ArrayList<String> getAvailableFilters();

    ArrayList<Product> filter(String name, String value) throws MenuException;

    ArrayList<Product> filter(String name, double min, double max) throws MenuException;

    void disableFilter(String selectedField) throws MenuException;

    ArrayList<String> getCurrentFilters();
}
