module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;
    requires org.restlet;
    opens client.view.register_login_view;
    opens client.view.main_menu;
    opens client.view.shopping_menus.product.comment_view;
    opens client.view.shopping_menus.product.product_digest_view;
    opens client.view.shopping_menus.product.product_view;
    opens client.view.shopping_menus.products_and_offs_menus.filter_view;
    opens client.view.shopping_menus.products_and_offs_menus.offs_view;
    opens client.view.shopping_menus.products_and_offs_menus.products_view;
    opens client.view.shopping_menus.products_and_offs_menus.sorts_view;
    opens client.view;

    exports client.view;
    exports server.server_resources.manager_account_controller;
    exports server.model;
    exports server.model.requests;
}