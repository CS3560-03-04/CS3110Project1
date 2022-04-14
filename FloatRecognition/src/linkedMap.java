
//CS3110 project1
public class linkedMap {
	String inputString;
	Node headNode;
	
	private class Node {
		//data of the node
		private double data;
		private int exponent;
		private boolean exponentSign;
		
		//nodeName for determining 
		private int nodeNumber;
		
		//by default, nodes will not be accepting
		private boolean acceptance = false;
		
		private Node digitNode = null;
		private Node zeroNode = null;
		private Node symbolNode = null;
		private Node expNode = null;
		private Node typeNode = null;
		private Node dotNode = null;
		private Node underscoreNode = null;
		
		/**
		 * creates a new node
		 * @param dataPortion data for the new node
		 */
		private Node (int newNumber) {
			nodeNumber = newNumber;
		}
		
		private int getNodeNumb() {
			return nodeNumber;
		}
		
		/**
		 * gets the data of the node
		 * @return String data of the node
		 */
		public double getData() {
			return data;
		}
		
		/**
		 * sets the data of the node
		 * @param newData data to be set as the node's contents
		 */
		private void setData(double newData) {
			data = newData;
		}
		
	//custom Node data
		//acceptance control
		private void setAcceptance(boolean newAcceptance) {
			acceptance = newAcceptance;
		}
		
		private boolean getAcceptance() {
			return acceptance;
		}
		
		//setters and getters for node mapping
		//1-9
		private void setDigitMap(Node nextNode) {
			digitNode = nextNode;
		}
		
		private Node getDigitMap() {
			return digitNode;
		}
		
		//0
		private void setZeroMap(Node nextNode) {
			zeroNode = nextNode;
		}
		
		private Node getZeroMap() {
			return zeroNode;
		}
		
		//+/-
		private void setSymbolMap(Node nextNode) {
			symbolNode = nextNode;
		}
		
		private Node getSymbolMap() {
			return symbolNode;
		}
		
		//.
		private void setDotMap(Node nextNode) {
			dotNode = nextNode;
		}
		
		private Node getDotMap() {
			return dotNode;
		}
		
		//_
		private void setUnderscoreMap(Node nextNode) {
			underscoreNode = nextNode;
		}
		
		private Node getUnderscoreMap() {
			return underscoreNode;
		}
		
		//e/E (exponent)
		private void setExpMap(Node nextNode) {
			expNode = nextNode;
		}
		
		private Node getExpMap() {
			return expNode;
		}
		
		//d/D/f/F (float)
		private void setTypeMap(Node nextNode) {
			typeNode = nextNode;
		}
		
		private Node getTypeMap() {
			return typeNode;
		}
	}
	
	private class nodeReturn {
		private Node node = null;
		private char storedChar;
		
		private nodeReturn(Node returnNode, char returnChar) {
			node = returnNode;
			storedChar = returnChar;
		}
		
		private char getChar() {
			return storedChar;
		}
		
		private Node getNode() {
			return node;
		}
	}
	
	public linkedMap(String input) {
		inputString = input;
	
	//create DFA conditional structure, traveling between nodes
		//creating nodes for DFA
		Node node0 = new Node(0);
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		Node node7 = new Node(7);
		Node node8 = new Node(8);
		Node node9 = new Node(9);
		Node node10 = new Node(10);
		Node node11 = new Node(11);
		
		//set headnode
		headNode = node0;
		
		//creating DFA connections
		node0.setDigitMap(node1);
		node0.setDotMap(node8);
		
		node1.setUnderscoreMap(node2);
		node1.setDigitMap(node1);
		node1.setExpMap(node4);
		node1.setDotMap(node3);
		node1.setTypeMap(node7);
		
		node2.setDigitMap(node1);
		node2.setUnderscoreMap(node2);
		
		
		node3.setAcceptance(true);
		node3.setTypeMap(node7);
		node3.setDigitMap(node5);
		node3.setExpMap(node4);
		
		node4.setDigitMap(node10);
		node4.setSymbolMap(node9);
		
		node5.setAcceptance(true);
		node5.setDigitMap(node5);
		node5.setUnderscoreMap(node6);
		node5.setTypeMap(node7);
		node5.setExpMap(node4);
		
		node6.setDigitMap(node5);
		node6.setUnderscoreMap(node6);
		
		node7.setAcceptance(true);
		
		node8.setDigitMap(node3);
		
		node9.setDigitMap(node10);
		
		node10.setAcceptance(true);
		node10.setTypeMap(node7);
		node10.setDigitMap(node10);
		node10.setUnderscoreMap(node11);
		
		node11.setUnderscoreMap(node11);
		node11.setDigitMap(node10);
	}
	
