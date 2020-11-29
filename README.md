# RestTemplateAddonUserSystemProxy
## 仕様
Spring Securityで多用されるRestTemplateは、Proxy環境下に対応していません。

あらかじめ以下のJVMパラメータを取り込むようにします。  
値は仮で、実際にJVM引数で指定されたものを取り込みます。
```
-Djdk.http.auth.tunneling.disabledSchemes="" 
-Djdk.http.auth.proxying.disabledSchemes=""
-Dhttp.proxyHost="localhost"
-Dhttp.proxyPort=3128
-Dhttp.proxyUser=test
-Dhttp.proxyPassword=test
-Dhttps.proxyHost="localhost"
-Dhttps.proxyPort=3128
-Dhttps.proxyUser=test
-Dhttps.proxyPassword=test
```

## 利用方法
PJルートにあるRestTemplateAddonUserSystemProxy.jarをダウンロードし、  
IDEの実行構成のJVM引数もしくはIDEそのもののJVM引数に以下を設定してください。  
※Windowsならパスは""で囲ってください。
```
-javaagent:/配置パス/RestTemplateAddonUserSystemProxy.jar
```
