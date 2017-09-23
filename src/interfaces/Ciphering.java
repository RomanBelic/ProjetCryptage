package interfaces;

public class Ciphering {
	
	public interface ICipher {
		byte[] encrypt(byte[] input);
		byte[] decrypt(byte[] input);
	}
	
	public interface IHashable{
		//CRC32 method
		long createHash(byte[] input);
		String createHashString(String input);
		byte[] generateSecretKey(int length);
		String generateSecretKeyString(int length);
	}
}
