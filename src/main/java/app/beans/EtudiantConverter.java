package app.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.entities.Etudiant;
import app.services.EtudiantService;

@ManagedBean
@Component
@Scope("session")
public class EtudiantConverter implements Converter{

	@Autowired
	private EtudiantService etudiantService; 
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && value.trim().length()>0) {
			return this.etudiantService.getByID(Integer.parseInt(value));
		}
			
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			return ((Etudiant)value).getId().toString();
		}
		return null;
	}
	
}
