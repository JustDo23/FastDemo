## Android 应用程序与浏览器

> 引言：这些知识点都比较简单了，感觉是强迫症在驱使着写下笔记。
>
> 时间：2017年05月23日
>
> 作者：JustDo23
>
> Github：[https://github.com/JustDo23/FastDemo](https://github.com/JustDo23/FastDemo)

### 01. 浏览器启动 APP

1. 首先需要明确一点基础概念

   ```xml
   <a href="[scheme]://[host]/[path]?[query]">点我启动应用程序</a> 
   ```

2. 在 `AndroidManifest.xml` 中对被启动的 `Activity` 进行配置

   ```java
   <activity android:name=".MainActivity">

         <intent-filter>
           <action android:name="android.intent.action.MAIN"/>
           <category android:name="android.intent.category.LAUNCHER"/>
         </intent-filter>

         <intent-filter>
           <action android:name="android.intent.action.VIEW"/>
           <category android:name="android.intent.category.DEFAULT"/>
           <category android:name="android.intent.category.BROWSABLE"/>
           <data android:scheme="chai" android:host="justdo23"/>
         </intent-filter>

         <intent-filter>
           <action android:name="android.intent.action.VIEW"/>
           <category android:name="android.intent.category.DEFAULT"/>
           <category android:name="android.intent.category.BROWSABLE"/>
           <data android:scheme="just"/>
         </intent-filter>

   </activity>
   ```

3. 接着写出 `Html` 文件 `Just.xml`

   ```html
   <!DOCTYPE html>
   <html>
   <head>
   <meta charset="utf-8">
   <title>启动 APP</title>
   </head>
   <body>
     <a href="chai://justdo23.com/login?name=xiao&password=123">无法启动</a>
     <a href="chai://justdo23/login?name=xiao&password=123">启动 APP1</a>
     <a href="chai://justdo23?name=xiao&password=123&url=https://www.baidu.com">启动 APP2</a>
     <a href="chai://justdo23?m=native&url=https://zhidao.baidu.com/list?cid=103106">启动 APP3</a>
     <a href="just://baidu.com">启动 APP4</a>
     <a href="just://justdo23.com">启动 APP5</a>
   </body>
   </html>
   ```

4. 在浏览器中打开 `Html` 文件，点击标签，就可以进行测试了

5. 这里**需要注意**：

   - 多个`intent-filter`是平级关系，如果嵌套在一起，会出现`桌面没有 App 图标`的情况
   - 注意`data`标签中的`scheme`标签文本是不支持大写的
   - 注意`data`标签中的`host`标签的使用
   - 注意上边两个`data`标签不能放在一个`intent-filter` 中

6. 在 Activity 中通过 intent 来获取数据

   ```java
   private HashMap<String, String> browserMap = new HashMap<>();// 浏览器数据

   /**
    * 从浏览器获取数据[方法一]
    */
   private void getParameterFromBrowser() {
     Intent intent = getIntent();
     Uri data = intent.getData();
     if (data == null) return;
     browserMap.put("scheme", data.getScheme());
     browserMap.put("host", data.getHost());
     Set<String> queryParameterNames = data.getQueryParameterNames();
     String[] queryParamNameArray = (String[]) queryParameterNames.toArray();
     for (int i = 0; i < queryParamNameArray.length; i++) {
       String key = queryParamNameArray[i];
       browserMap.put(key, data.getQueryParameter(key));
     }
   }
   ```

7. 在 Activity 中通过 intent 来获取数据

   ```java
   /**
    * 从浏览器获取数据[方法二]
    */
   private void getDataFromBrowser() {
     Intent intent = getIntent();
     String uri = intent.getDataString();
     if (uri != null) {
       String scheme = intent.getScheme();
       String leave = uri.split(scheme + "://")[1];
       String query;
       if (leave.contains("?")) {
         query = leave.split("\\?")[1];
       } else {
         query = leave;
       }
       browserMap = new HashMap();
       String[] split = query.split("&");
       for (int i = 0; i < split.length; i++) {
         String key = split[i].split("=")[0];
         String value = split[i].split("=")[1];
         browserMap.put(key, value);
       }
       browserMap.toString();
     }
   }
   ```

   **注意：** 这种方式是直接对从 `xml` 中获取到的启动字符串进行拆分截取从而获取到想要的数据，如果该串中包含了一个 `url` 的参数需要进行 **URL 编码** 否则解析会出现异常。


### 02. APP 启动浏览器 

1. 基础的启动网络地址

   ```java
   /**
    * 启动浏览器
    */
   private void startBrowser() {
     Intent intent = new Intent();
     intent.setAction("android.intent.action.VIEW");
     intent.setData(Uri.parse("https://www.baidu.com"));
     startActivity(intent);
   }
   ```

2. 启动原生浏览器并指定本地文件

   ```java
   /**
    * 启动浏览器[本地文件]
    */
   private void startBrowserByLocal() {
     Intent intent = new Intent();
     intent.setAction("android.intent.action.VIEW");
     intent.setData(Uri.parse("file:///sdcard/JustDo23/html/Just.html"));// 指定本地的数据源
     intent.addCategory("android.intent.category.BROWSABLE");// 可以没有
     intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");// 指定启动原生的浏览器[没有就启动不了浏览器]
     startActivity(intent);
   }
   ```

3. **注意**：如果设备上没有可接收隐式 Intent 的应用，您的应用将在调用 `startActivity()` 时崩溃。

   ```java
   if (intent.resolveActivity(getPackageManager()) != null) {// 添加没有的判断防止崩溃
     startActivity(intent);
   }
   ```


### 03. 拓展

1. **DeepLink**
2. **App Links**
3. **App Indexing**
4. [Google Handling App Links](https://developer.android.com/training/app-links/index.html)
5. [Google Add Android App Links](https://developer.android.com/studio/write/app-link-indexing.html)


