package com.example.datapersistence_arifmiwa;

import com.example.datapersistence_arifmiwa.model.Customer;
import com.example.datapersistence_arifmiwa.service.CustomerRepositoryImplementation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
public class DatapersistenceArifmiwaApplication implements CommandLineRunner {
    private static final Scanner scanner = new Scanner(System.in);
    private final CustomerRepositoryImplementation customerRepositoryImplementation;

    public DatapersistenceArifmiwaApplication(CustomerRepositoryImplementation customerRepositoryImplementation) {
        this.customerRepositoryImplementation = customerRepositoryImplementation;
    }

    public static void main(String[] args) {
        SpringApplication.run(DatapersistenceArifmiwaApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        int choice = -1;
        while (choice != 0) {
            System.out.println("****************************************");
            System.out.println("________________MENU________________");
            System.out.println("****************************************");
            System.out.println("Select an option:");
            System.out.println("1. List all customers");
            System.out.println("2. Find customer by ID");
            System.out.println("3. Find customer by name");
            System.out.println("4. List customers with limit and offset");
            System.out.println("5. Create a new customer");
            System.out.println("6. Update an existing customer");
            System.out.println("7. Country with most customer");
            System.out.println("9. Customer with max genre");
            System.out.println("10. Delete a customer");
            System.out.println("0. Exit");
            System.out.println("****************************************");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    List<Customer> customers = customerRepositoryImplementation.findAll();
                    System.out.println("****************************************");
                    customers.forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Enter customer ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = customerRepositoryImplementation.findById(id);
                    System.out.println("****************************************");
                    System.out.println(customer);
                    break;
                case 3:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    Set<Customer> customers1 = customerRepositoryImplementation.findByName(name);
                    System.out.println("****************************************");
                    customers1.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Enter limit: ");
                    int limit = scanner.nextInt();
                    System.out.print("Enter offset: ");
                    int offset = scanner.nextInt();
                    customers = customerRepositoryImplementation.findAllWithLimit(limit, offset);
                    System.out.println("****************************************");
                    customers.forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter company: ");
                    String company = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter city: ");
                    String city = scanner.nextLine();
                    System.out.print("Enter state: ");
                    String state = scanner.nextLine();
                    System.out.print("Enter country: ");
                    String country = scanner.nextLine();
                    System.out.print("Enter postal code: ");
                    String postalCode = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter fax: ");
                    String fax = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    Customer newCustomer = new Customer(firstName, lastName, country, postalCode, phone, fax, email);
                    customerRepositoryImplementation.save(newCustomer);
                    System.out.println("****************************************");
                    System.out.println("New customer '" + newCustomer.getFirst_name() + " " + newCustomer.getLast_name() + "' is created!");
                    break;
                case 6:
                    System.out.print("Enter customer ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    customer = customerRepositoryImplementation.findById(id);
                    if (customer == null) {
                        System.out.println("Customer not found");
                    } else {
                        System.out.println("Enter new customer information:");
                        System.out.print("First name [" + customer.getFirst_name() + "]: ");
                        firstName = scanner.nextLine();
                        firstName = firstName.isEmpty() ? customer.getFirst_name() : firstName;

                        System.out.print("Last name [" + customer.getLast_name() + "]: ");
                        lastName = scanner.nextLine();
                        lastName = lastName.isEmpty() ? customer.getLast_name() : lastName;

                        System.out.print("Country [" + customer.getCountry() + "]: ");
                        country = scanner.nextLine();
                        country = country.isEmpty() ? customer.getCountry() : country;

                        System.out.print("Postal code [" + customer.getPostal_code() + "]: ");
                        postalCode = scanner.nextLine();
                        postalCode = postalCode.isEmpty() ? customer.getPostal_code() : postalCode;

                        System.out.print("Phone [" + customer.getPhone() + "]: ");
                        phone = scanner.nextLine();
                        phone = phone.isEmpty() ? customer.getPhone() : phone;

                        System.out.print("Fax [" + customer.getFax() + "]: ");
                        fax = scanner.nextLine();
                        fax = fax.isEmpty() ? customer.getFax() : fax;

                        System.out.print("Email [" + customer.getEmail() + "]: ");
                        email = scanner.nextLine();
                        email = email.isEmpty() ? customer.getEmail() : email;

                        Customer updatedCustomer = new Customer(id, firstName, lastName, country, postalCode, phone, fax, email);
                        customerRepositoryImplementation.update(updatedCustomer);
                        System.out.println("****************************************");
                        System.out.println("Customer updated successfully");
                    }
                    break;
                case 7:
                    System.out.println("****************************************");
                    customerRepositoryImplementation.getCountryWithMostCustomers();
                    break;

                case 9:
                    System.out.println("****************************************");
                    System.out.print("Enter customer ID: ");
                    int id1 = scanner.nextInt();
                    List<String> genre = customerRepositoryImplementation.getMostPopularGenres(id1);
                    genre.forEach(System.out::println);

                    break;
                case 10:
                    System.out.print("Enter customer ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    customerRepositoryImplementation.delete(id);
                    System.out.println("****************************************");
                    System.out.println("Deleted customer successfully");
                    break;


                default:
                    System.out.println("Wrong choice!");
            }
        }
    }

}
