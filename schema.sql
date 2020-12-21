create table users (id bigint auto_increment, user_name varchar(255) not null unique, primary key auto_increment (id)) engine=InnoDB;

create table conversations (id bigint auto_increment, participant_one_id bigint not null, participant_two_id bigint not null, primary key auto_increment (id), foreign key(participant_one_id) references users(id), foreign key(participant_two_id) references users(id)) engine=InnoDB;

create table messages (id bigint auto_increment, conversation_id bigint not null, message_text varchar(255) not null, sender_id bigint not null, recipient_id bigint not null, create_date datetime(6), modify_date datetime(6), primary key auto_increment (id), foreign key(conversation_id) references conversations(id), foreign key(sender_id) references users(id), foreign key(recipient_id) references users(id)) engine=InnoDB;

create table liked_messages (id bigint auto_increment, message_id bigint not null, user_id bigint not null, primary key auto_increment (id), foreign key(message_id) references messages(id), foreign key(user_id) references users(id)) engine=InnoDB;
