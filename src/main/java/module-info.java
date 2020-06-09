module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    opens Main;
    opens View.AccountMenus.CustomerView.AccountView;
    opens View.AccountMenus.CustomerView.CartView;
    opens View.AccountMenus.CustomerView.OrdersView;
    opens View.AccountMenus.ManagerView.AccountView;
    opens View.AccountMenus.ManagerView.CategoryView;
    opens View.AccountMenus.ManagerView.DiscountView;
    opens View.AccountMenus.ManagerView.ManageProductsView;
    opens View.AccountMenus.ManagerView.ManageUsersView;
    opens View.AccountMenus.ManagerView.RequestsVeiw;
    opens View.AccountMenus.SellerView.AccountView;
    opens View.AccountMenus.SellerView.ManageProductView;
    opens View.AccountMenus.SellerView.SellersOffsVeiw;
    opens View.AccountMenus.SellerView.SellersProductView;
    opens View.MainMenuView;
    opens View.SampleLayouts;
    opens View.ShoppingMenus.Product.CommentView;
    opens View.ShoppingMenus.Product.ProductDigestView;
    opens View.ShoppingMenus.Product.ProductView;
    opens View.ShoppingMenus.ProductsAndOffsMenus.FiltersView;
    opens View.ShoppingMenus.ProductsAndOffsMenus.OffsView;
    opens View.ShoppingMenus.ProductsAndOffsMenus.ProductsView;
    opens View.ShoppingMenus.ProductsAndOffsMenus.SortsView;


    opens sample;
}