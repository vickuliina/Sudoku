package Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;


/* 
 * Jos kentt� ei onnistu, aloita kentt� uudestaan?
 */

public class Sudoku {
	
	public static void main(String[] args) {
		Sudoku peli = new Sudoku();
	}

	private int[][] aloitusKentta;
	private Scanner scanner;
	File file;
	File aloitusKenttaFile;
	
	public Sudoku() {
		scanner = new Scanner(System.in);
		System.out.println("SUDOKU");
		System.out.println("Valitse kentt� 1 tai 2");
		
		int kentanNumero = Integer.parseInt(scanner.nextLine());
		valitseAloitusKentta(kentanNumero);
		piirraKentta(aloitusKentta);
		
		
		while (sudokuTaysi() == false) {
			
			System.out.println("Vaakarivi");
			int vaaka = Integer.parseInt(scanner.nextLine()) - 1;
			
			System.out.println("Pystyrivi");
			int pysty = Integer.parseInt(scanner.nextLine()) - 1;
			
			System.out.println("Luku");
			int luku = Integer.parseInt(scanner.nextLine());
			
			siirto(vaaka, pysty, luku);
			piirraKentta(aloitusKentta);
		}
		
		
		if(onkoVoitto(aloitusKentta, lueTiedosto(file)) == true) {
			System.out.println("Voitto");
		}
		else {
			System.out.println("H�vi�");
		}
	}
	
	
	/*
	 * Asettaa alotuskent�lle luvun. Mik�li siirto ei ole mahdollinen tulostaa se virheteksin
	 * @param vaaka = vaakarivin numero, v�lilt� 0-8
	 * @param pysty = pystyrivin numero, v�lilt� 0-8
	 * @param luku = asetettava luku, v�lilt� 1-9
	 */
	
	public void siirto(int vaaka, int pysty, int luku){
		if (aloitusKentta[vaaka][pysty] == 0) {
			aloitusKentta[vaaka][pysty] = luku;
		}
		else if (luku < 0 || luku > 9 || pysty < 0 || pysty > 8 || vaaka < 0 || vaaka > 8){
			System.out.println("Ei ole mahdollinen siirto");
		}
	}
	
	
	/*
	 * Kertoo onko sudoku t�ysi
	 * paluttaa true, mik�li sudoku on t�ynn�, jokainen luku sudokussa on v�lilt� 1-9
	 * muuten palauttaa false
	 */
	public boolean sudokuTaysi() {
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if(aloitusKentta[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*
	 * Kertoo onko sis�lt��k� aloituskentt� samat luvut kuin t�ytetty kentt�
	 * @param aloitusKentta = aloituskentt�
	 * @param taysiKentta = oikea ratkaisu sudokulle
	 */
	public boolean onkoVoitto(int[][] aloitusKentta, int[][] taysiKentta) {
		for(int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if(aloitusKentta[i][j] != taysiKentta[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*
	 * Saa parametrina halutun kent�n numeron ja palauttaa numeroa vastaavan aloituskent�n
	 * @param kentanNumero = halutun kent�n numero
	 * @return aloituskentan luvut matsiisissa
	 */
	public int[][] valitseAloitusKentta(int kentanNumero) {
		if (kentanNumero == 1) {
			file = new File("kentta1.txt");
			aloitusKenttaFile = new File("aloitusKentta1.txt");
			aloitusKentta = lueTiedosto(aloitusKenttaFile);
		}
		else if (kentanNumero == 2) {
			file = new File("kentta2.txt");
			aloitusKenttaFile = new File("aloitusKentta2.txt");
			aloitusKentta = lueTiedosto(aloitusKenttaFile);
		}
		return aloitusKentta;
	}
	
	
	/*
	 * Piirt�� parametrina saaneen kent�n
	 * @param haluttu kent�n luvut matriisissa
	 */
	public void piirraKentta(int[][] kentta) {
		new Random();
		int apu1 = 0;
		int apu2 = 0;
		
		System.out.println(" ----------------------------");
		
		for (int i=0; i<9; i++) {
			System.out.print(" | ");
			apu1++;

			for (int j=0; j<9; j++) {
				apu2++;
				
				if(kentta[i][j] == 0) {
					System.out.print("_ ");
				}
				else {
					System.out.print(kentta[i][j] + " ");
				}
				
				if(apu2 == 3) {
					System.out.print(" | ");
					apu2 = 0;
				}
			}
			System.out.println();
			if(apu1 == 3) {
				System.out.println(" ----------------------------");
				apu1 = 0;
			}
		}
	}
	
	
	/*
	 * Lukee parametrina saaneen tiedoston sis�ll�n ja muuttaa sis�ll�n matriisiin, niin ett� siit� muodostuu kentt�
	 * @param file, tiedosto, jonka sis�lt� halutaan lukea
	 * @return palauuttaa tiedoston sis�ll�n matriisissa
	 */
	public int[][] lueTiedosto(File file) {
		int[][] kentta = new int[9][9];
		Scanner s;
		
		try {
			s = new Scanner(new FileReader(file.getPath()));
			while(s.hasNextInt()) {
				for (int i=0; i<9; i++) {
					for (int j=0; j<9; j++) {
						kentta[i][j] = s.nextInt();
					}
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return kentta;
	}
}
