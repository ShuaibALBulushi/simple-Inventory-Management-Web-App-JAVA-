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

CREATE TABLE inventory (
    id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL UNIQUE CONSTRAINT FK_inventory_products REFERENCES products(id),
    quantity INT NOT NULL DEFAULT 0
);

CREATE TABLE orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_date DATETIME2 DEFAULT GETUTCDATE(),
    status NVARCHAR(20) NOT NULL DEFAULT 'PENDING'
);

CREATE TABLE order_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL CONSTRAINT FK_orderitems_orders REFERENCES orders(id),
    product_id INT NOT NULL CONSTRAINT FK_orderitems_products REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL
);