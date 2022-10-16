package prr.tariffs;

public class Gold extends Tariff {
    private int _consecutive = 0;
    
    public void addCons() {
        _consecutive += 1;
    }

    public void setCons(int cons) {
        _consecutive = cons;
    }

    public int getCons() {
        return _consecutive;
    }

    public int text(int N) {
        resetCons();
        if (N < 50) {
            return 10;
        }
        else if (N < 100) {
            return 10;
        }
        return N*2;
    }

    public int voice(int N) {
        resetCons();
        return N*10;
    }

    public int video(int N) {
        addCons();
        if (getCons() == 5) {
            setReady(true);
        }
        return N*30;
    }
}
