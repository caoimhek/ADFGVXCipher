package ie.gmit.dip;

public class Cipher {

    //the set of encrypted symbols
    private char[] encryptionValues = {'A', 'D', 'F', 'G', 'V', 'X'};
    
    //the set of decrypted symbols
    private char[][] cypher = {
        {'p', 'h', '0', 'q', 'g', '6'}, 
        {'4', 'm', 'e', 'a', '1', 'y'},
        {'l', '2', 'n', 'o', 'f', 'd'},
        {'x', 'k', 'r', '3', 'c', 'v'},
        {'s', '5', 'z', 'w', '7', 'b'},
        {'j', '9', 'u', 't', 'i', '8'}};
            
    //the reordering of the columns for transpositioning        
    private int[] transpositionValues = {2, 0, 3, 1};

    //function to encrypt a given plaintext string
    public String getEncryptedCharactersFromString(String input) {
        
        //break the string into idividual characters
        char[] array = input.toCharArray(); 
        
        //encrypt each of the characters individually and collect them in a string
        StringBuilder output = new StringBuilder();
        for (char c : array) {
            output.append(getEncryptedCharacters(c));
        }
        
        //do the transposition operation to the string
        String transposed = transposeString(output.toString());
        
        //return the result
        return transposed;
    }

    //function to get cipher character letter     
    private String getEncryptedCharacters(char plain) {
        
        //find the corresponding encrypted character by
        //comparing against each entry of the 2d array
        for (int row = 0; row < cypher.length; row++) {
            for (int column = 0; column < cypher[row].length; column++) {
                if (cypher[row][column] == plain) {
                    
                    //build a string from the two characters
                    //and return it
                    char rowValue = encryptionValues[row]; 
                    char columnValue = encryptionValues[column];
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(rowValue);
                    stringBuilder.append(columnValue);
                    return stringBuilder.toString();
                }
            }
        }
        
        //could not found a corresponding entry
        //so we return null
        return null;
    }
    
    
    //transposes an encrypted string
    private String transposeString(String input){
        
        //we need a 2d array for the input
        
        //the number of the input array columns will be the size of the keyword
        int inputArrayColumns = transpositionValues.length;
        
        //the number of the input array rows is how many times we can fit the
        //keyword to the input
        int inputArrayRows = input.length() / transpositionValues.length;
        
        //when the input cannot be fitted to the keyword we need an extra row
        if (input.length() % transpositionValues.length > 0){
            inputArrayRows++;
        }
        
        char[][] inputArray = new char[inputArrayRows][inputArrayColumns];
        
        //we need to fill the array from the string
        int inputStringIndex = 0; //a counter for accessing the string characters in sequence
        for(int row = 0; row < inputArrayRows; row++){
            for(int column = 0; column < inputArrayColumns; column++){
                
                //we might have more entries in the array than the length
                //of the string so we must check. In such case, just add an empty char 
                if (inputStringIndex < input.length()){
                    inputArray[row][column] = input.charAt(inputStringIndex++);
                }
                else{
                    inputArray[row][column] = ' ';
                }
            }
        }
        
        //now we need to fractionate the array       
        for(int row = 0; row < inputArrayRows; row++){
            
            //select the current array row
            char [] rowArray = inputArray[row];
            
            //create a buffer for swapping the letters of the row
            //if we read and write on rowArray instead of using
            //the buffer we could end up copying the same
            //characters over the buffer
            char[] buffer = new char[inputArrayColumns];
            
            //do the fractioning swap by mapping the index and writting 
            //to the mapped location
            for(int column = 0; column < inputArrayColumns; column++){
                int mappedIndex = transpositionValues[column];
                buffer[mappedIndex] = rowArray[column]; 
            }
            
            //now that we swapped the characters
            //we must replace the row array with the new buffer
            inputArray[row] = buffer;
            
        }
        
        //we need to create the string by reading off each column
        StringBuilder outputStringBuilder = new StringBuilder();
        for(int column = 0; column < inputArrayColumns; column++){
            for(int row = 0; row < inputArrayRows; row++){
                outputStringBuilder.append(inputArray[row][column]);
            }
        }
        
        return outputStringBuilder.toString();
    }
    
