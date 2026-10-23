import java.util.Scanner;
class MyComplex {
    public double re;
    public double im;
    public MyComplex(double r, double i) {
        re = r;
        im = i;
    }
    public MyComplex plusC(MyComplex o) {
        return new MyComplex(this.re + o.re, this.im + o.im);
    }
    public MyComplex minusC(MyComplex o) {
        return new MyComplex(this.re - o.re, this.im - o.im);
    }
    public MyComplex umnC(MyComplex o) {
        double r = this.re * o.re - this.im * o.im;
        double i = this.re * o.im + this.im * o.re;
        return new MyComplex(r, i);
    }
    public MyComplex delC(MyComplex o) {
        double znam = o.re * o.re + o.im * o.im;
        if (znam == 0) {
            System.out.println("Внимание: деление комплексных чисел на 0!");
            return new MyComplex(0, 0);
        }
        double r = (this.re * o.re + this.im * o.im) / znam;
        double i = (this.im * o.re - this.re * o.im) / znam;
        return new MyComplex(r, i);
    }
}
class MyMatrix {
    public int rows;
    public int cols;
    public MyComplex[][] arr;
    public MyMatrix(int r, int c) {
        rows = r;
        cols = c;
        arr = new MyComplex[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr[i][j] = new MyComplex(0, 0);
            }
        }
    }
    public static MyMatrix plus(MyMatrix a, MyMatrix b) {
        MyMatrix res = new MyMatrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                res.arr[i][j] = a.arr[i][j].plusC(b.arr[i][j]);
            }
        }
        return res;
    }
    public static MyMatrix umnozhenie(MyMatrix a, MyMatrix b) {
        MyMatrix res = new MyMatrix(a.rows, b.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                MyComplex sum = new MyComplex(0, 0);
                for (int k = 0; k < a.cols; k++) {
                    MyComplex prod = a.arr[i][k].umnC(b.arr[k][j]);
                    sum = sum.plusC(prod);
                }
                res.arr[i][j] = sum;
            }
        }
        return res;
    }
    public static MyMatrix transponirovanie(MyMatrix a) {
        MyMatrix res = new MyMatrix(a.cols, a.rows);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                res.arr[j][i] = a.arr[i][j];
            }
        }
        return res;
    }
    public static MyComplex opr(MyMatrix a) {
        return computeDet(a.arr);
    }
    private static MyComplex computeDet(MyComplex[][] data) {
        int n = data.length;
        if (n == 1) {
            return data[0][0];
        }
        if (n == 2) {
            MyComplex ch1 = data[0][0].umnC(data[1][1]);
            MyComplex ch2 = data[0][1].umnC(data[1][0]);
            return ch1.minusC(ch2);
        }
        MyComplex det = new MyComplex(0, 0);
        for (int j = 0; j < n; j++) {
            MyComplex[][] sub = getSubMatrix(data, 0, j);
            MyComplex subDet = computeDet(sub);
            MyComplex term = data[0][j].umnC(subDet);
            if (j % 2 == 1) {
                det = det.minusC(term);
            } else {
                det = det.plusC(term);
            }
        }
        return det;
    }
    private static MyComplex[][] getSubMatrix(MyComplex[][] data, int rowToExclude, int colToExclude) {
        int n = data.length;
        MyComplex[][] sub = new MyComplex[n - 1][n - 1];
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
    public static MyMatrix inverse(MyMatrix a) {
        MyComplex det = opr(a);
        int n = a.rows;
        MyMatrix adj = new MyMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                MyComplex[][] sub = getSubMatrix(a.arr, i, j);
                MyComplex cofactor = computeDet(sub);
                if ((i + j) % 2 == 1) {
                    cofactor = new MyComplex(0, 0).minusC(cofactor);
                }
                adj.arr[i][j] = cofactor;
            }
        }
        MyMatrix adjT = transponirovanie(adj);
        MyMatrix inv = new MyMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv.arr[i][j] = adjT.arr[i][j].delC(det);
            }
        }
        return inv;
    }
    public static MyMatrix delenie(MyMatrix a, MyMatrix b) {
        MyComplex det = opr(b);
        if (det.re == 0 && det.im == 0) {
            System.out.println("Ошибка! Определитель делителя равен 0, делить нельзя!");
            return null;
        }
        MyMatrix obrB = inverse(b);
        return umnozhenie(a, obrB);
    }
    public void pechat() {
        System.out.println("Результат:");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print("(" + this.arr[i][j].re + " + " + this.arr[i][j].im + "i)\t");
            }
            System.out.println();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== МЕНЮ ===");
        System.out.println("1 - Сложение");
        System.out.println("2 - Умножение");
        System.out.println("3 - Деление (через обратную)");
        System.out.println("4 - Транспонирование");
        System.out.println("5 - Определитель");
        System.out.print("Выберите действие: ");
        int choice = sc.nextInt();
        System.out.println("\n--- Шаг 1: Ввод матрицы А ---");
        System.out.print("Введите количество строк матрицы А: ");
        int rA = sc.nextInt();
        System.out.print("Введите количество столбцов матрицы А: ");
        int cA = sc.nextInt();
        MyMatrix a = new MyMatrix(rA, cA);
        zapolnitElementi(a, sc);
        if (choice == 1 || choice == 2 || choice == 3) {
            System.out.println("\n--- Шаг 2: Проверка размеров матрицы Б ---");
            System.out.print("Введите количество строк матрицы Б: ");
            int rB = sc.nextInt();
            System.out.print("Введите количество столбцов матрицы Б: ");
            int cB = sc.nextInt();
            if (choice == 1) {
                if (rA != rB || cA != cB) {
                    System.out.println("Ошибка! Для сложения размеры матриц должны быть строго одинаковыми (" + rA + "x" + cA + ")!");
                    return;
                }
            }
            else if (choice == 2) {
                if (cA != rB) {
                    System.out.println("Ошибка! Столбцы матрицы А (" + cA + ") должны быть равны строкам матрицы Б (" + rB + ")!");
                    return;
                }
            }
            else if (choice == 3) {
                if (rB != cB) {
                    System.out.println("Ошибка! Матрица Б (делитель) должна быть строго квадратной!");
                    return;
                }
                if (cA != rB) {
                    System.out.println("Ошибка! Размеры несовместимы. Столбцы А (" + cA + ") должны быть равны строкам Б (" + rB + ")!");
                    return;
                }
            }
            System.out.println("\nРазмеры подходят! Заполните элементы матрицы Б:");
            MyMatrix b = new MyMatrix(rB, cB);
            zapolnitElementi(b, sc);
            if (choice == 1) MyMatrix.plus(a, b).pechat();
            if (choice == 2) MyMatrix.umnozhenie(a, b).pechat();
            if (choice == 3) {
                MyMatrix res = MyMatrix.delenie(a, b);
                if (res != null) res.pechat();
            }
        }
        else {
            if (choice == 4) {
                MyMatrix.transponirovanie(a).pechat();
            }
            else if (choice == 5) {
                if (rA != cA) {
                    System.out.println("Ошибка! Определитель можно вычислить только для квадратной матрицы!");
                } else {
                    MyComplex det = MyMatrix.opr(a);
                    System.out.println("Определитель матрицы А = " + det.re + " + (" + det.im + "i)");
                }
            }
        }
    }
    public static void zapolnitElementi(MyMatrix m, Scanner sc) {
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                System.out.print("Элемент [" + i + "][" + j + "] (введите Re и Im через пробел): ");
                double real = sc.nextDouble();
                double imag = sc.nextDouble();
                m.arr[i][j] = new MyComplex(real, imag);
            }
        }
    }
}
