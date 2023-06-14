package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import predictionCode.Main;
import sardinas_patterson.Sardinas;

@Controller
public class pageController {
	
	@GetMapping("/")
	private String index(Model model) {
		model.addAttribute("name", "mic");
		return "index";
	}

	@GetMapping("/test")
	private String test(Model model , String lang) {
		if (!lang.equals("[]") && !lang.equals("")) {
			model.addAttribute("name", "mic");
			List<String> data = new ArrayList<>();
			String str = lang;
			str = str.replaceAll("\\s+", "");
			String[] tableauString = str.substring(1, str.length() - 1).split(",");
			String tab = Main.getData(tableauString);
			Vector<String> vecteur = new Vector<>();
			vecteur.addAll(Arrays.asList(tableauString));
			try {
	            // Créez une instance de Runtime
	            Runtime runtime = Runtime.getRuntime();

	            // Spécifiez la commande pour exécuter le script Python
	            String command = "python last.py "+tab;

	            // Exécutez la commande
	            Process process = runtime.exec(command);

	            // Récupérez la sortie du script Python
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	                data.add(line);
	            }

	            // Attendez que le script Python se termine
	            int exitCode = process.waitFor();
	            System.out.println("Le script Python a été exécuté avec le code de sortie : " + exitCode);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			model.addAttribute("dataPython", data);
			model.addAttribute("dataJava", Sardinas.isCode(vecteur));
		}
		else {
			model.addAttribute("dataPython", "[false]");
			model.addAttribute("dataJava", "false");
		}
		return "test";
	}
}
