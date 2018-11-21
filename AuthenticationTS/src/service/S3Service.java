package service;

import bean.UserBean;
import dao.S3Dao;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
public class S3Service {
	

	public S3Service() {
		super();
		System.out.println("heredddd");
		// TODO Auto-generated constructor stub
	}

	public UserBean searchS3(String username) {
		// TODO Auto-generated method stub
		UserBean user =new UserBean();
		return user;
	}

	public boolean uploadTokenS3(String fileUpload, UserBean user) {
		// TODO Auto-generated method stub
		try
		{
			System.out.println("testing...");
			S3Dao s3dao = new S3Dao();
			
			System.out.println("uploading..");
			s3dao.uploadFile(fileUpload,user);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();;
			return false;
		}
		return true;
	}
	
	public boolean downloadloadTokenS3( UserBean user) {
		// TODO Auto-generated method stub
		try
		{
			System.out.println("testing...");
			S3Dao s3dao = new S3Dao();
			
			
			s3dao.downloadFile(user);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();;
			return false;
		}
		return true;
	}

	

}
