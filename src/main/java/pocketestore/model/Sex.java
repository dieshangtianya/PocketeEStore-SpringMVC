package pocketestore.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sex {
    Female(0),
    Male(1);


    private int value = 0;

    Sex(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public static Sex fromInt(int intValue) {
        for (Sex stateItem : Sex.values()) {
            if (stateItem.getValue() == intValue) {
                return stateItem;
            }
        }
        return null;
    }
}
