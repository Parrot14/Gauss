package Gauss;

public class Frac{

    public static final Frac ONE = new Frac(1);
    public static final Frac ZERO = new Frac(0);

    private int numerator, denominator;
    private boolean positive;

    private Frac(int numerator, int denominator, boolean positive){
        this.numerator = numerator;
        this.denominator = denominator;
        this.positive = positive;
    }

    public Frac(int numerator, int denominator){
        this.numerator = Math.abs(numerator);
        this.denominator = Math.abs(denominator);
        this.positive = numerator == 0 || (numerator>0) == (denominator>0);
        reduce();
    }

    public Frac(int number){
        this.numerator = Math.abs(number);
        this.denominator = 1;
        this.positive = number>=0;
        reduce();
    }

    public int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }

    // Multiplicative Inverse
    public Frac mInverse(){
        int temp = numerator;
        numerator = denominator;
        denominator = temp;
        return this;
    }

    // Aditive Inverse
    public Frac aInverse(){
        positive = !positive;
        return this;
    }

    public Frac copy(){
        return new Frac(numerator, denominator, positive);
    }

    public Frac copyTo(Frac frac){
        frac.numerator = numerator;
        frac.denominator = denominator;
        frac.positive = positive;
        return frac;
    }

    public Frac multiply(int number){
        return rawMultiply(number).fixSign().reduce();
    }

    public Frac multiply(Frac frac){
        return rawMultiply(frac).fixSign().reduce();
    }

    public Frac add(int number){
        return rawAdd(number).fixSign().reduce();
    }

    public Frac add(Frac frac){
        return rawAdd(frac).fixSign().reduce();
    }

    @Override
    public String toString() {
        return (positive?' ':'-')+" "+numerator+(denominator!=1?"/"+denominator:"");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Frac){
            Frac frac = (Frac) obj;
            return  frac.positive    == positive    &&
                    frac.denominator == denominator &&
                    frac.numerator   == numerator;
        }
        return false;
    }

    public boolean isOne(){
        return numerator == 1 && denominator == 1 && positive;
    }

    public boolean isZero(){
        return numerator == 0;
    }

    private Frac reduce(){
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return this;
    }

    private Frac fixSign(){
        if(numerator<0){
            numerator *= -1;
            aInverse();
        }else if (numerator == 0)
                positive = true;
        return this;
    }

    private Frac rawMultiply(int number){
        numerator *= number;
        return this;
    }

    private Frac rawMultiply(Frac frac){
        positive = positive == frac.positive;
        numerator *= frac.numerator;
        denominator *= frac.denominator;
        return this;
    }

    private Frac rawAdd(int number){
        if(positive)
            numerator += denominator*number;
        else
            numerator -= denominator*number;
        return this;
    }

    private Frac rawAdd(Frac frac){
        numerator *= frac.denominator;
        numerator += (positive == frac.positive?1:-1)*frac.numerator*denominator;
        denominator *= frac.denominator;
        return this;
    }
}