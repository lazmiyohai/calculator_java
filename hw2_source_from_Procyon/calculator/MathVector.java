// 
// Decompiled by Procyon v0.5.36
// 

package calculator;

public class MathVector
{
    private int size;
    private Scalar[] scalars;
    
    public MathVector(final int n) {
        this.size = n;
        this.scalars = new Scalar[n];
    }
    
    public MathVector(final MathVector v) {
        this.size = v.getSize();
        this.scalars = new Scalar[v.getSize()];
        for (int i = 0; i < v.getSize(); ++i) {
            this.scalars[i] = v.getScalars()[i];
        }
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Scalar[] getScalars() {
        return this.scalars;
    }
    
    public void setScalars(final int n) {
        this.scalars = new Scalar[n];
        this.size = n;
    }
    
    @Override
    public String toString() {
        String ans = "";
        for (int i = 0; i < this.size; ++i) {
            ans = String.valueOf(ans) + this.scalars[i] + "    ";
        }
        return ans;
    }
    
    public MathVector add(final MathVector v1) {
        if (v1.getSize() == this.size) {
            final MathVector v2 = new MathVector(this.size);
            for (int i = 0; i < this.size; ++i) {
                v2.getScalars()[i] = this.scalars[i].add(v1.getScalars()[i]);
            }
            return v2;
        }
        throw new RuntimeException("both vectors must be of the same size");
    }
    
    public MathVector mulByScalar(final Scalar s) {
        for (int i = 0; i < this.size; ++i) {
            this.scalars[i] = this.scalars[i].mul(s);
        }
        return this;
    }
    
    public Scalar mul(final MathVector v1) {
        if (v1.getSize() == this.size) {
            Scalar ans = this.scalars[0].mul(v1.getScalars()[0]);
            for (int i = 1; i < this.size; ++i) {
                ans = ans.add(this.scalars[i].mul(v1.getScalars()[i]));
            }
            return ans;
        }
        throw new RuntimeException("both vectors must be of the same size");
    }
}
