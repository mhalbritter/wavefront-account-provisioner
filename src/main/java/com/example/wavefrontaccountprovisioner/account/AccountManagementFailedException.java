package com.example.wavefrontaccountprovisioner.account;

/**
 * Thrown if managing an account failed.
 *
 * @author Stephane Nicoll
 */
public class AccountManagementFailedException extends RuntimeException {

    public AccountManagementFailedException(String message) {
        super(message);
    }

}
