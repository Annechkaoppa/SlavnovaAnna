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
        if (a.rows != a.cols) {
            System.out.println("Ошибка! Определитель можно вычислить только для квадратной матрицы!");
            return null;
        }
        return computeDet(a.arr);
    }
    private static Complex computeDet(Complex[][] data) {
        int n = data.length;
        if (n == 1) {
            return data[0][0];
        }
        if (n == 2) {
            Complex ch1 = data[0][0].umnC(data[1][1]);
            Complex ch2 = data[0][1].umnC(data[1][0]);
            return ch1.minusC(ch2);
        }
        Complex det = new Complex(0, 0);
        for (int j = 0; j < n; j++) {
            Complex[][] sub = getSubMatrix(data, 0, j);
            Complex subDet = computeDet(sub);
            Complex term = data[0][j].umnC(subDet);
            if (j % 2 == 1) {
                det = det.minusC(term);
            } else {
                det = det.plusC(term);
            }
        }
        return det;
    }
    private static Complex[][] getSubMatrix(Complex[][] data, int rowToExclude, int colToExclude) {
        int n = data.length;
        Complex[][] sub = new Complex[n - 1][n - 1];
        int r = -1;
        for (int i = 0; i < n; i++) {
            if (i == rowToExclude) continue;
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) {
                if (j == colToExclude) continue;
                c++;
                sub[r][c] = data[i][j];
            }
        }
        return sub;
    }
    public static Matrix inverse(Matrix a) {
        if (a.rows != a.cols) {
            System.out.println("Ошибка! Обратную матрицу можно найти только для квадратной матрицы!");
            return null;
        }
        Complex det = opr(a);
        if (det.re == 0 && det.im == 0) {
            System.out.println("Ошибка! Определитель равен 0, обратной матрицы не существует!");
            return null;
        }

        int n = a.rows;
        Matrix adj = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Complex[][] sub = getSubMatrix(a.arr, i, j);
                Complex cofactor = computeDet(sub);
                if ((i + j) % 2 == 1) {
                    cofactor = new Complex(0, 0).minusC(cofactor);
                }
                adj.arr[i][j] = cofactor;
            }
        }
        Matrix adjT = transponirovanie(adj);
        Matrix inv = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv.arr[i][j] = adjT.arr[i][j].delC(det);
            }
        }
        return inv;
    }
    public static Matrix delenie(Matrix a, Matrix b) {
        if (b.rows != b.cols) {
            System.out.println("Ошибка! Матрица-делитель Б должна быть квадратной!");
            return null;
        }
        if (a.cols != b.rows) {
            System.out.println("Ошибка! Размеры несовместимы для умножения на обратную матрицу!");
            return null;
        }
        Matrix obrB = inverse(b);
        if (obrB == null) return null;
        return umnozhenie(a, obrB);
    }
    public void pechat() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print("(" + this.arr[i][j].re + " + " + this.arr[i][j].im + "i)\t");
            }
            System.out.println();
        }
    }
}
