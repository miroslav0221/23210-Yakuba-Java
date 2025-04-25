package org.example.FactoryComand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;


public class Factory  {
    static final Logger factoryLogger = LogManager.getLogger(Factory.class.getName());

    final private HashMap<String, iComand> map_command_;

    public Factory() {
        map_command_ = new HashMap<>();
    }

    public void create_and_calc(String name_creator) {
        var it = map_command_.get(name_creator);
        if (it != null) {
            try {
                it.calc();
                factoryLogger.debug("Произведено вычисление");
            }
            catch (Exception e) {
                factoryLogger.error("Ошибка при вычислениях{}", e.getLocalizedMessage());
                System.err.println( "Обработка не корректна " + e.getLocalizedMessage());
            }

        }
        else {
            factoryLogger.debug("Подана не корректная команда");
            System.out.println("Нет такой команды");
        }

    }

    public void register_creator(String name, iComand command) {
        map_command_.put(name, command);
    }
}


