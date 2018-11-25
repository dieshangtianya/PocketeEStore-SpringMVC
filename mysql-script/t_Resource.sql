/*创建 权限资源表 t_Resource*/

DROP TABLE IF EXISTS t_Resource;

CREATE TABLE IF NOT EXISTS t_Resource(
  Id CHAR(36) PRIMARY KEY,
  ParentResourceId CHAR(36) NULL,
  ResourceName VARCHAR(50) NOT NULL,
  ResourceType TINYINT NOT NULL,
  Path VARCHAR(200) NULL,
  State CHAR(1) NOT NULL DEFAULT 1,
  Description VARCHAR(500) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
  关于资源的类型主要分为3类，page，navigation,interface
  navigation:1, 是说当前的资源是导航类，需要出现在系统导航菜单中
  page:2, 是说当前的资源是页面，是可以在系统找见对应页面文件
  interface:3, 是说当前的资源是一个接口，主要是针对外部系统提供权限控制（系统内对接口的权限不做控制），目前先不做。
 */