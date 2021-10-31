# クエッションブック
## 自分で問題を作成し作成した問題を出題出来るアプリです。分類ごとにクイズを作成できるため各区分に対して問題を分けて保存できます。
### ■サイドメニューでの区分として
 1. ホルダー 
    >■問題の管理・追加・修正・削除  
    >　問題の区分を作成し、その区分の中で問題を作成出来る箇所、また作成した問題の管理ができます.  
    >　・カテゴリー　大区分を表す項目で、その項目をタップするとブックの画面へ遷移します.    
    >　・ブック問題集が一つにまとめられた区分でタップすると問題集一覧画面へ遷移します。  
    >　・リーフ問題集の一覧を表示する区分で問題の削除、修正、追加を行うことができます。
  2. クイズゲーム
     >■作成したクイズを実際に出題  
     >　ホルダーで作成した問題をこの画面で出題することができます。各項目を選択していくとその項目の問題がランダムで出題されます。
     >　なお問題を終えた段階で回答率が表示され、どの回答を選択したのかを閲覧できます。  
  3. 統計
     >■出題問題の回答率をグラフ表示
     >クイズゲームを行った日時での回答率を円グラフで表示してあります。　　
     >https://github.com/PhilJay/MPAndroidChart　グラフのライブラリーとして
  4. 履歴
     >■出題問題の履歴
     >問題を行った日時と出題された問題の順番、選択した解答を履歴として残してあります。
     >現在、履歴の削除は行うことが出来ないようになってます。
  5. ゴミ箱
     >　何かしらのデータを削除したときに一時的に保存される箇所。この画面で物理的にデータを削除する  
  ___
## その他(開発者の気持ち)
### なぜこのアプリ作成しようと思ったか？
>はじめは、プログラムの勉強用で効率よく記憶に残せる方法をネットやYouTubeで漁っていたところ問題をひたすら解いて能動的になることで長期的に学んだことが保存できる記事が多かったので、よし自分で学んだことを問題にしてみようと考え。最初はワードやエクセルで問題を作成しようとしてましたが途中で、いや問題作るアプリ作ればいいんじゃない？と思い始めたことがきっかけでこのクエッションブックを作成しました.  

### 実際に使ってみてどうですか？
>うーん、（笑） いいんですけど、文字を入力するときにスマホだと操作感に難があるというか、どうしても問題文を入力するときに文章が多少長くなるので、文章を入れる時の過程がめんどくさいというかどうにかならないかなーと。PCと連携してキーボードで入力することができれば割と苦ではないかと思いました。あとは、達成感の方が強くてこのアプリで問題を作成してないですね...ほら、本を買っただけで満足しちゃうやつみたいな感じですよ、そうあんな感じ。

### 難しいと感じたところは？
>確か、問題を出題する画面と履歴を残す画面でしたかね、問題を出題するときに選択肢もランダムにしないといけないのと問題を終えて結果の画面から一つ前に戻った時にやり直し出来ないようにするなど、無駄にデザインパターンを取り入れてたところやDBのテーブルの紐づける箇所などなど...

### 反省点
>細かなバグがあったのとコードを見直したときに同じ処理を書いていた（←これはいいとして？）コードに対してのコメントが少ないこと。一番は履歴をを削除した時に回答率のテーブルの中から一つのレコードが削除されるのだが、問題を終えた段階で履歴のテーブルに回答率の識別番号を登録する必要がある、ちょっと文章で表しずらいので省略します。要するに一番最後の履歴を削除すると、次回から保存される履歴の詳細が見れなくなるバグがあるので、現状は履歴を消せないようにしているところ。

### 次回予告
>2021/8/10　辺りからお金管理アプリマネーマネージャーを開発中もちろんプライベートです！
