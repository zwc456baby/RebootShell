
## 开机自动执行 shell 的 apk

有时候想让 安卓系统开机后自动执行shell。

例如：开机后，自动打开网络 adbd，这样方便调试，不需要先连线后再调试。


安装apk 然后设置 开机需要执行的脚本即可。

例如开机自动开启 adbd：

```
stop adbd ; setprop service.adb.tcp.port 5555 ; start adbd
```

只有一个 EditText。如果需要执行多个命令，可以使用： `;` 分号隔开，这样能够执行多条

程序将自动检测 root，如果有 root。将自动使用 `root` 执行，否则使用 `sh`


