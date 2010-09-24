package actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import org.hibernate.SessionFactory;

import usuarios.dto.AdministradorDePermisos;
import usuarios.dto.UsuarioDTO;
import utilidades.HibernateUtil;

import beans.EdificiosBean;
import beans.UsuariosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

@SuppressWarnings("serial")
public class EdificioGastosListarAction extends ActionSupport{
	
	private Map<String,Object> session;
	
	public String execute() {
		ArrayList<EdificioDTO> lista = new ArrayList<EdificioDTO>();
		EdificiosBean listaEdificios = new EdificiosBean();
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			//Carga la lista segun el perfil que tiene el usuario que se logea
			AdministradorDePermisos administrador = AdministradorDePermisos.getInstancia();
			if (!administrador.visibleTodosLosEdificios()){
				lista.add(administrador.getUser().getEdificio());
			}else{
				lista = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
			}
				listaEdificios.setEdificios(lista);
				Map session = ActionContext.getContext().getSession();
		        session.put("lista",listaEdificios);
		        this.setSession(session);
		        return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}	
	}
	
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
	}
}
