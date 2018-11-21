package dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

import com.amazonaws.services.s3.AmazonS3;



import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import bean.UserBean;


public class S3Dao {
	
	
	public AmazonS3 s3client ;
	String bucketName;
	String awsAccessKey;
	String awsSecretKey;
	String regionName;
	
	public S3Dao() 
	{
		System.out.println("in dao");
		// TODO Auto-generated constructor stub
		awsAccessKey = "ACCESSKEY";
	    
	    /** Put your secret key here **/
	    awsSecretKey = "SECRETKEY";
	    
	    /** Put your bucket name here **/
	    bucketName = "tokendatabase";
	    
	    /** The name of the region where the bucket is created. (e.g. us-west-1) **/
	    regionName = "us-east-2";
		System.out.println("here1");
	    AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
            System.out.println("here1.1");
        } catch (Exception e) {
        	System.out.println("here1.2");
            System.out.println(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.");
        }
        System.out.println("here2");
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        s3client = AmazonS3ClientBuilder.standard().withRegion(regionName)
                                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                                .build();
        /*s3client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(regionName)
            .build();*/
	    System.out.println("   name is "+s3client.listBuckets().get(0).getName());
	    }

	public void uploadFile(String fileUpload, UserBean user) {
		// TODO Auto-generated method stub
		
		
		s3client.putObject(bucketName,user.getUsername(), new File(fileUpload));
		for (Bucket b : s3client.listBuckets()) {
			if(b.getName().equals("tokendatabase"))
			{
				ObjectListing objectListing = s3client.listObjects(b.getName());
				for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
				    System.out.println(os.getKey());
				}
			}
		}
	}

	public void downloadFile(UserBean user) throws IOException {
		// TODO Auto-generated method stub
		for (Bucket b : s3client.listBuckets()) {
			if(b.getName().equals("tokendatabase"))
			{
				ObjectListing objectListing = s3client.listObjects(b.getName());
				for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
				    System.out.println(os.getKey());
				    if(os.getClass().equals(user.getUsername()))
				    {
				    	
				    }
				}
			}
		}
		S3Object object= s3client.getObject(bucketName,user.getUsername());
		String ext =object.getObjectMetadata().getContentType();
		ext = ext.substring(ext.indexOf('/')+1);
		System.out.println("content tupe is "+ext);
		S3ObjectInputStream s3is = object.getObjectContent();
		File file = new File("C:\\Users\\namemh\\Desktop\\AWS\\"+user.getUsername()+"."+ext);
		file.setWritable(true);
	    FileOutputStream fos = new FileOutputStream(file);
	    byte[] read_buf = new byte[1024];
	    int read_len = 0;
	    while ((read_len = s3is.read(read_buf)) > 0) {
	        fos.write(read_buf, 0, read_len);
	    }
	    s3is.close();
	    fos.close();
		
		
		
	}
}
