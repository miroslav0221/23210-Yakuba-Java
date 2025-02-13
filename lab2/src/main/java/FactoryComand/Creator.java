package FactoryComand;
import java.util.function.Supplier;

public class Creator<T> {
    final private Supplier<T> create_func_;
    public Creator(Supplier<T> create_func) {
        create_func_ = create_func;
    }


    public T create() {
        return create_func_.get();
    }
}
