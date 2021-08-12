-- public.clients definition

-- Drop table

DROP TABLE public.clients cascade;

CREATE TABLE public.clients (
	username varchar NOT NULL,
	"password" bytea NOT NULL,
	id serial NOT NULL,
	CONSTRAINT clients_pk PRIMARY KEY (id)
);