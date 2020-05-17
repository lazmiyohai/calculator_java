// 
// Decompiled by Procyon v0.5.36
// 

package calculator;

public class Complex implements Scalar
{
    private Rational a;
    private Rational b;
    
    public Complex(final Rational a, final Rational b) {
        this.a = a;
        this.b = b;
    }
    
    public Complex(final Complex comp) {
        this.a = comp.getReal();
        this.b = comp.getImgn();
    }
    
    public Rational getReal() {
        return this.a;
    }
    
    public Rational getImgn() {
        return this.b;
    }
    
    public void setReal(final Rational r) {
        this.a = r;
    }
    
    public void setImgn(final Rational i) {
        this.b = i;
    }
    
    @Override
    public String toString() {
        return this.a + "+" + this.b + "i";
    }
    
    @Override
    public Scalar add(final Scalar s) {
        final Rational tempa = (Rational)this.a.add(((Complex)s).getReal());
        final Rational tempb = (Rational)this.b.add(((Complex)s).getImgn());
        final Scalar s2 = new Complex(tempa, tempb);
        return s2;
    }
    
    @Override
    public Scalar mul(final Scalar s) {
        final Rational tempa = (Rational)this.a.mul(((Complex)s).getReal()).add(this.b.mul(((Complex)s).getImgn()).neg());
        final Rational tempb = (Rational)this.a.mul(((Complex)s).getImgn()).add(this.b.mul(((Complex)s).getReal()));
        final Scalar s2 = new Complex(tempa, tempb);
        return s2;
    }
    
    @Override
    public Scalar neg() {
        final Rational tempa = (Rational)this.a.neg();
        final Rational tempb = (Rational)this.b.neg();
        final Scalar s1 = new Complex(tempa, tempb);
        return s1;
    }
    
    @Override
    public boolean isZero() {
        return this.a.isZero() && this.b.isZero();
    }
    
    @Override
    public Scalar inv() {
        final Rational denom = (Rational)((Rational)this.a.mul(this.a)).add(this.b.mul(this.b));
        final Rational tempa = (Rational)this.a.mul(denom.inv());
        final Rational tempb = (Rational)this.b.mul(denom.inv()).neg();
        final Scalar s1 = new Complex(tempa, tempb);
        return s1;
    }
}
