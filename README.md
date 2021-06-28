# CommonUtils

## 介绍
Android中的常用的工具类

### 使用说明
1、在工程中的builder.gradle中添加

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

2、在单独项目中的builder.gradle中添加

    dependencies {
	        implementation 'com.github.Henry-William-Code:CommonUtils:版本号'
	}

3、注释

#### StringUtil 字符串工具类
---

* isEmpty ： 判断字符串是否为空
* outPrintEmptyString ： 如果是空字符就输出""，如果不是空就原样输出


---

#### Base64Util base64加密工具类

---
* encode ：Base64加密
* decode ：Base64解密

---

#### MD5Util MD5加密
---
* addMD5 : MD5加密
* addMD5Twice ： MD5加密2次
* addMD5Ten ： MD5加密10次
* addMD5Times ： MD5加密多次（根据用户的次数加密）
---

#### SystemUtil Android系统相关的工具类
---
* getPackageVersion ：获取当前应用的版本号
* getPackageName ：获取当前应用的版本名
* getSystemVersion ： 获取当前手机的系统版本号
* getSystemModel ： 获取当前手机型号
* getDeviceBrand ： 获取手机厂商
* callPhone ： 拨打电话（跳转到拨号界面，用户手动点击拨打）
* settingsWidgetWidth ： 设置控制宽度（占屏幕几分之几） -- 父布局为RelativeLayout
* settingsWidgetWidth2 ： 设置控制宽度（占屏幕几分之几） -- 父布局为LinearLayout
* settingsWidgetHeight ： 设置控制高度（占屏幕几分之几）） -- 父布局为RelativeLayout
* settingsWidgetHeight2 ： 设置控制高度（占屏幕几分之几） -- 父布局为LinearLayout
* extractYearMonthDayOfIdCard ： 省份证的正则表达式^(\d{15}|\d{17}[\dx])$
* dip2px ： dp转px
* getRandomInt ： 获取随机数
* isTablet： 判断是否是平板 * 谷歌源码（尺寸，分辨率，能否打电话都是浮云）

---






#### TimeUtil 时间相关的工具类

---
* getFormatTime ： 根据时间值获取时间格式（多个重载方法）
* isContainsTime ： 是否包含当前时间
* isAfterEndTime ： 是否大于结束时间
* stringSwitchDate ： 文本字符串转为时间类型

---

#### WebUtil 网站相关的工具类

---

* intentBrowserWeb ： 使用浏览器转跳到某个页面
* getImageSrc ： 从HTML源码中提取图片路径，最后以一个 String 类型的 List 返回，如果不包含任何图片，则返回一个 size=0　的List
* removeHtmlTag ： 删除Html标签
---

### DecimalUtil 进制相关的计算类
