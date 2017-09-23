package implementations;

import java.util.Random;
import java.util.zip.CRC32;

import interfaces.Ciphering.IHashable;

public class HashImplementation implements IHashable {

	@Override
	public long createHash(byte[] input) {
		CRC32 crc = new CRC32();
		crc.update(input);
		return crc.getValue();
	}

	@Override
	public byte[] generateSecretKey(int length) {
		Random rand = new Random();
		byte[] key = new byte[length];
		for(int i = 0; i < length; i++){
			key[i] = (byte) rand.nextInt(255);
		}
		return key;
	}

	@Override
	public String createHashString(String input) {
		CRC32 crc = new CRC32();
		crc.update(input.getBytes());
		return String.valueOf(crc.getValue());
	}

	@Override
	public String generateSecretKeyString(int length) {
		Random rand = new Random();
		StringBuilder strB = new StringBuilder(length);
		int i = 0;
		while (i < 0){
			strB.append((char)rand.nextInt(255));
			i++;
		}
		return strB.toString();
	}
	
}
