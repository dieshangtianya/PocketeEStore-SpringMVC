package pocketestore.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum State {
    //禁用
    Disable(0),
    //启用
    Enable(1);

    private int value = 0;

    State(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public static State fromInt(int intValue) {
        for (State stateItem : State.values()) {
            if (stateItem.getValue() == intValue) {
                return stateItem;
            }
        }
        return null;
    }
}
