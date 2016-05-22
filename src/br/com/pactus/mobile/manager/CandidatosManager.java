package br.com.pactus.mobile.manager;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.commons.core.tool.Console;
import javax.commons.core.util.NumUtils;
import javax.commons.core.util.ObjUtils;
import javax.commons.persistence.PersistenceClause;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;

import org.primefaces.event.CaptureEvent;

import br.com.mediastinum.pectus.business.common.session.facade.SessionManager;
import br.com.mediastinum.pectus.business.model.entity.Parceiro;

@ManagedBean
@SessionScoped
public class CandidatosManager implements Serializable{

   private static final long serialVersionUID = 1L;
   private String filename;
   public Parceiro parceiroEdicao = new Parceiro();
   public List<Parceiro> parceiros = new ArrayList<Parceiro>();

   public CandidatosManager(){

      parceiroEdicao.setParAltura(50d);
      
      PersistenceClause clause = new PersistenceClause();
      clause.andEquals("camId", 24);
      Parceiro p = new Parceiro();
      p.addClause(clause);
      List<Parceiro> parceirosAUX = SessionManager.search().findAllByClause(p).getResult();

      int i = 0;
      for (Parceiro parceiro: parceirosAUX){
         i++;
         if (NumUtils.isLessThan(i, 4)){
            parceiros.add(parceiro);
         } else{
            return;
         }
      }

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

      if (ObjUtils.isNull(parceiroEdicao)){
         parceiroEdicao = new Parceiro();
         this.criaMensagem("Não foram encontrados resultados para o codigo " + codigo + ".", FacesMessage.SEVERITY_FATAL);
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

   // ============================================================
   // Gravação foto
   // ============================================================

   private String getRandomImageName(){
      int i = (int) (Math.random() * 10000000);

      return String.valueOf(i);
   }

   public String getFilename(){
      return filename;
   }

   public void oncapture(CaptureEvent captureEvent){
      filename = getRandomImageName();
      byte[] data = captureEvent.getData();

      ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".png";

      FileImageOutputStream imageOutput;
      try{
         imageOutput = new FileImageOutputStream(new File(newFileName));
         imageOutput.write(data, 0, data.length);
         imageOutput.close();
      } catch (IOException e){
         throw new FacesException("Error in writing captured image.", e);
      }
   }

}