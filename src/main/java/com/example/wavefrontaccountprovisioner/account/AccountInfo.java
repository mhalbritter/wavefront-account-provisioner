package com.example.wavefrontaccountprovisioner.account;

/**
 * Information of a provisioned account.
 *
 * @author Stephane Nicoll
 */
public record AccountInfo(String apiToken, String loginUrl) {
}
