# Migrating from Chatter 2.x to 3.x

Please refer to this page if you're **migrating from chatter version `2.0.4` to `3.x`**.

In this page you will find the summary of all the breaking changes that you potentially need to fix.

## 1. Class name changes

Generally name of classes from Chatter 2.x used to have `Chuck` as a prefix (e.g. `ChuckInterceptor`). In version 3.x we updated the naming of all the classes to have `Chatter` as a prefix (e.g. `ChatterInterceptor`). This is valid for all the classes in the library.

So if to launch the UI of Chatter, you would normally call:

```kotlin
Chuck.getLaunchIntent(context)
```

now you will call

```kotlin
Chatter.getLaunchIntent(context)
```

## 2. Package name changes

Please note that with version 3.x package name is also updated. The new package for the classes of Chatter will be `com.chatbooks.chatter.api`.

Here a summary of the name/package changes in chatter

| Old | New |
| --- | --- |
| `com.readystatesoftware.chuck.api.Chuck` | `com.chatbooks.chatter.api.Chatter` |
| `com.readystatesoftware.chuck.api.ChuckCollector` | `com.chatbooks.chatter.api.ChatterCollector` |
| `com.readystatesoftware.chuck.api.ChatterInterceptor` | `com.chatbooks.chatter.api.ChatterInterceptor` |

## 3. Update the code to configure the interceptor

Chatter v2.0 used to use a _Builder_ pattern to configure your interceptor. Chatter v3.0 instead is using _Kotlin named parameters_ with default values to configure the interceptor. Multiple builder methods have been **removed** and you need to replace them with parameters from the constructors.

### Java

The following code:

```java
ChuckInterceptor interceptor = new ChuckInterceptor(context, collector)
    .maxContentLength(120000L);
```

should be updated to:

```java
ChuckInterceptor interceptor = new ChuckInterceptor(context, collector, 120000)
```

### Kotlin

We suggest to use Kotlin to configure your interceptor as it makes the code more clean/elegant.

The following code:

```kotlin
val retentionManager = RetentionManager(androidApplication, ChuckCollector.Period.ONE_HOUR)

val collector = ChuckCollector(androidApplication)
            .retentionManager(retentionManager)
            .showNotification(true)

val interceptor = ChuckInterceptor(context, collector)
    .maxContentLength(120000L)
```

should be updated to:

```kotlin
val collector = ChatterCollector(
    context = this,
    showNotification = true,
    retentionPeriod = RetentionManager.Period.ONE_HOUR
)

val interceptor = ChatterInterceptor(
    context = context,
    collector = collector,
    maxContentLength = 120000L
)
```

## 4. RetentionManager is now replaced by the retentionPeriod

You don't need to create a `RetentionManager` anymore and you simply have to specify the `retentionPeriod` parameter when creating a `ChatterCollector`.

The `Period` enum has also been moved from `ChuckCollector` to `RetentionManager`.

## 5. registerDefaultCrashHanlder typo

The function `Chuck.registerDefaultCrashHanlder` contained a typo in the name and now is moved to `Chatter.registerDefaultCrashHandler`.