package com.memories.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig {
	
	@Value("${amazon.aws.access-key}")
	private String accessKeyId;
	
	@Value("${amazon.aws.secret-key}")
	private String accessKeySecret;
	
	@Value("${amazon.aws.region}")
	private String s3RegionName;
	
	@Bean
	public AmazonS3 getAmazonS3Client() {
		final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
		
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
				.withRegion(s3RegionName)
				.build();
	}

}
