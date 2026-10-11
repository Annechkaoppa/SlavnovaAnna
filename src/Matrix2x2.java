public class Matrix2x2 {
    public Complex[][] arr = new Complex[2][2];
    public static Matrix2x2 plus(Matrix2x2 a, Matrix2x2 b) {
        Matrix2x2 res = new Matrix2x2();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                res.arr[i][j] = a.arr[i][j].plusC(b.arr[i][j]);
            }
        }
        return res;
    }
    public static Matrix2x2 umnozhenie(Matrix2x2 a, Matrix2x2 b) {
        Matrix2x2 res = new Matrix2x2();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Complex ch1 = a.arr[i][0].umnC(b.arr[0][j]);
                Complex ch2 = a.arr[i][1].umnC(b.arr[1][j]);
                res.arr[i][j] = ch1.plusC(ch2);
            }
        }
        return res;
    }
    public static Matrix2x2 transponirovanie(Matrix2x2 a) {
        Matrix2x2 res = new Matrix2x2();
        res.arr[0][0] = a.arr[0][0];
        res.arr[0][1] = a.arr[1][0];
        res.arr[1][0] = a.arr[0][1];
        res.arr[1][1] = a.arr[1][1];
        return res;
    }
    public static Complex opr(Matrix2x2 a) {
        Complex ch1 = a.arr[0][0].umnC(a.arr[1][1]);
        Complex ch2 = a.arr[0][1].umnC(a.arr[1][0]);
        return new Complex(ch1.re - ch2.re, ch1.im - ch2.im);
    }
    public static Matrix2x2 delenie(Matrix2x2 a, Matrix2x2 b) {
        Complex det = opr(b);
        if (det.re == 0 && det.im == 0) {
            System.out.println("Ошибка! Определитель делителя равен 0");
            return null;
        }
        Matrix2x2 obrB = new Matrix2x2();
        obrB.arr[0][0] = b.arr[1][1].delC(det);
        obrB.arr[1][1] = b.arr[0][0].delC(det);
        Complex min1 = new Complex(-1, 0);
        obrB.arr[0][1] = b.arr[0][1].umnC(min1).delC(det);
        obrB.arr[1][0] = b.arr[1][0].umnC(min1).delC(det);
        return umnozhenie(a, obrB);
    }
}
