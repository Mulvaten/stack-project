//Author: Tyler Stratton
//Purpose: This program will utilize a stack implemented using a linked list in order to convert a given expression from infix to postfix, and then to evaluate a postfix expression.
package csc.pkg310.homework.pkg7;
import java.util.Scanner;

class StackNode {
    private char operator;
    private int operand;
    private StackNode next;
    
    //constructors
    public StackNode(){
        next = null;
        operand = 0;
        operator = ' ';
    }
    
    public StackNode(int o){
        next = null;
        operand = o;
    }
    
    public StackNode(char o){
        next = null;
        operator = o;
    }
    
    //getters
    StackNode getNext(){
        return next;
    }
    
    int getOperand(){
        return operand;
    }
    
    char getOperator(){
        return operator;
    }
    
    //setters
    void setNext(StackNode s){
        next = s;
    }
    
    void setOperand(int o){
        operand = o;
    }
    
    void setOperator(char o){
        operator = o;
    }
}

class StackLinkedList {
    private StackNode head;
    
    //constructor
    public StackLinkedList(){
        head = null;
    }
    
    //getter
    StackNode getHead(){
        return head;
    }
    
    //setter
    void setHead(StackNode s){
        head = s;
    }
    
    boolean isEmpty(){
        return head == null;
    }
    
    void push(StackNode s){ //we don't need to worry about overflow with linked list
        if(head == null){ //if stack is empty
            head = s;
        }else{
            s.setNext(head);
            head = s;
        }
    }
    
    StackNode pop(){
        StackNode temp = head;
        if(head != null){
            head = head.getNext();
        }
        return temp; //returns null if stack is empty
    }
}

public class StackProject {
    
    public static String convert(String infix){
        String postfix = "";
        StackLinkedList convert = new StackLinkedList();
        
        for(int i = 0; i < infix.length(); i++){
            
            if(infix.charAt(i) > 48 && infix.charAt(i) < 58){ //is operand
                postfix = postfix + infix.charAt(i); //concatenate to postfix
                
            }else if(infix.charAt(i) == '('){ //is open parenthesis
                postfix = postfix + " ";
                StackNode s = new StackNode('(');
                convert.push(s); //push to stack
                
            }else if(infix.charAt(i) == ')'){ //is close parentheses
                postfix = postfix + " ";
                StackNode t = convert.pop();
                while(t.getOperator() != '('){
                    postfix = postfix + t.getOperator() + " ";
                    t = convert.pop(); //pop items from stack until ( reached
                }
                
            }else{ //is operator
                StackNode h = convert.getHead();
                
                while(h != null && !isHigherPrecendence(h.getOperator(), infix.charAt(i))){
                    StackNode u = convert.pop();
                    postfix = postfix + u.getOperator() + " ";
                    h = convert.getHead();
                }
                StackNode r = new StackNode(infix.charAt(i));
                convert.push(r);
            }
        }
        
        while(convert.getHead() != null){
            StackNode m = convert.pop(); //pop and concatenate any remaining items from stack
            postfix = postfix + m.getOperator();
        }
        
        return postfix;
        
    }
    
    public static int evaluate(String postfix){
        StackLinkedList evaluate = new StackLinkedList();
        
        for(int i = 0; i < postfix.length(); i++){
            if(postfix.charAt(i) > 48 && postfix.charAt(i) < 58){ //is operand
                int n = postfix.charAt(i) - 48;
                StackNode s = new StackNode(n);
                evaluate.push(s); //push operands to stack
            }else if(postfix.charAt(i) == ' '){
                break;
            }else{ //is operator
                int n3;
                StackNode o1 = evaluate.pop();
                StackNode o2 = evaluate.pop();
                int n1 = o1.getOperand();
                int n2 = o2.getOperand();
                switch(postfix.charAt(i)){
                    case '+':
                        n3 = n1+n2;
                        break;
                    case '-':
                        n3 = n2-n1;
                        break;
                    case '*':
                        n3 = n1*n2;
                        break;
                    default:
                        n3 = n2/n1;
                }
                StackNode t = new StackNode(n3);
                evaluate.push(t);
            }
        }
        StackNode a = evaluate.pop();
        return a.getOperand();
    }
    
    public static boolean isHigherPrecendence(char h, char t){ //returns true if t is higher precendence than h in the stack, assume t is an operator
        if(h == '('){
            return true;
        }else return !(h == t || t == '-' || t == '+');
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;
        String infix = "";
        String postfix = "";
        int ans;
        
        do{
            System.out.println("Select from:");
            System.out.println("1. Read an expression in infix notation.");
            System.out.println("2. Convert infix to postfix.");
            System.out.println("3. Evaluate the infix expression");
            System.out.println("0. Exit.");
            choice = in.nextInt();
            
            switch(choice){
                case 0: //exit
                    System.out.println("Goodbye");
                    break;
                case 1: //read
                    System.out.println("Please enter an infix expression: ");
                    infix = in.nextLine();
                    System.out.println("The entered infix expression is: "+infix);
                    break;
                case 2: //convert
                    if("".equals(infix)){
                        System.out.println("Please enter an infix expression first.");
                    }else{
                        postfix = convert(infix);
                        System.out.println("The corresponding postfix expression is: "+postfix);
                    }
                    break;
                case 3: //evaluate
                    if("".equals(postfix)){
                        System.out.println("Please convert an infix expression to postfix first.");
                    }else{
                        ans = evaluate(postfix);
                        System.out.println(postfix+" = "+ans);
                    }
                    break;
                default:
                    System.out.println("You have entered an invalild value. Please try again.");
            }
        }while(choice != 0);
    }
}
