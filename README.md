# CommonUtils

Android 里可直接静态调用的 Java 工具库。源码语言是 Java 8，库模块 `minSdkVersion` 为 23。

工具按类组织。已发布的 `1.0.2` 有 **8 个工具类**，放在 6 个包里，并不是 8 个包。`calculate` 包同时包含 `DecimalUtil` 和 `MathematicsUtil`。当前 `main` 源码里还有尚未发布的类和方法，不能通过 JitPack `1.0.2` 调用。

## 版本说明

| 范围 | 怎么用 | 包含什么 |
| --- | --- | --- |
| 已发布 `1.0.2` | JitPack 依赖下面的 `1.0.2` | 只包含「已发布 1.0.2 的工具类」一节里的方法 |
| `main` 源码、尚未发布 | 自行构建当前 `main` | 「main 上尚未发布」一节。**不能**通过 `CommonUtils:1.0.2` 调用 |

`1.0.1` 的类还在 `com.henry.common.utils` 根包下。`1.0.2` 起改为下面的子包。两个 tag 的类名位置不同，不能混用。

本次没有发布新 tag，也没有修改 `1.0.2` 里已有方法的签名和返回值。

## 依赖已发布的 1.0.2

在工程的 `build.gradle` 中加入：

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

在使用该库的模块里加入：

```gradle
dependencies {
    implementation 'com.github.Henry-William-Code:CommonUtils:1.0.2'
}
```

调用前先看清方法属于哪一节。下面「main 上尚未发布」的示例只对当前 `main` 源码有效，不能用于 JitPack `1.0.2`。

## 已发布 1.0.2 的工具类

这些方法在 `1.0.2` 中已经存在。签名与 `1.0.2` tag 一致。

### StringUtil

包名：`com.henry.common.utils.string`

- `boolean isEmpty(String string)`：字符串为 null 或长度为 0 时返回 true。只含空格时返回 false。
- `String outPrintEmptyString(String string)`：null 或空串返回 `""`，否则原样返回。

### Base64Util

包名：`com.henry.common.utils.encryption`

- `String encode(String data)`
- `String decode(String data)`
- `char[] encode(byte[] data)`
- `byte[] decode(char[] data)`
- `void encode(File file)`
- `void decode(File file)`

### MD5Util

包名：`com.henry.common.utils.encryption`

- `String addMD5(String original)`：计算一次 MD5，返回 32 位小写十六进制；原文为空时返回空串。
- `String addMD5Twice(String original)`：连续计算 2 次。
- `String addMD5Ten(String original)`：连续计算 10 次。
- `String addMD5Times(String original, int times)`：按给定次数连续计算。

### SystemUtil

包名：`com.henry.common.utils.system`

- `int getPackageVersion(Context context)`：当前应用的 `versionCode`。
- `String getPackageName(Context context)`：当前应用的 `versionName`，不是应用包名。
- `String getSystemVersion()`：系统版本号。
- `String getSystemModel()`：手机型号。
- `String getDeviceBrand()`：手机厂商。
- `void callPhone(Context context, String phoneNum)`：打开拨号界面，不直接拨出。
- `void settingsWidgetWidth(Context context, View view, int number, int count)`：按屏幕宽度比例设置宽度，父布局为 `RelativeLayout`。
- `void settingsWidgetWidth2(Context context, View view, int number, int count)`：同上，父布局为 `LinearLayout`。
- `void settingsWidgetHeight(Context context, View view, int number, int count)`：按屏幕高度比例设置高度，父布局为 `RelativeLayout`。
- `void settingsWidgetHeight2(Context context, View view, int number, int count)`：同上，父布局为 `LinearLayout`。
- `String extractYearMonthDayOfIdCard(String id)`：从身份证号截取日期文本。
- `int dip2px(Context context, float dpValue)`：dp 转 px。
- `int getRandomInt(int min, int max)`：按给定上下界取一个 int。
- `boolean isTablet(Context context)`：用屏幕尺寸档位判断是否像平板。

### TimeUtil

包名：`com.henry.common.utils.time`

