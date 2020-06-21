package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * 暗号化ユーティリティー
 */
public class CipherUtil {

    /**
     * SHA-256で暗号化し、バイト配列をBase64エンコーディングします。
     *
     * @param target
     *            暗号化対象の文字列
     *
     * @return 暗号化された文字列
     */
    public static String encrypt(String target) {

        try {
            //SHA-256：与えられた文字列に対して256ビットのハッシュ値を返す（メッセージダイジェスト)
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //targetを使ってメッセージダイジェストを更新
            md.update(target.getBytes());
            //計算が完了したメッセージダイジェストをエンコードして戻り値を返す
            return Base64.encodeBase64URLSafeString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            //これ以上処理できない致命的な例外が発生した場合
            throw new RuntimeException(e);
        }
    }

}