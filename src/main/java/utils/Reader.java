package utils;

import java.util.Scanner;

public class Reader {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(){
        return  scanner.nextInt();
    }
    public static float readFloat(){
        return  scanner.nextFloat();
    }
}
