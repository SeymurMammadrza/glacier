package com.atl.glacier.ops;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DescribeVaultRequest;
import com.amazonaws.services.glacier.model.DescribeVaultResult;

import java.io.IOException;

public class VaultDescribe {
    public static AmazonGlacierClient client;

    public static void main(String[] args) throws IOException {

        ProfileCredentialsProvider credentials = new ProfileCredentialsProvider();

        client = new AmazonGlacierClient(credentials);
        client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

        String vaultName = "exampleVault";

        try {
            describeVault(client, vaultName);

        } catch (Exception e) {
            System.err.println("Vault operation failed." + e.getMessage());
        }
    }
    private static void describeVault(AmazonGlacierClient client, String vaultName) {
        DescribeVaultRequest describeVaultRequest = new DescribeVaultRequest()
                .withVaultName(vaultName);
        DescribeVaultResult describeVaultResult  = client.describeVault(describeVaultRequest);

        System.out.println("Describing the vault: " + vaultName);
        System.out.print(
                "CreationDate: " + describeVaultResult.getCreationDate() +
                        "\nLastInventoryDate: " + describeVaultResult.getLastInventoryDate() +
                        "\nNumberOfArchives: " + describeVaultResult.getNumberOfArchives() +
                        "\nSizeInBytes: " + describeVaultResult.getSizeInBytes() +
                        "\nVaultARN: " + describeVaultResult.getVaultARN() +
                        "\nVaultName: " + describeVaultResult.getVaultName());
    }
}
