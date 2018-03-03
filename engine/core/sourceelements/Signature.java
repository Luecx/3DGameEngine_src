package engine.core.sourceelements;

/**
 * Created by Luecx on 04.01.2017.
 */
public enum Signature {

    EMPTY_SIGNATURE(0),
    LINE_SYSTEM_SIGNATURE(2553);
    ;

    private final int signature;


    Signature(int signature) {
        this.signature = signature;
    }

    public int getSignature() {
        return signature;
    }

    public boolean equals(Signature s) {
        return s.signature == this.signature;
    }
}
