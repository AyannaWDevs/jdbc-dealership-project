package com.pluralsight.dealership;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final VehicleDao vehicleDao;
    private final LeaseContractDao contractDao;

    public UserInterface() {
        this.vehicleDao = new VehicleDaoImpl("jdbc:mysql://localhost:3306/cardealership", "root", "yearup");
        this.contractDao = new LeaseContractDaoImpl("jdbc:mysql://localhost:3306/cardealership", "root", "yearup");
    }

    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View All Vehicles");
            System.out.println("2. Search for a Vehicle");
            System.out.println("3. Admin Menu");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayVehicles(vehicleDao.findAllVehicles());
                    break;
                case "2":
                    displaySearchMenu(scanner);
                    break;
                case "3":
                    displayAdminMenu(scanner);
                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayAdminMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. View All Contracts");
            System.out.println("4. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addVehicle(scanner);
                    break;
                case "2":
                    removeVehicle(scanner);
                    break;
                case "3":

                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displaySearchMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Search Menu ---");
            System.out.println("1. By Price Range");
            System.out.println("2. By Make and Model");
            System.out.println("3. By Year");
            System.out.println("4. By Color");
            System.out.println("5. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter minimum price: ");
                    double minPrice = scanner.nextDouble();
                    System.out.print("Enter maximum price: ");
                    double maxPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    displayVehicles(vehicleDao.findVehicleByPrice(minPrice, maxPrice));
                    break;
                case "2":
                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();
                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();
                    displayVehicles(vehicleDao.findVehicleByMakeModel(make, model));
                    break;
                case "3":
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    displayVehicles(vehicleDao.findVehicleByYear(year));
                    break;
                case "4":
                    System.out.print("Enter color: ");
                    String color = scanner.nextLine();
                    displayVehicles(vehicleDao.findVehicleByColor(color));
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addVehicle(Scanner scanner) {
        System.out.print("Enter VIN: ");
        String vin = scanner.nextLine();
        System.out.print("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Make: ");
        String make = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Vehicle Body Style: ");
        String bodyStyle = scanner.nextLine();
        System.out.print("Enter Color: ");
        String color = scanner.nextLine();
        System.out.print("Enter Mileage: ");
        int mileage = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        //String vin, String make, String model, int year, double price, boolean sold, String color, String bodyStyle)

        Vehicle vehicle = new Vehicle(vin, make, model, year, price, false, color, bodyStyle, mileage);
        boolean success = vehicleDao.addVehicle(vehicle);
        System.out.println(success ? "Vehicle added successfully!" : "Failed to add vehicle.");
    }

    private void removeVehicle(Scanner scanner) {
        System.out.print("Enter VIN of the vehicle to remove: ");
        String vin = scanner.nextLine();
        boolean success = vehicleDao.deleteVehicle(vin);
        System.out.println(success ? "Vehicle removed successfully!" : "Failed to remove vehicle.");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            System.out.println("\n--- Vehicles ---");
            vehicles.forEach(System.out::println);
        }
    }

    private void displayContracts(List<LeaseContract> leaseContracts) {
        if (leaseContracts.isEmpty()) {
            System.out.println("No contracts found.");
        } else {
            System.out.println("\n--- Contracts ---");
            leaseContracts.forEach(System.out::println);
        }
    }
}
