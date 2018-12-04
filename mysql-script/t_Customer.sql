/*创建 客户基本表 t_Customer*/

DROP TABLE IF EXISTS t_Customer;

CREATE TABLE IF NOT EXISTS t_Customer(
    Id CHAR(36) PRIMARY KEY,
    CustomerName VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(30) NOT NULL,
    Sex CHAR(1) NULL,
    Email VARCHAR(100) NOT NULL,
    Phone VARCHAR(11) NOT NULL,
    State CHAR(1) NOT NULL DEFAULT 1,
    CreatedDate DATETIME NOT NULL DEFAULT  NOW()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
  说明：
  用户State的几种形式（1）正常（0）禁用
 */