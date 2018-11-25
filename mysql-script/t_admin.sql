/*创建 管理员表 t_Admin*/

DROP TABLE IF EXISTS t_Admin;

CREATE TABLE IF NOT EXISTS t_Admin(
    Id CHAR(36) Primary key,
    AdminName VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Sex Bool NULL,
    Email VARCHAR(100) NULL,
    Phone VARCHAR(11) NULL,
    State CHAR(1) NOT NULL DEFAULT 1,
    CreatedDate DATETIME NOT NULL DEFAULT  NOW()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
  说明：
  用户State的几种形式（1）正常（0）禁用
 */