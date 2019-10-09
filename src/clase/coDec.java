
package clase;


import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 * 
 * @author Victor Manuel Ramos Alvarez
 * @version 3.2
 * 23 Dic 2017
 * @version 3.3
 * 21 Ene 2018
 * @version 3.4
 * 22 Ene 2018
 * @version 5.0
 * 22 Ene 2018
 * @version 6.0
 * 31 Ene 2018
 * @version 6.1
 * 05 Feb 2018
 * @version 7.0
 * 11 Feb 2018
 * @version 7.1
 * 02 Feb 2019
 * @version 7.2
 * 17 Sep 2019
 * @version 7.3
 * 09 Oct 2019
 */

public class coDec {
    /**
     * Constructor
     */
    public static void coDec(){
        
    }
    
    /**
     * @serialField 
     */
    private static String secretKey = "-V1c7=R M@nu3l Rqm05-";
    private static final String VersionCoDec = "7.3";
    
    private static String Smtp = "mail.pydee.com.mx";
    private static String EnableTLS = "true";
    private static String Port = "587";
    private static String Usuario = "notificaciones@pydee.com.mx";
    private static String Autenticacion = "true";
    private static String Cuenta = "notificaciones@pydee.com.mx";
    private static String Password = "Correos2018";
    private static String [] Destinatario;
    private static String Asunto = "";
    private static String Cuerpo = "";
    private static String Adjuntos = "";
    private static String NombreArchivo = "";
  
    
    
    
    /**
     * Metodo para enviar correo
     * @return String
     */
    public static String mailEnviaCorreo(){
        
        String Respuesta = "";
        
        Properties props = new Properties();

        // Nombre del host de correo, es smtp.gmail.com
        //props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.host", Smtp);

        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", EnableTLS);

        // Puerto de servidor para envio de correos
        props.setProperty("mail.smtp.port", Port);

        // Nombre del usuario
        props.setProperty("mail.smtp.user", Usuario);

        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", Autenticacion);

        Session session = Session.getDefaultInstance(props);

        // Para obtener un log de salida más extenso
        session.setDebug(true);
        
        
        MimeMessage message = new MimeMessage(session);
        
        
        try {
            // Quien envia el correo
            message.setFrom(new InternetAddress(Cuenta));
            
            // A quien va dirigido
            
            for(int i=0;i<Destinatario.length;i++){
                System.out.println(Destinatario[i]);         
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario[i]));
            }
            

            message.setSubject(Asunto);
            //message.setText("Texto del mensaje"); 
            
            // Para agregar texto
            BodyPart texto = new MimeBodyPart();
            // Texto del mensaje
            texto.setText(Cuerpo); 
        
            // Para agregar archivo adjunto
            BodyPart adjunto = new MimeBodyPart();
            if(Adjuntos.length()>0){
                // Cargamos el archivo
                adjunto.setDataHandler(new DataHandler(new FileDataSource(Adjuntos)));
                // Opcional. De esta forma transmitimos al receptor el nombre original del
                // archivo
                adjunto.setFileName(NombreArchivo);               
            }
            
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            if(Adjuntos.length()>0){
                multiParte.addBodyPart(adjunto); 
            }
            