- `String getFormatTime(String format, Date time)`
- `String getFormatTime(String format, String time)`：`time` 按毫秒数字的文本解析。
- `String getFormatTime(String format, long time)`
- `boolean isContainsTime(String startTime, String endTime)`：当前毫秒时间是否落在两个毫秒文本之间。
- `boolean isAfterEndTime(String endTime)`：当前时间是否不早于结束毫秒。
- `Date stringSwitchDate(String format, String dateText)`：按格式把文本转成 `Date`。

### WebUtil

包名：`com.henry.common.utils.web`

- `void intentBrowserWeb(Context context, String url)`：用外部浏览器打开地址。
- `List<String> getImageSrc(String htmlCode)`：从 HTML 中提取限定后缀的图片地址；没有图片时返回空列表。
- `String removeHtmlTag(String htmlStr)`：去掉脚本、样式和 HTML 标签后返回文本。

### DecimalUtil

包名：`com.henry.common.utils.calculate`

- `String toBinaryString(int number)`：输出 32 位二进制文本，高位用 0 补齐。
- `int binary2Decimal(String number)`：二进制文本转十进制。
- `int scale2Decimal(String number, int scale)`：2 到 32 进制文本转十进制。
- `void checkNumber(String number)`：检查文本是否只含十进制数字，否则抛出 `IllegalArgumentException`。

### MathematicsUtil

包名：`com.henry.common.utils.calculate`

- `int power(int number, int times)`：计算整数次幂。`times` 小于 1 时返回 0。

## main 上尚未发布

下面的类和方法在当前 `main` 源码里，还没有打 tag，也没有发布到 JitPack。`CommonUtils:1.0.2` 不能调用它们。已发布方法的签名和返回值没有改。

### 新类 CollectionUtil

包名：`com.henry.common.utils.collection`

给集合、Map 和数组提供 null 安全的空判断与计数。类不能实例化。

#### `boolean isEmpty(Collection<?> data)`

判断集合是否没有元素。

- `data`：允许为 null。
- null 或 `isEmpty()` 为 true 时返回 true。
- 集合里有元素时返回 false；元素本身是 null 仍然算有元素。

```java
CollectionUtil.isEmpty((Collection<?>) null);          // true
CollectionUtil.isEmpty(Collections.emptyList());       // true
CollectionUtil.isEmpty(Collections.singletonList("a")); // false
```

#### `boolean isEmpty(Map<?, ?> data)`

判断 Map 是否没有元素。

- `data`：允许为 null。
- null 或没有条目时返回 true。
- 值为 null 的条目仍然算有元素。

```java
CollectionUtil.isEmpty((Map<?, ?>) null);    // true
CollectionUtil.isEmpty(Collections.emptyMap()); // true
```

#### `boolean isEmpty(Object[] data)`

判断数组是否没有元素。

- `data`：允许为 null。
- null 或长度为 0 时返回 true。
- 长度大于 0 时返回 false，即使元素是 null。

```java
CollectionUtil.isEmpty((Object[]) null);     // true
CollectionUtil.isEmpty(new Object[0]);       // true
CollectionUtil.isEmpty(new Object[]{null});  // false
```

#### `int size(Collection<?> data)`

返回集合元素个数。

- `data`：允许为 null。null 时返回 0。
- 其他情况返回 `data.size()`。不吞掉集合自己抛出的异常。

```java
CollectionUtil.size((Collection<?>) null);           // 0
CollectionUtil.size(Arrays.asList("a", null));       // 2
```

#### `int size(Map<?, ?> data)`

返回 Map 条目个数。

- `data`：允许为 null。null 时返回 0。
- 其他情况返回 `data.size()`。不吞掉 Map 自己抛出的异常。

```java
CollectionUtil.size((Map<?, ?>) null); // 0
```

#### `boolean isNotEmpty(Collection<?> data)`

集合是否至少有一个元素。规则与 `isEmpty(Collection)` 相反。

- `data`：允许为 null。null 或没有元素时返回 false。
- 元素本身是 null 时仍然返回 true。

```java
CollectionUtil.isNotEmpty((Collection<?>) null);            // false
CollectionUtil.isNotEmpty(Collections.singletonList(null)); // true
```

#### `boolean isNotEmpty(Map<?, ?> data)`

