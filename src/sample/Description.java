package sample;

import javafx.beans.property.SimpleStringProperty;

public class Description {

    private SimpleStringProperty range;
    private SimpleStringProperty name;

    public Description(String range, String name) {
        this.range = new SimpleStringProperty(range);
        this.name = new SimpleStringProperty(name);
    }

    public String getRange() {
        return range.get();
    }

    public SimpleStringProperty rangeProperty() {
        return range;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
}
