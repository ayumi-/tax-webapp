# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  unit                      varchar(255),
  constraint pk_account primary key (id))
;

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

create table entry (
  id                        bigint not null,
  constraint pk_entry primary key (id))
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

create table pricing_account (
  id                        bigint not null,
  unit                      varchar(255),
  party_id                  bigint,
  account_title             varchar(255),
  currency                  varchar(255),
  constraint pk_pricing_account primary key (id))
;

create table pricing_entry (
  id                        bigint not null,
  transaction_number        bigint,
  account_id                bigint,
  price                     decimal(38,1),
  currency                  varchar(255),
  constraint pk_pricing_entry primary key (id))
;

create table pricing_transaction (
  transaction_number        bigint not null,
  effective_date            timestamp,
  registered_date           timestamp,
  trading_transaction_number bigint,
  trading_entry_basis_id    bigint,
  trading_transaction_basis_number bigint,
  constraint pk_pricing_transaction primary key (transaction_number))
;

create table product (
  id                        bigint not null,
  name                      varchar(255),
  unit_price                decimal(38,1),
  item_id                   bigint,
  constraint pk_product primary key (id))
;

create table trading_account (
  id                        bigint not null,
  unit                      varchar(255),
  party_id                  bigint,
  product_id                bigint,
  constraint pk_trading_account primary key (id))
;

create table trading_entry (
  id                        bigint not null,
  transaction_number        bigint,
  account_id                bigint,
  quantity                  decimal(38,1),
  unit                      varchar(255),
  constraint pk_trading_entry primary key (id))
;

create table trading_transaction (
  transaction_number        bigint not null,
  effective_date            timestamp,
  registered_date           timestamp,
  contract_number           bigint,
  transaction_type          varchar(255),
  trading_subject           varchar(255),
  is_untaxed                boolean,
  constraint pk_trading_transaction primary key (transaction_number))
;

create table transaction (
  transaction_number        bigint not null,
  effective_date            timestamp,
  registered_date           timestamp,
  constraint pk_transaction primary key (transaction_number))
;

create sequence account_seq;

create sequence contract_seq;

create sequence entry_seq;

create sequence item_seq;

create sequence party_seq;

create sequence pricing_account_seq;

create sequence pricing_entry_seq;

create sequence pricing_transaction_seq;

create sequence product_seq;

create sequence trading_account_seq;

create sequence trading_entry_seq;

create sequence trading_transaction_seq;

create sequence transaction_seq;

alter table contract add constraint fk_contract_party_1 foreign key (party_id) references party (id) on delete restrict on update restrict;
create index ix_contract_party_1 on contract (party_id);
alter table pricing_account add constraint fk_pricing_account_party_2 foreign key (party_id) references party (id) on delete restrict on update restrict;
create index ix_pricing_account_party_2 on pricing_account (party_id);
alter table pricing_entry add constraint fk_pricing_entry_transaction_3 foreign key (transaction_number) references pricing_transaction (transaction_number) on delete restrict on update restrict;
create index ix_pricing_entry_transaction_3 on pricing_entry (transaction_number);
alter table pricing_entry add constraint fk_pricing_entry_account_4 foreign key (account_id) references pricing_account (id) on delete restrict on update restrict;
create index ix_pricing_entry_account_4 on pricing_entry (account_id);
alter table pricing_transaction add constraint fk_pricing_transaction_trading_5 foreign key (trading_transaction_number) references trading_transaction (transaction_number) on delete restrict on update restrict;
create index ix_pricing_transaction_trading_5 on pricing_transaction (trading_transaction_number);
alter table pricing_transaction add constraint fk_pricing_transaction_trading_6 foreign key (trading_entry_basis_id) references trading_entry (id) on delete restrict on update restrict;
create index ix_pricing_transaction_trading_6 on pricing_transaction (trading_entry_basis_id);
alter table pricing_transaction add constraint fk_pricing_transaction_trading_7 foreign key (trading_transaction_basis_number) references trading_transaction (transaction_number) on delete restrict on update restrict;
create index ix_pricing_transaction_trading_7 on pricing_transaction (trading_transaction_basis_number);
alter table product add constraint fk_product_item_8 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_product_item_8 on product (item_id);
alter table trading_account add constraint fk_trading_account_party_9 foreign key (party_id) references party (id) on delete restrict on update restrict;
create index ix_trading_account_party_9 on trading_account (party_id);
alter table trading_account add constraint fk_trading_account_product_10 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_trading_account_product_10 on trading_account (product_id);
alter table trading_entry add constraint fk_trading_entry_transaction_11 foreign key (transaction_number) references trading_transaction (transaction_number) on delete restrict on update restrict;
create index ix_trading_entry_transaction_11 on trading_entry (transaction_number);
alter table trading_entry add constraint fk_trading_entry_account_12 foreign key (account_id) references trading_account (id) on delete restrict on update restrict;
create index ix_trading_entry_account_12 on trading_entry (account_id);
alter table trading_transaction add constraint fk_trading_transaction_contra_13 foreign key (contract_number) references contract (contract_number) on delete restrict on update restrict;
create index ix_trading_transaction_contra_13 on trading_transaction (contract_number);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists contract;

drop table if exists entry;

drop table if exists item;

drop table if exists party;

drop table if exists pricing_account;

drop table if exists pricing_entry;

drop table if exists pricing_transaction;

drop table if exists product;

drop table if exists trading_account;

drop table if exists trading_entry;

drop table if exists trading_transaction;

drop table if exists transaction;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists contract_seq;

drop sequence if exists entry_seq;

drop sequence if exists item_seq;

drop sequence if exists party_seq;

drop sequence if exists pricing_account_seq;

drop sequence if exists pricing_entry_seq;

drop sequence if exists pricing_transaction_seq;

drop sequence if exists product_seq;

drop sequence if exists trading_account_seq;

drop sequence if exists trading_entry_seq;

drop sequence if exists trading_transaction_seq;

drop sequence if exists transaction_seq;

