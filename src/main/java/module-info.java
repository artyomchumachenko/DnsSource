module ru.mai.coursework.dns.dnssource {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires hibernate.jpa;

    opens ru.mai.coursework.dns.dnssource to javafx.fxml;
    exports ru.mai.coursework.dns.dnssource;
}