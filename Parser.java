import java.util.Iterator;
import java.util.Vector;

/**
 * 
 * @author Christine Mossiah (Almodovar) Spring '16
 *
 */
public class Parser {
    private final int front_tk = 0;
    private final int first = 0;
    private static boolean op = true;
    private static boolean write = false;
    private static int addr = 0;
    private static int functionaddr = 0;


    public boolean match(String curToken, String Token){
        return (curToken.equals(Token));
    }

    public void parse(Vector<Token> tkList, Vector<symbolTable> SymtabList, Vector<instructionTable> instList ){

        // Check for Program as the beginning String  ***********************************************
        Token leadingTk = tkList.firstElement();
        tkList.remove(front_tk);
        String currentTk = leadingTk.getTOKEN_TYPE();

        if(match(currentTk, "TK_PROGRAM")){
            Token progName = tkList.firstElement();
            tkList.remove(front_tk);

            currentTk = progName.getTOKEN_TYPE();
            if (match(currentTk,"TK_ID")){

                Token semico = tkList.firstElement();
                tkList.remove(front_tk);

                currentTk = semico.getTOKEN_TYPE();
                if(match(currentTk,"TK_SEMICOLON")){
                }
                else {
                    System.out.println("ERROR! Missing Semicolon");
                }
            }
            else {
                System.out.println("ERROR! Program without a Name");
            }
        }
        else{  //#
            System.out.println("ERROR! Program without a Header");    //#
        }   //#
        // End Program Header  ***********************************************





        // Parsing Variable Declaration    ***********************************************
        int lowbound, upbound, arraysize;
        Token varDec, varName, varDec1, varDec2, semiColon, commaORcolon, type, arrayitem;
        symbolTable identif;
        Vector<symbolTable> identfList;


        varDec = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk = varDec.getTOKEN_TYPE();

        if (match(currentTk, "TK_VAR")) {
            while(true) {
                identfList  = new Vector();

                // The variable names that had been declared
                while (true) {
                    identif = new symbolTable();
                    varName = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk = varName.getTOKEN_TYPE();

                    if (match(currentTk, "TK_ID")) {
                        identif.setName(varName.getTokenName());
                        identif.setValue("0");

                        identfList.add(identif);

                        commaORcolon = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk = commaORcolon.getTOKEN_TYPE();
                        if (!(match(currentTk, "TK_COMMA"))) {
                            break;
                        }
                    }
                }


                // The type of the variable that had been declared.

                if (match(currentTk, "TK_COLON")) {
                    type = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk = type.getTOKEN_TYPE();

                    if (match(currentTk, "TK_INT")) {

                        symbolTable tempIdf;
                        Iterator itr = identfList.iterator();
                        while (itr.hasNext()) {
                            tempIdf = (symbolTable) itr.next();
                            tempIdf.setType("TK_INT");
                            tempIdf.setAddress(addr);
                            addr += 4;
                        }

                        semiColon = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk = semiColon.getTOKEN_TYPE();

                        if (match(currentTk, "TK_SEMICOLON")) {

                            SymtabList.addAll(identfList);

                            varDec1 = tkList.firstElement();
                            currentTk = varDec1.getTOKEN_TYPE();

                            if (!(match(currentTk, "TK_ID"))) {
                                break;
                            }
                        } else {
                            System.out.println("ERROR! NEED SEMICOLON TO END DECLARATION");
                        }
                    }
                    else if (match(currentTk, "TK_ARRAY")) {

                        arrayitem = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk = arrayitem.getTOKEN_TYPE();

                        if (match(currentTk, "TK_OPEN_BRACKET")) {

                            arrayitem = tkList.firstElement();
                            tkList.remove(front_tk);
                            currentTk = arrayitem.getTOKEN_TYPE();


                            if (match(currentTk, "TK_INTEGER_LIT")){

                                lowbound = Integer.parseInt(arrayitem.getTokenName());

                                arrayitem = tkList.firstElement();
                                tkList.remove(front_tk);
                                currentTk = arrayitem.getTOKEN_TYPE();

                                if (match(currentTk, "TK_PERIOD")) {

                                    arrayitem = tkList.firstElement();
                                    tkList.remove(front_tk);
                                    currentTk = arrayitem.getTOKEN_TYPE();

                                    if (match(currentTk, "TK_PERIOD")) {

                                        arrayitem = tkList.firstElement();
                                        tkList.remove(front_tk);
                                        currentTk = arrayitem.getTOKEN_TYPE();

                                        if ( match(currentTk, "TK_INTEGER_LIT")) {

                                            upbound = Integer.parseInt(arrayitem.getTokenName());


                                            arraysize = upbound - lowbound + 1;

                                            arrayitem = tkList.firstElement();
                                            tkList.remove(front_tk);
                                            currentTk = arrayitem.getTOKEN_TYPE();

                                            if (match(currentTk,"TK_CLOSE_BRACKET")) {

                                                arrayitem = tkList.firstElement();
                                                tkList.remove(front_tk);
                                                currentTk = arrayitem.getTOKEN_TYPE();

                                                if ( match(currentTk, "TK_OF")) {

                                                    arrayitem = tkList.firstElement();
                                                    tkList.remove(front_tk);
                                                    currentTk = arrayitem.getTOKEN_TYPE();

                                                    if ( match(currentTk, "TK_INT")) {

                                                        symbolTable tempIdf;
                                                        Iterator itr = identfList.iterator();
                                                        while (itr.hasNext()) {
                                                            tempIdf = (symbolTable) itr.next();
                                                            tempIdf.setType("TK_ARRAY");
                                                            tempIdf.setValue(Integer.toString(arraysize));
                                                            tempIdf.setAddress(addr);
                                                            addr += 4;
                                                        }



                                                        symbolTable tempidf1;
                                                        for ( int ii = lowbound; ii <= upbound; ii++) {
                                                            tempidf1 = new symbolTable();
                                                            tempidf1.setType("TK_INT");
                                                            tempidf1.setValue("0");
                                                            tempidf1.setName(identfList.elementAt(0).getName() + Integer.toString(ii));
                                                            tempidf1.setAddress(addr);
                                                            identfList.add(tempidf1);
                                                            addr+=4;
                                                        }

                                                        arrayitem = tkList.firstElement();
                                                        tkList.remove(front_tk);
                                                        currentTk = arrayitem.getTOKEN_TYPE();


                                                        if (match(currentTk, "TK_SEMICOLON")) {

                                                            SymtabList.addAll(identfList);

                                                            varDec2 = tkList.firstElement();
                                                            currentTk = varDec2.getTOKEN_TYPE();

                                                            if (!(match(currentTk, "TK_ID"))) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    System.out.println("MISSING OF IN CREATING ARRAY");
                                                }

                                            }
                                            else {
                                                System.out.println("MISSING CLOSING BRACKET");
                                            }
          }
                                        else {
                                            System.out.println("ERROR, MISSING UPPERBOUND");
                                        }
                                    }
                                    else {
                                        System.out.println("ERROR MISSING SECOND PERIOD");
                                    }

                                }
                                else {
                                    System.out.println("ERROR MISSING FIRST PERIOD");
                                }

                            }
                            else {
                                System.out.println("ERROR, MISSING LOWERBOUND");
                            }
                        }
                        else {
                            System.out.println("ERROR, MISSING OPENING BRACKET");
                        }


                    }
                    else {
                        System.out.println("ERROR! TYPE IS Missing ");
                    }
                } else {
                    System.out.println("Without any Variable Declaration *************");
                }
            }
        }

        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|  Symbol Table After Declaration                                                                                                                       |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                 Name                |                 Type                |                 Value               |                Address              |");
        System.out.println("|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|");
        print(SymtabList);
        System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------------------");


        // End Variable Declaration ***********************************************





        // Parsing Function Declaration

        StringBuffer funcurrenttemp = new StringBuffer();
        int[] instructPtr = new int[] {0};
        functionaddr = instructPtr[0];


        if (match(currentTk,"TK_FUNCTION")) {

            Token funcnewTk = new Token();
            funcnewTk = tkList.firstElement();
            Token[] funcfreshTk= new Token[]{funcnewTk};

            funcfreshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk = funcfreshTk[first].getTOKEN_TYPE();
            funcurrenttemp.append(currentTk);

            functionDec(funcurrenttemp, funcfreshTk, tkList, instList, instructPtr, SymtabList);

            currentTk = funcurrenttemp.toString();

        }







        // END Parsing FUnction delcaration





        // Beginning Parsing Statements ***********************************************

        StringBuffer tempcurrentTk = new StringBuffer();

        tkList.remove(front_tk);
        if (!match(currentTk,"TK_BEGIN")) {                                 //Taking Care of begin
            System.out.println("ERROR Begin is missing");
        }

        Token newTk = new Token();
        newTk = tkList.firstElement();
        Token[] freshTk= new Token[]{newTk};

        while (!match(currentTk,"TK_END") ) {

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk = freshTk[first].getTOKEN_TYPE();
            tempcurrentTk.append(currentTk);


            Statements(tempcurrentTk, freshTk, tkList, instList, instructPtr, SymtabList);

            currentTk = tempcurrentTk.toString();

            if (match(currentTk, "TK_END")){
                break;
            }
        }

        instructionTable B = new instructionTable();
        B.setInstPtr(instructPtr[0]);
        B.setinstr("op_halt");
        B.setValue("END.");
        B.setType("TK_END");
        instList.add(B);
        instructPtr[0]++;
    }





    private void functionDec(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr, Vector<symbolTable> SymtabList ){
        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

        int lowbound, upbound, arraysize;
        Token varDec, varName, varDec1, varDec2, semiColon, commaORcolon, type, arrayitem;
        symbolTable identif;
        Vector<symbolTable> identfList;

        if (match(currentTk.toString(), "TK_VAR")) {
            while(true) {
                identfList  = new Vector();

                // The variable names that had been declared
                while (true) {


                    identif = new symbolTable();
                    varName = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0,currentTk.length(),varName.getTOKEN_TYPE());

                    if (match(currentTk.toString(), "TK_ID")) {
                        identif.setName(varName.getTokenName());
                        identif.setValue("0");

                        identfList.add(identif);

                        commaORcolon = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk.replace(0,currentTk.length(),commaORcolon.getTOKEN_TYPE());
                        if (!(match(currentTk.toString(), "TK_COMMA"))) {
                            break;
                        }
                    }
                }


                // The type of the variable that had been declared.

                if (match(currentTk.toString(), "TK_COLON")) {
                    type = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0,currentTk.length(),type.getTOKEN_TYPE());

                    if (match(currentTk.toString(), "TK_INT")) {

                        symbolTable tempIdf;
                        Iterator itr = identfList.iterator();
                        while (itr.hasNext()) {
                            tempIdf = (symbolTable) itr.next();
                            tempIdf.setType("TK_INT");
                            tempIdf.setAddress(addr);
                            addr += 4;
                        }

                        semiColon = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk.replace(0,currentTk.length(),semiColon.getTOKEN_TYPE());


                        if (match(currentTk.toString(), "TK_SEMICOLON")) {

                            SymtabList.addAll(identfList);

                            varDec1 = tkList.firstElement();
                            freshTk[first] = tkList.firstElement();
                            currentTk.replace(0,currentTk.length(),varDec1.getTOKEN_TYPE());

                            if (!(match(currentTk.toString(), "TK_ID"))) {
                                break;
                            }
                        } else {
                            System.out.println("ERROR! NEED SEMICOLON IN THE TO END DECLARATION");
                        }
                        print(identfList);
                    }
                    else if (match(currentTk.toString(), "TK_ARRAY")) {

                        arrayitem = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                        if (match(currentTk.toString(), "TK_OPEN_BRACKET")) {

                            arrayitem = tkList.firstElement();
                            tkList.remove(front_tk);
                            currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());


                            if (match(currentTk.toString(), "TK_INTEGER_LIT")){

                                lowbound = Integer.parseInt(arrayitem.getTokenName());

                                arrayitem = tkList.firstElement();
                                tkList.remove(front_tk);
                                currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                if (match(currentTk.toString(), "TK_PERIOD")) {

                                    arrayitem = tkList.firstElement();
                                    tkList.remove(front_tk);
                                    currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                    if (match(currentTk.toString(), "TK_PERIOD")) {

                                        arrayitem = tkList.firstElement();
                                        tkList.remove(front_tk);
                                        currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                        if ( match(currentTk.toString(), "TK_INTEGER_LIT")) {

                                            upbound = Integer.parseInt(arrayitem.getTokenName());


                                            arraysize = upbound - lowbound + 1;

                                            arrayitem = tkList.firstElement();
                                            tkList.remove(front_tk);
                                            currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                            if (match(currentTk.toString(),"TK_CLOSE_BRACKET")) {

                                                arrayitem = tkList.firstElement();
                                                tkList.remove(front_tk);
                                                currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                                if ( match(currentTk.toString(), "TK_OF")) {

                                                    arrayitem = tkList.firstElement();
                                                    tkList.remove(front_tk);
                                                    currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                                    if ( match(currentTk.toString(), "TK_INT")) {

                                                        symbolTable tempIdf;
                                                        Iterator itr = identfList.iterator();
                                                        while (itr.hasNext()) {
                                                            tempIdf = (symbolTable) itr.next();
                                                            tempIdf.setType("TK_ARRAY");
                                                            tempIdf.setValue(Integer.toString(arraysize));
                                                            tempIdf.setAddress(addr);
                                                            addr += 4;
                                                        }



                                                        symbolTable tempidf1;
                                                        for ( int ii = lowbound; ii <= upbound; ii++) {
                                                            tempidf1 = new symbolTable();
                                                            tempidf1.setType("TK_INT");
                                                            tempidf1.setValue("0");
                                                            tempidf1.setName(identfList.elementAt(0).getName() + Integer.toString(ii));
                                                            tempidf1.setAddress(addr);
                                                            identfList.add(tempidf1);
                                                            addr+=4;
                                                        }

                                                        arrayitem = tkList.firstElement();
                                                        tkList.remove(front_tk);
                                                        currentTk.replace(0,currentTk.length(),arrayitem.getTOKEN_TYPE());

                                                        print(identfList);

                                                        if (match(currentTk.toString(), "TK_SEMICOLON")) {

                                                            SymtabList.addAll(identfList);

                                                            varDec2 = tkList.firstElement();
                                                            freshTk[first] = tkList.firstElement();
                                                            currentTk.replace(0, currentTk.length(), varDec2.getTOKEN_TYPE());

                                                            if (!(match(currentTk.toString(), "TK_ID"))) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    System.out.println("MISSING OF IN CREATING ARRAY");
                                                }

                                            }
                                            else {
                                                System.out.println("MISSING CLOSING BRACKET");
                                            }
                                        }
                                        else {
                                            System.out.println("ERROR, MISSING UPPERBOUND");
                                        }
                                    }
                                    else {
                                        System.out.println("ERROR MISSING SECOND PERIOD");
                                    }

                                }
                                else {
                                    System.out.println("ERROR MISSING FIRST PERIOD");
                                }

                            }
                            else {
                                System.out.println("ERROR, MISSING LOWERBOUND");
                            }
                        }
                        else {
                            System.out.println("ERROR, MISSING OPENING BRACKET");
                        }


                    }
                    else {
                        System.out.println("ERROR! TYPE IS Missing ");
                    }
                } else {
                    System.out.println("Without any Variable Declaration *************");
                }
            }
        }

        tkList.remove(front_tk);
        if (!match(currentTk.toString(),"TK_BEGIN")) {                                 //Taking Care of begin
            System.out.println("ERROR Begin is missing");
        }

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

        StringBuffer tempcurrentTk = new StringBuffer();

        while (!match(currentTk.toString(),"TK_ENDBLOCK") ) {



            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());


            Statements(currentTk, freshTk, tkList, instList, instructPtr, SymtabList);


            if (match(currentTk.toString(), "TK_ENDBLOCK")){
                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

                if (match(currentTk.toString(), "TK_SEMICOLON")){
                    break;
                }
            }

        }

    }