Map 是否至少有一个元素。规则与 `isEmpty(Map)` 相反。

- `data`：允许为 null。null 或没有条目时返回 false。
- 值为 null 的条目仍然算有元素。

```java
CollectionUtil.isNotEmpty(Collections.emptyMap()); // false
```

#### `boolean isNotEmpty(Object[] data)`

数组是否至少有一个元素。规则与 `isEmpty(Object[])` 相反。

- `data`：允许为 null。null 或长度为 0 时返回 false。
- 长度大于 0 时返回 true，即使元素是 null。

```java
CollectionUtil.isNotEmpty(new Object[]{null}); // true
```

#### `<T> T getOrNull(List<T> list, int index)`

按下标读取列表元素。

- `list`：允许为 null。null 时返回 null。
- `index`：小于 0，或大于等于列表长度时返回 null。
- 下标合法但元素本身是 null 时也返回 null。返回值无法区分“越界”和“元素是 null”。

```java
CollectionUtil.getOrNull(Arrays.asList("a", "b"), 1); // "b"
CollectionUtil.getOrNull(Arrays.asList("a"), -1);     // null
CollectionUtil.getOrNull(null, 0);                    // null
```

#### `<V> V getOrDefault(Map<?, V> map, Object key, V defaultValue)`

读取 Map 的值。键不存在或 Map 为 null 时返回默认值。

- `map`：允许为 null。null 时返回 `defaultValue`，不抛异常。
- `key`：允许为 null。是否存在用 `containsKey` 判断。
- 键存在且值是 null 时返回 null，不返回 `defaultValue`。
- `defaultValue` 可以是 null。这时键不存在也会返回 null，无法和“键存在但值为 null”区分。

```java
Map<String, String> data = new HashMap<String, String>();
data.put("present", null);
CollectionUtil.getOrDefault(data, "missing", "fallback"); // "fallback"
CollectionUtil.getOrDefault(data, "present", "fallback"); // null
CollectionUtil.getOrDefault(null, "k", "fallback");       // "fallback"
```

### StringUtil 新增

#### `boolean isBlank(String string)`

判断字符串是否为 null、空串，或只含空白。

- `string`：允许为 null。
- 空白使用 `Character.isWhitespace`。空格、制表符、换行算空白。不间断空格 `\u00A0` 不算空白。
- null、`""`、只含空白时返回 true。含任一非空白字符时返回 false。
- 与已发布的 `isEmpty` 不同：`isEmpty(" ")` 为 false，`isBlank(" ")` 为 true。

```java
StringUtil.isBlank(null);       // true
StringUtil.isBlank(" \t");      // true
StringUtil.isBlank(" a ");      // false
StringUtil.isEmpty(" ");        // false，这是 1.0.2 已有行为
```

#### `boolean equals(String left, String right)`

按内容比较两个字符串。

- `left`、`right`：都允许为 null。
- 两者都为 null 时返回 true；只有一方为 null 时返回 false。
- 区分大小写，不去掉首尾空白。

```java
StringUtil.equals(null, null); // true
StringUtil.equals(null, "a");  // false
StringUtil.equals("a", "A");   // false
StringUtil.equals("a", "a");   // true
```

#### `String repeat(String text, int count)`

把 `text` 重复 `count` 次。

- `text`：允许为 null。null 或空串时返回 `""`。
- `count`：小于 1 时返回 `""`。等于 1 时返回原文本。
- 结果字符数大于 `Integer.MAX_VALUE` 时抛出 `IllegalArgumentException`，不返回截断结果。

```java
StringUtil.repeat(null, 3);  // ""
StringUtil.repeat("ab", 0);  // ""
StringUtil.repeat("ab", 3);  // "ababab"
```

#### `String join(CharSequence delimiter, String... parts)`

用分隔符连接多段文本。

- `delimiter`：允许为 null，此时按空串处理。
- `parts`：为 null 或没有元素时返回 `""`。
- 某个元素为 null 时按空串参加拼接，保留这个位置，不会变成文本 `"null"`。
- 分隔符只出现在元素之间。

