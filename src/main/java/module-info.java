module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;

    opens Main;
    opens view.account_menus;
    opens view.account_menus.customer_view.account_view;
    opens view.account_menus.customer_view.cart_view;
    opens view.account_menus.customer_view.orders_view;
    opens view.account_menus.manager_view.account_view;
    opens view.account_menus.manager_view.category_view;
    opens view.account_menus.manager_view.discount_view;
    opens view.account_menus.manager_view.manage_products_view;
    opens view.account_menus.manager_view.manage_users_view;
    opens view.account_menus.manager_view.requests_view;
    opens view.account_menus.seller_view.accounts_view;
    opens view.account_menus.seller_view.manage_products_view;
    opens view.account_menus.seller_view.sellers_offs_view;
    opens view.account_menus.seller_view.sellers_products_view;
    opens view.register_login_view;
    opens view.main_menu;
    opens view.shopping_menus.product.comment_view;
    opens view.shopping_menus.product.product_digest_view;
    opens view.shopping_menus.product.product_view;
    opens view.shopping_menus.products_and_offs_menus.filter_view;
    opens view.shopping_menus.products_and_offs_menus.offs_view;
    opens view.shopping_menus.products_and_offs_menus.products_view;
    opens view.shopping_menus.products_and_offs_menus.sorts_view;
    opens view;

    exports view;
}