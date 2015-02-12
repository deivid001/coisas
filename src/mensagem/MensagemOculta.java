package mensagem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class MensagemOculta {
	String caracter = null;
	boolean ok = false;

	public static void main(String args[]) {
		MensagemOculta msg = new MensagemOculta();
		msg.run();
	}

	public void run() {
		BufferedImage img = null;
		img = lerImagem(img);
		int coluna = img.getHeight();
		int linha = img.getWidth();
		int contBit = 1;

		for (int j = coluna - 1; j >= 0; j--) {
			for (int i = 0; i < linha; i++) {
				// obtem cor da linha x e coluna y
				int rgb = img.getRGB(i, j);
				Color pixel = new Color(img.getRGB(i, j));
				// int a = (int) ((rgb & 0xFF000000) >>> 24);
				int r = (int) ((rgb & 0x00FF0000) >>> 16);
				int g = (int) ((rgb & 0x0000FF00) >>> 8);
				int b = (int) (rgb & 0x000000FF);

				// li no artigo do Vinicius Godoy que o .bmp começa do B depois
				// G e R
				// então to setando os LSB dos pixels no Byte nessa ordem

				// converte o valor da cor em byte
				String byteB = Integer.toBinaryString(b);// 1°
				// contBit é um contador pra saber se o byte alcançou o tamanho
				// de 8 bits
				contBit = conversao(contBit, byteB);

				String byteG = Integer.toBinaryString(g);// 2°
				contBit = conversao(contBit, byteG);

				String byteR = Integer.toBinaryString(r);// 3°
				contBit = conversao(contBit, byteR);
			}
		}

		System.out.println("Final! Acabou de ler esse carai! " + linha + " "
				+ coluna);
	}

	private BufferedImage lerImagem(BufferedImage img) {
		try {
			img = ImageIO.read(new File("testImage.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return img;
	}

	public int conversao(int contBit, String bytes) {
		int pos = bytes.length();
		// pega o ultimo caracter da string bytes(LSB)
		char charTmp = bytes.charAt(pos - 1);

		if (caracter == null) {
			caracter = String.format("" + charTmp);
			// System.out.println(caracter);
		} else {
			// concatena os "bits" sempre da direita pra esquerda
			caracter = charTmp + caracter;
		}
		contBit++;
		// quando o a string caracter tiver tamanho 8 ela é impressa
		if (contBit == 9) {
			int b = Integer.parseInt(caracter, 2);// converte string em inteiro
			String c = new Character((char) b).toString();// converte inteiro em
															// caracter
			if (b != 00000000)// se o caracter não for nulo é impresso
				System.out.printf("" + c);
			// System.out.println(""+caracter);//imprime string do caracter em
			// binario
			caracter = null;// zera string que imprime o byte
			contBit = 1;

		}
		return contBit;

	}

}
