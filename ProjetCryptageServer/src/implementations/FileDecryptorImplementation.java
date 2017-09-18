package implementations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import interfaces.Ciphering.ICipher;
import interfaces.Patterns.IDelegate;
import utils.ConfigLoader;
import utils.Utils;

public class FileDecryptorImplementation implements IDelegate<Void, File> {

	private ICipher fileCipher;
	
	public FileDecryptorImplementation(){
		try {
			this.fileCipher = new CBCCipher(ConfigLoader.getCBCKey(), ConfigLoader.getCBCVector());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public Void action(File file) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
		String fileName = Utils.resolveName(file.getName());
		String extension = Utils.resolveExtension(file.getName());
		byte[] data = null;
		try (InputStream fis = new FileInputStream(file)){
			byte[] buff = new byte[2048];
			int bRead;
			while((bRead = fis.read(buff, 0, buff.length)) > 0){
				bos.write(buff, 0, bRead);
			}
			data = fileCipher.decrypt(bos.toByteArray());
			bos.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		try (FileOutputStream fos = new FileOutputStream("Uploads/".concat(fileName).concat(extension), false)){
			fos.write(data);
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}	
		return null;
	}
}
