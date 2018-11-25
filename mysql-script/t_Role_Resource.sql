/*创建 角色-资源关联表 t_Role_Resource*/

DROP TABLE IF EXISTS t_Role_Resource;

CREATE TABLE IF NOT EXISTS t_Role_Resource(
  Id CHAR(36) PRIMARY KEY,
  RoleId CHAR(36) NOT NULL,
  ResourceId CHAR(36) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;