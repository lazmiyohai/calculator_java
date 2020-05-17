// 
// Decompiled by Procyon v0.5.36
// 

package calculator;

import java.util.Vector;

public class Matrix
{
    private int m;
    private int n;
    private Vector<MathVector> matrix;
    
    public Matrix(final int m, final int n) {
        this.m = m;
        this.n = n;
        this.matrix = new Vector<MathVector>();
    }
    
    public Matrix(final Matrix mat) {
        this.m = mat.getRowSize();
        this.n = mat.getColumnSize();
        this.matrix = mat.getMatrix();
    }
    
    public int getRowSize() {
        return this.m;
    }
    
    public int getColumnSize() {
        return this.n;
    }
    
    public Vector<MathVector> getMatrix() {
        return this.matrix;
    }
    
    public void setRowSize(final int r) {
        this.m = r;
    }
    
    public void setColumnSize(final int c) {
        this.n = c;
    }
    
    public void setMatrix(final Vector<MathVector> v) {
        this.matrix = v;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.m; ++i) {
            s = String.valueOf(s) + this.matrix.elementAt(i) + System.lineSeparator();
        }
        return s;
    }
    
    public Matrix add(final Matrix mat) {
        if (mat.getRowSize() == this.m && mat.getColumnSize() == this.n) {
            final Matrix m1 = new Matrix(this.m, this.n);
            for (int i = 0; i < this.m; ++i) {
                m1.getMatrix().add(this.matrix.elementAt(i).add(mat.getMatrix().elementAt(i)));
            }
            return m1;
        }
        throw new RuntimeException("both vectors must be of the same size");
    }
    
    public Matrix mul(final Matrix mat) {
        if (mat.getRowSize() == this.n) {
            final Matrix m1 = new Matrix(this.m, mat.getColumnSize());
            for (int i = 0; i < this.m; ++i) {
                final MathVector v1 = new MathVector(mat.getColumnSize());
                final MathVector v2 = new MathVector(this.n);
                for (int j = 0; j < mat.getColumnSize(); ++j) {
                    for (int l = 0; l < this.n; ++l) {
                        v2.getScalars()[l] = mat.getMatrix().elementAt(l).getScalars()[j];
                    }
                    v1.getScalars()[j] = this.matrix.elementAt(i).mul(v2);
                }
                m1.getMatrix().add(v1);
            }
            return m1;
        }
        throw new RuntimeException("both vectors must be of the same size");
    }
    
    public void rowSwitching(final int i, final int j) {
        final MathVector temp = this.matrix.elementAt(i);
        this.matrix.set(i, this.matrix.elementAt(j));
        this.matrix.set(j, temp);
    }
    
    public Matrix transpose() {
        final Matrix mat = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; ++i) {
            final MathVector v1 = new MathVector(this.m);
            for (int j = 0; j < this.m; j = this.m + 1) {
                v1.getScalars()[j] = this.matrix.elementAt(j).getScalars()[i];
            }
            mat.getMatrix().add(v1);
        }
        return mat;
    }
    
    public Matrix solve() {
        boolean found = false;
        for (int i = 0; i < this.n - 1; ++i) {
            if (this.matrix.elementAt(i).getScalars()[i].isZero()) {
                for (int j = i + 1; j < this.m && !found; ++j) {
                    if (!this.matrix.elementAt(j).getScalars()[i].isZero()) {
                        this.rowSwitching(i, j);
                        found = true;
                    }
                }
                if (!found) {
                    throw new RuntimeException("this matrix has no solution!");
                }
                --i;
            }
            else {
                this.matrix.elementAt(i).mulByScalar(this.matrix.elementAt(i).getScalars()[i].inv());
                for (int j = 0; j < this.m; ++j) {
                    if (j != i) {
                        final MathVector v = new MathVector(this.matrix.elementAt(i));
                        this.matrix.set(j, this.matrix.elementAt(j).add(v.mulByScalar(this.matrix.elementAt(j).getScalars()[i].neg())));
                    }
                }
            }
        }
        return this;
    }
}
