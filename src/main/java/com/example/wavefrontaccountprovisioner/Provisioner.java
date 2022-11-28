package com.example.wavefrontaccountprovisioner;

import com.example.wavefrontaccountprovisioner.account.AccountInfo;
import com.example.wavefrontaccountprovisioner.account.AccountInfoStorage;
import com.example.wavefrontaccountprovisioner.account.AccountManagementClient;
import com.example.wavefrontaccountprovisioner.account.ApplicationTags;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Moritz Halbritter
 */
@Component
class Provisioner implements ApplicationRunner {
    private static final String DEFAULT_CLUSTER_URI = "https://wavefront.surf";
    private static final String DEFAULT_APPLICATION_NAME = "unnamed_application";
    public static final String DEFAULT_SERVICE_NAME = "unnamed_service";

    private final AccountInfoStorage accountInfoStorage;
    private final AccountManagementClient accountManagementClient;

    Provisioner(AccountInfoStorage accountInfoStorage, AccountManagementClient accountManagementClient) {
        this.accountInfoStorage = accountInfoStorage;
        this.accountManagementClient = accountManagementClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean force = args.containsOption("force");
        String applicationName = getArgument(args, "application", DEFAULT_APPLICATION_NAME);
        String serviceName = getArgument(args, "service", DEFAULT_SERVICE_NAME);
        String clusterUri = getArgument(args, "cluster", DEFAULT_CLUSTER_URI);

        AccountInfo accountInfo = force ? null : accountInfoStorage.load(applicationName, serviceName, clusterUri);
        if (accountInfo != null) {
            printAccountInfo("Found stored account info.", applicationName, serviceName, clusterUri, accountInfo);
            return;
        }

        ApplicationTags applicationTags = new ApplicationTags(applicationName, serviceName, null, null);

        System.out.println("Running Wavefront provision, please wait ...");
        accountInfo = accountManagementClient.provisionAccount(clusterUri, applicationTags);
        accountInfoStorage.save(accountInfo, applicationName, serviceName, clusterUri);

        printAccountInfo("Provision successful.", applicationName, serviceName, clusterUri, accountInfo);
    }

    private static String getArgument(ApplicationArguments args, String name, String def) {
        List<String> value = args.getOptionValues(name);
        if (value == null) {
            value = List.of(def);
        }
        return value.get(0);
    }

    private static void printAccountInfo(String prefix, String applicationName, String serviceName, String clusterUri, AccountInfo accountInfo) {
        System.out.printf("%s Put these properties in your application.properties:%n", prefix);
        System.out.println();
        System.out.printf("management.wavefront.api-token=%s%n", accountInfo.apiToken());
        System.out.printf("management.wavefront.uri=%s%n", clusterUri);
        System.out.printf("management.wavefront.application.service-name=%s%n", serviceName);
        System.out.printf("management.wavefront.application.name=%s%n", applicationName);
        System.out.println();
        System.out.printf("You can login via this url: %s%n", accountInfo.loginUrl());
    }
}
