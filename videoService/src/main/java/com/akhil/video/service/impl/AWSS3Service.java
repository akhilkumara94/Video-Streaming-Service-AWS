package com.akhil.video.service.impl;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.video.AWSS3Properties;
import com.akhil.video.AWSS3Util;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

@Service("awsS3Service")
public class AWSS3Service {
	final static Logger logger = Logger.getLogger(AWSS3Service.class);

	/**
	 * Uploads the file to the AWS S3 input bucket.
	 * 
	 * @param fileName        The name of the file to be uploaded.
	 * @param fileExtension   The extension of the file to be uploaded.
	 * @param inputStream     Input stream containing the options to upload to
	 *                        Amazon S3.
	 * @param inputStreamSize Size of the input stream.
	 * @return True if the upload was successful, False otherwise.
	 */
	public boolean upload(String fileName, String fileExtension, InputStream inputStream, long inputStreamSize) {
		String fileNameWithExtension = fileName + "." + fileExtension;
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(inputStreamSize);
		Upload upload = null;
		try {
			AmazonS3 s3Client = AWSS3Util.getAWSClient();
			TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3Client).build();
			// TransferManager processes all transfers asynchronously
			AWSS3Properties awss3Properties = new AWSS3Properties();
			upload = tm.upload(awss3Properties.getAWSProperties().getProperty("aws.s3.inputbucket"), fileNameWithExtension,
					inputStream, metadata);
			logger.info("Object upload started");
			upload.waitForCompletion();
			logger.info("Object upload complete");
		} catch (Exception exception) {
			logger.error(exception);
			if (upload != null) {
				upload.abort();
			}
			return false;
		}

		return true;
	}

	/**
	 * Deletes the video from the s3 output bucket which matches the videoId.
	 * 
	 * @param videoId The ID of the video to be deleted.
	 * @return True if the videos are deleted from S3, false otherwise.
	 */
	public boolean delete(String videoId) {
		AWSS3Properties awss3Properties = new AWSS3Properties();
		String outputBucketName = awss3Properties.getAWSProperties().getProperty("aws.s3.outputbucket");
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(outputBucketName)
				.withPrefix(videoId);
		AmazonS3 s3Client = AWSS3Util.getAWSClient();
		try {
			ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);

			while (true) {
				for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
					s3Client.deleteObject(outputBucketName, objectSummary.getKey());
				}
				if (objectListing.isTruncated()) {
					objectListing = s3Client.listNextBatchOfObjects(objectListing);
				} else {
					break;
				}
			}
		} catch (Exception exception) {
			logger.error(exception);
			return false;
		}

		return true;
	}
}
