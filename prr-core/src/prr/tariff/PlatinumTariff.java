package prr.tariff;

public class PlatinumTariff implements Tariff {
    public double text(int n) {
        if (n < 50) {
            return 0;
        }
        else if (n < 100) {
            return 4;
        }
        return 4;
    }

    public double voice(int n) {
        return n*10;
    }

    public double video(int n) {
        return n*30;
    }
}
