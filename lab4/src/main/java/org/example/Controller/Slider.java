package org.example.Controller;

import org.example.Factory.Factory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider implements ChangeListener {

    private final String type;
    private final Factory factory;

    public Slider(Factory factory, String type){
        this.factory = factory;
        this.type = type;
    }

    @Override
    public void stateChanged(ChangeEvent e){
        int delay = ((JSlider)e.getSource()).getValue();
        switch (type) {
            case "dealerDelay" -> factory.setDelayDealers(delay*1000);
            case "workersDelay" -> factory.setDelayWorkers(delay * 1000);
            case "bodySupplierDelay" -> factory.setDelaySuppliersBody(delay * 1000);
            case "motorSupplierDelay" -> factory.setDelaySuppliersMotor(delay * 1000);
            case "accessorySupplierDelay" -> factory.setDelaySuppliersAcces(delay * 1000);
        }
    }
}
