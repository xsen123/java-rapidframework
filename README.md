# java-rapidframework
基于spring jdbc template封装的ORM框架

- 项目的代码包含了如何使用该框架的示例代码
- 框架的核心代码为go.openus.rapidframework中的所有文件，可打成jar包后引入到第三方项目中使用
- 框架的快速开发主要体现在，第三方项目中的业务Service类只需要继承自JdbcRapidServiceImpl<T>，Service就自动具备了增删改查功能，用以操作实体T对应的数据库表
