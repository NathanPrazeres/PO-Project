package prr.tariff;

public class NormalTariff implements Tariff {
    public double text(int n) {
        if (n < 50) {
            return 10;
        }
        else if (n < 100) {
            return 16;
        }
        return n*2;
    }

    public double voice(int n) {
        return n*20;
    }

    public double video(int n) {
        return n*30;
    }   
}
