# RestTemplateAddonUserSystemProxy
## 仕様
Spring Securityで多用されるRestTemplateは、Proxy環境下に対応していません。  
例えば、OAuth2.0でサポートされているOIDCログインをProxy環境下で対応することはSprintBoot-2.4.0では、できません。  
内部的にIDトークンのRemoteJWKSetにおいて、公開鍵エンドポイントへRestTemplateを用いて、  
アクセスを行いますが、このRestTemplateをカスタマイズする方法がありません。

あらかじめ以下のJVMパラメータを取り込むようにします。  
値は仮で、実際にJVM引数で指定されたものなどを取り込みます。
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
実装としては、RestTemplateのnewにjavaagentでApacheHttpClientを持つ、
org.springframework.http.client.HttpComponentsClientHttpRequestFactoryを生成し、  
RestTemplateのコンストラクタ内で取り込むようにしています。  
反映されるVMパラメータの全量は以下Javadocで確認できます。
https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/impl/client/HttpClientBuilder.html

## 利用方法
PJルートにあるRestTemplateAddonUserSystemProxy.jarをダウンロードし、  
IDEの実行構成のJVM引数もしくはIDEそのもののJVM引数に以下を設定してください。  
※Windowsならパスは""で囲ってください。
```
-javaagent:/配置パス/RestTemplateAddonUserSystemProxy.jar
```
