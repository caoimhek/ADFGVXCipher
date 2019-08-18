package ie.gmit.dip;

public class Runner {

    public static void main(String[] args) {

        //1. get input from user
        Menu input = new Menu(); 
        String inputText = input.MenuInput();
        
        //remove spaces in the input message
        String processedInput = inputText.replace(" ","");

        //2. encrypt message
        Cipher cipher = new Cipher();
        String encryptedText = cipher.getEncryptedCharactersFromString (processedInput);

        //3. decrypt message
        String decryptedMessage = cipher.getDecryptedCharactersFromString(encryptedText);
        
        System.out.println("Input:"+inputText);
        System.out.println("Processed Input:"+ processedInput); 
        System.out.println("Encrypted:"+encryptedText);
        System.out.println("Decrypted:"+decryptedMessage);
    }
}
