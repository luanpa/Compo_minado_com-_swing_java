package br.com.potifolio.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	
	public Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
		
	}
	
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservador(CampoEvento evento) {
		observadores.stream()
		.forEach(o -> o.eventoObservador(this, evento));
	}
	
public boolean adicionarVizinhos(Campo vizinho) {
	boolean linhaDiferente = linha != vizinho.linha;
	boolean colunasDiferente = coluna != vizinho.coluna;
	boolean diagonal = linhaDiferente && colunasDiferente;
	
	int deltaLinha = Math.abs(linha - vizinho.linha);
	int deltaColuna = Math.abs(coluna - vizinho.coluna);
	int detalGeral = deltaColuna + deltaLinha;
	
	if(detalGeral == 1 && !diagonal) {
		vizinhos.add(vizinho);
		return true;
	} else if (detalGeral == 2 && diagonal) {
		vizinhos.add(vizinho);
		return true;
	} else {
		return false;
	}
	
 }
	
 public void alternarMarcacao() {
	if(!aberto) {
		marcado = !marcado;
		
		if(marcado) {
			notificarObservador(CampoEvento.MARCAR);
		} else {
			notificarObservador(CampoEvento.DESMARCAR);
		}
	}
}
 
 public boolean abrir () {
	 if(!aberto && !marcado) {
		 if(minado) {
			 notificarObservador(CampoEvento.EXPLODIR);
			 return true;	 
		 }
		 
		 setAberto(true);
		 
	 if(vizinhacaSegura()) {
		 vizinhos.forEach(v -> v.abrir());
	     }
	     return true;
	 } else {
		 return false;
   }
 }
 
 
public boolean vizinhacaSegura() {
	 return vizinhos.stream().noneMatch(v -> v.minado);
 }
 
 public void minar() {
		 minado = true;
 }
 public boolean isMinado() {
	 return minado;
 }
	 
 
 public boolean isMarcado() {
	 return marcado;
 }
 
  void setAberto(boolean aberto) {
	this.aberto = aberto;
	
	if(aberto) {
		 notificarObservador(CampoEvento.ABRIR);

	}
}

public boolean isAberto () {
	 return aberto;
 }
 
 public boolean isFechado () {
	 return !isAberto();
 }

public int getLinha() {
	return linha;
}

public int getColuna() {
	return coluna;
}
 
 boolean objetivoAlcando() {
	 boolean desvendado = !minado && aberto;
	 boolean protegido = minado && marcado;
	 return desvendado || protegido;
 }
 
 public int minasNaVizinhaca() {
	 return (int) vizinhos.stream().filter(v -> v.minado).count();
	 
 }

 void reiniciar() {
	 aberto = false;
	 minado = false;
	 marcado = false;
	 notificarObservador(CampoEvento.REINICIAR);
	 
 }
 
}
