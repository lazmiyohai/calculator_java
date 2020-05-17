// 
// Decompiled by Procyon v0.5.36
// 

package calculator;

public interface Scalar
{
    Scalar add(final Scalar p0);
    
    Scalar mul(final Scalar p0);
    
    Scalar neg();
    
    Scalar inv();
    
    boolean isZero();
}
