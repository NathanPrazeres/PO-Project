package prr.tariff;

public class Normal extends Tariff {
    @Override
    public String getType() {
        return "NORMAL";
    }

    public int text(int N) {
        if (N < 50) {
            return 10;
        }
        else if (N < 100) {
            return 16;
        }
        return N*2;
    }

    public int voice(int N) {
        return N*20;
    }

    public int video(int N) {
        return N*30;
    }
}
