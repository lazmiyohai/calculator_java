// 
// Decompiled by Procyon v0.5.36
// 

package calculator;

import java.util.Scanner;

public class CommandLineUI implements UI
{
    Scanner input;
    
    public CommandLineUI() {
        this.input = new Scanner(System.in);
    }
    
    public boolean isValidSize(final String toCheck) {
        boolean valid = false;
        final String[] a = toCheck.split(",");
        if (a.length == 2) {
            try {
                final int m = Integer.parseInt(a[0]);
                final int n = Integer.parseInt(a[1]);
                if (m > 0 && n > 0) {
                    valid = true;
                }
            }
            catch (NumberFormatException e) {
                valid = false;
            }
        }
        else {
            valid = false;
        }
        return valid;
    }
    
    public boolean isRowValid(final String toCheck, final int scalarChoice, final int n) {
        boolean valid = false;
        final String[] a = toCheck.split("\\s+");
        if (a.length == n) {
            for (int i = 0; i < n; ++i) {
                if (scalarChoice == 1) {
                    final String[] b = a[i].split("/");
                    if (b.length == 2) {
                        try {
                            final int x = Integer.parseInt(b[0]);
                            final int y = Integer.parseInt(b[1]);
                            valid = true;
                        }
                        catch (NumberFormatException e) {
                            valid = false;
                        }
                    }
                    else {
                        valid = false;
                    }
                }
                else {
                    final String[] b = a[i].split("\\+");
                    if (b.length == 2) {
                        b[1] = b[1].substring(0, b[1].length() - 1);
                        for (int j = 0; j < 2; ++j) {
                            final String[] c = b[j].split("/");
                            if (c.length == 2) {
                                try {
                                    final int x2 = Integer.parseInt(c[0]);
                                    final int y2 = Integer.parseInt(c[1]);
                                    valid = true;
                                }
                                catch (NumberFormatException e2) {
                                    valid = false;
                                }
                            }
                            else {
                                valid = false;
                            }
                        }
                    }
                    else {
                        valid = false;
                    }
                }
            }
        }
        else {
            valid = false;
        }
        return valid;
    }
    
    public Matrix matInput(final int m, final int n, final int scalarChoice) {
        final Matrix m2 = new Matrix(m, n);
        for (int i = 0; i < m; ++i) {
            boolean valid = false;
            final MathVector v = new MathVector(n);
            while (!valid) {
                final String s = this.input.nextLine();
                if (this.isRowValid(s, scalarChoice, n)) {
                    valid = true;
                    final String[] a = s.split("\\s+");
                    for (int j = 0; j < n; ++j) {
                        if (scalarChoice == 1) {
                            final String[] b = a[j].split("/");
                            final Scalar s2 = new Rational(Integer.parseInt(b[0]), Integer.parseInt(b[1]));
                            v.getScalars()[j] = s2;
                        }
                        else {
                            final String[] b = a[j].split("\\+");
                            final String[] c = b[0].split("/");
                            final String[] d = b[1].split("/");
                            d[1] = d[1].substring(0, d[1].length() - 1);
                            final Rational s3 = new Rational(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
                            final Rational s4 = new Rational(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
                            final Scalar s5 = new Complex(s3, s4);
                            v.getScalars()[j] = s5;
                        }
                    }
                }
                else {
                    System.out.println("Invalid input, please try again :");
                }
            }
            m2.getMatrix().add(v);
        }
        return m2;
    }
    
    @Override
    public void play() {
        System.out.println("Matrix Calculator");
        System.out.println("==========================");
        System.out.println("Please select the scalar field:");
        System.out.println("1) Rational Or 2) Complex");
        int scalarChoice = this.input.nextInt();
        this.input.nextLine();
        while (scalarChoice != 1 && scalarChoice != 2) {
            System.out.println("you must choose either 1 or 2");
            scalarChoice = this.input.nextInt();
        }
        System.out.println("Please select an option:");
        System.out.println("1) Addition");
        System.out.println("2) Multiplication");
        System.out.println("3) Solving linear equation systems");
        System.out.println("4) Exit");
        int choice = this.input.nextInt();
        if (this.input.hasNextLine()) {
            this.input.nextLine();
        }
        while (choice < 1 && choice > 4) {
            System.out.println("you must choose a number between 1 and 4");
            choice = this.input.nextInt();
        }
        System.out.println("You have selected option " + choice);
        int m = 0;
        int n = 0;
        boolean valid = false;
        if (choice == 1 || choice == 2) {
            System.out.println("Insert the matrix size: m,n");
            while (!valid) {
                final String s = this.input.nextLine();
                if (this.isValidSize(s)) {
                    valid = true;
                    final String[] a = s.split(",");
                    m = Integer.parseInt(a[0]);
                    n = Integer.parseInt(a[1]);
                }
                else {
                    System.out.println("Invalid input, please try again:");
                }
            }
            System.out.println("Insert the matrix");
            final Matrix m2 = this.matInput(m, n, scalarChoice);
            System.out.println("Insert the matrix size: m,n");
            int r = 0;
            int c = 0;
            for (valid = false; !valid; valid = false) {
                final String s2 = this.input.nextLine();
                if (this.isValidSize(s2)) {
                    valid = true;
                    final String[] a2 = s2.split(",");
                    r = Integer.parseInt(a2[0]);
                    c = Integer.parseInt(a2[1]);
                }
                else {
                    System.out.println("Invalid input, please try again:");
                }
                if (choice == 1 && (m != r || n != c)) {
                    System.out.println("Matrix no. 2 is of invalid size, please try again;");
                    valid = false;
                }
                if (choice == 2 && n != r) {
                    System.out.println("Matrix no. 2 is of invalid size, please try again:");
                }
            }
            System.out.println("Insert the matrix");
            final Matrix m3 = this.matInput(r, c, scalarChoice);
            if (choice == 1) {
                System.out.println("The solution is:");
                System.out.println(m2.add(m3).toString());
            }
            else {
                System.out.println("The solution is:");
                System.out.println(m2.mul(m3).toString());
            }
            this.play();
        }
        if (choice == 3) {
            System.out.println("Insert the matrix size: m,n");
            while (!valid) {
                final String s = this.input.nextLine();
                if (this.isValidSize(s)) {
                    valid = true;
                    final String[] a = s.split(",");
                    m = Integer.parseInt(a[0]);
                    n = Integer.parseInt(a[1]);
                }
                else {
                    System.out.println("Invalid input, please try again :");
                }
                if (n != m + 1) {
                    System.out.println("Column size must be > than row size by 1");
                    valid = false;
                }
            }
            System.out.println("Insert the matrix");
            final Matrix m2 = this.matInput(m, n, scalarChoice);
            try {
                System.out.println("The solution is:");
                System.out.println(m2.solve().toString());
            }
            catch (RuntimeException e) {
                System.out.println("This matrix has no solution !!!");
            }
            this.play();
        }
        if (choice == 4) {
            System.out.println("Thank you for using this program!");
        }
    }
}
