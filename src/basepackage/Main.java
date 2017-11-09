package basepackage;

public class Main {

    private static boolean containsDigit(String str) {

        for (int i = 0; i < 10; i++) {
            if (str.contains("" + i))
                return true;
        }

        return false;
    }

    private static String combination(int maximum, int required, String allowed) {

        int numberOfKeys = allowed.length();

        if (required == 0 || !containsDigit(allowed)) {
            return "";
        }

        // Check hacks
        String requiredString = ((Integer)required).toString();
        StringBuilder sequenceStr = new StringBuilder();

        for (int ch = 0; ch < requiredString.length(); ch++) {

            // Check if required number is positive and can be built from allowed digits
            if (allowed.contains("" + requiredString.charAt(ch))) {
                sequenceStr.append(requiredString.charAt(ch));
            }
            else {
                sequenceStr.setLength(0);
                break;
            }
        }

        if (sequenceStr.length() > 0) {
            System.out.println("yo");
            return sequenceStr.toString();
        }

        System.out.println("hey");

        // Check each possible number of presses
        for (int len = 3; len <= maximum; len++) {
            int[] sequence = new int[len];
            int laps = 1;

            for (int i = 0; i < len; i++) {
                laps *= numberOfKeys;
            }

            // Check each possible sequence
            for (int lap = 0; lap < laps; lap++) {

                // Check sequence
                if (checkSequence(allowed, sequence)) {

                    // If correct, calculate
                    double result = calculateSequence(allowed, sequence);

                    // count operand length!!!!!!!!!!!!!!!!!!!! overflow | maybe not in double

                    // If result is as required, return sequence
                    if (Double.compare((double)required, result) == 0) {
                        sequenceStr = new StringBuilder();
                        for (int num : sequence) {
                            sequenceStr.append(allowed.charAt(num));
                        }
                        return sequenceStr.toString();
                    }
                }

                // Get new sequence -> new lap
                for (int number = 0; number < len; number++) {
                    if (sequence[number] == numberOfKeys - 1) {
                        sequence[number] = 0;
                    }
                    else {
                        sequence[number]++;
                        break;
                    }
                }
            }
        }

        return "";
    }

    private static boolean checkSequence(String allowed, int[] sequence) {

        String possibleOperations = "+-*/";

        // The first symbol cannot be any operation except for "-"
        if (possibleOperations.contains("" + allowed.charAt(sequence[0]))
                && allowed.charAt(sequence[0]) != '-') {
            return false;
        }

        // The first symbol cannot be "0"
        if (allowed.charAt(sequence[0]) == '0') {
            return false;
        }

        // The last symbol cannot be an operation
        if (possibleOperations.contains("" + allowed.charAt(sequence[sequence.length - 1]))) {
            return false;
        }

        for (int i = 1; i < sequence.length; i++) {

            if (possibleOperations.contains("" + allowed.charAt(sequence[i - 1]))
                    && (

                        // There cannot be to operations in a row
                        possibleOperations.contains("" + allowed.charAt(sequence[i]))

                        // Operand cannot begin with "0"
                        || allowed.charAt(sequence[i]) == '0')
                    ) {
                return false;
            }
        }

        return true;
    }

    private static double calculateSequence(String allowed, int[] sequence) {

        // Get first operand
        // Loop:
            // Get operation
            // Get second operation
            // Calculate

        String possibleDigits = "0123456789";

        double firstOperand = 0;
        double secondOperand;
        char operation;

        int secondFinish = 0;

        while (secondFinish < sequence.length) {
            if (secondFinish == 0) {

                // Check first is operation
                if (allowed.charAt(sequence[0]) == '-') {
                    operation = '-';
                    secondFinish = 1;
                }
                else {
                    operation = '+';
                }
            }
            else {
                operation = allowed.charAt(sequence[secondFinish - 1]);
            }

            String secondOperandString = "";
            // Get second operator
            while (secondFinish < sequence.length
                    && possibleDigits.contains(""+allowed.charAt(sequence[secondFinish]))) {
                secondOperandString = secondOperandString.concat(""+allowed.charAt(sequence[secondFinish]));
                secondFinish++;
            }

            secondOperand = Double.parseDouble(secondOperandString);

            // Calculate new firstOperand
            switch (operation) {
                case '+':
                    firstOperand += secondOperand;
                    break;
                case '-':
                    firstOperand -= secondOperand;
                    break;
                case '*':
                    firstOperand *= secondOperand;
                    break;
                case '/':
                    firstOperand /= secondOperand;
                    break;
            }

            secondFinish++;
        }

        return firstOperand;
    }

    public static void main(String[] args) {


        /* Task:
         * the calculator is broken!
         * One has to get the required number
         * using only working keys
         * not exceeding the maximum pressing number
         */


        // Allowed keys of 0123456789+-*/:
        String keys = "378912/-*";

        // Maximum press number:
        int presses = 100;

        // Required number
        int required = -3434;

        // ------------------------------------------

        String result = combination(presses, required, keys);
        System.out.println(result);
        
        // out: -2*1717
    }
}