            // Se mete el texto y el archivo adjunto.
            message.setContent(multiParte);            
            
        } catch (AddressException ex) {
            return ex.getMessage();
            //Logger.getLogger(EnviaMail.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Error 1");
        } catch (MessagingException ex) {
            return ex.getMessage();
            //Logger.getLogger(EnviaMail.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Error 2");
        }
        
        Transport t;
        try {
            t = session.getTransport("smtp");            
            // Aqui usuario y password de quien envia
            t.connect(Cuenta, Password);
            t.sendMessage(message,message.getAllRecipients());
            t.close();
            return "correo enviado con exito";
        } catch (NoSuchProviderException ex) {
            return ex.getMessage();
            //Logger.getLogger(EnviaMail.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Error 3");
        } catch (MessagingException ex) {
            return ex.getMessage();
            //Logger.getLogger(EnviaMail.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Error S:" + ex);
        }
        
        
        
    }
    
    /**
     * Metodo para enviar nombre original de archivo adjunto
     * @param NombreOriginal String
     */
    public static void mailSetNombreAdjunto(String NombreOriginal){
        
        NombreArchivo = NombreOriginal;
    }
    
    /**
     * Metodo para indicar archivos adjuntos
     * @param ArchivosAdjuntos String
     */
    public static void mailSetAdjuntos(String ArchivosAdjuntos){
        
        Adjuntos = ArchivosAdjuntos;
    }
    
    /**
     * Metodo para indicar cuerpo del mensaje
     * @param CuerpoMensaje 
     */
    public static void mailSetCuerpo(String CuerpoMensaje){
        
        Cuerpo = CuerpoMensaje;
    }
    
    /**
     * Metodo para indicar el asunto del mensaje
     * @param AsuntoMensaje String
     */
    public static void mailSetAsunto(String AsuntoMensaje){
        
        Asunto = AsuntoMensaje;
    }
    
    /**
     * Metodo para indicar los correos destinatarios
     * @param Destinatarios String
     */
    public static void mailSetDestinatario(String Destinatarios){
        
        Destinatario = Destinatarios.split(",");
    }
    
    /**
     * Metodo para indicar password de la cuenta remitente
     * @param PasswordEnvio String
     */
    public static void mailSetPassword(String PasswordEnvio){
        
        Password = PasswordEnvio;
    }
    
    /**
     * Metodo para indicar la cuenta del remitente
     * @param CuentaEnvio String
     */
    public static void mailSetCuenta(String CuentaEnvio){
        
        Cuenta = CuentaEnvio;
    }
    
    /**
     * Metodo para indicar si requiere usuario y contraseña
     * @param Auth boolean
     */
    public static void mailSetAuth(boolean Auth){
        
        if(!Auth){
            Autenticacion = "false";
        }
    }
    
    /**
     * Metodo para indicar usuario remitente
     * @param User String
     */
    public static void mailSetusuario(String User){
        
        Usuario = User;
    }
    
    /**
     * Metodo para indicar el puerto SMTP
     * @param SmtpPort String
     */
    public static void mailSetSmtpPort(String SmtpPort){
        
        Port = SmtpPort;
    }
    
    /**
     * Metodo para indicar si esta habilitado el TLS
     * @param TLS boolean
     */
    public static void mailSetenableTLS(boolean TLS){
        
        if(!TLS){
            EnableTLS = "false";
        }
    }
    
    /**
     * Metodo para indicar en nombre de servidor de correos
     * @param SmtpHost String
     */
    public static void mailSetSmtpHost(String SmtpHost){
        
        Smtp = SmtpHost;
    }
    
    /**
     * Metodo para devolver version de libreria coDec
     * @return String
     */
    public static String getVersion(){
        
        return VersionCoDec;
        
    }
    
    /**
     * Metodo para calcular CRC-CCITT(XMODEM)
     * @param Tx Cadena a calcular CRC16
     * @return Regresa array incluyendo los dos Bytes del CRC16
     */
    public static int[] calculaCRC16(int[] Tx){
        
        
        int Crc = 0x0000;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12) 
        for(int J=0;J<Tx.length;J++){
            for (int i = 0; i < 8; i++) {
                boolean bit = ((Tx[J]   >> (7-i) & 1) == 1);
                boolean c15 = ((Crc >> 15    & 1) == 1);
                Crc <<= 1;
                if (c15 ^ bit) Crc ^= polynomial;
            }
        }

        Crc &= 0xffff;
        String Resultado = Integer.toHexString(Crc);
        //System.out.println("CRC16-CCITT = " + Resultado);
        int Byte1 = 0;
        int Byte2 = 0;
        switch(Resultado.length()){
            case 1:
                Resultado = "000" + Resultado;
                Byte1 = Integer.parseInt(Resultado.substring(0, 2), 16);
                Byte2 = Integer.parseInt(Resultado.substring(2, 4), 16);
                break;
            case 2:
                Resultado = "00" + Resultado;
                Byte1 = Integer.parseInt(Resultado.substring(0, 2), 16);
                Byte2 = Integer.parseInt(Resultado.substring(2, 4), 16);                
                break;
            case 3:
                Resultado = "0" + Resultado;
                Byte1 = Integer.parseInt(Resultado.substring(0, 2), 16);
                Byte2 = Integer.parseInt(Resultado.substring(2, 4), 16);                
                break;
            case 4:
                Byte1 = Integer.parseInt(Resultado.substring(0, 2), 16);
                Byte2 = Integer.parseInt(Resultado.substring(2, 4), 16);                
                break;                
        }
        int[] Cadena = new int[(Tx.length)+2];
        for(int i=0;i<Tx.length;i++){
            Cadena[i] = Tx[i];
        }
        Cadena[Tx.length] = Byte1;
        Cadena[Tx.length+1] = Byte2;
        
        return Cadena;
        
    }
    
    /**
     * Metodo para validar que no haya otra instancia de la aplicacion en ejecucion
     * @param String socketPort
     * @return boolean
     */
    public static boolean validaInstancia(String socketPort){
        
        int socketP = Integer.parseInt(socketPort);
        
        try {
          ServerSocket socket = new ServerSocket(socketP);
          return false;
        } catch (IOException x) {
          return true;
        }                
        
    }    
    
    /**
     * Metodo para calcular y encriptar registro de la etiqueta de la unidad
     * @return String registro encriptado
     */
    public static String calculaRegistro(){
        
        try{
            String unidad = getRuta();
            unidad = unidad.substring(0, 1);
            String serialHD = coDec.getSerialNumber(unidad);
            if(serialHD.length()>=7){
                serialHD = serialHD.substring(serialHD.length()-7, serialHD.length());
            }
            else{
                String serial = "error equipo no compatible con la aplicacion!!!";
                return serial;
            }
            int numSerial = Integer.parseInt(serialHD);
            numSerial = ((((numSerial*12)/127)+1789)*4)/2;
            String serial = String.valueOf(numSerial);
            serial = Encriptar(serial);
            return serial;
        }
        catch(Exception ex){
            String serial = "error " + ex.getMessage();
            return serial;
        }
        
    }
    
    /**
     * Metodo para generar archivo de configuracion con datos encriptados
     * @param Archivo
     * @param Parametro
     * @param Dato 
     */
    public static void generaArchivo(String Archivo, String[] Parametro, String[] Dato){
        
        int Longitud = Parametro.length;
        try {
            File ArchivoCfg = new File(Archivo);
            BufferedWriter Bw;
            if(ArchivoCfg.exists()){
                Bw = new BufferedWriter(new FileWriter(ArchivoCfg));
                for (int i=0;i<Longitud;i++){
                    Bw.write(Encriptar(Parametro[i]) + Encriptar(Dato[i]));
                }
            }
            else{
                Bw = new BufferedWriter(new FileWriter(ArchivoCfg));
                for (int i=0;i<Longitud;i++){
                    Bw.write(Encriptar(Parametro[i]) + Encriptar(Dato[i]));
                }
            }
            Bw.close();
        } catch (IOException ex) {
            Logger.getLogger(coDec.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error al generar o actualizar archivo de configuracion " + ex.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
        }            
    }
    
    /**
     * Metodo para generar archivo de configuracion con datos sin encriptar
     * @param Archivo
     * @param Parametro
     * @param Dato 
     */
    public static void generaArchivoSE(String Archivo, String[] Parametro, String[] Dato){
        
        int Longitud = Parametro.length;
        try {
            File ArchivoCfg = new File(Archivo);
            BufferedWriter Bw;
            if(ArchivoCfg.exists()){
                Bw = new BufferedWriter(new FileWriter(ArchivoCfg));
                for (int i=0;i<Longitud;i++){
                    Bw.write(Parametro[i] + Dato[i]);
                }
            }
            else{
                Bw = new BufferedWriter(new FileWriter(ArchivoCfg));
                for (int i=0;i<Longitud;i++){
                    Bw.write(Parametro[i] + Dato[i]);
                }
            }
            Bw.close();
        } catch (IOException ex) {
            Logger.getLogger(coDec.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error al generar o actualizar archivo de configuracion " + ex.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
        }            
    }    
    
    /**
     * Metodo para leer parametro encriptado de archivo de configuracion
     * @param parametro String parametro a leer
     * @param archivo String nombre de archivo
     * @return String valor del parametro ya desencriptado
     */
    public static String leeParametro(String parametro, String archivo){
        try{
            // Abrimos el archivo
            FileInputStream archivo2 = new FileInputStream(archivo);
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(archivo2);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            // Leer el archivo linea por linea
            String regresaParametro = null;
            while ((strLinea = buffer.readLine()) != null)   {
                //strLinea = strLinea.toLowerCase();
                if(strLinea.contains(Encriptar(parametro))){
                    int longitudParametro = parametro.length();
                    regresaParametro = strLinea.substring(longitudParametro);
                }                                
            }
            // Cerramos el archivo
            entrada.close();
            return Desencriptar(regresaParametro);
        }catch (Exception e){ //Catch de excepciones
            //log.error("Error en clase arametros metodo leeParametro " + e.getMessage());
            JOptionPane.showMessageDialog(null, "error en lectura de archivo de configuracion " + e.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
            //System.exit(0);
            return null;
        }
            
    }    
    
    /**
     * Metodo para leer parametro no encriptado de archivo de configuracion
     * @param parametro String parametro a leer
     * @param archivo String nombre de archivo
     * @return String valor del parametro no encriptado
     */
    public static String leeParametroSE(String parametro, String archivo){
        try{
            // Abrimos el archivo
            FileInputStream archivo2 = new FileInputStream(archivo);
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(archivo2);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            // Leer el archivo linea por linea
            String regresaParametro = null;
            while ((strLinea = buffer.readLine()) != null)   {
                //strLinea = strLinea.toLowerCase();
                if(strLinea.contains(parametro)){
                    int longitudParametro = parametro.length();
                    regresaParametro = strLinea.substring(longitudParametro);
                }                                
            }
            // Cerramos el archivo
            entrada.close();
            return regresaParametro;
        }catch (Exception e){ //Catch de excepciones
            //log.error("Error en clase arametros metodo leeParametro " + e.getMessage());
            JOptionPane.showMessageDialog(null, "error en lectura de archivo de configuracion " + e.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
            //System.exit(0);
            return null;
        }
            
    } 
    
    /**
     * Metodo para obtener la ruta actual
     * @return String ruta completa de archivo "ruta.txt"
     */
    public static String getRuta(){
        
        String ruta = "";
        File fichero = new File("p89i.txt");
        ruta = fichero.getAbsolutePath();
        ruta = ruta.substring(0, ruta.length()-8);
        return ruta;
        
    }
    
    private static byte[] createChecksum(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }    
    
    private static String getMD5Checksum(String filename) throws Exception {
       byte[] b = createChecksum(filename);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
   } 
    
    /**
     * Metodo para calcular el MD5 de un archivo
     * @param archivo String ruta y nombre de archivo
     * @return String MD5 de archivo
     */
    public static String calculaMD5(String archivo){
        
        String resultado = "";
        try{
            resultado = getMD5Checksum(archivo).toUpperCase();
        }
        catch(Exception e){
            resultado = e.getMessage();
        }
        return resultado;
        
    }   

    /**
     * Metodo para obtener el numero de serie de una unidad
     * @param drive String unidad o volumen
     * @return String numero de serie
     */
    public static String getSerialNumber(String drive) {
    
    String result = "";
    try {
      File file = File.createTempFile("realhowto",".vbs");
      file.deleteOnExit();
      FileWriter fw = new java.io.FileWriter(file);

      String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                  +"Set colDrives = objFSO.Drives\n"
                  +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                  +"Wscript.Echo objDrive.SerialNumber";  // see note
      fw.write(vbs);
      fw.close();
      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
      BufferedReader input =
        new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = input.readLine()) != null) {
         result += line;
      }
      input.close();
    }
    catch(Exception e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "error en getSerialNumber " + e.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
    }
    return result.trim();
    
  }
    
    /**
     * Metodo para setear contraseña de encripcion
     * @param passWord String ontraseña
     */
    public static void contraseña(String passWord){
        secretKey = passWord;
    }
    
    /**
     * Metodo para encriptar una cadena
     * @param texto String Cadena a encriptar
     * @return String Cadena encriptada
     * 
     */
    public static String Encriptar(String texto) {

            //String secretKey = "qualityinfosolutions"; //llave para encriptar datos
            //String secretKey = "siscuibiuros"; //llave para encriptar datos
            String base64EncryptedString = "";

            try {

                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 16);

                SecretKey key = new SecretKeySpec(keyBytes, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);

                byte[] plainTextBytes = texto.getBytes("utf-8");
                byte[] buf = cipher.doFinal(plainTextBytes);
                byte[] base64Bytes = Base64.encodeBase64(buf);
                base64EncryptedString = new String(base64Bytes);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error en Encriptar " + e.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
            }
            return base64EncryptedString;
    }    
    
    /**
     * Metodo para desencriptar una cadena
     * @param textoEncriptado String Cadena a desencriptar
     * @return String Cadena desencriptada
     * 
     */    
    public static String Desencriptar(String textoEncriptado) throws Exception {

            //String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
            //String secretKey = "siscuibiuros"; //llave para encriptar datos
            String base64EncryptedString = "";

            try {
                byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 16);
                SecretKey key = new SecretKeySpec(keyBytes, "AES");

                Cipher decipher = Cipher.getInstance("AES");
                decipher.init(Cipher.DECRYPT_MODE, key);

                byte[] plainText = decipher.doFinal(message);

                base64EncryptedString = new String(plainText, "UTF-8");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error en Desecriptar " + e.getMessage(), "coDec" + VersionCoDec + " dice:", 0);
            }
            return base64EncryptedString;
    }    
        
    
}
