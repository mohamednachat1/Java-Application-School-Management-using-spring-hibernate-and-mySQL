package app.beans;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import app.entities.User;
import app.services.UserService;
@Component
@ManagedBean
@SessionScope
public class GlobalBean {
	private boolean enligne;
	private User user; 
	private int menuActivIndex;
	private String currentFragment = "dashboard/Dashboard";
	private String loggedInUserTheme;
	private EtudiantBean etudiantBean;
	
	public EtudiantBean getEtudiantBean() {
		return etudiantBean;
	}

	public void setEtudiantBean(EtudiantBean etudiantBean) {
		this.etudiantBean = etudiantBean;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getMenuActivIndex() {
		return menuActivIndex;
	}

	public void t_exception() throws Exception {
		this.menuActivIndex = 2;
		FacesContext.getCurrentInstance().getExternalContext().redirect("errors/error.jsf");
	}
	
	public void setMenuActivIndex(int menuActivIndex) {
		this.menuActivIndex = menuActivIndex;
	}

	public String getCurrentFragment() {
		return currentFragment;
	}

	public void setCurrentFragment(String currentFragment) {
		this.currentFragment = currentFragment;
	}

	@Autowired
	private  UserService userService;
	private User currentuser;
	private Date lastloginOfUser;
	public GlobalBean() {
		this.enligne = false;
		this.user = new User();
		this.currentuser = new User();
		this.currentFragment = "dashboard/Dashboard";
		this.menuActivIndex = 0;
	}

	public void home() {
		this.currentFragment = "dashboard/dashboard";
		
//		((EtudiantBean)FacesContext.getCurrentInstance()
//				.getExternalContext()
//				.getSessionMap()
//				.get("etudiantBean")).listHandler();
		this.menuActivIndex = 0;
	}
	
	public void etudiant() {
		this.currentFragment = "etudiant/etudiant";
//		((EtudiantBean)FacesContext.getCurrentInstance()
//				.getExternalContext()
//				.getSessionMap()
//				.get("etudiantBean")).listHandler();
//		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		this.setEtudiantBean(((EtudiantBean)facesContext.getApplication().createValueBinding("#{etudiantBean}").getValue(facesContext)));
		this.etudiantBean.listHandler();
		
		this.menuActivIndex = 0;
	}
	
	public void etudiantForm() {
		this.currentFragment = "etudiant/etudiantFiche";
		this.menuActivIndex = 0;
	}
	
	public void filiere() {
		this.currentFragment = "filiere/filiere";
		((FiliereBean)FacesContext.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.get("filiereBean")).listHandler();
		this.menuActivIndex = 0;
	}
	
	public void filiereForm(){
		this.currentFragment = "filiere/filiereFiche";
		this.menuActivIndex = 0;
	}
	
	public void userDone() {
		this.currentFragment = "user/user";
		this.menuActivIndex = 1;
	}
	
	public void userForm() {
		this.currentFragment = "user/userFiche";
		this.menuActivIndex = 1;
	}
	
	
	public boolean isEnligne() {
		return enligne;
	}
	public void setEnligne(boolean enligne) {
		this.enligne = enligne;
	}
	public User getCurrentuser() {
		return currentuser;
	}
	public String getLastLoginOfUser() {
		if(this.lastloginOfUser == null) {
			return "-";
		}else {
			return new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(this.lastloginOfUser);
		}
	}
	public void setCurrentuser(User currentuser) {
		this.currentuser = currentuser;
	}
	public void logout() throws Exception {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().redirect("./");
	}

	public void verify() throws Exception {
		this.user.setPassword(this.user.md5(this.user.getPassword()));
		List<User> list_user = this.userService.getByExample(this.user);
		if(list_user!= null && list_user.size() == 1 && list_user.get(0).getPassword().equals(this.user.getPassword())) {
			this.enligne = true;
			this.currentuser = list_user.get(0);
			if(this.currentuser.getLastLogin() != null) {
				this.lastloginOfUser = this.currentuser.getLastLogin();
			}
			this.currentuser.setLastLogin(new Date());
			this.userService.save(this.currentuser);
			FacesContext.getCurrentInstance().getExternalContext().redirect("./");
			return;
		}
		this.user.setPassword("");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Authentifiacation Feiled","Mot de passe incorrect"));
	}
}