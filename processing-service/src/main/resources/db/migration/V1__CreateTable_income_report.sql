CREATE TABLE income_report
(
    cpf        varchar(11),
    year       int,
    product    varchar(20),
    status     varchar(15),
    pdf_data   text,
    updated_at timestamp not null,
    primary key (cpf, year, product)
);