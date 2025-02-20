## 推送规范

本仓库使用了 *main 分支保护*, 即任何包含直接 push 到 main 分支的提交的推送都是不允许的

开发者需要创建自己的分支, 分支命名无要求, 但推荐使用 [`提交规范`](#提交规范) 中的前缀, 如 perf/Vanilla, fix/Takuyama

## 代码规范

前端：Vue, Javascript (WebStorm)
后端：Spring Boot, Java (IntelliJ IDEA)

## 提交规范

使用 *[约定式提交](https://www.conventionalcommits.org/zh-hans/v1.0.0/)*, 即在提交信息前写上本次提交的类型

本项目主要提交类型:

- chore: 用于对非业务性代码进行修改，例如修改构建流程或者工具配置等
- fix: 修bug
- feat: 新功能
- docs: 文档
- perf: 优化
- refactor: 重构逻辑
- revert: 回滚提交
- wip: work in progress
- test: 用于修改测试用例，例如添加、删除、修改代码的测试用例等
- style: 用于修改代码的样式，例如调整缩进、空格、空行等  
