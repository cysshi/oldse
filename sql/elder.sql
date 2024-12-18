-- ----------------------------
-- 老人表
-- ----------------------------
drop table if exists sys_elder;
create table sys_elder (
  elder_id           bigint(20)      not null auto_increment    comment '老人ID',
  elder_name         varchar(30)     not null                   comment '老人姓名',
  gender             char(1)         not null                   comment '老人性别（0男 1女）',
  age                int(3)          not null                   comment '老人年龄',
  id_card            varchar(18)     not null                   comment '身份证号',
  phone              varchar(11)     not null                   comment '联系电话',
  address            varchar(200)    not null                   comment '联系地址',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (elder_id)
) engine=innodb auto_increment=100 comment = '老人信息表';

-- ----------------------------
-- 床位表
-- ----------------------------
drop table if exists sys_bed;
create table sys_bed (
  bed_id            bigint(20)      not null auto_increment    comment '床位ID',
  bed_number        varchar(20)     not null                   comment '床位号',
  elder_id          bigint(20)                                 comment '入住老人ID',
  price             decimal(10,2)   not null                   comment '床位价格',
  status            char(1)         not null default '0'       comment '床位状态（0空闲 1已入住 2维护）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (bed_id)
) engine=innodb auto_increment=100 comment = '床位信息表';