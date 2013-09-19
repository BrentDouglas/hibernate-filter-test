
-- For postgresql
create user filter_test with password 'iuashdkfjabhier7th3oqj4tnas';
create database filter_test with owner filter_test;
\c filter_test;

create table public.c(
    id serial primary key not null
);
alter table public.c owner to filter_test;

create table public.a(
    id serial primary key not null,
    prefix text not null,
    code text not null,
    c int references public.c(id) on delete cascade
);
alter table public.a owner to filter_test;