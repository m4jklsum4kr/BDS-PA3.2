package org.but.feec.javafx;

import org.but.feec.javafx.config.DataSourceConfig;

public class Main {
    public static void main(String[] args) {
        DataSourceConfig.initializeDataSource(args);
        App.main(args);
    }
}

//TODO: V LibRepository jsou SQL queries ty komplet predelat at sedi na nase.
//TODO: Poupravit ve views variables at odpovidaji poctu ktery jsou v zadani
//TODO: Mozna pridat nejaky GUI windows nepocital jsem kolik jich je
//TODO: napojit to nejak na nasi databazi
// jinak to je vsecko prefaktorovany a staci jen pridavat vpohode se to da compile. Nedostal jsem se pres prihlasovaci stranku
