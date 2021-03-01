# P3C

## Start

`idea` -> import  -> p3c/idea-plugin  gradle project

`idea` -> import  -> p3c/p3c-pmd maven project

```
cd p3c-idea
../gradlew runIde // runide task 为启动软件
```



![image-20210301190644741](README.assets\image-20210301190644741.png)

在idea run中添加 前置任务 maven build p3c-pmd 项目



![image-20210301190813058](README.assets\image-20210301190813058.png)

p3c-pmd build 之后会把jar copy 到 idea-plugin 的目录下被依赖

