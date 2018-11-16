# 简介
1. 基于spring boot starter的自动化配置特性,对于api请求参数做出校验的自定义starter
2. 应用框架只需要加上注解例如@NotEmpty等即可，不需要写任何其他多余的代码来校验。
3. 使用样例：https://github.com/yalunwang/demo-validator
4. 我的博客：http://www.yalunwang.com

**欢迎使用,多多加star,如使用过程中碰到问题，可以提出Issue，我们会尽力完善。

# 如何使用
summer-boot-validator支持三种类型的请求校验

- 简单参数直接定义在方法入参内

```java
@RequestMapping("/hi")
public String hi(@NotEmpty String name){
        return "hi"+name;
}
```

- 定义请求request类时可以加上

```java
public class DemoRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String age;
    省略get set
    开发时请务必加上get set
   
}
```
- 支持批量执行(有些场景是需要批量操作)

```java
public class DemoRequest {

    @RequestMapping("/hi2")
    public String hi2(@RequestBody  List<DemoRequest> request){
        return "hi"+request.get(0).getName();
    }
   
}
```



