
public class linkedPDA {
	private String inputString;
	private PDANode headNode;
	
	private class linkedStack {
		//node vars
		private stackNode topNode;
		private int stackCount;
		
		private linkedStack() {
			stackCount = 0;
			topNode = null;
		}
		
		private linkedStack (char firstInput) {
			topNode = new stackNode(firstInput, null);
			stackCount = 1;
		}
		
		private linkedStack (double firstInput) {
			topNode = new stackNode(firstInput, null);
			stackCount = 1;
		}
		
		//check count
		private int getCount() {
			return stackCount;
		}
		
		//push
		private void push (double newDouble) {
			stackNode newNode = new stackNode(newDouble, topNode);
			topNode = newNode;
			stackCount++;
		}
		
		private void push (char newChar) {
			stackNode newNode = new stackNode(newChar, topNode);
			topNode = newNode;
			stackCount++;
		}
		
		//double pop
		private double dubPop () {
			double result = topNode.getDouble();
			topNode = topNode.getNextNode();
			stackCount--;
			
			return result;
		}
		
		//char pop
		private char charPop () {
			char result = topNode.getChar();
			topNode = topNode.getNextNode();
			stackCount--;
			
			return result;
		}
		
		//double peak
		private double dubPeak() {
			return topNode.getDouble();
		}
		
		//char peak
		private char charPeak () {
			return topNode.getChar();
		}
		
		
		//typePeak
		private char typePeak() {
			return topNode.getType();
		}
		
	}
	
	
	
	//Stack's node implementation
	private class stackNode {
		//node data
		private stackNode nextNode;
		
		//contained data
		private char type;
		private double doubleData;
		private char charData;
		
		private stackNode (double input) {
			type = 'D';
			doubleData = input;
		}
		
		private stackNode (double input, stackNode next) {
			//data setting
			type = 'D';
			doubleData = input;
			
			//node control
			nextNode = next;
		}
		
		private stackNode (char input) {
			type = 'C';
			charData = input;
		}
		
		private stackNode (char input, stackNode next) {
			//data setting
			type = 'C';
			charData = input;
			
			//node control
			nextNode = next;
		}
		
		private char getType () {
			return type;
		}
		
		private double getDouble () {
			return doubleData;
		}
		
		private char getChar () {
			return charData;
		}
		
		private stackNode getNextNode () {
			return nextNode;
		}
		
		private void setNextNode(stackNode next) {
			nextNode = next;
		}
	}
	
	private class PDANode {
		//variable
		private int ID;
		private boolean acceptance;
		private char[] mapKey;
		private PDANode[] mapDict;
		private int mapSize;
		
		private PDANode (int newNumber) {
			ID = newNumber;
			acceptance = false;
			mapKey = new char[10];
			mapDict = new PDANode[10];
			mapSize = 0;
			
		}
		
		private PDANode (int newNumber, boolean accept) {
			ID = newNumber;
			acceptance = false;
			mapKey = new char[2];
			mapDict = new PDANode[2];
			mapSize = 0;
			acceptance = accept;
		}
		
		private int getID() {
			return ID;
		}
		
		private void setAcceptance (boolean newAccept) {
			acceptance = newAccept;
		}
		
		private boolean getAcceptance() {
			return acceptance;
		}
		
		private void setMap(char newMapInput, PDANode newNode) {
			boolean exists = false;
			
			//iterates until found, or reaches end of array
			for (int i = 0; i < mapSize; i++) {
				if (mapKey[i] == newMapInput) {
					exists = true;
					break;
				}
			}

			if ((mapSize < mapKey.length) && !exists) {
				mapKey[mapSize] = newMapInput;
				mapDict[mapSize] = newNode;
				mapSize++;
			} else if ((mapSize >= mapKey.length) && !exists) {
				//resize arrays
				char[] tempKey = new char[mapSize*2];
				PDANode[] tempDict = new PDANode[mapSize*2];
				
				//repopulate array
				for (int i = 0; i < mapSize; i++) {
					tempKey[i] = mapKey[i];
					tempDict[i] = mapDict[i];
				}
				
				//assign new arrays to keys and dict
				mapKey = tempKey;
				mapDict = tempDict;
				
				//set new map values
				mapKey[mapSize] = newMapInput;
				mapDict[mapSize] = newNode;
				mapSize++;
			} else {
				System.out.println("Input Is already mapped");
			}
		}
		
