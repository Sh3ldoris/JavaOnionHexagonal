--liquibase formatted sql

-- changeset system:init-schema
create schema water_connect;

-- changeset system:create-entity-tables
create table water_connect.connector_connected_pipes
(
    list_idx     integer not null,
    connector_id uuid    not null,
    pipe_id      uuid    not null,
    primary key (list_idx, connector_id)
);
create table water_connect.connectors
(
    diameter_mm     integer   not null,
    latitude        float(53) not null,
    longitude       float(53) not null,
    max_connections integer   not null,
    connector_id    uuid      not null,
    connector_type  varchar(255) check ((connector_type in ('TEE', 'ELBOW', 'COUPLING', 'VALVE', 'REDUCER'))),
    material        varchar(255) check ((material in ('PVC', 'COPPER', 'STEEL', 'HDPE'))),
    primary key (connector_id)
);
create table water_connect.customers
(
    registered_at timestamp(6) with time zone,
    customer_id   uuid not null,
    city          varchar(255),
    country       varchar(255),
    customer_type varchar(255) check ((customer_type in ('RESIDENTIAL', 'COMMERCIAL', 'INDUSTRIAL'))),
    email         varchar(255),
    full_name     varchar(255),
    phone         varchar(255),
    postal_code   varchar(255),
    street        varchar(255),
    primary key (customer_id)
);
create table water_connect.pipe_locations
(
    end_latitude    float(53) not null,
    end_longitude   float(53) not null,
    start_latitude  float(53) not null,
    start_longitude float(53) not null,
    pipe_id         uuid      not null,
    primary key (pipe_id)
);
create table water_connect.pipes
(
    diameter_mm         integer   not null,
    length_meters       float(53) not null,
    pressure_rating_bar float(53) not null,
    installed_at        timestamp(6) with time zone,
    pipe_id             uuid      not null,
    material            varchar(255) check ((material in ('PVC', 'COPPER', 'STEEL', 'HDPE'))),
    status              varchar(255) check ((status in ('PLANNED', 'INSTALLED', 'ACTIVE', 'DECOMMISSIONED'))),
    primary key (pipe_id)
);
create table water_connect.service_point
(
    activated_at     timestamp(6) with time zone,
    requested_at     timestamp(6) with time zone,
    connector_id     uuid,
    customer_id      uuid,
    meter_id         uuid unique,
    service_point_id uuid not null,
    status           varchar(255) check ((status in ('REQUESTED', 'APPROVED', 'INSTALLED', 'ACTIVE', 'SUSPENDED',
                                                     'DISCONNECTED'))),
    primary key (service_point_id)
);
create table water_connect.water_meter
(
    last_reading_m3 float(53) not null,
    installed_at    timestamp(6) with time zone,
    last_reading_at timestamp(6) with time zone,
    meter_id        uuid      not null,
    serial_number   varchar(255),
    primary key (meter_id)
);
create table water_connect.work_notes
(
    created_at    timestamp(6) with time zone not null,
    note_id       uuid                        not null,
    work_order_id uuid                        not null,
    author        varchar(255),
    content       varchar(255)                not null,
    primary key (note_id)
);
create table water_connect.work_order
(
    scheduled_date   date,
    completed_at     timestamp(6) with time zone,
    created_at       timestamp(6) with time zone,
    service_point_id uuid,
    work_order_id    uuid not null,
    assigned_team    varchar(255),
    status           varchar(255) check ((status in ('CREATED', 'SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'))),
    type             varchar(255) check ((type in ('NEW_CONNECTION', 'REPAIR', 'METER_REPLACEMENT', 'DISCONNECTION'))),
    primary key (work_order_id)
);

alter table if exists water_connect.connector_connected_pipes
    add constraint FK6chus6pxvd58vbvdkpr3veohj
        foreign key (connector_id)
            references water_connect.connectors;
alter table if exists water_connect.pipe_locations
    add constraint fk_pipe_locations_pipe
        foreign key (pipe_id)
            references water_connect.pipes;
alter table if exists water_connect.service_point
    add constraint FKnamydlroxtbwp8nne75i6ht47
        foreign key (meter_id)
            references water_connect.water_meter;
alter table if exists water_connect.work_notes
    add constraint FKqdt8nrdya9r8boxl92jm66al9
        foreign key (work_order_id)
            references water_connect.work_order;
