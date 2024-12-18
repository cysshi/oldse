-- ----------------------------
-- 1、床位表
-- ----------------------------
drop table if exists sys_bed;
create table sys_bed (
  bed_id           bigint(20)      not null auto_increment    comment '床位ID',
  bed_number       varchar(30)     not null                   comment '床位号',
  price            decimal(10,2)   default 0                  comment '床位价格',
  status           char(1)         default '0'                comment '床位状态（0空闲 1已入住 2维护）',
  elder_id         bigint(20)      default null               comment '入住老人ID',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (bed_id)
) engine=innodb auto_increment=100 comment = '床位信息表';

-- ----------------------------
-- 2、入住信息表
-- ----------------------------
drop table if exists sys_check_in;
create table sys_check_in (
  check_in_id      bigint(20)      not null auto_increment    comment '入住ID',
  elder_id         bigint(20)      not null                   comment '老人ID',
  bed_id           bigint(20)      not null                   comment '床位ID',
  check_in_time    datetime        not null                   comment '入住时间',
  check_out_time   datetime        default null               comment '退住时间',
  status           char(1)         default '1'                comment '状态（1在住 2已退住）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (check_in_id)
) engine=innodb auto_increment=100 comment = '入住信息表';