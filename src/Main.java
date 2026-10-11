import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Сложение");
        System.out.println("2 - Умножение");
        System.out.println("3 - Деление");
        System.out.println("4 - Транспонирование");
        System.out.println("5 - Определитель");
        System.out.print("Выберите действие: ");
        int choice = sc.nextInt();
        System.out.println("Ввод матрицы А:");
        Matrix2x2 a = vvodMatrici(sc);
        if (choice == 1) {
            System.out.println("Ввод матрицы Б:");
            Matrix2x2 b = vvodMatrici(sc);
            Matrix2x2 res = Matrix2x2.plus(a, b);
            pechat(res);
        }
        else if (choice == 2) {
            System.out.println("Ввод матрицы Б:");
            Matrix2x2 b = vvodMatrici(sc);
            Matrix2x2 res = Matrix2x2.umnozhenie(a, b);
            pechat(res);
        }
        else if (choice == 3) {
            System.out.println("Ввод матрицы Б:");
            Matrix2x2 b = vvodMatrici(sc);
            Matrix2x2 res = Matrix2x2.delenie(a, b);
            if (res != null) {
                pechat(res);
            }
        }
        else if (choice == 4) {
            Matrix2x2 res = Matrix2x2.transponirovanie(a);
            pechat(res);
        }
        else if (choice == 5) {
            Complex det = Matrix2x2.opr(a);
            System.out.println("Определитель = " + det.re + " + " + det.im + "i");
        }
    }
    public static Matrix2x2 vvodMatrici(Scanner sc) {
        Matrix2x2 m = new Matrix2x2();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print("Элемент [" + i + "][" + j + "] (введите вещественную и мнимую части через пробел): ");
                double r = sc.nextDouble();
                double im = sc.nextDouble();
                m.arr[i][j] = new Complex(r, im);
            }
        }
        return m;
    }
    public static void pechat(Matrix2x2 m) {
        for (int i = 0; i < 2; i++) {
            System.out.println("(" + m.arr[i][0].re + " + " + m.arr[i][0].im + "i)\t" +
                    "(" + m.arr[i][1].re + " + " + m.arr[i][1].im + "i)");
        }
    }
}