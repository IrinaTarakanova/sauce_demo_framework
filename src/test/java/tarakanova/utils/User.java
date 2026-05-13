package tarakanova.utils;

/**
 * User enum defines different user types available for testing in SauceDemo application.
 * Each user type has predefined credentials for various test scenarios.
 *
 * User Types:
 * - STANDARD: Regular user with full access (used for positive test cases)
 * - LOCKED: User account that is locked out (used for negative test cases)
 * - PROBLEM: User with various UI issues (used for error handling tests)
 * - PERFORMANCE: User with performance-related issues (used for performance tests)
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public enum User {
    /**
     * Standard user with valid credentials for successful login scenarios.
     */
    STANDARD("standard_user", "secret_sauce"),

    /**
     * Locked out user for testing account lockout scenarios.
     */
    LOCKED("locked_out_user", "secret_sauce"),

    /**
     * Problem user with various UI issues for error handling tests.
     */
    PROBLEM("problem_user", "secret_sauce"),

    /**
     * Performance glitch user for testing performance-related issues.
     */
    PERFORMANCE("performance_glitch_user", "secret_sauce");

    private final String username;
    private final String password;

    /**
     * Constructor for User enum.
     *
     * @param username User's login username
     * @param password User's login password
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
        // Note: Static logger cannot be used in enum constructor
        // Logging moved to getter methods if needed
    }

    /**
     * Gets the username for this user type.
     *
     * @return Username string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password for this user type.
     *
     * @return Password string
     */
    public String getPassword() {
        return password;
    }
}