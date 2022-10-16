package prr.tariffs;

public abstract class Tariff {
    private boolean _ready = false;

    public abstract int text(int N);
    public abstract int voice(int N);
    public abstract int video(int N);

    public boolean isReady() {
        return _ready;
    }
    public void setReady(boolean ready) {
        _ready = ready;
    }
}
