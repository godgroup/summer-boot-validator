# 简介
1. 基于spring boot starter的自动化配置特性,对于api请求参数做出校验的自定义starter
2. 应用框架只需要加上注解例如@NotEmpty等即可，不需要写任何其他多余的代码来校验。
3. 使用样例：https://github.com/yalunwang/demo-validator
4. 我的博客：http://www.yalunwang.com

**欢迎使用,多多加star,如使用过程中碰到问题，可以提出Issue，我们会尽力完善。

# 如何使用
## summer-boot-validator支持三种类型的请求校验

1. 简单参数直接定义在方法入参内

```java
@RequestMapping("/hi")
public String hi(@NotEmpty String name){
        return "hi"+name;
}
```

2. 定义请求request类时可以加上

```java
public class DemoRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String age;
    //省略get set
    //开发时请务必加上get set
   
}
```
3. 支持批量执行(有些场景是需要批量操作)

```java
public class DemoRequest {

    @RequestMapping("/hi2")
    public String hi2(@RequestBody  List<DemoRequest> request){
        return "hi"+request.get(0).getName();
    }
   
}
```

## 必要的配置
```java
summer-boot-validator一共需要两个配置
summer.boot.validator.errorCode=-9999        --校验的参数如不符合入参的设置抛出的错误码 默认为-9999
summer.boot.validator.enable=false   --是否开启校验     默认false

如果想用使用需要开启校验 summer.boot.validator.enable=true
如果自己约定错误码 summer.boot.validator.errorCode=xxxx (自己的错误码)
```

## 捕获summer-boot-validato的异常
summer-boot-validator 如果发现入参参数不符合你的设置会抛出ValidateRuntimeException异常请在项目统一异常错误里处理一下，返回符合自己项目要求的返回,下面是一个例子：
```java
@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);
    @ExceptionHandler
    public ApiResponse<Object> handler(HttpServletRequest request, Exception ex) {
        StringBuilder errorMessage = new StringBuilder();
        Integer errorCode = 0;
        if (ex instanceof ValidateRuntimeException) {
            ValidateRuntimeException validateRuntimeException = (ValidateRuntimeException) ex;
            errorCode = validateRuntimeException.getCode();
            errorMessage.append(JSON.toJSONString(validateRuntimeException.getFailedReason()));
            LOGGER.error(errorMessage.toString());
        } else {
            //其他异常
        }
        LOGGER.error(ex.getMessage(), ex);
        return ApiResponse.fail(errorCode, errorMessage.toString());
    }
}
```

