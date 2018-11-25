package pocketestore.model;

/**
 * 简化订单状态
 */
public enum OrderState {
    //未支付
    NoPaid(1),
    //已支付
    Paid(2),
    //已完成
    Completed(3),
    //已取消
    Canceled(4);

    private int value = 0;

    OrderState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderState fromInt(int intValue) {
        for (OrderState stateItem : OrderState.values()) {
            if (stateItem.getValue() == intValue) {
                return stateItem;
            }
        }
        return null;
    }
}
