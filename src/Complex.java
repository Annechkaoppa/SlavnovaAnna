public class Complex {
    public double re;
    public double im;
    public Complex(double r, double i) {
        re = r;
        im = i;
    }
    public Complex plusC(Complex o) {
        return new Complex(this.re + o.re, this.im + o.im);
    }
    public Complex minusC(Complex o) {
        return new Complex(this.re - o.re, this.im - o.im);
    }
    public Complex umnC(Complex o) {
        double r = this.re * o.re - this.im * o.im;
        double i = this.re * o.im + this.im * o.re;
        return new Complex(r, i);
    }
    public Complex delC(Complex o) {
        double znam = o.re * o.re + o.im * o.im;
        if (znam == 0) {
            System.out.println("Внимание: деление комплексных чисел на 0!");
            return new Complex(0, 0);
        }
        double r = (this.re * o.re + this.im * o.im) / znam;
        double i = (this.im * o.re - this.re * o.im) / znam;
        return new Complex(r, i);
    }
}
