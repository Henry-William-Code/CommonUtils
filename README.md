# CommonUtils

Android 里可直接静态调用的 Java 工具库。源码语言是 Java 8，库模块 `minSdkVersion` 为 23。

工具按类组织。当前发布版本是 `1.0.6`。`1.0.2` 有 8 个工具类，放在 6 个包里，并不是 8 个包。`calculate` 包同时包含 `DecimalUtil` 和 `MathematicsUtil`。`1.0.3` 增加了 `CollectionUtil`、`ConvertUtil`、`Sha256Util` 和 `FileUtil`。`1.0.4` 再增加 `IoUtil` 和 `ObjectUtil`。`1.0.5` 增加 `HexUtil` 和 `ArrayUtil`。`1.0.6` 增加 `GzipUtil`、`UrlCodecUtil`、`RegexUtil` 和 `Crc32Util`，并给已有类追加了方法。当前 `main` 上还有一批尚未发布的方法，见文末「main 上尚未发布」，不能通过 `CommonUtils:1.0.6` 调用。

## 版本说明

| 范围 | 怎么用 | 包含什么 |
| --- | --- | --- |
| 已发布 `1.0.2` | JitPack `CommonUtils:1.0.2` | 只包含「已发布 1.0.2 的工具类」一节里的方法 |
| 已发布 `1.0.3` | JitPack `CommonUtils:1.0.3` | 包含 `1.0.2` 的全部方法，以及「1.0.3 新增」一节 |
| 已发布 `1.0.4` | JitPack `CommonUtils:1.0.4` | 包含 `1.0.3` 的全部方法，以及「1.0.4 新增」一节 |
| 已发布 `1.0.5` | JitPack `CommonUtils:1.0.5` | 包含 `1.0.4` 的全部方法，以及「1.0.5 新增」一节 |
| 已发布 `1.0.6` | JitPack `CommonUtils:1.0.6` | 包含 `1.0.5` 的全部方法，以及「1.0.6 新增」一节 |
| `main` 上尚未发布 | 不能用 `1.0.6` 调用 | 只包含文末「main 上尚未发布」一节 |

`1.0.1` 的类还在 `com.henry.common.utils` 根包下。`1.0.2` 起改为下面的子包。`1.0.1` 与 `1.0.2` 的类名位置不同，不能混用。`1.0.3` 至 `1.0.6` 沿用该包结构，没有修改此前已发布方法的签名和返回值。

## 依赖 1.0.6

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
    implementation 'com.github.Henry-William-Code:CommonUtils:1.0.6'
}
```

`1.0.6` 包含到「1.0.6 新增」为止的全部已发布方法。仍使用 `1.0.5` 的工程不能调用「1.0.6 新增」。`main` 上尚未发布的方法也不在 `1.0.6` 里。

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

## 1.0.3 新增

下面的类和方法从 `1.0.3` 起可以通过 JitPack 使用。`1.0.2` 没有它们。`1.0.2` 已有方法的签名和返回值没有改。

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

#### `<T> T firstOrNull(List<T> list)`

返回列表的第一个元素。

- `list`：允许为 null。null 或空列表返回 null。
- 第一个元素本身是 null 时也返回 null，无法和空列表区分。

```java
CollectionUtil.firstOrNull(Arrays.asList("a", "b")); // "a"
CollectionUtil.firstOrNull(Collections.emptyList()); // null
```

#### `<T> T lastOrNull(List<T> list)`

返回列表的最后一个元素。

- `list`：允许为 null。null 或空列表返回 null。
- 最后一个元素本身是 null 时也返回 null，无法和空列表区分。

```java
CollectionUtil.lastOrNull(Arrays.asList("a", "b")); // "b"
CollectionUtil.lastOrNull(null);                    // null
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

#### `long endOfUtcDay(long millis)`

返回该毫秒时间戳所在 UTC 日的最后一毫秒。

- 日界与 `startOfUtcDay` 相同，结果是该日起点加 86399999 毫秒。
- 不使用设备时区。`-1` 的终点是 `-1`，不是 `86399999`。
- `Long.MIN_VALUE` 的日起点无法表示，`Long.MAX_VALUE` 所在日的终点大于 long 最大值，这两种情况都抛出 `ArithmeticException`。

