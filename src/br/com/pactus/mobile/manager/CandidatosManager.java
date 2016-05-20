package br.com.pactus.mobile.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.commons.core.tool.Console;
import javax.commons.core.util.ObjUtils;
import javax.commons.persistence.PersistenceClause;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mediastinum.pectus.business.common.session.facade.SessionManager;
import br.com.mediastinum.pectus.business.model.entity.Parceiro;

@ManagedBean
@SessionScoped
public class CandidatosManager implements Serializable{

   private static final long serialVersionUID = 1L;

   public Parceiro parceiroEdicao = new Parceiro();
   public List<Parceiro> parceiros = new ArrayList<Parceiro>();

   public CandidatosManager(){

      PersistenceClause clause = new PersistenceClause();
      clause.andEquals("camId", 24);
      Parceiro p = new Parceiro();
      p.addClause(clause);
      parceiros = SessionManager.search().findAllByClause(p).getResult();

   }

   public String candidatoEdicao(){
      return "votacao?faces-redirect=true";
   }

   public String avaliacao(){
      parceiroEdicao = new Parceiro();
      return "votacao?faces-redirect=true";
   }

   public void avaliacaoDialog(){

   }

   public String buscarParceiro(){
      Long codigo = parceiroEdicao.getParId();
      Parceiro p = new Parceiro();
      p.setParId(codigo);      
      parceiroEdicao = SessionManager.search().findById(p).getResult();
      
      if(ObjUtils.isNull(parceiroEdicao)){
         parceiroEdicao = new Parceiro();
         this.criaMensagem("Não foram encontrados resultados para o codigo " +codigo+ ".", FacesMessage.SEVERITY_FATAL);
      }
      
      return "avaliacao?faces-redirect=true";
   }

   public String novoParceiro(){

      parceiroEdicao = new Parceiro();
      return "avaliacao?faces-redirect=true";
   }

   public void salvar(){
      Console.println(parceiroEdicao.getParNomeRazaoSocial());
      this.criaMensagem("Candidato " + parceiroEdicao.getParNomeRazaoSocial() + " salvo com sucesso!", FacesMessage.SEVERITY_INFO);
      parceiroEdicao = new Parceiro();
   }

   private void criaMensagem(String mensagem, Severity severity){
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, mensagem, ""));
   }

   public Parceiro getParceiroEdicao(){
      return parceiroEdicao;
   }

   public void setParceiroEdicao(Parceiro parceiroEdicao){
      this.parceiroEdicao = parceiroEdicao;
   }

   public List<Parceiro> getParceiros(){
      return parceiros;
   }

   public void setParceiros(List<Parceiro> parceiros){
      this.parceiros = parceiros;
   }

   public List<Parceiro> getCandidatos(){
      return SessionManager.search().findAll(new Parceiro()).getResult();
   }

   public static long getSerialversionuid(){
      return serialVersionUID;
   }
}