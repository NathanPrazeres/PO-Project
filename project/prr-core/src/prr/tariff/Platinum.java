package prr.tariffs;

public class Platinum extends Tariff {
    private int _consecutive = 0;
    
    @Override
    public String getType() {
        return "PLATINUM";
    }

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
        addCons();
        if (getCons() == 2) {
            setReady(true);
        }
        if (N < 50) {
            return 0;
        }
        else if (N < 100) {
            return 4;
        }
        return 4;
    }

    public int voice(int N) {
        resetCons();
        return N*10;
    }

    public int video(int N) {
        resetCons();
        return N*30;
    }
}
