package model.search;

import model.Product;
import model.enumerations.SortTypes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductSort{
    private ArrayList<Product> products;
    private SortTypes sort;

    public ProductSort(ArrayList<Product> products, SortTypes sort){
        this.sort = sort;
        this.products = products;
    }

    public void setSortType(SortTypes sort){
        this.sort = sort;
    }

    public ArrayList<Product> getSortedProducts(){

        if(sort == null)
            return  products;

        Comparator<Product> comparator = Comparator.comparing((product) -> {
            switch(sort) {
                case MOST_EXPENSIVE:
                    return -product.getPrice();
                case CHEAPEST:
                    return product.getPrice();
                case MOST_VISITED:
                    return -(double)product.getVisitCount();
                case HIGHEST_SALES:
                    //TODO add number of sales to product
                    return -(double)product.getVisitCount();
                case HIGHEST_SCORE:
                    return -product.getAverageScore();
            }
            return product.getPrice();
        });
        Stream<Product> stream = products.stream().sorted(comparator);

        return (ArrayList)stream.collect(Collectors.toList());
    }

}
