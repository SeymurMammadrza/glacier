package com.atl.glacier.ops;

import com.amazonaws.services.glacier.AmazonGlacierClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glacier.GlacierClient;
import software.amazon.awssdk.services.glacier.model.DeleteVaultRequest;
import software.amazon.awssdk.services.glacier.model.DeleteVaultResponse;
import software.amazon.awssdk.services.glacier.model.GlacierException;

public class VaultDelete {

    public static AmazonGlacierClient client;

    public static void main(String[] args) {

        final String USAGE = "\n" +
                "Usage: " +
                "DeleteVault <vaultName>\n\n" +
                "Where:\n" +
                "  vaultName - the name of the vault to delete. \n\n";

        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String vaultName = args[0];
        GlacierClient glacier = GlacierClient.builder()
                .region(Region.US_EAST_1)
                .build();

        vaultDelete(glacier, vaultName);
        glacier.close();
    }

    public static void vaultDelete(GlacierClient glacier, String vaultName) {

        try {
            DeleteVaultRequest delVaultRequest = DeleteVaultRequest.builder()
                    .vaultName(vaultName)
                    .build();

            DeleteVaultResponse delVaultResult = glacier.deleteVault(delVaultRequest);
            System.out.println("The vault was deleted!");
        } catch (GlacierException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}