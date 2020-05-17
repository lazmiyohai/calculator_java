// 
// Decompiled by Procyon v0.5.36
// 

package calculator;

public class Rational implements Scalar
{
    private int a;
    private int b;
    
    public Rational(final int a, final int b) {
        if (b == 0) {
            throw new RuntimeException("b cannot be 0");
        }
        this.a = a;
        this.b = b;
    }
    
    public Rational(final Rational rat) {
        this.a = rat.getNumer();
        this.b = rat.getDenom();
    }
    
    public int getNumer() {
        return this.a;
    }
    
    public int getDenom() {
        return this.b;
    }
    
    public void setNumer(final int numer) {
        this.a = numer;
    }
    
    public void setDenom(final int denom) {
        this.b = denom;
    }
    
    public int GCD(final int a, final int b) {
        if (b == 0) {
            return a;
        }
        return this.GCD(b, a % b);
    }
    
    @Override
    public String toString() {
        if (this.a == 0) {
            this.b = 1;
        }
        else if (this.a / this.b == 1 && this.a % this.b == 0) {
            this.a = 1;
            this.b = 1;
        }
        else if (this.b % this.a == 0) {
            this.b /= this.a;
            this.a = 1;
        }
        else {
            final int x = this.GCD(this.a, this.b);
            this.a /= x;
            this.b /= x;
        }
        return String.valueOf(this.a) + "/" + this.b;
    }
    
    @Override
    public Scalar add(final Scalar s) {
        int tempa = 0;
        int tempb = 0;
        if (this.b == ((Rational)s).getDenom()) {
            tempa = this.a + ((Rational)s).getNumer();
            tempb = this.b;
        }
        else {
            tempa = this.a * ((Rational)s).getDenom() + this.b * ((Rational)s).getNumer();
            tempb = this.b * ((Rational)s).getDenom();
        }
        final Scalar s2 = new Rational(tempa, tempb);
        return s2;
    }
    
    @Override
    public boolean isZero() {
        return this.a == 0;
    }
    
    @Override
    public Scalar mul(final Scalar s) {
        final int tempa = this.a * ((Rational)s).getNumer();
        final int tempb = this.b * ((Rational)s).getDenom();
        final Scalar s2 = new Rational(tempa, tempb);
        return s2;
    }
    
    @Override
    public Scalar neg() {
        final int tempa = this.a * -1;
        final Scalar s1 = new Rational(tempa, this.b);
        return s1;
    }
    
    @Override
    public Scalar inv() {
        final Scalar s1 = new Rational(this.b, this.a);
        return s1;
    }
}
