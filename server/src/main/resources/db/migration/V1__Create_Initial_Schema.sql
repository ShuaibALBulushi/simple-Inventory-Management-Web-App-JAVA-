CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT CONSTRAINT FK_products_categories REFERENCES categories(id),
    name NVARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);