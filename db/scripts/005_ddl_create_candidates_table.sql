create table candidates
(
    id            serial primary key,
    name          varchar,
    description   varchar,
    creation_date timestamp,
    city_id       int references cities (id),
    file_id       int references files (id)
);