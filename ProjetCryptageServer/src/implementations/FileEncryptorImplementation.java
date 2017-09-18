package implementations;

import java.io.File;
import java.io.FileOutputStream;

import interfaces.Ciphering.ICipher;
import interfaces.Patterns.IDelegate;
import models.Upload;
import utils.ConfigLoader;

public class FileEncryptorImplementation implements IDelegate<Void, Upload> {
	
	private final String dirName;
	private ICipher fileCipher;
	
	public FileEncryptorImplementation(String dirName){
		this.dirName = dirName;
		new File(dirName).mkdir();	
		try {
			this.fileCipher = new CBCCipher(ConfigLoader.getCBCKey(), ConfigLoader.getCBCVector());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public Void action(Upload upload) {
		byte[] eData = fileCipher.encrypt(upload.getData());
		try (FileOutputStream fs = new FileOutputStream(
				dirName.concat("/").
				concat(upload.getName()).
				concat(upload.getExtension()).
				concat(".encrypted")
				)){
			fs.write(eData);
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}

}
