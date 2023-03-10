package app.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import app.entities.Etudiant;
import app.entities.Filiere;
import app.entities.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import app.services.EtudiantService;
import app.services.FiliereService;

@Component
@ManagedBean
@SessionScope
	public class EtudiantBean implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Etudiant etudiant;
		private Integer idf;
		private boolean modifyContext;
		private Etudiant completeEtudiant;
		private String currentView;
		private String menuLabel;
		private List<Etudiant> etudiants;
		private Etudiant etudiantSelected;
		private List<Etudiant> etudiantsFilitred;
		private StreamedContent image;
		private Filiere completeFiliere;
		private UploadedFile filePhoto;
		private String CNE;
		
		public String getCNE() {
			return this.CNE;
		}
		public void setCNE(String cNE) {
			this.CNE = cNE;
		}
		public void handleUpload(FileUploadEvent event) {
	        UploadedFile file = event.getFile();
	        byte[] contents = file.getContent();
	        this.etudiant.setPhoto(contents);
	        System.out.println(contents);
		}
		 
		@Autowired
		private EtudiantService etudiantService;
		@Autowired
		private FiliereService filiereService;
		
		public Filiere getCompleteFiliere() {
			return completeFiliere;
		}
		public void setCompleteFiliere(Filiere completeFiliere) {
			this.completeFiliere = completeFiliere;
		}

		public String completeFiliereLabel(Filiere f) {
			if(f==null) {
				return null;
			}else {
				return f.getCode();
			}
		}

		public List<Filiere> completesFiliere(String search_code){
			System.out.println("CODE : "+search_code);
			Filiere f = new Filiere();
			f.setCode(search_code);
			List<Filiere> filis = filiereService.getByExample(f);
			return filis;
		}

		public StreamedContent getImage() throws Exception{
			if(this.etudiant.getPhoto() != null) {
				return this.image = DefaultStreamedContent.builder()
								 .contentType("image/png")
								 .stream(() -> {return new ByteArrayInputStream(this.etudiant.getPhoto()); })
								 .build();
			}
			return null;
		}

		public boolean existImg() {
			try {
				if(this.etudiant.getPhoto() == null) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		public void setImage(StreamedContent image) {
			this.image = image;
		}
		public void deleteEtudiant(Etudiant etd) {
			if(this.etudiantService.delete(etd)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Done", "Etudiant est supprimer!!!"));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not found", "Etudiant n'exist pas!!!"));
			}
		}

		public EtudiantBean() {
			this.etudiant = new Etudiant();
			this.modifyContext = false;
			this.menuLabel = "Liste";
			this.currentView = "Liste";
		}
		
		public List<Etudiant> getEtudiantsFilitred() {
			return etudiantsFilitred;
		}

		public void setEtudiantsFilitred(List<Etudiant> etudiantsFilitred) {
			this.etudiantsFilitred = etudiantsFilitred;
		}

		public Etudiant getEtudiantSelected() {
			return etudiantSelected;
		}

		public void setEtudiantSelected(Etudiant etudiantSelected) {
			this.etudiantSelected = etudiantSelected;
		}
		public void upload(FileUploadEvent event) {
			System.out.println("ok");
			byte[] bs = event.getFile().getContent();
			this.etudiant.setPhoto(bs);
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

		public void setEtudiants(List<Etudiant> etudiants) {
			this.etudiants = etudiants;
		}

		public void setCurrentView(String currentView) {
			this.currentView = currentView;
		}
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
			this.etudiant = new Etudiant();
			this.etudiants = this.etudiantService.getAll();
			this.modifyContext = false;
		}
		public void addHandler() {
			this.completeFiliere = null;
			this.currentView = "Fiche";
			this.menuLabel = "Ajout";
			this.rendermenuItems = true;
			this.etudiant = new Etudiant();
			this.modifyContext = false;
		}
		public void showHandler(Etudiant e) {
			this.completeFiliere = e.getFiliere();
			this.currentView = "Fiche";
			this.menuLabel = "Fiche";
			this.etudiant = e;
			this.rendermenuItems = true;
			this.modifyContext = true;
			this.setCNE(e.getCne());
		}
		
		public Etudiant getCompleteEtudiant() {
			return completeEtudiant;
		}
		public void setCompleteEtudiant(Etudiant completeEtudiant) {
			this.completeEtudiant = completeEtudiant;
		}
		
		public String completeEtudiantLabel(Etudiant e) {
			if(e==null) {
				return null;
			}else {
				return e.getCne()+" : "+e.getNom()+" "+e.getPrenom();
			}
		}
		
		public List<Etudiant> completesEtudiant(String search_cne){
			Etudiant e = new Etudiant();
			e.setCne(search_cne);
			List<Etudiant> etds = etudiantService.getByExample(e);
			return etds;
		}
		public void quickSearch() {
			if(this.completeEtudiant == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not found", "Etudiant n'exist pas!!!"));
				return;
			}
			FacesContext facesContext = FacesContext.getCurrentInstance();
			this.setGlobalBean((GlobalBean)facesContext.getApplication().createValueBinding("#{globalBean}").getValue(facesContext));
			this.globalBean.etudiant();
			this.showHandler(this.completeEtudiant);
			this.completeEtudiant= null;
		}
		
		public Integer getIdf() {
			return idf;
		}
		public void setIdf(Integer idf) {
			this.idf = idf;
		}
		
		public Etudiant getEtudiant() {
			return etudiant;
		}
			
		public void setEtudiant(Etudiant etudiant) {
			this.etudiant = etudiant;
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
		
		public List<Etudiant> getEtudiants(){
			return etudiantService.getAll();
		}
		
		public void saveData() {
			this.etudiantService.save(this.etudiant);
			this.etudiant = new Etudiant();
			this.idf = null;
			String edit_add;
			if(isModifyContext())
			{
				edit_add = "La modification ";
			}else
			{
				edit_add = "L'ajout ";
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Done",edit_add+"d'etudiant est succee"));
			if(isModifyContext()) {
				this.addHandler();
			}
			this.modifyContext = false;
			this.completeFiliere = null;
		}
		
		public void save() 
		{
			Etudiant etd = new Etudiant();
			etd.setCne(this.etudiant.getCne());
			List<Etudiant> etds = this.etudiantService.getByExample(etd);
			for (Etudiant etudiant : etds) {
				System.out.println("The Name : "+etudiant.getNom());
			}
			if(this.etudiantService.getByExample(etd).size() != 0 && this.isModifyContext() == false){
				this.etudiant.setCne("");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error ","CNE deja exist "));
			}else {
				User user = new User();
				FacesContext facesContext = FacesContext.getCurrentInstance();
				this.setGlobalBean((GlobalBean)facesContext.getApplication().createValueBinding("#{globalBean}").getValue(facesContext));
				user.setId(this.globalBean.getCurrentuser().getId());
				this.etudiant.setUser(user);
				this.etudiant.setFiliere(this.completeFiliere);
				if(this.isModifyContext()) {
					Etudiant etdd = new Etudiant();
					etdd.setCne(this.getCNE());
					System.out.println(this.getCNE());
					if(etdd.getCne().equals(this.etudiant.getCne())) {
						saveData();
						this.setCNE("");
					}else {
						if(this.etudiantService.getByExample(etd).size() != 0) {
							this.etudiant.setCne("");
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error ","CNE deja exist "));
						}else {
							saveData();
							this.setCNE("");
						}
					}
				}else 
				{
					saveData();
					this.setCNE("");
				}
			}
//		
		}
		
	public void modifyContext(Etudiant e) {
			this.etudiant = e;
			this.idf = etudiant.getFiliere().getId();
			this.modifyContext = true;
		}
		
		public void delete(Etudiant e) {
			this.etudiantService.delete(e);
		}
	public int getNbreEtudiant() {
			return this.getEtudiants().size();
		}

	}
