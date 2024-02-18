create table BRAND (
    BRAND_ID int primary key,
    NAME varchar(255) not null
);

create table PRODUCT (
    PRODUCT_ID int primary key,
    NAME varchar(255) not null
);

create table PRICE_LIST (
    PRICE_LIST_ID int primary key,
    NAME varchar(255) not null
);

create table PRICES (
    ID int AUTO_INCREMENT primary key,
    BRAND_ID int,
    START_DATE timestamp not null,
    END_DATE timestamp not null,
    PRICE_LIST_ID int,
    PRODUCT_ID int,
    PRIORITY int not null,
    PRICE decimal(10, 2) not null,
    CURR varchar(3) not null,
    foreign key (BRAND_ID) references BRAND(BRAND_ID),
    foreign key (PRICE_LIST_ID) references PRICE_LIST(PRICE_LIST_ID),
    foreign key (PRODUCT_ID) references PRODUCT(PRODUCT_ID)
);