    private void Statements(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr, Vector<symbolTable> SymtabList ) {

        String lefthSide = new String();
        StringBuffer idbuild;

        while( true) {
            if (match(currentTk.toString(),"TK_ID")) {
                idbuild = new StringBuffer();

                idbuild.append(freshTk[first].getTokenName());

                if (match(tkList.firstElement().getTOKEN_TYPE(),"TK_OPEN_BRACKET")) {
                    tkList.remove(front_tk);

                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

                    if (match(currentTk.toString(),"TK_INTEGER_LIT")) {
                        idbuild.append((freshTk[first].getTokenName()));

                        freshTk[first] = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());
                    }
                    else if (match(currentTk.toString(),"TK_ID")) {
                        idbuild.append("#"+ (freshTk[first].getTokenName()));

                        freshTk[first] = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());
                    }
                }
                lefthSide = idbuild.toString();

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            }
            else if (match(currentTk.toString(), "TK_REPEAT")) {

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

                Repeat(currentTk, freshTk, tkList, instList, instructPtr, SymtabList);

            }
            else if (match(currentTk.toString(),"TK_WHILE")) {

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

                whileStatement(currentTk, freshTk, tkList, instList, instructPtr, SymtabList);
            }
            else if (match(currentTk.toString(),"TK_FOR")) {

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

                forStatement(currentTk, freshTk, tkList, instList, instructPtr,SymtabList);
            }

