package org.but.feec.javafx;

import org.but.feec.javafx.config.DataSourceConfig;

public class Main {
    public static void main(String[] args) {
        DataSourceConfig.initializeDataSource(args);
        App.main(args);
    }
}
