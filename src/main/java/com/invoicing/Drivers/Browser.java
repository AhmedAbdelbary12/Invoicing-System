package com.invoicing.Drivers;

public enum Browser {
    // Create Browsers Here

    CHROME {
        @Override
        public AbstractFactory getDriverFactory() {
            return new ChromeFactory();
        }
    },
    EDGE {
        @Override
        public AbstractFactory getDriverFactory() {
            return new EdgeFactory();
        }
    };

    public abstract AbstractFactory getDriverFactory();
}
