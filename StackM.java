import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;
import java.util.Vector;

/**
 * 
 * @author Christine Mossiah (Almodovar) Spring '16
 *
 */
public class StackM {
    private static Stack instructStack = new Stack();
    private static final int frontItem = 0;

    public void evalStack(Vector<symbolTable> symbolList, Vector<instructionTable> instList) {

        int size_instrL = instList.size();
        int size_symblL = symbolList.size();

        ListIterator itr = instList.listIterator();
        instructionTable currentInstr;
        while (itr.hasNext()) {
            currentInstr = (instructionTable)(itr.next());

            if(currentInstr.getinstr().equals("op_push")) {

                if(currentInstr.getType().equals("TK_ID")) {

                    StringBuffer lleft = new StringBuffer();

                    if (currentInstr.getValue().contains("#")) {
                        int indexofsplit1 = currentInstr.getValue().indexOf('#');

                        lleft.append(currentInstr.getValue().substring(0,currentInstr.getValue().indexOf('#')));
                        String right1 = currentInstr.getValue().substring(currentInstr.getValue().indexOf('#') + 1, currentInstr.getValue().length());

                        Iterator iittr = symbolList.iterator();
                        symbolTable Eval = new symbolTable();
                        while ( iittr.hasNext()) {
                            Eval = (symbolTable) iittr.next();
                            if (Eval.getName().equals(right1)) {
                                lleft.append(Eval.getValue());
                            }
                        }
                    }
                    else {
                        lleft.append(currentInstr.getValue());
                    }

                    Vector<symbolTable> symList_copy = (Vector) symbolList.clone();
                    symbolList.clear();

                    for ( int i = 0; i < size_symblL; i++ ) {
                        symbolTable a = symList_copy.firstElement();
                        symList_copy.remove(frontItem);

                        if(a.getName().equals(lleft.toString())) {
                            instructStack.push(a.getValue());
                            symbolList.add(a);
                        }
                        else {
                            symbolList.add(a);
                        }
                    }
                }
                else {
                    instructStack.push(currentInstr.getValue());
                }
            }





            else if (currentInstr.getinstr().equals("op_pop")) {

                String poped = (String) instructStack.pop();;
                StringBuffer left = new StringBuffer();

                if (currentInstr.getValue().contains("#")) {
                    int indexofsplit = currentInstr.getValue().indexOf('#');

                    left.append(currentInstr.getValue().substring(0,currentInstr.getValue().indexOf('#')));
                    String right = currentInstr.getValue().substring(currentInstr.getValue().indexOf('#') + 1, currentInstr.getValue().length());

                    Iterator iittr = symbolList.iterator();
                    symbolTable Eval = new symbolTable();
                    while ( iittr.hasNext()) {
                        Eval = (symbolTable) iittr.next();
                        if (Eval.getName().equals(right)) {
                            left.append(Eval.getValue());
                        }
                    }
                }
                else {
                    left.append(currentInstr.getValue());
                }

                Vector<symbolTable> symList_copy = (Vector) symbolList.clone();
                symbolList.clear();

                for ( int j =0; j < size_symblL; j++) {
                    symbolTable a = symList_copy.firstElement();
                    symList_copy.remove(frontItem);

                    if (a.getName().equals(left.toString())) {
                        a.setValue(poped);
                        symbolList.add(a);
                    }
                    else {
                        symbolList.add(a);
                    }
                }
            }
            else if (currentInstr.getinstr().equals("op_add")) {
                int op1 = Integer.parseInt((String)instructStack.pop());
                int op2 = Integer.parseInt((String)instructStack.pop());
                int sum = op1 + op2;
                instructStack.push(Integer.toString(sum));
            }
            else if (currentInstr.getinstr().equals("op_sub")) {
                int op1 = Integer.parseInt((String)instructStack.pop());
                int op2 = Integer.parseInt((String)instructStack.pop());
                int difference = op2 - op1;
                instructStack.push(Integer.toString(difference));
            }
            else if (currentInstr.getinstr().equals("op_mul")) {
                int op1 = Integer.parseInt((String)instructStack.pop());
                int op2 = Integer.parseInt((String)instructStack.pop());
                int product = op1 * op2;
                instructStack.push(Integer.toString(product));
            }
            else if (currentInstr.getinstr().equals("op_greater")) {
                int op1 = Integer.parseInt((String)instructStack.pop());
                int op2 = Integer.parseInt((String)instructStack.pop());
                int greater_result;
                if (op1  > op2 || op1 == op2) { greater_result = 0; } else { greater_result = 1; }
                instructStack.push(Integer.toString(greater_result));
            }
            else if (currentInstr.getinstr().equals("op_less")) {
                int op1 = Integer.parseInt((String)instructStack.pop());
                int op2 = Integer.parseInt((String)instructStack.pop());
                int less_result;
                if (op1 < op2 || op1 == op2) { less_result = 0; } else { less_result = 1; }
                instructStack.push(Integer.toString(less_result));
            }
            else if ( currentInstr.getinstr().equals("op_jfalse")) {
                int flag = Integer.parseInt((String)instructStack.pop());

                if ( flag == 0) {
                    int jumpTo = (Integer.parseInt(currentInstr.getValue()));
                    int at = currentInstr.getInstPtr();
                    int diff = at - jumpTo + 1;
                    if (diff > 0) {
                        while (diff > 0) {
                            diff--;
                            currentInstr = (instructionTable) (itr.previous());
                        }
                    }
                    else if (diff < 0) {
                        while (diff <=0) {
                            diff++;
                            currentInstr = (instructionTable) (itr.next());
                        }
                    }
                }
            }
            else if (currentInstr.getinstr().equals("op_jmp")) {
                int jumpTo = (Integer.parseInt(currentInstr.getValue()));
                int at = currentInstr.getInstPtr();
                int diff = at - jumpTo + 1;

                if (diff > 0) {
                    while (diff > 0) {
                        diff--;
                        currentInstr = (instructionTable) (itr.previous());
                    }
                }
                else if (diff < 0) {
                    while (diff < 0) {
                        diff++;
                        currentInstr = (instructionTable) (itr.next());
                    }
                }
            }
            else if (currentInstr.getinstr().equals("op_writeln") ) {
                System.out.println("-> " + instructStack.pop());
            }
        }

    }
}
