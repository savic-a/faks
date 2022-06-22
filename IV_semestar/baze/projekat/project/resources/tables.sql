create table gs (
    idGs integer primary key,
    nazGs varchar(50) not null,
    constraint gs_uk unique (nazGs)
);

create table skiCentar (
    idSki integer primary key,
    nazSki varchar(50) not null,
    telSki varchar(15) not null,
    emailSki varchar(50),
    gsSki integer references gs not null,
    constraint skiCentar_uk unique (nazSki)
);

create table staza (
    idSt integer primary key,
    nazSt varchar(20) not null,
    duzSt integer not null,
    tip varchar(10) not null check (tip in ('crna', 'crvena', 'plava')),
    constraint staza_uk unique (nazSt),
    constraint staza_ch check (duzSt>200)
);

create table radnik (
    idRad integer primary key,
    ime varchar(20) not null,
    prz varchar(25) not null,
    plt decimal(10, 2),
    gsRad integer references gs not null,
    constraint radnik_ch check (plt>3000)
);

create table odrzavanje (
    idRad integer references radnik,
    idSt integer references staza,
    brc integer not null,
    constraint odrzavanje_pk primary key (idRad, idSt)
);