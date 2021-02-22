// Ivan Xavier - 92441

package io.calculator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RunCalculadoraSufixaTest {
	TestInfo testInfo;
	TestReporter testReporter;
	
	@BeforeEach
	void consoleLogs(TestInfo testInfo, TestReporter testReporter) {
		this.testInfo = testInfo;
		this.testReporter = testReporter;
		testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
	}
    @Nested
    @EnabledOnOs(OS.WINDOWS)
    class FileTestsWindows {
    	String filePath = new File("").getAbsolutePath();
    	String pathTest = filePath.concat("\\test_files\\test.txt");
    	String pathResult = filePath.concat("\\test_files\\result.txt");
    	@Test
    	@Tag("File")
    	//@Disabled
    	@DisplayName("writing a result file")
	    public void writeResultFileTest() throws IOException {
	    	FileWriter myWriter = new FileWriter(pathResult);
	    	myWriter.close();
    	}
	    @Test
	    @Tag("File")
	    //@Disabled
	    @DisplayName("creating and writing in a test file")
	    public void writeTestFileTest() throws IOException {
	    	FileWriter myWriter = new FileWriter(pathTest);
	    	myWriter.write("1 + ( 2 - ( 4 * 5 ) / 6 )");
	    	myWriter.close();
	    }
	    @Test
	    @Tag("File")
	    //@Disabled
	    @DisplayName("reading file")
	    public void readFileTest() throws FileNotFoundException {
	    	File myObj = new File(pathTest);
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	        	myReader.nextLine();
	        }
	        myReader.close();
	    }
	    @Test
	    @Tag("Application")
	    //@Disabled
	    //@RepeatedTest(5)
	    @DisplayName("running application with single line")
	    public void runCalculatorSingleLine() throws IOException {
	    	FileWriter myWriter = new FileWriter(pathTest);
	    	myWriter.write("1 + ( 2 - ( 4 * 5 ) / 6 )");
	    	myWriter.close();
			Scanner file = new Scanner(new FileReader(pathTest));
			BufferedWriter output = new BufferedWriter(new FileWriter(pathResult));
			RunCalculadoraSufixa.RunCalculadora(file, output);
			FileReader readResult = new FileReader(pathResult);
			BufferedReader bufferReader = new BufferedReader(readResult);
			String resultLine;
			StringBuilder result = new StringBuilder();
			while ((resultLine = bufferReader.readLine()) != null) {
				result.append(resultLine);
			};
		    bufferReader.close();
		    assertEquals(result.toString(),
		    		"1 2 4 5 * 6 / - + ? -0.3333333333333335",
		    		() -> "Should return: <1 2 4 5 * 6 / - + ? -0.3333333333333335> but actual" + result.toString());
	    }
	    @Test
	    @Tag("Application")
	    //@Disabled
	    //@RepeatedTest(5)
	    @DisplayName("running application with multiple lines")
	    public void runCalculatorMultipleLines() throws IOException {
	    	String pathTest = filePath.concat("/test_files/test2.txt");
	    	String pathResult = filePath.concat("/test_files/result2.txt");
	    	FileWriter myWriter = new FileWriter(pathTest);
	    	myWriter.write("1 + ( 2 - ( 4 * 5 ) / 6 )\r\n7 + ( 8 - ( 9 * 10 ) / 11 )\r\n");
	    	myWriter.close();
			Scanner file = new Scanner(new FileReader(pathTest));
			BufferedWriter output = new BufferedWriter(new FileWriter(pathResult));
			RunCalculadoraSufixa.RunCalculadora(file, output);
			FileReader readResult = new FileReader(pathResult);
			BufferedReader bufferReader = new BufferedReader(readResult);
			String resultLine;
			StringBuilder result = new StringBuilder();
			while ((resultLine = bufferReader.readLine()) != null) {
				result.append(resultLine + System.lineSeparator());
			};
		    bufferReader.close();
		    assertEquals(result.toString(),
		    		"1 2 4 5 * 6 / - + ? -0.3333333333333335\r\n7 8 9 10 * 11 / - + ? 6.818181818181818\r\n",
		    		() -> "Should return: <1 2 4 5 * 6 / - + ? -0.3333333333333335\\r\\n7 8 9 10 * 11 / - + ? 6.818181818181818> but actual" + result.toString());
	    } 
    }
    @Nested
    //@Disabled
    @DisplayName("Methods in CalculadoraSufixa")
    class CalculadoraSufixaMethods {
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("sum positive test")
	    public void verResultadoSumPositiveTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = 10.0;
	        calculator.processaToken("6");
	        calculator.processaToken("4");
	        calculator.processaToken("+");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <10.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("sum negative test")
	    public void verResultadoSumNegativeTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = -6.0;
	        calculator.processaToken("-2");
	        calculator.processaToken("-4");
	        calculator.processaToken("+");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <-6.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("subtract positive test")
	    public void verResultadoSubtractPositiveTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = -4.0;
	        calculator.processaToken("1");
	        calculator.processaToken("5");
	        calculator.processaToken("-");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <-4.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("subtract negative test")
	    public void verResultadoSubtractNegativeTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = 2.0;
	        calculator.processaToken("-2");
	        calculator.processaToken("-4");
	        calculator.processaToken("-");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <2.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("multiply positive test")
	    public void verResultadoMultiplyPositiveTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = 18.0;
	        calculator.processaToken("3");
	        calculator.processaToken("6");
	        calculator.processaToken("*");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <18.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("multiply negative test")
	    public void verResultadoMultiplyNegativeTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = 15.0;
	        calculator.processaToken("-3");
	        calculator.processaToken("-5");
	        calculator.processaToken("*");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <15.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("divide positive test")
	    public void verResultadoDividePositiveTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = 2.0;
	        calculator.processaToken("10");
	        calculator.processaToken("5");
	        calculator.processaToken("/");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <2.0> instead of " + actual);
	    }
	    @Test
	    @Tag("Operators")
	    //@Disabled
	    @DisplayName("divide negative test")
	    public void verResultadoDivideNegativeTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = 1.6;
	        calculator.processaToken("-8");
	        calculator.processaToken("-5");
	        calculator.processaToken("/");    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <1.6> instead of " + actual);
	    }
	    @Test
	    @Tag("Method")
	    //@Disabled
	    @DisplayName("verResultado() test")
	    public void verResultadoTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        double expected = -0.3333333333333335;
	        String string = "1 2 4 5 * 6 / - + ";
	        String[] stringParts = string.split(" ");
	        for(int i = 0; i < stringParts.length; i++)
	        { 
	           calculator.processaToken(stringParts[i]);
	        }    
	        double actual = calculator.verResultado();
	        assertEquals(expected, actual, () -> "This method should return: <-0.3333333333333335> instead of " + actual);
	    }
	    @Test
	    @Tag("Method")
	    //@Disabled
	    @DisplayName("haResultadoEmpty() test")
	    public void haResultadoEmptyTest() {
	        CalculadoraSufixa instance = new CalculadoraSufixa();
	        boolean expResult = false;
	        boolean result = instance.haResultado();
	        assertEquals(expResult, result);
	    }
	    @Test
	    @Tag("Method")
	    //@Disabled
	    @DisplayName("haResultado() test")
	    public void haResultadoTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        calculator.processaToken("1");
	        calculator.processaToken("2");
	        calculator.processaToken("+");
	        assertEquals(true, calculator.haResultado());
	    }
	    @Test
	    @Tag("Method")
	    //@Disabled
	    @DisplayName("paraSufixa() test")
	    public void paraSufixaTest() {
	        assertEquals("1 2 4 5 * 6 / - + ", CalculadoraSufixa.paraSufixa("1 + ( 2 - ( 4 * 5 ) / 6 )"), "Should convert infix to suffix");
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    //@RepeatedTest(5)
	    @DisplayName("valid tokens test")
	    public void processaValidTokenTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        calculator.processaToken("0");
	        calculator.processaToken("1");
	        calculator.processaToken("2");
	        calculator.processaToken("3");
	        calculator.processaToken("4");
	        calculator.processaToken("5");
	        calculator.processaToken("6");
	        calculator.processaToken("7");
	        calculator.processaToken("8");
	        calculator.processaToken("9");
	        calculator.processaToken("+");
	        calculator.processaToken("-");
	        calculator.processaToken("*");
	        calculator.processaToken("/");
	    }  
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("invalid letters test")
	    public void processaLetterTokenTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        try{
	        	calculator.processaToken("a");
	        	calculator.processaToken("b");
	        	calculator.processaToken("C");
	        	calculator.processaToken("D");
	        	fail("Should return error processing letters");
	        }catch(Exception exception){
	        	assertTrue(true);
	        }    
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("invalid special tokens test")
	    public void processaSpecialTokenTest() {
	        CalculadoraSufixa calculator = new CalculadoraSufixa();
	        try{
	        	calculator.processaToken("@");
	        	calculator.processaToken("{");
	        	calculator.processaToken("$");
	        	fail("Should return error processing special characters");
	        }catch(Exception exception){
	        	assertTrue(true);
	        }    
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("without brakets test")
	    public void verifyWithoutBraketsTest() {
	        assertTrue(CalculadoraSufixa.verificaParenteses("1 + 2 - 3 * 4 / 5"), "Should run whithout brackets");
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("with one braket test")
	    public void verifySingleBraketTest() {
	        assertTrue(CalculadoraSufixa.verificaParenteses("1 + ( 2 - 4 )"), "Should run with single bracket");
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("with multiple brakets test")
	    public void verifyMultipleBraketsTest() {
	        assertTrue(CalculadoraSufixa.verificaParenteses("1 + ( 2 - ( 4 * 5 ) / 6 )"), "Should run with multiple brackets");
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("with invalid left braket test")
	    public void verifyOneBracketErrorLeftTest() {
	        assertFalse(CalculadoraSufixa.verificaParenteses("1 + 2 ) 3 - 4 "), "Should return error with only one left braket");
	    }
	    @Test
	    @Tag("Validation")
	    //@Disabled
	    @DisplayName("with invalid right braket test")
	    public void verifyOneBracketErrorRightTest() {
	        assertFalse(CalculadoraSufixa.verificaParenteses("1 + 2 ( 3 - 4 "), "Should return error with only one right braket");
	    }
    }
	@Nested
    //@Disabled
	@EnabledOnOs(value = {OS.AIX, OS.LINUX, OS.MAC, OS.SOLARIS})
	class FileTestsAIX_Linux_Mac_Solaris {
		String filePath = new File("").getAbsolutePath();
    	String pathTest = filePath.concat("/test_files/test.txt");
    	String pathResult = filePath.concat("/test_files/result.txt");
		@Test
	    //@Disabled
		@Tag("File")
		@DisplayName("writing a result file")
	    public void writeResultFileTest() throws IOException {
	    	FileWriter myWriter = new FileWriter(pathResult);
	    	myWriter.close();
		}
	    @Test
	    //@Disabled
	    @Tag("File")
	    @DisplayName("creating and writing in a test file")
	    public void writeTestFileTest() throws IOException {
	    	FileWriter myWriter = new FileWriter(pathTest);
	    	myWriter.write("1 + ( 2 - ( 4 * 5 ) / 6 )");
	    	myWriter.close();
	    }
	    @Test
	    //@Disabled
	    @Tag("File")
	    @DisplayName("reading file")
	    public void readFileTest() throws FileNotFoundException {
	    	File myObj = new File(pathTest);
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	        	myReader.nextLine();
	        }
	        myReader.close();
	    }
	    @Test
	    //@Disabled
	    //@RepeatedTest(5)
	    @Tag("Application")
	    @DisplayName("running application with single line")
	    public void runCalculatorSingleLine() throws IOException {
	    	FileWriter myWriter = new FileWriter(pathTest);
	    	myWriter.write("1 + ( 2 - ( 4 * 5 ) / 6 )");
	    	myWriter.close();
			Scanner file = new Scanner(new FileReader(pathTest));
			BufferedWriter output = new BufferedWriter(new FileWriter(pathResult));
			RunCalculadoraSufixa.RunCalculadora(file, output);
			FileReader readResult = new FileReader(pathResult);
			BufferedReader bufferReader = new BufferedReader(readResult);
			String resultLine;
			StringBuilder result = new StringBuilder();
			while ((resultLine = bufferReader.readLine()) != null) {
				result.append(resultLine);
			};
		    bufferReader.close();
		    assertEquals(result.toString(),
		    		"1 2 4 5 * 6 / - + ? -0.3333333333333335",
		    		() -> "Should return: <1 2 4 5 * 6 / - + ? -0.3333333333333335> but actual" + result.toString());
	    }
	    @Test
	    //@Disabled
	    //@RepeatedTest(5)
	    @Tag("Application")
	    @DisplayName("running application with multiple lines")
	    public void runCalculatorMultipleLines() throws IOException {
	    	String pathTest = filePath.concat("/test_files/test2.txt");
	    	String pathResult = filePath.concat("/test_files/result2.txt");
	    	FileWriter myWriter = new FileWriter(pathTest);
	    	myWriter.write("1 + ( 2 - ( 4 * 5 ) / 6 )\r\n7 + ( 8 - ( 9 * 10 ) / 11 )\r\n");
	    	myWriter.close();
			Scanner file = new Scanner(new FileReader(pathTest));
			BufferedWriter output = new BufferedWriter(new FileWriter(pathResult));
			RunCalculadoraSufixa.RunCalculadora(file, output);
			FileReader readResult = new FileReader(pathResult);
			BufferedReader bufferReader = new BufferedReader(readResult);
			String resultLine;
			StringBuilder result = new StringBuilder();
			while ((resultLine = bufferReader.readLine()) != null) {
				result.append(resultLine + System.lineSeparator());
			};
		    bufferReader.close();
		    assertEquals(result.toString(),
		    		"1 2 4 5 * 6 / - + ? -0.3333333333333335\r\n7 8 9 10 * 11 / - + ? 6.818181818181818\r\n",
		    		() -> "Should return: <1 2 4 5 * 6 / - + ? -0.3333333333333335\\r\\n7 8 9 10 * 11 / - + ? 6.818181818181818> but actual" + result.toString());
	    } 
	}
}