package Gauss;

public class Rational{

    public static final Rational ONE = new Rational(1);
    public static final Rational ZERO = new Rational(0);

    private int numerator, denominator;
    private boolean positive;

    private Rational(int numerator, int denominator, boolean positive){
        this.numerator = numerator;
        this.denominator = denominator;
        this.positive = positive;
    }

    public Rational(int numerator, int denominator){
        this.numerator = Math.abs(numerator);
        this.denominator = Math.abs(denominator);
        this.positive = numerator == 0 || (numerator>0) == (denominator>0);
        reduce();
    }

    public Rational(int number){
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
    public Rational mInverse(){
        int temp = numerator;
        numerator = denominator;
        denominator = temp;
        return this;
    }

    // Aditive Inverse
    public Rational aInverse(){
        positive = !positive;
        return this;
    }

    public Rational copy(){
        return new Rational(numerator, denominator, positive);
    }

    public Rational copyTo(Rational frac){
        frac.numerator = numerator;
        frac.denominator = denominator;
        frac.positive = positive;
        return frac;
    }

    public Rational multiply(int number){
        return rawMultiply(number).fixSign().reduce();
    }

    public Rational multiply(Rational frac){
        return rawMultiply(frac).fixSign().reduce();
    }

    public Rational add(int number){
        return rawAdd(number).fixSign().reduce();
    }

    public Rational add(Rational frac){
        return rawAdd(frac).fixSign().reduce();
    }

    @Override
    public String toString() {
        return (positive?' ':'-')+" "+numerator+(denominator!=1?"/"+denominator:"");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Rational){
            Rational frac = (Rational) obj;
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

    private Rational reduce(){
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return this;
    }

    private Rational fixSign(){
        if(numerator<0){
            numerator *= -1;
            aInverse();
        }else if (numerator == 0)
                positive = true;
        return this;
    }

    private Rational rawMultiply(int number){
        numerator *= number;
        return this;
    }

    private Rational rawMultiply(Rational frac){
        positive = positive == frac.positive;
        numerator *= frac.numerator;
        denominator *= frac.denominator;
        return this;
    }

    private Rational rawAdd(int number){
        if(positive)
            numerator += denominator*number;
        else
            numerator -= denominator*number;
        return this;
    }

    private Rational rawAdd(Rational frac){
        numerator *= frac.denominator;
        numerator += (positive == frac.positive?1:-1)*frac.numerator*denominator;
        denominator *= frac.denominator;
        return this;
    }
}