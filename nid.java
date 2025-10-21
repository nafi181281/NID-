import java.util.*;
import java.text.*;
import java.time.*;

class Citizen {
    String nid;
    String name;
    String dob;
    String gender;
    String address;
    String fatherName;
    String motherName;
    String bloodGroup;
    boolean isActive;
    LocalDateTime createdAt;
    LocalDateTime lastModified;
}

public class NationalIDSystem {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Citizen> citizens = new ArrayList<>();
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final Random random = new Random();

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Default admin login:");
        System.out.println("Username: " + ADMIN_USERNAME);
        System.out.println("Password: " + ADMIN_PASSWORD);

        while (running) {
            System.out.println("\nNATIONAL ID MANAGEMENT SYSTEM");
            System.out.println("1. Admin Login");
            System.out.println("2. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    if (adminLogin()) {
                        adminMenu();
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;
                case "2":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        System.out.println("Exiting...");
    }

    private static boolean adminLogin() {
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        return user.equals(ADMIN_USERNAME) && pass.equals(ADMIN_PASSWORD);
    }

    private static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nADMIN PANEL");
            System.out.println("1. Register Citizen");
            System.out.println("2. View Citizens");
            System.out.println("3. Search Citizen");
            System.out.println("4. Delete Citizen");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": registerCitizen(); break;
                case "2": viewCitizens(); break;
                case "3": searchCitizen(); break;
                case "4": deleteCitizen(); break;
                case "5": running = false; break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void registerCitizen() {
        Citizen c = new Citizen();

        System.out.println("\nEnter Citizen Details:");
        System.out.print("Full Name: "); c.name = sc.nextLine();
        do {
            System.out.print("DOB (DD-MM-YYYY): "); c.dob = sc.nextLine();
        } while (!validateDate(c.dob));
        System.out.print("Gender (Male/Female): "); c.gender = sc.nextLine();
        System.out.print("Address: "); c.address = sc.nextLine();
        System.out.print("Father Name: "); c.fatherName = sc.nextLine();
        System.out.print("Mother Name: "); c.motherName = sc.nextLine();

        String[] validBG = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        boolean valid = false;
        do {
            System.out.print("Blood Group: "); c.bloodGroup = sc.nextLine();
            for (String bg : validBG) {
                if (bg.equalsIgnoreCase(c.bloodGroup)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) System.out.println("Invalid blood group!");
        } while (!valid);

        c.nid = String.format("%010d", random.nextInt(1000000000));
        c.isActive = true;
        c.createdAt = LocalDateTime.now();
        c.lastModified = LocalDateTime.now();

        citizens.add(c);
        System.out.println("Generated NID: " + c.nid);
        System.out.println("Citizen registered successfully!");
    }

    private static void viewCitizens() {
        if (citizens.isEmpty()) {
            System.out.println("No citizens found!");
            return;
        }
        for (Citizen c : citizens) {
            displayCitizen(c);
            System.out.println("-----------------------------");
        }
    }

    private static void searchCitizen() {
        System.out.print("Enter NID: ");
        String nid = sc.nextLine();
        for (Citizen c : citizens) {
            if (c.nid.equals(nid)) {
                displayCitizen(c);
                return;
            }
        }
        System.out.println("Citizen not found!");
    }

    private static void deleteCitizen() {
        System.out.print("Enter NID to delete: ");
        String nid = sc.nextLine();
        Iterator<Citizen> it = citizens.iterator();
        while (it.hasNext()) {
            Citizen c = it.next();
            if (c.nid.equals(nid)) {
                it.remove();
                System.out.println("Citizen deleted.");
                return;
            }
        }
        System.out.println("Citizen not found!");
    }

    private static void displayCitizen(Citizen c) {
        System.out.println("\nNID: " + c.nid);
        System.out.println("Name: " + c.name);
        System.out.println("DOB: " + c.dob);
        System.out.println("Gender: " + c.gender);
        System.out.println("Address: " + c.address);
        System.out.println("Father: " + c.fatherName);
        System.out.println("Mother: " + c.motherName);
        System.out.println("Blood Group: " + c.bloodGroup);
        System.out.println("Status: " + (c.isActive ? "Active" : "Inactive"));
        System.out.println("Created: " + c.createdAt);
        System.out.println("Last Modified: " + c.lastModified);
    }

    private static boolean validateDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            System.out.println("Invalid date format! Use DD-MM-YYYY");
            return false;
        }
    }
}
