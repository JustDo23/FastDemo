## Android 文件存储

> 引言：文件存储[内部存储]和[外部存储]。SD 卡上的文件路径。
>
> 时间：2017年06月17日
>
> 作者：JustDo23
>
> Github：[https://github.com/JustDo23/FastDemo](https://github.com/JustDo23/FastDemo)

### 01. 文件存储

就 **I/O 流**操作来说，Android 和 Java 是基本相同的，区别可能就是文件路径，**相对路径**与**绝对路径**。某天在浏览手机存储路径时候产生疑惑和兴趣，忽然感觉好久没有写过文件操作的代码，生疏的东西便需要进行梳理。

**文件存储**是**数据存储**中一个重要方式，存储的**路径不同**便有了**内部存储**和**外部存储**。

**强调：**所有的安卓设备都有**内部存储**和**外部存储**。

### 02. 内部存储

1. 内部存储并不是内存。
2. 内部存储是系统上一个**特殊位置**其路径为**`/data/data/<package>`**其中的 package 具体指的是主包名。
3. 每个应用被安装之后都会有自己的内部存储位置，默认情况下只有本应用才能对应的内部存储文件，当然在创建文件的时候可以指定文件的访问权限。
4. 当**应用被卸载**之后，对应的内部存储也会**自动被删除**。
5. 内部存储空间有限因而显得可贵，它也是系统本身和系统应用主要的数据存储所在地，一旦内部存储空间耗尽，手机也就无法使用了。
6. **内部存储一般使用 Context 来获取和操作。**
7. **Shared Preferences** 存储于内部存储**`/data/data/<package>/shared_prefs`**目录。
8. **SQLite** 数据库存储于内部存储**`/data/data/<package>/database`**目录。

### 03. 外部存储

1. 早期的 Android 手机有提供**扩展**存储空间的**内存卡**卡槽，也就是 **SD card** 存储，可拆卸可移动类似于 **U盘** 或者**移动硬盘**。因而比较容易理解为外部存储空间。
2. 现在的 Android 手机基本趋于一体机，取消**内存卡**卡槽，而在手机机身中内置大容量存储空间，这部分机身存储空间同样是外部存储。
3. 直观**区分方法：**将手机连接电脑，能被电脑识别出的部分一定是外部存储。
4. 刚开始学习 Android 的时候比较熟悉的是**`/mnt/sdcar`**目录，这个目录指向外部存储根路径，现在每次看到的都是**`/storage/emulated/0`**目录，同样指向外部存储根路径，在 DDMS 上可以看到第一个路径是指向第二个了。
5. 原则上讲外部存储是公开的，可以被用户或者其他应用访问。外部存储空间可以分为**公共文件**和**私有文件**两种类型。
   1. **公共文件**就是`外部存储根目录`下的`DCIM`，`Download`，`Movies`，`Music`，`Pictures`，`Ringtones`等目录。
   2. **私有文件**就是`外部存储根目录`下的**`Android/data/<package>`**目录。
   3. 其实这个所谓的私有文件因为在外部存储所以同样是可以被访问的。
   4. 当**应用被安装**的时候，同样会**自动生成**相应的**私有文件目录**。
   5. 当**应用被卸载**的时候，相应的**私有文件目录**同样会**自动被删除**。
6. 外部存储需要提前在功能清单中申请权限。
7. 在使用外部存储之前需要先**判断外部存储的状态**。
8. 使用的文件目录不同需要的**权限**也不大相同。

### 04. 文件路径

通过代码查看相关路径

```java
/**
 * 查看文件路径
 *
 * @param context
 */
private void filePath(Context context) {
  // 通过 Context 获取内部存储路径
  LogUtils.e("context.getFilesDir() = " + context.getFilesDir());// [ /data/data/com.just.fast/files ]
  LogUtils.e("context.getCacheDir() = " + context.getCacheDir());// [ /data/data/com.just.fast/cache ]
  // 获取外部存储根路径[先判断外部存储是否挂载][读写文件需要权限]
  LogUtils.e("Environment.getExternalStorageState() = " + Environment.getExternalStorageState());// [ mounted ]
  LogUtils.e("Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());// [ /storage/emulated/0 ]
  // [不建议在外部存储根目录操作][因此获取外部存储私有路径][不需要权限]
  LogUtils.e("context.getExternalFilesDir(null) = " + context.getExternalFilesDir(null));// [ /storage/emulated/0/Android/data/com.just.fast/files ]
  LogUtils.e("context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) = " + context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));// [ /storage/emulated/0/Android/data/com.just.fast/files/Movies ]
  LogUtils.e("context.getExternalCacheDir() = " + context.getExternalCacheDir());// [ /storage/emulated/0/Android/data/com.just.fast/cache ]
  // [不建议在外部存储根目录操作][因此获取外部存储共享路径][读写文件需要权限]
  LogUtils.e("Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) = " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));// [ /storage/emulated/0/Music ]

  // 其他路径[获取系统根路径下的相关路径]
  LogUtils.e("Environment.getRootDirectory() = " + Environment.getRootDirectory());// [ /system ]
  LogUtils.e("Environment.getDataDirectory() = " + Environment.getDataDirectory());// [ /data ]
  LogUtils.e("Environment.getDownloadCacheDirectory() = " + Environment.getDownloadCacheDirectory());// [ /cache ]

  // [true,仿真外部存储][一体机之类]
  LogUtils.e("Environment.isExternalStorageEmulated() = " + Environment.isExternalStorageEmulated());// [ true ]
  // [true,外部存储物理硬件可以移除][SD卡子类][false,一体机之类]
  LogUtils.e("Environment.isExternalStorageRemovable() = " + Environment.isExternalStorageRemovable());// [ false ]
}
```

代码注释多精简结果

```java
  // 通过 Context 获取内部存储路径
  context.getFilesDir() =  [ /data/data/com.just.fast/files ]
  context.getCacheDir() =  [ /data/data/com.just.fast/cache ]
  // 获取外部存储根路径[先判断外部存储是否挂载][读写文件需要权限]
  Environment.getExternalStorageState() =  [ mounted ]
  Environment.getExternalStorageDirectory() =  [ /storage/emulated/0 ]
  // [不建议在外部存储根目录操作][因此获取外部存储私有路径][不需要权限]
  context.getExternalFilesDir(null) =  [ /storage/emulated/0/Android/data/com.just.fast/files ]
  context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) =  [ /storage/emulated/0/Android/data/com.just.fast/files/Movies ]
  context.getExternalCacheDir() =  [ /storage/emulated/0/Android/data/com.just.fast/cache ]
  // [不建议在外部存储根目录操作][因此获取外部存储共享路径][读写文件需要权限]
  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) =  [ /storage/emulated/0/Music ]

  // 其他路径[获取系统根路径下的相关路径]
  Environment.getRootDirectory() =  [ /system ]
  Environment.getDataDirectory() =  [ /data ]
  Environment.getDownloadCacheDirectory() =  [ /cache ]

  // [true,仿真外部存储][一体机之类]
  Environment.isExternalStorageEmulated() =  [ true ]
  // [true,外部存储物理硬件可以移除][SD卡子类][false,一体机之类]
  Environment.isExternalStorageRemovable() =  [ false ]
```

### 05. 参考文档

1. [android中的文件操作详解以及内部存储和外部存储](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2013/0923/1557.html)
2. 官网 [存储选项](https://developer.android.com/guide/topics/data/data-storage.html)











