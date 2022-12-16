package prr.tariff;

public class GoldTariff implements Tariff {
    public double text(int n) {
        if (n < 50) {
            return 10;
        }
        else if (n < 100) {
            return 10;
        }
        return n*2;
    }

    public double voice(int n) {
        return n*10;
    }

    public double video(int n) {
        return n*30;
    }
}
