package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public static void main(String[] args){
		try {
		
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "";
				}
				
				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					return f.isDirectory() || f.getName().endsWith(".txt");
				}
			});
			
			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				
				File file = chooser.getSelectedFile();
			
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				
				String line = reader.readLine();
				String linha;
				ArrayList<String> linhas = new ArrayList<>();
				ArrayList<String> infos = new ArrayList<>();
				
				while(line != null) {
					if(
							!line.isEmpty()
						&&  !line.contains("Planos de Voo Repetitivos")
						&&  !line.contains("Classifica��o")
						&&  !line.contains("------------------")
						&&  !line.contains("VALIDO")
						&&  !line.contains("DESDE")
						&&  !line.contains("IN�CIO DE VALIDADE")
						) {
							if(Character.isDigit(line.trim().charAt(0))) {
								String[] dados = line.split(" ");
								for(String d:dados) {
									if(!d.isEmpty())
										infos.add(d);
								}
								infos.remove(0);
								infos.remove(0);
								while(infos.get(infos.size()-1).length() > 8) {
									infos.remove(infos.size()-1);
								}
								while(infos.size() > 5) {
									infos.remove(infos.size()-2);
								}
								linha = "";
								for(int i = 0;  i < infos.size(); i++) {
									if(i > 0) {
										linha += ";";
									}
									linha += infos.get(i);
								}
								linhas.add(linha);
								infos.clear();
							}
					}
					line = reader.readLine();
				}
				reader.close();
				
				for(String l: linhas)
					System.out.println(l);
				
				File out = new File(file.getParent(), "output.txt");
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(out));
				writer.flush();
				for(String l: linhas)
					writer.append(l + System.lineSeparator());
				
				writer.close();
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
