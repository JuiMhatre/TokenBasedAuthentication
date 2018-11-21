package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.UserBean;
import service.S3Service;

public class LoginAction  extends ActionSupport implements ModelDriven<UserBean> {

	UserBean user =new UserBean();
	@Override
	public UserBean getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	public String login()
	{
		S3Service s3service =new S3Service();
		s3service.downloadloadTokenS3(user);
		if(user!=null)
		{
			return SUCCESS;
		}
		else{
			return ERROR;
		}
	}
	

}
