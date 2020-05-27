package Java_Training.practice100.no220.no058;
import java.io.IOException;
import java.nio.file.*;

public class No058 {
	public static void main(String[] args) {
		
		if (args.length < 1 ) {
            System.err.println("引数にテキストファイル名を指定し、再実行してください。");
            System.exit(1);
        }
        try {
            // ファイル内容をバイト表記で取得.
            final byte[] fileContentBytes = Files.readAllBytes(Paths.get(args[0]));
            
            // バイト数が3未満の場合.
            if (fileContentBytes.length < 3){
                System.out.println("BOM無しファイルです。");
                System.exit(0);
            }            
            
            // BOM判定.
            if (fileContentBytes[0] == (byte)0xEF && fileContentBytes[1] == (byte)0xBB
                    && fileContentBytes[2] == (byte)0xBF) {
                
                System.out.println("BOM付きファイルです。");
                System.exit(1);
            } else {
                System.out.println("BOM無しファイルです。");
                System.exit(0);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}