```java
TimeUtil.endOfUtcDay(0L);         // 86399999
TimeUtil.endOfUtcDay(86400000L);  // 172799999
TimeUtil.endOfUtcDay(-1L);        // -1
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

### 新类 ConvertUtil

包名：`com.henry.common.utils.convert`

把十进制文本安全地解析成整数。失败时返回调用方给出的默认值，不抛出解析异常。类不能实例化。不代替会抛异常的 `DecimalUtil.checkNumber`，也不改 `DecimalUtil`。这个类从 `1.0.3` 开始提供。

#### `int parseInt(String text, int defaultValue)`

把十进制文本解析成 int。

- `text`：允许为 null。null、空串、只含空白、非法格式或超出 int 范围时返回 `defaultValue`。
- 空白规则与 `StringUtil.isBlank` 相同。非空白文本会去掉首尾空白，中间空格不接受。
- 允许 ASCII 的 `+` 和 `-`。不接受小数点、千分位、`0x` 前缀或全角数字。
- 默认值可以是任意 int。文本无效时如果默认值碰巧等于某个合法数字，调用方无法从返回值看出用的是默认值。

```java
ConvertUtil.parseInt("  +12 ", -1); // 12
ConvertUtil.parseInt(null, 9);      // 9
ConvertUtil.parseInt("2147483648", 9); // 9
```

#### `long parseLong(String text, long defaultValue)`

把十进制文本解析成 long。空白、正负号和非法格式与 `parseInt` 相同。

- 超出 long 范围时返回 `defaultValue`，不抛异常。
- `9223372036854775807` 得到 `Long.MAX_VALUE`，`-9223372036854775808` 得到 `Long.MIN_VALUE`。
- 再多一位就返回默认值。

```java
ConvertUtil.parseLong("-9223372036854775808", 0L); // Long.MIN_VALUE
ConvertUtil.parseLong("12a", 3L);                  // 3
```

### 新类 Sha256Util

包名：`com.henry.common.utils.encryption`

SHA-256 摘要。这是单向散列，不是可逆加密。类不能实例化。不修改 `MD5Util`。这个类从 `1.0.3` 开始提供。

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

## 1.0.4 新增

下面的类和方法从 `1.0.4` 起可以通过 JitPack 使用。`1.0.3` 没有它们。`1.0.3` 已有方法的签名和返回值没有改。

### 新类 IoUtil

包名：`com.henry.common.utils.io`

关闭流，以及在字节上限内读取输入流。类不能实例化，也不使用 Android API。

#### `void closeQuietly(Closeable closeable)`

关闭对象，并吞掉 `IOException`。

- `closeable`：允许为 null。null 时直接返回。
- 只捕获 `IOException`。其他运行时异常仍向外抛出。
- 不判断对象是否已经关闭。

```java
IoUtil.closeQuietly(null);
IoUtil.closeQuietly(inputStream);
```

#### `byte[] readBytes(InputStream input, int maxBytes)`

读取输入流的全部字节，但不超过 `maxBytes`。

- `input`：不能为 null。null 时抛出 `IllegalArgumentException`。
- `maxBytes`：小于 0 时抛出 `IllegalArgumentException`。
- 实际字节数大于 `maxBytes` 时抛出 `IllegalArgumentException`，不返回截断内容。
- 流里没有字节时返回长度为 0 的数组。`maxBytes` 为 0 且流为空时同样返回空数组。
- 不关闭 `input`。读取失败时抛出 `IOException`。

```java
byte[] data = IoUtil.readBytes(input, 1024);
```

#### `String readUtf8(InputStream input, int maxBytes)`

按 UTF-8 读取文本。字节上限和拒绝规则与 `readBytes` 相同。

- 非法 UTF-8 字节替换为 `U+FFFD`，不因此抛异常。
- 不关闭 `input`。

```java
String text = IoUtil.readUtf8(input, 1024);
```

### TimeUtil 新增

已发布的 `addDays` 没有变化。

#### `long addHours(long millis, int hours)`

在毫秒时间戳上增加或减少整小时。

- `millis`：起始毫秒。`hours`：增加的小时数，可以是负数或 0。
- 一小时固定为 3600000 毫秒，不使用设备时区，也不处理夏令时。
- `hours` 先提升为 long 再乘以一小时的毫秒数，这个乘法不会溢出。
- 相加结果放不进 long 时抛出 `ArithmeticException`。

```java
TimeUtil.addHours(0L, 1);              // 3600000
TimeUtil.addHours(3600000L, -1);       // 0
TimeUtil.addHours(Long.MAX_VALUE, 1);  // 抛出 ArithmeticException
```

### ConvertUtil 新增

已发布的 `parseInt` 和 `parseLong` 没有变化。

#### `boolean parseBoolean(String text, boolean defaultValue)`

把文本解析成布尔值。

- `text`：允许为 null。null、空串、只含空白，或不是 true / false 时返回 `defaultValue`，不抛异常。
- 去掉首尾空白后，只接受 ASCII 大小写不敏感的 `true` 和 `false`。
- 不接受 `1`、`0`、`yes`、`no` 或全角字母。中间有空白时返回默认值。
- 文本无效时，无法从返回值区分“解析成功”和“使用了默认值”。

```java
ConvertUtil.parseBoolean(" TRUE ", false); // true
ConvertUtil.parseBoolean("no", true);      // true，因为 "no" 不是 false
ConvertUtil.parseBoolean(null, false);     // false
```

### FileUtil 新增

已发布的 `isEmpty` 没有变化。

#### `boolean isDirectory(File file)`

判断路径是不是已存在的目录。

- `file`：允许为 null。null、不存在，或存在但不是目录时返回 false。
- 普通文件返回 false。
- 空目录返回 true。这和 `isEmpty` 不同：空目录在 `isEmpty` 中返回 false。

```java
FileUtil.isDirectory(null);          // false
FileUtil.isDirectory(new File(".")); // 当前目录存在时为 true
```

### 新类 ObjectUtil

包名：`com.henry.common.utils.object`

对象的 null 安全判断。类不能实例化，也不使用 Android API。字符串内容比较仍用 `StringUtil.equals`。这个类从 `1.0.4` 开始提供。

#### `<T> T defaultIfNull(T value, T defaultValue)`

value 为 null 时返回默认值。

- `value`、`defaultValue` 都允许为 null。
- value 不是 null 时返回 value，即使它是空串。
- 默认值本身是 null 时，value 为 null 也会返回 null，无法区分“原值就是 null”和“使用了默认值”。

```java
ObjectUtil.defaultIfNull(null, "b"); // "b"
ObjectUtil.defaultIfNull("a", "b");  // "a"
ObjectUtil.defaultIfNull(null, null); // null
```

#### `boolean equals(Object left, Object right)`

按 `Object.equals` 做 null 安全比较。

- 两者都为 null 时返回 true；只有一方为 null 时返回 false。
- 不比较数组内容。两个不同的数组对象即使元素相同也返回 false。
- 字符串内容相同但大小写不同返回 false。这和 `StringUtil.equals` 对字符串的结果一致，但本方法接受任意对象。

```java
ObjectUtil.equals(null, null);              // true
ObjectUtil.equals("a", new String("a"));    // true
ObjectUtil.equals(new String[]{"a"}, new String[]{"a"}); // false
```

#### `<T> T firstNonNull(T... values)`

返回第一个不是 null 的参数。

- `values` 数组本身允许为 null，此时返回 null。
- 没有参数，或全部元素都是 null 时返回 null。
- 空串不是 null，会作为结果返回。不调用元素的 equals。

```java
ObjectUtil.firstNonNull(null, "b", "c"); // "b"
ObjectUtil.firstNonNull();               // null
ObjectUtil.firstNonNull("", "b");        // ""
```

### TimeUtil 新增

同属 `1.0.4` 的 `addHours` 没有变化。

#### `long addMinutes(long millis, int minutes)`

在毫秒时间戳上增加或减少整分钟。

- `millis`：起始毫秒。`minutes`：增加的分钟数，可以是负数或 0。
- 一分钟固定为 60000 毫秒，不使用设备时区，也不处理夏令时。
- `minutes` 先提升为 long 再乘以一分钟的毫秒数，这个乘法不会溢出。
- 相加结果放不进 long 时抛出 `ArithmeticException`。

```java
TimeUtil.addMinutes(0L, 1);             // 60000
TimeUtil.addMinutes(60000L, -1);        // 0
TimeUtil.addMinutes(Long.MAX_VALUE, 1); // 抛出 ArithmeticException
```

### FileUtil 新增

同属 `1.0.4` 的 `isDirectory` 没有变化。

#### `boolean isFile(File file)`

判断路径是不是已存在的普通文件。

- `file`：允许为 null。null、不存在，或存在但不是普通文件时返回 false。
- 目录返回 false。
- 不存在的路径在这里返回 false，在 `isEmpty` 中返回 true。

```java
FileUtil.isFile(null); // false
```

#### `boolean exists(File file)`

判断路径是否存在。

- `file`：允许为 null。null 或不存在时返回 false。
- 普通文件和目录存在时都返回 true。
- 不读取内容，也不区分文件和目录。

```java
FileUtil.exists(null); // false
```

## 1.0.5 新增

下面的类和方法从 `1.0.5` 起可以通过 JitPack 使用。`1.0.4` 没有它们。`1.0.4` 已有方法的签名和返回值没有改。

### 新类 HexUtil

包名：`com.henry.common.utils.hex`

字节数组和十六进制文本的互相转换。类不能实例化，也不使用 Android API。这不是 `DecimalUtil.parseHex` 那种 int 进制转换，也不改 `Sha256Util` 或 `MD5Util`。

#### `String encode(byte[] data)`

把字节数组编成小写十六进制。每个字节两个字符，不带 `0x` 前缀。

- `data`：允许为 null。null 或空数组返回 `""`。
- `-1` 这个字节写成 `ff`，不是带符号的十进制。

```java
HexUtil.encode(null);                         // ""
HexUtil.encode(new byte[] {0x0f, (byte) 0xff}); // "0fff"
```

#### `byte[] decode(String hex)`

把十六进制文本解析成字节数组。

- `hex`：允许为 null。null、空串或只含空白时返回长度为 0 的数组。空白规则与 `StringUtil.isBlank` 相同。
- 字母不区分大小写。不接受 `0x` 或 `0X` 前缀，也不去掉非空白文本里的空格或换行。
- 长度为奇数、含非法字符，或非空白文本中夹着空白时抛出 `IllegalArgumentException`，不返回截断结果。

```java
HexUtil.decode(null);   // 空数组
HexUtil.decode("AB");   // new byte[] {(byte) 0xab}
HexUtil.decode("0x10"); // 抛出 IllegalArgumentException
```

### TimeUtil 新增

已发布的 `addMinutes` 没有变化。

#### `long addSeconds(long millis, int seconds)`

在毫秒时间戳上增加或减少整秒。

- `millis`：起始毫秒。`seconds`：增加的秒数，可以是负数或 0。
- 一秒固定为 1000 毫秒，不使用设备时区，也不处理夏令时。
- `seconds` 先提升为 long 再乘以一秒的毫秒数，这个乘法不会溢出。
- 相加结果放不进 long 时抛出 `ArithmeticException`。

```java
TimeUtil.addSeconds(0L, 1);             // 1000
TimeUtil.addSeconds(1000L, -1);         // 0
TimeUtil.addSeconds(Long.MAX_VALUE, 1); // 抛出 ArithmeticException
```

### CollectionUtil 新增

#### `boolean contains(Collection<?> data, Object value)`

判断集合是否包含指定元素。

- `data`：允许为 null。null 时返回 false，不抛异常。
- `value`：允许为 null。集合里有 null 元素时，查找 null 返回 true。
- 其余情况调用 `data.contains(value)`，不吞掉集合自己抛出的异常。

```java
CollectionUtil.contains(null, "a");                         // false
CollectionUtil.contains(Arrays.asList("a", null), null);    // true
```

### FileUtil 新增

已发布的 `getExtension` 和 `getNameWithoutExtension` 没有变化。

#### `String getName(String pathOrName)`

取最后一个 `/` 或 `\` 之后的文件名，保留扩展名。

- `pathOrName`：允许为 null。null、空串或只含空白时返回 `""`。空白规则与 `getExtension` 相同，其余文本不 trim。
- 路径以分隔符结尾时返回 `""`。没有分隔符时返回原文本。
- `.gitignore` 和 `file.` 都原样保留，不去掉扩展名。

```java
FileUtil.getName("/path/to/archive.tar.gz"); // "archive.tar.gz"
FileUtil.getName("foo/bar/");                // ""
FileUtil.getName(null);                      // ""
```

### 新类 ArrayUtil

包名：`com.henry.common.utils.array`

数组查找。类不能实例化，也不使用 Android API。对象数组是否为空仍用 `CollectionUtil.isEmpty(Object[])`，本类不提供这个方法，也不改 `CollectionUtil`。

#### `boolean isEmpty(int[] data)`

判断 int 数组是否没有元素。

- `data`：允许为 null。null 或长度为 0 时返回 true。

```java
ArrayUtil.isEmpty(null);        // true
ArrayUtil.isEmpty(new int[]{0}); // false
```

#### `int indexOf(Object[] data, Object value)`

查找对象数组中第一个匹配元素的下标。

- `data`：允许为 null。null 或没有匹配时返回 -1。
- `value`：允许为 null。元素和 value 都是 null 时算匹配。
- 使用 `Object.equals`。元素本身是数组时只按引用相等，不比较数组内容。

```java
ArrayUtil.indexOf(new Object[]{null, "b"}, "b"); // 1
ArrayUtil.indexOf(null, "a");                    // -1
```

#### `boolean contains(Object[] data, Object value)`

判断对象数组是否包含指定元素。规则与上面的 `indexOf` 相同，`indexOf >= 0` 时返回 true。

```java
ArrayUtil.contains(new Object[]{null, "b"}, null); // true
```

#### `int indexOf(int[] data, int value)`

查找 int 数组中第一个等于 value 的下标。`data` 为 null 或没有匹配时返回 -1。

```java
ArrayUtil.indexOf(new int[]{1, 3}, 3); // 1
ArrayUtil.indexOf((int[]) null, 1);    // -1
```

#### `boolean contains(int[] data, int value)`

判断 int 数组是否包含指定值。`data` 为 null 时返回 false。

```java
ArrayUtil.contains(new int[]{0, -1}, -1); // true
```

### ConvertUtil 新增

已发布的 `parseInt`、`parseLong` 和 `parseBoolean` 没有变化。

#### `double parseDouble(String text, double defaultValue)`

把十进制文本解析成有限的 double。

- `text`：允许为 null。null、空串、只含空白、非法格式，或结果为 NaN、无穷大时返回 `defaultValue`，不抛异常。
- 空白规则和去掉首尾空白的方式与 `parseInt` 相同。中间空白不接受。
- 允许 ASCII 正负号、小数点和标准十进制指数，例如 `1.5e2`。
- 不接受千分位、全角数字、逗号小数，也不接受 `0x` 十六进制浮点。
- 文本本身是 NaN 或 Infinity，以及大数溢出成无穷大，都返回默认值。下溢成 0 的有限结果返回 0。

```java
ConvertUtil.parseDouble("1.5e2", -1); // 150
ConvertUtil.parseDouble("NaN", 9);    // 9
ConvertUtil.parseDouble(null, 9);     // 9
```

### IoUtil 新增

已发布的 `closeQuietly`、`readBytes` 和 `readUtf8` 没有变化。

#### `int copy(InputStream input, OutputStream output, int maxBytes)`

在字节上限内把输入流全部写入输出流，返回实际拷贝的字节数。

- `input`、`output`：都不能为 null。任一为 null，或 `maxBytes` 小于 0 时抛出 `IllegalArgumentException`。
- 先完整读入。实际字节数超过 `maxBytes` 时抛出 `IllegalArgumentException`，不会把截断内容写入 `output`。
- 返回值是 `int`，范围是 0 到 `maxBytes`。
- 不关闭两个流。读写失败时抛出 `IOException`。

```java
int copied = IoUtil.copy(input, output, 1024);
```

### FileUtil 新增

同属 `1.0.5` 的 `getName` 没有变化。

#### `String getParent(String pathOrName)`

取父路径。不访问磁盘，也不解析 `.` 和 `..`。

- `pathOrName`：允许为 null。null、空串或只含空白时返回 `""`。空白规则与 `getExtension` 相同，其余文本不 trim。
- 按最后一个 `/` 或 `\` 分割。没有分隔符时返回 `""`。
- 路径以分隔符结尾时，先去掉这一层目录，再取上一层：`foo/bar/` 得到 `foo`。
- 只有根分隔符 `/` 或 `\` 时返回 `""`。`/path/to/file.txt` 得到 `/path/to`。

```java
FileUtil.getParent("/path/to/file.txt"); // "/path/to"
FileUtil.getParent("foo/bar/");          // "foo"
FileUtil.getParent("file.txt");          // ""
```

## 1.0.6 新增

下面的类和方法从 `1.0.6` 起可以通过 JitPack 使用。`1.0.5` 没有它们。`1.0.5` 已有方法的签名和返回值没有改。

### 新类 GzipUtil

包名：`com.henry.common.utils.compress`

用 JDK 的 `java.util.zip` 做 gzip 压缩和解压。类不能实例化，也不使用 Android API。

#### `byte[] compress(byte[] data)`

把字节数组压成 gzip。

- `data`：允许为 null。null 时返回长度为 0 的数组，不做压缩。
- 空数组会压成合法的 gzip 字节，不是空数组。解压后仍是空数组。
- 不修改入参数组的内容。

```java
GzipUtil.compress(null);          // 空数组
GzipUtil.compress(new byte[0]);   // 合法 gzip，解压后为空数组
```

#### `byte[] decompress(byte[] data, int maxBytes)`

解压 gzip，结果不超过 `maxBytes`。

- `data`：不能为 null。null，或 `maxBytes` 小于 0 时抛出 `IllegalArgumentException`。
- `data` 长度为 0，或不是合法 gzip 时抛出 `IllegalArgumentException`，原因放在 cause 里。
- 解压后字节数大于 `maxBytes` 时抛出 `IllegalArgumentException`，不返回截断内容。
- 解压结果可以是空数组。`maxBytes` 为 0 时，只有解压结果也为空才允许。

```java
byte[] gzip = GzipUtil.compress(new byte[] {1, 2, 3});
GzipUtil.decompress(gzip, 3); // new byte[] {1, 2, 3}
```

### 新类 UrlCodecUtil

包名：`com.henry.common.utils.codec`

UTF-8 的 `application/x-www-form-urlencoded` 编解码。类不能实例化。不修改 `WebUtil`。使用 `URLEncoder` 和 `URLDecoder`，不另做一套百分号规则。

#### `String encode(String text)`

按表单规则编码。

- `text`：允许为 null。null 或空串返回 `""`。
- 空格编成 `+`。UTF-8 不可用时抛出 `IllegalStateException`。

```java
UrlCodecUtil.encode(null);  // ""
UrlCodecUtil.encode("a b"); // "a+b"
```

#### `String decode(String text)`

按表单规则解码。

- `text`：允许为 null。null 或空串返回 `""`。
- `+` 解成空格。
- 非法百分号序列，例如末尾单独的 `%`、不完整的 `%A` 或非十六进制，抛出 `IllegalArgumentException`，不返回截断结果。
- 对原文而言，`encode` 与 `decode` 互为逆运算。

```java
UrlCodecUtil.decode("a+b"); // "a b"
UrlCodecUtil.decode(UrlCodecUtil.encode("你")); // "你"
```

### StringUtil 新增

已发布的 `equals` 和 `isBlank` 没有变化。

#### `boolean equalsIgnoreCase(String left, String right)`

按 ASCII 大小写不敏感的方式比较两个字符串。

- 两者都为 null 时返回 true；只有一方为 null 时返回 false。
- 不 trim。只把 `A-Z` 看成与 `a-z` 相同，不处理 Unicode 大小写。
- 已有的 `equals` 仍然区分大小写。

```java
StringUtil.equalsIgnoreCase("AbC", "aBc"); // true
StringUtil.equalsIgnoreCase(null, null);   // true
StringUtil.equals("A", "a");                // false
```

#### `String defaultIfBlank(String text, String defaultValue)`

text 为空白时返回默认值，否则原样返回 text。

- 空白规则与 `isBlank` 相同。`defaultValue` 允许为 null。
- 非空白文本不去掉首尾空白。

```java
StringUtil.defaultIfBlank("  ", "fallback"); // "fallback"
StringUtil.defaultIfBlank(" a ", "fallback"); // " a "
```

### TimeUtil 新增

已发布的 `daysBetween` 没有变化。

#### `long hoursBetween(long startMillis, long endMillis)`

计算相差的整小时数。

- 一小时固定为 3600000 毫秒，整数除法向 0 截断，不使用设备时区。
- 结束早于开始时为负数。差值放不进 long 时抛出 `ArithmeticException`。

```java
TimeUtil.hoursBetween(0L, 3600000L); // 1
TimeUtil.hoursBetween(0L, 3599999L); // 0
```

#### `long minutesBetween(long startMillis, long endMillis)`

计算相差的整分钟数。规则与 `hoursBetween` 相同，一分钟固定为 60000 毫秒。

```java
TimeUtil.minutesBetween(0L, 60000L); // 1
TimeUtil.minutesBetween(60000L, 0L); // -1
```

### CollectionUtil 新增

已有的 `isEmpty(Object[])` 没有变化，也没有改 `ArrayUtil`。

#### `int size(Object[] data)`

返回数组元素个数。

- `data`：允许为 null。null 时返回 0，否则返回 `data.length`。

```java
CollectionUtil.size((Object[]) null);      // 0
CollectionUtil.size(new Object[] {null});  // 1
```

### MathematicsUtil 新增

已发布的 `clamp` 没有变化。

#### `boolean isInRange(int value, int min, int max)`

判断整数是否落在闭区间内。

- `min > max` 时抛出 `IllegalArgumentException`，不互换边界。
- `value < min` 或 `value > max` 返回 false，否则返回 true。

```java
MathematicsUtil.isInRange(0, 0, 10);  // true
MathematicsUtil.isInRange(11, 0, 10); // false
```

### ObjectUtil 新增

#### `String toStringOrEmpty(Object value)`

把对象转成文本。

- `value`：允许为 null。null 时返回 `""`，否则返回 `String.valueOf(value)`。
- 不做数组内容的深度格式化。

```java
ObjectUtil.toStringOrEmpty(null); // ""
ObjectUtil.toStringOrEmpty(12);   // "12"
```

### 新类 RegexUtil

包名：`com.henry.common.utils.regex`

正则匹配。类不能实例化，也不使用 Android API。`CommonUtils:1.0.5` 没有这个类。

#### `boolean matches(String text, String regex)`

判断整个文本是否匹配正则。

- `text` 或 `regex` 为 null 时返回 false，不编译正则。
- 空正则是合法模式，只匹配空串。
- 正则语法错误时抛出 `PatternSyntaxException`，它是 `IllegalArgumentException` 的子类。

```java
RegexUtil.matches("abc", "a.c"); // true
RegexUtil.matches(null, "a");    // false
```

#### `String findFirst(String text, String regex)`

返回第一处匹配到的文本。

- `text` 或 `regex` 为 null，或没有匹配时返回 null。
- 匹配结果本身可以是空串，这和没有匹配不同。
- 正则语法错误时抛出 `PatternSyntaxException`。

```java
RegexUtil.findFirst("a12b", "\\d+"); // "12"
RegexUtil.findFirst("abc", "z");     // null
```

### 新类 Crc32Util

包名：`com.henry.common.utils.checksum`

用 JDK 的 `java.util.zip.CRC32` 计算 CRC-32。类不能实例化。

#### `long checksum(byte[] data)`

计算字节数组的 CRC-32。

- `data`：允许为 null。null 或空数组返回 0，因此无法从返回值区分这两种输入。
- 结果是无符号 32 位值，放在 long 的低 32 位。
- 不修改入参数组。

```java
Crc32Util.checksum(null); // 0
Crc32Util.checksum("123456789".getBytes(java.nio.charset.StandardCharsets.US_ASCII)); // 3421780262
```

#### `long checksumUtf8(String text)`

按 UTF-8 计算文本的 CRC-32。

- `text`：允许为 null。null 或空串返回 0。
- 其余规则与 `checksum(byte[])` 相同。

```java
Crc32Util.checksumUtf8("123456789"); // 3421780262
Crc32Util.checksumUtf8(null);        // 0
```

### ArrayUtil 新增

已有的 `indexOf(int[], int)` 仍返回第一处匹配。

#### `int lastIndexOf(int[] data, int value)`

返回最后一处匹配的下标。

- `data`：允许为 null。null 或没有匹配时返回 -1。

```java
ArrayUtil.lastIndexOf(new int[] {1, 3, 1}, 1); // 2
ArrayUtil.lastIndexOf(null, 1);                // -1
```

### IoUtil 新增

已有的 `copy` 没有变化。

#### `void writeBytes(OutputStream output, byte[] data)`

把字节数组写入输出流。

- `output`：不能为 null。null 时抛出 `IllegalArgumentException`。
- `data`：允许为 null。null 或空数组时不写入任何字节。
- 不关闭输出流，也不修改入参数组。写入失败时抛出 `IOException`。

```java
IoUtil.writeBytes(output, new byte[] {1, 2});
```

### ConvertUtil 新增

已有的 `parseDouble` 没有变化。

#### `float parseFloat(String text, float defaultValue)`

把十进制文本解析成有限的 float。

- 空白、正负号、指数和拒绝规则与 `parseDouble` 相同。
- 结果为 NaN、无穷大，或超出 float 范围而变成无穷大时返回 `defaultValue`，不抛异常。

```java
ConvertUtil.parseFloat("1.5e2", -1f); // 150
ConvertUtil.parseFloat("NaN", -1f);   // -1
```

### FileUtil 新增

已有的 `getParent` 没有变化。

#### `String join(String parent, String child)`

用一个 `/` 连接父路径和子路径。不访问磁盘，也不解析 `.` 和 `..`。

- null 或只含空白的一侧按空串处理，空白规则与 `getExtension` 相同。
- 去掉父路径末尾和子路径开头的 `/`、`\` 后再连接。
- 两侧都空时返回空串。只有一侧有内容时返回那一侧。
- 只由分隔符组成的路径去掉分隔符后为空，因此 `join("/", "bar")` 得到 `bar`，不保留根斜杠。

```java
FileUtil.join("foo/", "/bar"); // "foo/bar"
FileUtil.join(null, "bar");    // "bar"
FileUtil.join("/", "bar");     // "bar"
```

### TimeUtil 新增

已有的 `minutesBetween` 没有变化。

#### `long secondsBetween(long startMillis, long endMillis)`

计算相差的整秒数。

- 一秒固定为 1000 毫秒，整数除法向 0 截断，不使用设备时区。
- 结束早于开始时为负数。差值放不进 long 时抛出 `ArithmeticException`。

```java
TimeUtil.secondsBetween(0L, 1000L); // 1
TimeUtil.secondsBetween(0L, 999L);  // 0
```

### MathematicsUtil 新增

已有的 int 版 `isInRange` 没有变化。

#### `boolean isInRange(long value, long min, long max)`

判断 long 是否落在闭区间内。

- `min > max` 时抛出 `IllegalArgumentException`，不互换边界。
- `value < min` 或 `value > max` 返回 false，否则返回 true。

```java
MathematicsUtil.isInRange(5L, 0L, 10L); // true
MathematicsUtil.isInRange(11L, 0L, 10L); // false
```

### ObjectUtil 新增

#### `int hashCode(Object value)`

返回对象的哈希码。

- `value`：允许为 null。null 时返回 0，否则返回 `value.hashCode()`。
- 数组不计算元素内容。

```java
ObjectUtil.hashCode(null); // 0
ObjectUtil.hashCode("a");  // 与 "a".hashCode() 相同
```

## main 上尚未发布

下面这些方法只在当前 `main` 源码里。JitPack `CommonUtils:1.0.6` 不包含它们。这一批还没有打 tag，也没有发布新版本。

### 新类 UuidUtil

包名 `com.henry.common.utils.uuid`。生成和校验标准 UUID 文本。

#### `String random()`

生成随机 UUID 的标准文本。

- 固定 36 个字符，小写十六进制，连字符位置是 8-4-4-4-12。
- 每次调用生成一个新的值。

```java
String id = UuidUtil.random();
UuidUtil.isValid(id); // true
```

#### `boolean isValid(String text)`

判断文本是不是标准 UUID。

- `text`：允许为 null。null、空串、缺少连字符或长度不对时返回 false，不抛异常。
- 必须带连字符，分段长度是 8-4-4-4-12。十六进制不区分大小写。
- 不接受去掉连字符的 32 位文本。

```java
UuidUtil.isValid("550e8400-e29b-41d4-a716-446655440000"); // true
UuidUtil.isValid("550E8400-E29B-41D4-A716-446655440000"); // true
UuidUtil.isValid(null); // false
```

### 新类 VersionUtil

包名 `com.henry.common.utils.version`。比较点号数字版本，例如 `1.0.10` 和 `1.0.2`。

#### `int compare(String left, String right)`

按点号分段做数值比较。

- 缺少的段按 0 处理，因此 `1.0` 等于 `1.0.0`。`1.0.10` 大于 `1.0.2`。
- 前导零不改变数值。不 trim 非空白文本。
- null、空串、只含空白、空段、非数字，或某一段超出 long 范围时抛出 `IllegalArgumentException`。
- 不接受正负号、字母前缀或空格。
- 左侧较小返回负数，相等返回 0，左侧较大返回正数。

```java
VersionUtil.compare("1.0.10", "1.0.2"); // 大于 0
VersionUtil.compare("1.0", "1.0.0");    // 0
```

### StringUtil 新增

已有的 `isBlank`、`defaultIfBlank` 没有变化。

#### `String trimToEmpty(String text)`

去掉首尾空白；null 时返回空串。

- `text`：允许为 null。null 时返回 `""`。
- 空白规则与 `isBlank` 相同，使用 `Character.isWhitespace`。不间断空格 `\u00A0` 会保留。
- 中间的空白不删除。首尾本来就没有空白时返回原字符串。

```java
StringUtil.trimToEmpty(null);       // ""
StringUtil.trimToEmpty(" a b\t");    // "a b"
StringUtil.trimToEmpty("\u00A0");    // "\u00A0"
```

### ArrayUtil 新增

已有的 `lastIndexOf(int[], int)` 没有变化。

#### `int lastIndexOf(Object[] data, Object value)`

查找对象数组中最后一个匹配元素的下标。

- `data`：允许为 null。null 或没有匹配时返回 -1。
- `value`：允许为 null。元素和 value 都为 null 时算匹配。
- 比较使用 `Object.equals`。元素本身是数组时只按引用相等，不比较数组内容。

```java
ArrayUtil.lastIndexOf(new Object[] {"b", null, "b"}, "b"); // 2
ArrayUtil.lastIndexOf(new Object[] {"a", null}, null);     // 1
```

### IoUtil 新增

已有的 `readUtf8`、`writeBytes` 没有变化。

#### `void writeUtf8(OutputStream output, String text)`

按 UTF-8 把文本写入输出流。

- `output`：不能为 null，否则抛出 `IllegalArgumentException`。
- `text`：允许为 null。null 或空串时不写入任何字节。
- 不关闭 `output`。写入失败时抛出 `IOException`。

```java
ByteArrayOutputStream output = new ByteArrayOutputStream();
IoUtil.writeUtf8(output, "中文");
```

### FileUtil 新增

已有的 `exists` 没有变化。

#### `boolean isAbsolute(String pathOrName)`

判断路径文本是不是以分隔符开头。

- `pathOrName`：允许为 null。null、空串或只含空白时返回 false。
- 不 trim。第一个字符是 `/` 或 `\` 时返回 true。`" /tmp"` 返回 false。
- 不访问磁盘，也不解析 `.` 和 `..`。

```java
FileUtil.isAbsolute("/tmp/a"); // true
FileUtil.isAbsolute("a/b");    // false
```

### TimeUtil 新增

已有的 `addSeconds` 没有变化。

#### `long addMillis(long millis, long delta)`

在毫秒时间戳上增加或减少任意毫秒。

- `delta` 可以为负数或 0。不使用设备时区，也不把毫秒换成秒。
- 相加的结果无法用 long 表示时抛出 `ArithmeticException`。

```java
TimeUtil.addMillis(0L, 1L);  // 1
TimeUtil.addMillis(5L, -1L); // 4
```

### MathematicsUtil 新增

已有的 `isInRange` 没有变化。

#### `long abs(long value)`

返回 long 的绝对值。

- `value` 为 `Long.MIN_VALUE` 时抛出 `IllegalArgumentException`，因为该绝对值无法用 long 表示。
- 其余负数返回相反数，0 和非负数原样返回。

```java
MathematicsUtil.abs(-1L); // 1
MathematicsUtil.abs(0L);  // 0
```

### HexUtil 新增

已有的 `decode` 没有变化。`decode` 对非法输入抛异常；本方法只返回 false。

#### `boolean isHex(String hex)`

判断文本是不是偶数长度的十六进制。

- `hex`：允许为 null。null、空串、只含空白、奇数长度、非法字符、`0x` 前缀，或中间夹空白时返回 false，不抛异常。
- 字母不区分大小写。

```java
HexUtil.isHex("0f");   // true
HexUtil.isHex("0F");   // true
HexUtil.isHex(null);   // false
HexUtil.isHex("0x10"); // false
```
