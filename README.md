# CommonUtils

Android 里可直接静态调用的 Java 工具库。源码语言是 Java 8，库模块 `minSdkVersion` 为 23。

工具按类组织。当前发布版本是 `1.0.4`。`1.0.2` 有 8 个工具类，放在 6 个包里，并不是 8 个包。`calculate` 包同时包含 `DecimalUtil` 和 `MathematicsUtil`。`1.0.3` 增加了 `CollectionUtil`、`ConvertUtil`、`Sha256Util` 和 `FileUtil`。`1.0.4` 再增加 `IoUtil` 和 `ObjectUtil`，并给已有类追加了方法。

## 版本说明

| 范围 | 怎么用 | 包含什么 |
| --- | --- | --- |
| 已发布 `1.0.2` | JitPack `CommonUtils:1.0.2` | 只包含「已发布 1.0.2 的工具类」一节里的方法 |
| 已发布 `1.0.3` | JitPack `CommonUtils:1.0.3` | 包含 `1.0.2` 的全部方法，以及「1.0.3 新增」一节 |
| 已发布 `1.0.4` | JitPack `CommonUtils:1.0.4` | 包含 `1.0.3` 的全部方法，以及「1.0.4 新增」一节 |

`1.0.1` 的类还在 `com.henry.common.utils` 根包下。`1.0.2` 起改为下面的子包。`1.0.1` 与 `1.0.2` 的类名位置不同，不能混用。`1.0.3` 和 `1.0.4` 沿用该包结构，没有修改此前已发布方法的签名和返回值。

## 依赖 1.0.4

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
    implementation 'com.github.Henry-William-Code:CommonUtils:1.0.4'
}
```

`1.0.4` 包含「已发布 1.0.2 的工具类」「1.0.3 新增」和「1.0.4 新增」。仍使用 `1.0.3` 的工程不能调用「1.0.4 新增」。

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
