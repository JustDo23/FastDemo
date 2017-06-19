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
   6. 系统媒体扫描程序**不会**读取这些私有目录中的文件，因此**不能从MediaStore**内容提供程序访问这些文件。
6. 外部存储需要提前在功能清单中申请权限。
7. 在使用外部存储之前需要先**判断外部存储的状态**。每次使用之前都应该先判断状态。
8. 使用的文件目录不同需要的**权限**也不大相同。

![](https://ooo.0o0.ooo/2017/06/19/5947347ba9189.png)

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
  LogUtils.e("context.getDir(dirName, MODE_PRIVATE) = " + context.getDir("dirName", MODE_PRIVATE));// [ /data/data/com.just.fast/app_dirName ]
  // 获取外部存储根路径[先判断外部存储是否挂载][读写文件需要权限]
  LogUtils.e("Environment.getExternalStorageState() = " + Environment.getExternalStorageState());// [ mounted ]
  LogUtils.e("Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());// [ /storage/emulated/0 ]
  // [不建议在外部存储根目录操作][因此获取外部存储私有路径][不需要权限][私有文件]
  LogUtils.e("context.getExternalFilesDir(null) = " + context.getExternalFilesDir(null));// [ /storage/emulated/0/Android/data/com.just.fast/files ]
  LogUtils.e("context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) = " + context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));// [ /storage/emulated/0/Android/data/com.just.fast/files/Movies ]
  LogUtils.e("context.getExternalCacheDir() = " + context.getExternalCacheDir());// [ /storage/emulated/0/Android/data/com.just.fast/cache ]
  // [不建议在外部存储根目录操作][因此获取外部存储共享路径][读写文件需要权限][公共文件]
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
  context.getDir(dirName, MODE_PRIVATE) =  [ /data/data/com.just.fast/app_dirName ]
  // 获取外部存储根路径[先判断外部存储是否挂载][读写文件需要权限]
  Environment.getExternalStorageState() =  [ mounted ]
  Environment.getExternalStorageDirectory() =  [ /storage/emulated/0 ]
  // [不建议在外部存储根目录操作][因此获取外部存储私有路径][不需要权限][私有文件]
  context.getExternalFilesDir(null) =  [ /storage/emulated/0/Android/data/com.just.fast/files ]
  context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) =  [ /storage/emulated/0/Android/data/com.just.fast/files/Movies ]
  context.getExternalCacheDir() =  [ /storage/emulated/0/Android/data/com.just.fast/cache ]
  // [不建议在外部存储根目录操作][因此获取外部存储共享路径][读写文件需要权限][公共文件]
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

### 05.  内部存储实用方法

1. 写入

   ```java
   /**
    * 向内部存储写数据
    */
   private void write2Internal() {
     String fileName = "just";// 文件名
     String writeContent = "Test open and write file internal.";// 写入内容
     try {
       FileOutputStream fileOutputStream = this.openFileOutput(fileName, MODE_APPEND);// 打开文件输出流
       fileOutputStream.write(writeContent.getBytes());// 流写入数据
       fileOutputStream.close();// 关闭流
     } catch (IOException e) {
       e.printStackTrace();
     }
   }
   ```

2. 读取

   ```java
   /**
    * 从内部存储读数据
    */
   private void readFromInternal() {
     String fileName = "just";// 文件名
     try {
       FileInputStream fileInputStream = this.openFileInput(fileName);// 打开文件输入流
       byte[] allByte = new byte[fileInputStream.available()];// 字节数组
       fileInputStream.read(allByte);// 读取
       String readContent = new String(allByte);// 转换为字符串
       fileInputStream.close();
       tv_read.setText(readContent);
     } catch (IOException e) {
       e.printStackTrace();
     }
   }
   ```

3. 删除

   ```java
   /**
    * 删除内部存储上文件
    */
   private void deleteInternalFile() {
     String fileName = "just";// 文件名
     this.deleteFile(fileName);
   }
   ```



### 06. 外部存储多个

这里注意一个情景：一台设备既有机身内置大容量外部存储，同时又对外提供了扩展的 **SD卡槽**，那么外部存储就有两个位置了。使用 **`context.getExternalFilesDir()`** 默认获取到内部分区也就是机身外部存储路径，那就无法操作 SD卡路径。不过，从 **Android 4.4** 开始，通过使用 **`context.getExternalFilesDirs()`** 获取两个路径，返回 **file 数组**。数组中第一个条目被视为是外部主存储；除非该位置已满或不可用，否则应该使用该位置。为了向下兼容可以使用支持库中提供的 **`ContextCompat.getExternalFilesDirs()`** 进行兼容，同样返回数组，但只包含一个目录。

对于**缓存路径**同样有兼容方法 **`ContextCompat.getExternalCacheDirs()`** 向下兼容。

### 07. 参考文档

1. [android中的文件操作详解以及内部存储和外部存储](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2013/0923/1557.html)
2. 官网 [存储选项](https://developer.android.com/guide/topics/data/data-storage.html)