	public double analyzeString() {
		Node travNode = headNode;
		double result = 0;
		double exponent = 0;
		int decimalIndex = 0;
		
		//true is positive, false is negative. By default, the exponent is false.
		boolean sign = true;
		
		char[] inputChars = inputString.toCharArray();
		for (int i = 0; i < inputChars.length; i++) {
			nodeReturn checker = checkChar(inputChars[i], travNode);
			travNode = checker.getNode();
			
			
			if (travNode == null) {
				return Double.POSITIVE_INFINITY;
			}
			
			switch(travNode.getNodeNumb()) {
			case 0:
				if (checker.getChar() == 'd') {
					result = result*10 + getValue(inputChars[i]);
				}
			
				break;
			case 1:
				if (checker.getChar() == 'd') {
					result = result*10 + getValue(inputChars[i]);
				}
				break;
			case 2:
				if (checker.getChar() == 'd') {
					result = result*10 + getValue(inputChars[i]);
				}
				break;
			case 3:
				if (checker.getChar() == 'd') {
					result = result + getValue(inputChars[i])*Math.pow(10, ((-1*decimalIndex) - 1));
					decimalIndex++;
				}
				break;
			case 4:
				break;
			case 5:
				if (checker.getChar() == 'd') {
					result = result + getValue(inputChars[i])*Math.pow(10, ((-1*decimalIndex) - 1));
					decimalIndex++;
				}
				break;
			case 6:
				if (checker.getChar() == 'd') {
					result = result + getValue(inputChars[i])*Math.pow(10, ((-1*decimalIndex) - 1));
					decimalIndex++;
				}
				break;
			case 7:
				//our thing will be typed, but it shouldn't matter for this
				break;
			case 8:
				//sends into decimal stuff
				break;
			case 9:
				if (checker.getChar() == 's') {
					if (inputChars[i] == '+') {
						sign = true;
					} else if (inputChars[i] == '-') {
						sign = false;
					}
				}
				break;
			case 10:
				if (checker.getChar() == 'd') {
					exponent = exponent*10 + getValue(inputChars[i]);
				}
				break;
			}
		}
		
		//compute final float
		
		if (!travNode.getAcceptance()) {
			result = Double.POSITIVE_INFINITY;
		} else if (sign) {
			result = result*Math.pow(10, exponent);
		} else {
			result = result*(Math.pow(10, -1*exponent));
		}
		
		return result;
	}
	
	private double getValue(char input) {
		switch(input) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		default: 
			return 420;
		}
	}
	
	private nodeReturn checkChar(char input, Node currentNode) {
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
			result = new nodeReturn(currentNode.getDigitMap(), 'd');
			break;
		case '+':
		case '-':
			result = new nodeReturn(currentNode.getSymbolMap(), 's');
			break;
		case '.':
			result = new nodeReturn(currentNode.getDotMap(), 'p');
			break;
		case '_':
			result = new nodeReturn(currentNode.getUnderscoreMap(), '_');
			break;
		case 'e':
		case 'E':
			result = new nodeReturn(currentNode.getExpMap(), 'e');
			break;
		case 'f':
		case 'F':
		case 'd':
		case 'D':
			result = new nodeReturn(currentNode.getTypeMap(), 't');
			break;
		default:
			result = new nodeReturn(null, 'x');
		}
		
		return result;
	}
}

