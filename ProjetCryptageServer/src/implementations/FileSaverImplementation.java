package implementations;

import java.io.File;
import java.io.FileOutputStream;

import interfaces.Patterns.IDelegate;
import models.Upload;

public class FileSaverImplementation implements IDelegate<Void, Upload> {
	
	private final String dirName;
	
	public FileSaverImplementation(String dirName){
		this.dirName = dirName;
		new File(dirName).mkdir();	
	}
	
	@Override
	public Void action(Upload upload) {
		try (FileOutputStream fs = new FileOutputStream(dirName.concat("/").
				concat(upload.getName()).concat(upload.getExtension()))){
			fs.write(upload.getData());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}

}
