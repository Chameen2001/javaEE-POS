package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderBOImpl;
import bo.custom.impl.OrderDetailBOImpl;

public class BOFactory {
    private static BOFactory boFactory = null;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory = boFactory == null ? new BOFactory() : boFactory;
    }

    public enum BOType {
        CUSTOMER_BO, ITEM_BO, ORDER_BO, ORDERDETAIL_BO
    }

    public SuperBO getBOImpl(BOType boType) {
        switch (boType) {
            case CUSTOMER_BO:
                return new CustomerBOImpl();
            case ITEM_BO:
                return new ItemBOImpl();
            case ORDER_BO:
                return new OrderBOImpl();
            case ORDERDETAIL_BO:
                return new OrderDetailBOImpl();
            default:
                return null;
        }
    }
}
