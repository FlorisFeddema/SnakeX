package SnakeX.Client.Logic;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;


public class Hash {
    /***
     * Hashes the input string
     * @param input string to hash
     * @return hashed string
     */
    public static String getHash(String input){
        return Hashing.sha512()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }
}
