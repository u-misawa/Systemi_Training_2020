[1]<br>
catchブロックを複数記述する場合は記述順番に気を付ける<br>
詳細な例外クラスを上にしてあいまいな例外クラスを記述していく<br>

[2]<br>
1. throw命令でも例外内容を識別できるようにするためにExpectionをスローしない<br>
1. 検査例外と非検査例外の使い分けをする<br>
検査例外は呼び出し側で問題を回復するため、例外が回復可能状態になければいけない<br>
回復できない例外は非検査例外としてスローし、例外の発生元をバグとして修正することを表す<br>
1. 標準例外を利用することで自作の例外クラスを作成する必要がない<br>