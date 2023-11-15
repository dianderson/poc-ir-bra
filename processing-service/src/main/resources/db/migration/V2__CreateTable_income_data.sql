CREATE TABLE income_data
(
    cpf         varchar(11),
    year        int,
    product     varchar(20),
    is_finished boolean,
    status      varchar(15),
    pdf_data    text,
    primary key (cpf, year, product, is_finished)
)