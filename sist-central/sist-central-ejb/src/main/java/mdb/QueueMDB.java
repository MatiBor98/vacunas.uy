package mdb;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.eclipse.microprofile.config.Config;

import android.util.Base64;
import logica.servicios.local.ReservaServiceLocal;


@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/vacunatorio"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		})
public class QueueMDB implements MessageListener {
	
	@EJB
    private ReservaServiceLocal reservaService;
	@Inject
	Config config;

    public QueueMDB() {
    }
	
    public void onMessage(Message message) {
        if (message instanceof BytesMessage) {
        	try {
        		BytesMessage msg = (BytesMessage) message;
        		int TEXT_LENGTH = (int) msg.getBodyLength();
        		byte[] textBytes = new byte[TEXT_LENGTH];
        		msg.readBytes(textBytes, TEXT_LENGTH);
				
				//desencriptar
				String texto = desencriptar(textBytes);				
				
				List<String> reservasFinalizadas = Pattern.compile("\\&").splitAsStream(texto).collect(Collectors.toList());
				if(!reservasFinalizadas.get(0).equals("Confirmadas:")) {
					String reservasConf = reservasFinalizadas.get(0);
					reservasConf = reservasConf.replace("Confirmadas:", "");
					List<String> reservasConfirmadas = Pattern.compile("\\|").splitAsStream(reservasConf).collect(Collectors.toList());
					for(String reserva:reservasConfirmadas) {
						List<String> reservaYLote = Pattern.compile("\\,").splitAsStream(reserva).collect(Collectors.toList());
						reservaService.confirmarVacuna(Integer.parseInt(reservaYLote.get(0)), reservaYLote.get(1));
					}
				}
				if(!reservasFinalizadas.get(1).equals("Caducadas:")) {
					String reservasCanc = reservasFinalizadas.get(1);
					reservasCanc = reservasCanc.replace("Caducadas:", "");
					List<String> reservasCanceladas = Pattern.compile("\\|").splitAsStream(reservasCanc).collect(Collectors.toList());
					for(String reserva:reservasCanceladas) {
						reservaService.cancelarVacuna(Integer.parseInt(reserva));
					}
				}
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | JMSException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
        }
    }
    
    public String desencriptar(byte[] bytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    	String key = config.getValue("privKey", String.class);
    	byte[] keyByte = Base64.decode(key, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);      
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decipheredText = cipher.doFinal(bytes);
		String texto = new String(decipheredText);
		return texto;
    }

}
