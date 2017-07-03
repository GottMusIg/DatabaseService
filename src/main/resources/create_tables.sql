create table account
(
	id int auto_increment
		primary key,
	name varchar(45) not null,
	password varchar(64) not null,
	constraint name_UNIQUE
		unique (name)
)
;

create table characteraccountrelation
(
	id int auto_increment
		primary key,
	character_id int not null,
	account_id int not null,
	constraint characteraccountrelation_account_id_fk
		foreign key (account_id) references gmidb.account (id)
)
;

create index characteraccountrelation_account_id_fk
	on characteraccountrelation (account_id)
;

create index characteraccountrelation_wowcharacter_id_fk
	on characteraccountrelation (character_id)
;

create table classspecification
(
	id int auto_increment
		primary key,
	name varchar(45) not null,
	WOWClass_id int not null
)
;

create index fk_classSpecification_WOWClass1_idx
	on classspecification (WOWClass_id)
;

create table item
(
	id int auto_increment
		primary key,
	wowhead_tooltip varchar(100) not null,
	context varchar(45) not null,
	name varchar(45) not null,
	item_level int not null,
	icon_tooltip varchar(100) not null
)
;

create table realm
(
	id int auto_increment
		primary key,
	name varchar(30) not null,
	location varchar(5) not null
)
;

create table specificationdps
(
	id int auto_increment
		primary key,
	dps int not null,
	classSpecification_id int not null,
	constraint fk_specificationDPS_classSpecification
		foreign key (classSpecification_id) references gmidb.classspecification (id)
)
;

create index fk_specificationDPS_classSpecification_idx
	on specificationdps (classSpecification_id)
;

create table wowcharacter
(
	id int auto_increment
		primary key,
	name varchar(45) not null,
	dps int not null,
	realm_id int not null,
	classspecification_id int not null,
	thumbnail_id varchar(45) null,
	head_id int not null,
	neck_id int not null,
	shoulder_id int not null,
	back_id int not null,
	skirt_id int not null,
	chest_id int not null,
	wrist_id int not null,
	main_hand_id int not null,
	off_hand_id int not null,
	hands_id int not null,
	finger_1_id int not null,
	finger_2_id int not null,
	waist_id int not null,
	legs_id int not null,
	feet_id int not null,
	trinket_1_id int not null,
	trinket_2_id int not null,
	average_item_level int not null,
	average_item_level_equipped int not null,
	constraint fk_character_realm1
		foreign key (realm_id) references gmidb.realm (id),
	constraint fk_character_classspecification1
		foreign key (classspecification_id) references gmidb.classspecification (id),
	constraint fk_wowcharacter_item1
		foreign key (head_id) references gmidb.item (id),
	constraint fk_wowcharacter_item2
		foreign key (neck_id) references gmidb.item (id),
	constraint fk_wowcharacter_item3
		foreign key (shoulder_id) references gmidb.item (id),
	constraint fk_wowcharacter_item4
		foreign key (back_id) references gmidb.item (id),
	constraint fk_wowcharacter_item5
		foreign key (skirt_id) references gmidb.item (id),
	constraint fk_wowcharacter_item6
		foreign key (chest_id) references gmidb.item (id),
	constraint fk_wowcharacter_item7
		foreign key (wrist_id) references gmidb.item (id),
	constraint fk_wowcharacter_item8
		foreign key (main_hand_id) references gmidb.item (id),
	constraint fk_wowcharacter_item9
		foreign key (off_hand_id) references gmidb.item (id),
	constraint fk_wowcharacter_item10
		foreign key (hands_id) references gmidb.item (id),
	constraint fk_wowcharacter_item11
		foreign key (finger_1_id) references gmidb.item (id),
	constraint fk_wowcharacter_item12
		foreign key (finger_2_id) references gmidb.item (id),
	constraint fk_wowcharacter_item13
		foreign key (waist_id) references gmidb.item (id),
	constraint fk_wowcharacter_item14
		foreign key (legs_id) references gmidb.item (id),
	constraint fk_wowcharacter_item15
		foreign key (feet_id) references gmidb.item (id),
	constraint fk_wowcharacter_item16
		foreign key (trinket_1_id) references gmidb.item (id),
	constraint fk_wowcharacter_item17
		foreign key (trinket_2_id) references gmidb.item (id)
)
;

create index fk_character_classspecification1_idx
	on wowcharacter (classspecification_id)
;

create index fk_character_realm1_idx
	on wowcharacter (realm_id)
;

create index fk_wowcharacter_item10_idx
	on wowcharacter (hands_id)
;

create index fk_wowcharacter_item11_idx
	on wowcharacter (finger_1_id)
;

create index fk_wowcharacter_item12_idx
	on wowcharacter (finger_2_id)
;

create index fk_wowcharacter_item13_idx
	on wowcharacter (waist_id)
;

create index fk_wowcharacter_item14_idx
	on wowcharacter (legs_id)
;

create index fk_wowcharacter_item15_idx
	on wowcharacter (feet_id)
;

create index fk_wowcharacter_item16_idx
	on wowcharacter (trinket_1_id)
;

create index fk_wowcharacter_item17_idx
	on wowcharacter (trinket_2_id)
;

create index fk_wowcharacter_item1_idx
	on wowcharacter (head_id)
;

create index fk_wowcharacter_item2_idx
	on wowcharacter (neck_id)
;

create index fk_wowcharacter_item3_idx
	on wowcharacter (shoulder_id)
;

create index fk_wowcharacter_item4_idx
	on wowcharacter (back_id)
;

create index fk_wowcharacter_item5_idx
	on wowcharacter (skirt_id)
;

create index fk_wowcharacter_item6_idx
	on wowcharacter (chest_id)
;

create index fk_wowcharacter_item7_idx
	on wowcharacter (wrist_id)
;

create index fk_wowcharacter_item8_idx
	on wowcharacter (main_hand_id)
;

create index fk_wowcharacter_item9_idx
	on wowcharacter (off_hand_id)
;

alter table characteraccountrelation
	add constraint characteraccountrelation_wowcharacter_id_fk
		foreign key (character_id) references gmidb.wowcharacter (id)
;

create table wowclass
(
	id int auto_increment
		primary key,
	name varchar(45) not null,
	constraint classname_UNIQUE
		unique (name)
)
;

alter table classspecification
	add constraint fk_classSpecification_WOWClass1
		foreign key (WOWClass_id) references gmidb.wowclass (id)
;