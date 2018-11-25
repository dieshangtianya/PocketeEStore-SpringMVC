package pocketestore.serviceimpl.factory;

import java.lang.*;
import java.lang.reflect.*;

public class DaoFactory {
    private static DaoFactory instance = new DaoFactory();

    public static DaoFactory getInstance() {
        return instance;
    }

    private String daoPackage = "pocketestore.daoimpl";

    private DaoFactory() {

    }

    public Object createDao(String daoName) {
        String daoFullName = daoPackage + "." + daoName;
        try {
            Class daoClass = Class.forName(daoFullName);
            Constructor constructor = daoClass.getDeclaredConstructor();
            if (constructor == null) {
                return null;
            }
            Object instance = constructor.newInstance();

            return instance;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
