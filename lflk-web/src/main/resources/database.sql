create table tb_sys_dept
(
    dept_id          int auto_increment comment '部门编号 部门编号'
        primary key,
    dept_code        varchar(32) charset utf8  null comment '部门编码 部门编码',
    dept_name        varchar(128) charset utf8 null comment '部门名称 部门名称',
    dept_pid         int                       not null comment '父部门编号 父部门编号',
    dept_pcode       varchar(32) charset utf8  null comment '父部门编码 父部门编码',
    dept_pname       varchar(128) charset utf8 not null comment '父部门名称 父部门名称',
    create_user_id   int                       null comment '创建人id 创建人id',
    create_user_name varchar(128) charset utf8 null comment '创建人名称 创建人姓名',
    create_time      datetime                  null comment '创建时间 创建时间',
    update_user_id   varchar(128) charset utf8 null comment '更新人id 更新人id',
    update_user_name varchar(128) charset utf8 null comment '更新人姓名 更新人姓名',
    update_time      datetime                  null comment '最后更新时间 最后更新时间',
    deleted          int default 0             null comment '是否删除，0代表未删除（默认值），1代表已删除'
)
    comment '部门表 ';

create table tb_sys_menu
(
    id               int auto_increment comment '菜单ID 菜单ID'
        primary key,
    menu_name        varchar(128) charset utf8  null comment '菜单名称 菜单名称',
    pid              int                        null comment '父级菜单id 父级菜单id',
    url              varchar(3072) charset utf8 null comment '跳转url 跳转路径',
    order_num        int                        null comment '菜单内排序 菜单内排序',
    type             varchar(32) charset utf8   null comment '菜单类型 类型，0菜单，1按钮',
    icon             varchar(3072) charset utf8 null comment '图标样式 内联样式',
    disabled         varchar(32) charset utf8   null comment '是否可用 0不可用，1可用',
    open             varchar(32) charset utf8   null comment '是否打开 0不展开，1展开',
    children         varchar(128) charset utf8  null comment '子菜单 子菜单',
    create_user_id   int                        null comment '创建人id 创建人id',
    create_user_name varchar(128) charset utf8  null comment '创建人名称 创建人姓名',
    create_time      datetime                   null comment '创建时间 创建时间',
    update_user_id   varchar(128) charset utf8  null comment '更新人id 更新人id',
    update_user_name varchar(128) charset utf8  null comment '更新人姓名 更新人姓名',
    update_time      datetime                   null comment '最后更新时间 最后更新时间',
    deleted          int default 0              null comment '是否删除，0代表逻辑未删除（默认值），1代表逻辑已删除'
)
    comment '菜单表 ';

create table tb_sys_role
(
    id               int auto_increment comment '角色ID 角色ID'
        primary key,
    role_name        varchar(128) charset utf8  null comment '角色名 角色名',
    role_name_ch     varchar(128) charset utf8  null comment '角色中文 角色名中文',
    role_remark      varchar(3072) charset utf8 null comment '角色描述 角色描述',
    deleted          int                        null comment '是否删除 是否删除，0逻辑未删除，1逻辑已删除',
    create_user_id   int                        null comment '创建人id 创建人id',
    create_user_name varchar(128) charset utf8  null comment '创建人名称 创建人姓名',
    create_time      datetime                   null comment '创建时间 创建时间',
    update_user_id   varchar(128) charset utf8  null comment '更新人id 更新人id',
    update_user_name varchar(128) charset utf8  null comment '更新人姓名 更新人姓名',
    update_time      datetime                   null comment '最后更新时间 最后更新时间'
)
    comment '角色表 ';

create table tb_sys_user
(
    id               int auto_increment comment '主键ID 主键ID'
        primary key,
    real_name        varchar(128) charset utf8  null comment '姓名 真实姓名',
    jh               varchar(128) charset utf8  null comment '警号 警号',
    gmsfhm           varchar(128) charset utf8  null comment '公民身份号码 公民身份号码',
    dept_id          varchar(128) charset utf8  null comment '部门编号 部门主键',
    dept_name        varchar(128) charset utf8  null comment '部门名称 部门名称',
    last_login_ip    varchar(128) charset utf8  null comment '最近登录IP 最近登录的ip地址',
    create_user_id   int                        null comment '创建人id 创建人id',
    create_user_name varchar(128) charset utf8  null comment '创建人名称 创建人姓名',
    create_time      datetime                   null comment '创建时间 创建时间',
    update_user_id   varchar(128) charset utf8  null comment '更新人id 更新人id',
    update_user_name varchar(128) charset utf8  null comment '更新人姓名 更新人姓名',
    update_time      datetime                   null comment '最后更新时间 最后更新时间',
    password         varchar(128) charset utf8  not null,
    dept_code        varchar(32) charset utf8   null,
    avatar           varchar(1024) charset utf8 null comment '用户头像',
    status           int                        null comment '是否可用，0不可用，1可用',
    deleted          int                        null comment '是否删除，0逻辑未删除值(默认为 0)，1逻辑已删除'
)
    comment '用户表 ';