module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;
    requires org.restlet;
    opens view.account_menus.seller_view;
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