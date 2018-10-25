package utils;

import java.util.Scanner;

public class TextUtils {
    private static Scanner sc = new Scanner(System.in);

    private TextUtils() {
    }

    public static int readInt() {
        int input;
        try {
            System.out.print(">> ");
            input = Integer.parseInt(sc.nextLine());
            return input;
        } catch (NumberFormatException e) {
            printErrMessage("Not a number.");
            return readInt();
        }
    }
    public static double readDouble() {
        double input;
        try {
            System.out.print(">> ");
            input = Double.parseDouble(sc.nextLine());
            return input;
        } catch (NumberFormatException e) {
            printErrMessage("Not a number.");
            return readInt();
        }
    }

    public static void printErrMessage(String message) {
        System.out.print("<!> " + message + "<!>\n");
    }

    public static String readString() {
        System.out.print(">> ");
        return sc.nextLine();
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
