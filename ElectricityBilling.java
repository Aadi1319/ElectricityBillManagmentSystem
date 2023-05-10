import java.io.*;
import java.util.Scanner;

public class ElectricityBilling {

    private static final double RESIDENTIAL_RATE = 4.5;
    private static final double COMMERCIAL_RATE = 5.5;
    private static final double INDUSTRIAL_PEAK_RATE = 6.5;
    private static final double INDUSTRIAL_OFFPEAK_RATE = 4.5;
    private static final int INDUSTRIAL_PEAK_START_HOUR = 8;
    private static final int INDUSTRIAL_PEAK_END_HOUR = 18;

    
    private static double calculateCharge(double unit, String type) {
        double rate;
        if (type.equals("R")) {
            rate = RESIDENTIAL_RATE;
        } else if (type.equals("C")) {
            rate = COMMERCIAL_RATE;
        } else if (type.equals("I")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter hour of usage (0-23): ");
            int hour = scanner.nextInt();
            scanner.nextLine();
            if (hour >= INDUSTRIAL_PEAK_START_HOUR && hour < INDUSTRIAL_PEAK_END_HOUR) {
                rate = INDUSTRIAL_PEAK_RATE;
            } else {
                rate = INDUSTRIAL_OFFPEAK_RATE;
            }
            scanner.close();
        } else {
            throw new IllegalArgumentException("Invalid type of connection.");
        }

        return unit * rate;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File("electricity-bill.txt"); 

        try {
            if (file.createNewFile()) {
                System.out.println("Created new file: " + file.getName());
            } else {
                System.out.println("Found existing file: " + file.getName());
              
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    System.out.println(line);
                }
                fileScanner.close();
            }

            // get customer information from user
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter unit consumed: ");
            double unit = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter type of connection (R/C/I): ");
            String type = scanner.nextLine();

            double charge = calculateCharge(unit, type);

  
            FileWriter writer = new FileWriter(file, true);
            writer.write("Name: " + name + "\n");
            writer.write("Unit consumed: " + unit + "\n");
            writer.write("Type of connection: " + type + "\n");
            writer.write("Charge: " + charge + "\n\n");
            writer.close();

            System.out.println("\nName: " + name);
            System.out.println("Unit consumed: " + unit);
            System.out.println("Type of connection: " + type);
            System.out.println("Charge: " + charge);
            scanner.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
