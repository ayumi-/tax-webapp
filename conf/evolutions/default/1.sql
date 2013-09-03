# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contract (
  contract_number           bigint not null,
  party_id                  bigint,
  agreement_date            timestamp,
  effective_date            timestamp,
  expired_date              timestamp,
  contract_type             varchar(255),
  price_rounding_method     varchar(255),
  consumption_tax_rounding_method varchar(255),
  consumption_tax_calculation_base varchar(255),
  constraint pk_contract primary key (contract_number))
;

create table item (
  id                        bigint not null,
  name                      varchar(255),
  is_untaxed                boolean,
  constraint uq_item_name unique (name),
  constraint pk_item primary key (id))
;

create table party (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_party primary key (id))
;

create table product (
  id                        bigint not null,
  name                      varchar(255),
  unit_price                integer,
  item_id                   bigint,
  constraint pk_product primary key (id))
;

create sequence contract_seq;

create sequence item_seq;

create sequence party_seq;

create sequence product_seq;

alter table contract add constraint fk_contract_party_1 foreign key (party_id) references party (id) on delete restrict on update restrict;
create index ix_contract_party_1 on contract (party_id);
alter table product add constraint fk_product_item_2 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_product_item_2 on product (item_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists contract;

drop table if exists item;

drop table if exists party;

drop table if exists product;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists contract_seq;

drop sequence if exists item_seq;

drop sequence if exists party_seq;

drop sequence if exists product_seq;