		private PDANode checkMap(char input) {
			int index = -1;
			
			//iterates until found, or reaches end of array
			boolean found = false;
			int i = 0;
			while (i < mapKey.length && !found) {
				if (mapKey[i] == input) {
					index = i;
				}
				i++;
			}
			
			//return node needed
			if (index >= 0) {
				return mapDict[index];
			} else {
				return null;
			}
		}
	}
	
	public linkedPDA (String input) {
		
		//create PDA Nodes
		PDANode node0 = new PDANode(0);
		PDANode node1 = new PDANode(1, true);
		
		//set headNode for other methods to access it
		headNode = node0;
		
		//NOTE: Make sure digits map to the correct thing along with other stuff.
		
	//create mappings for character and floats
		node0.setMap('.', node1);
		node0.setMap('d', node1);
		node0.setMap('w', node0);
		node0.setMap('(', node0);
		
		node1.setMap('w', node1);
		node1.setMap('m', node0);
		node1.setMap('a', node0);
		node1.setMap('(', node0);
		node1.setMap(')', node1);
		
	//testing code

		
		
		inputString = input;
		headNode = node0;
	}
	
	public double analyzeString() {
		boolean done = false;
		double result = 0;
		String procString = inputString;
		PDANode travNode = headNode;
		int index = 0;
		
		//initiate output and precedent stacks
		linkedStack output = new linkedStack('$');
		linkedStack prec = new linkedStack('$');
		
		char[] input = inputString.toCharArray();
		
		//read in input
		while (!done && index < input.length) {
			
			nodeReturn checker;
			try {
				checker = checkChar(input[index], travNode);
			} catch (java.lang.NullPointerException e)  {
				return Double.POSITIVE_INFINITY;
			}
			
			
			if (travNode == null) {
				return Double.POSITIVE_INFINITY;
			}
			
			//logic for restarting input
			
			
			//exit logic
			if (input.length <= 0) {
				done = true;
			}
			
			switch(travNode.getID()) {
			case 0:
				if (checker.getChar() == 'd' || checker.getChar() == '.') {
					//process the double or float
					
					String floatString = "";
					for (int i = index; i < input.length; i++) {
						floatString = floatString + Character.toString(input[i]);
					}
					
					linkedMap process = new linkedMap(floatString);
					double pushable = process.analyzeString();
					if (pushable == Double.POSITIVE_INFINITY) {
						done = true;
						return pushable;
					}
					
					//push result to stack
					output.push(pushable);
					
					//change input string/chars
					inputString = process.getRemainder();
					input = inputString.toCharArray();

					index = -1;
				} else if (checker.getChar() == '(') {
					prec.push(input[index]);
				}
				break;
			case 1:
				if (checker.getChar() == ')') {
					char check;
					boolean found = false;
					while (!found) {
						check = prec.charPeak();
						if (check == '*' || check == '/' || check == '+' || check == '-') {
							output.push(prec.charPop());
						} else if (check == '(' || check == '$') {
							if (check == '$') {
								return Double.POSITIVE_INFINITY;
							}
							found = true;
							prec.charPop();
						}
					}
				} else if (checker.getChar() == 'm') {
					char check = prec.charPeak();
					if (check == '$' || check == '(' || check == '+' || check == '-') {
						prec.push(input[index]);//?

					} else if (check == '*' || check == '/') {
						boolean met = false;
						char tempCheck = ' ';

						while (!met) {
							tempCheck = prec.charPeak();
							if (tempCheck == '*' || tempCheck == '/') {
								output.push(tempCheck);
								prec.charPop();
							} else {
								prec.push(input[index]);
								met = true;
							}
						}
					}
				} else if (checker.getChar() == 'a') {
					char check = prec.charPeak();
					
					if (check == '$' || check == '(') {
						prec.push(input[index]);//?

					} else if (check == '*' || check == '/' || check == '+' || check == '-') {
						boolean met = false;
						char tempCheck = ' ';

						while (!met) {
							tempCheck = prec.charPeak();
							if (tempCheck == '*' || tempCheck == '/' || tempCheck == '+' || tempCheck == '-') {
								output.push(tempCheck);
								prec.charPop();
							} else {
								prec.push(input[index]);
								met = true;
							}
						}
					}
				} else if (checker.getChar() == '(') {
					return Double.POSITIVE_INFINITY;
				}
				break;
			default:
				return Double.POSITIVE_INFINITY;
			}
			index++;
			travNode = checker.getNode();
		}
		
		result = 42;
		
		while (prec.charPeak() != '$') {
			if (prec.charPeak() != '(') {
				output.push(prec.charPop());
			} else {
				return Double.POSITIVE_INFINITY;
			}
		}
		
		linkedStack eval = new linkedStack('$');
		
		//reverse stack
		boolean end = false;
		while (!end) {
			if (output.typePeak() == 'C') {
				if (output.charPeak() != '$') {
					eval.push(output.charPop());
				} else {
					end =  true;
				}
			} else if (output.typePeak() == 'D') {
				eval.push(output.dubPop());
			}
		}
		
		//evaluate new stack
		end = false;
		
		linkedStack storage = new linkedStack('$');
		
		while (!end) {
			if (eval.typePeak() == 'C') {
				if (eval.charPeak() != '$' || eval.getCount() > 2) {
					char op = eval.charPop();
					double numb2 = storage.dubPop();
					double numb1 = storage.dubPop();
					
					switch(op) {
					case '+':
						double calc = numb1+numb2;
						storage.push(calc);
						break;
					case '-':
						double calc1 = numb1-numb2;
						storage.push(calc1);
						break;
					case '/':
						double calc2 = numb1/numb2;
						storage.push(calc2);
						break;
					case '*':
						double calc3 = numb1*numb2;
						storage.push(calc3);
						break;
					default: 
						System.out.println("defaulted");
						end = true;
					}
				} else {
					end =  true;
				}
			} else if (eval.typePeak() == 'D') {
				storage.push(eval.dubPop());
			}
		}
		result = storage.dubPop();
		//begin popping and evaluating
		
		//set return value
		
		return result;
	}
	
