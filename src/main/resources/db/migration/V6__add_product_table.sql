drop table if exists product cascade;

create table product
(
    id bigint not null auto_increment primary key,
    description varchar(100),
    product_status varchar(20),
    created_date timestamp,
    updated_date timestamp
) engine = InnoDB;
