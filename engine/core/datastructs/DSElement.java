package engine.core.datastructs;

import engine.core.sourceelements.SourceElement;

/**
 * Created by Luecx on 18.01.2017.
 */
public class DSElement<T extends SourceElement> implements DataStruct {

    private T element;

    public DSElement(T element) {
        this.element = element;
    }

    public DSElement() {
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    @Override
    public void clear() {
        element = null;
    }
}
