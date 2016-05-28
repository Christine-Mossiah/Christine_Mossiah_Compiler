
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 * @author Christine Almodovar Spring '16
 *
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        Vector<Token> tkList = new Vector<Token>();
        /*
            Reading the text file and store the whole file into byte into a String
         */
        String inputText = new Scanner(new File("input.txt")).useDelimiter("\\A").next();
        inputText = inputText + "~";

        int [] tk_array = new int[]{0, 1, 0};
        myScanner scaner = new myScanner(inputText);


        //------------------------------------------
        //        Scanner
        //------------------------------------------
        Token tk;
        int index = tk_array[0];
        while(index < inputText.length())
        {
            tk = new Token();
            tk = scaner.getToken(tk_array,tk);

            if ( tk != null) {
                tkList.add(tk);
            }
            else
                System.out.println("Tk stored in List Failed");
            index = tk_array[0];
        }

        printToken(tkList);

        //------------------------------------------
        //        Parser
        //------------------------------------------
        Vector<symbolTable> SymtabList = new Vector<symbolTable>();
        Vector<instructionTable> instList = new Vector<instructionTable>();
        Parser parser = new Parser();
        parser.parse(tkList,SymtabList,instList);

        printInstr(instList);



        //------------------------------------------
        //        Stack
        //------------------------------------------
        StackM stackM = new StackM();
        stackM.evalStack(SymtabList,instList);


        PrintSymbol(SymtabList);

        System.out.println();
        System.out.println();
        System.out.println(inputText);




    }


    //          Table Printing              //
    //--------------------------------------
    public static void printToken(Vector<Token> tkList) {
        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|  Token List                                                                                                                                           |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|              TOKEN NAME             |             TOKEN TYPE              |                 LINE                |                COLUMN               |");
        System.out.println("|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|");
        Iterator itr = tkList.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }

        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printInstr(Vector<instructionTable> insList) {
        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|  Instruction List                                                                                                                                     |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|              Instr Ptr              |                 Name                |              Instructions           |                Values               |");
        System.out.println("|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|");
        Iterator<instructionTable> itr = insList.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }

        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void PrintSymbol(Vector<symbolTable> insList) {
        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|  Symbol Table After Execution                                                                                                                         |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                 Name                |                 Type                |                 Value               |                Address              |");
        System.out.println("|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|");
        Iterator<symbolTable> itr = insList.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }

        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
    }












}