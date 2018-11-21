package service;

import bean.UserBean;
import dao.DynamoDbDao;

public class DynamoDbService {

	public boolean uploadCredentialsDynamoDb(UserBean user) {
		// TODO Auto-generated method stub
		try
		{
			DynamoDbDao dynamodbdao = new DynamoDbDao();
			dynamodbdao.upload(user);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in Dynamodb upload");
			return false;
		}
		return true;
	}
	public boolean checkCredentialsDynamoDb(UserBean user){
		try
		{
			DynamoDbDao dynamodbdao = new DynamoDbDao();
			boolean flag =dynamodbdao.read(user).contains(user.getPassword());
			System.out.println("validation result "+flag);
			return flag;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in Dynamodb upload");
			return false;
		}
	}

}
