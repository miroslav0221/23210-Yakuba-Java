package FactoryComand;
import java.util.HashMap;

public class Factory <T> {
    final private HashMap<String, Creator<T>> map_creator_;

    public Factory() {
        map_creator_ = new HashMap<>();
    }

    public T create(String name_creator) {
        var it = map_creator_.get(name_creator);
        if (it != null) {
            return it.create();
        }

        return null;
    }

    public void register_creator(String name, Creator<T> creator) {
        map_creator_.put(name, creator);
    }
}

