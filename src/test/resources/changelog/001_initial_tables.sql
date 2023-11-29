-- liquibase formatted sql
CREATE TABLE cart
(
    id         uuid primary key not null,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product
(
    id          uuid primary key not null,
    description VARCHAR(256)     not null,
    amount      numeric(38, 2)   not null,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart_product
(
    cart_id    uuid not null,
    product_id uuid not null,
    quantity integer not null,
    foreign key (product_id) references product (id)
        match simple on update no action on delete no action,
    foreign key (cart_id) references cart (id)
        match simple on update no action on delete no action
);