```java
StringUtil.join(",", "a", "b", "c");             // "a,b,c"
StringUtil.join(null, "a", "b");                 // "ab"
StringUtil.join(",", "a", null, "b");            // "a,,b"
StringUtil.join(",", (String[]) null);           // ""
```

### MathematicsUtil 新增

已发布的 `power` 没有变化。

#### `int clamp(int value, int min, int max)`

把整数限制在闭区间内。

- `value`：原值。
- `min`：下界，包含。`max`：上界，包含。
- `min > max` 时抛出 `IllegalArgumentException`，不会对调两个边界。
- `value < min` 返回 `min`，`value > max` 返回 `max`，否则返回 `value`。

```java
MathematicsUtil.clamp(12, 0, 10); // 10
MathematicsUtil.clamp(-1, 0, 10); // 0
MathematicsUtil.clamp(5, 0, 10);  // 5
```

#### `long clamp(long value, long min, long max)`

把 long 限制在闭区间内。已有的 int 重载不变。

- `value`、`min`、`max` 都是 long。`min > max` 时抛出 `IllegalArgumentException`，不会对调边界。
- 只做比较，`Long.MIN_VALUE` 和 `Long.MAX_VALUE` 可以作为原值或边界。
- 小于下界返回 `min`，大于上界返回 `max`，否则返回原值。

```java
MathematicsUtil.clamp(12L, 0L, 10L); // 10
MathematicsUtil.clamp(-1L, 0L, 10L); // 0
MathematicsUtil.clamp(5L, 0L, 10L);  // 5
```

#### `int gcd(int a, int b)`

计算最大公约数。

- 负数按绝对值计算，结果始终大于等于 0。
- `gcd(0, 0)` 返回 0。`gcd(0, n)` 返回 `n` 的绝对值。
- 任一参数为 `Integer.MIN_VALUE` 时抛出 `IllegalArgumentException`。

```java
MathematicsUtil.gcd(12, 8);   // 4
MathematicsUtil.gcd(-12, 8);  // 4
MathematicsUtil.gcd(0, 0);    // 0
```

#### `int lcm(int a, int b)`

计算最小公倍数。

- 负数按绝对值计算，结果始终大于等于 0。
- 任一参数为 0 时返回 0，包括 `lcm(0, 0)`。
- 任一参数为 `Integer.MIN_VALUE`，或结果大于 `Integer.MAX_VALUE` 时，抛出 `IllegalArgumentException`。参数含 `Integer.MIN_VALUE` 时即使另一方是 0 也抛异常，不返回 0。

```java
MathematicsUtil.lcm(4, 6);  // 12
MathematicsUtil.lcm(0, 5);  // 0
MathematicsUtil.lcm(-4, 6); // 12
```

### TimeUtil 新增

#### `long daysBetween(long startMillis, long endMillis)`

计算两个毫秒时间戳相差的整天数。

- `startMillis`：开始时间。`endMillis`：结束时间。单位都是毫秒。
- 用 `86400000` 毫秒作为一天，做整数除法，向 0 截断。不足一天的部分舍去。
- 不按设备时区换算日历日期。同一天内的两个本地时刻不会因为时区被改成 1 天。
- `endMillis` 小于 `startMillis` 时结果为负数；不足一整天的负数差值结果为 0。
- 两个时间戳相减溢出 long 时抛出 `ArithmeticException`。

```java
TimeUtil.daysBetween(0L, 86400000L);  // 1
TimeUtil.daysBetween(0L, 86399999L);  // 0
TimeUtil.daysBetween(0L, -1L);        // 0
TimeUtil.daysBetween(86400000L, 0L);  // -1
```

#### `long addDays(long millis, int days)`

在毫秒时间戳上增加或减少整天。

- `millis`：起始毫秒。`days`：增加的天数，可以是负数或 0。
- 一天固定为 86400000 毫秒，不使用设备时区，也不处理夏令时。
- `days` 先提升为 long 再乘以一天的毫秒数，这个乘法不会溢出。
- 相加结果放不进 long 时抛出 `ArithmeticException`。

```java
TimeUtil.addDays(0L, 1);             // 86400000
TimeUtil.addDays(86400000L, -1);     // 0
TimeUtil.addDays(Long.MAX_VALUE, 1); // 抛出 ArithmeticException
```

