package br.com.pactus.mobile.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.pectrus.model.Candidato;

@ManagedBean
@SessionScoped
public class CandidatosManager implements Serializable{

   private static final long serialVersionUID = 1L;

   public Candidato candidatoEdicao = new Candidato("", "", false);
   public List<Candidato> candidatos = new ArrayList<Candidato>();

   public CandidatosManager(){

      candidatos.add(new Candidato("rafael", "001", true));
      for (int i = 0; i < 4; i++){
         candidatos.add(new Candidato("candidato_" + i, "00" + i, true));
      }

   }

   public String candidatoEdicao(){
      candidatoEdicao = new Candidato("edicao", "edicao", true);
      return "votacao?faces-redirect=true";
   }

   public void novoCandidatoDialog(){
      candidatoEdicao = new Candidato("", "", true);
   }

   public String novoCandidato(){
      candidatoEdicao = new Candidato("", "", true);
      return "votacao?faces-redirect=true";
   }

   public void salvar(){
      if (!candidatos.contains(candidatoEdicao)){
         candidatos.add(candidatoEdicao);
      }
      candidatoEdicao = new Candidato("", "", true);
      
      this.criaMensagem("Candidato salvo com sucesso!", FacesMessage.SEVERITY_INFO);
   }

   
   private void criaMensagem(String mensagem, Severity severity) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, mensagem, ""));
   }
   
   public List<Candidato> getClientes(){
      return candidatos;
   }

   public Candidato getCandidatoEdicao(){
      return candidatoEdicao;
   }

   public void setCandidatoEdicao(Candidato candidato){
      this.candidatoEdicao = candidato;
   }

   public List<Candidato> getCandidatos(){
      return candidatos;
   }

   public void setCandidatos(List<Candidato> candidatos){
      this.candidatos = candidatos;
   }

   public static long getSerialversionuid(){
      return serialVersionUID;
   }

}