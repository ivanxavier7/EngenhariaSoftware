package io.calculator;

import java.util.Scanner;
import java.util.EmptyStackException;
import java.io.*;

public class RunCalculadoraSufixa {

    public static void RunCalculadora(Scanner ficheiro, BufferedWriter output) throws IOException {
		String expressao, sufixa;
		Scanner temp;
		CalculadoraSufixa calculadora;
		while (ficheiro.hasNextLine()){
		    expressao = ficheiro.nextLine();
		    if (!CalculadoraSufixa.verificaParenteses(expressao))
		    	output.write("Número de parênteses não é coincidente");
		    else {
				sufixa = CalculadoraSufixa.paraSufixa(expressao);
				output.write(sufixa+"?");
				temp = new Scanner(sufixa);
				calculadora = new CalculadoraSufixa();
			try {
			    while (temp.hasNext())
					calculadora.processaToken(temp.next());
				    if (calculadora.haResultado())
					if (Double.isNaN(calculadora.verResultado()))
					    output.write(" Divisão por zero.");
					else
					    output.write(" "+calculadora.verResultado());
				    else
				    	output.write(" Pilha em estado incorrecto.");
			} catch (EmptyStackException e) {
			    output.write(" Argumentos insuficientes.");
		}
	    }
	    output.newLine();
	}
	output.close();
    }
    
    public static void main(String[] args) throws IOException {
		Scanner leitor = new Scanner(System.in);
		System.out.print("Ficheiro de dados? ");
		Scanner ficheiro = new Scanner(new FileReader(leitor.next()));
		System.out.print("Ficheiro de resultados? ");
		BufferedWriter output = new BufferedWriter(new FileWriter(leitor.next()));
		RunCalculadora(ficheiro, output);
		leitor.close();
		
    }
}
