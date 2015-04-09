import java.util.Random;

class Helper {
    double sum;
    Random rand;
    public Helper() {
        this.sum = 0;
        this.rand = new Random();
    }
    public int number() {
        return this.rand.nextInt(100);
    }
    public synchronized void add(double n) {
        this.sum += n;
    }
    public double total() {
        return this.sum;
    }
}