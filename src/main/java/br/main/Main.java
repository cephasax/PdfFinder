package br.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;

import br.fileManipulation.FileManager;

public class Main {

	public static File[] files;
	public static FileManager fileManager = new FileManager();
	public static PDFHighlighter pdfHi;
	public static FileOutputStream outPut;
	public static ArrayList<Achei> resultados;
	public static ArrayList<String> results;
	
	public static void main(String[] args) throws Exception {
		
		pdfHi = new PDFHighlighter();
		files = fileManager.selecionarArquivos();
		outPut = new FileOutputStream("file.txt");
		resultados = new ArrayList<Achei>();
		results = new ArrayList<String>();
		
		primeiraEtapa();
		outPut.close();
		segundaEtapa();
		
		for(String s: results) {
			System.out.println(s);
		}

	}

	//Procura nos arquivos e grava onde foi encontrado o resultado
	public static void primeiraEtapa() throws IOException {
		for (File file : files){
			System.out.println("Document: " + file.getName());
			
			outPut.write("\n".getBytes());
			outPut.write(("Document: " + file.getName() + " ").getBytes());
			
			PDDocument doc = PDDocument.load(file);
			pdfHi.generateXMLHighlight(doc, "cephas", outPut);
			doc.close();
		}
	}
	
	public static void segundaEtapa() throws FileNotFoundException {

		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("file.txt"));
			String line = bufferedReader.readLine();
			while (line != null) {
				if(line.contains("pg")) {
					results.add(line);
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
}
