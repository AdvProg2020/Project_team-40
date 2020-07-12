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
    opens client.view.account_menus;
    opens client.view.account_menus.manager_view.account_view;
    opens client.view.account_menus.customer_view.account_view;
    opens client.view.account_menus.seller_view.accounts_view;
    opens client.view.shopping_menus.product.comment_view;
    opens client.view.shopping_menus.product.product_digest_view;
    opens client.view.shopping_menus.product.product_view;
    opens client.view.shopping_menus.products_and_offs_menus.filter_view;
    opens client.view.shopping_menus.products_and_offs_menus.offs_view;
    opens client.view.shopping_menus.products_and_offs_menus.products_view;
    opens client.view.shopping_menus.products_and_offs_menus.sorts_view;
    opens client.view;

    exports client.view;
    exports server.model.log;
    exports server.server_resources.manager_account_controller;
    exports server.server_resources.customer_account_controller;
    exports exceptions;
    exports server.model;
    exports server.model.users;
    exports server.model.requests;
    exports server.model.search;
    exports server.model.enumerations;
}