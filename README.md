# CS3110Project1

This project accepts inputs of the format of java floating point expressions, per our in class discussion and the java documentation. 
It then evaluates the expression and outputs the result of the evaluation to the commandline, or prints a message indicating invalid inputs if the input is invalid.
It does not accept signed doubles.
It also does not differentiate between d or D and f or F in deciding what type to output. It will always output a double.

This project utilizes a PDA using a custom dataStructure and swithch/if statements, which can be found in the linkedPDA.java file. This file also makes use of previous work with DFAs, viewable in the linkedMap.java file.

USAGE:
to use this project, run the main method in the driver file, and then proceed to providing input via the commandline per the prompts.
after entering an expression, it will evaluate and output. It will then open up for a new input.
In order to quit running this file, you can use the q or Q inputs.
