package com.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class TestDataGenerator {

    private static final Faker faker = new Faker(new Locale("en-US"));

    /**
     * Generates a fake first name
     */
    public static String generateFirstName() {
        return faker.name().firstName();
    }

    /**
     * Generates a fake last name
     */
    public static String generateLastName() {
        return faker.name().lastName();
    }

    /**
     * Generates a random 12-character password
     */
    public static String generateRandomPassword() {
        String password = "";
        for (int i = 0; i < 12; i++) {
            password += (char) (33 + (int) (Math.random() * 94));
        }
        return password;
    }

    /**
     * Generates complete user registration data
     */
    public static class UserData {
        public String firstName;
        public String lastName;
        public String email;
        public String password;

        public UserData(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
        }

        @Override
        public String toString() {
            return "UserData{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    /**
     * Generates complete user registration data with fake information
     */
    public static UserData generateUserData() {
        String firstName = generateFirstName();
        String lastName = generateLastName();
        String email = firstName.toLowerCase() + "+" + lastName.toLowerCase() + "@gmail.com";
        String password = generateRandomPassword();
        return new UserData(firstName, lastName, email, password);
    }
}
