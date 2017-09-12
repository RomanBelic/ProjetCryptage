package interfaces;

public class Ciphering {
	
	public interface ICipher {
		byte[] encrypt(byte[] input);
		byte[] decrypt(byte[] input);
	}
	
	public interface IHashable{
		long createHash(byte[] input);
		//CRC32 method
	}
}
