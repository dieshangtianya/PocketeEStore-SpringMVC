/*创建 产品分类对应表 t_Product_Category*/

DROP TABLE IF EXISTS t_Product_Category;

CREATE TABLE IF NOT EXISTS t_Product_Category(
  Id CHAR(36) PRIMARY KEY,
  ProductId CHAR(36) NOT NULL,
  CategoryId CHAR(36) NOT NULL
  /*设置联合主键*/
  /*PRIMARY  KEY(ProductId,CategoryId)*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;