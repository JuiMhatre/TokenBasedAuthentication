package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import bean.UserBean;

public class DynamoDbDao {

	AmazonDynamoDB dynamoDB;
	String awsAccessKey;
	String awsSecretKey;
	String regionName;
	String tablename;
	
	public DynamoDbDao() {
		
		// TODO Auto-generated constructor stub
		awsAccessKey = "ACCESSKEY";
	    
	    /** Put your secret key here **/
	    awsSecretKey = "SECRETKEY";
	    regionName = "us-east-2";
	    tablename="credentialsdatabase";
		System.out.println("here1");
	    AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
            
        } catch (Exception e) {
        	
            System.out.println(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.");
        }
        System.out.println("here2");
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(regionName)
                .build();
	}

	public void upload(UserBean user) {
		// TODO Auto-generated method stub
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("username", new AttributeValue(user.getUsername()));
        item.put("password", new AttributeValue(user.getPassword()));
        item.put("publickey", new AttributeValue(user.getPublickey()));
        PutItemRequest putItemRequest = new PutItemRequest(tablename, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result for upload: " + putItemResult.toString());
		
	}
	public String read(UserBean user)
	{
		HashMap<String,AttributeValue> creds = new HashMap<String,AttributeValue>();
		creds.put("username",new AttributeValue(user.getUsername()));
		GetItemRequest request= new GetItemRequest().withKey(creds).withTableName(tablename);
		try {
		    Map<String,AttributeValue> returned_item =	dynamoDB.getItem(request).getItem();
		    if (returned_item != null) {
		    	System.out.println("password is "+returned_item.get("password"));
		        return returned_item.get("password").toString();
		    } else {
		        System.out.format("No item found with the key %s!\n", user.getUsername());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		    System.out.println("Error reading db");
		    return "";
	}
		return "";
	}
}
	
	
