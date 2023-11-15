CREATE TABLE income_reports
(
    cpf                 varchar(11),
    year                int,
    awaiting_processing varchar(50),
    primary key (cpf, year)
)