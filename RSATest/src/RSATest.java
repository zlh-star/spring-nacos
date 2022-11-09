import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSATest {

    public static void main(String[] args) throws  Exception{
        //获取密钥生成器对象
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(Integer.parseInt("2048"));

        //生成密钥对对象
        KeyPair keyPair=keyPairGenerator.generateKeyPair();

        RSAPrivateKey rsaPrivateKey= (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey rsaPublicKey= (RSAPublicKey) keyPair.getPublic();

        //用于执行加密解密
        RSATest encrypt=new RSATest();

        //需要加密的密文
        String encryptText="123456";

        byte[] bytes=encryptText.getBytes(StandardCharsets.UTF_8);


        //使用公钥进行加密
        final Base64.Encoder encoder = Base64.getEncoder();
        //Base64加密
        byte[] enpasswordByte = encoder.encode(bytes);
        //公钥加密
        byte e[]=encrypt.encrypt(rsaPublicKey,enpasswordByte);
        System.out.println("加密后的内容为:"+new String(e));

        //使用私钥进行解密
        final Base64.Decoder decoder=Base64.getDecoder();
        //解密
        byte de[]=encrypt.decrypt(rsaPrivateKey,e);
        //Base64解码
        byte[] depasswordByte=decoder.decode(de);
        System.out.println("解密后的内容为:"+new String(depasswordByte));

    }

    protected byte[] encrypt(RSAPublicKey rsaPublicKey,byte[] obj) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,rsaPublicKey);
        return cipher.doFinal(obj);
    }

    protected byte[] decrypt(RSAPrivateKey rsaPrivateKey,byte[] obj) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,rsaPrivateKey);
        return cipher.doFinal(obj);
    }
}
