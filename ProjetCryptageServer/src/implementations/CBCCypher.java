package implementations;

import java.io.ByteArrayInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;

import interfaces.Ciphering.ICipher;

public class CBCCypher implements ICipher {
	
	private final byte[] keyBytes;
	private final int blockLength;
	private final byte[] initVectorBytes;
	
	public CBCCypher (String key, String initVector) throws InvalidAlgorithmParameterException{
		this.keyBytes = key.getBytes();
		this.blockLength = keyBytes.length;
		this.initVectorBytes = initVector.getBytes();
		if (keyBytes.length != initVectorBytes.length)
			throw new InvalidAlgorithmParameterException("Taille de la clé et IV doit être identique");
	}

	@Override
	public byte[] encrypt(byte[] input) {	
		byte[] prevBlock = Arrays.copyOf(initVectorBytes, blockLength);
		byte[] buffer = new byte[blockLength];
		byte[] output = new byte[input.length];
		
		int bRead;
		int bWrote = 0;
		try (ByteArrayInputStream bas = new ByteArrayInputStream(input)){
			while((bRead = bas.read(buffer, 0, blockLength)) > 0){
				for (int i = 0; i < bRead; i++){
					int xor = (int)prevBlock[i] ^ (int)buffer[i] ^ (int)keyBytes[i];
					prevBlock[i] = (byte)xor;
					output[bWrote + i] = prevBlock[i];
				}
				bWrote += bRead;
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return output;
	}

	@Override
	public byte[] decrypt(byte[] input) {
		byte[] prevBlock = Arrays.copyOf(initVectorBytes, blockLength);
		byte[] buffer = new byte[blockLength];
		byte[] output = new byte[input.length];
		
		int bRead;
		int bWrote = 0;
		try (ByteArrayInputStream bas = new ByteArrayInputStream(input)){
			while((bRead = bas.read(buffer, 0, blockLength)) > 0){
				for (int i = 0; i < bRead; i++){
					int xor = (int)buffer[i] ^ (int)keyBytes[i] ^ (int)prevBlock[i];
					output[bWrote + i] = (byte)xor;
					prevBlock[i] = buffer[i];
				}
				bWrote += bRead;
				System.out.println(bWrote);
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return output;
	}
}
