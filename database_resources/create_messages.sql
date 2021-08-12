-- public.messages definition

-- Drop table

-- DROP TABLE public.messages;

CREATE TABLE public.messages (
	message_id serial NOT NULL,
	sender_id int4 NULL,
	recipient_id int4 NULL,
	balance float8 NOT NULL,
	CONSTRAINT messages_pk PRIMARY KEY (message_id)
);


-- public.messages foreign keys

ALTER TABLE public.messages ADD CONSTRAINT messages_fk FOREIGN KEY (sender_id) REFERENCES public.clients(id) ON DELETE CASCADE ON UPDATE CASCADE;