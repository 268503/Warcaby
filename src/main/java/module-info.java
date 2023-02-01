module warcaby {
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    exports klient;
    exports serwer;
    exports serwer.dto;
}