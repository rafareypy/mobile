package br.com.pectus.mobile.login;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.caelum.livraria.bean.MenuBean;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.commons.core.common.SystemContext;
import javax.commons.core.tool.NetInfo;
import javax.commons.core.tool.SystemInfo;
import javax.commons.core.util.ObjUtils;

import br.com.mediastinum.pectus.business.common.constant.BusinessMessages;
import br.com.mediastinum.pectus.business.common.entity.Usuario;
import br.com.mediastinum.pectus.business.common.session.bean.AccessControlBean;
import br.com.mediastinum.pectus.business.common.session.facade.SessionManager;
import br.com.mediastinum.pectus.business.common.session.service.AccessControlService;
import br.com.mediastinum.pectus.business.model.entity.Parametro;


@Model
public class LoginBean {
	
	private Usuario usuarioEncontrado = new Usuario();	
	
	@Inject
	UsuarioLogadoBean usuarioLogado;
	
	@Inject
	MenuBean menu;

	public Usuario getUsuario() {
		return usuarioEncontrado;
	}
	
	public String efetuaLogin() {

	   this.usuarioEncontrado = ((AccessControlService) SessionManager.service(AccessControlBean.class)).isValidConnection(usuarioEncontrado);
      if (!usuarioEncontrado.hasErrors()){
			usuarioLogado.logar(usuarioEncontrado);
			criaMensagem("Usuario logado!");
			return menu.paginaAvaliacao();
      }
		criaMensagem("Usuário não encontrado!");
		limparForm();
		
		return "";
	}
	
	public String efetuaLogout() {
		this.usuarioLogado.deslogar();
		return this.menu.paginaLogin();
	}

	
	private void limparForm() {
		this.usuarioEncontrado = new Usuario();
	}

	private void criaMensagem(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}


}
