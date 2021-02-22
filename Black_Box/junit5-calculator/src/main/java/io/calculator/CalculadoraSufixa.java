package io.calculator;

import java.util.Stack;
import java.util.EmptyStackException;
import java.util.Scanner;

public class CalculadoraSufixa {

    private Stack<Double> pilha;

    /**
     * Inicializa uma nova calculadora de pilha.
     */
    public CalculadoraSufixa() {
	pilha = new Stack<Double>();
    }

    /**
     * Processa o próximo token.
     * @param token o token a processar
     * @throws EmptyStackException se não houver operandos suficientes
     * @throws NumberFormatException se o token for incorrecto
     */
    public void processaToken(String token){
	double op1, op2;
	if (eOperador(token)){
	    op2 = pilha.peek();
	    pilha.pop();
	    op1 = pilha.peek();
	    pilha.pop();
	    if (token.equals("+"))
		pilha.push(op1+op2);
	    else if (token.equals("-"))
		pilha.push(op1-op2);
	    else if (token.equals("*"))
		pilha.push(op1*op2);
	    else if (token.equals("/")){
		pilha.push(op1/op2);
		if (pilha.peek() == Double.POSITIVE_INFINITY || pilha.peek() == Double.NEGATIVE_INFINITY){
		    pilha.pop();
		    pilha.push(Double.NaN);
		}
	    }
	}
	else
	    pilha.push(Double.parseDouble(token));
    }


    /**
     * Devolve o resultado da avaliação.
     * Pré-condição: haResultado();
     * @return resultado das contas
     */
    public double verResultado() {
	return pilha.peek();
    }

    /**
     * Indica se há um resultado da avaliação.
     * @return se há resultado
     */
    public boolean haResultado() {
	if (pilha.isEmpty())
	    return false;
	else {
	    double x = pilha.peek();
	    pilha.pop();
	    boolean resultado = pilha.isEmpty();
	    pilha.push(x);
	    return resultado;
	}
    }

    /**
     * Verifica se uma expressão em notação infixa tem os parênteses correctos.
     * @param infixa expressão a verificar
     * @result se está correcta ou não
     */
    public static boolean verificaParenteses(String infixa){
	Scanner expressao = new Scanner(infixa);
	Stack<String> pilha = new Stack<String>();
	String token;
	try {
	    while (expressao.hasNext()){
		token = expressao.next();
		if (token.equals("("))
		    pilha.push(token);
		else if (token.equals(")"))
		    pilha.pop();
	    }
	    expressao.close();
	    return (pilha.isEmpty());
	} catch (EmptyStackException e) {
	    return false;
	}
    }

    /**
     * Converte uma expressão infixa numa equivalente sufixa.
     * @param infixa a expressão a converter
     * @result a expressão convertida
     */
    public static String paraSufixa(String infixa){
	Scanner expressao = new Scanner(infixa);
	String token;
	Stack<String> pilha = new Stack<String>();
	StringBuilder resultado = new StringBuilder();
	while (expressao.hasNext()){
	    token = expressao.next();
	    if (token.equals("("))
		pilha.push(token);
	    else if (token.equals(")")){
		while (!pilha.peek().equals("(")){
		    resultado.append(pilha.peek()+" ");
		    pilha.pop();
		}
		pilha.pop();
	    }
	    else if (token.equals("+") || token.equals("-")){
		while (!pilha.isEmpty() && (pilha.peek().equals("*") || pilha.peek().equals("/"))){
		    resultado.append(pilha.peek()+" ");
		    pilha.pop();
		}
		pilha.push(token);
	    }
	    else if (token.equals("*") || token.equals("/"))
		pilha.push(token);
	    else // é um operando
		resultado.append(token+" ");
	}
	expressao.close();
	while (!pilha.isEmpty()){
	    resultado.append(pilha.peek()+" ");
	    pilha.pop();
	}
	return resultado.toString();
    }

    /**
     * Verifica se um token é um operador.
     * @param token o token a testar
     * @return se é um operador
     */
    private static boolean eOperador(String token){
	return token.equals("+") || token.equals("-") ||
	    token.equals("*") || token.equals("/");
    }

}
