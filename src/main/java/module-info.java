
module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;
    requires org.restlet;
    requires yagson;
    opens server.model;
    opens server.model.users;
    opens server.model.log;
    opens server.model.requests;
    opens server.model.chat;
    opens server.model.enumerations;
    opens server.model.search;
    opens client.view.register_login_view;
    opens client.view.main_menu;
    opens client.view.account_menus;
    opens client.view.account_menus.seller_view;
    opens client.view.account_menus.seller_view.sellers_offs_view;
    opens client.view.account_menus.seller_view.accounts_view;
    opens client.view.account_menus.seller_view.sellers_products_view;
    opens client.view.account_menus.seller_view.auction_view;
    opens client.view.account_menus.customer_view.cart_view;
    opens client.view.account_menus.customer_view.orders_view;
    opens client.view.account_menus.customer_view.account_view;
    opens client.view.account_menus.manager_view;
    opens client.view.account_menus.manager_view.account_view;
    opens client.view.account_menus.manager_view.category_view;
    opens client.view.account_menus.manager_view.discount_view;
    opens client.view.account_menus.manager_view.manage_products_view;
    opens client.view.account_menus.manager_view.manage_users_view;
    opens client.view.account_menus.manager_view.requests_view;
    opens client.view.account_menus.customer_seller_common_view;
    opens client.view.account_menus.customer_view;
    opens client.view.bank;
    opens client.view.bank.receipts;
    opens client.view.bank.transactions;
    opens client.view.shopping_menus.product.product_view;
    opens client.view.shopping_menus.products_and_offs_menus.products_view;
    opens client.view.shopping_menus.products_and_offs_menus.sorts_view;
    opens client.view;
    opens client.view.account_menus.support_view;

    exports client.view;
    exports server.model.log;
    exports server.server_resources.manager_account_controller;
    exports server.server_resources.customer_account_controller;
    exports server.server_resources.seller_account_controller;
    exports server.server_resources.seller_customer_common;
    exports server.server_resources.accounts;
    exports server.server_resources.bank;
    exports server.server_resources.chat;
    exports server.server_resources.shop;
    exports exceptions;
    exports server.model;
    exports server.model.chat;
    exports server.model.users;
    exports server.model.requests;
    exports server.model.search;
    exports server.model.enumerations;
}