	private class nodeReturn {
		private PDANode node = null;
		private char storedChar;
		
		private nodeReturn(PDANode returnNode, char returnChar) {
			node = returnNode;
			storedChar = returnChar;
		}
		
		private char getChar() {
			return storedChar;
		}
		
		private PDANode getNode() {
			return node;
		}
	}
	
	private nodeReturn checkChar(char input, PDANode currentNode) {
		nodeReturn result = null;
		
		switch(input) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			result = new nodeReturn(currentNode.checkMap('d'), 'd');
			break;
		case '+':
		case '-':
			result = new nodeReturn(currentNode.checkMap('a'), 'a');
			break;
		case '.':
			result = new nodeReturn(currentNode.checkMap('.'), '.');
			break;
		case '*':
		case '/':
			result = new nodeReturn(currentNode.checkMap('m'), 'm');
			break;
		case ' ':
		case '\t':
		case '\n':
		case '\u000B':
		case '\f':
		case '\r':
		case '\u001C':
		case '\u001D':
		case '\u001E':
		case '\u001F':
			result = new nodeReturn(currentNode.checkMap('w'), 'w');
			break;
		case '(':
			result = new nodeReturn(currentNode.checkMap('('), '(');
			break;
		case ')':
			result = new nodeReturn(currentNode.checkMap(')'), ')');
			break;
		default:
			result = new nodeReturn(null, 'x');
			break;
		}
		
		return result;
	}
}