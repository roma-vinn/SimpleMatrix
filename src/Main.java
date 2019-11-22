public class Main {

    public static void main(String[] args) {
        double[][] data1 = {{3, 2}, {4, 3}};
        ArrayMatrix arrayMatrix1 = new ArrayMatrix(data1);

        double[][] data2 = {{18}, {25}};
        ArrayMatrix arrayMatrix2 = new ArrayMatrix(data2);

        System.out.println("A * X = B, where\nA = ");
        arrayMatrix1.show();
        System.out.println("B = ");
        arrayMatrix2.show();

        System.out.println("Answer is:\nX = ");
        arrayMatrix1.inverse().dot(arrayMatrix2).show();
    }
}
