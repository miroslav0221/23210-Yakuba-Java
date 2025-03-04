package org.example.FactoryComand;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Calculator.Context;
import org.example.Main;

public abstract class iComand {
    static final Logger comandLogger = LogManager.getLogger(iComand.class.getName());
    Context context_;
    iComand(Context context) {
        context_ = context;
    }
    abstract void calc();
}
