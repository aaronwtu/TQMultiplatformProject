package org.aaronwtlu.project.previewer.Foundation

import org.aaronwtlu.project.Klog

/// 订一个注解
/// annotation class Fancy

//@Target 指定可以用该注解标注的元素的可能的类型（类、函数、属性与表达式）；
//@Retention (保留期)指定该注解是否存储在编译后的 class 文件中，以及它在运行时能否通过反射可见 （默认都是 true）；
//@Repeatable 允许在单个元素上多次使用相同的该注解；
//@MustBeDocumented 修饰的注解将被文档工具提取到API文档中

/// Doc
/// https://www.geekailab.com/doc/as/book/docs/Part1/Android%E5%BC%80%E5%8F%91%E5%BF%85%E5%A4%87Kotlin%E6%A0%B8%E5%BF%83%E6%8A%80%E6%9C%AF/%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3Kotlin%E6%B3%A8%E8%A7%A3.html

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.EXPRESSION
)
@Retention(
    AnnotationRetention.SOURCE
)
@MustBeDocumented
annotation class Fancy

@Fancy class Foo {
    @Fancy fun baz(@Fancy foo: Int): Int {
        return (@Fancy 1)
    }
}

//和一般的声明很类似，只是在class前面加上了annotation修饰符
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION
)
annotation class ApiDoc(val value: String)

// 通过注解给 AnnotationTarget 添加额外信息

@ApiDoc("修饰类") class TestBox {
    @ApiDoc("修饰字段")
    var size = 6
    @ApiDoc("修饰方法")
    fun test() { }
}

public enum class TestMethod {
    GET,
    POST
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestHttpMethod(val method: TestMethod)

interface TestApi {
    val func: String
    val callee: String
    val version: String
        get() = "1.0.0" //可以提供一个默认方法
}

@TestHttpMethod(TestMethod.GET)
class TestDetailInfo: TestApi {
    override val func: String
        get() = "trpc.video.testSync"
    override val callee: String
        get() = "/trpc.video.testSync.getDetailInfo"
}

fun testFire(api: TestApi) {
    val annotations = api.javaClass.annotations
    val method = annotations.find { it is TestHttpMethod } as? TestHttpMethod
    method?.let {
        Klog.d("http method => $it")
    }
}