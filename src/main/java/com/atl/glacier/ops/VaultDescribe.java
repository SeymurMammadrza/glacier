package com.atl.glacier.ops;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glacier.GlacierClient;
import software.amazon.awssdk.services.glacier.model.DescribeVaultRequest;
import software.amazon.awssdk.services.glacier.model.DescribeVaultResponse;
import software.amazon.awssdk.services.glacier.model.GlacierException;

public class VaultDescribe {

    public static void main(String[] args) {

        final String USAGE = "\n" +
                "Usage: " +
                "DescribeVault <vaultName>\n\n" +
                "Where:\n" +
                "  vaultName - the name of the vault to describe.\n\n";

        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String vaultName = args[0];
        GlacierClient glacier = GlacierClient.builder()
                .region(Region.US_EAST_1)
                .build();

        vaultDescribe(glacier, vaultName);
        glacier.close();
    }

    public static void vaultDescribe(GlacierClient glacier, String vaultName) {

        try {
            DescribeVaultRequest describeVaultRequest = DescribeVaultRequest.builder()
                    .vaultName(vaultName)
                    .build();

            DescribeVaultResponse desVaultResult = glacier.describeVault(describeVaultRequest);
            System.out.println("Describing the vault: " + vaultName);
            System.out.print(
                    "CreationDate: " + desVaultResult.creationDate() +
                            "\nLastInventoryDate: " + desVaultResult.lastInventoryDate() +
                            "\nNumberOfArchives: " + desVaultResult.numberOfArchives() +
                            "\nSizeInBytes: " + desVaultResult.sizeInBytes() +
                            "\nVaultARN: " + desVaultResult.vaultARN() +
                            "\nVaultName: " + desVaultResult.vaultName());
        } catch (GlacierException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
