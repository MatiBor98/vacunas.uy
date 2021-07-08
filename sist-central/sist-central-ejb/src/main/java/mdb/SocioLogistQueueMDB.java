package mdb;


import logica.servicios.local.LoteServiceLocal;
import org.eclipse.microprofile.config.Config;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.ActivationConfigProperty;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;


@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/socio-logistico"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class SocioLogistQueueMDB implements MessageListener {

    @EJB
    LoteServiceLocal loteService;

    @Inject
    private Config config;

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public SocioLogistQueueMDB() {
    }

    public void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Message message) {
        TextMessage msg = null;
        if (message instanceof TextMessage) {
            msg = (TextMessage) message;
            try {
                String texto = msg.getText();
                System.out.println(texto);
                String[] partesEncriptadas= texto.split("\\|");
                String mySecret = config.getValue(partesEncriptadas[0] + ".secretKey", String.class);
                String mensajeEncryptado = partesEncriptadas[1];
                String mensaje = decrypt(mensajeEncryptado,mySecret);
                String[] partes= mensaje.split("\\|");
                switch(partes[3]) {
                    case "Despachado":
                        loteService.despacharLote(Integer.parseInt(partes[0]), partes[1], LocalDate.parse(partes[2]));
                        break;
                    case "Entregado":
                        loteService.entregarLote(Integer.parseInt(partes[0]), partes[1], LocalDate.parse(partes[2]));

                        break;
                    default:
                        System.out.println("no entro");
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public String decrypt2(String strToDecrypt, String secretKeyString) {
        try {
            String SALT = "ssshhhhhhhhhhh!!!!";
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKeyString.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
