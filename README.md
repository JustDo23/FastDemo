## FastDemo

> 引言：
>
> 时间：
>
> 作者：JustDo23

### 01. Method Count 65,536

1. [有效减少 Android 应用的方法数](https://zhuanlan.zhihu.com/p/26272085)
2. 使用 [https://github.com/KeepSafe/dexcount-gradle-plugin](https://github.com/KeepSafe/dexcount-gradle-plugin) 插件
3. 在 **console** 中运行 `./gradlew assembleDebug` 就会在 `build/outputs/dexcount/debugChart` 中生成数量报告
4. 使用 [**methodscount**](http://www.methodscount.com/) 来查询常用库的方法数

