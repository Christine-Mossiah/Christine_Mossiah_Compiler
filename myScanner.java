import java.util.Objects;

/**
 * 
 * @author Christine Almodovar Spring '16
 *
 */
public class myScanner {
    private static String text;
    private static String[] KEYWORDS= new String[] {"PROGRAM", "BEGIN", "END.", "END", "PROCEDURE", "IF", "THEN", "ELSE",
            "FOR", "WHILE", "DO", "AND", "OR", "TO", "OF", "NOT", "REPEAT","BREAK", "FUNCTION", "TYPE", "UNTIL", "LABEL", "CONST", "VAR",
            "INTEGER", "REAL", "WRITELN", "ARRAY"};
    StringBuffer stringBulder;
    public myScanner(String file)
    {
        text = file;
    }



    /*****************************
     *
        NOTE:
            INFO is an array of size 3
                INFO[0] = index of the text fie
                INFO[1] = Lines
                INFO[2] = Column or number of word we are up to
     *
    *****************************/

    public Token getToken(int[] info, Token tk){
        //----------------------------------

        //  Case:   Endline

        //----------------------------------

        while (text.charAt(info[0]) == '\n'){
            info[0]++;
            info[1]++;
            info[2]=0;
        }



        //----------------------------------

        //  Case:   White spaces

        //----------------------------------
        while((Character.isSpaceChar(text.charAt(info[0])) && text.charAt(info[0]) != '~')
                || (Character.isWhitespace(text.charAt(info[0])) && text.charAt(info[0]) != '~')){
            info[0]++; info[2]++;
        }



        //----------------------------------

        //  Case:   String literals
        //          if we found an alphabet

        //----------------------------------
        if (Character.isAlphabetic(text.charAt(info[0])) ){

                stringBulder = new StringBuffer();
                if ((info[0]-1) != -1 && text.charAt(info[0]-1) == '\''){
                    while(text.charAt(info[0]) != '\'' && text.charAt(info[0])!= '~' ){
                        if ( text.charAt(info[0])== '\n') {
                            info[1]++;
                            info[2] = 0;
                        }
                        stringBulder.append(text.charAt(info[0]));
                        info[0]++;
                        info[2]++;

                    }
                    if(text.charAt(info[0]) == '~') {
                        System.out.println("Error: Single quotation Missing");
                        tk.setTokenName(stringBulder.toString());
                        tk.setTOKEN_TYPE("TK_ERROR");
                        tk.setLine_num(info[1]);
                        tk.setLine_col(info[2]);
                        return tk;
                    }

                    tk.setTOKEN_TYPE("TK_STR_LIT");
                    tk.setTokenName(stringBulder.toString());
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    return tk;
                }
                else {
                        while(Character.isAlphabetic(text.charAt(info[0])) || Character.isDigit(text.charAt(info[0]))){
                            if(Character.isAlphabetic(text.charAt(info[0]))){
                                stringBulder.append((text.substring(info[0],info[0]+1)).toUpperCase());
                                info[2]++;
                            }
                            else{
                                stringBulder.append(text.charAt(info[0]));
                                info[2]++;
                            }
                            info[0]++;
                        }

                        if(text.charAt(info[0]) == '.') {
                            if (stringBulder.toString().equals("END")) {
                                stringBulder.append('.');
                                tk.setTokenName(stringBulder.toString());
                                tk.setLine_num(info[1]);
                                tk.setLine_col(info[2]);
                                info[0]++;
                            }
                        }
                        else {
                            while(text.charAt(info[0]) == '\n') {
                                info[0]++;
                            }
                                tk.setTokenName(stringBulder.toString());
                                tk.setLine_num(info[1]);
                                tk.setLine_col(info[2]);

                        }

                        for(int key_index = 0; key_index < KEYWORDS.length; key_index++) {
                            if (stringBulder.toString().equals(KEYWORDS[key_index])) {
                                switch (stringBulder.toString()) {

                                    case "PROGRAM": {
                                        tk.setTOKEN_TYPE("TK_PROGRAM");
                                        return tk;
                                    }
                                    case "ARRAY": {
                                        tk.setTOKEN_TYPE("TK_ARRAY");
                                        return tk;
                                    }
                                    case "END": {
                                        tk.setTOKEN_TYPE("TK_ENDBLOCK");
                                        return tk;
                                    }
                                    case "WRITELN": {
                                        tk.setTOKEN_TYPE("TK_WRITELN");
                                        return tk;
                                    }
                                    case "CONST": {
                                        tk.setTOKEN_TYPE("TK_CONST");
                                        return tk;
                                    }
                                    case "VAR": {
                                        tk.setTOKEN_TYPE("TK_VAR");
                                        return tk;
                                    }
                                    case "BEGIN": {
                                        tk.setTOKEN_TYPE("TK_BEGIN");
                                        return tk;
                                    }
                                    case "END.": {
                                        tk.setTOKEN_TYPE("TK_END");
                                        return tk;
                                    }
                                    case "PROCEDURE": {
                                        tk.setTOKEN_TYPE("TK_PROCEDURE");
                                        return tk;
                                    }
                                    case "IF": {
                                        tk.setTOKEN_TYPE("TK_IF");
                                        return tk;
                                    }
                                    case "OF": {
                                        tk.setTOKEN_TYPE("TK_OF");
                                        return tk;
                                    }
                                    case "THEN": {
                                        tk.setTOKEN_TYPE("TK_THEN");
                                        return tk;
                                    }
                                    case "ELSE": {
                                        tk.setTOKEN_TYPE("TK_ELSE");
                                        return tk;
                                    }
                                    case "FOR": {
                                        tk.setTOKEN_TYPE("TK_FOR");
                                        return tk;
                                    }
                                    case "WHILE": {
                                        tk.setTOKEN_TYPE("TK_WHILE");
                                        return tk;
                                    }
                                    case "DO": {
                                        tk.setTOKEN_TYPE("TK_DO");
                                        return tk;
                                    }
                                    case "REAL": {
                                        tk.setTOKEN_TYPE("TK_REAL");
                                        return tk;
                                    }
                                    case "INTEGER": {
                                        tk.setTOKEN_TYPE("TK_INT");
                                        return tk;
                                    }
                                    case "AND": {
                                        tk.setTOKEN_TYPE("TK_AND");
                                        return tk;
                                    }
                                    case "OR": {
                                        tk.setTOKEN_TYPE("TK_OR");
                                        return tk;
                                    }
                                    case "TO": {
                                        tk.setTOKEN_TYPE("TK_TO");
                                        return tk;
                                    }
                                    case "NOT": {
                                        tk.setTOKEN_TYPE("TK_NOT");
                                        return tk;
                                    }
                                    case "REPEAT": {
                                        tk.setTOKEN_TYPE("TK_REPEAT");
                                        return tk;
                                    }
                                    case "BREAK": {
                                        tk.setTOKEN_TYPE("TK_BREAK");
                                        return tk;
                                    }
                                    case "FUNCTION": {
                                        tk.setTOKEN_TYPE("TK_FUNCTION");
                                        return tk;
                                    }
                                    case "TYPE": {
                                        tk.setTOKEN_TYPE("TK_TYPE");
                                        return tk;
                                    }
                                    case "UNTIL": {
                                        tk.setTOKEN_TYPE("TK_UNTIL");
                                        return tk;
                                    }
                                    case "LABEL": {
                                        tk.setTOKEN_TYPE("TK_LABEL");
                                        return tk;
                                    }
                                }
                            }
                        }
                        tk.setTOKEN_TYPE("TK_ID");
                        return tk;

                }

        }







        else if (Character.isDigit(text.charAt(info[0]))) {
            StringBuffer numBuilder = new StringBuffer();
            while (Character.isDigit(text.charAt(info[0])) && text.charAt(info[0]) != '~') {
                numBuilder.append(text.charAt(info[0]));
                info[0]++;
                info[2]++;
            }
            if (text.charAt(info[0]) == '.') {
                numBuilder.append(text.charAt(info[0]));
                info[0]++;
                info[2]++;
                if (Character.isDigit(text.charAt(info[0])) || text.charAt(info[0]) == '.') {
                    while (Character.isDigit(text.charAt(info[0])) && text.charAt(info[0]) !='~'){
                        numBuilder.append(text.charAt(info[0]));
                        info[0]++;
                        info[2]++;
                    }
                    if (text.charAt(info[0]) == '.') {
                        numBuilder.append((text.charAt(info[0])));
                        tk.setTokenName(numBuilder.toString());
                        tk.setTOKEN_TYPE("TK_ERROR");
                        tk.setLine_num(info[1]);
                        tk.setLine_col(info[2]);
                        info[0]++;
                        return tk;
                    }
                    tk.setTokenName(numBuilder.toString());
                    tk.setTOKEN_TYPE("TK_REAL_LIT");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    return tk;
                }
                else {
                    tk.setTokenName(numBuilder.toString());
                    tk.setTOKEN_TYPE("TK_ERROR");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    return tk;
                }
            }
            else {
                tk.setTokenName(numBuilder.toString());
                tk.setLine_num(info[1]);
                tk.setLine_col(info[2]);
                tk.setTOKEN_TYPE("TK_INTEGER_LIT");
                return tk;
            }
        }




        else {
            StringBuffer operation = new StringBuffer();
            switch (text.charAt(info[0])) {
                case ';': {
                    info[2]++;
                    tk.setTokenName(";");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_SEMICOLON");
                    info[0]++;
                    return tk;
                }
                case ':': {
                    operation.append(text.charAt(info[0]));
                    info[0]++;
                    if (text.charAt(info[0]) == '=') {
                        operation.append(text.charAt(info[0]));
                        info[2]++;
                        tk.setTokenName(operation.toString());
                        tk.setLine_num(info[1]);
                        tk.setLine_col(info[2]);
                        tk.setTOKEN_TYPE("TK_ASSIGNMENT");
                        info[0]++;
                        return tk;
                    } else {
                        info[2]++;
                        tk.setTokenName(":");
                        tk.setLine_num(info[1]);
                        tk.setLine_col(info[2]);
                        tk.setTOKEN_TYPE("TK_COLON");
                        return tk;
                    }
                }
                case '+': {
                    info[2]++;
                    tk.setTokenName("+");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_PLUS");
                    info[0]++;
                    return tk;
                }
                case '-': {
                    info[2]++;
                    tk.setTokenName("-");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_MINUS");
                    info[0]++;
                    return tk;
                }
                case '/': {
                    info[2]++;
                    tk.setTokenName("/");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_DIV");
                    info[0]++;
                    return tk;
                }
                case '*': {
                    info[2]++;
                    tk.setTokenName("*");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_MULTI");
                    info[0]++;
                    return tk;
                }
                case '%': {
                    info[2]++;
                    tk.setTokenName("%");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_MOD");
                    info[0]++;
                    return tk;
                }
                case '(': {
                    info[2]++;
                    tk.setTokenName("(");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_OPEN_PAREN");
                    info[0]++;
                    return tk;
                }
                case ')': {
                    info[2]++;
                    tk.setTokenName(")");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_CLOSE_PAREN");
                    info[0]++;
                    return tk;
                }
                case '<': {
                    info[2]++;
                    tk.setTokenName("<");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_LESS");
                    info[0]++;
                    return tk;
                }
                case '>': {
                    info[2]++;
                    tk.setTokenName(">");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_GREATER");
                    info[0]++;
                    return tk;
                }
                case '\'': {
                    info[2]++;
                    tk.setTokenName("'");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_SINGLE_QUOTE");
                    info[0]++;
                    return tk;
                }
                case '"': {
                    info[2]++;
                    tk.setTokenName("\"");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_DOUBLE_QUOTE");
                    info[0]++;
                    return tk;
                }
                case '~': {
                    info[2]++;
                    tk.setTokenName("~");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_EOF");
                    info[0]++;
                    return tk;
                }
                case ',': {
                    info[2]++;
                    tk.setTokenName(",");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_COMMA");
                    info[0]++;
                    return tk;
                }
                case '=': {
                    info[2]++;
                    tk.setTokenName("=");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_EQUALS");
                    info[0]++;
                    return tk;
                }
                case '[': {
                    info[2]++;
                    tk.setTokenName("[");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_OPEN_BRACKET");
                    info[0]++;
                    return tk;
                }
                case ']': {
                    info[2]++;
                    tk.setTokenName("]");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_CLOSE_BRACKET");
                    info[0]++;
                    return tk;
                }
                case '.': {
                    info[2]++;
                    tk.setTokenName(".");
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_PERIOD");
                    info[0]++;
                    return tk;
                }
                default: {
                    info[2]++;
                    operation.append(text.charAt(info[0]));
                    tk.setTokenName(operation.toString());
                    tk.setLine_num(info[1]);
                    tk.setLine_col(info[2]);
                    tk.setTOKEN_TYPE("TK_ERROR");
                    info[0]++;
                    return tk;
                }
            }
        }
    }

//TODO
    public void printInfo(int[] info)
    {
        for (int i = 0; i< info.length; i++)
        {
            System.out.print(info[i] + "\t");
        }
        System.out.println();
    }

}
