
public class Logic {
	private String answer = new String();
	private String num1, num2, op;
	
	public Logic() {
		clear();
	}
	
	public void clear() {
		num1 = "";
		num2 = "";
		op = "";
	}
	
	public String screenUpdate(String buttonName) {
		
		switch (buttonName) {
		
		case "+/-":
			if (num1.equals(""))
				num1 = "0";
			
			if (!num2.equals("")) {
				num2 = changeSign(num2);
				answer = num1 + op + num2;
			}
			else if (!op.equals("")) {
				num2 = "-";
				answer = num1 + op + num2;
			}
			else if (!num1.equals("")) {
				num1 = changeSign(num1);
				answer = num1 + op;
			}
			break;
			
		case ".":
			if (num2.equals("-"))
				num2 = "-0";
			
			if (!num2.equals("")) {
				num2 = addDot(num2);
				answer = num1 + op + num2;
			}
			else if (!op.equals("")) { // We cannot add a dot after operator
				break;
			}
			else if (!num1.equals("")) { 
				num1 = addDot(num1);
				answer = num1;
			}
			else { // We have a lone 0 on screen
				num1 = "0";
				num1 = addDot(num1);
				answer = num1;
			}
			break;
			
		case "/":
			num1 = fixNumber(num1); // Will fix numbers like 5. back to 5 so we wont get 5./ on screen			
			if (!num2.equals("") && !num2.equals("-")) {
				calculate();
				op = "/";
				num2 = "";
				answer = num1 + op;
			} 
			else if (!op.equals("")) { // We cannot use more then one operator, like +/
				break;
			}
			else if (!num1.equals("")) {
				op = "/";
				answer = num1 + op;
			}
			else { // We have a lone 0 on screen
				num1 = "0";
				op = "/";
				answer = num1 + op;
			}
			break;
			
		case "*":
			num1 = fixNumber(num1);
			if (!num2.equals("") && !num2.equals("-")) {
				calculate();
				op = "*";
				num2 = "";
				answer = num1 + op;
			} 
			else if (!op.equals("")) { // We cannot use more then one operator, like +*
				break;
			}
			else if (!num1.equals("")) {
				op = "*";
				answer = num1 + op;
			}
			else { // We have a lone 0 on screen
				num1 = "0";
				op = "*";
				answer = num1 + op;
			}
			break;
			
		case "-":
			num1 = fixNumber(num1);
			if (!num2.equals("") && !num2.equals("-")) {
				calculate();
				op = "-";
				num2 = "";
				answer = num1 + op;
			}
			else if (!op.equals("") && num2.startsWith("-")) { // We can use one minus after operator
				break;
			}
			else if (!op.equals("") && num2.equals("")) { // We can use one minus after operator
				num2 = "-";
				answer = num1 + op + num2;
			}
			else if (!num1.equals("")) {
				op = "-";
				answer = num1 + op;
			}
			else { // We have a lone 0 on screen
				num1 = "0";
				op = "-";
				answer = num1 + op;
			}
			break;
		case "+":
			num1 = fixNumber(num1);
			if (!num2.equals("") && !num2.equals("-")) {
				calculate();
				op = "+";
				num2 = "";
				answer = num1 + op;
			}
			else if (!op.equals("")) { // We cannot use more then one operator, like *+
				break;
			}
			else if (!num1.equals("")) {
				op = "+";
				answer = num1 + op;
			}
			else { // We have a lone 0 on screen
				num1 = "0";
				op = "+";
				answer = num1 + op;
			}
			break;
			
		case "=":
			if (!num2.equals("") && !num2.equals("-")) {
				calculate();
				answer = num1;
			}
			else if (!op.equals("")) {
				num2 = num1; // My calculator on the PC just does the operator on the same number
				calculate();
				answer = num1;
			}
			else if (!num1.equals("")) { // Manually doing + 0 so calculate() will make number like 6.500000 to 6.5
				num2 = "0";
				op = "+";
				calculate();
				answer = num1;
			}
			else {
				num1 = "0";
				answer = num1;
			}
			clear(); // We pressed '=' so we done with the current calculation
			break;
			
		case "0": // Zero needs a special case then the other numbers
			if (!op.equals("")) { // We are at second number
				// We remove the state that we have multiple zeros at start like 000000 or -00000
				if (!num2.contains(".") && (num2.startsWith("0") || num2.startsWith("-0"))) 
					break;
				num2 = num2 + "0";
				answer = num1 + op + num2;
			}
			else { // We are at first number
				if (!num1.contains(".") && (num1.startsWith("0") || num1.startsWith("-0"))) 
					break;
				num1 = num1 + "0";
				answer = num1;
			}
			break;
		default: // Buttons with numbers on them (1-9)
			if (!op.equals("")) { // We are at second number
				num2 = num2 + buttonName;
				answer = num1 + op + num2;
			}
			else { // We are at first number
				if (num1.equals("0"))
					num1 = buttonName;
				else
					num1 = num1 + buttonName;
				answer = num1;
			}
		}
		return answer;
	}
	
	@SuppressWarnings("finally")
	private String fixNumber(String num) { 
		try {
			double numDouble = Double.parseDouble(num);
			if (numDouble == (int) numDouble)
				num = Integer.toString((int) numDouble);
		} finally {
			return num;
		}	
	}
	
	private String changeSign(String num) {
		if (num.startsWith("0") && !num.startsWith("0."))
    		return num;
    	else if (num.startsWith("-"))
    		num = num.substring(1);
        else 
        	num = "-" + num;
		return num;
	}
	      
	private String addDot(String num) {
    	if (!num.contains(".")) {
    		num = num + ".";
    	}
    	return num;
    }
	
	private void calculate() {
		switch (op) {
		
		case "/":
			if (Double.parseDouble(num2) == 0)
				num1 = "Cannot divide by zero";
			else
				num1 = Double.toString(Double.parseDouble(num1) / Double.parseDouble(num2));
			break;
		case "*":
			num1 = Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2));
			break;
		case "-":
			num1 = Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2));
			break;
		case "+":
			num1 = Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2));
			break;
		}	
		num1 = fixNumber(num1); // We convert to int if possible
	}
}