#### `boolean isSameDay(long millisA, long millisB)`

判断两个毫秒时间戳是否属于同一个 UTC 日。

- 日界是 `floor(millis / 86400000)`，不是设备本地日历。
- 不是 Java 整数除法的向 0 截断。纪元前不足一天的时间不会和 1970-01-01 算成同一天。
- 不读取设备时区。

```java
TimeUtil.isSameDay(0L, 86399999L); // true
TimeUtil.isSameDay(0L, 86400000L); // false
TimeUtil.isSameDay(-1L, 0L);       // false
```

#### `long startOfUtcDay(long millis)`

返回该毫秒时间戳所在 UTC 日的起始毫秒。

- 日界与 `isSameDay` 相同，使用 `floor(millis / 86400000)`，不是设备时区。
- 结果是 86400000 的整数倍，并且和原时间戳属于同一个 UTC 日。
- `-1` 属于前一天，起始毫秒是 `-86400000`，不是 `0`。
- `Long.MIN_VALUE` 所在 UTC 日的起点小于 long 最小值，抛出 `ArithmeticException`。`Long.MAX_VALUE` 可以正常返回。

```java
TimeUtil.startOfUtcDay(0L);          // 0
TimeUtil.startOfUtcDay(86399999L);   // 0
TimeUtil.startOfUtcDay(86400000L);   // 86400000
TimeUtil.startOfUtcDay(-1L);         // -86400000
```

### WebUtil 新增

#### `boolean isHttpUrl(String url)`

判断文本是不是 http 或 https 地址。只检查方案，不打开页面，也不代替 `intentBrowserWeb`。

- `url`：允许为 null。null、空串返回 false。
- 方案名只对 ASCII 字母 `A-Z` 忽略大小写。必须以 `http://` 或 `https://` 开头。
- `://` 后面至少要有一个非空白字符。`http://`、`https://` 以及方案后只有空白时返回 false。
- 不 trim。开头有空白返回 false。
- `javascript:`、`file:`、`intent:`、`ftp:` 以及其他少斜杠写法返回 false。
- 不检查主机是否真实存在。

```java
WebUtil.isHttpUrl("https://example.com/a"); // true
WebUtil.isHttpUrl("HTTP://example.com");    // true
WebUtil.isHttpUrl(" http://example.com");   // false
WebUtil.isHttpUrl("javascript:alert(1)");   // false
WebUtil.isHttpUrl(null);                    // false
```

### DecimalUtil 新增

已发布的 `toBinaryString`、`binary2Decimal`、`scale2Decimal`、`checkNumber` 没有变化。

#### `String toHexString(int number)`

把 int 的 32 个比特写成 8 位小写十六进制，高位用 0 补齐。

- `number`：任意 int，按无符号比特解释，不输出正负号。
- 结果长度始终是 8。`-1` 得到 `ffffffff`，`Integer.MIN_VALUE` 得到 `80000000`。

```java
DecimalUtil.toHexString(0);                 // "00000000"
DecimalUtil.toHexString(0xABC);             // "00000abc"
DecimalUtil.toHexString(-1);                // "ffffffff"
DecimalUtil.toHexString(Integer.MIN_VALUE); // "80000000"
```

#### `int parseHex(String hex)`

把十六进制文本按无符号 32 位比特解析成 int。`toHexString` 的反向解析，不改变 `checkNumber`。

- `hex`：允许为 null。null、空串或只含空白时抛出 `IllegalArgumentException`。
- 可以带 `0x` 或 `0X` 前缀。前缀后必须是 1 到 8 位十六进制数字，字母不区分大小写。
- 超过 8 位就抛异常，多出来的前导 0 也不接受。不 trim 非空白文本，夹着空格或正负号也会抛异常。
- `ffffffff` 得到 -1，`80000000` 得到 `Integer.MIN_VALUE`。

```java
DecimalUtil.parseHex("0xABC");     // 2748
DecimalUtil.parseHex("ffffffff");  // -1
DecimalUtil.parseHex(DecimalUtil.toHexString(Integer.MIN_VALUE)); // Integer.MIN_VALUE
```

