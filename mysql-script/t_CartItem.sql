/*创建 购物车明细表 t_CartItem*/

DROP TABLE IF EXISTS t_CartItem;

CREATE TABLE IF NOT EXISTS t_CartItem(
  Id CHAR(36) PRIMARY KEY,
  CartId CHAR(36) NOT NULL ,
  ProductId CHAR(36) NOT NULL,
  Quantity INT NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;