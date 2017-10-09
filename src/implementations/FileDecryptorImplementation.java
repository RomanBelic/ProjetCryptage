package implementations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import interfaces.Ciphering.ICipher;
import interfaces.Patterns.IDelegate;
import utils.ConfigLoader;

public class FileDecryptorImplementation implements IDelegate<Void, String> {

	private ICipher fileCipher;
	private final String dir;
	
	public FileDecryptorImplementation(String dir){
		this.dir = dir;
		new File(dir).mkdir();
		try {
			this.fileCipher = new CBCCipher(ConfigLoader.getCBCKey(), ConfigLoader.getCBCVector());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public Void action(String filePath) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
		byte[] data = null;
		try (InputStream fis = new FileInputStream(filePath)){
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
		filePath = filePath.replace(".encrypted", "");
		int subFrom = filePath.lastIndexOf(File.separatorChar) > - 1 ? filePath.lastIndexOf(File.separatorChar) + 1 : 0;
		filePath = filePath.substring(subFrom, filePath.length());
		try (FileOutputStream fos = new FileOutputStream(dir.concat(File.separator).concat(filePath), false)){
			fos.write(data);
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}	
		return null;
	}
}