### 新类 Sha256Util

包名：`com.henry.common.utils.encryption`

SHA-256 摘要。这是单向散列，不是可逆加密。类不能实例化。不修改已发布的 `MD5Util`。`CommonUtils:1.0.2` 没有这个类。

#### `String digest(String text)`

计算文本的 SHA-256，返回 64 位小写十六进制。

- `text`：允许为 null。null 返回 `""`，不计算摘要。
- 空串会计算空字节的真实摘要，所以和 null 不同。
- 固定按 UTF-8 编码，不使用平台默认字符集。
- 运行环境缺少 SHA-256 时抛出 `IllegalStateException`。API 23 和 Java 8 都提供该算法。

```java
Sha256Util.digest(null); // ""
Sha256Util.digest("");   // "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
Sha256Util.digest("abc"); // "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"
```

#### `String digest(byte[] data)`

计算字节数组的 SHA-256，返回 64 位小写十六进制。

- `data`：允许为 null。null 返回 `""`。
- 空数组计算真实摘要，不返回空串。
- 缺少 SHA-256 时抛出 `IllegalStateException`。

```java
Sha256Util.digest((byte[]) null); // ""
Sha256Util.digest(new byte[0]);   // 与 Sha256Util.digest("") 相同
```

### 新类 FileUtil

包名：`com.henry.common.utils.file`

本地路径和 `java.io.File` 的辅助方法。类不能实例化，也不使用 Android API。不递归遍历目录。

#### `String getExtension(String pathOrName)`

取扩展名，不包含小数点。

- `pathOrName`：允许为 null。null、空串或只含空白时返回 `""`。空白规则与 `StringUtil.isBlank` 相同。其余文本不 trim。
- 只看最后一个 `/` 或 `\` 之后的文件名。目录名里的小数点不算扩展名。
- 没有小数点、小数点在末尾，或小数点是文件名第一个字符时返回 `""`。`.gitignore` 没有扩展名，`archive.tar.gz` 的扩展名是 `gz`。
- 不改变大小写。路径以分隔符结尾时返回 `""`。

```java
FileUtil.getExtension("archive.tar.gz"); // "gz"
FileUtil.getExtension("dir.old/file");   // ""
FileUtil.getExtension(".gitignore");     // ""
FileUtil.getExtension(null);             // ""
```

#### `String getNameWithoutExtension(String pathOrName)`

取最后一个路径分隔符之后的文件名，并去掉扩展名。

- 判定扩展名的规则与 `getExtension` 相同。
- null、空串或只含空白时返回 `""`。
- 没有扩展名时返回文件名本身，所以 `file.`、`.gitignore` 和 `..` 都原样保留。
- 路径以分隔符结尾时返回 `""`。

```java
FileUtil.getNameWithoutExtension("/path/to/archive.tar.gz"); // "archive.tar"
FileUtil.getNameWithoutExtension(".gitignore");              // ".gitignore"
FileUtil.getNameWithoutExtension("file.");                   // "file."
```

#### `boolean isEmpty(File file)`

判断是不是空的普通文件。

- `file`：允许为 null。null、路径不存在，或普通文件长度为 0 时返回 true。
- 目录返回 false，即使目录里没有子项。已存在但既不是文件也不是目录的路径也返回 false。

```java
FileUtil.isEmpty(null);                         // true
FileUtil.isEmpty(new File("missing.txt"));      // 文件不存在时为 true
FileUtil.isEmpty(new File("."));                // 目录为 false
```

#### `long size(File file)`

返回普通文件的字节长度。

- `file`：允许为 null。null、不存在的路径、目录，以及其他非普通文件都返回 0。
- 不递归统计目录中的文件。

```java
FileUtil.size(null); // 0
```

#### `boolean deleteQuietly(File file)`

删除一个文件或空目录。

- `file`：允许为 null。null 或不存在时返回 false。
- 只调用 `File.delete()`，不递归删除。非空目录删除失败并返回 false，目录和里面的文件都还在。
- `SecurityException` 时返回 false，不向外抛出。

```java
FileUtil.deleteQuietly(null); // false
```
