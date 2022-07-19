package com.akhil.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * Singleton instance of the amazon client.
 * 
 * @author akhil
 */
@Component
public class AWSS3Util {
	private static AmazonS3 awsS3Client;

	/**
	 * Private class to prevent instantiation.
	 */
	private AWSS3Util() {
	}

	/**
	 * Returns the instance of AWS S3 client. Creates the client only if it's not
	 * created before.
	 * 
	 * @return The {@link AmazonS3} instance to access S3 bucket.
	 */
	public static synchronized AmazonS3 getAWSClient() {
		if (awsS3Client == null) {
			awsS3Client = createAWSS3Client(createAWSCredentials(), Regions.US_EAST_1);
		}

		return awsS3Client;
	}

	/**
	 * Creates the AWS basic credentials using the properties.
	 * 
	 * @return The basic AWS Credentials.
	 */
	private static BasicAWSCredentials createAWSCredentials() {
		AWSS3Properties awss3Properties = new AWSS3Properties();
		return new BasicAWSCredentials(awss3Properties.getAWSProperties().getProperty("aws.accesskey"),
				awss3Properties.getAWSProperties().getProperty("aws.secretkey"));
	}

	/**
	 * Creates the Amazon S3 client using the credentials and region.
	 * 
	 * @param credentials  The AWS Credentials.
	 * @param clientRegion The region of the S3 bucket.
	 * @return The {@link AmazonS3} client.
	 */
	private static AmazonS3 createAWSS3Client(AWSCredentials credentials, Regions clientRegion) {
		return AmazonS3ClientBuilder.standard().withRegion(clientRegion)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	}
}
