/*创建 分类组合 t_CategorySet*/

DROP TABLE IF EXISTS t_CategorySet;

CREATE TABLE IF NOT EXISTS t_CategorySet(
    Id CHAR(36) Primary key,
    SetName VARCHAR(20) NOT NULL,
    SetType CHAR(1) NOT NULL,
    State CHAR(1) NOT NULL DEFAULT 1,
    OrderNum INT NOT NULL DEFAULT 1,
    CreatedDate DATETIME NOT NULL DEFAULT  NOW()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
  说明：
  Category Set State的几种形式（1）启用（0）禁用
  Set Type 表示当前的分类组的使用形式，（1）Standard 标准菜单（2）Activity 为以后预留
 */