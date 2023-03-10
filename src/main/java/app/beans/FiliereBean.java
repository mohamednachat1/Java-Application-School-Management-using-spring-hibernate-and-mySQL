package app.beans;

import java.util.List;

import app.entities.Etudiant;
import app.entities.Filiere;
import app.entities.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.services.FiliereService;
@ManagedBean
@Component
@Scope("session")
public class FiliereBean {
	@Autowired
	private FiliereService filiereService;
	
	private Filiere filiere;
	private boolean modifyContext;
	private String currentView;
	private List<Filiere> filieresFilitred;
	private Filiere filiereSelected;
	private List<Filiere> filieres;
	private Filiere viewFiliere;
	private String CODE;
	
	
	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public Filiere getViewFiliere() {
		return viewFiliere;
	}

	public void setViewFiliere(Filiere viewFiliere) {
		this.viewFiliere = viewFiliere;
	}

	public void deleteFiliere(Filiere filiere) {
		if(this.nbrEtudiant(filiere.getId()) != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Cette filiere n'est pas vide."));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Done","Cette filiere est supprime."));
			this.filiereService.delete(filiere);
		}
	}

	public FiliereBean() {
		this.filiere = new Filiere();
		this.modifyContext = false;
		this.menuLabel = "Liste";
		this.currentView = "Liste";
	}
	
	public int nbrEtudiant(int id) {
		return this.filiereService.countNbrEtudiant(id);
	}

	public Filiere getFiliereSelected() {
		return filiereSelected;
	}

	public void setFiliereSelected(Filiere filiereSelected) {
		this.filiereSelected = filiereSelected;
	}

	public List<Filiere> getFilieresFilitred() {
		return filieresFilitred;
	}

	public void setFilieresFilitred(List<Filiere> filieresFilitred) {
		this.filieresFilitred = filieresFilitred;
	}

	@ManagedProperty(value = "#{globalBean}")
	private GlobalBean globalBean;
	public GlobalBean getGlobalBean() {
		return globalBean;
	}

	public void setGlobalBean(GlobalBean globalBean) {
		this.globalBean = globalBean;
	}
	
	public String getCurrentView() {
		return currentView;
	}

	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}

	private String menuLabel;
	private boolean rendermenuItems;
	public boolean isRendermenuItems() {
		return rendermenuItems;
	}

	public void setRendermenuItems(boolean rendermenuItems) {
		this.rendermenuItems = rendermenuItems;
	}

	public String getMenuLabel() {
		return menuLabel;
	}

	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	
	public void listHandler() {
		this.currentView = "Liste";
		this.menuLabel = "Liste";
		this.rendermenuItems = false;
		this.filiere = new Filiere();
		this.filieres = this.filiereService.getAll();
		this.modifyContext = false;
	}
	public void addHandler() {
		this.currentView = "Fiche";
		this.menuLabel = "Ajout";
		this.rendermenuItems = true;
		this.modifyContext = false;
		this.filiere = new Filiere();
	}
	public void showHandler(Filiere f) {
		this.currentView = "Fiche";
		this.menuLabel = "Ajout";
		this.menuLabel = "Fiche";
		this.filiere = f;
		this.rendermenuItems = true;
		this.modifyContext = true;
		this.setCODE(f.getCode());
	}
	

	public void setFilieres(List<Filiere> filieres) {
		this.filieres = filieres;
	}

	
	
	public Filiere getFiliere() {
		return filiere;
	}

	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
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
	
	public List<Filiere> getFilieres(){
		return this.filiereService.getAll();
	}
	
	public int getNbreFiliere() {
		return this.getFilieres().size();
	}
	
	public void saveData() {
		String edit_add;
		if(isModifyContext()) {
			edit_add = "La modification ";
		}else {
			edit_add = "L'ajout ";
		}
		this.filiereService.save(this.filiere);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Done",edit_add+"du filiere est succee"));
		this.filiere = new Filiere();
		if(isModifyContext()) {
			this.addHandler();
		}
		this.modifyContext = false;
	}
	
	public void save() {
		User user = new User();
		Filiere fil = new Filiere();
		fil.setCode(this.filiere.getCode());
		if(this.filiereService.getByExample(fil).size() != 0 && this.isModifyContext() == false){
			this.filiere.setCode("");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error ","Code deja exist "));
		}else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			this.setGlobalBean((GlobalBean)facesContext.getApplication().createValueBinding("#{globalBean}").getValue(facesContext));
			user.setId(this.globalBean.getCurrentuser().getId());
			this.filiere.setUser(user);
			if(this.isModifyContext()) {
				Filiere f = new Filiere();
				f.setCode(this.getCODE());
				if(f.getCode().equals(this.filiere.getCode())) {
					saveData();
				}else {
					if(this.filiereService.getByExample(f).size() != 0) {
						this.filiere.setCode("");
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error ","Code deja exist "));
					}else {
						saveData();
					}
				}
			}else 
			{
				saveData();
			}
		}
		
	}
	
	public void modifyContext(Filiere f) {
		this.filiere = f;
		this.modifyContext = true;
	}
	
}
