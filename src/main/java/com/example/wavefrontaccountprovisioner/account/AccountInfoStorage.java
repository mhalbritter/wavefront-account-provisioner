package com.example.wavefrontaccountprovisioner.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.prefs.Preferences;

/**
 * @author Moritz Halbritter
 */
public class AccountInfoStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountInfoStorage.class);

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(AccountInfoStorage.class);
    private static final String API_TOKEN_KEY = "apiToken";
    private static final String LOGIN_URL_KEY = "loginUrl";

    @Nullable
    public AccountInfo load(String applicationName, String serviceName, String clusterUri) {
        Key key = new Key(applicationName, serviceName, clusterUri);
        LOGGER.debug("Loading AccountInfo for key {}", key);
        String apiToken = PREFERENCES.get(key(API_TOKEN_KEY, key), null);
        String loginUrl = PREFERENCES.get(key(LOGIN_URL_KEY, key), null);

        if (apiToken == null || loginUrl == null) {
            LOGGER.debug("No AccountInfo found");
            return null;
        }

        AccountInfo result = new AccountInfo(apiToken, loginUrl);
        LOGGER.debug("AccountInfo found: {}", result);
        return result;
    }

    public void save(AccountInfo accountInfo, String applicationName, String serviceName, String clusterUri) {
        Key key = new Key(applicationName, serviceName, clusterUri);
        LOGGER.debug("Saving AccountInfo: {} for key {}", accountInfo, key);
        PREFERENCES.put(key(API_TOKEN_KEY, key), accountInfo.apiToken());
        PREFERENCES.put(key(LOGIN_URL_KEY, key), accountInfo.loginUrl());
    }

    private String key(String name, Key key) {
        return name + "-" + key.suffix();
    }

    private record Key(String applicationName, String serviceName, String clusterUri) {
        String suffix() {
            return applicationName + "-" + serviceName + "-" + clusterUri;
        }
    }
}
