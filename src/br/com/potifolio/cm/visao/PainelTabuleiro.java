	package br.com.potifolio.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.potifolio.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(tabuleiro.getLinhas(),tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		
		tabuleiro.registrarObservadores(e -> {
			
			SwingUtilities.invokeLater(() -> {
			if(e.booleanValue()) {
				JOptionPane.showMessageDialog(this,"vc ganhou =)");
			} else {
				JOptionPane.showMessageDialog(this,"vc perdeu =(");
			}
		
			tabuleiro.reiniciar();
		});
	});
 }
  }