            else if (match(currentTk.toString(),"TK_WRITELN")) {
                write = true;

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                Writeln(currentTk, freshTk, tkList, instList, instructPtr);
            }
            else if (match(currentTk.toString(), "TK_IF")) {
                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                Ifelse(currentTk, freshTk, tkList, instList, instructPtr,SymtabList);
            }

            //Since the first token is a Identifier, find the assignment operator
            if (match(currentTk.toString(), "TK_ASSIGNMENT")) {

                op = true;

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());
            }


            Logic(currentTk, freshTk, tkList, instList, instructPtr);

            //Ending an statement
            if(match(currentTk.toString(), "TK_SEMICOLON")) {
                if(op && !write ) {
                    instructionTable B = new instructionTable();
                    B.setInstPtr(instructPtr[0]);
                    B.setinstr("op_pop");
                    B.setValue(lefthSide);
                    instList.add(B);
                    instructPtr[0]++;
                    op = false;
                }
                else if (write) {
                    instructionTable B = new instructionTable();
                    B.setInstPtr(instructPtr[0]);
                    B.setinstr("op_writeln");
                    B.setValue("");
                    instList.add(B);
                    instructPtr[0]++;
                    write = false;

                }

            }

            if (match(currentTk.toString(), "TK_UNTIL")) {
                return;
            }

            if (match(currentTk.toString(), "TK_ELSE")) {
                return;
            }

            if ( match(currentTk.toString(), "TK_ENDBLOCK")){
                return;
            }

            if (match(currentTk.toString(),"TK_END" )) {
                break;
            }
                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());



        }
    }



    private void Logic(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {

        String save = currentTk.toString();
        Token saveTk = freshTk[first];

        if (match(currentTk.toString(), "TK_GREATER")) {
            op = false;
            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if(match(currentTk.toString(), "TK_SEMICOLON")) {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_greater");
                B.setValue("greater");
                B.setType(currentTk.toString());
                instList.add(B);
                instructPtr[0]++;
            }
            else {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_greater");
                B.setValue("greater");
                B.setType(save);
                instList.add(B);
                instructPtr[0]++;
            }
        }
        else if (match(currentTk.toString(), "TK_LESS")) {
            op = false;

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if (match(currentTk.toString(), "TK_SEMICOLON")) {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_less");
                B.setValue("less");
                B.setType(currentTk.toString());
                instList.add(B);
                instructPtr[0]++;
            } else {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_less");
                B.setValue("less");
                B.setType(save);
                instList.add(B);
                instructPtr[0]++;
            }
        }
        else {
            E(currentTk, freshTk, tkList, instList, instructPtr);
        }
    }





    private void E(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {
        T(currentTk, freshTk, tkList, instList, instructPtr);
        E_PRIME(currentTk, freshTk, tkList, instList, instructPtr);
    }


    private void T(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {
        F(currentTk, freshTk, tkList, instList, instructPtr);
        T_PRIME(currentTk, freshTk, tkList, instList, instructPtr);
    }


    private void E_PRIME(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {
        String save = currentTk.toString();
        Token saveTk = freshTk[first];

        if(match(currentTk.toString(), "TK_PLUS")) {

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if (match(currentTk.toString(), "TK_SEMICOLON")) {

                T(currentTk, freshTk, tkList, instList, instructPtr);

                instructionTable A = new instructionTable();

                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_add");
                A.setValue("+");
                A.setType(currentTk.toString());
                instList.add(A);

                instructPtr[0]++;
            }
            else {

                T(currentTk, freshTk, tkList, instList, instructPtr);

                instructionTable A = new instructionTable();
                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_add");
                A.setValue("+");
                A.setType(save);
                instList.add(A);
                instructPtr[0]++;
            }
            E_PRIME(currentTk, freshTk, tkList, instList, instructPtr);
        }
        else if (match(currentTk.toString(), "TK_MINUS")) {

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if (match(currentTk.toString(), "TK_SEMICOLON")) {
                T(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable A = new instructionTable();
                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_sub");
                A.setValue(freshTk[first].getTokenName());
                A.setType(currentTk.toString());
                instList.add(A);
                instructPtr[0]++;
            }
            else {
                T(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable A = new instructionTable();
                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_sub");
                A.setValue(saveTk.getTokenName());
                A.setType(save);
                instList.add(A);
                instructPtr[0]++;
            }
            E_PRIME(currentTk, freshTk, tkList, instList, instructPtr);
        }
        else {
            return;
        }
    }


    private void F(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {
        StringBuffer idbuild1;
        String ttype;
        if (match(currentTk.toString(), "TK_ID")) {


            idbuild1 = new StringBuffer();
            ttype = new String();

            idbuild1.append(freshTk[first].getTokenName());
            if (match(tkList.firstElement().getTOKEN_TYPE(),"TK_OPEN_BRACKET")) {
                tkList.remove(front_tk);

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());

                if (match(currentTk.toString(),"TK_INTEGER_LIT")) {
                    idbuild1.append((freshTk[first].getTokenName()));
                    ttype = "TK_ID";


                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());
                }
                else if (match(currentTk.toString(),"TK_ID")) {
                    idbuild1.append("#"+ (freshTk[first].getTokenName()));
                    ttype = "TK_ID";

                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0, currentTk.length(), freshTk[first].getTOKEN_TYPE());
                }

            }
            else {
                ttype = currentTk.toString();
            }


            instructionTable I = new instructionTable();
            I.setInstPtr(instructPtr[0]);
            I.setinstr("op_push");
            I.setValue(idbuild1.toString());
            I.setType(ttype);
            instList.add(I);
            instructPtr[0]++;

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            return;
        }
        else if (match(currentTk.toString(), "TK_INTEGER_LIT")) {
            instructionTable I = new instructionTable();
            I.setInstPtr(instructPtr[0]);
            I.setinstr("op_push");
            I.setValue(freshTk[first].getTokenName());
            I.setType(currentTk.toString());
            instList.add(I);
            instructPtr[0]++;

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            return;
        }
        else if (match(currentTk.toString(), "TK_STR_LIT")) {
            instructionTable I = new instructionTable();
            I.setInstPtr(instructPtr[0]);
            I.setinstr("op_push");
            I.setValue(freshTk[first].getTokenName());
            I.setType(currentTk.toString());
            instList.add(I);
            instructPtr[0]++;

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            return;
        }
        else {
            return;
        }
    }


    private void T_PRIME(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {
        String save = currentTk.toString();
        Token saveTk = freshTk[first];

        if (match(currentTk.toString(), "TK_MULTI")) {
            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if(match(currentTk.toString(), "TK_SEMICOLON")) {
                F(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable A = new instructionTable();
                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_multi");
                A.setValue(freshTk[first].getTokenName());
                A.setType(currentTk.toString());
                instList.add(A);
                instructPtr[0]++;
            }
            else {
                F(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable A = new instructionTable();
                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_multi");
                A.setValue(saveTk.getTokenName());
                A.setType(save);
                instList.add(A);
                instructPtr[0]++;
            }
            T_PRIME(currentTk, freshTk, tkList, instList, instructPtr);
        }
        else if (match(currentTk.toString(), "TK_GREATER")) {
            op = false;
            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if(match(currentTk.toString(), "TK_SEMICOLON")) {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_greater");
                B.setValue("greater");
                B.setType(currentTk.toString());
                instList.add(B);
                instructPtr[0]++;
            }
            else {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_greater");
                B.setValue("greater");
                B.setType(save);
                instList.add(B);
                instructPtr[0]++;
            }
        }
        else if (match(currentTk.toString(), "TK_LESS")) {
            op = false;
            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if (match(currentTk.toString(), "TK_SEMICOLON")) {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_less");
                B.setValue("less");
                B.setType(currentTk.toString());
                instList.add(B);
                instructPtr[0]++;
            } else {
                E(currentTk, freshTk, tkList, instList, instructPtr);
                instructionTable B = new instructionTable();
                B.setInstPtr(instructPtr[0]);
                B.setinstr("op_less");
                B.setValue("less");
                B.setType(save);
                instList.add(B);
                instructPtr[0]++;
            }
        }


        return;

    }







    private  void Repeat(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr , Vector<symbolTable> SymtabList) {
        int i = instructPtr[0];

        Statements(currentTk, freshTk, tkList, instList, instructPtr, SymtabList);

        if (match(currentTk.toString(), "TK_UNTIL")) {
            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            Logic(currentTk, freshTk, tkList, instList, instructPtr);


            instructionTable B = new instructionTable();
            B.setInstPtr(instructPtr[0]);
            B.setinstr("op_jfalse");
            B.setValue( Integer.toString(i));
            instList.add(B);
            instructPtr[0]++;
        }
    }


    private void forStatement(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr, Vector<symbolTable> SymtabList) {

        String indexID, indexType;
        symbolTable index = new symbolTable();
        indexID = freshTk[first].getTokenName();
        indexType = currentTk.toString();
        index.setName(freshTk[first].getTokenName());
        index.setType(currentTk.toString());
        index.setValue("0");
        index.setAddress(addr);
        addr +=4;
        SymtabList.add(index);

        freshTk[first] = tkList.firstElement();
        tkList.remove(front_tk);
        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

        if (match(currentTk.toString(), "TK_ASSIGNMENT")) {
            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if (match(currentTk.toString(), "TK_INTEGER_LIT")) {

                instructionTable ins = new instructionTable();
                ins.setInstPtr(instructPtr[0]);
                ins.setinstr("op_push");
                ins.setType(freshTk[first].getTOKEN_TYPE());
                ins.setValue(freshTk[first].getTokenName());
                instructPtr[0]++;
                instList.add(ins);

                ins = new instructionTable();
                ins.setInstPtr(instructPtr[0]);
                ins.setinstr("op_pop");
                ins.setType(indexType);
                ins.setValue(indexID);
                instructPtr[0]++;
                instList.add(ins);

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                if (match(currentTk.toString(), "TK_TO")){

                    int jumpInstr  = instructPtr[0];

                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                    if (match(currentTk.toString(),"TK_INTEGER_LIT")) {

                        ins = new instructionTable();
                        ins.setInstPtr(instructPtr[0]);
                        ins.setinstr("op_push");
                        ins.setType(indexType);
                        ins.setValue(indexID);
                        instructPtr[0]++;
                        instList.add(ins);

                        ins = new instructionTable();
                        ins.setInstPtr(instructPtr[0]);
                        ins.setinstr("op_push");
                        ins.setType(freshTk[first].getTOKEN_TYPE());
                        ins.setValue(freshTk[first].getTokenName());
                        instructPtr[0]++;
                        instList.add(ins);


                        ins = new instructionTable();
                        ins.setInstPtr(instructPtr[0]);
                        ins.setinstr("op_less");
                        ins.setValue("less");
                        ins.setType("TK_LESS");
                        instructPtr[0]++;
                        instList.add(ins);

                        freshTk[first] = tkList.firstElement();
                        tkList.remove(front_tk);
                        currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                        if (match(currentTk.toString(),"TK_DO")){

                            freshTk[first] = tkList.firstElement();
                            tkList.remove(front_tk);
                            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                            if (match(currentTk.toString(), "TK_BEGIN")) {

                                int jumpfalseinst = instructPtr[0];

                                ins = new instructionTable();
                                ins.setInstPtr(instructPtr[0]);
                                ins.setinstr("op_jfalse");
                                instructPtr[0]++;
                                instList.add(ins);

                                freshTk[first] = tkList.firstElement();
                                tkList.remove(front_tk);
                                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                                Statements(currentTk, freshTk, tkList, instList,instructPtr, SymtabList);

                                ins = new instructionTable();
                                ins.setInstPtr(instructPtr[0]);
                                ins.setinstr("op_push");
                                ins.setType(indexType);
                                ins.setValue(indexID);
                                instructPtr[0]++;
                                instList.add(ins);

                                ins = new instructionTable();
                                ins.setInstPtr(instructPtr[0]);
                                ins.setinstr("op_push");
                                ins.setType("TK_INTEGER_LIT");
                                ins.setValue("1");
                                instructPtr[0]++;
                                instList.add(ins);

                                ins = new instructionTable();
                                ins.setInstPtr(instructPtr[0]);
                                ins.setinstr("op_add");
                                ins.setType("TK_ADD");
                                ins.setValue("+");
                                instructPtr[0]++;
                                instList.add(ins);



                                ins = new instructionTable();
                                ins.setInstPtr(instructPtr[0]);
                                ins.setinstr("op_pop");
                                ins.setType(indexType);
                                ins.setValue(indexID);
                                instructPtr[0]++;
                                instList.add(ins);

                                Iterator itr = instList.iterator();
                                while (itr.hasNext()) {
                                    instructionTable curinstruc = (instructionTable)(itr.next());
                                    if(curinstruc.getInstPtr() == jumpfalseinst){
                                        curinstruc.setValue(Integer.toString(instructPtr[0]));
                                        break;
                                    }
                                }


                                if (match(currentTk.toString(),"TK_ENDBLOCK")) {

                                    freshTk[first] = tkList.firstElement();
                                    tkList.remove(front_tk);
                                    currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                                        ins = new instructionTable();
                                        ins.setInstPtr(instructPtr[0]);
                                        ins.setinstr("op_jmp");
                                        ins.setValue(Integer.toString(jumpInstr));
                                        instList.add(ins);
                                        instructPtr[0]++;

                                        op = false;

                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println("MISSING TO IN A FOR STATEMENT");
                }
            }
            else {
                System.out.println("NOT AN INTEGER");
            }
        }
        else {
            System.out.println("ASSIGNMENT REQUIRE FOR IMPLEMENTATION");
        }
    }

    private void whileStatement(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr, Vector<symbolTable> SymtabList ) {
        int i = instructPtr[0];

        Logic (currentTk, freshTk, tkList, instList,instructPtr);


        if(match(currentTk.toString(),"TK_DO")){

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            instructionTable E = new instructionTable();
            E.setInstPtr(instructPtr[0]);
            E.setinstr("op_jfalse");
            instList.add(E);
            instructPtr[0]++;

            if (match(currentTk.toString(),"TK_BEGIN")){

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                Statements(currentTk, freshTk, tkList, instList,instructPtr, SymtabList);

                Iterator itr = instList.iterator();
                while (itr.hasNext()) {
                    instructionTable curinstruc = (instructionTable)(itr.next());
                    if(curinstruc.getInstPtr() == E.getInstPtr()){
                        curinstruc.setValue(Integer.toString(instructPtr[0]));
                        break;
                    }
                }


                if (match(currentTk.toString(),"TK_ENDBLOCK")) {

                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                    instructionTable A = new instructionTable();
                    A.setInstPtr(instructPtr[0]);
                    A.setinstr("op_jmp");
                    A.setValue(Integer.toString(i));
                    instList.add(A);
                    instructPtr[0]++;


                }

            }
            else {
                Statements(currentTk, freshTk, tkList, instList,instructPtr, SymtabList);

                instructionTable A = new instructionTable();
                A.setInstPtr(instructPtr[0]);
                A.setinstr("op_jmp");
                A.setValue(Integer.toString(i));
                instList.add(A);
                instructPtr[0]++;

                Iterator itr = instList.iterator();
                while (itr.hasNext()) {
                    instructionTable curinstruc = (instructionTable)(itr.next());
                    if(curinstruc.getInstPtr() == E.getInstPtr()){
                        curinstruc.setValue(Integer.toString(instructPtr[0]));
                        break;
                    }
                }
            }






        }

    }

    private void Writeln(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr) {
        if (match(currentTk.toString(), "TK_OPEN_PAREN")) {


            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            if(match(currentTk.toString(), "TK_SINGLE_QUOTE")) {


                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                Logic(currentTk,freshTk,tkList,instList,instructPtr);

                if(match(currentTk.toString(), "TK_SINGLE_QUOTE")) {


                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());
                }
                else {
                    System.out.println("ERROR! MISSING SINGLE QUOTATION IN WRITELN");
                }
            }
            else {

                Logic(currentTk, freshTk, tkList, instList, instructPtr);

            }

            if(match(currentTk.toString(), "TK_CLOSE_PAREN")) {
                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                if(match(currentTk.toString(), "TK_SEMICOLON")) {
                    instructionTable B = new instructionTable();
                    B.setInstPtr(instructPtr[0]);
                    B.setinstr("op_writeln");
                    B.setValue("");
                    instList.add(B);
                    instructPtr[0]++;
                    write = false;

                    freshTk[first] = tkList.firstElement();
                    tkList.remove(front_tk);
                    currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());
                }
            }
        }
    }

    private void Ifelse(StringBuffer currentTk, Token[] freshTk, Vector<Token> tkList, Vector<instructionTable> instList, int[] instructPtr, Vector<symbolTable> SymtabList) {
        Logic(currentTk, freshTk, tkList, instList, instructPtr);

        if (match(currentTk.toString(), "TK_THEN")) {

            freshTk[first] = tkList.firstElement();
            tkList.remove(front_tk);
            currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

            instructionTable E = new instructionTable();
            E.setInstPtr(instructPtr[0]);
            E.setinstr("op_jfalse");
            E.setValue("");
            E.setType(currentTk.toString());
            instList.add(E);
            instructPtr[0]++;

            Statements(currentTk, freshTk, tkList, instList, instructPtr, SymtabList);

            Iterator itr = instList.iterator();
            while (itr.hasNext()) {
                instructionTable curinstruc = (instructionTable)(itr.next());
                if(curinstruc.getInstPtr() == E.getInstPtr()){
                    curinstruc.setValue(Integer.toString(instructPtr[0]));
                    break;
                }
            }

            E.setValue(Integer.toString(instructPtr[0]));

            if( match(currentTk.toString(), "TK_ELSE")) {

                freshTk[first] = tkList.firstElement();
                tkList.remove(front_tk);
                currentTk.replace(0,currentTk.length(),freshTk[first].getTOKEN_TYPE());

                instructionTable C = new instructionTable();
                C.setInstPtr(instructPtr[0]);
                C.setinstr("op_jmp");
                C.setValue("");
                C.setType(currentTk.toString());
                instList.add(C);
                instructPtr[0]++;

                Statements(currentTk, freshTk, tkList, instList, instructPtr, SymtabList);

                Iterator itr1 = instList.iterator();
                while (itr1.hasNext()) {
                    instructionTable curinstruc = (instructionTable)(itr1.next());
                    if(curinstruc.getInstPtr() == C.getInstPtr()){
                        curinstruc.setValue(Integer.toString(instructPtr[0]));
                        break;
                    }
                }
            }
        }
    }

    private void print(Vector list) {
        //System.out.println(list.elementAt(0));

        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            System.out.println(itr.next());
        }

    }
}
