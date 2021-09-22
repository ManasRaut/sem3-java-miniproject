package java_miniproject;

//import javax.crypto.Cipher;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

class EncryptedData{
    //encrypted password will go in User data base
    //privateKey will go in private data base
    String encryptedPassword,privateKey;
}
public class Encryption {
    public static EncryptedData encrypt(String password)throws Exception{
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);
        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the public key from the key pair
        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();
        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        byte[] privateKeyBytes=privateKey.getEncoded();
        String privateKeyStr=Base64.getEncoder().encodeToString(privateKeyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] passwordBytes=password.getBytes();
        cipher.update(passwordBytes);
        byte[] cipherText = cipher.doFinal();
        EncryptedData data=new EncryptedData();
        data.encryptedPassword=Base64.getEncoder().encodeToString(cipherText);
        data.privateKey=privateKeyStr;
        return data;
    }
    public static String decrypt(EncryptedData data)throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        String encryptedKey=data.privateKey;
        byte[] privateKeyByte=Base64.getDecoder().decode(encryptedKey.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec spec=new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFact=KeyFactory.getInstance("RSA");
        PrivateKey privateKey=keyFact.generatePrivate(spec);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherText=Base64.getDecoder().decode(data.encryptedPassword.getBytes(StandardCharsets.UTF_8));
        //Decrypting the text
        byte[] decipheredText = cipher.doFinal(cipherText);
        return new String(decipheredText);
    }
}