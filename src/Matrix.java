public class Matrix {
    public int rows;
    public int cols;
    public Complex[][] arr;
    public Matrix(int r, int c) {
        rows = r;
        cols = c;
        arr = new Complex[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr[i][j] = new Complex(0, 0);
            }
        }
    }
    public static Matrix plus(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            System.out.println("Ошибка! Размеры матриц должны быть одинаковыми!");
            return null;
        }
        Matrix res = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                res.arr[i][j] = a.arr[i][j].plusC(b.arr[i][j]);
            }
        }
        return res;
    }
    public static Matrix umnozhenie(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            System.out.println("Ошибка! Столбцы матрицы А должны быть равны строкам матрицы Б!");
            return null;
        }
        Matrix res = new Matrix(a.rows, b.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < a.cols; k++) {
                    Complex prod = a.arr[i][k].umnC(b.arr[k][j]);
                    sum = sum.plusC(prod);
                }
                res.arr[i][j] = sum;
            }
        }
        return res;
    }
    public static Matrix transponirovanie(Matrix a) {
        Matrix res = new Matrix(a.cols, a.rows);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                res.arr[j][i] = a.arr[i][j];
            }
        }
        return res;
    }
    public static Complex opr(Matrix a) {
        if (a.rows != 2 || a.cols != 2) {
            System.out.println("Ошибка! В этой простой версии определитель считается только для 2х2!");
            return null;
        }
        Complex ch1 = a.arr[0][0].umnC(a.arr[1][1]);
        Complex ch2 = a.arr[0][1].umnC(a.arr[1][0]);
        return ch1.minusC(ch2);
    }
    public static Matrix delenie(Matrix a, Matrix b) {
        if (b.rows != 2 || b.cols != 2) {
            System.out.println("Ошибка! Деление доступно только для матриц делителей размера 2х2!");
            return null;
        }
        Complex det = opr(b);
        if (det.re == 0 && det.im == 0) {
            System.out.println("Ошибка! Определитель делителя равен 0, делить нельзя!");
            return null;
        }
        Matrix obrB = new Matrix(2, 2);
        obrB.arr[0][0] = b.arr[1][1].delC(det);
        obrB.arr[1][1] = b.arr[0][0].delC(det);
        Complex min1 = new Complex(-1, 0);
        obrB.arr[0][1] = b.arr[0][1].umnC(min1).delC(det);
        obrB.arr[1][0] = b.arr[1][0].umnC(min1).delC(det);
        return umnozhenie(a, obrB);
    }
}
