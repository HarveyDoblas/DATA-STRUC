import java.util.Scanner;
import java.util.Stack;

public class ExpressionConverterCalculator {
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    
    private static int performOperation(char operator, int operand1, int operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                return 0;
        }
    }
    
    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        
        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        
        return postfix.toString();
    }
    
    private static String infixToPrefix(String infix) {
        StringBuilder prefix = new StringBuilder();
        StringBuilder reversedInfix = new StringBuilder(infix).reverse();
        
        for (int i = 0; i < reversedInfix.length(); i++) {
            if (reversedInfix.charAt(i) == '(') {
                reversedInfix.setCharAt(i, ')');
                i++;
            } else if (reversedInfix.charAt(i) == ')') {
                reversedInfix.setCharAt(i, '(');
                i++;
            }
        }
        
        String reversedPostfix = infixToPostfix(reversedInfix.toString());
        prefix.append(new StringBuilder(reversedPostfix).reverse());
        
        return prefix.toString();
    }
    
    private static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(Character.getNumericValue(c));
            } else if (isOperator(c)) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(c, operand1, operand2);
                stack.push(result);
            }
        }
        
        return stack.pop();
    }
    
    private static int evaluatePrefix(String prefix) {
        Stack<Integer> stack = new Stack<>();
        
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char c = prefix.charAt(i);
            
            if (Character.isDigit(c)) {
                stack.push(Character.getNumericValue(c));
            } else if (isOperator(c)) {
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                int result = performOperation(c, operand1, operand2);
                stack.push(result);
            }
        }
        
        return stack.pop();
    }
    
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the infix expression: ");
        String infixExpression = scanner.nextLine();
        
        String postfixExpression = infixToPostfix(infixExpression);
        System.out.println("Postfix expression: " + postfixExpression);
        int postfixTotal = evaluatePostfix(postfixExpression);
        System.out.println("Postfix total: " + postfixTotal);
        
        String prefixExpression = infixToPrefix(infixExpression);
        System.out.println("Prefix expression: " + prefixExpression);
        int prefixTotal = evaluatePrefix(prefixExpression);
        System.out.println("Prefix total: " + prefixTotal);
        
        scanner.close();
    }
}