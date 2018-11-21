package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.UserBean;
import service.DynamoDbService;
import service.S3Service;
import service.ValidationService;

public class Login2Action extends ActionSupport implements ModelDriven<UserBean> {

	UserBean user =new UserBean();
	String fileUpload ="";
	
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
	
	public String login2()
	{
		S3Service s3service =new S3Service();
		DynamoDbService dynamodbservice = new DynamoDbService();
		ValidationService validationservice = new ValidationService();
		
		boolean flag2=dynamodbservice.checkCredentialsDynamoDb(user);
		if( flag2)
		{
			return SUCCESS;
		}
		else
			return ERROR;
	}
}
