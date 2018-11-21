package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.UserBean;
import service.DynamoDbService;
import service.S3Service;

public class RegistrationAction extends ActionSupport implements ModelDriven<UserBean>{

	UserBean user =new UserBean();
	String fileUpload;
	public String getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}
	@Override
	public UserBean getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	public String register()
	{
		//System.out.println("Jui "+fileUpload);
		S3Service s3service =new S3Service();
		boolean flag1=	flag1=s3service.uploadTokenS3(fileUpload,user);
		DynamoDbService dynamodbservice = new DynamoDbService();
		
		boolean flag2=dynamodbservice.uploadCredentialsDynamoDb(user);
		if(flag1 && flag2)
			return SUCCESS;
		else
			return ERROR;
	}
	public String gotoLogin()
	{
		return SUCCESS;
	}
	
}
