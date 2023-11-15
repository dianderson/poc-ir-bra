CREATE TABLE income_request
(
    cpf                 varchar(11),
    year                int,
    awaiting_processing varchar(50),
    created_at          timestamp not null,
    updated_at          timestamp not null,
    primary key (cpf, year)
)