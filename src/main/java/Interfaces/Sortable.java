package Interfaces;

import exceptions.MenuException;

import java.util.ArrayList;

public interface Sortable {
    ArrayList<String> getAvailableSorts();

    void addSort(String sort) throws MenuException;

    void disableSort();

    String getCurrentSort();
}