    //detransposes an encrypted string
    private String detransposeString(String input){
                
        //we need a 2d array for the input
        
        //the number of the input array columns will be the size of the keyword
        int inputArrayColumns = transpositionValues.length;
        
        //the number of the input array rows is how many times we can fit the
        //keyword to the input
        int inputArrayRows = input.length() / transpositionValues.length;
        
        //when the input cannot be fitted to the keyword we need an extra row
        if (input.length() % transpositionValues.length > 0){
            inputArrayRows++;
        }
        
        char[][] inputArray = new char[inputArrayRows][inputArrayColumns];
        
        //we need to fill the array from the string
        int inputStringIndex = 0; //a counter for accessing the string
        for(int column = 0; column < inputArrayColumns; column++){
            for(int row = 0; row < inputArrayRows; row++){
                inputArray[row][column] = input.charAt(inputStringIndex++);
            }
        }
        
        //we need to defractionate the array 
        for(int row = 0; row < inputArrayRows; row++){
            
            //select the current array row
            char [] rowArray = inputArray[row];
            
            //create a buffer to for swapping the letters of the row
            char[] buffer = new char[inputArrayColumns];
            
            //do the swap by mapping the index and writting 
            //to the mapped location. The mapping is the inverse
            // of the one used in transposing the string
            for(int column = 0; column < inputArrayColumns; column++){
                int mappedIndex = transpositionValues[column];
                buffer[column] = rowArray[mappedIndex]; 
            }
            //we must replace the row array with the new buffer
            inputArray[row] = buffer;
            
        }
        
        //we need to create the string by reading off each row
        StringBuilder outputStringBuilder = new StringBuilder();
        for(int row = 0; row < inputArrayRows; row++){
            for(int column = 0; column < inputArrayColumns; column++){
                outputStringBuilder.append(inputArray[row][column]);
            }
        }
        
        return outputStringBuilder.toString();       
    }
   
   //helper function that converts an array to string 
   //for debugging
   private String arrayStr(char[] array){
       String str = new String(array);
       return str;
   }
   
   
   //helper function that converts an array to string
   //for debugging
   private String arrayStr(char[][] array){
       StringBuilder builder = new StringBuilder();
       builder.append("[");
       for (int i = 0; i < array.length; i++){
           char[] row = array[i];
           builder.append(row);
           builder.append(",");
       }
       
       builder.append("]");
       return builder.toString();
   }
    
    //function to decrypt a given string
    public String getDecryptedCharactersFromString(String input) {
        //detranspose the string
        String detransposed = detransposeString(input);
        
        //for each pair of characters in the detransposed string
        //calculate the corresponsing decrypted characted
        //and collect them on a string
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < detransposed.length(); i += 2) {
            stringBuilder.append(getDecryptedCharacters(detransposed.charAt(i), detransposed.charAt(i + 1)));
        }
        
        //return the collected characters string
        return stringBuilder.toString();
    }


    //function to get decrypted plaintext chars from the encrytion values correspoding to the cypher matrix
    public String getDecryptedCharacters(char encryptionValueA, char encryptionValueB) {
        
        //finds the encryptionvalues of the rows
        int row = lookup(encryptionValues, encryptionValueA);
        
        //finds the encryption values of the columns
        int column = lookup(encryptionValues, encryptionValueB);
        
        StringBuilder stringBuilder = new StringBuilder();
        
         //check if any of the two chars were not found
        if (row != -1 && column != -1) {
            
            //decrypted result is equal to the entry of the cypher matrix
            //when using row and column as coordinates 
            char decrypted = cypher[row][column];  
            stringBuilder.append(decrypted);
        }

        return stringBuilder.toString();
    }

    //looks up the index of an element in an array
    private int lookup(char[] array, char element) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == element)
                return i;

        return -1;
    }

}
