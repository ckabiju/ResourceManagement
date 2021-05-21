package com.ntt.resourcemanagement.Authentication_authorization.util;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class Hashing {

	public static void main(String[] args) {
		System.out.println(bCryptEncode("authenticationForResourceManagement"));

	}

	public static String bCryptEncode(String stringToEncode) {
		//BCryptPasswordEncoder()
		//BCryptPasswordEncoder(int strength)
		// BCryptPasswordEncoder(int strength, java.security.SecureRandom random)
		//1. strength - By default, it's set to 10, though it can go up to 32 - The larger the strength is, the more work it takes to compute the hash. This "strength" is actually the number of iterations (210) used.
		//2. SecureRandom is an object containing a random number that's used to randomize the generated hashes:
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12,  new SecureRandom()); // Strength set as 12
		return encoder.encode(stringToEncode);
	}
	
	public static String Pbkdf2Encode(String stringToEncode) {
		//Pbkdf2PasswordEncoder()
		//Pbkdf2PasswordEncoder(java.lang.CharSequence secret)
		//Pbkdf2PasswordEncoder(java.lang.CharSequence secret, int iterations, int hashWidth)
		
		//1. Secret - Key used during the encoding process. As the name implies, it should be secret.
		//2. Iteration - The number of iterations used to encode the password, the documentation advises as many iterations for your system to take 0.5 seconds to hash.
		//3. Hash Width - The size of the hash itself.
		Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);
		return encoder.encode(stringToEncode);
	}
	
	public static String SCryptEncode(String stringToEncode) {
		//SCryptPasswordEncoder()
		//SCryptPasswordEncoder(int cpuCost, int memoryCost, int parallelization, int keyLength, int saltLength)

		//1. CPU cost - CPU Cost of the algorithm, the default is 214 - 16348. This int must be a power of 2.
		//2. Memory cost - By default is 8
		//3. Parallelization - Although formally present, SCrypt doesn't take advantage of parallelization.
		//4. Key Length - Defines the length of the output hash, by default, it's set to 32.
		//5. Salt Length - Defines the length of the salt, the default value is 64.
		
		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		return encoder.encode(stringToEncode);
	}
	
	
	
}
