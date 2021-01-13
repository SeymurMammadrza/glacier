package com.atl.glacier.ops;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glacier.GlacierClient;
import software.amazon.awssdk.services.glacier.model.GlacierException;
import software.amazon.awssdk.services.glacier.model.InitiateJobRequest;
import software.amazon.awssdk.services.glacier.model.InitiateJobResponse;
import software.amazon.awssdk.services.glacier.model.JobParameters;


public class ArchiveDownload {
    public static void main(String[] args) {

        final String USAGE = "\n" +
                "ArchiveDownload - start a job to retrieve vault inventory\n\n" +
                "Usage: ArchiveDownload <vaultName> <accountId>\n\n" +
                "Where:\n" +
                "  vaultName - the name of the vault.\n" +
                "  accountId - the account ID value.\n\n";

        if (args.length != 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String vaultName = args[0];
        String accountId = args[1];

        GlacierClient glacier = GlacierClient.builder()
                .region(Region.US_EAST_1)
                .build();

        createJob(glacier, vaultName, accountId) ;
        glacier.close();
    }

    public static void createJob(GlacierClient glacier, String vaultName, String accountId) {

        try {

            JobParameters job = JobParameters.builder()
                    .type("inventory-retrieval")
                    .build();

            InitiateJobRequest initJob = InitiateJobRequest.builder()
                    .jobParameters(job)
                    .accountId(accountId)
                    .vaultName(vaultName)
                    .build();

            InitiateJobResponse response = glacier.initiateJob(initJob);

            System.out.println("The job ID is: " +response.jobId()) ;
            System.out.println("The relative URI path of the job is: " +response.location()) ;

        } catch(GlacierException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);

        }
        System.out.println("Done");
    }
}


