package app.beans;
import app.entities.User;
import app.services.UserService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;  
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@SessionScoped
public class UserBean {
	private User user;
	@ManagedProperty(value = "#{globalBean}")
	private GlobalBean globalBean;
	@Autowired
	private UserService userService;
	private List<User> users;
	private String currentView;
	private String menuLabel;
	private boolean rendermenuItems;
	private boolean modifyContext;
	private List<User> usersFilitred;
	private User viewUser;

	public User getViewUser() {
		return viewUser;
	}
	public void setViewUser(User viewUser) {
		this.viewUser = viewUser;
	}
	public void deleteUser(User user) {
		this.userService.delete(user);
	}
	public String lastDate(Date date) {
		if(date == null) {
			return "-";
		}else {
			return new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(date);
		}
	}

	public List<User> getUsersFilitred() {
		return usersFilitred;
	}

	public void setUsersFilitred(List<User> usersFilitred) {
		this.usersFilitred = usersFilitred;
	}

	public UserBean() {
		this.user = new User();
		this.modifyContext = false;
		this.currentView = "Liste";
		this.menuLabel = "Liste";
	}
	
	public boolean isModifyContext() {
		return modifyContext;
	}
	
	public void setModifyContext(boolean modifyContext) {
		this.modifyContext = modifyContext;
	}

	public String getForm_label(){
		
		return isModifyContext()? "Modification" : "Ajout";
	}
	public String getButton_label(){
		return isModifyContext()? "Modifier" : "Ajouter";
	}
	
	public void listHandler() {
		this.currentView = "Liste";
		this.menuLabel = "Liste";
		this.rendermenuItems = true;
		this.users = this.userService.getAll();
	}
	public void addHandler() {
		this.currentView = "Fiche";
		this.menuLabel = "Ajout";
		this.rendermenuItems = true;
		this.modifyContext = false;
		this.user = new User();
	}
	public void showHandler(User u) {
		this.currentView = "Fiche";
		this.user = u;
		this.menuLabel = "Modification";
		this.rendermenuItems = true;
		this.modifyContext = true;
	}
	
	public List<User> getUsers() {
		return this.userService.getAll();
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getCurrentView() {
		return currentView;
	}
	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}
	public String getMenuLabel() {
		return menuLabel;
	}
	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	public boolean isRendermenuItems() {
		return rendermenuItems;
	}
	public void setRendermenuItems(boolean rendermenuItems) {
		this.rendermenuItems = rendermenuItems;
	}

	
	public GlobalBean getGlobalBean(){
		return globalBean;
	}
	public void setGlobalBean(GlobalBean glbBean){
		this.globalBean = glbBean;
	}

	public void save() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String edit_add;
		if(isModifyContext()) {
			edit_add = "La modification ";
		}else {
			edit_add = "L'ajout ";
			this.user.setLastLogin(null);
			this.user.setTheme("");
			String password = this.user.md5(this.user.getPassword());
			this.user.setPassword(password);
		}
		User user = new User();
		user.setLogin(this.user.getLogin());
		if(this.userService.getByExample(user).size() != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not found", "Login Deja exist!!!"));
		}else {
			this.userService.save(this.user);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Done", edit_add+"du utilisateur est succee"));
			if(isModifyContext()) {
				this.addHandler();
			}
			this.user = new User();
			this.modifyContext = false;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
