# RestTemplateAddonUserSystemProxy
## 背景
Spring Securityで多用される`org.springframework.web.client.RestTemplate`は、Proxy環境下に対応していません。  
例えば、OAuth2.0でサポートされているOIDCログインをProxy環境下で対応することはSprintBoot-2.4.0では、できません。  
内部的にIDトークンのRemoteJWKSetにおいて、公開鍵エンドポイントへ`org.springframework.web.client.RestTemplate`を用いて、  
アクセスを行いますが、この`org.springframework.web.client.RestTemplate`をカスタマイズする方法がありません。
これを解決するために作成しました。

# 仕様
以下のような起動時のJVMパラメータを取り込むようにします。動作原理はLombokと同じjavaagentによるClass書き換えです。  
```
-Dhttp.proxyHost="localhost"
-Dhttp.proxyPort=3128
-Dhttp.proxyUser=test
-Dhttp.proxyPassword=test
-Dhttps.proxyHost="localhost"
-Dhttps.proxyPort=3128
-Dhttps.proxyUser=test
-Dhttps.proxyPassword=test
```
反映されるVMパラメータの全量は`org.apache.http.client.HttpClientBuilder`の`useSystemProperties`のとおりです。
[[Javadoc] org.apache.http.client.HttpClientBuilder](https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/impl/client/HttpClientBuilder.html)

## 詳細
実装としては、`org.springframework.web.client.RestTemplate`のコンストラクタに、以下の処理を追記します。
1. `org.apache.http.client.HttpClient`を`org.apache.http.client.HttpClientBuilder`により生成。
2. 1.Builderオプションとして、`useSystemProperties`, `setSSLContext`を指定。
3. `org.springframework.http.client.HttpComponentsClientHttpRequestFactory`を生成し、1を格納。   
4. `org.springframework.web.client.RestTemplate`のコンストラクタ内で取り込み。  

## 利用方法
### 前提
以下の依存関係を含んだプロジェクトであることが必須です。
```
implementation 'org.apache.httpcomponents:httpclient'
implementation 'org.springframework.boot:spring-boot-starter-web'
```

### 利用方法
当PJをzipでダウンロードし、PJルートにあるRestTemplateAddonUserSystemProxy.jarを利用してください。  
IDEの実行構成のJVM引数もしくはIDEそのもののJVM引数に以下を設定してください。  
※Windowsならパスの先頭はドライブレターを記載してから絶対パスを設定してください。
```
-javaagent:/配置パス/RestTemplateAddonUserSystemProxy.jar
-javaagent:C:/RestTemplateAddonUserSystemProxy.jar
-javaagent:./RestTemplateAddonUserSystemProxy.jar
```
# Appendix
## 実現方法
javaassitにより実現しています。
具体的には行ったことは、
1. RestTemplateのコンストラクタの改造
2. HTTPS接続時の接続先証明書チェックの無視

#### 1. RestTemplateのコンストラクタの改造
javaassitにて、`org.springframework.web.client.RestTemplate`のコンストラクタを抽出し、  
その全てにプロキシ対応（HTTP,HTTPS）した`org.apache.http.client.HttpClient`を含んだ  
`org.springframework.http.client.HttpComponentsClientHttpRequestFactory`を設定しました。

#### 2. HTTPS接続時の接続先証明書チェックの無視
参考サイト1.の内容に沿って`javax.net.ssl.SSLContext`を生成。  

### 注意点
Developer環境での利用を推奨します。

#### 参考サイト
1. [Java × Spring Boot のlocal環境でのProxy設定とSSL証明書の無視](https://kcf-developers.hatenablog.jp/entry/2018/09/04/112933)
